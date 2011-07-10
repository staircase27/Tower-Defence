/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Enemies;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Simon Armstrong
 */
public abstract class EnemyFactory {

    private EnemyFactory() {
    }

    public static BaseEnemy createEnemy(Class<? extends BaseEnemy> enemyClass, double level, Point target) {
        try {
            return enemyClass.getConstructor(double.class, Point.class).newInstance(level, target);
        } catch (InstantiationException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        } catch (SecurityException ex) {
            throw new RuntimeException("Can't create the requested Enemy");
        }
    }
}
