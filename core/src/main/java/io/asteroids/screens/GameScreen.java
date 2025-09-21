package io.asteroids.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import io.asteroids.Asteroids;

public class GameScreen implements Screen {
    private float worldWidth;
    private float worldHeight;
    Texture background;
    Sprite ship;
    Sprite asteroidBig_1;
    Sprite asteroidBig_2;
    Sprite asteroidBig_3;
    Sprite asteroidMid_2;
    Sprite asteroidMid_1;
    Sprite asteroidMid_3;
    Sprite asteroidSmall_1;
    Sprite asteroidSmall_2;
    Sprite asteroidSmall_3;
    Sprite bullet;


    final Asteroids game;

    public GameScreen(Asteroids game) {
        this.game = game;
        worldWidth = this.game.viewport.getWorldWidth();
        worldHeight = this.game.viewport.getWorldHeight();
        background = new Texture("background.png");
        TextureAtlas atlas = new TextureAtlas("asteroids_atlas/pack.atlas");
        ship = new Sprite(atlas.findRegion("Ships/ship-a/ship-a1"));
        ship.setSize(1, 1);
        ship.setPosition(worldWidth / 2.0f, worldHeight / 2.0f);
        ship.setOrigin(0.5f, 0.5f);
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
        float speed = 4f;
        float deltaTime = Gdx.graphics.getDeltaTime();
        float rotateSpeed = 4f;

        Vector2 position = new Vector2(ship.getX(), ship.getY());
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.rotate(rotateSpeed * -1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.rotate(rotateSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            float radians = MathUtils.degreesToRadians * (ship.getRotation() + 90);
            float dx = MathUtils.cos(radians) * speed * deltaTime;
            float dy = MathUtils.sin(radians) * speed * deltaTime;
            position.add(dx, dy);
            ship.setPosition(position.x, position.y);
        }
        transportObject(ship);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //TODO: fire
        }
    }

    private void transportObject(Sprite object) {
        float edgeOffset = 0.5f;
        if (object.getX() >= worldWidth - edgeOffset)
            object.setX(-edgeOffset);
        if (object.getX() <= -(edgeOffset * 1.1)) {
            object.setX(worldWidth - edgeOffset);
        }
        if (object.getY() >= worldHeight)
            object.setY(-edgeOffset);
        if (object.getY() <= -(edgeOffset * 1.1)) {
            object.setY(worldHeight - edgeOffset);
        }
    }

    private void logic() {
        //TODO: spawn in asteroids
        //TODO: transport asteroids from one edge to the other
        //TODO: collision with ship
        //TODO: collision with bullet

    }

    private void draw() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, worldWidth, worldHeight);
        //draw text. Remember that x and y are in meters
        ship.draw(game.batch);
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
