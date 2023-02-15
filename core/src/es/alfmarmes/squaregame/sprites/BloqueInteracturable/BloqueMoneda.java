package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.pantallas.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.sprites.Objetos.DefObjeto;
import es.alfmarmes.squaregame.sprites.Objetos.Seta;
import es.alfmarmes.squaregame.herramientas.Constantes;

public class BloqueMoneda extends BloqueInteractuable {
    private static TiledMapTileSet tileSet;
    private final int BLOQUE_VACIO_ID = 28;
    private final String  RUTA_TILESET = "tileset_gutter";

    public BloqueMoneda(PantallaDeJuego pantallaDeJuego, MapObject objeto) {
        super(pantallaDeJuego, objeto);
        fixture.setUserData(this);
        tileSet = mapa.getTileSets().getTileSet(RUTA_TILESET);
        setFiltroDeCategoria(Constantes.MONEDA_BIT);
    }

    @Override
    public void toque(Cuadrado jugador) {
        if (getCell().getTile().getId() == BLOQUE_VACIO_ID)
            SquareGame.manager.get(Constantes.R_CHOQUE).play();
        else {
            if (objeto.getProperties().containsKey("seta")) {
                pantallaDeJuego.spawnItem(new DefObjeto(new Vector2(cuerpo.getPosition().x,
                        cuerpo.getPosition().y + Constantes.TILE),
                        Seta.class));
                SquareGame.manager.get(Constantes.R_MEJORA_APARECE).play();
            } else {
                SquareGame.manager.get(Constantes.R_MONEDA).play();
            }

            getCell().setTile(tileSet.getTile(BLOQUE_VACIO_ID));
            Hud.addScore(300);
        }
    }
}
