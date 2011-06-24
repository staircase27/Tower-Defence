/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies;

import com.staircase27.TD.lib.Towers.BaseMissileTower;
import com.staircase27.TD.lib.Towers.DamagingTower;

/**
 *
 * @author Simon Armstrong
 */
public class ShieldedEnemy extends BaseEnemy{
    
    public ShieldedEnemy(double level){
        super(level/2, 1, level/2);
    }

    @Override
    public boolean invunerable(DamagingTower tower) {
        return false;
    }
    
}
