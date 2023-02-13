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

import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.tools.Constantes;

public abstract class BloqueInteractuable {
    /**
     * Pantalla de Juego en el que se encuentra el bloque
     */
    protected PantallaDeJuego pantallaDeJuego;
    /**
     * Objecto dado por Tiled
     */
    protected MapObject objecto;
    /**
     * Mundo de en el que existe el bloque
     */
    protected World mundo;
    /**
     * Mapa en el que se encuentra el bloque
     */
    protected TiledMap mapa;
    // Variables de Box 2d
    /**
     * Forma del bloque
     */
    protected Rectangle limites;
    /**
     * Cuerpo físico de Box 2d del bloque
     */
    protected Body cuerpo;
    /**
     * Elemento fijo de la
     */
    protected Fixture fixture;


    // Borrar si no se usa
    public BloqueInteractuable(PantallaDeJuego pantallaDeJuego, Rectangle limites) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.mundo = pantallaDeJuego.getMundo();
        this.mapa = pantallaDeJuego.getMap();
        this.limites = limites;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(Constantes.escalarAppm(limites.getX() + limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getY() + limites.getHeight() / 2));

        cuerpo = mundo.createBody(bdef);

        shape.setAsBox(  Constantes.escalarAppm(limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getHeight() / 2));
        fdef.shape = shape;

        fixture = cuerpo.createFixture(fdef);
    }

    //// Constructor
    /**
     * Constructor asigna la pantalla de juego, el mundo
     * @param pantallaDeJuego
     * @param objecto
     */
    public BloqueInteractuable(PantallaDeJuego pantallaDeJuego, MapObject objecto) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.mundo = pantallaDeJuego.getMundo();
        this.mapa = pantallaDeJuego.getMap();
        this.limites = ((RectangleMapObject) objecto).getRectangle();
        this.objecto = objecto;
        definirFisica();
    }

    //// Métodos constructores
    private void  definirFisica(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(Constantes.escalarAppm(limites.getX() + limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getY() + limites.getHeight() / 2));

        cuerpo = mundo.createBody(bdef);

        shape.setAsBox(Constantes.escalarAppm(limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getHeight() / 2));
        fdef.shape = shape;

        fixture = cuerpo.createFixture(fdef);
    }

    /**
     * Efecto dado sobre el jugador al tocar el bloque o golpear con la cabeza
     * @param jugador objeto del jugador que ha tocado el bloque
     */
    public abstract void toque(Cuadrado jugador);

    /**
     * Asigna la categoría del objeto de clase Fixture(elemento fijo)
     * @param filterBit Bit de categoría
     */
    public void setFiltroDeCategoria(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * Obtiene le identificador del sprite de la celda que corresponde con el bloque
     * @return identificador de la celda
     */
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) mapa.getLayers().get(1);
        return layer.getCell((int) (cuerpo.getPosition().x * Constantes.PPM / Constantes.PIXELS_TILE),
                (int) (cuerpo.getPosition().y * Constantes.PPM / Constantes.PIXELS_TILE));
    }

}
