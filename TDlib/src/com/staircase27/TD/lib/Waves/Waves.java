/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Waves;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.lib.Pair;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public final class Waves {
    private List<Wave> waves;
    
    public Waves(List<Wave> waves){
        this.waves = waves;
    }
    
    public List<Wave> getWaves(){
        return waves;
    }

    public final Set<TwoItems<Class<? extends BaseEnemy>, Double>> getAllEnemies() {
        Set<TwoItems<Class<? extends BaseEnemy>, Double>> enemies=new HashSet<TwoItems<Class<? extends BaseEnemy>, Double>>();
        for(Wave wave:waves){
            enemies.addAll(wave.getAllEnemies());
        }
        return enemies;
    }

    public final Set<Pair<Point>> getAllRoutes() {
        Set<Pair<Point>> routes=new HashSet<Pair<Point>>();
        for(Wave wave:waves){
            routes.addAll(wave.getAllRoutes());
        }
        return routes;
    }
}
