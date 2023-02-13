package es.alfmarmes.squaregame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.tools.Constantes;

public class Cuadrado extends Sprite {
    public static final int VIDAS_INICIO = 5;
    private static final int I_IMG_PARADO = 0;
    private static final int I_IMG_SALTANDO = 4;
    private static final int I_IMG_FIN = 5;

    public static Personaje personajeSeleccionado;

    /**
     * Devuelve el nombre el  personaje cuya enumeración es igual a personaje
     * @param personaje enumeración del personaje
     * @return nombre del personaje
     */
    public static String nomberPersonaje(Personaje personaje) {
        String devolver;
        if(personaje == Personaje.ARMANDO){
            devolver = "ARMANDO";
        }else {
            devolver = "EDUARDO";
        }
        return devolver;
    }


    public enum Estado {
        FALLING, JUMPING, STANDING, RUNNING, MUERTO, CAMBIANDO
    }
    public enum Personaje {
        EDUARDO, ARMANDO
    }

    public World mundo;
    public Body cuerpo;

    public Estado estadoActual;
    public Estado estadoAnterior;



    private boolean corriendoDerecha;
    private boolean tieneCasco;
    private float contadorEstado;

    private boolean runGrowAnimation;
    private boolean decrecer;
    private boolean muerto;
    private PantallaDeJuego pantallaDeJuego;

    private int numeroSaltos;
    private int maxSaltos;

    // Animaciones de cuadrado normal
    private TextureRegion parado;
    private TextureRegion marioJump;
    private Animation correr;
    private TextureRegion cuadradoMuerto;
    private int vidas;


    // Animaciones de cuadrado con casco
    private TextureRegion cascoParado;
    private TextureRegion cascoJump;
    private Animation cascoCorre;

    // Animación cambio de estado
    private Animation crecimiento;

    //TODO para los checkpoints
    private float ultimaX;
    private float ultimaY;


//    private Array<FireBall> fireballs;

    public Cuadrado(PantallaDeJuego pantallaDeJuego, Cuadrado.Personaje personaje) {
        this.pantallaDeJuego = pantallaDeJuego;
        this.mundo = pantallaDeJuego.getMundo();
        this.vidas = VIDAS_INICIO;
        this.estadoActual = Estado.STANDING;
        this.estadoAnterior = Estado.STANDING;
        this.contadorEstado = 0;
        this.corriendoDerecha = true;
        this.numeroSaltos = 0;


        Array<TextureRegion> frames = new Array<>();
        String cuadrado;
        String casco;
        personajeSeleccionado = personaje;
        if (personajeSeleccionado == Personaje.ARMANDO) {
            this.maxSaltos = 2 ;
            cuadrado = "armando";
            casco = "armando_casco";
        } else {
            this.maxSaltos = 1;
            cuadrado = "cuadrado";
            casco = "cuadrado_casco";
        }
        //// Sprite parado
        // Sin casco
        TextureRegion cuadradoPequenno = pantallaDeJuego.getAtlas().findRegion(cuadrado);
        TextureRegion cuadradoCasco = pantallaDeJuego.getAtlas().findRegion(casco);
        parado = new TextureRegion(cuadradoPequenno,
                I_IMG_PARADO, 0, Constantes.PIXELS_TILE, Constantes.PIXELS_TILE);
        // Con casco
        cascoParado = new TextureRegion(cuadradoCasco, I_IMG_PARADO, 0,
                Constantes.PIXELS_TILE, Constantes.PIXELS_TILE);
        //// Animación de correr sin casco
        for (int i = 0; i < I_IMG_FIN; i++) { // Coger imagenes de la SpriteSheet
            frames.add(new TextureRegion(cuadradoPequenno, i * Constantes.PIXELS_TILE, 0,
                    Constantes.PIXELS_TILE, Constantes.PIXELS_TILE));
        }
        correr = new Animation<>(0.1f, frames);
        frames.clear();

        //// Animación de correr con casco
        for (int i = 0; i < I_IMG_FIN; i++) {
            frames.add(new TextureRegion(cuadradoCasco,
                    i * Constantes.PIXELS_TILE, 0,
                    Constantes.PIXELS_TILE, Constantes.PIXELS_TILE));
        }

        cascoCorre = new Animation<>(0.1f, frames);
        frames.clear();

        //get jump animation frames and add them to marioJump Animation
        marioJump = new TextureRegion(cuadradoPequenno,
                I_IMG_SALTANDO * Constantes.PIXELS_TILE, 0,
                Constantes.PIXELS_TILE, Constantes.PIXELS_TILE);
        cascoJump = new TextureRegion(cuadradoCasco,
                I_IMG_SALTANDO * Constantes.PIXELS_TILE, 0,
                Constantes.PIXELS_TILE, Constantes.PIXELS_TILE);

        //get set animation frames from growing mario
        frames.add(cascoParado);
        frames.add(parado);
        frames.add(cascoParado);
        frames.add(parado);
        crecimiento = new Animation<>(0.2f, frames);


        // Crar region de textura de cuadrado muerto
        cuadradoMuerto = parado;


        definirCuadrado();
        setBounds(0, 0, Constantes.TILE, Constantes.TILE);
        setRegion(parado);

        //fireballs = new Array<FireBall>();
    }

