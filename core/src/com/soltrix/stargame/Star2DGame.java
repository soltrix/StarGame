package com.soltrix.stargame;

import com.badlogic.gdx.Game;
import com.soltrix.stargame.screen.MenuScreen;

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
