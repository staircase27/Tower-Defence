/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib.Updatable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * A base class for all the updatables.
 * 
 * These are to use for doing an update to a container object and then decide if to accept them or not.
 * These should not be reused after the update has been applied.
 * @param <T> the type of the object this is an updatable for
 * @author Simon Armstrong
 */
public abstract class Updatable<T> {

    /** A set of all the updatables linked to this one*/
    private HashSet<Updatable<?>> linked = new HashSet<Updatable<?>>(Arrays.asList(new Updatable<?>[]{this}));
    /** the original object this will update*/
    private final T original;

    /**
     * Creates a new updatable based on original.
     * @param original the original container object
     */
    public Updatable(T original) {
        this.original = original;
    }

    /**
     * get the original object this will update.
     * @return the original object
     */
    public final T getOriginal() {
        return original;
    }

    /**
     * get the view through which changes can be made.
     * @return a view of the original map with changes made
     */
    public abstract T getView();

    /**
     * link this updatable to another one so they will all update together.
     * @param link an updatable to link to
     */
    public final void link(Updatable<?> link) {
        link.linked.addAll(linked);
        for (Updatable<?> updatable : linked) {
            updatable.linked = link.linked;
        }
    }

    /**
     * unlink this updatable from all updatables it was linked to.
     * 
     * NOTE: calling link and then unlink will not necessarily leave the links the same.
     */
    public final void unlink() {
        linked.remove(this);
        this.linked = new HashSet<Updatable<?>>(Arrays.asList(new Updatable<?>[]{this}));
    }

    /**
     * implements the actual update for this updatable.
     */
    protected abstract void doUpdate();

    /**
     * updates this updatable and all updatables linked to it.
     */
    public final void update() {
        for (Updatable<?> update : linked) {
            update.doUpdate();
        }
    }
}
