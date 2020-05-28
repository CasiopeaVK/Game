package com.mygdx.game.entities.npc;

import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.Map;

import java.util.*;

public class Path {
    private int currentPointIndex = 0;
    private List<Vector2> points = new ArrayList<>();
    private boolean isForward = true;

    public Path(Map map, String pathName) {
        PolylineMapObject polygon = (PolylineMapObject) map.getObject("Paths", pathName);
        Polyline polyline = polygon.getPolyline();
        buildPoints(polyline);
        System.out.println(points);
    }

    private void buildPoints(Polyline polyline) {
        float[] unparsedPoints = polyline.getTransformedVertices();
        for (int i = 0; i < unparsedPoints.length; i += 2) {
            points.add(new Vector2(unparsedPoints[i], unparsedPoints[i + 1]));
        }
    }

    public Vector2 getIsoFirstPoint() {
        return points.get(0);
    }

    public void moveNext() {
        if(currentPointIndex==points.size()-1){
            isForward = false;
        }

        if(isForward){
            currentPointIndex++;
        }else {
            moveBefore();
        }
    }

    public void moveBefore() {
        if(currentPointIndex>0){
            currentPointIndex--;
        }else {
            isForward = true;
        }
    }

    public Vector2 getIsoCurrent() {
        return points.get(currentPointIndex % points.size());
    }

}
