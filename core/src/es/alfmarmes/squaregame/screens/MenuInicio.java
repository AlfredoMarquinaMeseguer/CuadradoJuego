package es.alfmarmes.squaregame.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.tools.Constantes;

/**
 * Pantalla principal del juego, donde se muestra el menú principal
 *
 * @author Santiago Faci
 * @version 1.0
 */
public class MenuInicio implements Screen {


    private final TextureAtlas atlas;
    private final SpriteBatch batch;
    private final TextureAtlas imagenes;
    private OrthographicCamera camaraJuego;
    private FitViewport puerto;
    SquareGame game;
    Stage stage;
    Skin skin;
    Table table;

    public MenuInicio(SquareGame game) {
        SquareGame.manager.get(Constantes.R_MUSICA_MENU).play();
        this.game = game;
        atlas = new TextureAtlas("skin/pixthulhu-ui.atlas");
        this.imagenes = new TextureAtlas("tileset/enemigosyobjeto.atlas");
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"), atlas);

//        skin.getFont("font").getData().setScale(0.5f);
        skin.setScale(0.1f);
        batch = new SpriteBatch();
        camaraJuego = new OrthographicCamera();
        puerto = new FitViewport(Constantes.V_ANCHO * 2, Constantes.V_ALTO * 2, camaraJuego);
        puerto.apply();
        camaraJuego.position.set(camaraJuego.viewportWidth / 2, camaraJuego.viewportHeight / 2, 0);
        camaraJuego.update();
        stage = new Stage(puerto, batch);
    }
    /*
    http://localhost:63342/SquareGame/gdx-1.11.0-javadoc.jar/stylesheet.css?_ijt=sct8544u9oogr99vs22hedu9c4
     */

    private void loadScreen() {
        Gdx.input.setInputProcessor(stage);
        //Crea Tabla
        Table mainTable = new Table();
        //La tabla llena el stage y se centra
        mainTable.setFillParent(true);
        mainTable.center();

        Label nombre = new Label("Cuadrado saltar\u00EDn", skin);
        Label subtitulo = new Label("Con Música de Linkin Park ", skin);

        Sprite cuadradoImagen = new Sprite(imagenes.findRegion("cuadrado"),
                0, 0, 16, 16);
        SpriteDrawable cuadrado = new SpriteDrawable(cuadradoImagen);

        ImageButton seleccionCuadrado = new ImageButton(cuadrado);
        seleccionCuadrado.setSize(200, 200);

        Sprite armandoImagen = new Sprite(imagenes.findRegion("armando"),
                0, 0, 16, 16);
        SpriteDrawable armando = new SpriteDrawable(armandoImagen);

        ImageButton seleccionArmando = new ImageButton(armando);
        seleccionArmando.setSize(200, 200);

        TextButton botonEduardo = new TextButton("Eduardo", skin);
        botonEduardo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugar(event, Cuadrado.Personaje.EDUARDO);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        TextButton botonArmando = new TextButton("Armando", skin);
        botonArmando.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugar(event, Cuadrado.Personaje.ARMANDO);
                return super.touchDown(event, x, y, pointer, button);
            }

        });

        TextButton exitButton = new TextButton("Salir", skin);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Gdx.app.exit();

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        seleccionCuadrado.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Añado botones a la tabla
        mainTable.add(nombre).padBottom(10).fill(true);
        mainTable.row().center();
        mainTable.add(subtitulo).padBottom(30);
        mainTable.row().center();
        mainTable.add(seleccionCuadrado).padRight(16f);
        mainTable.add(seleccionArmando).padLeft(16f);
        mainTable.row().center();
        mainTable.add(botonEduardo);
        mainTable.add(botonArmando);
        mainTable.row();
        mainTable.add(exitButton).right();

        //Add tabla al stage
        stage.addActor(mainTable);

    }

    private boolean jugar(Event event, Cuadrado.Personaje personaje) {
        SquareGame.manager.get(Constantes.R_MUSICA_MENU).stop();
        dispose();
        game.setScreen(new PantallaDeJuego(game, personaje));
        return true;
    }

    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        table.add(button).fillX().padBottom(10);
        table.row();
        return button;
    }

    @Override
    public void show() {

        loadScreen();
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Pinta el menú
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {

        stage.dispose();
    }

    @Override
    public void hide() {


    }

    @Override
    public void pause() {


    }


    @Override
    public void resize(int arg0, int arg1) {
        // TODO Auto-generated method stub
        loadScreen();
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
}
