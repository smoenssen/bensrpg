package com.smoftware.bensrpg.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.smoftware.bensrpg.BensRPG;
import com.smoftware.bensrpg.sprites.Hero;
import com.smoftware.bensrpg.sprites.tileObjects.AbstractCollisionTileObject;

/**
 * Created by brentaureli on 9/4/15.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case BensRPG.HERO_BIT | BensRPG.MAP1_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.MAP2_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.OBSTACLE_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.GENERIC_OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.BOUNDS_OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.ARMORY_DOOR_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
            case BensRPG.HERO_BIT | BensRPG.ARMORY_EXIT_DOOR_BIT:
                if(fixA.getFilterData().categoryBits == BensRPG.HERO_BIT)
                    ((AbstractCollisionTileObject) fixB.getUserData()).onCollision((Hero) fixA.getUserData());
                else
                    ((AbstractCollisionTileObject) fixA.getUserData()).onCollision((Hero) fixB.getUserData());
                break;
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
