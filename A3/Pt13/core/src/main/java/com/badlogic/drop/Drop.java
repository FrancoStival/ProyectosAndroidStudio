package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drop extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont customFont;
    public FitViewport viewport;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        if (Gdx.files.internal("font.ttf").exists()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 24;
            customFont = generator.generateFont(parameter);
            generator.dispose();
        } else {
            customFont = new BitmapFont();
        }

        viewport = new FitViewport(8, 5);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new SplashScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        if (customFont != null) customFont.dispose();
    }

}
