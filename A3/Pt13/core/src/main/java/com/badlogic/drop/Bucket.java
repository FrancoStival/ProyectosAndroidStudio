package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bucket extends Image {

    public Bucket(Texture texture) {
        super(texture);
        setPosition(800 / 2 - 100 / 2, 0);
        setSize(100, 100);
    }
}
