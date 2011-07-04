/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.grid.Grid;
import com.staircase27.TD.lib.lib.Updatable.MapUpdate;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public final class TowerDefence {

    public static enum Areas {

        BLOCKED, BOOST, RAISED
    }
    public final Grid grid;
    HashMap<Point, Set<Point>> endsMap;
    HashMap<Point, Set<Point>> startsMap;
    HashMap<Point, BaseTower> towers = new HashMap<Point, BaseTower>();
    HashMap<Point, Areas> areas;
    Routing routing;

    public TowerDefence(Grid grid, HashMap<Point, Areas> areas, HashMap<Point, Set<Point>> endsMap) {
        this.grid = grid;
        this.areas = areas;
        this.endsMap = endsMap;
        initialise();
        routing.generateRoutes();
    }

    TowerDefence(Grid grid, HashMap<Point, Areas> areas, HashMap<Point, Set<Point>> endsMap, boolean hehe) {
        this.grid = grid;
        this.areas = areas;
        this.endsMap = endsMap;
        initialise();
        routing.generateRoutes();
    }

    void initialise() {
        HashSet<Point> blockedPoints = new HashSet<Point>();
        for (Point point : grid) {
            if ((areas.containsKey(point) && (areas.get(point) == Areas.BLOCKED || areas.get(point) == Areas.RAISED)) || towers.containsKey(point)) {
                blockedPoints.add(point);
            }
        }
        startsMap = new HashMap<Point, Set<Point>>();
        for (Point end : endsMap.keySet()) {
            for (Point start : endsMap.get(end)) {
                if (!startsMap.containsKey(start)) {
                    startsMap.put(start, new HashSet<Point>());
                }
                startsMap.get(start).add(end);
            }
        }
        routing = new Routing(grid, blockedPoints, endsMap.keySet());
    }

    public Point getNextPoint(Point target, Point curr, Point prev) {
        return routing.getNextPoint(target, curr, prev);
    }

    public boolean addTower(BaseTower tower, Point point) {
        Map<Point, MapUpdate<Point, Integer>> updates = routing.updateRoutesBlocked(point);
        MapUpdate<Point, Integer> update = null;
        for (Point end : endsMap.keySet()) {
            update = updates.get(end);
            for (Point start : endsMap.get(end)) {
                if (update.removed(start)) {
                    return false;
                }
            }
            //TODO: test for enemies being blocked
        }
        if (update == null) {
            return false;
        }
        update.update();
        towers.put(point, tower);
        return true;
    }

    public BaseTower replaceTower(BaseTower tower, Point point) {
        if (towers.containsKey(point)) {
            return towers.put(point, tower);
        } else {

            return null;
        }
    }

    public BaseTower removeTower(Point point) {
        routing.updateRoutesUnblocked(point);
        return towers.remove(point);
    }
}
