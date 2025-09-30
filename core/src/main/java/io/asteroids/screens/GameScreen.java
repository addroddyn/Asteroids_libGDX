package io.asteroids.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.asteroids.Asteroids;
import io.asteroids.models.*;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final float worldWidth;
    private final float worldHeight;
    private float asteroidTimer;
    private final float ASTEROID_SPAWNRATE = 5f;
    private final float FIRE_RATE = 0.2f;
    private float bulletTimer;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Bullet> bullets;
    TextureAtlas atlas;
    Texture background;
    Ship ship;
    ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
    ArrayList<Bullet> bulletsToRemove = new ArrayList<>();


    final Asteroids game;

    public GameScreen(Asteroids game) {
        this.game = game;
        worldWidth = this.game.viewport.getWorldWidth();
        worldHeight = this.game.viewport.getWorldHeight();
        asteroidTimer = 0f;
        bulletTimer = FIRE_RATE + 1;
        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        SetTextures();
    }

    private void SetTextures() {
        background = new Texture("background.png");
        atlas = new TextureAtlas("asteroids_atlas/pack.atlas");
        ship = new Ship(new Sprite(atlas.findRegion("Ships/ship-a/ship-a1")), worldWidth / 2.0f, worldHeight / 2.0f, 100, 400f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        input(deltaTime);
        logic(deltaTime);
        draw();

    }

    private void input(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.rotate(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.rotate(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ship.thrust(deltaTime);
        }
        transportObject(ship);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (bulletTimer > FIRE_RATE) {
                bullets.add(ship.fire(atlas, 50f, 600f));
                bulletTimer = 0f;
            }
            bulletTimer += deltaTime;
        }
    }

    private void transportObject(SpaceObject object) {
        Sprite sprite = object.getSprite();
        if (sprite.getX() > worldWidth + sprite.getWidth())
            sprite.setX(-sprite.getWidth());
        if (sprite.getX() < -sprite.getWidth()) {
            sprite.setX(worldWidth + sprite.getWidth());
        }
        if (sprite.getY() > worldHeight + sprite.getHeight())
            sprite.setY(-sprite.getHeight());
        if (sprite.getY() < -sprite.getHeight()) {
            sprite.setY(worldHeight + sprite.getHeight());
        }
    }

    private void logic(float deltaTime) {
        createAsteroids(deltaTime);
        moveStuff(deltaTime);
        checkForCollision();
        removeAsteroids();
        removeBullets();
    }


    private void removeAsteroids() {
        for (Asteroid a : asteroidsToRemove) {
            asteroids.remove(a);
            if (a.getAsteroidSize().equals(AsteroidSize.BIG)) {
                asteroids.add(new Asteroid(atlas, AsteroidSize.MEDIUM, a.getSprite().getX(), a.getSprite().getY()));
                asteroids.add(new Asteroid(atlas, AsteroidSize.MEDIUM, a.getSprite().getX(), a.getSprite().getY()));
                asteroids.add(new Asteroid(atlas, AsteroidSize.MEDIUM, a.getSprite().getX(), a.getSprite().getY()));
            } else if (a.getAsteroidSize().equals(AsteroidSize.MEDIUM)) {
                asteroids.add(new Asteroid(atlas, AsteroidSize.SMALL, a.getSprite().getX(), a.getSprite().getY()));
                asteroids.add(new Asteroid(atlas, AsteroidSize.SMALL, a.getSprite().getX(), a.getSprite().getY()));
            }
        }
        asteroidsToRemove = new ArrayList<>();
    }

    private void removeBullets() {
        for (Bullet b : bulletsToRemove) {
            bullets.remove(b);
        }
        bulletsToRemove = new ArrayList<>();
    }

    private void checkForCollision() {

        for (Asteroid a : asteroids) {
            if (a.getSprite().getBoundingRectangle().overlaps(ship.getSprite().getBoundingRectangle())) {
                ship.getSprite().setPosition(1, 1);
            } else {
                for (Bullet b : bullets) {
                    if (a.getSprite().getBoundingRectangle().overlaps(b.getSprite().getBoundingRectangle())) {
                        asteroidsToRemove.add(a);
                        bulletsToRemove.add(b);
                        break;
                    }
                }
            }
        }

    }

    private void moveStuff(float deltaTime) {
        ship.move(deltaTime);
        for (Asteroid a : asteroids) {
            a.move(deltaTime);
            transportObject(a);
        }
        for (Bullet b : bullets) {
            b.move(deltaTime);
            float posX = b.getSprite().getX();
            float posY = b.getSprite().getY();
            if ((posX > worldWidth && posY > worldHeight) ||
                (posX > worldWidth && posY < 0) ||
                (posX < 0 && posY > worldHeight) ||
                (posX < 0 && posY < 0)
            ) {
                bulletsToRemove.add(b);
            }
        }
    }

    private void createAsteroids(float deltaTime) {
        if (asteroidTimer > ASTEROID_SPAWNRATE) {
            Asteroid asteroid = new Asteroid(atlas, worldWidth, worldHeight);
            asteroids.add(asteroid);
            asteroidTimer = 0f;
        }
        asteroidTimer += deltaTime;
    }

    private void draw() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, worldWidth, worldHeight);
        ship.draw(game.batch);
        for (Asteroid a : asteroids) {
            a.draw(game.batch);
        }
        for (Bullet b : bullets) {
            b.draw(game.batch);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
