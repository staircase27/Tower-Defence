/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib.Updatable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Simon Armstrong
 */
public abstract class Updatable {

    private Set<Updatable> linked=new HashSet<Updatable>(Arrays.asList(new Updatable[]{this}));

    public Updatable() {
    }

    abstract void doUpdate();

    public void link(Updatable link) {
        link.linked.addAll(linked);
        for(Updatable updatable:linked)
            updatable.linked = link.linked;
    }

    public void unlink() {
        linked.remove(this);
        this.linked = new HashSet<Updatable>(Arrays.asList(new Updatable[]{this}));
    }

    public void update() {
        for (Updatable update : linked) {
            update.doUpdate();
        }
    }
    
}
