package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TankStars extends Game {

    public SpriteBatch batch;
    public SpriteBatch batch2;
public SpriteBatch batch3;
    @Override
    public void create () {
//		this.create();
        batch = new SpriteBatch();
        batch2 = new SpriteBatch();
        batch3=new SpriteBatch();

        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
        batch2.dispose();
        batch3.dispose();

    }
}
