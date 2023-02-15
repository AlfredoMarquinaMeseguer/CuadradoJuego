package es.alfmarmes.squaregame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import es.alfmarmes.squaregame.sprites.BloqueInteracturable.PuntoControl;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.sprites.Enemigos.Enemigo;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.BloqueInteractuable;
import es.alfmarmes.squaregame.sprites.Objetos.Objeto;

public class ContactListenerDeMundo implements ContactListener {
    /**
     * Método que se ejecuta cuando dos objetos empizan a colisionar.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        short categoriaDeA = fixA.getFilterData().categoryBits;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            // Collision detectada entre la cabeza del cuadrado y los bricks o los bloques moneda
            // Se golpean
            case Constantes.CABEZA_CUADRADO_BIT | Constantes.BRICK_BIT:
            case Constantes.CABEZA_CUADRADO_BIT | Constantes.MONEDA_BIT:
                if (categoriaDeA == Constantes.CABEZA_CUADRADO_BIT) {
                    ((BloqueInteractuable) fixB.getUserData()).
                            toque((Cuadrado) fixA.getUserData());
                } else {
                    ((BloqueInteractuable) fixA.getUserData()).
                            toque((Cuadrado) fixB.getUserData());
                }
                break;
            // Colisión detectada entre el cuadrado y los bricks o los bloques moneda
            // Se devuelve salto
            case Constantes.CUADRADO_BIT | Constantes.BRICK_BIT:
            case Constantes.CUADRADO_BIT | Constantes.MONEDA_BIT:
                if (categoriaDeA == Constantes.CUADRADO_BIT) {
                    ((Cuadrado) fixA.getUserData()).devolverSalto();
                } else {
                    ((Cuadrado) fixB.getUserData()).devolverSalto();
                }
                break;
            // Colisión jugador con punto de control
            // Guardar posición de punto control
            case Constantes.CUADRADO_BIT | Constantes.CONTROL_BIT:
                if (categoriaDeA == Constantes.CONTROL_BIT) {
                    ((PuntoControl) fixA.getUserData()).toque((Cuadrado) fixB.getUserData());
                } else {
                    ((PuntoControl) fixB.getUserData()).toque((Cuadrado) fixA.getUserData());
                }
                break;
            // Colisión jugador con enemigo
            // Destruir enemigo
            case Constantes.CUADRADO_BIT | Constantes.CABEZA_ENEMIGO_BIT:
                if (categoriaDeA == Constantes.CABEZA_ENEMIGO_BIT) {
                    ((Enemigo) fixA.getUserData()).pisado();
                } else {
                    ((Enemigo) fixB.getUserData()).pisado();
                }
                break;
            // Colisión enemigo con el entorno
            case Constantes.SUELO_BIT | Constantes.ENEMIGO_BIT:
            case Constantes.PINCHOS_BIT | Constantes.ENEMIGO_BIT:
            case Constantes.BRICK_BIT | Constantes.ENEMIGO_BIT:
                if (categoriaDeA == Constantes.ENEMIGO_BIT) {
                    ((Enemigo) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemigo) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            // Colisión del cuadrado con un enemigo
            // Golpea cuadrado
            case Constantes.CUADRADO_BIT | Constantes.ENEMIGO_BIT:
                if (categoriaDeA == Constantes.ENEMIGO_BIT &&
                        !isDestruir(fixA)) {
                    ((Cuadrado) fixB.getUserData()).golpe();
                } else if (isDestruir(fixB)) {
                    ((Cuadrado) fixA.getUserData()).golpe();
                }

                break;
            // Colisión del personaje con los pinchos
            case Constantes.CUADRADO_BIT | Constantes.PINCHOS_BIT:
                if (categoriaDeA == Constantes.CUADRADO_BIT) {
                    ((Cuadrado) fixA.getUserData()).golpe();

                } else {
                    ((Cuadrado) fixB.getUserData()).golpe();
                }
                break;
            //Colisión entre enemigos
            case Constantes.ENEMIGO_BIT:
                ((Enemigo) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemigo) fixB.getUserData()).reverseVelocity(true, false);
                break;
            // En caso de que un objeto se choque con el terreno
            case Constantes.SUELO_BIT | Constantes.OBJETO_BIT:
            case Constantes.BRICK_BIT | Constantes.OBJETO_BIT:
            case Constantes.PINCHOS_BIT | Constantes.OBJETO_BIT:
                if (categoriaDeA == Constantes.OBJETO_BIT) {
                    ((Objeto) fixA.getUserData()).invertirVelocidad(true, false);
                } else {
                    ((Objeto) fixB.getUserData()).invertirVelocidad(true, false);
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.OBJETO_BIT:
                if (categoriaDeA == Constantes.OBJETO_BIT) {
                    ((Objeto) fixA.getUserData()).use((Cuadrado) fixB.getUserData());
                } else {
                    ((Objeto) fixB.getUserData()).use((Cuadrado) fixA.getUserData());
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.SUELO_BIT:
                if (categoriaDeA == Constantes.CUADRADO_BIT) {
                    ((Cuadrado) fixA.getUserData()).reiniciarSaltos();
                } else {
                    ((Cuadrado) fixB.getUserData()).reiniciarSaltos();
                }
                Gdx.app.log("Salto", "doble dado");
                break;

        }
    }

    /**
     * Comprueba si fix es un enemigo a destruir
     *
     * @param fix Fixture del enemigo
     * @return (es un enemigo a destuir ?)
     */
    private static boolean isDestruir(Fixture fix) {
        boolean destroy;
        try {
            destroy = !((Enemigo) fix.getUserData()).isPorDestruir() &&
                    !((Enemigo) fix.getUserData()).isDestruido();
        } catch (ClassCastException ex) {
            destroy = false;
        }
        return destroy;
    }

    /**
     * Método que se ejecuta cuando dos objetos dejan de colisionar.
     */
    @Override
    public void endContact(Contact contact) {
    }

    /**
     * Método que se ejecuta cuando un objeto comienza a colisionar con otro objeto
     * y continua colisionando en el tiempo.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * Método que se ejecuta cuando un objeto comienza a colisionar con otro objeto
     * y continua colisionando en el tiempo.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
