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

import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.Ladrillo;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.BloqueMoneda;
import es.alfmarmes.squaregame.sprites.BloqueInteracturable.PuntoControl;
import es.alfmarmes.squaregame.sprites.Enemigos.Triangulo;

public class B2CreadorDelMundo {


    private final Array<Triangulo> triangulos;

    public Array<Triangulo> getTriangulos() {
        return triangulos;
    }

    /**
     * Constructor del mundo, define todos los bloques  y enemigos, todos los objetos interacutables,
     * del mapa perteneciente a la pantalla de juego.
     * @param pantallaDeJuego pantalla de juego donde se encuentra el mapa y el mundo
     */
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
            bdef.position.set(Constantes.escalarAppm(rect.getX() + rect.getWidth() / 2),
                    Constantes.escalarAppm(rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bdef);

            shape.setAsBox(Constantes.escalarAppm(rect.getWidth() / 2),
                    Constantes.escalarAppm(rect.getHeight() / 2));
            fdef.shape = shape;

            body.createFixture(fdef);
        }
        // Crear los pinchos
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(Constantes.escalarAppm(rect.getX() + rect.getWidth() / 2),
                    Constantes.escalarAppm(rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bdef);

            shape.setAsBox(Constantes.escalarAppm(rect.getWidth() / 2),
                    Constantes.escalarAppm(rect.getHeight() / 2));
            fdef.shape = shape;
            fdef.filter.categoryBits = Constantes.PINCHOS_BIT;
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
        // Crear Triangulos
        triangulos = new Array<Triangulo>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            triangulos.add(new Triangulo(pantallaDeJuego, Constantes.escalarAppm(rect.getX()),
                    Constantes.escalarAppm(rect.getY())));
        }
        // Crear Checkpoints
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            new PuntoControl(pantallaDeJuego, object);
        }


    }
}
