package es.alfmarmes.squaregame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.PuntoControl;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.sprites.Enemigos.Enemigo;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.BloqueInteractuable;
import es.alfmarmes.squaregame.sprites.Objetos.Objeto;

public class ContactListenerDeMundo implements ContactListener {
    private static int DEATHCOUNT = 0;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        short categoriaDeA = fixA.getFilterData().categoryBits;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case Constantes.CABEZA_CUADRADO_BIT | Constantes.BRICK_BIT:
            case Constantes.CABEZA_CUADRADO_BIT | Constantes.COIN_BIT:
                if (categoriaDeA == Constantes.CABEZA_CUADRADO_BIT) {
                    ((BloqueInteractuable) fixB.getUserData()).
                            toque((Cuadrado) fixA.getUserData());
                } else {
                    ((BloqueInteractuable) fixA.getUserData()).
                            toque((Cuadrado) fixB.getUserData());
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.BRICK_BIT:
            case Constantes.CUADRADO_BIT | Constantes.COIN_BIT:
                if (categoriaDeA == Constantes.CUADRADO_BIT) {
                    ((Cuadrado) fixA.getUserData()).devolverSalto();
                } else {
                    ((Cuadrado) fixB.getUserData()).devolverSalto();
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.CONTROL_BIT:
                if (categoriaDeA == Constantes.CONTROL_BIT) {
                    ((PuntoControl) fixA.getUserData()).toque((Cuadrado) fixB.getUserData());
                } else {
                    ((PuntoControl) fixB.getUserData()).toque((Cuadrado) fixA.getUserData());
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.CABEZA_ENEMIGO_BIT:
                if (categoriaDeA == Constantes.CABEZA_ENEMIGO_BIT) {
                    ((Enemigo) fixA.getUserData()).onHitOnHead();
                } else {
                    ((Enemigo) fixB.getUserData()).onHitOnHead();
                }
                break;
            case Constantes.SUELO_BIT | Constantes.ENEMIGO_BIT:
            case Constantes.PINCHOS_BIT | Constantes.ENEMIGO_BIT:
            case Constantes.BRICK_BIT | Constantes.ENEMIGO_BIT:
                if (categoriaDeA == Constantes.ENEMIGO_BIT) {
                    ((Enemigo) fixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemigo) fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case Constantes.CUADRADO_BIT | Constantes.ENEMIGO_BIT:
                if (categoriaDeA == Constantes.ENEMIGO_BIT &&
                        !isDestroy(fixA)) {
                    ((Cuadrado) fixB.getUserData()).hit();
                } else if (isDestroy(fixB)) {
                    ((Cuadrado) fixA.getUserData()).hit();
                }

                break;
            case Constantes.CUADRADO_BIT | Constantes.PINCHOS_BIT:
                if (categoriaDeA == Constantes.CUADRADO_BIT) {
                    ((Cuadrado) fixA.getUserData()).hit();

                } else {
                    ((Cuadrado) fixB.getUserData()).hit();
                }
                break;
            case Constantes.ENEMIGO_BIT://Colision entre enemigos
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

    private static boolean isDestroy(Fixture fixA) {
        boolean destroy;
        try {
            destroy = !((Enemigo) fixA.getUserData()).isPorDestruir() &&
                    !((Enemigo) fixA.getUserData()).isDestruido();
        } catch (ClassCastException ex) {
            destroy = false;
        }
        return destroy;
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
