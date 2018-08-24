package com.soltrix.stargame.screen.menuscreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.soltrix.stargame.base.ActionListener;
import com.soltrix.stargame.base.ScaledTouchUpButton;
import com.soltrix.stargame.math.Rect;

public class ButtonNewGame extends ScaledTouchUpButton {
    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
        super(atlas.findRegion("btPlay"), actionListener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.03f);
        setLeft(worldBounds.getLeft() + 0.03f);
    }
}
