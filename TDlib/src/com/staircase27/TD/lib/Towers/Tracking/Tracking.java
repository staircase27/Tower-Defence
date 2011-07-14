/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers.Tracking;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import java.awt.geom.Point2D;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public interface Tracking {
    
    public static enum Stability{
        STABLE,UNSTABLE
    }
    
    public BaseEnemy chooseEnemy(Point2D.Double towerLocation, Set<BaseEnemy> enemies);
    
}
