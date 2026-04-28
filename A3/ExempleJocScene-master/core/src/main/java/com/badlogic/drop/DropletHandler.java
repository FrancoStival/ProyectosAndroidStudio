package com.badlogic.drop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.TimeUtils;

public class DropletHandler extends Group {
    private long lastDropletTime;
    private long currentInterval = 1000000000L;
    private float fallDuration = 2.0f;
    private final Texture texture;
    private final Sound bonkSound;
    private final Sound explosionSound;

    public DropletHandler(AssetManager assetManager) {
        this.texture = assetManager.get(AssetDescriptors.dropletTexture);
        this.bonkSound = assetManager.get(AssetDescriptors.bonkSound);
        this.explosionSound = assetManager.get(AssetDescriptors.explosionSound);
        this.lastDropletTime = TimeUtils.nanoTime();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        spawnDroplet();

        if (fallDuration > 0.6f) {
            fallDuration -= 0.02f * delta;
        }
        if (currentInterval > 300000000L) {
            currentInterval -= 5000000L * delta;
        }

        SnapshotArray<Actor> children = getChildren();
        Actor[] items = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor actor = items[i];
            if (actor instanceof Droplet) {
                Droplet droplet = (Droplet) actor;
                if (droplet.getY() <= 0.1f) {
                    if (bonkSound != null) bonkSound.play();
                    droplet.remove();
                }
            }
        }
        children.end();
    }

    private void spawnDroplet() {
        if (TimeUtils.nanoTime() - lastDropletTime > currentInterval) {
            lastDropletTime = TimeUtils.nanoTime();
            Droplet droplet = new Droplet(texture);
            droplet.addAction(Actions.moveTo(droplet.getX(), 0, fallDuration));
            addActor(droplet);
        }
    }

    public int checkCollisions(Bucket bucket) {
        int collisions = 0;
        SnapshotArray<Actor> children = getChildren();
        Actor[] items = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor actor = items[i];
            if (actor instanceof Droplet) {
                Droplet droplet = (Droplet) actor;
                if (droplet.inBucket(bucket)) {
                    if (explosionSound != null) explosionSound.play();
                    droplet.remove();
                    collisions++;
                }
            }
        }
        children.end();
        return collisions;
    }
}
