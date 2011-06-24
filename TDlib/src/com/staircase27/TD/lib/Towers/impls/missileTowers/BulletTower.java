/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers.impls.missileTowers;

import com.staircase27.TD.lib.Towers.BaseMissileTower;
import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.lib.Pair;

/**
 *
 * @author Simon Armstrong
 */
public class BulletTower extends BaseMissileTower{

    public BulletTower(){
        super(1 ,1, Type.BULLET, 3);
    }

    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return new Pair<Class<? extends BaseTower>>(HeavyTower.class, RapidTower.class);
    }
    
}
