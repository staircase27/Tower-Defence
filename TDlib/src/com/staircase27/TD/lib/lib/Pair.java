/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A two element immutable set of items.
 * @param <T> The type of the two elements in this object
 * @author Simon Armstrong
 */
public class Pair<T> implements Set<T> {

    /** the first element*/
    private T a;
    /** the second element*/
    private T b;

    /**
     * Creates a Pair with the two elements A and B
     * @param a the first element
     * @param b the second element
     */
    public Pair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    /**
     * get the A element
     * @return the A element
     */
    public T getA() {
        return a;
    }

    /**
     * get the B element
     * @return the B element
     */
    public T getB() {
        return b;
    }

    /**
     * @inheritDoc
     * 
     * This always returns 2;
     */
    @Override
    public int size() {
        return 2;
    }

    /**
     * @inheritDoc
     * 
     * This always returns false;
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return ((o == null) && (a == null || b == null))
                || ((o != null) && (o.equals(a) || o.equals(b)));
    }

    /**
     * The implementation of iterator for the pair class.
     * 
     * Returns A then B.
     */
    private class PairIterator implements Iterator<T> {

        int i = 0;

        @Override
        public boolean hasNext() {
            return i < 2;
        }

        @Override
        public T next() {
            if (i == 0) {
                i++;
                return a;
            } else if (i == 1) {
                i++;
                return b;
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * Removal is not supported by Pair.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new PairIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[2];
        array[0] = a;
        array[1] = b;
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 2);
        array[0] = (T) a;
        array[1] = (T) b;
        return array;
    }

    /**
     * adding to a pair is not supported.
     */
    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * removing from a pair is not supported.
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> e = c.iterator();
        while (e.hasNext()) {
            if (!contains(e.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * adding to a pair is not supported.
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * removing from a pair is not supported.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * removing from a pair is not supported.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    /**
     * removing from a pair is not supported.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported");
    }
}
