/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies.impls;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.Towers.DamagingTower;
import java.awt.Point;

/**
 *
 * @author Simon Armstrong
 */
public class BasicEnemy extends BaseEnemy{
    public BasicEnemy(double level,Point target){
        super(level,1,target);
    }

    @Override
    public boolean invunerable(DamagingTower tower) {
        return false;
    }
    
}
