package com.mygdx.game.entities.npc;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.map.Map;
import com.sun.tools.javac.util.Pair;

import java.util.*;

public class Path {
    private List<Point> pointList = new ArrayList<>();

    public Path(Map map, String pathName) {
        MapLayer layer = map.getLayer(pathName);
        Iterator<MapObject> mapObjects = layer.getObjects().iterator();
        mapObjects.forEachRemaining(mapObject -> pointList.add(new Point(mapObject)));
        Collections.sort(pointList, (o1, o2) -> {
            int i1 = Integer.valueOf(o1.getName().get(0));
            int i2 = Integer.valueOf(o2.getName().get(0));
            return i1 - i2;
        });
        for (Point point : pointList) {
            System.out.println(point.getName());
        }
    }

    private class Point {
        private Pair<List<String>, Rectangle> self;

        public Point(MapObject mapObject) {
            self = new Pair<>(Arrays.asList(mapObject.getName().split("_")),
                    ((RectangleMapObject) mapObject).getRectangle());
        }

        public List<String> getName() {
            return self.fst;
        }
    }
}