    //-------------------------------------Getters y setters-------------------------------------
    public boolean isMuerto() {
        return muerto;
    }

    public boolean isTieneCasco() {
        return tieneCasco;
    }

    public float getContadorEstado() {
        return contadorEstado;
    }

    public int getNumeroSaltos() {
        return numeroSaltos;
    }

    public int getMaxSaltos() {
        return maxSaltos;
    }

    public float getUltimaX() {
        return ultimaX;
    }

    public void setUltimaX(float ultimaX) {
        this.ultimaX = ultimaX;
    }

    public float getUltimaY() {
        return ultimaY;
    }

    public void setUltimaY(float ultimaY) {
        this.ultimaY = ultimaY;
    }

    //-------------------------------------Métodos-------------------------------------

    /**
     * Suma un salto al número de saltos
     */
    public void sumarSalto() {
        if (numeroSaltos < maxSaltos){
            this.numeroSaltos++;
        }
    }

    /**
     * Resta un salto al número de saltos
     */
    public void devolverSalto() {
        if (numeroSaltos > 0){
            this.numeroSaltos--;
        }
    }

    /**
     * Reinicia el contador de saltos
     */
    public void reiniciarSaltos() {
        numeroSaltos = 0;
    }

    /**
     * Actualiza el estado del Cuadrado cada frame
     *
     * @param dt tiempo en el que se llama el cuadrado
     */
    public void actualizar(float dt) {

        if (pantallaDeJuego.getHud().isTimeUp() && !isMuerto()) {
            die();
        }
        setPosition(cuerpo.getPosition().x - (getWidth() / 2), cuerpo.getPosition().y - (getHeight() / 2));
        setRegion(getFrame(dt));

        if (decrecer) {
            decrecer();
        }
    }

    /**
     * Devuelve la imagen que se debe dibujar en el frame dado
     *
     * @param dt Tiempo deframa dado
     * @return Imagen a dibujar
     */
    private TextureRegion getFrame(float dt) {
        estadoActual = getEstado();

        TextureRegion region;
        switch (estadoActual) {
            case MUERTO:
                region = cuadradoMuerto;
                break;
            case CAMBIANDO:
                region = (TextureRegion) crecimiento.getKeyFrame(contadorEstado);
                if (crecimiento.isAnimationFinished(contadorEstado)) {
                    runGrowAnimation = false;
                }
                break;
            case JUMPING:
                if (tieneCasco) {
                    region = cascoJump;
                } else {
                    region = marioJump;
                }

                break;
            case RUNNING:
                if (tieneCasco) {
                    region = (TextureRegion) cascoCorre.getKeyFrame(contadorEstado, true);
                } else {
                    region = (TextureRegion) correr.getKeyFrame(contadorEstado, true);
                }
                break;
            case FALLING:
            case STANDING:
            default:
                if (tieneCasco) {
                    region = cascoParado;
                } else {
                    region = parado;
                }

        }

        if ((cuerpo.getLinearVelocity().x < 0 || !corriendoDerecha) && !region.isFlipX()) {
            region.flip(true, false);
            corriendoDerecha = false;
        } else if ((cuerpo.getLinearVelocity().x > 0 || corriendoDerecha) && region.isFlipX()) {
            region.flip(true, false);
            corriendoDerecha = true;
        }

        contadorEstado = (estadoActual == estadoAnterior) ? contadorEstado + dt : 0;
        estadoAnterior = estadoActual;


        return region;
    }

