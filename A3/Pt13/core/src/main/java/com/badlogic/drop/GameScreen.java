package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    final Drop game;
    final Stage stage;
    final AssetManager assetManager = new AssetManager();

    Music rainMusic;
    DropletHandler droplets;

    Label scoreLabel;
    Label livesLabel;

    int score = 0;
    int lives = 3;
    int lastLifeRewardScore = 0;
    boolean assetsLoaded = false;

    public GameScreen(final Drop game) {
        this.game = game;
        stage = new Stage(new FitViewport(800, 480));
    }

    @Override
    public void show() {
        loadAssets();
    }

    private void setupUI() {
        Image backgroundImage = new Image(assetManager.get(AssetDescriptors.background));
        backgroundImage.setSize(800, 480);
        stage.addActor(backgroundImage);

        rainMusic = assetManager.get(AssetDescriptors.rainMusic);
        rainMusic.setLooping(true);
        rainMusic.play();

        droplets = new DropletHandler(this, assetManager);
        stage.addActor(droplets);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.customFont, Color.YELLOW);
        scoreLabel = new Label("Punts: 0", labelStyle);
        scoreLabel.setPosition(20, 440);

        livesLabel = new Label("Vides: 3", labelStyle);
        livesLabel.setPosition(20, 400);

        stage.addActor(scoreLabel);
        stage.addActor(livesLabel);

        Gdx.input.setInputProcessor(stage);
    }

    public void addScore(int points) {
        score += points;
        scoreLabel.setText("Punts: " + score);

        if (score >= lastLifeRewardScore + 100) {
            lastLifeRewardScore = (score / 100) * 100;
            if (lives < 3) {
                lives++;
                livesLabel.setText("Vides: " + lives);
            }
        }
    }

    public void removeLife() {
        lives--;
        livesLabel.setText("Vides: " + lives);
        if (lives <= 0) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        if (!assetsLoaded) {
            if (assetManager.update()) {
                assetsLoaded = true;
                setupUI();
            } else {
                return;
            }
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        stage.act(delta);
        stage.draw();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.dropletTexture);
        assetManager.load(AssetDescriptors.background);
        assetManager.load(AssetDescriptors.dropSound);
        assetManager.load(AssetDescriptors.rainMusic);
        assetManager.load(AssetDescriptors.bonkSound);
        assetManager.load(AssetDescriptors.explosionSound);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        if (rainMusic != null) rainMusic.stop();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        assetManager.dispose();
        stage.dispose();
    }
}
