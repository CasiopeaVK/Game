package com.mygdx.game.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ecs.component.B2DComponent;
import com.mygdx.game.ecs.component.PlayerComponent;

public class ECSengine extends PooledEngine {
    public ECSengine() {
        super();
    }

    public void createPlayer(final Vector2 spawnPoint) {
        final Entity player = this.createEntity();
        player.add(this.createComponent(PlayerComponent.class));
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);



    }
}
