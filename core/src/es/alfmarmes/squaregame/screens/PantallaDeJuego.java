package es.alfmarmes.squaregame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.concurrent.LinkedBlockingQueue;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.sprites.Enemigos.Enemigo;
import es.alfmarmes.squaregame.sprites.Objetos.DefObjeto;
import es.alfmarmes.squaregame.sprites.Objetos.Objeto;
import es.alfmarmes.squaregame.sprites.Objetos.Seta;
import es.alfmarmes.squaregame.tools.B2CreadorDelMundo;
import es.alfmarmes.squaregame.tools.Constantes;
import es.alfmarmes.squaregame.tools.ContactListenerDeMundo;

public class PantallaDeJuego implements Screen {


    private final Music musica;
    private SquareGame game;
    private TextureAtlas atlas;

    // Relacionados con la vista
    private OrthographicCamera camaraJuego;
    private Viewport puerto;
    private Hud hud;
    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Variables Box2d
    private World mundo;
    private Box2DDebugRenderer b2dr;

    //private Box2D
    private B2CreadorDelMundo creadorDelMundo;

    // Sprites
    private Cuadrado jugador;

    // Objetos
    private Array<Objeto> consumible;
    private LinkedBlockingQueue<DefObjeto> consumiblesAparecer;
    private boolean ganado;

    public PantallaDeJuego(SquareGame game, Cuadrado.Personaje personajeSeleccionado) {
        this.atlas = new TextureAtlas("tileset/enemigosyobjeto.atlas");


        this.game = game;
        camaraJuego = new OrthographicCamera();
        puerto = new FitViewport(Constantes.V_ANCHO / Constantes.PPM,
                Constantes.V_ALTO / Constantes.PPM, camaraJuego);


        // Cargar mapa de juego y setup el renderizador
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("tileset/level1plus.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constantes.PPM);

        // Colocar la camara en el lugar inicial correto
        camaraJuego.position.set(puerto.getWorldWidth() / 2,
                puerto.getWorldHeight() / 2, 0);


        // Esto se va a cambiar en algun momento
        mundo = new World(new Vector2(0, -10), true);
                b2dr = new Box2DDebugRenderer();


        creadorDelMundo = new B2CreadorDelMundo(this);

        // Jugador
        this.jugador = new Cuadrado(this, personajeSeleccionado);

        mundo.setContactListener(new ContactListenerDeMundo());
        hud = new Hud(game.batch);

        // Música
        musica = SquareGame.manager.get(Constantes.R_MUSICA);
        musica.setLooping(true);
        musica.play();

        consumible = new Array<>();
        consumiblesAparecer = new LinkedBlockingQueue<>();
        ganado = false;
    }

    //-------------------------------------Getters y setters-------------------------------------

    public Hud getHud() {
        return hud;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getMundo() {
        return mundo;
    }

    public void update(float dt) {
        handleInput(dt);
        handleSpawningItems();

        mundo.step(1 / 60f, 6, 2);


        //Actualizar posición del jugador
        jugador.actualizar(dt);
        // Actualizar a los enemigo (Goombas)
        for (Enemigo enemigo : creadorDelMundo.getGoombas()) {
            enemigo.update(dt);
            if (enemigo.getX() < jugador.getX() + (224 * Constantes.ESCALAR_PPM)) {
                enemigo.cuerpo.setActive(true);
            }
        }
        // Actualizar objetos
        for (Objeto item : consumible) {
            item.update(dt);
        }

        // Centrar camara en el jugador y actualizar
        if (!jugador.isMuerto()) {
            camaraJuego.position.x = jugador.cuerpo.getPosition().x;
        }

        camaraJuego.update();

        //Actualizar interfaz grafica
        hud.update(dt);
        // Renderizar solo la parte del mundo que se ve en la camara
        renderer.setView(camaraJuego);

    }

    private void handleInput(float dt) {
        // Se puede restringir el
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
                && jugador.getNumeroSaltos() < jugador.getMaxSaltos()) {
            jugador.sumarSalto();
            jugador.cuerpo.applyLinearImpulse(new Vector2(0, 4f),
                    jugador.cuerpo.getWorldCenter(), true);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                && jugador.cuerpo.getLinearVelocity().x <= 2) {
            jugador.cuerpo.applyLinearImpulse(new Vector2(0.1f, 0),
                    jugador.cuerpo.getWorldCenter(), true);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                && jugador.cuerpo.getLinearVelocity().x >= -2) {
            jugador.cuerpo.applyLinearImpulse(new Vector2(-0.1f, 0),
                    jugador.cuerpo.getWorldCenter(), true);
        }
    }

    public void handleSpawningItems() {
        /*if (itemsToSpawn.isEmpty()) {
            return;
        }*/
        if (!consumiblesAparecer.isEmpty()) {
            DefObjeto idef = consumiblesAparecer.poll();
            if (idef.type == Seta.class) {
                consumible.add(new Seta(this, idef.position.x, idef.position.y));
            }
        }

    }

    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderizar el mapa de juego
        renderer.render();

        // Renderizar el Box2DDebugLines
//        b2dr.render(mundo, camaraJuego.combined);


        game.batch.setProjectionMatrix(camaraJuego.combined);
        game.batch.begin();
        jugador.draw(game.batch);
        // Dibujar enemigos
        for (Enemigo enemigo : creadorDelMundo.getGoombas()) {
            enemigo.draw(game.batch);
        }

        // Dibujar objetos
        for (Objeto item : consumible) {
            item.draw(game.batch);
        }

        game.batch.end();

        // Dibujar el hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            perder();
        } else if (ganado) {
            ganar();
        }

    }

    private void ganar() {
        SquareGame.manager.get(Constantes.R_MUSICA).stop();
        game.setScreen(new PantallaGanar(game, Cuadrado.personajeSeleccionado));
        dispose();
    }

    private void perder() {
        SquareGame.manager.get(Constantes.R_MUSICA).stop();
        game.setScreen(new GameOverScreen(game, Cuadrado.personajeSeleccionado));
        dispose();
    }

    /**
     * Determina si el juego debe acabarse ya o no. Se debe acabar tres segundos después de
     * muerto
     *
     * @return Acabar juego
     */
    public boolean gameOver() {
        return (jugador.isMuerto() && jugador.getContadorEstado() > 3);
    }


    public void spawnItem(DefObjeto idef) {
        consumiblesAparecer.add(idef);
    }

    //-------------------------------------Métodos Override-------------------------------------
    @Override
    public void resize(int width, int height) {
        puerto.update(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        mundo.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public void setGanado(boolean b) {
        this.ganado = b;
    }
}
