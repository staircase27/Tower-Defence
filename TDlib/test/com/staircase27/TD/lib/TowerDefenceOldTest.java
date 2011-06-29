/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.TowerDefenceOld.Areas;
import com.staircase27.TD.lib.grid.SquareGrid;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
public class TowerDefenceOldTest {
    
    public TowerDefenceOldTest() {
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
     * Test of constructor method, of class TowerDefenceOld.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        TowerDefenceOld instance = new TowerDefenceOld(new SquareGrid(10, 10), new HashMap<Point, TowerDefenceOld.Areas>());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of initialise method, of class TowerDefenceOld.
     */
    @Test
    public void testInitialise() {
        System.out.println("initialise");
        TowerDefenceOld instance = new TowerDefenceOld(new SquareGrid(10, 10), new HashMap<Point, TowerDefenceOld.Areas>(),false);
        instance.initialise();
        assertTrue(instance.blockedPoints.isEmpty());
        assertTrue(instance.routes.isEmpty());
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefenceOld.Areas>();
        areas.put(new Point(1,1), Areas.END);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(5,5), Areas.RAISED);
        areas.put(new Point(7,7), Areas.START);
        instance = new TowerDefenceOld(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        assertEquals(instance.blockedPoints.size(), 2);
        assertTrue(instance.blockedPoints.contains(new Point(3,3)));
        assertTrue(instance.blockedPoints.contains(new Point(5,5)));
        assertEquals(instance.routes.size(), 1);
        assertTrue(instance.routes.containsKey(new Point(1,1)));
    }

    /**
     * Test of generateRoutes method, of class TowerDefenceOld.
     */
    @Test
    public void testGenerateRoutes() {
        System.out.println("generateRoutes");
        TowerDefenceOld instance = new TowerDefenceOld(new SquareGrid(10, 10), new HashMap<Point, TowerDefenceOld.Areas>(),false);
        instance.initialise();
        assertTrue(instance.blockedPoints.isEmpty());
        assertTrue(instance.routes.isEmpty());
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefenceOld.Areas>();
        areas.put(new Point(0,0), Areas.END);
        areas.put(new Point(1,1), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);
        areas.put(new Point(1,3), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,5), Areas.BLOCKED);
        areas.put(new Point(1,6), Areas.BLOCKED);
        areas.put(new Point(1,7), Areas.BLOCKED);
        areas.put(new Point(2,1), Areas.BLOCKED);
        areas.put(new Point(4,8), Areas.BLOCKED);
        areas.put(new Point(3,1), Areas.BLOCKED);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(3,5), Areas.BLOCKED);
        areas.put(new Point(3,6), Areas.BLOCKED);
        areas.put(new Point(4,6), Areas.BLOCKED);
        areas.put(new Point(2,8), Areas.BLOCKED);
        areas.put(new Point(3,8), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);
        areas.put(new Point(3,7), Areas.START);
        instance = new TowerDefenceOld(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        instance.generateRoutes();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of generateRoute method, of class TowerDefenceOld.
     */
    @Test
    public void testGenerateRoute() {
        System.out.println("generateRoute");
        Point end = new Point(0,0);
        HashMap<Point, TwoItems<Integer, Set<Point>>> route = new HashMap<Point, TwoItems<Integer, Set<Point>>>();
        TowerDefenceOld instance = new TowerDefenceOld(new SquareGrid(10, 10), new HashMap<Point, TowerDefenceOld.Areas>(),false);
        instance.initialise();
        instance.generateRoute(end, route);
        assertEquals(route.size(), 100);
    }

    /**
     * Test of updateRouteBlocked method, of class TowerDefenceOld.
     */
    @Test
    public void testUpdateRouteBlocked() {
        System.out.println("generateRoutes");
        TowerDefenceOld instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefenceOld.Areas>();
        Point end=new Point(0,0);
        areas.put(end, Areas.END);
        areas.put(new Point(1,1), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);
        areas.put(new Point(1,3), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,5), Areas.BLOCKED);
        areas.put(new Point(1,6), Areas.BLOCKED);
        areas.put(new Point(1,7), Areas.BLOCKED);
        areas.put(new Point(2,1), Areas.BLOCKED);
        areas.put(new Point(3,1), Areas.BLOCKED);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(3,5), Areas.BLOCKED);
        areas.put(new Point(3,6), Areas.BLOCKED);
        areas.put(new Point(2,8), Areas.BLOCKED);
        areas.put(new Point(3,8), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);

