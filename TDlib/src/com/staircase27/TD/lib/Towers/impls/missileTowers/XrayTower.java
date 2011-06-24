/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers.impls.missileTowers;

import com.staircase27.TD.lib.Towers.BaseMissileTower;
import com.staircase27.TD.lib.Towers.BaseTower;
import com.staircase27.TD.lib.lib.Pair;

/**
 *
 * @author Simon Armstrong
 */
public class XrayTower extends BaseMissileTower{

    public XrayTower(){
        super(0.2, 3.5, Type.LASER, 3, DamageTarget.HP, DamageShieldType.IGNORE);
    }

    @Override
    public Pair<Class<? extends BaseTower>> getUpgrades() {
        return null;
    }
    
}
