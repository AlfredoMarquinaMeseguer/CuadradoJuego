package es.alfmarmes.squaregame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import es.alfmarmes.squaregame.screens.MenuInicio;

public class SquareGame extends Game {
    /**
     * Ancho de la pantalla
     */
    public static final int V_ANCHO = 400;
    /**
     * Alto de la pantalla
     */
    public static final int V_ALTO = 208;
    /**
     * Pixels por metro
     */
    public static final float PPM = 100;
    /**
     * Pixels que ocupa un tile
     */
    public static final int PIXELS_TILE = 16;
    /**
     * Lado de un tile, transformado con ppm
     */
    public static final float TILE = 16 / PPM;

    public static final String APP = "squareGame";
    //// Bits de colisiones
    public static final short NOTHING_BIT = 0;
    public static final short SUELO_BIT = 1;
    public static final short CUADRADO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short PINCHOS_BIT = 32;
    public static final short ENEMIGO_BIT = 64;
    public static final short CABEZA_ENEMIGO_BIT = 128;
    public static final short OBJETO_BIT = 256;
    public static final short CABEZA_CUADRADO_BIT = 512;

    public static final short CONTROL_BIT = 1024; // POR VER SI SE USA


    // Utilizarlo de forma estática puede caursar errores
    public static AssetManager manager;
    public SpriteBatch batch;

    public final static AssetDescriptor<Music> R_MUSICA = new AssetDescriptor<Music>
            ("audio/music/linkin_music.mp3", Music.class);
    public final static AssetDescriptor<Sound> R_MONEDA = new AssetDescriptor<Sound>
            ("audio/sounds/coin.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_CHOQUE = new AssetDescriptor<Sound>
            ("audio/sounds/bump.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_ROMPER_BLOQUE = new AssetDescriptor<Sound>
            ("audio/sounds/breakblock.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_BONK = new AssetDescriptor<Sound>
            ("audio/sounds/stomp.wav", Sound.class);

    public final static AssetDescriptor<Sound> R_MEJORA_APARECE = new AssetDescriptor<Sound>
            ("audio/sounds/powerup_spawn.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_MEJORA = new AssetDescriptor<>
            ("audio/sounds/powerup.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_REDUCCION = new AssetDescriptor<Sound>
            ("audio/sounds/damage.wav", Sound.class);
    public final static AssetDescriptor<Sound> R_MUERTE = new AssetDescriptor<Sound>
            ("audio/sounds/muerte.mp3", Sound.class);
    private Skin skin;
    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("skin/pixthulhu-ui.json", Skin.class,
            new SkinLoader.SkinParameter("skin/pixthulhu-ui.atlas"));


    public Skin getSkin() {
        if(skin == null ) {
            skin = new Skin(Gdx.files.internal("skin2/comic-ui.json"));
        }
        return skin;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();

        manager.load(R_MUSICA);
        manager.load(R_MONEDA);
        manager.load(R_CHOQUE);
        manager.load(R_ROMPER_BLOQUE);
        manager.load(R_BONK);
        manager.load(R_MEJORA_APARECE);
        manager.load(R_MEJORA);
        manager.load(R_REDUCCION);
        manager.load(R_MUERTE);
        manager.load(SKIN);

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
