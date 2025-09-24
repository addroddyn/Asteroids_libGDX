package io.asteroids.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.asteroids.Asteroids;
import io.asteroids.models.Asteroid;
import io.asteroids.models.Ship;
import io.asteroids.models.SpaceObject;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final float worldWidth;
    private final float worldHeight;
    private float asteroidTimer;
    private final float ASTEROID_SPAWNRATE = 5f;
    private ArrayList<Asteroid> asteroids;
    TextureAtlas atlas;
    Texture background;
    Ship ship;


    final Asteroids game;

    public GameScreen(Asteroids game) {
        this.game = game;
        worldWidth = this.game.viewport.getWorldWidth();
        worldHeight = this.game.viewport.getWorldHeight();
        asteroidTimer = 0f;
        asteroids = new ArrayList<>();
        SetTextures();
    }

    private void SetTextures() {
        background = new Texture("background.png");
        atlas = new TextureAtlas("asteroids_atlas/pack.atlas");
        ship = new Ship(new Sprite(atlas.findRegion("Ships/ship-a/ship-a1")), worldWidth / 2.0f, worldHeight / 2.0f, 1, 4f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();

    }

    private void input() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.rotate(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.rotate(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ship.forward(deltaTime);
        }
        transportObject(ship);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //TODO: fire
        }
    }

    private void transportObject(SpaceObject object) {
        Sprite sprite = object.getSprite();
        if (sprite.getX() > worldWidth + sprite.getWidth())
            sprite.setX(-sprite.getWidth());
        if (sprite.getX() < -sprite.getWidth())  {
            sprite.setX(worldWidth + sprite.getWidth());
        }
        if (sprite.getY() > worldHeight + sprite.getHeight())
            sprite.setY(-sprite.getHeight());
        if (sprite.getY() < -sprite.getHeight()) {
            Gdx.app.log("x", "Leaving south, current position is: " + sprite.getY());
            sprite.setY(worldHeight + sprite.getHeight());
            Gdx.app.log("x", "Arriving north, current position is: " + sprite.getY());
        }
    }

    private void logic() {
        createAsteroids();
        //TODO: collision with ship
        //TODO: collision with bullet

    }

    private void createAsteroids(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        asteroidTimer += deltaTime;
        if (asteroidTimer > ASTEROID_SPAWNRATE){
            Asteroid asteroid = new Asteroid(atlas,worldWidth, worldHeight);
            asteroids.add(asteroid);
            asteroidTimer = 0f;
        }
        for(Asteroid a : asteroids){
            a.forward(deltaTime);
        }
    }

    private void draw() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, worldWidth, worldHeight);
        ship.draw(game.batch);
        for (Asteroid a : asteroids)
        {
            transportObject(a);
            a.draw(game.batch);
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
