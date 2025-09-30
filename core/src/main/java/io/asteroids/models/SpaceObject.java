package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class SpaceObject {

    private float speed;
    private Sprite sprite;

    public SpaceObject() {}

    public void initialize(Sprite sprite, float posX, float posY, float size, float initialSpeed)
    {
        this.sprite = sprite;
        this.sprite.setSize(size, size);
        this.sprite.setOriginCenter();
        this.sprite.setX(posX);
        this.sprite.setY(posY);
        this.sprite.getBoundingRectangle().setSize(size);
        speed = initialSpeed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void move(float deltaTime) {
        float newX = sprite.getX() + getVelocity().x * deltaTime;
        float newY = sprite.getY() + getVelocity().y * deltaTime;
        sprite.setPosition(newX, newY);
    }

    protected abstract float getRadians();
    protected abstract Vector2 getVelocity();

    public void dispose(){
        sprite.getTexture().dispose();
    }
}
