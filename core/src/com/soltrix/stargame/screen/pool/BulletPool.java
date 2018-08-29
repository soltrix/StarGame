package com.soltrix.stargame.screen.pool;

/*
 * Пул пуль
 */

import com.soltrix.stargame.base.SpritesPool;
import com.soltrix.stargame.screen.gamescreen.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void debugLog() {
        System.out.println("active/free:" + activeObjects.size() + "/" + freeObjects.size());
    }
}
