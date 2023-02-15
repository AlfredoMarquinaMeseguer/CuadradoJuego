package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.pantallas.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.herramientas.Constantes;

public class Ladrillo extends BloqueInteractuable {


    public Ladrillo(PantallaDeJuego pantallaDeJuego, MapObject objeto) {
        super(pantallaDeJuego,objeto);
        fixture.setUserData(this);
        setFiltroDeCategoria(Constantes.BRICK_BIT);
    }

    @Override
    public void toque(Cuadrado jugador) {
        //Gdx.app.log("Brick","Le has dado al brick");
        if (jugador.isTieneCasco()){
            setFiltroDeCategoria(Constantes.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            SquareGame.manager.get(Constantes.R_ROMPER_BLOQUE).play();
        } else {
            SquareGame.manager.get(Constantes.R_CHOQUE).play();
        }


    }
}
