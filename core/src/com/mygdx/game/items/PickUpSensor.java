package com.mygdx.game.items;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Player;
import lombok.Getter;

public class PickUpSensor implements ContactListener {

    @Getter
    private boolean isTriggered = false;
    @Getter
    private PickUpItem pickUpItem = null;

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if (A.getUserData() == null || B.getUserData() == null) {
            return;
        }
        pickUpItem = isItemContact(A, B);
        if (pickUpItem != null) {
            pickUpItem.setOutlineToSprite(true);
            isTriggered = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if (A.getUserData() == null || B.getUserData() == null) {
            return;
        }
        pickUpItem = isItemContact(A, B);
        if (pickUpItem != null) {
            pickUpItem.setOutlineToSprite(false);
            isTriggered = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private PickUpItem isItemContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof PickUpItem) {
            if (b.getUserData() instanceof Player) {
                return (PickUpItem) a.getUserData();
            }
        } else if (b.getUserData() instanceof PickUpItem) {
            if (a.getUserData() instanceof Player) {
                return (PickUpItem) b.getUserData();
            }
        }
        return null;
    }
}
