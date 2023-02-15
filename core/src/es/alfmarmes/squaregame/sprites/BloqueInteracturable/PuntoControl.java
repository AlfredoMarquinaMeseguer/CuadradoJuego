package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;

import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.tools.Constantes;

public class PuntoControl extends BloqueInteractuable {
    /**
     * Posición X de checkpoint que otorga al tocarlo
     */
    private final float posicionX;

    /**
     * Posición Y de checkpoint que otorga al tocarlo
     */
    private final float posicionY;

    /**
     * Se llama a super, se guarda la posicion de respawn que da el bloque, convierte el bloque
     * en sensor y otorga categoría al bloque.
     * @param pantallaDeJuego pantalla donde se encuentra el objeto
     * @param object
     */
    public PuntoControl(PantallaDeJuego pantallaDeJuego, MapObject object) {
        super(pantallaDeJuego, object);
        posicionX = Constantes.escalarAppm(limites.getX() + limites.getWidth() / 2);
        posicionY = Constantes.escalarAppm(limites.getY() + limites.getHeight() / 2)
                + Constantes.TILE;

        fixture.setUserData(this);
        fixture.setSensor(true);
        setFiltroDeCategoria(Constantes.CONTROL_BIT);
    }

    /**
     * Realiza la acción al tener un toque con el jugador
     *
     * @param jugador objeto jugador
     */
    @Override
    public void toque(Cuadrado jugador) {
        // Si tiene la propiedad ganar se gana el juego
        if (objeto.getProperties().containsKey("ganar")) {
            pantallaDeJuego.setGanado(true);

        } else {
            // Si no la tiene es un punto de control y se guarda la posición
            jugador.setUltimaX(posicionX);
            jugador.setUltimaY(posicionY);
        }
    }
}
