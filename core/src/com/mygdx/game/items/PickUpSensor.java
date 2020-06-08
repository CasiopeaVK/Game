package com.mygdx.game.items;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Door;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.SingleDoor;
import com.mygdx.game.entities.npc.EvilNPC;
import lombok.Getter;

public class PickUpSensor implements ContactListener {

    @Getter
    private boolean isTriggered = false;
    @Getter
    private Item item = null;
    @Getter
    private boolean isNearDoor = false;
    @Getter
    private Door door = null;

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
        activateTrigger(A, B);
        activateDoorTrigger(A, B);
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
        deactivateTrigger(A, B);
        deactivateDoorTrigger(A, B);
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

    private void activateTrigger(Fixture a, Fixture b) {
        if (a.getUserData() instanceof EvilNPC) {
            if (b.getUserData() instanceof Player) {
                ((EvilNPC) a.getUserData()).triggerNpc();
            }
        } else if (b.getUserData() instanceof EvilNPC) {
            if (a.getUserData() instanceof Player) {
                ((EvilNPC) b.getUserData()).triggerNpc();
            }
        }
    }

    private void deactivateTrigger(Fixture a, Fixture b) {
        if (a.getUserData() instanceof EvilNPC) {
            if (b.getUserData() instanceof Player) {
                ((EvilNPC) a.getUserData()).cancelTrigger();
            }
        } else if (b.getUserData() instanceof EvilNPC) {
            if (a.getUserData() instanceof Player) {
                ((EvilNPC) b.getUserData()).cancelTrigger();
            }
        }
    }

    private void activateDoorTrigger(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Door) {
            if (b.getUserData() instanceof Player) {
                isNearDoor = true;
                door = (Door) a.getUserData();
            }
        } else if (b.getUserData() instanceof Door) {
            if (a.getUserData() instanceof Player) { ;
                isNearDoor = true;
                door = (Door) b.getUserData();
            }
        }
    }

    private void deactivateDoorTrigger(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Door) {
            if (b.getUserData() instanceof Player) {
                isNearDoor = false;
                door = null;
            }
        } else if (b.getUserData() instanceof Door) {
            if (a.getUserData() instanceof Player) {
                isNearDoor = false;
                door = null;
            }
        }
    }

}