    /**
     * Calcual y devuelve el estado actual del cuadrado
     *
     * @return estado actual
     */
    private Estado getEstado() {
        if (muerto) {
            return Estado.MUERTO;
        } else if (runGrowAnimation) {
            return Estado.CAMBIANDO;
        } else if ((cuerpo.getLinearVelocity().y > 0) ||
                (cuerpo.getLinearVelocity().y < 0 && estadoAnterior == Estado.JUMPING)) {
            return Estado.JUMPING;
        } else if (cuerpo.getLinearVelocity().y < 0) {
            return Estado.FALLING;
        } else if (cuerpo.getLinearVelocity().x != 0) {
            return Estado.RUNNING;
        } else {
            return Estado.STANDING;
        }
    }

    /**
     * Define la forma e interacciones de Box2d del Cuadrado
     */
    private void definirCuadrado() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(32 * Constantes.ESCALAR_PPM, 32 * Constantes.ESCALAR_PPM); // Temporal
        bdef.type = BodyDef.BodyType.DynamicBody;

        //// Creamos un sitio
        cuerpo = mundo.createBody(bdef);

        // Definimos la fixtura del personaje
        FixtureDef fdef = new FixtureDef();
        // Establecemos las forma de colisión como un circulo y lo definimos
        CircleShape forma = new CircleShape();
        forma.setRadius(7 * Constantes.ESCALAR_PPM);
        fdef.shape = forma;


        /*
         Asigna:
            - Categorybits = bit de la categoría a la que pertenece
            - maskBits = categorías con las que colisiona/interactua
         */
        fdef.filter.categoryBits = Constantes.CUADRADO_BIT;
        fdef.filter.maskBits = Constantes.SUELO_BIT |
                Constantes.COIN_BIT |
                Constantes.BRICK_BIT |
                Constantes.PINCHOS_BIT |
                Constantes.ENEMIGO_BIT |
                Constantes.CABEZA_ENEMIGO_BIT |
                Constantes.OBJETO_BIT |
                Constantes.CONTROL_BIT;


        cuerpo.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2( Constantes.escalarAppm(-2), Constantes.escalarAppm(7)),
                new Vector2(Constantes.escalarAppm(2) , Constantes.escalarAppm(7) ));


        fdef.filter.categoryBits = Constantes.CABEZA_CUADRADO_BIT;

        fdef.shape = head;
        fdef.isSensor = true;
        cuerpo.createFixture(fdef).setUserData(this);

    }

    public void grow() {
        if (!tieneCasco) {
            runGrowAnimation = true;
            tieneCasco = true;
            SquareGame.manager.get(Constantes.R_MEJORA).play();
        } else {
            Hud.actualizarVidas(vidas);
            vidas++;
        }
    }

    public void decrecer() {
        runGrowAnimation = true;
        tieneCasco = false;
        SquareGame.manager.get(Constantes.R_REDUCCION).play();
    }

    public void die() {
        Gdx.app.log("Hola",vidas+", "+muerto);
        if (!muerto && (vidas == 0 || pantallaDeJuego.getHud().isTimeUp())) {

            SquareGame.manager.get(Constantes.R_MUSICA).stop();
            SquareGame.manager.get(Constantes.R_MUERTE).play();
            muerto = true;
            Filter filter = new Filter();
            filter.maskBits = Constantes.NOTHING_BIT;

            for (Fixture fixture : cuerpo.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            cuerpo.applyLinearImpulse(new Vector2(0, 4f), cuerpo.getWorldCenter(), true);
        } else {
            Hud.actualizarVidas(vidas);
          vidas --;
          setPosition(ultimaX, ultimaY);
        }
    }

    public void hit() {
        /*if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.STANDING_SHELL)
            ((Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? Turtle.KICK_RIGHT : Turtle.KICK_LEFT);
        else {*/
        if (tieneCasco) {
            decrecer();
        } else {
            die();
        }
        devolverSalto();


//        }
    }

}
