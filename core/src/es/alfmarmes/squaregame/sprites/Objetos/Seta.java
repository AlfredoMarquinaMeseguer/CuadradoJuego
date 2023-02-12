package es.alfmarmes.squaregame.sprites.Objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;

public class Seta extends Objeto {
    public Seta(PantallaDeJuego pantallaDeJuego, float x, float y) {
        super(pantallaDeJuego, x, y);
        setRegion(pantallaDeJuego.getAtlas().findRegion("casco"), 0, 0, 16, 16);
        velocidad = new Vector2(0.7f, 0);
    }

    @Override
    public void definirObjeto() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());

        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / SquareGame.PPM);

        fdef.filter.categoryBits = SquareGame.OBJETO_BIT;
        fdef.filter.maskBits = SquareGame.SUELO_BIT
                | SquareGame.COIN_BIT
                | SquareGame.BRICK_BIT
                | SquareGame.PINCHOS_BIT
                | SquareGame.CUADRADO_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Cuadrado jugador) {
        destroy();
        jugador.grow();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocidad.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocidad);
    }
}
