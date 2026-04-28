package com.badlogic.drop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Gestiona les gotes que van apareixent, caient i desapareixent. És un grup d'actors que
 * contindrà les gotes com a actors
 */
public class DropletHandler extends Group {
    private long lastDropletTime; // Temps en què s'h generat la darrera gota
    private final long DROPLET_TIME_INTERVAL = 1000000000; // Interval que transcorre entre gotes
    // La imatge de la gota
    private final Texture texture;
    // El so que fa quan es recull una gota amb la galleda
    private final Sound dropSound;

    /**
     * El constructor genera la primera gota i guarda l'instant de temps
     */
    public DropletHandler(AssetManager assetManager) {
        texture = assetManager.get(AssetDescriptors.dropletTexture);
        dropSound = assetManager.get(AssetDescriptors.dropSound);
        lastDropletTime = TimeUtils.nanoTime();
        spawnDroplet();

    }

    /**
     * Crea una gota si és necessari
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        spawnDroplet();
    }

    /**
     * Comprova el tems transcorregut des de la darrera gota generada i si supera l'interval estblert
     * se'n genera una altra
     */
    private void spawnDroplet() {
        if ( TimeUtils.nanoTime() - lastDropletTime > DROPLET_TIME_INTERVAL ){
            lastDropletTime = TimeUtils.nanoTime();
            Droplet droplet = new Droplet(texture);
            // Afegim a la gota l'acció de desplaçar-se fins la part inferior de la pantalla
            // en dos segons
            droplet.addAction(Actions.moveTo(droplet.getX(), 0,2));
            //Afegim la gota  la llista d'actors del grup
            addActor(droplet);
        }
    }

    /**
     * Comprova si alguna de les gotes ha col·lidit amb el personatge
     * @param bucket el personatge (cubell)
     * @return el nombre de col·lisions detectades
     */
    public int checkCollisions(Bucket bucket) {
        int collisions = 0;

        // Use the iterator's remove method to avoid ConcurrentModificationException
        Iterator<Actor> it = getChildren().iterator();

        while (it.hasNext()) {
            Droplet droplet = (Droplet) it.next();
            if (droplet.inBucket(bucket)) {
                it.remove(); // Safer removal
                collisions++;
                dropSound.play();
            }
        }
        return collisions;
    }

}
