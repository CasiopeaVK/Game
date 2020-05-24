package com.mygdx.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.view.AnimationType;

public class AnimationComponent implements Component, Pool.Poolable{

    public AnimationType aniType;
    public float aniTime;

    @Override
    public void reset() {
        aniType = null;
        aniTime = 0;

    }
}
