/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.Towers.BaseTower;
import java.awt.Point;
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
     * Test of initialise method, of class TowerDefence.
     */
    @Test
    public void testInitialise() {
        System.out.println("initialise");
        TowerDefence instance = null;
        instance.initialise();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextPoint method, of class TowerDefence.
     */
    @Test
    public void testGetNextPoint() {
        System.out.println("getNextPoint");
        Point target = null;
        Point curr = null;
        Point prev = null;
        TowerDefence instance = null;
        Point expResult = null;
        Point result = instance.getNextPoint(target, curr, prev);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTower method, of class TowerDefence.
     */
    @Test
    public void testAddTower() {
        System.out.println("addTower");
        BaseTower tower = null;
        Point point = null;
        TowerDefence instance = null;
        boolean expResult = false;
        boolean result = instance.addTower(tower, point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of replaceTower method, of class TowerDefence.
     */
    @Test
    public void testReplaceTower() {
        System.out.println("replaceTower");
        BaseTower tower = null;
        Point point = null;
        TowerDefence instance = null;
        BaseTower expResult = null;
        BaseTower result = instance.replaceTower(tower, point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeTower method, of class TowerDefence.
     */
    @Test
    public void testRemoveTower() {
        System.out.println("removeTower");
        Point point = null;
        TowerDefence instance = null;
        BaseTower expResult = null;
        BaseTower result = instance.removeTower(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
