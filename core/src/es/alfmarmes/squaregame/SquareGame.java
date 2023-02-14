package es.alfmarmes.squaregame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import es.alfmarmes.squaregame.screens.MenuInicio;
import es.alfmarmes.squaregame.tools.Constantes;

public class SquareGame extends Game {
    // Utilizarlo de forma estática puede caursar errores
    public static AssetManager manager;
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