        areas.put(new Point(7,1), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);
        areas.put(new Point(7,3), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,5), Areas.BLOCKED);
        areas.put(new Point(7,6), Areas.BLOCKED);
        areas.put(new Point(7,7), Areas.BLOCKED);
        areas.put(new Point(6,1), Areas.BLOCKED);
        areas.put(new Point(5,1), Areas.BLOCKED);
        areas.put(new Point(5,3), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(5,5), Areas.BLOCKED);
        areas.put(new Point(5,6), Areas.BLOCKED);
        areas.put(new Point(6,8), Areas.BLOCKED);
        areas.put(new Point(5,8), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);

        areas.put(new Point(3,7), Areas.START);
        instance = new TowerDefenceOld(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        instance.generateRoutes();
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        MapUpdate<Point, TwoItems<Integer, Set<Point>>> update = instance.updateRouteBlocked(blocked, instance.routes.get(end));
        update.update();
        blocked=new Point(4,7);
        instance.blockedPoints.add(blocked);
        update=instance.updateRouteBlocked(blocked, instance.routes.get(end));
        update.update();
    }

    /**
     * Test of updateRoutesBlocked method, of class TowerDefenceOld.
     */
    @Test
    public void testUpdateRoutesBlocked() {
        System.out.println("generateRoutes");
        TowerDefenceOld instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefenceOld.Areas>();
        Point end=new Point(0,0);
        areas.put(end, Areas.END);
        end=new Point(6,2);
        areas.put(end, Areas.END);
        areas.put(new Point(1,1), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);
        areas.put(new Point(1,3), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,5), Areas.BLOCKED);
        areas.put(new Point(1,6), Areas.BLOCKED);
        areas.put(new Point(1,7), Areas.BLOCKED);
        areas.put(new Point(2,1), Areas.BLOCKED);
        areas.put(new Point(3,1), Areas.BLOCKED);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(3,5), Areas.BLOCKED);
        areas.put(new Point(3,6), Areas.BLOCKED);
        areas.put(new Point(2,8), Areas.BLOCKED);
        areas.put(new Point(3,8), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);

        areas.put(new Point(7,1), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);
        areas.put(new Point(7,3), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,5), Areas.BLOCKED);
        areas.put(new Point(7,6), Areas.BLOCKED);
        areas.put(new Point(7,7), Areas.BLOCKED);
        areas.put(new Point(6,1), Areas.BLOCKED);
        areas.put(new Point(5,1), Areas.BLOCKED);
        areas.put(new Point(5,3), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(5,5), Areas.BLOCKED);
        areas.put(new Point(5,6), Areas.BLOCKED);
        areas.put(new Point(6,8), Areas.BLOCKED);
        areas.put(new Point(5,8), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);

        areas.put(new Point(3,7), Areas.START);
        instance = new TowerDefenceOld(new SquareGrid(10, 10), areas);
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        Map<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> update = instance.updateRoutesBlocked(blocked);
        for(Entry<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        Point blocked2 = new Point(4,7);
        instance.blockedPoints.add(blocked2);
        update=instance.updateRoutesBlocked(blocked2);
        for(Entry<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> entry:update.entrySet()){
            entry.getValue().update();
        }
    }

    /**
     * Test of printDebug method, of class TowerDefenceOld.
     * DOES NOTHING AS THIS IS DEBUG CODE THAT PRINTS STUFF
     */
    @Test
    public void testPrintDebug() {
        //not needed
    }

    /**
     * Test of updateRoutesUnblocked method, of class TowerDefenceOld.
     */
    @Test
    public void testUpdateRoutesUnblocked() {
        System.out.println("generateRoutes");
        TowerDefenceOld instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefenceOld.Areas>();
        Point end=new Point(0,0);
        areas.put(end, Areas.END);
        end=new Point(6,2);
        areas.put(end, Areas.END);
        areas.put(new Point(1,1), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);
        areas.put(new Point(1,3), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,5), Areas.BLOCKED);
        areas.put(new Point(1,6), Areas.BLOCKED);
        areas.put(new Point(1,7), Areas.BLOCKED);
        areas.put(new Point(2,1), Areas.BLOCKED);
        areas.put(new Point(3,1), Areas.BLOCKED);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(3,5), Areas.BLOCKED);
        areas.put(new Point(3,6), Areas.BLOCKED);
        areas.put(new Point(2,8), Areas.BLOCKED);
        areas.put(new Point(3,8), Areas.BLOCKED);
        areas.put(new Point(3,4), Areas.BLOCKED);
        areas.put(new Point(1,4), Areas.BLOCKED);
        areas.put(new Point(1,2), Areas.BLOCKED);

        areas.put(new Point(7,1), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);
        areas.put(new Point(7,3), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,5), Areas.BLOCKED);
        areas.put(new Point(7,6), Areas.BLOCKED);
        areas.put(new Point(7,7), Areas.BLOCKED);
        areas.put(new Point(6,1), Areas.BLOCKED);
        areas.put(new Point(5,1), Areas.BLOCKED);
        areas.put(new Point(5,3), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(5,5), Areas.BLOCKED);
        areas.put(new Point(5,6), Areas.BLOCKED);
        areas.put(new Point(6,8), Areas.BLOCKED);
        areas.put(new Point(5,8), Areas.BLOCKED);
        areas.put(new Point(5,4), Areas.BLOCKED);
        areas.put(new Point(7,4), Areas.BLOCKED);
        areas.put(new Point(7,2), Areas.BLOCKED);

        areas.put(new Point(3,7), Areas.START);
        instance = new TowerDefenceOld(new SquareGrid(10, 10), areas);
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        Map<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> update = instance.updateRoutesBlocked(blocked);
        for(Entry<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        Point blocked2 = new Point(4,7);
        instance.blockedPoints.add(blocked2);
        update=instance.updateRoutesBlocked(blocked2);
        for(Entry<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        instance.blockedPoints.remove(blocked2);
        instance.updateRoutesUnblocked(blocked2);
        instance.blockedPoints.remove(blocked);
        instance.updateRoutesUnblocked(blocked);
    }

    /**
     * Test of updateRouteUnblocked method, of class TowerDefenceOld.
     */
    @Test
    public void testUpdateRouteUnblocked() {
        System.out.println("updateRouteUnblocked");
        Point unblockedPoint = null;
        HashMap<Point, TwoItems<Integer, Set<Point>>> route = null;
        TowerDefenceOld instance = null;
        instance.updateRouteUnblocked(unblockedPoint, route);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
