package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SplashScreen implements Screen {
    private final Drop game;
    private Stage stage;
    private Texture splashTexture;

    public SplashScreen(Drop game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 480));

        // Intentar carregar splash.png, si no existeix usar background.png
        if (Gdx.files.internal("splash.png").exists()) {
            splashTexture = new Texture(Gdx.files.internal("splash.png"));
        } else {
            splashTexture = new Texture(Gdx.files.internal("background.png"));
        }

        Image splashImage = new Image(splashTexture);
        splashImage.setSize(800, 480);

        // Efecte d'aparició i canvi a la pantalla principal
        splashImage.getColor().a = 0;
        splashImage.addAction(Actions.sequence(
            Actions.fadeIn(2f),
            Actions.delay(1f),
            Actions.fadeOut(1f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MainMenuScreen(game));
                }
            })
        ));

        stage.addActor(splashImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (splashTexture != null) splashTexture.dispose();
    }
}
