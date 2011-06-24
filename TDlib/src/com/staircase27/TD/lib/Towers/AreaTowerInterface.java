/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public interface AreaTowerInterface {
    public double getRange();
    public void enterArea(BaseEnemy enemy);
    public void leaveArea(BaseEnemy enemy);
    public void activateTower(Set<BaseEnemy> enemies,Set<BaseMissileTower> towers);
    public void disactivateTower(Set<BaseEnemy> enemies,Set<BaseMissileTower> towers);
}
