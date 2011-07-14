/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib.Updatable;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An updatable for Sets.
 * 
 * This class provides a view that lets us update a set and then decide whether to apply the changes or not.
 * @param <V> the type of element in the set. See {@link Set}
 * @author Simon Armstrong
 */
public class SetUpdate<V> extends Updatable<Set<V>> {

    /** The removed elements*/
    private final Set<V> removals = new HashSet<V>();
    /** The added elements*/
    private final Set<V> additions = new HashSet<V>();

    /**
     * creates a SetUpdate 
     * @param original
     */
    public SetUpdate(Set<V> original) {
        super(original);
    }

    @Override
    public void doUpdate() {
        getOriginal().addAll(additions);
        getOriginal().removeAll(removals);
    }
    /**The setView for this set. Stored so instance can be shared between all uses.*/
    private Set<V> setView = null;

    @Override
    public Set<V> getView() {
        if (setView == null) {
            setView = new SetView();
        }
        return setView;
    }

    /**
     * The implementation of a view for a Set.
     */
    private class SetView extends AbstractSet<V> {

        @Override
        public int size() {
            return getOriginal().size() + additions.size() - removals.size();
        }

        @Override
        public boolean isEmpty() {
            return additions.isEmpty() && (getOriginal().size() == removals.size());
        }

        @Override
        public boolean contains(Object o) {
            return additions.contains(o) || (getOriginal().contains(o) && !removals.contains(o));
        }

        @Override
        public Iterator<V> iterator() {
            return new SetViewIterator();
        }

        /**
         * An iterator for SetViews.
         * 
         * Returns the elements in the order original elements (skipping the removed elements) then added elements.
         */
        private class SetViewIterator implements Iterator<V> {

            /**flag for if we are iterating over additions*/
            private boolean addition = false;
            /**the current iterator over elements from the base sets*/
            private Iterator<V> it = getOriginal().iterator();
            /**flag for having run out of elements*/
            private boolean finished = false;
            /**flag for having a next element ready*/
            private boolean nextReady = false;
            /**the next element to return from this iterator*/
            private V next;
            /**the current element returned from this iterator*/
            private V current;

            /**
             * Returns true if the iteration has more elements. 
             * 
             * (In other words, returns true if next would return an element rather than throwing an exception.) 
             * @return true if the iterator has more elements
             */
            @Override
            public boolean hasNext() {
                if (!addition && removals.contains(next)) {
                    nextReady = false;
                }
                while (!nextReady) {
                    if (it.hasNext()) {
                        next = it.next();
                        if (!addition) {
                            if (removals.contains(next)) {
                                continue;
                            }
                        }
                        nextReady = true;
                    } else {
                        if (addition) {
                            finished = true;
                            return false;
                        } else {
                            it = additions.iterator();
                        }
                    }
                }
                return true;
            }

            /**
             * Returns the next element in the iteration. 
             * @return the next element in the iteration
             */
            @Override
            public V next() {
                if (hasNext()) {
                    current = next;
                    nextReady = false;
                    return current;
                } else {
                    throw new NoSuchElementException();
                }
            }

            /**
             * Removes from the underlying collection the last element returned by the iterator.
             * 
             * This method can be called only once per call to next. 
             * The behavior of an iterator is unspecified if the underlying collection is modified while the iteration is in progress in any way other than by calling this method.
             * Removing is not supported if hasNext has been called since the call to next.
             * 
             * @exception IllegalStateException if the next method has not yet been called or the remove method has already been called after the last call to the next method if hasNext has been called since next was called
             */
            @Override
            public void remove() throws IllegalStateException {
                if (addition) {
                    if (!(nextReady | finished)) {
                        it.remove();
                    } else {
                        throw new IllegalStateException("Can't remove after hasNext has been called");
                    }
                } else {
                    removals.add(current);
                }
            }
        }

        @Override
        public boolean add(V e) {
            if (getOriginal().contains(e)) {
                return removals.remove(e);
            } else {
                return additions.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            if (getOriginal().contains(o)) {
                return removals.add((V) o);
            } else {
                return additions.remove(o);
            }
        }

        @Override
        public void clear() {
            additions.clear();
            removals.addAll(getOriginal());
        }
    }
}
