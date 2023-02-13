package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;

public class PuntoControl extends BloqueInteractuable {
    private PantallaDeJuego pantallaDeJuego;
    private final float posicionX;
    private final float posicionY;


    public PuntoControl(PantallaDeJuego pantallaDeJuego, MapObject object) {
        super(pantallaDeJuego, object);
        this.pantallaDeJuego = pantallaDeJuego;
        posicionX = (bounds.getX() + bounds.getWidth() / 2) / SquareGame.PPM;
        posicionY = ((bounds.getY() + bounds.getHeight() / 2)/ SquareGame.PPM)
                +  SquareGame.TILE;

        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(SquareGame.CONTROL_BIT);
    }

    @Override
    public void toque(Cuadrado jugador) {
        if (object.getProperties().containsKey("ganar")){
            pantallaDeJuego.setGanado(true);
        }else{
            jugador.setUltimaX(posicionX);
            jugador.setUltimaY(posicionY);
        }

        Gdx.app.log("Control",""+posicionX+", "+posicionY );
    }
}
