/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.TowerDefence;
import java.awt.Point;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseMissileTower extends BaseAttackTower{
    
    public  BaseMissileTower(Point location, double baseRate, double baseRange, double baseDamage) {
        super(location, baseRate, baseRange, baseDamage);
    }

    public BaseMissileTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget) {
        super(location, baseRate, baseRange, baseDamage, damageTarget);
    }

    public BaseMissileTower(Point location, double baseRate, double baseRange,
            double baseDamage, DamageTarget damageTarget,
            DamageShieldType damageShieldType) {
        super(location, baseRate, baseRange, baseDamage, damageTarget, damageShieldType);
    }
    
//    public abstract Bullet getNewBullet();

    @Override
    public void update(TowerDefence towerDefence, double timestep) {
        updateTarget(towerDefence, timestep);
    }

}
