package es.alfmarmes.squaregame.sprites.Objetos;

import com.badlogic.gdx.math.Vector2;

public class DefObjeto {
    public Vector2 position;
    public Class<?> type;

    public DefObjeto(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }
}
