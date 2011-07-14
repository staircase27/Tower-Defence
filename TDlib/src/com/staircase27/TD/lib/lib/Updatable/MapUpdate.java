/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib.lib.Updatable;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An updatable for maps.
 * 
 * This class provides a view that lets a map be edited and then lets you choose if to accept the changes or not.
 * @param <K> type of the keys. See {@link Map}
 * @param <V> type of the values. See {@link Map}
 * @author Simon Armstrong
 */
public final class MapUpdate<K,V> extends Updatable<Map<K,V>>{

    /**updates to the map*/
    private final Map<K,V> updates=new HashMap<K, V>();
    /**additions to the map*/
    private final Map<K,V> additions=new HashMap<K, V>();
    /**removals from the map*/
    private final Set<K> removals=new HashSet<K>();
    
    /**
     * Create a new MapUpdate based of original.
     * @param original the map this will update
     */
    public MapUpdate(Map<K,V> original){
        super(original);
    }

    /**
     * Create a new MapUpdate based of original and linked to the updatable link.
     * @param original the map this will update
     * @param link the updatable to link to
     */
    public MapUpdate(Map<K,V> original,MapUpdate<?,?> link){
        this(original);
        link(link);
    }
    
    @Override
    public void doUpdate(){
        getOriginal().putAll(updates);
        getOriginal().putAll(additions);
        getOriginal().keySet().removeAll(removals);
    }
    
    /**
     * check if the key k has been removed from the set.
     * @param k the key to check
     * @return if k has been removed
     */
    public boolean removed(K k) {
        return removals.contains(k);
    }

    /**the MapView for this MapUpdate*/
    MapView mapView=null;
    @Override
    public Map<K,V> getView(){
        if(mapView==null)
            mapView=new MapView();
        return mapView;
    }
    
    /**
     * A Map implementation that provides an editable view of the original map with updates.
     */
    private class MapView implements Map<K,V>{
    
        @Override
        public int size() {
            return getOriginal().size()+additions.size()-removals.size();
        }

        @Override
        public boolean isEmpty() {
            return size()>0;
        }

        @Override
        public boolean containsKey(Object key) {
            return (getOriginal().containsKey(key)||additions.containsKey(key))&&!removals.contains(key);
        }

