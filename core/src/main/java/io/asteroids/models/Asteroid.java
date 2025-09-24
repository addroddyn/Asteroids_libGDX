package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Asteroid extends SpaceObject {
    private enum AsteroidSize {
        SMALL,
        MEDIUM,
        BIG
    }

    private AsteroidSize size;
    private final float SMALL_SPRITE_SIZE = 0.5f;
    private final float MID_SPRITE_SIZE = 1f;
    private final float BIG_SPRITE_SIZE = 1.5f;
    private final float ASTEROID_SPEED = 2f;
    private Random rnd;

    private float initialX;
    private float initialY;
    private float direction;


    public Asteroid(TextureAtlas atlas,float worldWidth, float worldHeight) {
        super();
        rnd = new Random();
        setInitialLocation(worldWidth, worldHeight);
        direction = MathUtils.degreesToRadians * (rnd.nextFloat(360f));

        this.size = rnd.nextBoolean() ? AsteroidSize.MEDIUM : AsteroidSize.BIG;
        if (size == AsteroidSize.BIG) {
            initialize(new Sprite(atlas.findRegion(getRandomBigSprite(rnd.nextInt(3)))), initialX, initialY, BIG_SPRITE_SIZE, ASTEROID_SPEED);
        } else {
            initialize(new Sprite(atlas.findRegion(getRandomMidSprite(rnd.nextInt(3)))), initialX, initialY, MID_SPRITE_SIZE, ASTEROID_SPEED);
        }
    }

    public Asteroid(TextureAtlas atlas, AsteroidSize size) {
        rnd = new Random();
        this.size = size;
        if (size == AsteroidSize.BIG) {
            initialize(new Sprite(atlas.findRegion(getRandomBigSprite(rnd.nextInt(3)))), initialX, initialY, BIG_SPRITE_SIZE, ASTEROID_SPEED);
        } else if (size == AsteroidSize.MEDIUM) {
            initialize(new Sprite(atlas.findRegion(getRandomMidSprite(rnd.nextInt(3)))), initialX, initialY, MID_SPRITE_SIZE, ASTEROID_SPEED);
        } else {
            initialize(new Sprite(atlas.findRegion(getRandomSmallSprite(rnd.nextInt(3)))), initialX, initialY, SMALL_SPRITE_SIZE, ASTEROID_SPEED);
        }
    }

    private String getRandomBigSprite(Integer index) {
        switch (index) {
            case 0:
                return "Asteroids/big-a";
            case 1:
                return "Asteroids/big-b";
            default:
                return "Asteroids/big-c";
        }
    }

    private String getRandomMidSprite(Integer index) {
        switch (index) {
            case 0:
                return "Asteroids/med-a";
            case 1:
                return "Asteroids/med-b";
            default:
                return "Asteroids/med-c";
        }
    }

    private String getRandomSmallSprite(Integer index) {
        switch (index) {
            case 0:
                return "Asteroids/small-a";
            case 1:
                return "Asteroids/small-b";
            default:
                return "Asteroids/small-c";
        }
    }

    public void setInitialLocation(float worldWidth, float worldHeight) {
        switch (rnd.nextInt(4)) {
            case 0:
                initialX = 1f;
                initialY = rnd.nextFloat(worldHeight);
                break;
            case 1:
                initialX = rnd.nextFloat(worldWidth);
                initialY = worldHeight - 1f;
                break;
            case 2:
                initialX = worldWidth - 1f;
                initialY = rnd.nextFloat(worldHeight);
                break;
            default:
                initialX = rnd.nextFloat(worldWidth);
                initialY = 1f;
                break;
        }
    }

    @Override
    protected float getRadians() {
        return direction;
    }

}
