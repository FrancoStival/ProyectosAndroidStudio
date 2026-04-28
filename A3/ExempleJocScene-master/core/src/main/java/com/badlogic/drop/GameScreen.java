package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    Bucket bucket;
    DropletHandler raindrops;

    Label scoreLabel;
    Label livesLabel;

    int score;
    int lives = 3;
    float scoreTimer = 0;
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
        Texture fons = assetManager.get(AssetDescriptors.background);
        Image backgroundImage = new Image(fons);
        backgroundImage.setSize(800, 480);
        stage.addActor(backgroundImage);

        rainMusic = assetManager.get(AssetDescriptors.rainMusic);
        rainMusic.setLooping(true);
        rainMusic.play();

        raindrops = new DropletHandler(assetManager);
        bucket = new Bucket(assetManager.get(AssetDescriptors.bucketTexture));

        stage.addActor(raindrops);
        stage.addActor(bucket);

        // UI Labels
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.customFont, Color.YELLOW);
        scoreLabel = new Label("Score: 0", labelStyle);
        scoreLabel.setPosition(10, 450);

        livesLabel = new Label("Lives: 3", labelStyle);
        livesLabel.setPosition(10, 420);

        stage.addActor(scoreLabel);
        stage.addActor(livesLabel);

        Gdx.input.setInputProcessor(new InputHandler(this));
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

        // Score increases over time
        scoreTimer += delta;
        if (scoreTimer >= 1.0f) {
            score++;
            scoreLabel.setText("Score: " + score);
            scoreTimer = 0;
        }

        // Check for collisions
        int collisions = raindrops.checkCollisions(bucket);
        if (collisions > 0) {
            lives -= collisions;
            livesLabel.setText("Lives: " + lives);
            if (lives <= 0) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

        stage.act(delta);
        stage.draw();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.bucketTexture);
        assetManager.load(AssetDescriptors.dropletTexture);
        assetManager.load(AssetDescriptors.background);
        assetManager.load(AssetDescriptors.dropSound);
        assetManager.load(AssetDescriptors.rainMusic);
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
