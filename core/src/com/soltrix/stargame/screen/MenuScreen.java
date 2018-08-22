package com.soltrix.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.base.Base2DScreen;
import com.soltrix.stargame.base.Sprite;
import com.soltrix.stargame.math.Rect;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {

    Texture img;
    Sprite logo;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        logo = new Sprite(new TextureRegion(img));
        logo.setSize(23f, 23f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