        @Override
        public boolean containsValue(Object value) {
            if(additions.containsValue(value)){
                return true;
            }else{
                for(Map.Entry<K, V> entry:updates.entrySet()){
                    if(value.equals(entry.getValue())&&!removals.contains(entry.getKey())){
                        return true;
                    }
                }
                for(Map.Entry<K, V> entry:getOriginal().entrySet()){
                    if(value.equals(entry.getValue())&&!removals.contains(entry.getKey())&&!updates.containsKey(entry.getKey())){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public V get(Object key) {
            if(additions.containsKey(key)){
                return additions.get(key);
            }else if(updates.containsKey(key)){
                return updates.get(key);
            }else if(removals.contains(key)){
                return null;
            }else{
                return getOriginal().get(key);
            }
        }

        @Override
        public V put(K key, V value) {
            if(!getOriginal().containsKey(key)){
                return additions.put(key, value);
            }else if(updates.containsKey(key)){
                return updates.put(key, value);
            }else if(removals.contains(key)){
                updates.put(key, value);
                removals.remove(key);
                return null;
            }else{
                updates.put(key, value);
                return getOriginal().get(key);
            }
        }

        @Override
        public V remove(Object key) {
            if(!getOriginal().containsKey(key)){
                return additions.remove(key);
            }else{
                @SuppressWarnings("unchecked")
                K k=(K) key;
                if(updates.containsKey(key)){
                    removals.add(k);
                    return updates.remove(key);
                }else{
                    removals.add(k);
                    return getOriginal().get(key);
                }
            }
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            for(Map.Entry<? extends K, ? extends V> entry:m.entrySet()){
                put(entry.getKey(),entry.getValue());
            }
        }

        @Override
        public void clear() {
            updates.clear();
            additions.clear();
            removals.addAll(getOriginal().keySet());
        }

        /**A key set implementation for MapUpdate*/
        private class KeySet extends AbstractSet<K>{

            @Override
            public int size() {
                return MapView.this.size();
            }

            @Override
            public boolean isEmpty() {
                return MapView.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return MapView.this.containsKey(o);
            }

            @Override
            public Iterator<K> iterator() {
                return new KeyIterator();
            }

            @Override
            public boolean add(K e) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean remove(Object o) {
                boolean val=MapView.this.containsKey(o);
                MapView.this.remove(o);
                return val;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                for(Object o:c){
                    if(!MapView.this.containsKey(o)){
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean addAll(Collection<? extends K> c) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                Iterator<K> it=new KeyIterator();
                boolean val=false;
                for(K k=it.next();it.hasNext();k=it.next()){
                    if(!c.contains(k)){
                        it.remove();
                        val=true;
                    }
                }
                return val;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                boolean val=false;
                for(Object o:c){
                    if(MapView.this.containsKey(o)){
                        MapView.this.remove(o);
                        val=true;
                    }
                }
                return val;
            }

            @Override
            public void clear() {
                MapView.this.clear();
            }

        }

        /**
         * An iterator for key set for MapUpdate.
         * 
         * Returns the elements in the order original elements (skipping the removed elements) then added elements.
         */
        private class KeyIterator implements Iterator<K> {

            /**flag for if we are iterating over additions*/
            private boolean addition = false;
            /**the current iterator over elements from the base sets*/
            private Iterator<K> it = getOriginal().keySet().iterator();
            /**flag for having run out of elements*/
            private boolean finished = false;
            /**flag for having a next element ready*/
            private boolean nextReady = false;
            /**the next element to return from this iterator*/
            private K next;
            /**the current element returned from this iterator*/
            private K current;

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
                            it = additions.keySet().iterator();
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
            public K next() {
                if (hasNext()) {
                    current = next;
                    nextReady = false;
                    return current;
                } else {
                    throw new NoSuchElementException();
                }
            }

            /**
             * Removes from the underlying map the last element returned by the iterator.
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

        /**An entry set implementation for MapUpdate*/
        private class EntrySet extends AbstractSet<Map.Entry<K,V>>{

            @Override
            public int size() {
                return MapView.this.size();
            }

            @Override
            public boolean isEmpty() {
                return MapView.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                if(o instanceof Map.Entry){
                    Map.Entry<?,?> ob=(Map.Entry<?,?>) o;
                    return MapView.this.containsKey(ob.getKey()) && MapView.this.get(ob.getKey()).equals(ob.getValue());
                }
                return false;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new EntryIterator();
            }

            @Override
            public boolean add(Map.Entry<K, V> e) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean remove(Object o) {
                if(o instanceof Map.Entry){
                    Map.Entry<?,?> ob=(Map.Entry<?,?>) o;
                    if(MapView.this.containsKey(ob.getKey()) && MapView.this.get(ob.getKey()).equals(ob.getValue())){
                        MapView.this.remove(ob.getKey());
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void clear() {
                MapView.this.clear();
            }

        }

        /**a class to hold an entry in the MapUpdate*/
        private class Entry implements Map.Entry<K,V>{
            private final K k;
            public Entry(K k){
                this.k=k;
            }
            @Override
            public K getKey() {
                return k;
            }

            @Override
            public V getValue() {
                return MapView.this.get(k);
            }

            @Override
            public V setValue(V value) {
                return MapView.this.put(k, value);
            }

            @Override
            public final boolean equals(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                Object k1 = getKey();
                Object k2 = e.getKey();
                if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                    Object v1 = getValue();
                    Object v2 = e.getValue();
                    if (v1 == v2 || (v1 != null && v1.equals(v2)))
                        return true;
                }
                return false;
            }

            @Override
            public final int hashCode() {
                return (k==null   ? 0 : k.hashCode()) ^
                       (getValue()==null ? 0 : getValue().hashCode());
            }

            @Override
            public final String toString() {
                return getKey() + "=" + getValue();
            }
        }

        /**an entry iterator for MapUpdate that wraps a KeyIterator and returns Entry objects*/
        private class EntryIterator implements Iterator<Map.Entry<K,V>>{

            KeyIterator it=new KeyIterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Map.Entry<K,V> next() {
                return new Entry(it.next());
            }

            @Override
            public void remove() {
                it.remove();
            }

        }
        
        /**a values collection implementation for MapUpdate*/
        private class Values extends AbstractCollection<V>{

            @Override
            public int size() {
                return MapView.this.size();
            }

            @Override
            public boolean isEmpty() {
                return MapView.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return MapView.this.containsValue(o);
            }

            @Override
            public Iterator<V> iterator() {
                return new ValuesIterator();
            }

            @Override
            public boolean add(V e) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean addAll(Collection<? extends V> c) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public void clear() {
                MapView.this.clear();
            }
            
        }
        
        /**an value iterator for MapUpdate that wraps a KeyIterator and returns Entry objects*/
        private class ValuesIterator implements Iterator<V>{

            KeyIterator it=new KeyIterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public V next() {
                return MapView.this.get(it.next());
            }

            @Override
            public void remove() {
                it.remove();
            }
        }

        /**The KeySet for this MapView*/
        KeySet keySet=null;
        @Override
        public Set<K> keySet() {
            if(keySet==null){
                keySet=new KeySet();
            }
            return keySet;
        }

        /**The values collection for this MapView*/
        Values values=null;
        @Override
        public Collection<V> values() {
            if(values==null){
                values=new Values();
            }
            return values;
        }

        /**The EntrySet for this MapView*/
        EntrySet entrySet=null;
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            if(entrySet==null){
                entrySet=new EntrySet();
            }
            return entrySet;
        }
    
    }
}
