package com.soltrix.stargame.screen.gamescreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.screen.sprites.Star;

public class TrackingStars extends Star {

    private Vector2 trackingV;

    private  Vector2 sumV = new Vector2();

    public TrackingStars(TextureAtlas atlas, Vector2 trackingV) {
        super(atlas);
        this.trackingV = trackingV;
    }

    @Override
    public void update(float delta) {
        sumV.setZero().mulAdd(trackingV, 0.2f).rotate(180).add(v);
        pos.mulAdd(sumV, delta);
        checkAndHandleBounds();
    }
}
