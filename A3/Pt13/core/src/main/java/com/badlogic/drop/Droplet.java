package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Droplet extends Image {
    public enum Type { FRUIT, BOMB }
    private final Type type;
    private boolean captured = false;

    public Droplet(Texture texture, Type type, final GameScreen gameScreen, final com.badlogic.gdx.audio.Sound catchSound, final com.badlogic.gdx.audio.Sound bombSound) {
        super(texture);
        this.type = type;
        setPosition(MathUtils.random(0, 800 - 64), 480);
        setSize(64, 64);

        if (type == Type.BOMB) {
            setColor(1, 0, 0, 1);
        } else {
            setColor(0, 1, 0, 1);
        }

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (captured) return;
                captured = true;
                if (Droplet.this.type == Type.FRUIT) {
                    if (catchSound != null) catchSound.play();
                    gameScreen.addScore(10);
                } else {
                    if (bombSound != null) bombSound.play();
                    gameScreen.removeLife();
                }
                remove();
            }
        });
    }

    public Type getType() {
        return type;
    }
}
