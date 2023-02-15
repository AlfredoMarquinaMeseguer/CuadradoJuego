package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import es.alfmarmes.squaregame.pantallas.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.herramientas.Constantes;

public abstract class BloqueInteractuable {
    /**
     * Pantalla de Juego en el que se encuentra el bloque
     */
    protected PantallaDeJuego pantallaDeJuego;
    /**
     * Objecto dado por Tiled
     */
    protected MapObject objeto;
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





    //// Constructor
    /**
     * Constructor asigna los campos y llama a deifnirFisica
     * @param pantallaDeJuego pantalla en la que se encuentra el bloque
     * @param objeto creado por tiled, contiene la posición y tamaño, entre otras cosas
     */
    public BloqueInteractuable(PantallaDeJuego pantallaDeJuego, MapObject objeto) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.mundo = pantallaDeJuego.getMundo();
        this.mapa = pantallaDeJuego.getMap();
        this.limites = ((RectangleMapObject) objeto).getRectangle();
        this.objeto = objeto;
        definirFisica();
    }


    //// Métodos constructores
    /**
     * Define la física de un objeto en un mundo de Box2D en LibGDX.
     * La posición y tipo de cuerpo se define a través de un objeto BodyDef,
     * la forma se define a través de un objeto PolygonShape y las propiedades
     * de la forma se definen a través de un objeto FixtureDef. Luego se crea
     * un cuerpo en el mundo y se crea una fixture con la forma definida.
     */
    private void definirFisica() {
        // Se crean los objetos BodyDef, FixtureDef y PolygonShape
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Se define el tipo de cuerpo como estático y se establece la posición
        // del centro del objeto en el mundo.
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(Constantes.escalarAppm(limites.getX() + limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getY() + limites.getHeight() / 2));

        // Se crea un cuerpo en el mundo y se asigna a la variable de clase 'cuerpo'.
        cuerpo = mundo.createBody(bdef);

        // Se define la forma del objeto como un cuadrado y se establecen sus
        // propiedades a través del objeto FixtureDef.
        shape.setAsBox(Constantes.escalarAppm(limites.getWidth() / 2),
                Constantes.escalarAppm(limites.getHeight() / 2));
        fdef.shape = shape;

        // Se crea una fixture en el cuerpo con las propiedades definidas en fdef.
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
