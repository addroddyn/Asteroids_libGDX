package io.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.asteroids.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Asteroids extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    public ShapeRenderer renderer;


    public void create() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        // use libGDX's default font
        font = new BitmapFont();
        viewport = new FitViewport(1600, 900);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight()));
        //font.getData().setScale(1f);
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render(); // important!
        /*batch.begin();

        this.font.draw(batch, "This is always here ", 2, 1.5f);
        batch.end();*/
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
