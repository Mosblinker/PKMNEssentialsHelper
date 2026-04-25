/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Mosblinker
 * @param <K>
 */
public class ColorRGBMap<K> extends AbstractMap<K,Integer> {
    
    private final Map<K,Color> map;
    
    public ColorRGBMap(Map<K,Color> map){
        this.map = Objects.requireNonNull(map);
    }
    
    public Map<K,Color> getColors(){
        return map;
    }
    
    private Set<Entry<K, Integer>> entrySet = null;

    @Override
    public Set<Entry<K, Integer>> entrySet() {
        if (entrySet == null)
            entrySet = new AbstractSet<>(){
                @Override
                public Iterator<Entry<K, Integer>> iterator() {
                    return new Iterator<>(){
                        Iterator<Entry<K, Color>> itr = map.entrySet().iterator();
                        @Override
                        public boolean hasNext() {
                            return itr.hasNext();
                        }
                        @Override
                        public Entry<K, Integer> next() {
                            Entry<K, Color> entry = itr.next();
                            return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(),
                                    (entry.getValue()!=null)?entry.getValue().getRGB():null);
                        }
                        @Override
                        public void remove(){
                            itr.remove();
                        }
                    };
                }
                @Override
                public int size() {
                    return map.entrySet().size();
                }
            };
        return entrySet;
    }
    @Override
    public Integer get(Object key){
        Color value = map.get(key);
        return (value!=null)?value.getRGB():null;
    }
    @Override
    public boolean containsKey(Object key){
        return map.containsKey(key);
    }
    @Override
    public Integer put(K key, Integer value){
        Color old = map.put(key, (value!=null)?new Color(value,true):null);
        return (old!=null)?old.getRGB():null;
    }
    @Override
    public Integer remove(Object key){
        Color value = map.remove(key);
        return (value!=null)?value.getRGB():null;
    }
    @Override
    public void clear(){
        map.clear();
    }
}
