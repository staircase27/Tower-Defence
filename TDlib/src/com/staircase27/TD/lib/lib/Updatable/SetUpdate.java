/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib.Updatable;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V> 
 * @author Simon Armstrong
 */
public class SetUpdate<V> extends Updatable{

    private final Set<V> original;
    private final Set<V> removals=new HashSet<V>();
    private final Set<V> additions=new HashSet<V>();

    public SetUpdate(Set<V> original){
        this.original=original;
    }
    
    @Override
    void doUpdate() {
        original.addAll(additions);
        original.removeAll(removals);
    }

    private Set<V> setView=null;
    public Set<V> getSet(){
        if( setView==null)
            setView=new SetView();
        return setView;
    }

    private class SetView extends AbstractSet<V> {

        SetView() {
        }

        @Override
        public int size() {
            return original.size()+additions.size()-removals.size();
        }

        @Override
        public boolean isEmpty() {
            return additions.isEmpty()&&(original.size()==removals.size());
        }

        @Override
        public boolean contains(Object o) {
            return additions.contains(o)||(original.contains(o)&&!removals.contains(o));
        }

        @Override
        public Iterator<V> iterator() {
            return new SetViewIterator();
        }
        
        private class SetViewIterator implements Iterator<V>{

            private boolean addition=false;
            private Iterator<V> it=original.iterator();
            private boolean nextReady;
            private V next;
            private V current;
            @Override
            public boolean hasNext() {
                while(!nextReady){
                    if(it.hasNext()){
                        next=it.next();
                        if(!addition){
                            if(removals.contains(next))
                                continue;
                        }
                        nextReady=true;
                    }else{
                        if(addition){
                            return false;
                        }else{
                            it=additions.iterator();
                        }
                    }
                }
                return true;
            }

            @Override
            public V next() {
                if(hasNext()){
                    current=next;
                    nextReady=false;
                    return current;
                }else{
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        }
        @Override
        public boolean add(V e) {
            if(original.contains(e)){
                return removals.remove(e);
            }else{
                return additions.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            if(original.contains(o)){
                return removals.add((V)o);
            }else{
                return additions.remove(o);
            }
        }

        @Override
        public void clear() {
            additions.clear();
            removals.addAll(original);
        }
    }
    
}
