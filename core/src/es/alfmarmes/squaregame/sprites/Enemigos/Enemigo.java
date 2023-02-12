package es.alfmarmes.squaregame.sprites.Enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import es.alfmarmes.squaregame.screens.PantallaDeJuego;

public abstract class Enemigo extends Sprite {

    protected World mundo;
    protected PantallaDeJuego pantallaDeJuego;
    public Body cuerpo;
    public Vector2 velocidad;
    protected boolean porDestruir;
    protected boolean destruido;
    public Enemigo(PantallaDeJuego pantallaDeJuego, float x, float y) {

        this.mundo = pantallaDeJuego.getMundo();
        this.pantallaDeJuego = pantallaDeJuego;
        setPosition(x, y);
        definirEnemigo();
        velocidad = new Vector2(0.5f, 0);// TEMPORAL
        cuerpo.setActive(false);
    }

    protected abstract void definirEnemigo();

    public abstract void update(float dt);

    public abstract void onHitOnHead();

    public boolean isPorDestruir() {
        return porDestruir;
    }

    public boolean isDestruido() {
        return destruido;
    }

    /**
     * Método que cambia de sentido del movimiento del enemigo
     *
     * @param xFlip Si se cambia el sentido horizontal
     * @param yFlip Si se cambia el sentido vertical
     */
    public void reverseVelocity(boolean xFlip, boolean yFlip) {
        // Cambiar la dirección horizontal
        if (xFlip) {
            velocidad.x = -velocidad.x;
        }
        // Cambiar la dirección vertical
        if (yFlip) {
            velocidad.y = -velocidad.y;
        }
    }
}
