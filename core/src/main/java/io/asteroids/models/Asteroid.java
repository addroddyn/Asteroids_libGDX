package io.asteroids.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Asteroid {




    private enum AsteroidSize {
        SMALL,
        MEDIUM,
        BIG
    }

    private AsteroidSize size;
    private final float SMALL_SPRITE_SIZE = 0.5f;
    private final float MID_SPRITE_SIZE = 1f;
    private final float BIG_SPRITE_SIZE = 1.5f;
    private float direction;
    private Random rnd;
    public Sprite sprite;

    public Asteroid(TextureAtlas atlas) {
        rnd = new Random();

        this.size = rnd.nextBoolean() ? AsteroidSize.MEDIUM : AsteroidSize.BIG;
        if (size == AsteroidSize.BIG) {
            sprite = new Sprite(atlas.findRegion(getRandomBigSprite(rnd.nextInt(3))));
            sprite.setSize(BIG_SPRITE_SIZE, BIG_SPRITE_SIZE);
        } else {
            sprite = new Sprite(atlas.findRegion(getRandomMidSprite(rnd.nextInt(3))));
            sprite.setSize(MID_SPRITE_SIZE, MID_SPRITE_SIZE);
        }
    }

    public Asteroid(TextureAtlas atlas, AsteroidSize size) {
        rnd = new Random();
        this.size = size;
        if (size == AsteroidSize.BIG) {
            sprite = new Sprite(atlas.findRegion(getRandomBigSprite(rnd.nextInt(3))));
            sprite.setSize(BIG_SPRITE_SIZE, BIG_SPRITE_SIZE);
        }
        else if (size == AsteroidSize.MEDIUM) {
            sprite = new Sprite(atlas.findRegion(getRandomMidSprite(rnd.nextInt(3))));
            sprite.setSize(MID_SPRITE_SIZE, MID_SPRITE_SIZE);
        }
        else {
            sprite = new Sprite(atlas.findRegion(getRandomSmallSprite(rnd.nextInt(3))));
            sprite.setSize(SMALL_SPRITE_SIZE, SMALL_SPRITE_SIZE);
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

    public void startMoving(float worldWidth, float worldHeight) {
        switch (rnd.nextInt(4)){
            case 0:
                sprite.setX(1f);
                sprite.setY(rnd.nextFloat(worldHeight));
                break;
            case 1:
                sprite.setX(rnd.nextFloat(worldWidth));
                sprite.setY(worldHeight -1f);
                break;
            case 2:
                sprite.setX(worldWidth -1f);
                sprite.setY(rnd.nextFloat(worldHeight));
                break;
            default:
                sprite.setX(rnd.nextFloat(worldWidth));
                sprite.setY(1f);
                break;
        }


        direction = MathUtils.degreesToRadians * (rnd.nextFloat(360f));

    }
    public void move(float asteroidSpeed, float deltaTime) {
        float dx = MathUtils.cos(direction) * asteroidSpeed * deltaTime;
        float dy = MathUtils.sin(direction) * asteroidSpeed * deltaTime;
        Vector2 position = new Vector2(sprite.getX(), sprite.getY());
        position.add(dx, dy);
        sprite.setPosition(position.x, position.y);
    }

}
