package es.alfmarmes.squaregame.escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.herramientas.Constantes;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private static Integer contadorDeMundo;
    private static float contarTiempo;
    private static Integer puntuacion;

    Label labelTiempo;
    static Label labelPuntuacion;
    Label timeLabel;
    static Label labelVidas;
    Label labelMundo;
    Label cuadradoLabel;

    //-------------------------------------Constructor-------------------------------------
    public Hud(SpriteBatch sb) {
        this.contadorDeMundo = 300;
        this.contarTiempo = 0;
        this.puntuacion = 0;

        this.viewport = new FitViewport(Constantes.V_ANCHO, Constantes.
                V_ALTO, new OrthographicCamera());

        this.stage = new Stage(this.viewport, sb);

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);
        String personaje;
        if (Cuadrado.personajeSeleccionado == Cuadrado.Personaje.ARMANDO) {
            personaje = "ARMANDO";
        } else {
            personaje = "EDUARDO";
        }

        this.labelTiempo = new Label(String.format("%03d", contadorDeMundo),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelPuntuacion = new Label(String.format("%06d", puntuacion),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeLabel = new Label("TIEMPO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelVidas = new Label(Cuadrado.VIDAS_INICIO + "",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.labelMundo = new Label("TOQUES",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.cuadradoLabel = new Label(personaje,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tabla.add(cuadradoLabel).expandX().padTop(10);
        tabla.add(labelMundo).expandX().padTop(10);
        tabla.add(timeLabel).expandX().padTop(10);

        tabla.row();
        tabla.add(labelPuntuacion).expandX().padTop(0);
        tabla.add(labelVidas).expandX().padTop(0);
        tabla.add(labelTiempo).expandX().padTop(0);

        stage.addActor(tabla);
    }

    public static int getPuntuacion() {
        return puntuacion;
    }

    public static int getPuntuacionTotal() {
        return puntuacion + (Integer.valueOf(labelVidas.getText().toString()) * 300) + (contadorDeMundo * 10);
    }

    //-------------------------------------Métodos-------------------------------------
    public void update(float dt) {
        contarTiempo += dt;
        if (contarTiempo >= 1) {
            contadorDeMundo--;
            labelTiempo.setText(String.format("%03d", contadorDeMundo));
            contarTiempo = 0;
        }

    }

    public static void addScore(int value) {
        puntuacion += value;
        labelPuntuacion.setText(String.format("%06d", puntuacion));
    }

    public static void actualizarVidas(int vidas) {
        labelVidas.setText(String.format("%01d", vidas));
    }

    public boolean isTimeUp() {
        return (contadorDeMundo <= 0);
    }

    //-------------------------------------Métodos Override-------------------------------------
    @Override
    public void dispose() {
        stage.dispose();
    }
}
