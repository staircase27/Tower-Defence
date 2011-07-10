/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import java.util.Iterator;
import java.util.TreeSet;
import com.staircase27.TD.lib.Towers.BaseTower;
import java.awt.Point;
import java.awt.geom.Point2D;
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
    
    @Test
    public void testIntersectionCode(){
        double theta0;
        double theta1=Math.random()*Math.PI*2;
        Point2D.Double point0;
        Point2D.Double point1=new Point2D.Double(Math.random()*6, Math.random()*6);
        for(int i=0;i<10000;i++){
            theta0=theta1;
            theta1=theta0+Math.random()*Math.PI/4;
            point0=point1;
            point1=new Point2D.Double(Math.random()*6, Math.random()*6);
            findIntersectionCode(theta0, theta1, point0, point1, 5, 0.5, i);
        }
    }
    
    public void findIntersectionCode(double theta0,double theta1,Point2D.Double point0,Point2D.Double point1, double length,double radius,int time){
        TreeSet<Double> intersections=new TreeSet<Double>();
        double intersection;
        double determinant;
        double value;
        double a1,b1,c1;
        double a2,b2,c2;
        a1=(Math.cos(theta1)-Math.cos(theta0))*(point1.x-point0.x)+(Math.sin(theta1)-Math.sin(theta0))*(point1.y-point0.y);
        a2=(Math.cos(theta1)-Math.cos(theta0))*(point1.y-point0.y)-(Math.sin(theta1)-Math.sin(theta0))*(point1.x-point0.x);
        b1=Math.cos(theta0)*(point1.x-point0.x)+Math.sin(theta0)*(point1.y-point0.y)+
           (Math.cos(theta1)-Math.cos(theta0))*point0.x+(Math.sin(theta1)-Math.sin(theta0))*point0.y;
        b2=Math.cos(theta0)*(point1.y-point0.y)-Math.sin(theta0)*(point1.x-point0.x)+
           (Math.cos(theta1)-Math.cos(theta0))*point0.y-(Math.sin(theta1)-Math.sin(theta0))*point0.x;
        c1=Math.cos(theta0)*point0.x+Math.sin(theta0)*point0.y;
        c2=Math.cos(theta0)*point0.y-Math.sin(theta0)*point0.x;
        //bottom intersections
        determinant=b1*b1-4*a1*(c1-0);
        if(determinant>0){
            intersection=(-b1+Math.sqrt(determinant))/(2*a1);
            value=a2*intersection*intersection+b2*intersection+c2;
            if(value>=-radius&&value<=radius){
                intersections.add(intersection);
            }
            intersection=(-b1-Math.sqrt(determinant))/(2*a1);
            value=a2*intersection*intersection+b2*intersection+c2;
            if(value>=-radius&&value<=radius){
                intersections.add(intersection);
            }
        }
        //top intersections
        determinant=b1*b1-4*a1*(c1-length);
        if(determinant>0){
            intersection=(-b1+Math.sqrt(determinant))/(2*a1);
            value=a2*intersection*intersection+b2*intersection+c2;
            if(value>=-radius&&value<=radius){
                intersections.add(intersection);
            }
            intersection=(-b1-Math.sqrt(determinant))/(2*a1);
            value=a2*intersection*intersection+b2*intersection+c2;
            if(value>=-radius&&value<=radius){
                intersections.add(intersection);
            }
        }
        //left intersections
        determinant=b2*b2-4*a2*(c2-radius);
        if(determinant>0){
            intersection=(-b2+Math.sqrt(determinant))/(2*a2);
            value=a1*intersection*intersection+b1*intersection+c1;
            if(value>=0&&value<=length){
                intersections.add(intersection);
            }
            intersection=(-b2-Math.sqrt(determinant))/(2*a2);
            value=a1*intersection*intersection+b1*intersection+c1;
            if(value>=0&&value<=length){
                intersections.add(intersection);
            }
        }
        //right intersections
        determinant=b2*b2-4*a2*(c2+radius);
        if(determinant>0){
            intersection=(-b2+Math.sqrt(determinant))/(2*a2);
            value=a1*intersection*intersection+b1*intersection+c1;
            if(value>=0&&value<=length){
                intersections.add(intersection);
            }
            intersection=(-b2-Math.sqrt(determinant))/(2*a2);
            value=a1*intersection*intersection+b1*intersection+c1;
            if(value>=0&&value<=length){
                intersections.add(intersection);
            }
        }

        assertEquals(intersections.size()%2,0);

        int i=0;
        Iterator<Double> it=intersections.iterator();
        for(;it.hasNext();i++){
            intersection=it.next();
            if(0<=intersection&&intersection<=1){
                System.out.print(i%2);
                System.out.print(" ");
                System.out.println(time+intersection);
            }
        }
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
