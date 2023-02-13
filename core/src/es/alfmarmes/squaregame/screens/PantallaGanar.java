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

    private Cuadrado.Personaje personaje;

    private SquareGame game;

    public PantallaGanar(SquareGame game, Cuadrado.Personaje personaje) {
        this.game = game;
        this.viewport = new FitViewport(Constantes.V_ANCHO, Constantes.V_ALTO);
        this.stage = new Stage(this.viewport, ((SquareGame) game).batch);
        this.personaje = personaje;
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();

        table.center();
        table.setFillParent(true);


        Label gameOverLabel = new Label("¡¡¡Has Ganado "+Cuadrado.nomberPersonaje(personaje)+"!!!", font);
        Label puntuacionLabel = new Label(Hud.getScore()+" puntos", font);
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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
