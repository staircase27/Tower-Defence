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
public class StrongTower extends BaseMissileTower{

    public StrongTower(){
        super(0.5, 2, Type.LASER, 2);
    }

    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return new Pair<Class<? extends BaseTower>>(XrayTower.class, ShielderTower.class);
    }
    
}
