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

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.sprites.Cuadrado;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    static Label vidasLabel;
    Label worldLabel;
    Label marioLabel;

    //-------------------------------------Constructor-------------------------------------
    public Hud(SpriteBatch sb) {
        this.worldTimer = 300;
        this.timeCount = 0;
        this.score = 0;

        this.viewport = new FitViewport(SquareGame.V_ANCHO, SquareGame
                .V_ALTO, new OrthographicCamera());

        this.stage = new Stage(this.viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        String personaje;
        if (Cuadrado.personajeSeleccionado == Cuadrado.Personaje.ARMANDO){
            personaje = "ARMANDO";
        }else{
            personaje = "EDUARDO";
        }

        this.countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vidasLabel = new Label(Cuadrado.VIDAS_INICIO+"",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.worldLabel = new Label("TOQUES",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.marioLabel = new Label(personaje,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX().padTop(0);
        table.add(vidasLabel).expandX().padTop(0);
        table.add(countdownLabel).expandX().padTop(0);

        stage.addActor(table);
    }

    public static int getScore() {
        return  score;
    }

    //-------------------------------------Métodos-------------------------------------
    public void update(float dt){
        timeCount += dt;
        if (timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount =0;
        }

    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    public static void actualizarVidas(int vidas){
        vidasLabel.setText(String.format("%01d", vidas));
    }

    public boolean isTimeUp(){
        return (worldTimer <= 0);
    }

    //-------------------------------------Métodos Override-------------------------------------
    @Override
    public void dispose() {
        stage.dispose();
    }
}
