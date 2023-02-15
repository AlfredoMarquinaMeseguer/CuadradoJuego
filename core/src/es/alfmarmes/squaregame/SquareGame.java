package es.alfmarmes.squaregame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.alfmarmes.squaregame.pantallas.MenuInicio;
import es.alfmarmes.squaregame.herramientas.Constantes;

public class SquareGame extends Game {
    // Utilizarlo de forma estática puede causar errores
    /**
     * Para cargar la música y la skin del menú
     */
    public static AssetManager manager;
    /**
     * Para cargar sprites
     */
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();

        manager.load(Constantes.R_MUSICA);
        manager.load(Constantes.R_MUSICA_MENU);
        manager.load(Constantes.R_MONEDA);
        manager.load(Constantes.R_CHOQUE);
        manager.load(Constantes.R_ROMPER_BLOQUE);
        manager.load(Constantes.R_BONK);
        manager.load(Constantes.R_DANNO);
        manager.load(Constantes.R_MEJORA_APARECE);
        manager.load(Constantes.R_MEJORA);
        manager.load(Constantes.R_QUITAR_CASCO);
        manager.load(Constantes.R_MUERTE);
        manager.load(Constantes.SKIN);

        manager.finishLoading();

        setScreen(new MenuInicio(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }
}
