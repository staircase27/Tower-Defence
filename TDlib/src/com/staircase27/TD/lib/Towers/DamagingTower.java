/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

/**
 *
 * @author Simon Armstrong
 */
public abstract class DamagingTower extends BaseTower {


    public static enum DamageTarget {

        HP, SPEED
    }

    public static enum DamageShieldType {

        DEFAULT, ONLY, IGNORE
    }

    private final double baseDamage;
    private double damage;
    private final DamageTarget damageTarget;
    private final DamageShieldType damageShieldType;

    public DamagingTower(double baseDamage, DamageTarget damageTarget, DamageShieldType damageShieldType){
        this.damage = this.baseDamage = baseDamage;
        this.damageTarget = damageTarget;
        this.damageShieldType = damageShieldType;

    }
    
    
    public double getBaseDamage() {
        return baseDamage;
    }

    public double getDamage() {
        return damage;
    }

    public DamageShieldType getDamageShieldType() {
        return damageShieldType;
    }

    public DamageTarget getDamageTarget() {
        return damageTarget;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void adjustDamage(boolean increase, double factor) {
        if (increase) {
            setDamage(getDamage() * (1 + factor));
        } else {
            setDamage(getDamage() / (1 + factor));
        }
    }
    
}
