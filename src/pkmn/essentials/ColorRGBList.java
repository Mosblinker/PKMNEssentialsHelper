/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public class ColorRGBList extends AbstractList<Integer>{
    
    private final List<Color> list;
    
    public ColorRGBList(List<Color> list){
        this.list = Objects.requireNonNull(list);
    }
    
    public List<Color> getColors(){
        return list;
    }
    @Override
    public Integer get(int index) {
        Color color = list.get(index);
        return (color == null)?null:color.getRGB();
    }
    @Override
    public int size() {
        return list.size();
    }
    @Override
    public void add(int index, Integer element){
        if (element == null)
            list.add(index, null);
        else
            list.add(index, new Color(element,true));
    }
    @Override
    public Integer set(int index, Integer element){
        Color old;
        if (element == null)
            old = list.set(index, null);
        else
            old = list.set(index, new Color(element,true));
        return (old == null) ? null : old.getRGB();
    }
    @Override
    public Integer remove(int index) {
        Color color = list.remove(index);
        return (color == null) ? null : color.getRGB();
    }
    @Override
    public boolean remove(Object o){
        int index = indexOf(o);
        if (index < 0)
            return false;
        remove(index);
        return true;
    }
    @Override
    public void clear(){
        list.clear();
    }
}
