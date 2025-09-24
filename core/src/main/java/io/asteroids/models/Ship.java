package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ship extends SpaceObject {

    private final float ROTATION_SPEED = 4f;

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
}
