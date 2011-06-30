/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.TowerDefence.Areas;
import com.staircase27.TD.lib.grid.SquareGrid;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class TowerDefenceTest {
    
    public TowerDefenceTest() {
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
     * Test of constructor method, of class TowerDefence.
     */
    @Test
    public void testConstructor() {
        System.out.println("constructor");
        TowerDefence instance = new TowerDefence(new SquareGrid(10, 10), new HashMap<Point, TowerDefence.Areas>());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of initialise method, of class TowerDefence.
     */
    @Test
    public void testInitialise() {
        System.out.println("initialise");
        TowerDefence instance = new TowerDefence(new SquareGrid(10, 10), new HashMap<Point, TowerDefence.Areas>(),false);
        instance.initialise();
        assertTrue(instance.blockedPoints.isEmpty());
        assertTrue(instance.routes.isEmpty());
        assertTrue(instance.rhss.isEmpty());
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
        areas.put(new Point(1,1), Areas.END);
        areas.put(new Point(3,3), Areas.BLOCKED);
        areas.put(new Point(5,5), Areas.RAISED);
        areas.put(new Point(7,7), Areas.START);
        instance = new TowerDefence(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        assertEquals(instance.blockedPoints.size(), 2);
        assertTrue(instance.blockedPoints.contains(new Point(3,3)));
        assertTrue(instance.blockedPoints.contains(new Point(5,5)));
        assertEquals(instance.routes.size(), 1);
        assertTrue(instance.routes.containsKey(new Point(1,1)));
        assertTrue(instance.rhss.containsKey(new Point(1,1)));
    }

    /**
     * Test of generateRoutes method, of class TowerDefence.
     */
    @Test
    public void testGenerateRoutes() {
        System.out.println("generateRoutes");
        TowerDefence instance = new TowerDefence(new SquareGrid(10, 10), new HashMap<Point, TowerDefence.Areas>(),false);
        instance.initialise();
        assertTrue(instance.blockedPoints.isEmpty());
        assertTrue(instance.routes.isEmpty());
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
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
        instance = new TowerDefence(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        instance.generateRoutes();
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of generateRoute method, of class TowerDefence.
     */
    @Test
    public void testGenerateRoute() {
        System.out.println("generateRoute");
        Point end = new Point(0,0);
        HashMap<Point, Integer> route = new HashMap<Point, Integer>();
        HashMap<Point, Integer> rhs = new HashMap<Point, Integer>();
        TowerDefence instance = new TowerDefence(new SquareGrid(10, 10), new HashMap<Point, TowerDefence.Areas>(),false);
        instance.initialise();
        instance.generateRoute(end, route, rhs);
        assertEquals(route.size(), 100);
    }

    /**
     * Test of updateRouteBlocked method, of class TowerDefence.
     */
    @Test
    public void testUpdateRouteBlocked() {
        System.out.println("generateRoutes");
        TowerDefence instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
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
        instance = new TowerDefence(new SquareGrid(10, 10), areas,false);
        instance.initialise();
        instance.generateRoutes();
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        MapUpdate<Point, Integer> update = instance.updateRouteBlocked(blocked, end, instance.routes.get(end), instance.rhss.get(end));
        update.update();
        blocked=new Point(4,7);
        instance.blockedPoints.add(blocked);
        update=instance.updateRouteBlocked(blocked, end, instance.routes.get(end), instance.rhss.get(end));
        update.update();
    }

    /**
     * Test of updateRoutesBlocked method, of class TowerDefence.
     */
    @Test
    public void testUpdateRoutesBlocked() {
        System.out.println("generateRoutes");
        TowerDefence instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
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
        instance = new TowerDefence(new SquareGrid(10, 10), areas);
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(blocked);
        for(Entry<Point, MapUpdate<Point, Integer>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        Point blocked2 = new Point(4,7);
        instance.blockedPoints.add(blocked2);
        update=instance.updateRoutesBlocked(blocked2);
        for(Entry<Point, MapUpdate<Point, Integer>> entry:update.entrySet()){
            entry.getValue().update();
        }
    }

    /**
     * Test of printDebug method, of class TowerDefence.
     * DOES NOTHING AS THIS IS DEBUG CODE THAT PRINTS STUFF
     */
    @Test
    public void testPrintDebug() {
        //not needed
    }

    /**
     * Test of updateRoutesUnblocked method, of class TowerDefence.
     */
    @Test
    public void testUpdateRoutesUnblocked() {
        System.out.println("generateRoutes");
        TowerDefence instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
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
        instance = new TowerDefence(new SquareGrid(10, 10), areas);
        
        Point blocked=new Point(4,2);
        instance.blockedPoints.add(blocked);
        Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(blocked);
        for(Entry<Point, MapUpdate<Point, Integer>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        Point blocked2 = new Point(4,7);
        instance.blockedPoints.add(blocked2);
        update=instance.updateRoutesBlocked(blocked2);
        for(Entry<Point, MapUpdate<Point, Integer>> entry:update.entrySet()){
            entry.getValue().update();
        }
        
        instance.blockedPoints.remove(blocked2);
        instance.updateRoutesUnblocked(blocked2);
        instance.blockedPoints.remove(blocked);
        instance.updateRoutesUnblocked(blocked);
    }

    /**
     * Test of updateRouteUnblocked method, of class TowerDefence.
     */
    @Test
    public void testUpdateRouteUnblocked() {
        System.out.println("updateRouteUnblocked");
        Point unblockedPoint = null;
        HashMap<Point, TwoItems<Integer, Set<Point>>> route = null;
        TowerDefence instance = null;
        //instance.updateRouteUnblocked(unblockedPoint, route);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextPoint method, of class TowerDefence.
     */
    @Test
    public void testGetNextPoint() {
        TowerDefence instance;
        HashMap<Point, Areas> areas=new HashMap<Point, TowerDefence.Areas>();
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

        areas.put(new Point(9,2), Areas.BLOCKED);

        areas.put(new Point(9,9), Areas.START);
        instance = new TowerDefence(new SquareGrid(10, 10), areas);
        
        Map<Point,Integer> choices=new HashMap<Point, Integer>();
        int n=10000;
        double error=Math.sqrt(n);
        Point prev=new Point(9, 9);
        Point curr=new Point(9, 8);
        Point next=null;
        System.out.println("Doing choices");
        for(int i=0;i<n;i++){
            next=instance.getNextPoint(end, curr, prev);
            if(choices.containsKey(next)){
                choices.put(next, choices.get(next)+1);
            }else{
                choices.put(next, 1);
            }
        }
        int total=0;
        for(Entry<Point, Integer> entry:choices.entrySet()){
            System.out.print(entry.getKey());
            System.out.println(entry.getValue());
            total+=entry.getValue();
        }
        assertEquals(total, n);
        assertTrue(choices.get(new Point(9, 7))>0.6*n-error);
        assertTrue(choices.get(new Point(9, 7))<0.6*n+error);
        
        curr=null;
        prev=null;
        next=new Point(9,9);
        Map<Point,Integer> path2=new HashMap<Point, Integer>();
        int i=0;
        System.out.println(instance.index);
        while(!end.equals(curr)){
            instance.printDebug(next, instance.routes.get(end), path2,path2);
            prev=curr;
            curr=next;
            next=instance.getNextPoint(end, curr, prev);
            path2.put(curr,i++);
            System.out.println(next);
        }
        ArrayList<Point> blockable = new ArrayList<Point>(Arrays.asList(new Point[]{new Point(8,2),new Point(0,2),new Point(4,2),new Point(4,7)}));
        ArrayList<Point> blocked=new ArrayList<Point>();
        
        curr=null;
        prev=null;
        next=new Point(9,9);
        path2.clear();

        for(int j=0;j<100;j++){
            if(Math.random()<0.5){
                if(blockable.isEmpty())
                    continue;
                Collections.shuffle(blockable);
                Point p=blockable.get(0);
                instance.blockedPoints.add(p);
                Map<Point, MapUpdate<Point, Integer>> update = instance.updateRoutesBlocked(p);
                if(update.get(end).removed(curr)||update.get(end).removed(new Point(9,9))){
                    instance.blockedPoints.remove(p);
                    continue;
                }
                update.get(end).update();
                blockable.remove(0);
                blocked.add(p);
            }else{
                if(blocked.isEmpty())
                    continue;
                Collections.shuffle(blocked);
                Point p=blocked.get(0);
                blocked.remove(0);
                blockable.add(p);
                instance.blockedPoints.remove(p);
                instance.updateRoutesUnblocked(p);
            }
            for(int k=0;k<4;k++){
                if(end.equals(curr)||curr==null){
                    curr=null;
                    prev=null;
                    next=new Point(9,9);
                    path2.clear();
                }else{
                    next=instance.getNextPoint(end, curr, prev);
                }
                System.out.println(next);
                prev=curr;
                curr=next;
                path2.put(curr,i++);
                instance.printDebug(curr, instance.routes.get(end), path2, path2);
            }
        }
    }
}
