package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bullet {
    public World world;
    public Body b2body;
    BodyDef bodydef;
    Tank tank;
    Image bullet=null;
    public Bullet(Tank tank){
        this.tank=tank;
        this.world=tank.world;

//        makebullet();

    }
    public void makebullet(){
        if (tank.getPl()==1) bullet=new Image(new Texture(Gdx.files.internal("Weapons/bulletfire.png")));
        else bullet=new Image(new Texture(Gdx.files.internal("Weapons/bulletfire2.png")));
        bullet.setSize(70f,30f);
        tank.arena.stage.addActor(bullet);
        bodydef=new BodyDef();
        bodydef.type=BodyDef.BodyType.DynamicBody;
        if (tank.getPl()==1) bodydef.position.set(100+tank.b2body.getPosition().x,100+tank.b2body.getPosition().y);
        else bodydef.position.set(tank.b2body.getPosition().x-100,tank.b2body.getPosition().y+100);
        b2body=world.createBody(bodydef);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(20);
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    public void update(){
//        arena.game.batch3.begin();
//        sprite.draw(arena.game.batch3);
//        arena.game.batch3.end();
        if (this.bullet!=null && this.b2body!=null) {

            bullet.setPosition(b2body.getPosition().x/1.98f,b2body.getPosition().y/1.848f);
        }



//        bullet.setPosition(x+50+b2body.getPosition().x/1.98f,y+50+b2body.getPosition().y/1.848f);
//        tank1.setPosition(b2body.getPosition().x,b2body.getPosition().y);
//        System.out.println("x:"+b2body.getPosition().x);
//        System.out.println("y:"+b2body.getPosition().y);

    }



}
