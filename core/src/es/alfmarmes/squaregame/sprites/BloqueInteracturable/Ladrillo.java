package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;

public class Ladrillo extends BloqueInteractuable {

    // Borrar si no se usa
    public Ladrillo(PantallaDeJuego pantallaDeJuego, Rectangle bounds) {
        super(pantallaDeJuego, bounds);
        fixture.setUserData(this);
        setCategoryFilter(SquareGame.BRICK_BIT);
    }

    public Ladrillo(PantallaDeJuego pantallaDeJuego, MapObject objeto) {
        super(pantallaDeJuego,objeto);
        fixture.setUserData(this);
        setCategoryFilter(SquareGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Cuadrado mario) {
        //Gdx.app.log("Brick","Le has dado al brick");
        if (mario.isTieneCasco()){
            setCategoryFilter(SquareGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            SquareGame.manager.get(SquareGame.R_ROMPER_BLOQUE).play();
        } else {
            SquareGame.manager.get(SquareGame.R_CHOQUE).play();
        }


    }
}
