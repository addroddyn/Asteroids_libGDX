package io.asteroids.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class SpaceObject {

    private float speed;
    private Sprite sprite;

    public SpaceObject() {}

    public void initialize(Sprite sprite, float posX, float posY, float size, float initialSpeed)
    {
        this.sprite = sprite;
        this.sprite.setSize(size, size);
        this.sprite.setX(posX);
        this.sprite.setY(posY);
        this.sprite.setOrigin(size / 2f, size / 2f);
        speed = initialSpeed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void forward(float deltaTime) {
        Vector2 position = new Vector2(sprite.getX(), sprite.getY());
        Gdx.app.log(this.getClass().toString(), "initial position: " + position.x + ", " + position.y);
        float dx = MathUtils.cos(getRadians()) * speed * deltaTime;
        float dy = MathUtils.sin(getRadians()) * speed * deltaTime;
        position.add(dx, dy);
        sprite.setPosition(position.x, position.y);
        Gdx.app.log(this.getClass().toString(), "radians: " + getRadians());
        Gdx.app.log(this.getClass().toString(), "final position: " + sprite.getX() + ", " + sprite.getY());
    }

    protected abstract float getRadians();

}
