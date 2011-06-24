/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.grid.Grid;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Simon Armstrong
 */
public final class TowerDefence {

    public static enum Areas {
        START,END,BLOCKED,BOOST,RAISED
    }
    final Grid grid;
    HashMap<Point, BaseTower> towers = new HashMap<Point, BaseTower>();
    HashMap<Point, Areas> areas;
    HashSet<Point> blockedPoints;
    HashMap<Point, HashMap<Point, TwoItems<Integer, Set<Point>>>> routes;

    public TowerDefence(Grid grid,HashMap<Point,Areas> areas) {
        this.grid = grid;
        this.areas = areas;
        initialise();
        generateRoutes();
    }
    TowerDefence(Grid grid,HashMap<Point,Areas> areas,boolean hehe) {
        this.grid = grid;
        this.areas = areas;
        //initialise();
        //generateRoutes();
    }
    
    public void initialise(){
        blockedPoints=new HashSet<Point>();
        routes=new HashMap<Point, HashMap<Point, TwoItems<Integer, Set<Point>>>>();
        Point size = grid.getSize();
        for(Point point:grid){
            if((areas.containsKey(point) && ( areas.get(point)==Areas.BLOCKED || areas.get(point)==Areas.RAISED )) || towers.containsKey(point)){
                blockedPoints.add(point);
            }else if(areas.containsKey(point) && areas.get(point)==Areas.END){
                routes.put(point,new HashMap<Point, TwoItems<Integer, Set<Point>>>());
            }
        }
    }
    
    /**
     * 
     */
    public void generateRoutes(){
        for(Point end:routes.keySet()){
            generateRoute(end,routes.get(end));
        }
    }

    public Map<Point,MapUpdate<Point, TwoItems<Integer, Set<Point>>>> updateRoutesBlocked(Point blocked){
        Map<Point,MapUpdate<Point, TwoItems<Integer, Set<Point>>>> updates=new HashMap<Point, MapUpdate<Point, TwoItems<Integer, Set<Point>>>>();
        for(Point end:routes.keySet()){
            updates.put(end, updateRouteBlocked(blocked, routes.get(end)));
        }
        return updates;
    }

    public void updateRoutesUnblocked(Point unblocked){
        for(Point end:routes.keySet()){
            updateRouteUnblocked(unblocked,routes.get(end));
        }
    }
    
    Comparator<TwoItems<Integer,Point>> comparator=new Comparator<TwoItems<Integer, Point>>() {
        @Override
        public int compare(TwoItems<Integer, Point> o1, TwoItems<Integer, Point> o2) {
        int value=o1.getA().compareTo(o2.getA());
        if(value==0){
            value=o1.getB().x-o2.getB().x;
        }
        if(value==0){
            value=o1.getB().y-o2.getB().y;
        }
        return value;
        }
    };
    
