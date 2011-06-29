/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.staircase27.TD.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <K> type of the keys
 * @param <V> type of the values
 * @author Simon Armstrong
 */
public final class MapUpdate<K,V>{

    private Map<K,V> original;
    private Map<K,V> updates=new HashMap<K, V>();
    private Map<K,V> additions=new HashMap<K, V>();
    private Set<K> removals=new HashSet<K>();
    private Set<MapUpdate<?,?>> linked=new HashSet<MapUpdate<?, ?>>(Arrays.asList(new MapUpdate<?, ?>[]{this}));
    
    public MapUpdate(Map<K,V> original){
        this.original=original;
    }

    public MapUpdate(Map<K,V> original,MapUpdate<?,?> link){
        this(original);
        link(link);
    }
    
    public void link(MapUpdate<?,?> link){
        link.linked.addAll(linked);
        linked=link.linked;
    }
    
    public void unlink(){
        linked.remove(this);
        this.linked=new HashSet<MapUpdate<?, ?>>(Arrays.asList(new MapUpdate<?, ?>[]{this}));
    }
    
    public void update(){
        for(MapUpdate<?,?> update:linked){
            update.doUpdate();
        }
    }
    
    private void doUpdate(){
        original.putAll(updates);
        original.putAll(additions);
        original.keySet().removeAll(removals);
    }
    
    MapView mapView=null;
    public Map<K,V> getMap(){
        if(mapView==null)
            mapView=new MapView();
        return mapView;
    }
    
    private class MapView implements Map<K,V>{
    
        @Override
        public int size() {
            return original.size()+additions.size()-removals.size();
        }

        @Override
        public boolean isEmpty() {
            return size()>0;
        }

        @Override
        public boolean containsKey(Object key) {
            return (original.containsKey(key)||additions.containsKey(key))&&!removals.contains(key);
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
                for(Map.Entry<K, V> entry:original.entrySet()){
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
                return original.get(key);
            }
        }

        @Override
        public V put(K key, V value) {
            if(!original.containsKey(key)){
                return updates.put(key, value);
            }else if(updates.containsKey(key)){
                return updates.put(key, value);
            }else if(removals.contains(key)){
                updates.put(key, value);
                removals.remove(key);
                return null;
            }else{
                updates.put(key, value);
                return original.get(key);
            }
        }

        @Override
        public V remove(Object key) {
            if(!original.containsKey(key)){
                return additions.remove(key);
            }else{
                K k=(K) key;
                if(updates.containsKey(key)){
                    removals.add(k);
                    return updates.remove(key);
                }else{
                    removals.add(k);
                    return original.get(key);
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
            removals.addAll(original.keySet());
        }

        private class KeySet implements Set<K>{

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
            public Object[] toArray() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public <T> T[] toArray(T[] a) {
                throw new UnsupportedOperationException("Not supported yet.");
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
        KeySet keySet=new KeySet();

        private class KeyIterator implements Iterator<K>{

            EntryIterator it=new EntryIterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public K next() {
                return it.next().getKey();
            }

            @Override
            public void remove() {
                it.remove();
            }

        }

        private class EntrySet implements Set<Map.Entry<K,V>>{

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
            public Object[] toArray() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public <T> T[] toArray(T[] a) {
                throw new UnsupportedOperationException("Not supported yet.");
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
            public boolean containsAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean addAll(Collection<? extends Map.Entry<K, V>> c) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
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
        EntrySet entrySet=new EntrySet();

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
        }

        private class EntryIterator implements Iterator<Map.Entry<K,V>>{
            boolean addition=true;
            Iterator<Map.Entry<K,V>> it=additions.entrySet().iterator();
            Map.Entry<K,V> current=null;
            Map.Entry<K,V> next=null;
            boolean nextReady=false;
            @Override
            public boolean hasNext() {
                while(!nextReady){
                    if(!it.hasNext()){
                        if(addition){
                            it=original.entrySet().iterator();
                        }else{
                            return false;
                        }
                    }else{
                        next=it.next();
                        if(!addition){
                            if(!removals.contains(next.getKey())){
                                nextReady=true;
                            }
                        }else{
                            nextReady=true;
                        }
                    }
                }
                return true;
            }

            @Override
            public Entry next() {
                if(hasNext()){
                    return new Entry(next.getKey());
                }else{
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {
                MapView.this.remove(current.getKey());
            }

        }

        @Override
        public Set<K> keySet() {
            return keySet;
        }

        @Override
        public Collection<V> values() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return entrySet;
        }
    
    }
}
