package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.sprites.Objetos.DefObjeto;
import es.alfmarmes.squaregame.sprites.Objetos.Seta;

public class BloqueMoneda extends BloqueInteractuable {
    private static TiledMapTileSet tileSet;
    private final int BLOQUE_VACIO_ID = 28;
    private final String  RUTA_TILESET = "tileset_gutter";


    // Borrar si no se usa
    public BloqueMoneda(PantallaDeJuego pantallaDeJuego, Rectangle bounds) {
        super(pantallaDeJuego, bounds);
        fixture.setUserData(this);
        tileSet = map.getTileSets().getTileSet(RUTA_TILESET);
        setCategoryFilter(SquareGame.COIN_BIT);
    }


    public BloqueMoneda(PantallaDeJuego pantallaDeJuego, MapObject objeto) {
        super(pantallaDeJuego, objeto);
        fixture.setUserData(this);
        tileSet = map.getTileSets().getTileSet(RUTA_TILESET);
        setCategoryFilter(SquareGame.COIN_BIT);
    }

    /* @Override
     public void onHeadHit(Cuadrado mario) {
         Gdx.app.log("Coin", "Le has dado al coin block");
         if (getCell().getTile().getId() == BLOQUE_VACIO_ID) {
             SquareGame.manager.get(SquareGame.R_CHOQUE, Sound.class).play();
         } else {
             getCell().setTile(tileSet.getTile(BLOQUE_VACIO_ID));
             SquareGame.manager.get(SquareGame.R_MONEDA, Sound.class).play();
             Hud.addScore(300);
             playScreen.spawnItem(new DefObjeto(new Vector2(body.getPosition().x,
                     body.getPosition().y + 16 / SquareGame.PPM),
                     Seta.class));
         }


     }*/
    @Override
    public void toque(Cuadrado jugador) {
        if (getCell().getTile().getId() == BLOQUE_VACIO_ID)
            SquareGame.manager.get(SquareGame.R_CHOQUE).play();
        else {
            if (object.getProperties().containsKey("seta")) {
                pantallaDeJuego.spawnItem(new DefObjeto(new Vector2(body.getPosition().x,
                        body.getPosition().y + 16 / SquareGame.PPM),
                        Seta.class));
                SquareGame.manager.get(SquareGame.R_MEJORA_APARECE).play();
            } else {
                SquareGame.manager.get(SquareGame.R_MONEDA).play();
            }

            getCell().setTile(tileSet.getTile(BLOQUE_VACIO_ID));
            Hud.addScore(300);
        }
    }
}
