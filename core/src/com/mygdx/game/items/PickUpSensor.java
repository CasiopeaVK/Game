package com.mygdx.game.items;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Player;
import lombok.Getter;

public class PickUpSensor implements ContactListener {

    @Getter
    private boolean isTriggered = false;
    @Getter
    private Item item = null;

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if (A.getUserData() == null || B.getUserData() == null) {
            return;
        }
        item = isItemContact(A, B);
        if (item != null) {
            isTriggered = true;
            item.setZoomToSprite(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if (A.getUserData() == null || B.getUserData() == null) {
            return;
        }
        item = isItemContact(A, B);
        if (item != null) {
            isTriggered = false;
            item.setZoomToSprite(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private Item isItemContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Item) {
            if (b.getUserData() instanceof Player) {
                return (Item) a.getUserData();
            }
        } else if (b.getUserData() instanceof Item) {
            if (a.getUserData() instanceof Player) {
                return (Item) b.getUserData();
            }
        }
        return null;
    }
}
