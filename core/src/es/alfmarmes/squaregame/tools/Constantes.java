package es.alfmarmes.squaregame.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Constantes {
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
     * Escala entre Tiles y pixeles
     */
    public static final float ESCALAR_PPM = 1 / PPM;
    /**
     * Pixels que ocupa un tile
     */
    public static final int PIXELS_TILE = 16;
    /**
     * Lado de un tile, transformado con ppm
     */
    public static final float TILE = PIXELS_TILE / PPM;

    public static final String APP = "squareGame";
    //// Bits de colisiones
    /**
     * Bit para no colisionar
     */
    public static final short NOTHING_BIT = 0;
    /**
     * Bit categoria del suelo
     */
    public static final short SUELO_BIT = 1;
    /**
     * Bit categoría del jugador
     */
    public static final short CUADRADO_BIT = 2;
    /**
     * Bit categoría de los bloques rompibles
     */
    public static final short BRICK_BIT = 4;
    /**
     * Bit categoría de los bloques de
     */
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short PINCHOS_BIT = 32;
    public static final short ENEMIGO_BIT = 64;
    public static final short CABEZA_ENEMIGO_BIT = 128;
    public static final short OBJETO_BIT = 256;
    public static final short CABEZA_CUADRADO_BIT = 512;
    public static final short CONTROL_BIT = 1024;


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

    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>("skin/pixthulhu-ui.json", Skin.class,
            new SkinLoader.SkinParameter("skin/pixthulhu-ui.atlas"));

    public static float escalarAppm(float numero) {
        return (numero / PPM);
    }
}
