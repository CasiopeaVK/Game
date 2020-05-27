package com.mygdx.game.entities.npc;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Path {
    private Vector2 currentPoint;
    private List<Vector2> points = new ArrayList<>();

    public Path(Map map, String pathName) {
        PolylineMapObject polygon = (PolylineMapObject) map.getObject("Paths", pathName);
        Polyline polyline = polygon.getPolyline();
        buildPoints(polyline);
        System.out.println(points);
    }

    private void buildPoints(Polyline polyline) {
        float[] unparsedPoints = polyline.getTransformedVertices();
        for (int i = 0; i < unparsedPoints.length; i+=2) {
            points.add(new Vector2(unparsedPoints[i],unparsedPoints[i+1]));
        }
    }

    public Vector2 getIsoFirstPoint(){
        return points.get(0);
    }

}
