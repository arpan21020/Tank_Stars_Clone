package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;
	Skin myskin;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
//		badlogic =new Texture(Gdx.files.internal("badlogic.jpg"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		myskin=new Skin(Gdx.files.internal("glassy-ui.json"));
		Button button2 = new TextButton("Text Button",myskin,"small");
		button2.setSize(100,50);
		button2.setPosition(0,0);
		button2.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Press a Button");
			}

			@Override
			public boolean touchDown(InputEvent event,float x,float y,int pointer,int button){
				System.out.println("Pressed Text Button");
				return true;
			}
		}	);
		stage.addActor(button2);

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
