/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers.impls.areaTowers;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.Towers.AreaTowerInterface;
import com.staircase27.TD.lib.Towers.BaseAttackTower;
import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.lib.Pair;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public class ConfuseTower extends BaseTower implements AreaTowerInterface{
    public static final double slowing=0.2;
            
    
    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return null;
    }

    @Override
    public double getRange() {
        return 1;
    }

    @Override
    public void enterArea(BaseEnemy enemy) {
        //TODO: confuse
    }

    @Override
    public void leaveArea(BaseEnemy enemy) {
        //TODO: confuse
    }
    
    @Override
    public void activateTower(Set<BaseEnemy> enemies, Set<BaseAttackTower> towers) {
        for(BaseEnemy enemy:enemies){
            enterArea(enemy);
        }
    }

    @Override
    public void disactivateTower(Set<BaseEnemy> enemies, Set<BaseAttackTower> towers) {
        for(BaseEnemy enemy:enemies){
            leaveArea(enemy);
        }
    }
}
