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

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
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

        this.countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.scoreLabel = new Label(String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.levelLabel = new Label("1-1",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.worldLabel = new Label("WORLD",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.marioLabel = new Label("MARIO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX().padTop(0);
        table.add(levelLabel).expandX().padTop(0);
        table.add(countdownLabel).expandX().padTop(0);

        stage.addActor(table);
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

    public boolean isTimeUp(){
        return (worldTimer <= 0);
    }

    //-------------------------------------Métodos Override-------------------------------------
    @Override
    public void dispose() {
        stage.dispose();
    }
}
