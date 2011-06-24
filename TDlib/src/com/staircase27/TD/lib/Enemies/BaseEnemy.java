/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies;

import com.staircase27.TD.lib.Towers.DamagingTower;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Simon Armstrong
 */
public abstract class BaseEnemy {

    //the current location in cartesian space
    private Point2D.Double location = null;
    //the label for the point in grid space this is traveling from
    private Point from = null;
    //the label for the point in grid space this is traveling to
    private Point to = null;
    //the life points of this unit
    private double HP;
    //the speed of this unit
    private double speed;
    //the maximum speed of this unit currently
    private double maxSpeed;
    //the amount of timeStep left to wait till speed will recover;
    private double speedDelay;
    private static final double SPEEDDELAY=100;
    private final double speedRecoveryRate=1;
    //the life points of this units shield
    private double shield;
    private double shieldDelay;
    private static final double SHIELDDELAY=100;
    private final double shieldRecoveryRate=1;
    //the vunerability of this unit to attackes
    private double vunerability = 1;
    //the initial speed
    private final double baseSpeed;
    //the initial life points
    private final double baseHP;
    //the initial life points of the shield
    private final double baseShield;
    //the rate of damage being applied by area effect or laser towers
    private double damageHPOnlyRate=0;
    private double damageShieldOnlyRate=0;
    private double damageRate=0;
    //the rate of damage being applied by area effect or laser towers
    private double damageSpeedRate=0;

    
    public BaseEnemy(double baseHP,double baseSpeed){
        this(baseHP, baseSpeed, 0);
    }
    public BaseEnemy(double baseHP,double baseSpeed,double baseShield){
        this.speed=this.maxSpeed=this.baseSpeed=baseSpeed;
        this.HP=this.baseHP=baseHP;
        this.shield=this.baseShield=baseShield;
    }
    
    /**
     * @return the location
     */
    public final Point2D.Double getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public final void setLocation(Point2D.Double location) {
        this.location = location;
    }

    /**
     * @return the from
     */
    public final Point getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public final void setFrom(Point from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public final Point getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public final void setTo(Point to) {
        this.to = to;
    }

    public abstract boolean invunerable(DamagingTower tower);
    
    public final boolean doDamageHit(double damage, DamagingTower tower){
        if (!invunerable(tower)){
            if(tower.getDamageTarget()==DamagingTower.DamageTarget.SPEED){
                setSpeed(getSpeed() - 0.5 * damage * getVunerability());
            }else if((tower.getDamageShieldType()!=DamagingTower.DamageShieldType.IGNORE) && 
                    getShield()>0){
                setShield(getShield() - 1 * damage * getVunerability());
                shieldDelay=SHIELDDELAY;                
            }else if(tower.getDamageShieldType()!=DamagingTower.DamageShieldType.ONLY){
                setHP(getHP() - 1 * damage * getVunerability());
                shieldDelay=SHIELDDELAY;
            }
        }
        if (getHP()<=0){
            return true;
        }else{
            return false;
        }
    }
    
    public final void startDamage(double damage,DamagingTower tower){
        if(!invunerable(tower)){
            if(tower.getDamageTarget()==DamagingTower.DamageTarget.SPEED){
                damageSpeedRate+=damage*0.5;
            }else if(tower.getDamageShieldType()==DamagingTower.DamageShieldType.DEFAULT){
                damageRate+=damage;
            }else if(tower.getDamageShieldType()==DamagingTower.DamageShieldType.ONLY){
                damageShieldOnlyRate+=damage;
            }else{
                damageHPOnlyRate+=damage;
            }
        }
    }

    public final void endDamage(double damage,DamagingTower tower){
        if(!invunerable(tower)){
            if(tower.getDamageTarget()==DamagingTower.DamageTarget.SPEED){
                damageSpeedRate-=damage*0.5;
            }else if(tower.getDamageShieldType()==DamagingTower.DamageShieldType.DEFAULT){
                damageRate-=damage;
            }else if(tower.getDamageShieldType()==DamagingTower.DamageShieldType.ONLY){
                damageShieldOnlyRate-=damage;
            }else{
                damageHPOnlyRate-=damage;
            }
        }
    }
    
    
    public final boolean update(double timeStep){
        if(getDamageSpeedRate()>0){
            setSpeed(getSpeed()-getDamageSpeedRate()*timeStep);
            speedDelay=SPEEDDELAY;
        }else{
            speedDelay-=timeStep;
            if(getSpeedDelay()<=0 && getSpeed()<getMaxSpeed()){
                double newSpeed=getSpeedRecoveryRate()*timeStep+getSpeed();
                if(newSpeed>getMaxSpeed()) newSpeed=getMaxSpeed();
                setSpeed(newSpeed);
            }
        }
        if(getBaseShield()>0){
            if(getDamageShieldOnlyRate()>0){
                setShield(getShield()-getDamageShieldOnlyRate()*timeStep);
            }
            if(getDamageRate()>0){
                if(getDamageRate()*timeStep>getShield()){
                    setHP(getHP()-getDamageRate()*timeStep+getShield());
                    setShield(0);
                }else{
                    setShield(getShield()-getDamageRate()*timeStep);
                }
            }
            if(getDamageHPOnlyRate()>0){
                setHP(getHP()-getDamageHPOnlyRate()*timeStep);
            }
            if(getDamageHPOnlyRate()+getDamageRate()+getDamageShieldOnlyRate()>0){
                shieldDelay=SHIELDDELAY;
            }else{
                shieldDelay-=timeStep;
                if(getShieldDelay()<=0 && getShield()<getBaseShield()){
                    double newShield=getShieldRecoveryRate()*timeStep+getShield();
                    if(newShield>getBaseShield()) newShield=getBaseShield();
                    setShield(newShield);
                }
            }
        }else{
            setHP(getHP()-getDamageHPOnlyRate()*timeStep-getDamageRate()*timeStep);
        }
        if (getHP()<=0){
            return true;
        }else{
            return false;
        }
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getSpeedDelay() {
        return speedDelay;
    }

    public double getSpeedRecoveryRate() {
        return speedRecoveryRate;
    }

    public double getShield() {
        return shield;
    }

    public void setShield(double shield) {
        this.shield = shield;
    }

    public double getShieldDelay() {
        return shieldDelay;
    }

    public double getShieldRecoveryRate() {
        return shieldRecoveryRate;
    }

    public double getVunerability() {
        return vunerability;
    }

    public void setVunerability(double vunerability) {
        this.vunerability = vunerability;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public double getBaseHP() {
        return baseHP;
    }

    public double getBaseShield() {
        return baseShield;
    }

    public double getDamageHPOnlyRate() {
        return damageHPOnlyRate;
    }

    public double getDamageShieldOnlyRate() {
        return damageShieldOnlyRate;
    }

    public double getDamageRate() {
        return damageRate;
    }

    public double getDamageSpeedRate() {
        return damageSpeedRate;
    }
}