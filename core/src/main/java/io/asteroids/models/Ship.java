package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ship extends SpaceObject {

    private final float ROTATION_SPEED = 4f;
    private Vector2 velocity = new Vector2();
    private float thrustPower = 2f;

    public Ship(Sprite sprite, float posX, float posY, float size, float initialSpeed) {
        super();
        initialize(sprite, posX, posY, size, initialSpeed);
    }

    @Override
    protected float getRadians() {
        return MathUtils.degreesToRadians * (getSprite().getRotation() + 90);
    }

    public void rotate(boolean clockWise) {
        this.getSprite().rotate(ROTATION_SPEED * (clockWise ? -1 : 1));
    }

    public void thrust(float deltaTime){
        float dx = MathUtils.cos(getRadians()) * thrustPower * deltaTime;
        float dy = MathUtils.sin(getRadians()) * thrustPower * deltaTime;
        velocity.add(dx, dy);
    }

    @Override
    public void move(float deltatime){
        super.move(deltatime);
        velocity.scl(0.99f);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }
}
