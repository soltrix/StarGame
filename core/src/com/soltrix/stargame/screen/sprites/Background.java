package com.soltrix.stargame.screen.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soltrix.stargame.base.Sprite;
import com.soltrix.stargame.math.Rect;

public class Background extends Sprite {
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
