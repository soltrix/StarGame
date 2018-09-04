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
import com.soltrix.stargame.base.ActionListener;
import com.soltrix.stargame.base.Base2DScreen;
import com.soltrix.stargame.math.Rect;
import com.soltrix.stargame.screen.gamescreen.Bullet;
import com.soltrix.stargame.screen.gamescreen.ButtonNewGame;
import com.soltrix.stargame.screen.gamescreen.Enemy;
import com.soltrix.stargame.screen.gamescreen.MainShip;
import com.soltrix.stargame.screen.gamescreen.MessageGameOver;
import com.soltrix.stargame.screen.pool.BulletPool;
import com.soltrix.stargame.screen.pool.EnemyPool;
import com.soltrix.stargame.screen.pool.ExplosionPool;
import com.soltrix.stargame.screen.sprites.Background;
import com.soltrix.stargame.screen.sprites.Star;
import com.soltrix.stargame.utils.EnemyEmitter;

import java.util.List;

public class GameScreen extends Base2DScreen implements ActionListener {

    private enum State {PLAYING, GAME_OVER}

    private static final int STAR_COUNT = 50;

    private Background background;
    private Texture bgTexture;
    private TextureAtlas atlas;
    private Star star[];
    private MainShip mainShip;

    private BulletPool bulletPool = new BulletPool();

    private Music music;
    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    private State state;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    int frags;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        bgTexture = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bgTexture));
        star = new Star[STAR_COUNT];
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        for (int i = 0; i < star.length ; i++) {
            star[i] = new Star(atlas);
        }
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, bulletSound, explosionPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, mainShip, bulletSound);
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        startNewGame();
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
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    public void update(float delta) {
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
        for (int i = 0; i < star.length ; i++) {
            star[i].update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemyEmitter.generateEnemies(delta);
                break;
            case GAME_OVER:
                break;
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList){
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                        break;
                    }
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
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
        explosionPool.dispose();
        enemyPool.dispose();
        bulletSound.dispose();
        music.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else {
            buttonNewGame.touchDown(touch,pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else {
            buttonNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    private void startNewGame() {
        state = State.PLAYING;

        frags = 0;

        mainShip.startNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }


    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) {
            startNewGame();
        }
    }
}
