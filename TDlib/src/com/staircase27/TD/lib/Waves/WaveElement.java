/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.Waves;

import com.staircase27.TD.lib.Enemies.BaseEnemy;
import com.staircase27.TD.lib.Enemies.EnemyFactory;
import com.staircase27.TD.lib.TowerDefence;
import com.staircase27.TD.lib.lib.Pair;
import com.staircase27.TD.lib.lib.TwoItems;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public abstract class WaveElement {

    private WaveElement() {
    }
    
    public abstract Set<TwoItems<Class<? extends BaseEnemy>, Double>> getAllEnemies();
    public abstract Set<Pair<Point>> getAllRoutes();
    public abstract double update(TowerDefence towerDefence,double timestep);

    public static abstract class Compound extends WaveElement {

        private final List<WaveElement> waveElements;

        private Compound(List<WaveElement> waveElements) {
            this.waveElements = waveElements;
        }

        public List<WaveElement> getWaveElements() {
            return waveElements;
        }

        @Override
        public final Set<TwoItems<Class<? extends BaseEnemy>, Double>> getAllEnemies() {
            Set<TwoItems<Class<? extends BaseEnemy>, Double>> enemies=new HashSet<TwoItems<Class<? extends BaseEnemy>, Double>>();
            for(WaveElement waveElement:waveElements){
                enemies.addAll(waveElement.getAllEnemies());
            }
            return enemies;
        }

        @Override
        public final Set<Pair<Point>> getAllRoutes() {
            Set<Pair<Point>> routes=new HashSet<Pair<Point>>();
            for(WaveElement waveElement:waveElements){
                routes.addAll(waveElement.getAllRoutes());
            }
            return routes;
        }

        public static final class Parallel extends Compound {

            public Parallel(List<WaveElement> waveElements) {
                super(waveElements);
            }

            @Override
            public double update(TowerDefence towerDefence, double timestep) {
                double left=timestep;
                for(WaveElement waveElement:getWaveElements()){
                    double thisLeft=waveElement.update(towerDefence, timestep);
                    if(thisLeft<left){
                        left=thisLeft;
                    }
                }
                return left;
            }

        }

        public static final class Series extends Compound {
            int i=0;
            WaveElement current;
            public Series(List<WaveElement> waveElements) {
                super(new ArrayList<WaveElement>(waveElements));
            }

            @Override
            public double update(TowerDefence towerDefence, double timestep) {
                if(i>=getWaveElements().size()){
                    return timestep;
                }
                double left=getWaveElements().get(i).update(towerDefence, timestep);
                while(timestep>=0){
                    i++;
                    if(i>=getWaveElements().size()){
                        return left;
                    }
                    left=getWaveElements().get(i).update(towerDefence, left);
                }
                return -1;
            }

        }

    }

    public static final class Enemies extends WaveElement {

        private final List<TwoItems<Class<? extends BaseEnemy>, Double>> enemiesDef;
        private final boolean enemyRandom;
        private final List<Pair<Point>> routes;
        private final boolean routeRandom;
        private final double averageGap;
        private final double gapRandomness;
        private final int enemyCount;
        private double gapLeft=0;
        private int enemiesLeft;

        public Enemies(List<TwoItems<Class<? extends BaseEnemy>, Double>> enemiesDef, boolean enemyRandom,
                List<Pair<Point>> routes, boolean routeRandom, 
                double averageGap, double gapRandomness, int enemyCount) {
            this.enemiesDef = enemiesDef;
            this.enemyRandom = enemyRandom;
            this.routes = routes;
            this.routeRandom = routeRandom;
            this.averageGap = averageGap;
            this.gapRandomness = gapRandomness;
            this.enemiesLeft=this.enemyCount = enemyCount;
        }

        public List<TwoItems<Class<? extends BaseEnemy>, Double>> getEnemiesDef() {
            return enemiesDef;
        }

        public boolean isEnemyRandom() {
            return enemyRandom;
        }

        public List<Pair<Point>> getRoutes() {
            return routes;
        }

        public boolean isRouteRandom() {
            return routeRandom;
        }

        public double getAverageGap() {
            return averageGap;
        }

        public double getGapRandomness() {
            return gapRandomness;
        }

        public int getEnemyCount() {
            return enemyCount;
        }

        @Override
        public Set<TwoItems<Class<? extends BaseEnemy>, Double>> getAllEnemies() {
            return new HashSet<TwoItems<Class<? extends BaseEnemy>, Double>>(enemiesDef);
        }

        @Override
        public Set<Pair<Point>> getAllRoutes() {
            return new HashSet<Pair<Point>>(routes);
        }

        @Override
        public double update(TowerDefence towerDefence, double timestep) {
            gapLeft-=timestep;
            while(gapLeft<0){
                //choose what to spawn and where.
                Pair<Point> route;
                if(routeRandom){
                    route=routes.get((int)(Math.random()*routes.size()));
                }else{
                    route=routes.get(enemiesLeft%routes.size());
                }
                TwoItems<Class<? extends BaseEnemy>, Double> enemyDef;
                if(enemyRandom){
                    enemyDef=enemiesDef.get((int)(Math.random()*enemiesDef.size()));
                }else{
                    enemyDef=enemiesDef.get(enemiesLeft%enemiesDef.size());
                }
                BaseEnemy enemy=EnemyFactory.createEnemy(enemyDef.getA(),enemyDef.getB(),route.getB());
                towerDefence.spawnEnemy(enemy, route.getA());
                enemiesLeft--;
                if(enemiesLeft==0){
                    return -gapLeft;
                }
                gapLeft+=averageGap*(1+gapRandomness*Math.random())/(1+gapRandomness/2);
            }
            return -1;
        }
    }
    
    public static final class Message extends WaveElement{
        private final String message;
        private final boolean pause;
        /**
         * 
         * @param message
         * @param pause
         */
        public Message(String message,boolean pause){
            this.message = message;
            this.pause = pause;
        }

        public String getMessage() {
            return message;
        }

        public boolean doPause() {
            return pause;
        }

        @Override
        public Set<TwoItems<Class<? extends BaseEnemy>, Double>> getAllEnemies() {
            return new HashSet<TwoItems<Class<? extends BaseEnemy>, Double>>();
        }

        @Override
        public Set<Pair<Point>> getAllRoutes() {
            return new HashSet<Pair<Point>>();
        }

        @Override
        public double update(TowerDefence towerDefence, double timestep) {
            return timestep;
        }
    }
}
