package es.alfmarmes.squaregame.sprites.Enemigos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.alfmarmes.squaregame.SquareGame;
import es.alfmarmes.squaregame.escenas.Hud;
import es.alfmarmes.squaregame.screens.PantallaDeJuego;
import es.alfmarmes.squaregame.tools.Constantes;

public class Triangulo extends Enemigo {


    private float tiempoEstado;
    private Animation<TextureRegion> animacionAndar;
    private TextureRegion imagenMuerto;
    private Array<TextureRegion> frames;
    private static TextureRegion region;

    public Triangulo(PantallaDeJuego pantallaDeJuego, float x, float y) {
        super(pantallaDeJuego, x, y);

        if (region == null){
            region = pantallaDeJuego.getAtlas().findRegion("triangle");
        }
        tiempoEstado = 0;
        porDestruir = false;
        destruido = false;
        // Animación de andar
        frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(region, i * 16,
                    0, 16, 16));
        }
        animacionAndar = new Animation<>(0.4f, frames);
        frames.clear();

        imagenMuerto = new TextureRegion(region,4*16, 0, 16, 16);



        setBounds(getX(), getY(), Constantes.TILE, Constantes.TILE);
        TextureRegion initialState=new TextureRegion(region,
                32, 0, 16, 16);
        setRegion(initialState);


    }

    @Override
    public void update(float dt) {
        tiempoEstado += dt;

        if (porDestruir && !destruido) {
            mundo.destroyBody(cuerpo);
            destruido = true;
            setRegion(imagenMuerto);
            tiempoEstado = 0;
        } else if (!destruido) {
            cuerpo.setLinearVelocity(velocidad);
            setPosition(cuerpo.getPosition().x - (getWidth() / 2),
                    cuerpo.getPosition().y - (getHeight() / 2));

            setRegion(((TextureRegion) animacionAndar.getKeyFrame(tiempoEstado, true)));

        }
    }



    @Override
    protected void definirEnemigo() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());

        //bdef.position.set(1+32 / SquareGame.PPM, 32 / SquareGame.PPM); // Temporal
        bdef.type = BodyDef.BodyType.DynamicBody;

        cuerpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape forma = new CircleShape();
        forma.setRadius(Constantes.escalarAppm(7));

        fdef.filter.categoryBits = Constantes.ENEMIGO_BIT;
        fdef.filter.maskBits = Constantes.SUELO_BIT
                | Constantes.MONEDA_BIT
                | Constantes.BRICK_BIT
                | Constantes.PINCHOS_BIT
                | Constantes.CUADRADO_BIT
                | Constantes.ENEMIGO_BIT;

        fdef.shape = forma;
        cuerpo.createFixture(fdef).setUserData(this);

        // Create head here:
        // Creamos el polígono para la cabeza que aplastaremos
        PolygonShape cabeza = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-8, 10).scl(Constantes.ESCALAR_PPM);
        vertice[1] = new Vector2(8, 10).scl(Constantes.ESCALAR_PPM);
        vertice[2] = new Vector2(-3, 3).scl(Constantes.ESCALAR_PPM);
        vertice[3] = new Vector2(3, 3).scl(Constantes.ESCALAR_PPM);
        cabeza.set(vertice);
        // Definimos el fdef con la cabeza y lo añadimos al cuerpo
        fdef.shape = cabeza;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Constantes.CABEZA_ENEMIGO_BIT;
        fdef.filter.maskBits = Constantes.CUADRADO_BIT;
        cuerpo.createFixture(fdef).setUserData(this);
    }


    @Override
    public void draw(Batch batch) {
        /*Dibujar solo si no está muerto o tiempo menor que 1.
        La segunda condición es para poder dejar el sprite de muerto
        un tiempo.*/
        if (!super.destruido || tiempoEstado <1){
            super.draw(batch);
        }
    }

    @Override
    public void pisado() {
        porDestruir = true;
        destruido = false;
        SquareGame.manager.get(Constantes.R_BONK).play();
        Hud.addScore(200);
    }
}
