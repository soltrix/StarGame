package com.soltrix.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soltrix.stargame.base.Base2DScreen;
import com.soltrix.stargame.math.Rect;
import com.soltrix.stargame.screen.gamescreen.MainShip;
import com.soltrix.stargame.screen.pool.BulletPool;
import com.soltrix.stargame.screen.sprites.Background;
import com.soltrix.stargame.screen.sprites.Star;

public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 50;

    private Background background;
    private Texture bgTexture;
    private TextureAtlas atlas;
    private Star star[];
    private MainShip mainShip;

    private BulletPool bulletPool = new BulletPool();

    private Music music;
    private Sound bulletSound;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bgTexture));
        star = new Star[STAR_COUNT];
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        for (int i = 0; i < star.length ; i++) {
            star[i] = new Star(atlas);
        }
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        mainShip = new MainShip(atlas, bulletPool, bulletSound);
        this.music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length ; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length ; i++) {
            star[i].update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length ; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bgTexture.dispose();
        atlas.dispose();
        bulletPool.dispose();
        bulletSound.dispose();
        music.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
