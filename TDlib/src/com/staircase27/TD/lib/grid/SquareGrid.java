/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.grid;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public class SquareGrid implements Grid{

    private final int xSize;
    private final int ySize;
    
    public SquareGrid(int xSize,int ySize){
        this.xSize=xSize;
        this.ySize=ySize;
    }
    
    @Override
    public Point getSize() {
        return new Point(xSize, ySize);
    }

    @Override
    public boolean isValid(Point point) {
        return point.x<xSize && point.y<ySize && point.x>=0 && point.y>=0;
    }

    @Override
    public Set<Point> getNeighbours(Point point) {
        Set<Point> points=new HashSet<Point>();
        Point newPoint;
        newPoint=new Point(point.x+1,point.y);
        if(isValid(newPoint)) points.add(newPoint);
        newPoint=new Point(point.x-1,point.y);
        if(isValid(newPoint)) points.add(newPoint);
        newPoint=new Point(point.x,point.y+1);
        if(isValid(newPoint)) points.add(newPoint);
        newPoint=new Point(point.x,point.y-1);
        if(isValid(newPoint)) points.add(newPoint);
        return points;
    }

    @Override
    public Double getPointLocation(Point point) {
        return new Point2D.Double((point.x+0.5)/xSize,(point.y+0.5)/ySize);
    }

    @Override
    public Point getPointAt(Double location) {
        int x=(int) Math.round(location.x*xSize-0.5);
        int y=(int) Math.round(location.y*ySize-0.5);
        return new Point(x,y);
    }

    @Override
    public Iterator<Point> getAllPoints() {
        return new AllPointsIterator();
    }

    @Override
    public Iterator<Point> iterator() {
        return getAllPoints();
    }
    
    private class AllPointsIterator implements Iterator<Point>{

        int x=0;
        int y=0;
        
        @Override
        public boolean hasNext() {
            if(x==xSize){
                x=0;y++;
            }
            return x<xSize&&y<ySize;
        }

        @Override
        public Point next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return new Point(x++,y);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    } 
    
}
