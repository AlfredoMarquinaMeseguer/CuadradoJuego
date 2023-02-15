package es.alfmarmes.squaregame.sprites.Objetos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import es.alfmarmes.squaregame.pantallas.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.herramientas.Constantes;

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
        shape.setRadius(Constantes.escalarAppm(7));

        fdef.filter.categoryBits = Constantes.OBJETO_BIT;
        fdef.filter.maskBits = Constantes.SUELO_BIT
                | Constantes.MONEDA_BIT
                | Constantes.BRICK_BIT
                | Constantes.PINCHOS_BIT
                | Constantes.CUADRADO_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Cuadrado jugador) {
        destroy();
        jugador.otorgarCasco();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocidad.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocidad);
    }
}
