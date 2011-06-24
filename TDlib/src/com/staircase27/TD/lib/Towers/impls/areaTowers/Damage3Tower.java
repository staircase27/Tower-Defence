/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers.impls.areaTowers;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.Towers.AreaTowerInterface;
import com.staircase27.TD.lib.Towers.BaseMissileTower;
import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.Towers.DamagingTower;
import com.staircase27.TD.lib.lib.Pair;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public class Damage3Tower extends DamagingTower implements AreaTowerInterface{
    public Damage3Tower(){
        super(1, DamageTarget.HP, DamageShieldType.DEFAULT);
    }
            
    
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
        enemy.startDamage(getDamage(), this);
    }

    @Override
    public void leaveArea(BaseEnemy enemy) {
        enemy.endDamage(getDamage(), this);
    }
    
    @Override
    public void activateTower(Set<BaseEnemy> enemies, Set<BaseMissileTower> towers) {
        for(BaseEnemy enemy:enemies){
            enterArea(enemy);
        }
    }

    @Override
    public void disactivateTower(Set<BaseEnemy> enemies, Set<BaseMissileTower> towers) {
        for(BaseEnemy enemy:enemies){
            leaveArea(enemy);
        }
    }
}
