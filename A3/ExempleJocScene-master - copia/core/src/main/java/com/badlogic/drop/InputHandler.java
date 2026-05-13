package com.badlogic.drop;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

/**
 * Ës la classe que processa l'entrada
 */
public class InputHandler implements InputProcessor {
    private GameScreen gameScreen;


    public InputHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Aquest mètode es crida en tocar la pantalla
     * @param screenX posició horitzontal, en píxels
     * @param screenY posició vertical, en píxels
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        moveBucket(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        moveBucket(screenX, screenY);
        return true;
    }

    private void moveBucket(int screenX, int screenY) {
        float worldWidth = gameScreen.stage.getWidth();
        Vector3 vector = gameScreen.stage.getCamera().unproject(new Vector3(screenX, screenY, 0));
        float x = vector.x - gameScreen.bucket.getWidth() / 2;
        if (x < 0) x = 0;
        if (x > worldWidth - gameScreen.bucket.getWidth()) x = worldWidth - gameScreen.bucket.getWidth();
        gameScreen.bucket.setX(x);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
