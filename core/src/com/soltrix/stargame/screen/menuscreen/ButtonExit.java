package com.soltrix.stargame.screen.menuscreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.soltrix.stargame.base.ActionListener;
import com.soltrix.stargame.base.ScaledTouchUpButton;
import com.soltrix.stargame.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {
    public ButtonExit(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btExit"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setRight(worldBounds.getRight() - 0.03f);
    }
}
