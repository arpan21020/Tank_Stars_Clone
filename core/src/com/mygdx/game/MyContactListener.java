package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa=contact.getFixtureA();
        Fixture fb=contact.getFixtureB();
        if (fa==null||fb==null)return;
        if(fa.getUserData() instanceof Bullet && fb.getUserData()==null){
           Bullet b=(Bullet) fa.getUserData();
           b.bullet.setPosition(200000f,0f);
           b.bodydef.position.set(20000f,0f);
        }
        if(fa.getUserData()==null && fb.getUserData() instanceof Bullet){
            Bullet b=(Bullet) fb.getUserData();
            b.bullet.setPosition(200000f,0f);
            b.bodydef.position.set(20000f,0f);
        }
        System.out.println(fa.getUserData()+"collides with"+fb.getUserData());
//        System.out.println("fa.getinstance():"+fa.getUserData()==null+"String:"+fb==null);

        if(fa.getUserData() instanceof Bullet && fb.getUserData() instanceof Tank){
            Tank t=(Tank)fb.getUserData();
            t.setHealth(t.getHealth()-20f);
            System.out.println(t.getHealth());
        }
        if(fa.getUserData() instanceof Tank && fb.getUserData() instanceof Bullet){
            Tank t=(Tank)fa.getUserData();
            t.setHealth(t.getHealth()-20f);
            System.out.println(t.getHealth());
        }





    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
