package es.alfmarmes.squaregame.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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


    SquareGame game;
    Stage stage;
    Skin skin;
    FitViewport puerto;

    /**
     * Constructor
     * @param game juego que va a iniciar el programa
     */
    public MenuInicio(SquareGame game) {
        SquareGame.manager.get(Constantes.R_MUSICA_MENU).play();
        this.game = game;
        TextureAtlas atlas = new TextureAtlas("skin/pixthulhu-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"), atlas);
        skin.setScale(0.1f);
        SpriteBatch batch = new SpriteBatch();
        OrthographicCamera camaraJuego = new OrthographicCamera();
        puerto= new FitViewport(Constantes.V_ANCHO * 2, Constantes.V_ALTO * 2, camaraJuego);
        puerto.apply();
        camaraJuego.position.set(camaraJuego.viewportWidth / 2, camaraJuego.viewportHeight / 2, 0);
        camaraJuego.update();
        stage = new Stage(puerto, batch);
    }

    /**
     * Carga los elementos de la pantalla
     */
    private void cargarPantalla() {
        Gdx.input.setInputProcessor(stage);
        //Crea Tabla
        Table tabla = new Table();
        //La tabla llena el stage y se centra
        tabla.setFillParent(true);
        tabla.center();

        Label nombre = new Label("Cuadrado saltador", skin);
        Label subtitulo = new Label("Con Musica de Linkin Park ", skin);
        Label seleccion = new Label("Elige Personaje: ", skin);


        Sprite cuadradoImagen = new Sprite(new TextureRegion(new Texture("images/eduardo.png")));
        cuadradoImagen.setSize(80,80);

        SpriteDrawable cuadrado = new SpriteDrawable(cuadradoImagen);

        ImageButton seleccionCuadrado = new ImageButton(cuadrado);
        seleccionCuadrado.setSize(200, 200);
        seleccionCuadrado.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugar(Cuadrado.Personaje.EDUARDO);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Sprite armandoImagen = new Sprite(new TextureRegion(new Texture("images/armando.png")));
        armandoImagen.setSize(80,80);

        SpriteDrawable armando = new SpriteDrawable(armandoImagen);

        ImageButton seleccionArmando = new ImageButton(armando);
        seleccionArmando.setSize(200, 200);





        seleccionArmando.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugar(Cuadrado.Personaje.ARMANDO);
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




        //Añado botones a la tabla
        tabla.add(nombre).padBottom(10).fill(true);
        tabla.row();
        tabla.add(subtitulo).padBottom(30);
        tabla.row();
        tabla.add(seleccion).padBottom(30).padTop(50).center();
        tabla.row();
        tabla.add(seleccionCuadrado).padRight(16f);
        tabla.add(seleccionArmando).padLeft(16f);
        tabla.row();
        tabla.add(exitButton).right().padTop(10);

        //Add tabla al stage
        stage.addActor(tabla);

    }

    /**
     * Inicia el juego con el personaje seleccionado
     * @param personaje personaje seleccionado
     */
    private void jugar(Cuadrado.Personaje personaje) {
        SquareGame.manager.get(Constantes.R_MUSICA_MENU).stop();
        dispose();
        game.setScreen(new PantallaDeJuego(game, personaje));
    }


    @Override
    public void show() {

        cargarPantalla();
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(1, 0.07843137254f, 0.7568627f, 1);
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
        puerto.setScreenSize(arg0, arg1);
        cargarPantalla();
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
}
