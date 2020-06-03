package com.mygdx.game.entities.npc;

public class NpcBuilder {
    public static Npc setEndStartDelay(Npc npc, int startDelayMillis, int endDelayMillis){
        npc.setMovementDelayManager(
                new MovementDelayManager() {
                    long startLimit = System.currentTimeMillis() + startDelayMillis;
                    long endLimit = System.currentTimeMillis() + endDelayMillis;

                    @Override
                    public boolean preMovePredicate() {
                        if ((npc.getPath().isFirst()) && System.currentTimeMillis() < startLimit) {
                            return false;
                        }
                        if((npc.getPath().isLast()) && System.currentTimeMillis() < endLimit){
                            return false;
                        }
                        startLimit = System.currentTimeMillis() + startDelayMillis;
                        endLimit = System.currentTimeMillis() + endDelayMillis;
                        return true;
                    }

                    @Override
                    public boolean postMovePredicate() {
                        return false;
                    }
                }
        );
        return npc;
    }
}
