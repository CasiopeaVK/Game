package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

public class IsoUtils {
    public static Vector2 TwoDToIso(Vector2 point) {
        Vector2 vel2 = new Vector2();

        vel2.x = point.x - point.y;
        vel2.y = -(point.x + point.y) / 2;
        return vel2;
    }
}
