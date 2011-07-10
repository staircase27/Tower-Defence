/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Waves;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.lib.Pair;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public final class Wave {
    private final String description;
    private final double delay;
    private final double compleationDelayFactor;
    private final WaveElement waveElement;
    
    public Wave(String description,double delay,WaveElement waveElement){
        this(description, delay, 1, waveElement);
    }
    /**
     * 
     * @param description
     * @param delay
     * @param compleationDelayFactor
     * @param waveElement
     */
    public Wave(String description,double delay,double compleationDelayFactor,WaveElement waveElement){
        this.description = description;
        this.delay = delay;
        this.compleationDelayFactor = compleationDelayFactor;
        this.waveElement = waveElement;
        
    }

    public String getDescription() {
        return description;
    }

    public double getDelay() {
        return delay;
    }

    public double getCompleationDelayFactor() {
        return compleationDelayFactor;
    }

    public WaveElement getWaveElement() {
        return waveElement;
    }
    
    public Set<TwoItems<Class<? extends BaseEnemy>, Integer>> getAllEnemies(){
        return waveElement.getAllEnemies();
    }
    public Set<Pair<Point>> getAllRoutes(){
        return waveElement.getAllRoutes();
    }
}
