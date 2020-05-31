package com.mygdx.game.entities.npc;

public interface MovementDelayManager {
    boolean preMovePredicate();
    boolean postMovePredicate();
}
