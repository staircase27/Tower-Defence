/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.Towers.impls.missileTowers.MissileTower;
import com.staircase27.TD.lib.Towers.impls.areaTowers.AreaTower;
import com.staircase27.TD.lib.lib.Pair;

/**
 *
 * @author Simon Armstrong
 */
public class Towers {
    public static Pair<Class<? extends BaseTower>> getBaseTowers(){
        return new Pair<Class<? extends BaseTower>>(MissileTower.class, AreaTower.class);
    }
}
