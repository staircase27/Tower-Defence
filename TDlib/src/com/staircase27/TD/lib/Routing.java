/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.lib.Updatable.MapUpdate;
import com.staircase27.TD.lib.grid.Grid;
import com.staircase27.TD.lib.lib.TwoItems;
import com.staircase27.TD.lib.lib.Updatable.SetUpdate;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements the incremental routing calculations for the tower defence.
 * The algorithm used is D* Lite, optimised version (Fig 4), from Proceedings 
 * of the AAAI Conference on Artificial Intelligence (AAAI), pages 476-483, 2002
 * by S. Koenig and M. Likhachev. <a href="http://www.aaai.org/Papers/AAAI/2002/AAAI02-072.pdf">
 * http://www.aaai.org/Papers/AAAI/2002/AAAI02-072.pdf</a> or 
 * <a href="http://idm-lab.org/bib/abstracts/papers/aaai02b.pdf">
 * http://idm-lab.org/bib/abstracts/papers/aaai02b.pdf</a>
 * 
 * This class doesn't use a heuristics and iterates till all possible cells have 
 * their routes calculated.
 * 
 * It implements the open set using both a HashMap and a Sorted Set. When a point
 * is added to the open set it is added to both the map and sorted set. When
 * a point is updated or removed it is not removed from the list but only updated
 * in the map and when a point is removed from sorted set it is checked against 
 * the map to see if it is a valid entry. This idea is copied from 
 * <a href="http://code.google.com/p/dstarlite/">
 * http://code.google.com/p/dstarlite/</a>
 * 
 * The route selection is at random from all the shortest paths with a higher
 * propability for routes nearer to straight on.
 * @author Simon Armstrong
 */
public final class Routing {

    /**
     * creates a new routing object.
     * 
     * @param grid the grid we are travelling around
     * @param blockedPoints the set of points that are blocked at the start
     * @param ends the set of all the end points
     */
    public Routing(Grid grid, HashSet<Point> blockedPoints, Set<Point> ends) {
        this.grid = grid;
        this.blockedPoints = blockedPoints;
        routes = new HashMap<Point, HashMap<Point, Integer>>();
        rhss = new HashMap<Point, HashMap<Point, Integer>>();
        for (Point end : ends) {
            routes.put(end, new HashMap<Point, Integer>());
            rhss.put(end, new HashMap<Point, Integer>());
        }
    }
    /**
     * The grid this routing uses
     */
    public final Grid grid;
    /**The set of blocked point. This is updated while the code runs*/
    HashSet<Point> blockedPoints;
    /**A Map of the rhs values for all the points. Infinity is represented by not being present in the map*/
    HashMap<Point, HashMap<Point, Integer>> rhss;
    /**A Map of the g values for all the points. Infinity is represented by not being present in the map*/
    HashMap<Point, HashMap<Point, Integer>> routes;

    /**
     * generate the initial routes for the end points and blocked points on the grid for this routing object.
     */
    public void generateRoutes() {
        for (Point end : routes.keySet()) {
            generateRoute(end, routes.get(end), rhss.get(end));
        }
    }

    /**
     * Calculate the new routes and return changes to be either accepted or rejected.
     * 
     * NOTE: The returned map updates are all linked together!
     * @param blocked the point that is being blocked
     * @return A Map of the MapUpdate Objects for the updated routes. 
     */
    public Map<Point, MapUpdate<Point, Integer>> updateRoutesBlocked(Point blocked) {
        SetUpdate<Point> blockedPointsUpdate = new SetUpdate<Point>(blockedPoints);
        Set<Point> newBlockedPoints = blockedPointsUpdate.getView();
        newBlockedPoints.add(blocked);
        Map<Point, MapUpdate<Point, Integer>> updates = new HashMap<Point, MapUpdate<Point, Integer>>();
        for (Point end : routes.keySet()) {
            updates.put(end, updateRouteBlocked(blocked, newBlockedPoints, end, routes.get(end), rhss.get(end)));
            blockedPointsUpdate.link(updates.get(end));
        }
        return updates;
    }

    /**
     * calculate the new routes.
     * @param unblocked the point that is being unblocked
     */
    public void updateRoutesUnblocked(Point unblocked) {
        blockedPoints.remove(unblocked);
        for (Point end : routes.keySet()) {
            updateRouteUnblocked(unblocked, end, routes.get(end), rhss.get(end));
        }
    }
    
