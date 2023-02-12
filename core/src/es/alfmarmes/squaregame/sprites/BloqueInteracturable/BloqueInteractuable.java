package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;

public abstract class BloqueInteractuable {

    protected Fixture fixture;
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected PantallaDeJuego pantallaDeJuego;
    protected MapObject object;

    // Borrar si no se usa
    public BloqueInteractuable(PantallaDeJuego pantallaDeJuego, Rectangle bounds) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.world = pantallaDeJuego.getMundo();
        this.map = pantallaDeJuego.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / SquareGame.PPM,
                (bounds.getY() + bounds.getHeight() / 2) / SquareGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / SquareGame.PPM,
                (bounds.getHeight() / 2) / SquareGame.PPM);
        fdef.shape = shape;

        fixture = body.createFixture(fdef);
    }
    public BloqueInteractuable(PantallaDeJuego pantallaDeJuego, MapObject object) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.world = pantallaDeJuego.getMundo();
        this.map = pantallaDeJuego.getMap();
        this.bounds = ((RectangleMapObject)object).getRectangle() ;
        this.object = object;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / SquareGame.PPM,
                (bounds.getY() + bounds.getHeight() / 2) / SquareGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / SquareGame.PPM,
                (bounds.getHeight() / 2) / SquareGame.PPM);
        fdef.shape = shape;

        fixture = body.createFixture(fdef);
    }

    public  abstract  void  onHeadHit(Cuadrado mario);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return  layer.getCell((int) (body.getPosition().x * SquareGame.PPM/16),
                (int) (body.getPosition().y*SquareGame.PPM/16));
    }

}
