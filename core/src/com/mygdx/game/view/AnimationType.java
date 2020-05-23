package com.mygdx.game.view;

public enum  AnimationType {
    HERO_MOVE_UP("hero/character_movement.atlas", "hero", 0.05f, 2),
    HERO_MOVE_DOWN("hero/character_movement.atlas", "hero", 0.05f, 0),
    HERO_MOVE_LEFT("hero/character_movement.atlas", "hero", 0.05f, 3),
    HERO_MOVE_RIGHT("hero/character_movement.atlas", "hero", 0.05f, 1);

    private final String atlasPath;
    private final String atlasKey;
    private final float frameTime;
    private final int index;

    AnimationType(final String atlasPath, String atlasKey, float frameTime, int index) {
        this.atlasPath = atlasPath;
        this.atlasKey = atlasKey;
        this.frameTime = frameTime;
        this.index = index;
    }
}