    /**A random number generator for route choosing*/
    private Random rand = new Random();

    /**
     * Get the next point in the route from current to target.
     * 
     * This chooses a point at random from all the shortest routes.
     * The probability of each next point is weighted by how close the next 
     * point is to going straight on if there is a previous point or even choice
     * otherwise. The straightness of the route is measured using 
     * {@link Grid#getStrightness(java.awt.Point, java.awt.Point, java.awt.Point)}.
     * The Random choice is done by a {@link Random} object in this class.
     * @param target the point we are trying to get to
     * @param current the current position
     * @param previous the previous position to use for weighting
     * @return the next point to travel to.
     */
    public Point getNextPoint(Point target, Point current, Point previous) {
        List<TwoItems<Double, Point>> nexts = new LinkedList<TwoItems<Double, Point>>();
        HashMap<Point, Integer> route = routes.get(target);
        Integer distance = route.get(current);
        double total = 0;
        for (Point n : grid.getNeighbours(current)) {
            if (route.containsKey(n) && route.get(n) == distance - 1) {
                if (previous == null) {
                    total += 1;
                } else {
                    total += 2 + grid.getStrightness(previous, current, n);
                }
                nexts.add(new TwoItems<Double, Point>(total, n));
            }
        }
        double choice = rand.nextDouble() * total;
        Iterator<TwoItems<Double, Point>> it = nexts.iterator();
        while (it.hasNext()) {
            TwoItems<Double, Point> next = it.next();
            if (next.getA() > choice) {
                return next.getB();
            }
        }
        return null;
    }
    
