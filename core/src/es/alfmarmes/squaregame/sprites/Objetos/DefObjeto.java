package es.alfmarmes.squaregame.sprites.Objetos;

import com.badlogic.gdx.math.Vector2;

public class DefObjeto {
    public Vector2 posicion;
    public Class<?> type;

    public DefObjeto(Vector2 posicion, Class<?> type) {
        this.posicion = posicion;
        this.type = type;
    }
}
