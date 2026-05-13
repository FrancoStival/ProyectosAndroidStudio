package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {
    private final Drop game;
    private Stage stage;
    private Texture backgroundTexture;

    public MainMenuScreen(final Drop game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 480));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        Image background = new Image(backgroundTexture);
        background.setSize(800, 480);
        stage.addActor(background);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.customFont, Color.WHITE);

        Label titleLabel = new Label("DODGE THE SLIMES!!!", labelStyle);
        titleLabel.setFontScale(1.8f);
        titleLabel.setAlignment(Align.center);

        Label instructionLabel = new Label("Avoid the enemies!\nYou have 3 lives.", labelStyle);
        instructionLabel.setFontScale(1.0f);
        instructionLabel.setAlignment(Align.center);

        Label startLabel = new Label("Tap anywhere to START", labelStyle);
        startLabel.setFontScale(1.2f);
        startLabel.setColor(Color.YELLOW);
        startLabel.setAlignment(Align.center);

        table.add(titleLabel).padBottom(20).row();
        table.add(instructionLabel).padBottom(40).row();
        table.add(startLabel);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}
