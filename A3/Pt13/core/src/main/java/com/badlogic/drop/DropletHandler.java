package com.badlogic.drop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.TimeUtils;

public class DropletHandler extends Group {
    private long lastDropletTime;
    private long currentInterval = 1000000000L;
    private float fallDuration = 3.0f;
    private final Texture texture;
    private final Sound dropSound;
    private final Sound explosionSound;
    private final Sound bonkSound;
    private final GameScreen gameScreen;

    public DropletHandler(GameScreen gameScreen, AssetManager assetManager) {
        this.gameScreen = gameScreen;
        this.texture = assetManager.get(AssetDescriptors.dropletTexture);
        this.dropSound = assetManager.get(AssetDescriptors.dropSound);
        this.explosionSound = assetManager.get(AssetDescriptors.explosionSound);
        this.bonkSound = assetManager.get(AssetDescriptors.bonkSound);
        this.lastDropletTime = TimeUtils.nanoTime();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        spawnDroplet();

        if (fallDuration > 1.0f) {
            fallDuration -= 0.01f * delta;
        }
        if (currentInterval > 400000000L) {
            currentInterval -= 2000000L * delta;
        }

        SnapshotArray<Actor> children = getChildren();
        Actor[] items = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor actor = items[i];
            if (actor instanceof Droplet) {
                Droplet droplet = (Droplet) actor;
                if (droplet.getY() <= 0) {
                    if (droplet.getType() == Droplet.Type.FRUIT) {
                        if (bonkSound != null) bonkSound.play();
                        gameScreen.removeLife();
                    }
                    droplet.remove();
                }
            }
        }
        children.end();
    }

    private void spawnDroplet() {
        if (TimeUtils.nanoTime() - lastDropletTime > currentInterval) {
            lastDropletTime = TimeUtils.nanoTime();

            Droplet.Type type = MathUtils.random() < 0.2f ? Droplet.Type.BOMB : Droplet.Type.FRUIT;

            Droplet droplet = new Droplet(texture, type, gameScreen, dropSound, explosionSound);
            droplet.addAction(Actions.moveTo(droplet.getX(), -64, fallDuration));
            addActor(droplet);
        }
    }
}
