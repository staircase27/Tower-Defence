/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.TowerDefence;
import java.awt.Point;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseLaserTower extends BaseAttackTower{
    
    public  BaseLaserTower(Point location, double baseRate, double baseRange, double baseDamage) {
        super(location, baseRate, baseRange, baseDamage);
    }

    public BaseLaserTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget) {
        super(location,baseRate, baseRange, baseDamage, damageTarget);
    }

    public BaseLaserTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget,
            DamageShieldType damageShieldType) {
        super(location, baseRate, baseRange, baseDamage, damageTarget, damageShieldType);
    }
    
    private double angle;
    private TreeMap<Double,Double> angleHistory=new TreeMap<Double, Double>();
    
    public void updateTracking(double timeStep){
//        getTarget
    }

    @Override
    public void update(TowerDefence towerDefence, double timestep) {
        updateTarget(towerDefence, timestep);
        updateTracking(timestep);
    }

}
