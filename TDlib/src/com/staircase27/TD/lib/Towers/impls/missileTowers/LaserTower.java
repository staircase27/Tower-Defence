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
public class LaserTower extends BaseMissileTower{

    public LaserTower(){
        super(1, 1, Type.LASER, 1);
    }

    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return new Pair<Class<? extends BaseTower>>(FastTower.class, StrongTower.class);
    }
    
}
