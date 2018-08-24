package com.soltrix.stargame.screen.gamescreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.base.Sprite;
import com.soltrix.stargame.math.Rect;

public class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private Vector2 v0 = new Vector2(0.5f, 0.0f);
    private Vector2 v = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        this.worldBounds = worldBounds;
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }
}
