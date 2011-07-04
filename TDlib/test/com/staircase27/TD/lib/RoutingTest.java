/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashMap;
import com.staircase27.TD.lib.grid.SquareGrid;
import com.staircase27.TD.lib.lib.Updatable.MapUpdate;
import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Simon Armstrong
 */
public class RoutingTest {

    public RoutingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of generateRoutes method, of class Routing.
     */
    @Test
    public void testGenerateRoutes() {
        System.out.println("generateRoutes");
        Routing instance = new Routing(new SquareGrid(10, 10), new HashSet<Point>(), new HashSet<Point>());
        instance.filebase="generateRoutes_"+instance.filebase;
        instance.generateRoutes();
        assertTrue(instance.routes.isEmpty());
        HashSet<Point> blockedPoints = new HashSet<Point>();
        HashSet<Point> ends = new HashSet<Point>();
        Point end=new Point(0, 0);
        ends.add(end);
        blockedPoints.add(new Point(1, 1));
        blockedPoints.add(new Point(1, 2));
        blockedPoints.add(new Point(1, 3));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 5));
        blockedPoints.add(new Point(1, 6));
        blockedPoints.add(new Point(1, 7));
        blockedPoints.add(new Point(2, 1));
        blockedPoints.add(new Point(4, 8));
        blockedPoints.add(new Point(3, 1));
        blockedPoints.add(new Point(3, 3));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(3, 5));
        blockedPoints.add(new Point(3, 6));
        blockedPoints.add(new Point(4, 6));
        blockedPoints.add(new Point(2, 8));
        blockedPoints.add(new Point(3, 8));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 2));
        instance = new Routing(new SquareGrid(10, 10), blockedPoints, ends);
        instance.filebase="generateRoutes_"+instance.filebase;
        instance.generateRoutes();
        assertEquals(instance.routes.size(), 1);
        
        assertEquals(instance.routes.get(end).size(), 100-blockedPoints.size() );

    }

    /**
     * Test of updateRoutesBlocked method, of class Routing.
     */
    @Test
    public void testUpdateRoutesBlocked() {
        System.out.println("updateRoutesBlocked");
        Routing instance;
        HashSet<Point> blockedPoints = new HashSet<Point>();
        HashSet<Point> ends = new HashSet<Point>();
        Point end1 = new Point(0, 0);
        ends.add(end1);
        Point end2 = new Point(6, 2);
        ends.add(end2);
        blockedPoints.add(new Point(1, 1));
        blockedPoints.add(new Point(1, 2));
        blockedPoints.add(new Point(1, 3));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 5));
        blockedPoints.add(new Point(1, 6));
        blockedPoints.add(new Point(1, 7));
        blockedPoints.add(new Point(2, 1));
        blockedPoints.add(new Point(3, 1));
        blockedPoints.add(new Point(3, 3));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(3, 5));
        blockedPoints.add(new Point(3, 6));
        blockedPoints.add(new Point(2, 8));
        blockedPoints.add(new Point(3, 8));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 2));

        blockedPoints.add(new Point(7, 1));
        blockedPoints.add(new Point(7, 2));
        blockedPoints.add(new Point(7, 3));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 5));
        blockedPoints.add(new Point(7, 6));
        blockedPoints.add(new Point(7, 7));
        blockedPoints.add(new Point(6, 1));
        blockedPoints.add(new Point(5, 1));
        blockedPoints.add(new Point(5, 3));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(5, 5));
        blockedPoints.add(new Point(5, 6));
        blockedPoints.add(new Point(6, 8));
        blockedPoints.add(new Point(5, 8));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 2));

        instance = new Routing(new SquareGrid(10, 10), blockedPoints, ends);
        instance.filebase="updateRoutesBlocked_"+instance.filebase;
        instance.generateRoutes();

        Point blocked = new Point(4, 2);
        Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(blocked);
        update.get(end1).update();

        Point blocked2 = new Point(4, 7);
        update = instance.updateRoutesBlocked(blocked2);
        update.get(end1).update();
    }

    /**
     * Test of updateRoutesUnblocked method, of class Routing.
     */
    @Test
    public void testUpdateRoutesUnblocked() {
        System.out.println("updateRoutesUnblocked");
        Routing instance;
        HashSet<Point> blockedPoints = new HashSet<Point>();
        HashSet<Point> ends = new HashSet<Point>();
        Point end1 = new Point(0, 0);
        Point end2 = new Point(6, 2);
        ends.add(end1);
        ends.add(end2);
        blockedPoints.add(new Point(1, 1));
        blockedPoints.add(new Point(1, 2));
        blockedPoints.add(new Point(1, 3));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 5));
        blockedPoints.add(new Point(1, 6));
        blockedPoints.add(new Point(1, 7));
        blockedPoints.add(new Point(2, 1));
        blockedPoints.add(new Point(3, 1));
        blockedPoints.add(new Point(3, 3));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(3, 5));
        blockedPoints.add(new Point(3, 6));
        blockedPoints.add(new Point(2, 8));
        blockedPoints.add(new Point(3, 8));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 2));

        blockedPoints.add(new Point(7, 1));
        blockedPoints.add(new Point(7, 2));
        blockedPoints.add(new Point(7, 3));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 5));
        blockedPoints.add(new Point(7, 6));
        blockedPoints.add(new Point(7, 7));
        blockedPoints.add(new Point(6, 1));
        blockedPoints.add(new Point(5, 1));
        blockedPoints.add(new Point(5, 3));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(5, 5));
        blockedPoints.add(new Point(5, 6));
        blockedPoints.add(new Point(6, 8));
        blockedPoints.add(new Point(5, 8));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 2));

        instance = new Routing(new SquareGrid(10, 10), blockedPoints, ends);
        instance.filebase="updateRoutesUnblocked_"+instance.filebase;
        instance.generateRoutes();

        Point blocked = new Point(4, 2);
        Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(blocked);
        update.get(end1).update();

        Point blocked2 = new Point(4, 7);
        update = instance.updateRoutesBlocked(blocked2);
        update.get(end1).update();

        instance.updateRoutesUnblocked(blocked2);
        instance.updateRoutesUnblocked(blocked);
    }

    /**
     * Test of getNextPoint method, of class Routing.
     */
    @Test
    public void testGetNextPoint() {
        Routing instance;
        HashSet<Point> blockedPoints = new HashSet<Point>();
        HashSet<Point> ends = new HashSet<Point>();
        Point end = new Point(0, 0);
        ends.add(end);
        blockedPoints.add(new Point(1, 1));
        blockedPoints.add(new Point(1, 3));
        blockedPoints.add(new Point(1, 5));
        blockedPoints.add(new Point(1, 7));
        blockedPoints.add(new Point(3, 1));
        blockedPoints.add(new Point(3, 3));
        blockedPoints.add(new Point(3, 5));
        blockedPoints.add(new Point(3, 7));
        blockedPoints.add(new Point(5, 1));
        blockedPoints.add(new Point(5, 3));
        blockedPoints.add(new Point(5, 5));
        blockedPoints.add(new Point(5, 7));
        blockedPoints.add(new Point(7, 1));
        blockedPoints.add(new Point(7, 3));
        blockedPoints.add(new Point(7, 5));
        blockedPoints.add(new Point(7, 7));
        instance = new Routing(new SquareGrid(9, 9), blockedPoints, ends);
        instance.filebase="getNextPoint_"+instance.filebase;
        instance.generateRoutes();

        Map<Point, Integer> choices = new HashMap<Point, Integer>();
        int n = 10000;
        double error = Math.sqrt(n);
        Point prev = new Point(8, 7);
        Point curr = new Point(8, 6);
        Point next = null;
        System.out.println("Doing choices");
        for (int i = 0; i < n; i++) {
            next = instance.getNextPoint(end, curr, prev);
            if (choices.containsKey(next)) {
                choices.put(next, choices.get(next) + 1);
            } else {
                choices.put(next, 1);
            }
        }
        int total = 0;
        for (Entry<Point, Integer> entry : choices.entrySet()) {
            System.out.print(entry.getKey());
            System.out.println(entry.getValue());
            total += entry.getValue();
        }
        assertEquals(total, n);
        assertEquals(choices.size(), 2);
        assertTrue(choices.get(new Point(8, 5)) > 0.6 * n - error);
        assertTrue(choices.get(new Point(8, 5)) < 0.6 * n + error);

        curr = null;
        prev = null;
        next = new Point(8, 8);
        Map<Point, Integer> path2 = new HashMap<Point, Integer>();
        int i = 0;
        System.out.println(instance.index);
        while (!end.equals(curr)) {
            instance.printDebug('1', next, instance.routes.get(end), path2, path2);
            prev = curr;
            curr = next;
            next = instance.getNextPoint(end, curr, prev);
            path2.put(curr, i++);
            System.out.println(next);
        }
        ArrayList<Point> blockable = new ArrayList<Point>(Arrays.asList(new Point[]{
                    new Point(0, 1), new Point(0, 3), new Point(0, 5), new Point(0, 7), new Point(1, 0),
                    new Point(1, 2), new Point(1, 4), new Point(1, 6), new Point(1, 8), new Point(2, 1),
                    new Point(2, 3), new Point(2, 5), new Point(2, 7), new Point(3, 0), new Point(3, 2),
                    new Point(3, 4), new Point(3, 6), new Point(3, 8), new Point(4, 1), new Point(4, 3),
                    new Point(4, 5), new Point(4, 7), new Point(5, 0), new Point(5, 2), new Point(5, 4),
                    new Point(5, 6), new Point(5, 8), new Point(6, 1), new Point(6, 3), new Point(6, 5),
                    new Point(6, 7), new Point(7, 0), new Point(7, 2), new Point(7, 4), new Point(7, 6),
                    new Point(7, 8), new Point(8, 1), new Point(8, 3), new Point(8, 5), new Point(8, 7)}));
        HashSet<Point> blocked = new HashSet<Point>();

        curr = null;
        prev = null;
        next = new Point(8, 8);
        path2.clear();

        int j = 0;
        while (j < 10) {
            Collections.shuffle(blockable);
            Point p = blockable.get(0);

            if (!blocked.contains(p)) {
                Set<Point> hilight = new HashSet<Point>();
                hilight.add(p);
                hilight.add(curr);
                instance.printDebug('1', hilight, instance.routes.get(end), path2, path2);
                instance.blockedPoints.add(p);
                Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(p);
                if (update.get(end).removed(curr) || update.get(end).removed(new Point(8, 8))) {
                    instance.blockedPoints.remove(p);
                    continue;
                }
                update.get(end).update();
                blocked.add(p);
                instance.printDebug('1', hilight, instance.routes.get(end), path2, path2);
            } else {
                Set<Point> hilight = new HashSet<Point>();
                hilight.add(p);
                hilight.add(curr);
                instance.printDebug('1', hilight, instance.routes.get(end), path2, path2);
                blocked.remove(p);
                instance.blockedPoints.remove(p);
                instance.updateRoutesUnblocked(p);
                instance.printDebug('1', hilight, instance.routes.get(end), path2, path2);
            }
            if (end.equals(curr) || curr == null) {
                curr = null;
                prev = null;
                next = new Point(8, 8);
                path2.clear();
                i = 0;
                j++;
            } else {
                next = instance.getNextPoint(end, curr, prev);
            }
            prev = curr;
            curr = next;
            path2.put(curr, i++);
            System.out.println(curr);
            instance.printDebug('1', next, instance.routes.get(end), path2, path2);
        }
    }

    /**
     * UNEADED Test of printDebug method, of class Routing.
     */
    @Test
    public void testPrintDebug_5args_1() {
    }

    /**
     * UNEADED Test of printDebug method, of class Routing.
     */
    @Test
    public void testPrintDebug_5args_2() {
    }

    /**
     * Test of generateRoute method, of class Routing.
     */
    @Test
    public void testGenerateRoute() {
        System.out.println("generateRoute");
        Point end = new Point(0, 0);
        HashMap<Point, Integer> route = new HashMap<Point, Integer>();
        HashMap<Point, Integer> rhs = new HashMap<Point, Integer>();
        Routing instance = new Routing(new SquareGrid(10, 10), new HashSet<Point>(), new HashSet<Point>());
        instance.filebase="generateRoute_"+instance.filebase;
        instance.generateRoute(end, route, rhs);
        assertEquals(route.size(), 100);
        assertEquals(rhs.size(), 100);
        for (Point p : route.keySet()) {
            assertEquals(route.get(p), rhs.get(p));
        }
    }

    /**
     * Test of updateRouteBlocked method, of class Routing.
     */
    @Test
    public void testUpdateRouteBlocked() {
        System.out.println("updateRouteBlocked");
        Routing instance;
        HashSet<Point> blockedPoints = new HashSet<Point>();
        HashSet<Point> ends = new HashSet<Point>();
        Point end = new Point(0, 0);
        ends.add(end);
        blockedPoints.add(new Point(1, 1));
        blockedPoints.add(new Point(1, 2));
        blockedPoints.add(new Point(1, 3));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 5));
        blockedPoints.add(new Point(1, 6));
        blockedPoints.add(new Point(1, 7));
        blockedPoints.add(new Point(2, 1));
        blockedPoints.add(new Point(3, 1));
        blockedPoints.add(new Point(3, 3));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(3, 5));
        blockedPoints.add(new Point(3, 6));
        blockedPoints.add(new Point(2, 8));
        blockedPoints.add(new Point(3, 8));
        blockedPoints.add(new Point(3, 4));
        blockedPoints.add(new Point(1, 4));
        blockedPoints.add(new Point(1, 2));

        blockedPoints.add(new Point(7, 1));
        blockedPoints.add(new Point(7, 2));
        blockedPoints.add(new Point(7, 3));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 5));
        blockedPoints.add(new Point(7, 6));
        blockedPoints.add(new Point(7, 7));
        blockedPoints.add(new Point(6, 1));
        blockedPoints.add(new Point(5, 1));
        blockedPoints.add(new Point(5, 3));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(5, 5));
        blockedPoints.add(new Point(5, 6));
        blockedPoints.add(new Point(6, 8));
        blockedPoints.add(new Point(5, 8));
        blockedPoints.add(new Point(5, 4));
        blockedPoints.add(new Point(7, 4));
        blockedPoints.add(new Point(7, 2));

        instance = new Routing(new SquareGrid(10, 10), blockedPoints, ends);
        instance.filebase="updateRouteBlocked_"+instance.filebase;

        instance.generateRoutes();

        Point blocked = new Point(4, 2);
        instance.blockedPoints.add(blocked);
        MapUpdate<Point, Integer> update = instance.updateRouteBlocked(blocked, instance.blockedPoints, end, instance.routes.get(end), instance.rhss.get(end));
        update.update();
        blocked = new Point(4, 7);
        instance.blockedPoints.add(blocked);
        update = instance.updateRouteBlocked(blocked, instance.blockedPoints, end, instance.routes.get(end), instance.rhss.get(end));
        update.update();
    }

    /**
     * Test of updateRouteUnblocked method, of class Routing.
     * Unneeded as tested by UpdateRoutesUnblocked
     */
    @Test
    public void testUpdateRouteUnblocked() {
    }
}
