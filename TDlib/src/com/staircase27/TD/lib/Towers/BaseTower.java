/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.lib.Pair;
import java.awt.Point;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseTower {
    private final Point location;
    public BaseTower(Point location){
        this.location=location;
    }
    public abstract Pair<Class<? extends BaseTower>> getUpgrades();
    public String getName(){
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0,simpleName.length()-5);
    }

    public Point getLocation() {
        return location;
    }
}
