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
public class ScatterTower extends BaseMissileTower{

    public ScatterTower(){
        super(100 ,1.2, Type.BULLET, 0.1);
    }

    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return null;
    }
    
}
