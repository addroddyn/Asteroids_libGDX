package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends SpaceObject{
    private Sprite sprite;
    private float speed;
    private float direction;

    public Bullet(TextureAtlas atlas, float radians, float x, float y, float size, float speed) {
        super();
        sprite = new Sprite(atlas.findRegion("FX/Bullet-c/bullet-c2"));
        direction = radians;
        this.speed = speed;
        initialize(sprite, x, y,size,speed );
    }

    @Override
    protected float getRadians() {
        return direction;
    }

    @Override
    protected Vector2 getVelocity() {
        return new Vector2(MathUtils.cos(direction), MathUtils.sin(direction)).scl(speed);
    }
}
