package com.soltrix.stargame.screen.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.base.Sprite;
import com.soltrix.stargame.math.Rect;
import com.soltrix.stargame.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private  Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        // задаем рандомную скорость звезды
        v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f));
        // задаем размер звезды 1% от экрана
        setHeightProportion(0.01f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    protected void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        // задаем рандомную позицию звезды по оси X в пределах экрана
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        // задаем рандомную позицию звезды по оси Y в пределах экрана
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        // устанавливаем звезду
        pos.set(posX, posY);
    }
}
