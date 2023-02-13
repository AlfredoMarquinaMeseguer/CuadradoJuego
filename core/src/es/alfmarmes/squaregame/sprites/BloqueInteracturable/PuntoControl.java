package es.alfmarmes.squaregame.sprites.BloqueInteracturable;

import com.badlogic.gdx.maps.MapObject;

import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.sprites.Cuadrado;
import es.alfmarmes.squaregame.tools.Constantes;

public class PuntoControl extends BloqueInteractuable {
    private PantallaDeJuego pantallaDeJuego;
    private final float posicionX;
    private final float posicionY;


    public PuntoControl(PantallaDeJuego pantallaDeJuego, MapObject object) {
        super(pantallaDeJuego, object);
        this.pantallaDeJuego = pantallaDeJuego;
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
        if (objecto.getProperties().containsKey("ganar")) {
            pantallaDeJuego.setGanado(true);

        } else {
            // Si no la tiene es un punto de control y se guarda la posición
            jugador.setUltimaX(posicionX);
            jugador.setUltimaY(posicionY);
        }
    }
}
