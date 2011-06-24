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
 *
 * @author Simon Armstrong
 */
public class Pair<T> implements Set<T> {
    private T A;
    private T B;

    public Pair(T A, T B) {
        this.A=A;
        this.B=B;
    }
    public T getA(){
        return A;
    }
    public T getB(){
        return B;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return A.equals(o) || B.equals(o);
    }

    private class PairIterator implements Iterator<T>{

        int i=0;
        @Override
        public boolean hasNext() {
            return i<2;
        }

        @Override
        public T next() {
            if (i==0){
                return A;
            }else if (i==1){
                return B;
            }else{
                throw new NoSuchElementException();
            }
        }

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
        array[0]=A;array[1]=B;
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
       T[] array=(T[])java.lang.reflect.Array
                  .newInstance(a.getClass().getComponentType(), 2);
       array[0]=(T) A;array[1]=(T) B;
       return array;
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
	Iterator<?> e = c.iterator();
	while (e.hasNext())
	    if (!contains(e.next()))
		return false;
	return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported");
    }
}