    /**a flag to enable debuging*/
    boolean DEBUG = true;
    /**the base filename for the debug output*/
    String filebase = "out_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS").format(new Date());
    /**the output index which is appended to the file name for debug output*/
    int index = 0;

    /**
     * outputs the data from the route planning to a file as:
     * x y \t type \t distance \t rhs
     * where type is bit flags for blocked points (1), open set (2) and hilight (3)
     * or whatever is passed as those arguments.
     * @param hilight a point to hilight
     * @param route the route values
     * @param rhs the rhs values
     * @param U the open set
     */
    void printDebug(char key, Point hilight, Map<Point, Integer> route, Map<Point, Integer> rhs, Map<Point, Integer> U) {
        printDebug(key, Collections.singleton(hilight), route, rhs, U);
    }

    /**
     * outputs the data from the route planning to a file as:
     * x y \t type \t distance \t rhs
     * where type is bit flags for blocked points (1), open set (2) and hilight (3)
     * or whatever is passed as those arguments.
     * @param hilight points to hilight
     * @param route the route values
     * @param rhs the rhs values
     * @param U the open set
     */
    void printDebug(char key, Set<Point> hilight, Map<Point, Integer> route, Map<Point, Integer> rhs, Map<Point, Integer> U) {
        if (!DEBUG) {
            return;
        }
        try {
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter(filebase + "_" + index + ".tdd"));
            out.write(key);
            out.write("\n");
            for (Point point : grid) {
                if (!grid.isValid(point)) {
                    System.out.println("YARG");
                }
                out.write(point.x + " " + point.y + "\t");
                int type = 0;
                if (blockedPoints.contains(point)) {
                    type += 1;
                }
                if (U.containsKey(point)) {
                    type += 2;
                }
                if (hilight.contains(point)) {
                    type += 4;
                }
                out.write("" + type + " ");
                int distance = Integer.MAX_VALUE;
                if (route.containsKey(point)) {
                    distance = route.get(point);
                }
                out.write(distance + "\t");
                distance = Integer.MAX_VALUE;
                if (rhs.containsKey(point)) {
                    distance = rhs.get(point);
                }
                out.write(distance + "\t");
                out.write("\n");
            }
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("Oh dear");
        }
        index++;
    }
    /**
     * A comparator for the entries in the open set.
     * 
     * Sorts points by key integer and then x coordinate then y coordinate
     */
    public static final Comparator<TwoItems<Integer, Point>> comparator = new Comparator<TwoItems<Integer, Point>>() {

        @Override
        public int compare(TwoItems<Integer, Point> o1, TwoItems<Integer, Point> o2) {
            int value = o1.getA().compareTo(o2.getA());
            if (value == 0) {
                value = o1.getB().x - o2.getB().x;
            }
            if (value == 0) {
                value = o1.getB().y - o2.getB().y;
            }
            return value;
        }
    };
    /**The sorted set part of the open set implementation*/
    private TreeSet<TwoItems<Integer, Point>> Ul = new TreeSet<TwoItems<Integer, Point>>(comparator);
    /**The map part of the open set implementation*/
    private HashMap<Point, Integer> Um = new HashMap<Point, Integer>();

    /**
     * Updates a points value in the open set
     * @param route the g values
     * @param rhs the rhs values
     * @param Ul the sorted set part of the open set
     * @param Um the map part of the open set
     * @param p the point to update
     */
    private void UpdateVertex(Map<Point, Integer> route, Map<Point, Integer> rhs, TreeSet<TwoItems<Integer, Point>> Ul, Map<Point, Integer> Um, Point p) {
        if (route.containsKey(p)) {
            if ((!rhs.containsKey(p)) || route.get(p) != rhs.get(p)) {
                int k = route.get(p);
                if (rhs.containsKey(p) && rhs.get(p) < k) {
                    k = rhs.get(p);
                }
                Ul.add(new TwoItems<Integer, Point>(k, p));
                Um.put(p, k);
            } else {
                Um.remove(p);
            }
        } else {
            if (rhs.containsKey(p)) {
                int k = rhs.get(p);
                Ul.add(new TwoItems<Integer, Point>(k, p));
                Um.put(p, k);
            } else {
                Um.remove(p);
            }
        }
    }

    /**
     * Calculates the paths.
     * @param p_end the end point the paths are being calculated for.
     * @param blockedPoints the points that are currently blocked
     * @param route the g values
     * @param rhs the rhs values
     * @param Ul the sorted set part of the open set
     * @param Um the map part of the open set
     */
    private void ComputePath(Point p_end, Set<Point> blockedPoints, Map<Point, Integer> route, Map<Point, Integer> rhs, TreeSet<TwoItems<Integer, Point>> Ul, Map<Point, Integer> Um) {
        while (!Ul.isEmpty()) {
            TwoItems<Integer, Point> entry = Ul.pollFirst();
            if (!(Um.containsKey(entry.getB()) && Um.get(entry.getB()) == entry.getA())) {
                continue;
            }
            Point p = entry.getB();
            int k_old = entry.getA();
            Um.remove(p);

            int k_new = Integer.MAX_VALUE;
            if (route.containsKey(p)) {
                k_new = route.get(p);
            }
            if (rhs.containsKey(p) && rhs.get(p) < k_new) {
                k_new = rhs.get(p);
            }
            if (k_old < k_new) {
                Ul.add(new TwoItems<Integer, Point>(k_new, p));
                Um.put(p, k_new);
            } else if (((!route.containsKey(p)) && rhs.containsKey(p)) || (route.containsKey(p) && rhs.containsKey(p) && route.get(p) > rhs.get(p))) {
                route.put(p, rhs.get(p));
                for (Point n : grid.getNeighbours(p)) {
                    if (n != p_end && !blockedPoints.contains(n)) {
                        if (!(rhs.containsKey(n) && rhs.get(n) < 1 + route.get(p))) {
                            rhs.put(n, 1 + route.get(p));
                        }
                    }
                    UpdateVertex(route, rhs, Ul, Um, n);
                }
            } else {
                int g_old = Integer.MAX_VALUE;
                if (route.containsKey(p)) {
                    g_old = route.get(p);
                }
                route.remove(p);
                for (Point n : grid.getNeighbours(p)) {
                    if (blockedPoints.contains(n)) {
                        rhs.remove(n);
                    } else {
                        if ((g_old == Integer.MAX_VALUE && !rhs.containsKey(n)) || g_old != Integer.MAX_VALUE && rhs.containsKey(n) && rhs.get(n) == 1 + g_old) {
                            int new_rhs = Integer.MAX_VALUE;
                            for (Point nn : grid.getNeighbours(n)) {
                                if (route.containsKey(nn) && 1 + route.get(nn) < new_rhs) {
                                    new_rhs = 1 + route.get(nn);
                                }
                            }
                            if (new_rhs == Integer.MAX_VALUE) {
                                rhs.remove(n);
                            } else {
                                rhs.put(n, new_rhs);
                            }
                        }
                    }
                    UpdateVertex(route, rhs, Ul, Um, n);
                }
                int new_rhs = Integer.MAX_VALUE;
                for (Point nn : grid.getNeighbours(p)) {
                    if (route.containsKey(nn) && !blockedPoints.contains(p) && 1 + route.get(nn) < new_rhs) {
                        new_rhs = 1 + route.get(nn);
                    }
                }
                if (new_rhs == Integer.MAX_VALUE) {
                    rhs.remove(p);
                } else {
                    rhs.put(p, new_rhs);
                }
                UpdateVertex(route, rhs, Ul, Um, p);
            }
            printDebug('0', p, route, rhs, Um);
        }
    }

    /**
     * Calculates the route for an end point.
     * 
     * Used by {@link #generateRoutes()}.
     * @param p_end the end point we are calculating routes for
     * @param route the g values for this end point
     * @param rhs the rhs values for this point
     */
    void generateRoute(Point p_end, Map<Point, Integer> route, Map<Point, Integer> rhs) {
        rhs.put(p_end, 0);
        Ul.add(new TwoItems<Integer, Point>(0, p_end));
        Um.put(p_end, 0);
        printDebug('0', p_end, route, rhs, Um);
        ComputePath(p_end, blockedPoints, route, rhs, Ul, Um);
    }

    /**
     * Updates a route when a point is blocked for an end point and returns a 
     * MapUpdate so the new route could be accepted or rejected.
     * 
     * Used by {@link #updateRoutesBlocked(java.awt.Point)}.
     * @param p_end the end point we are calculating routes for
     * @param route the g values for this end point
     * @param rhs the rhs values for this point
     * @param blockedPoint the point that is blocked
     * @param newBlockedPoints the new blockedPoints 
     * @return the MapUpdate to be either rejected or accepted
     */
    MapUpdate<Point, Integer> updateRouteBlocked(Point blockedPoint, Set<Point> newBlockedPoints, Point p_end, Map<Point, Integer> route, Map<Point, Integer> rhs) {
        //records the updates to the route so can choose to accept or reject
        MapUpdate<Point, Integer> routeUpdate = new MapUpdate<Point, Integer>(route);
        Map<Point, Integer> newRoute = routeUpdate.getView();
        MapUpdate<Point, Integer> rhsUpdate = new MapUpdate<Point, Integer>(rhs, routeUpdate);
        Map<Point, Integer> newRhs = rhsUpdate.getView();
        newRhs.remove(blockedPoint);
        UpdateVertex(newRoute, newRhs, Ul, Um, blockedPoint);
        printDebug('0', blockedPoint, newRoute, newRhs, Um);
        ComputePath(p_end, newBlockedPoints, newRoute, newRhs, Ul, Um);
        return routeUpdate;
    }

    /**
     * Updates a route when a point is unblocked for an end point.
     * 
     * The blocked points set must be updated before this is called.
     * Used by {@link #updateRoutesUnblocked(java.awt.Point)}
     * @param unblockedPoint the unblocked point
     * @param p_end the end point to update the routes for
     * @param route the g values
     * @param rhs the rhs values
     */
    void updateRouteUnblocked(Point unblockedPoint, Point p_end, Map<Point, Integer> route, Map<Point, Integer> rhs) {
        int new_rhs = Integer.MAX_VALUE;
        for (Point n : grid.getNeighbours(unblockedPoint)) {
            if (route.containsKey(n) && new_rhs > 1 + route.get(n)) {
                new_rhs = 1 + route.get(n);
            }
        }
        if (new_rhs == Integer.MAX_VALUE) {
            rhs.remove(unblockedPoint);
        } else {
            rhs.put(unblockedPoint, new_rhs);
        }
        UpdateVertex(route, rhs, Ul, Um, unblockedPoint);
        printDebug('0', unblockedPoint, route, rhs, Um);
        ComputePath(p_end, blockedPoints, route, rhs, Ul, Um);
    }
}
