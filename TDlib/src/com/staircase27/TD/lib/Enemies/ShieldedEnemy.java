/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies;

import com.staircase27.TD.lib.Towers.DamagingTower;
import java.awt.Point;

/**
 *
 * @author Simon Armstrong
 */
public class ShieldedEnemy extends BaseEnemy{
    
    public ShieldedEnemy(double level, Point target){
        super(level/2, 1, level/2,target);
    }

    @Override
    public boolean invunerable(DamagingTower tower) {
        return false;
    }
    
}
