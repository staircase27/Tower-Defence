/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies;

import com.staircase27.TD.lib.Towers.DamagingTower;

/**
 *
 * @author Simon Armstrong
 */
public class BasicEnemy extends BaseEnemy{
    public BasicEnemy(double level){
        super(level,1);
    }

    @Override
    public boolean invunerable(DamagingTower tower) {
        return false;
    }
    
}
