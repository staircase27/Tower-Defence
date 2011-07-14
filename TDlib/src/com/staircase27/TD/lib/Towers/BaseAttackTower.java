/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.TowerDefence;
import com.staircase27.TD.lib.Towers.Tracking.Tracking;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseAttackTower extends DamagingTower {

    private final double baseRate;
    private double rate;
    private final double baseRange;
    private double range;

    public BaseAttackTower(Point location, double baseRate, double baseRange, double baseDamage) {
        this(location,baseRate, baseRange, baseDamage, DamageTarget.HP, DamageShieldType.DEFAULT);
    }

    public BaseAttackTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget) {
        this(location,baseRate, baseRange, baseDamage, damageTarget, DamageShieldType.DEFAULT);
    }

    public BaseAttackTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget,
            DamageShieldType damageShieldType) {
        super(location,baseDamage, damageTarget, damageShieldType);
        this.rate = this.baseRate = baseRate;
        this.range = this.baseRange = baseRange;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void adjustRate(boolean increase, double factor) {
        if (increase) {
            setRate(getRate() * (1 + factor));
        } else {
            setRate(getRate() / (1 + factor));
        }
    }

    public double getBaseRange() {
        return baseRange;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void adjustRange(boolean increase, double factor) {
        if (increase) {
            setRange(getRange() * (1 + factor));
        } else {
            setRange(getRange() / (1 + factor));
        }
    }
    
    private Tracking tracking;
    private Tracking.Stability trackingStability=Tracking.Stability.STABLE;
    private BaseEnemy target=null;
    private TreeMap<Double,BaseEnemy> targetHistory=new TreeMap<Double, BaseEnemy>();
    public void updateTarget(TowerDefence towerDefence,double timestep){
        targetHistory.clear();
        NavigableMap<Double,Point2D.Double> path;
        Entry<Double, Point2D.Double> currentPosition;
        Entry<Double, Point2D.Double> nextPosition;
        double time;
        Point2D.Double location=towerDefence.grid.getPointLocation(this.getLocation());
        if(target==null||trackingStability==Tracking.Stability.UNSTABLE){
            target=tracking.chooseEnemy(location, towerDefence.getEnemiesInRange(this));
        }
        targetHistory.put(0.0, target);
        if(target!=null){
            path=target.getPath().tailMap(target.getPath().floorKey(0.0),true);
            currentPosition = path.firstEntry();
        }else{
            TwoItems<Double, BaseEnemy> nextTarget=towerDefence.getNextEnemyInRange(this, 0.0);
            if(nextTarget==null){
                return;
            }
            time=nextTarget.getA();
            target=nextTarget.getB();
            targetHistory.put(time, target);
            path=target.getPath().tailMap(target.getPath().floorKey(time),true);
            currentPosition = path.firstEntry();
        }
        while(currentPosition!=path.lastEntry()){
            nextPosition=path.higherEntry(currentPosition.getKey());
            double a=Math.pow(currentPosition.getValue().x-nextPosition.getValue().x,2)+
                    Math.pow(currentPosition.getValue().y-nextPosition.getValue().y,2);
            double b=(currentPosition.getValue().x-location.x)*(currentPosition.getValue().x-nextPosition.getValue().x)+
                    (currentPosition.getValue().y-location.y)*(currentPosition.getValue().y-nextPosition.getValue().y);
            double c=Math.pow(currentPosition.getValue().x-location.x,2)+
                    Math.pow(currentPosition.getValue().y-location.y,2);
            double end;
            if(a>0){
                end=(-b+Math.sqrt(Math.pow(b, 2)-4*a*(c-getRange())))/2*a;
            }else{
                end=(-b-Math.sqrt(Math.pow(b, 2)-4*a*(c-getRange())))/2*a;
            }
            if(end<1){
                time=currentPosition.getKey()+(nextPosition.getKey()-currentPosition.getKey())*end;
                target=tracking.chooseEnemy(location, towerDefence.getEnemiesInRange(this,time));
                targetHistory.put(time, target);
                if(target!=null){
                    path=target.getPath().tailMap(target.getPath().floorKey(time),true);
                    currentPosition = path.firstEntry();
                }else{
                    TwoItems<Double, BaseEnemy> nextTarget=towerDefence.getNextEnemyInRange(this, time);
                    if(nextTarget==null){
                        break;
                    }
                    time=nextTarget.getA();
                    target=nextTarget.getB();
                    targetHistory.put(time, target);
                    path=target.getPath().tailMap(target.getPath().floorKey(time),true);
                    currentPosition = path.firstEntry();
                }
            }else{
                currentPosition=nextPosition;
            }
        }
    }
    public abstract void update(TowerDefence towerDefence,double timestep);
}
