package es.alfmarmes.squaregame.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.Ladrillo;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.BloqueMoneda;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.PuntoControl;
import es.alfmarmes.squaregame.sprites.Enemigos.Triangulo;

public class B2CreadorDelMundo {


    private final Array<Triangulo> goombas;

    public Array<Triangulo> getGoombas() {
        return goombas;
    }

    public B2CreadorDelMundo(PantallaDeJuego pantallaDeJuego) {
        World world = pantallaDeJuego.getMundo();
        TiledMap map = pantallaDeJuego.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Crear el suelo
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SquareGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / SquareGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / SquareGame.PPM,
                    (rect.getHeight() / 2) / SquareGame.PPM);
            fdef.shape = shape;

            body.createFixture(fdef);
        }
        // Crear las tuberias
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SquareGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / SquareGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / SquareGame.PPM,
                    (rect.getHeight() / 2) / SquareGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SquareGame.PINCHOS_BIT;
            body.createFixture(fdef);
        }
        // Crear las Monedas
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new BloqueMoneda(pantallaDeJuego, object);
        }
        // Crear las Ladrillos
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new Ladrillo(pantallaDeJuego, object);
        }
        // Crear Goombas
        goombas = new Array<Triangulo>();
        for (MapObject object:map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Triangulo(pantallaDeJuego, rect.getX()/SquareGame.PPM,
                    rect.getY()/SquareGame.PPM));
        }

        // Crear Checkpoints
        for (MapObject object:map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            new PuntoControl(pantallaDeJuego, object);
        }



    }
}
