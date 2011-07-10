/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.Towers.AreaTowerInterface;
import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.Waves.Wave;
import com.staircase27.TD.lib.Waves.Waves;
import com.staircase27.TD.lib.grid.Grid;
import com.staircase27.TD.lib.lib.Pair;
import com.staircase27.TD.lib.lib.TwoItems;
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
    final Waves waves;
    HashMap<Point, Set<Point>> endsMap;
    HashMap<Point, Set<Point>> startsMap;
    HashMap<Point, Areas> areas;
    Routing routing;
    HashMap<Point, BaseTower> towers = new HashMap<Point, BaseTower>();
    HashMap<Point,HashSet<BaseEnemy>> enemies= new HashMap<Point, HashSet<BaseEnemy>>();
    private int lives;
    private boolean playing=false;
    private int energy=0;
    private int waveIndex=-1;
    private double nextWaveDelay=0;
    private Wave wave;

    public TowerDefence(Grid grid, Waves waves, HashMap<Point, Areas> areas, int initalEnergy, int lives) {
        this.grid = grid;
        this.waves = waves;
        wave=waves.getWaves().get(0);
        this.areas = areas;
        this.energy=initalEnergy;
        this.lives = lives;
        initialise();
        routing.generateRoutes();
    }

    TowerDefence(Grid grid, Waves waves, HashMap<Point, Areas> areas, int energy, int livesLeft, boolean playing, int waveIndex, double nextWaveDelay) {
        this(grid, waves, areas,energy,livesLeft);
        this.waveIndex=waveIndex;
        wave=waves.getWaves().get(waveIndex);
        this.nextWaveDelay=nextWaveDelay;
        this.playing=playing;
    }

    void initialise() {
        HashSet<Point> blockedPoints = new HashSet<Point>();
        for (Point point : grid) {
            if ((areas.containsKey(point) && (areas.get(point) == Areas.BLOCKED || areas.get(point) == Areas.RAISED)) || towers.containsKey(point)) {
                blockedPoints.add(point);
            }
        }
        startsMap = new HashMap<Point, Set<Point>>();
        endsMap = new HashMap<Point, Set<Point>>();
        for(Pair<Point> route:waves.getAllRoutes()){
            if(!endsMap.containsKey(route.getB())){
                endsMap.put(route.getB(), new HashSet<Point>());
            }
            endsMap.get(route.getB()).add(route.getA());
            if(!startsMap.containsKey(route.getA())){
                startsMap.put(route.getA(), new HashSet<Point>());
            }
            startsMap.get(route.getA()).add(route.getB());
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
            for(BaseEnemy enemy:enemies.get(end)){
                 if (update.removed(enemy.getFrom())||update.removed(enemy.getTo())){
                     return false;
                 }
            }
        }
        if (update == null) {
            return false;
        }
        update.update();
        towers.put(point, tower);
        if(tower instanceof AreaTowerInterface){
            ((AreaTowerInterface)tower).activateTower(null, null);
        }
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
    
    public void spawnEnemy(BaseEnemy enemy,Point start){
        Point target=enemy.getTarget();
        if(! endsMap.get(target).contains(start))
            throw new IllegalArgumentException(start+" is not a valid start point for an enemy with target "+target);
        enemies.get(target).add(enemy);
        enemy.setTo(start);
        enemy.setLocation(grid.getPointLocation(start));
    }
    
    public void update(double timeStep){
        if(playing){
            //waves
            double waveTimeLeft=timeStep;
            while(waveTimeLeft>0){
                if(nextWaveDelay>0){
                    nextWaveDelay-=timeStep;
                    waveTimeLeft=-nextWaveDelay;
                }else{
                    waveTimeLeft=wave.getWaveElement().update(this, waveTimeLeft);
                    if(waveTimeLeft>0){
                        waveIndex++;
                        wave=waves.getWaves().get(waveIndex);
                        nextWaveDelay=wave.getDelay();
                    }
                }
            }

            //calculate the moves for all the enemies
            Set<BaseEnemy> finishedEnemies=new HashSet<BaseEnemy>();
            for(Point end:enemies.keySet()){
                for (BaseEnemy enemy:enemies.get(end)){
                    if(enemy.updateMove(this, timeStep))
                        finishedEnemies.add(enemy);
                }
            }

            //TODO all other update code
            //calculate bullet movements and do bullet damage
            
            //calculate laser movements
            
            //calculate laser hits
            
            //get enemies to apply damage

            //handle enemies that have moved to their target point.
            for(BaseEnemy enemy:finishedEnemies){
                if(enemies.get(enemy.getTarget()).remove(enemy)){
                    lives--;
                }
            }
        }
    }
}
