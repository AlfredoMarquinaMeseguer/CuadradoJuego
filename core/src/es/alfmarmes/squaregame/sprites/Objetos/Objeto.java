package es.alfmarmes.squaregame.sprites.Objetos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import es.alfmarmes.squaregame.pantallas.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.herramientas.Constantes;

public abstract class Objeto extends Sprite {
    protected PantallaDeJuego pantallaDeJuego;
    protected World world;
    protected Vector2 velocidad;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Objeto(PantallaDeJuego pantallaDeJuego, float x, float y) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.world = pantallaDeJuego.getMundo();
        toDestroy = false;
        destroyed = false;

        setPosition(x,y);
        setBounds(getX(), getY(), Constantes.TILE, Constantes.TILE);
        definirObjeto();
    }

    public abstract void definirObjeto();
    public abstract void use(Cuadrado jugador);


    public void update(float dt){
        if (toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    @Override
    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }

    public void destroy(){
        toDestroy = true;
    }

    /**
     * Cambia la dirección en la que se mueve el objeto según los parametros.
     * @param xCambio cambiar velocidad horizontal
     * @param yCambio cambiar velocidad vertical
     */
    public void invertirVelocidad(boolean xCambio, boolean yCambio){
        // Cambiar la dirección horizontal
        if (xCambio){
            velocidad.x = -velocidad.x;
        }
        // Cambiar la dirección vertical
        if (yCambio){
            velocidad.y = -velocidad.y;
        }
    }
}
