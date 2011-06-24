/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Towers;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseMissileTower extends DamagingTower {

    public static enum Type {

        BULLET, LASER
    }
    private final double baseRate;
    private double rate;
    private final double baseRange;
    private double range;
    private final Type type;

    public BaseMissileTower(double baseRate, double baseRange,
            Type type, double baseDamage) {
        this(baseRate, baseRange, type, baseDamage, DamageTarget.HP, DamageShieldType.DEFAULT);
    }

    public BaseMissileTower(double baseRate, double baseRange,
            Type type, double baseDamage,
            DamageTarget damageTarget) {
        this(baseRate, baseRange, type, baseDamage, damageTarget, DamageShieldType.DEFAULT);
    }

    public BaseMissileTower(double baseRate, double baseRange,
            Type type, double baseDamage,
            DamageTarget damageTarget, DamageShieldType damageShieldType) {
        super(baseDamage, damageTarget, damageShieldType);
        this.rate = this.baseRate = baseRate;
        this.range = this.baseRange = baseRange;
        this.type = type;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void adjustRate(boolean increase, double factor) {
        if (increase) {
            setRate(getRate() * (1 + factor));
        } else {
            setRate(getRate() / (1 + factor));
        }
    }

    public double getBaseRange() {
        return baseRange;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void adjustRange(boolean increase, double factor) {
        if (increase) {
            setRange(getRange() * (1 + factor));
        } else {
            setRange(getRange() / (1 + factor));
        }
    }

    public Type getType() {
        return type;
    }
}