    String filebase="out";
    int index=0;
    public void printDebug(Point hilight,Map<Point, TwoItems<Integer, Set<Point>>> route,Set<Point> closed,Set<TwoItems<Integer,Point>> open){
        if(!true){
            return;
        }
        try {
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter(filebase+index+".txt"));
            for(Point point:grid){
                if(!grid.isValid(point)){
                    System.out.println("YARG");
                }
               out.write(point.x+" "+point.y+"\t");
               int type=1;
               int distance=Integer.MAX_VALUE;
               if(route.containsKey(point)){
                   distance=route.get(point).getA();
               }
               if(blockedPoints.contains(point)){
                   type=0;
               }else if(closed!=null && closed.contains(point)){
                   type=3;
               }else if(open.contains(new TwoItems<Integer, Point>(distance,point))){
                   type=2;
               }
               if(point.x==hilight.x && point.y==hilight.y){
                   type+=3;
               }
               out.write(""+type+" ");
               out.write(distance+"\t");
               int i=0;
               if(route.containsKey(point)){
                   for(Point from:route.get(point).getB()){
                       i++;
                       out.write(from.x+" "+from.y+" ");
                   }
               }
               for(;i<4;i++){
                   out.write("-1 -1 ");
               }
               out.write("\n");
            }
            out.flush();
            out.close();
        } catch (IOException ex) {
            System.out.println("Oh dear");
        }
        index++;
    }
    
    public void generateRoute(Point end,HashMap<Point, TwoItems<Integer, Set<Point>>> route){
        //The set of points that have been finally processed
        HashSet<Point> closed=new HashSet<Point>();
        //The set of points that have provisional lengths and need processing
        SortedSet<TwoItems<Integer,Point>> open=new TreeSet<TwoItems<Integer, Point>>(comparator);
        //add the end point node to the data set
        route.put(end, new TwoItems<Integer, Set<Point>>(0, new HashSet<Point>()));
        open.add(new TwoItems<Integer, Point>(0, end));
        printDebug(end, route, closed, open);
        //while there are nodes to process
        while(!open.isEmpty()){
            //get the next one and remove from open and add to closed
            TwoItems<Integer, Point> current = open.first();
            open.remove(current);
            closed.add(current.getB());
            //for each neighbour
            for(Point point:grid.getNeighbours(current.getB())){
                //check if point is already processed
                if(!closed.contains(point) && !blockedPoints.contains(point)){
                    //get this points data and create if needed
                    TwoItems<Integer, Set<Point>> data = route.get(point);
                    if (data==null){
                        data=new TwoItems<Integer, Set<Point>>(Integer.MAX_VALUE, new HashSet<Point>());
                        route.put(point, data);
                    }
                    //check if this is an improvement or same or worse and update as needed
                    if(current.getA()+1<data.getA()){
                        open.remove(new TwoItems<Integer, Point>(data.getA(),point));
                        open.add(new TwoItems<Integer, Point>(current.getA()+1,point));
                        data.setA(current.getA()+1);
                        data.getB().clear();
                        data.getB().add(current.getB());
                    }else if(current.getA()+1==data.getA()){
                        data.getB().add(current.getB());
                    }
                }
            }
            printDebug(current.getB(), route, closed, open);
        }
    }
    
    public MapUpdate<Point, TwoItems<Integer, Set<Point>>> updateRouteBlocked(Point blockedPoint,HashMap<Point, TwoItems<Integer, Set<Point>>> route){
        //records the updates to the route so can choose to accept or reject
        MapUpdate<Point, TwoItems<Integer, Set<Point>>> routeUpdate = new MapUpdate<Point, TwoItems<Integer, Set<Point>>>(route);
        Map<Point, TwoItems<Integer, Set<Point>>> newRoute = routeUpdate.getMap();
        
        Point end;
        Iterator<Entry<Point, TwoItems<Integer, Set<Point>>>> it = newRoute.entrySet().iterator();
        while(it.hasNext()){
            Entry<Point, TwoItems<Integer, Set<Point>>> entry = it.next();
            if(entry.getValue().getA()==0)
                end=entry.getKey();
            it.remove();
        }
        generateRoute(blockedPoint, route);
        
        //nodes to process
//        SortedSet<TwoItems<Integer,Point>> open=new TreeSet<TwoItems<Integer, Point>>(comparator);
//        //update the removed node and add to list of nodes to process
//        newRoute.remove(blockedPoint);
//        open.add(new TwoItems<Integer, Point>(Integer.MAX_VALUE, blockedPoint));
//        //while nodes to process next node is processed
//        printDebug(blockedPoint, newRoute, null, open);
//        while(!open.isEmpty()){
//            TwoItems<Integer, Point> next = open.first();
//            open.remove(next);
////            System.out.println(next);
//            // if length is still a valid length
//            if (){
//            //for each neighbour
//                for(Point neighbour:grid.getNeighbours(next.getB())){
//                    //if it is not blocked (same as has a route to as no extra nodes will be acessable)
//                    if(!blockedPoints.contains(neighbour)){
//                        //find the current data for the node
//                        TwoItems<Integer, Set<Point>> data = newRoute.get(neighbour);
//                        //remove this node from the routes to the neighbour as it is no longer giving the same length
//                        if(data!=null)
//                            data.getB().remove(next.getB());
//                        //if there are no routes to this neighbour of the correct length
//    //                    System.out.print(neighbour);
//    //                    System.out.print(data);
//                        if(data==null||data.getB().isEmpty()){
//                            //find the current best options
//                            Set<Point> bestFrom=new HashSet<Point>();
//                            int distance=Integer.MAX_VALUE-1;
//                            //for each potential best neighbour
//    //                        System.out.print(" - ");
//                            for(Point from:grid.getNeighbours(neighbour)){
//                                //check if a cell has a route to it, currently has this cell as it's only route to it and 
//                                //it gives a route that is either better or as good as current
//    //                            System.out.print(from);
//    //                            if(newRoute.containsKey(from))
//    //                                System.out.print(newRoute.get(from).getA());
//    //                            System.out.print(" ");
//                                if(newRoute.containsKey(from) && (!newRoute.get(from).getB().contains(neighbour)||newRoute.get(from).getB().size()!=1) && distance>=newRoute.get(from).getA()){
//                                    if(distance>newRoute.get(from).getA()){
//                                        bestFrom.clear();
//                                        distance=newRoute.get(from).getA();
//                                    }
//                                    bestFrom.add(from);
//                                }
//                            }
//    //                        System.out.print(" - ");
//    //                        System.out.print(distance+1);
//                            if((data==null&&distance+1!=Integer.MAX_VALUE)||(data!=null&&distance+1!=data.getA())){
//                                //remove current entry in open set if there is one
//                                if(data!=null){
//                                    open.remove(new TwoItems<Integer, Point>(newRoute.get(neighbour).getA(),neighbour));
//                                }else{
//                                    open.remove(new TwoItems<Integer, Point>(-1,neighbour));
//                                }
//                                //if didn't find a route remove the route to this cell
//                                if(distance==Integer.MAX_VALUE-1){
//                                    newRoute.remove(neighbour);
//                                    distance=-2;
//                                //else put the new route in the table;
//                                }else{
//                                    newRoute.put(neighbour, new TwoItems<Integer, Set<Point>>(distance+1, bestFrom));
//                                }
//                                for(Point from:bestFrom){
//                                    newRoute.get(from).getB().remove(neighbour);
//                                }
//                                //and add to the open nodes list
//                                open.add(new TwoItems<Integer, Point>(distance+1,neighbour));
//                            }
//                        }
//    //                    System.out.println();
//                    }
//                }
//            }else{
//                
//            }
//            printDebug(next.getB(), newRoute, null, open);
//        }
        return routeUpdate;
    }
    
    public void updateRouteUnblocked(Point unblockedPoint,HashMap<Point, TwoItems<Integer, Set<Point>>> route){
        //nodes to process
        SortedSet<TwoItems<Integer,Point>> open=new TreeSet<TwoItems<Integer, Point>>(comparator);
        printDebug(unblockedPoint, route, null, open);
        int distance=Integer.MAX_VALUE-1;
        Set<Point> bestFrom=new HashSet<Point>();
        for(Point from:grid.getNeighbours(unblockedPoint)){
            if(route.containsKey(from)&&route.get(from).getA()<=distance){
                if(route.get(from).getA()<=distance){
                    distance=route.get(from).getA();
                    bestFrom.clear();
                }
                bestFrom.add(from);
            }
        }
        route.put(unblockedPoint,new TwoItems<Integer, Set<Point>>(distance+1, bestFrom));
        open.add(new TwoItems<Integer, Point>(distance+1, unblockedPoint));
        printDebug(unblockedPoint, route, null, open);
        while(!open.isEmpty()){
            TwoItems<Integer, Point> next = open.first();
            open.remove(next);
            for(Point neighbour:grid.getNeighbours(next.getB())){
                if(!blockedPoints.contains(neighbour)){
                    TwoItems<Integer, Set<Point>> data=route.get(neighbour);
                    if(data==null||data.getA()>next.getA()+1){
                        if(data!=null)
                            open.remove(new TwoItems<Integer, Point>(data.getA(), neighbour));
                        bestFrom=new HashSet<Point>();
                        bestFrom.add(next.getB());
                        route.put(neighbour, new TwoItems<Integer, Set<Point>>(next.getA()+1,bestFrom));
                        open.add(new TwoItems<Integer, Point>(next.getA()+1, neighbour));
                    }else if(data.getA()==next.getA()+1){
                        data.getB().add(next.getB());
                    }
                }
            }
            printDebug(next.getB(), route, null, open);
        }
    }
}
