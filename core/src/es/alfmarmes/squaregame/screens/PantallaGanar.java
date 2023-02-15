package es.alfmarmes.squaregame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.tools.Constantes;

public class PantallaGanar implements Screen {
    private Viewport viewport;

    private Stage stage;

    /**
     * Personaje que el jugador ha elegido para jugar
     */
    private Cuadrado.Personaje personaje;

    /**
     * Juego en el que nos encontramos
     */
    private SquareGame game;

    /**
     * Recibe como entrada una instancia de SquareGame (el objeto principal del juego) y un objeto
     * del tipo Cuadrado.Personaje que representa al  personaje que el jugador ha elegido para
     * jugar. En el constructor, se crea una nueva instancia de Viewport y Stage, y se agrega el
     * contenido de la pantalla (una tabla que contiene tres etiquetas) al escenario.
     * @param game el objeto principal del juego.
     * @param personaje personaje que el jugador ha elegido para jugar.
     */
    public PantallaGanar(SquareGame game, Cuadrado.Personaje personaje) {
        this.game = game;
        this.viewport = new FitViewport(Constantes.V_ANCHO, Constantes.V_ALTO);
        this.stage = new Stage(this.viewport, ((SquareGame) game).batch);
        this.personaje = personaje;
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();

        table.center();
        table.setFillParent(true);


        Label gameOverLabel = new Label("¡¡¡Has Ganado " + Cuadrado.nomberPersonaje(personaje) + "!!!", font);
        Label puntuacionLabel = new Label(Hud.getPuntuacionTotal() + " puntos", font);
        Label playAgainLabel = new Label("Click para volver al menu", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(puntuacionLabel).expandX().padTop(10f);
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    /**
     * Se llama repetidamente mientras se muestra la pantalla y se encarga de dibujar el contenido
     * de la pantalla. Si el jugador ha tocado la pantalla, vuelve a la pantalla de inicio del
     * juego.
     * @param delta Tiempo en segundos desde el último renderizado.
     */
    @Override
    public void render(float delta) {
        // Listener para volver al inicio del nivel
        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuInicio(game));
            dispose();
        }

        Gdx.gl.glClearColor(1, 1, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    /**
     * Libera recursos que ya no se van a utilizar
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
