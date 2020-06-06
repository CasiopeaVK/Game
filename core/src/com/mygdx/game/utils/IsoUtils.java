package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Constants;

public class IsoUtils {
    public static Vector2 TwoDToIso(Vector2 point) {
        Vector2 vel2 = new Vector2();

        vel2.x = point.x - point.y;
        vel2.y = -(point.x + point.y) / 2;
        return vel2;
    }

    public static Vector2 IsoTo2d(Vector2 isoVector) {
        float x = (isoVector.x + isoVector.y) * Constants.UNIT_SCALE;
        float y = ((isoVector.y - isoVector.x) * Constants.UNIT_SCALE) / 2;
        return new Vector2(x, y);
    }

    public static Vector2 LocalToIso(Vector2 local){
        float y = (2*local.y+local.x)/(2*Constants.UNIT_SCALE);
        float x = (local.x/Constants.UNIT_SCALE) - y;
        return new Vector2(x,y);
    }


    public static Vector2 multiplyVector(Vector2 v, float scalar){
        return new Vector2(v.x*scalar,v.y*scalar);
    }

    public static boolean Vector3Equals(Vector3 v1, Vector3 v2) {
        return v1.x == v2.x && v1.y == v2.y && v1.z == v2.z;
    }

    public static Vector2 Vector3ToVector2(Vector3 vector3) {
        return new Vector2(vector3.x, vector3.y);
    }

    public static double CalculateVector2Length(Vector2 vector2){
        return Math.sqrt(Math.pow(vector2.x,2)+Math.pow(vector2.y,2));
    }

    public static Vector2 getDirection(Vector2 vector2){
        float length = (float) CalculateVector2Length(vector2);
        return new Vector2(vector2.x/length, vector2.y/length);
    }
}
