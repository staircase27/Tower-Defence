/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.grid;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public interface Grid extends Iterable<Point> {
    public Point getSize();
    public boolean isValid(Point point);
    public Set<Point> getNeighbours(Point point);
    public Point2D.Double getPointLocation(Point point);
    public Point getPointAt(Point2D.Double location);
    public Iterator<Point> getAllPoints();
    public double getStrightness(Point previous, Point current, Point next);
}
