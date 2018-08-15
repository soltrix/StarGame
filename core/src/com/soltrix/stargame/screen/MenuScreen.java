package com.soltrix.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.base.Base2DScreen;

/*
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {
    float SPEED = 1f;

    SpriteBatch batch;
    Texture img;

    Vector2 pos;
    Vector2 v;
    Vector2 touchPos;
    Vector2 buf;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        // изначальная позиция
        pos = new Vector2(0,0);
        // движение
        //y = new Vector2(0f,1f);
        //x = new Vector2(1f,0f);
        v = new Vector2(0f,0f);
        touchPos = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buf.set(touchPos);
        if (touchPos.cpy().sub(pos).len() > SPEED) {
            pos.add(v);
        } else {
            pos.set(touchPos);
            v.setZero();
        }
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        pos.add(v);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touchPos.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touchPos.x = " + touchPos.x + " touchPos.y = " + touchPos.y);
        v.set(touchPos.cpy().sub(pos).setLength(SPEED));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        return true;

    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }
}
