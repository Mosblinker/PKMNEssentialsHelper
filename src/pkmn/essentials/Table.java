/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public class Table<E> implements Iterable<E> {
    
    private int xSize = 0;
    
    private int ySize = 0;
    
    private List<List<List<E>>> data = new ArrayList<>();
    
    public Table(int layers, int rows, int columns){
        xSize = columns;
        ySize = rows;
        for (int z = 0; z < layers; z++){
            List<List<E>> l = new ArrayList<>();
            for (int y = 0; y < ySize; y++){
                l.add(new ArrayList<>());
            }
            data.add(l);
        }
    }
    
    public Table(){
        this(0,0,0);
    }
    
    public Table(Collection<E> c){
        xSize = c.size();
        ySize = 1;
        List<E> l = new ArrayList<>(c);
        List<List<E>> l1 = new ArrayList<>();
        l1.add(l);
        data.add(l1);
    }
    
    public Table(Table<E> table){
        xSize = table.xSize;
        ySize = table.ySize;
        for (List<List<E>> d : table.data){
            List<List<E>> list = new ArrayList<>();
            for (List<E> l : d){
                list.add(new ArrayList<>(l));
            }
            data.add(list);
        }
    }
    
    public int size() {
        return xSize * ySize * data.size();
    }
    
    public int getColumnCount(){
        return xSize;
    }
    
    public int getRowCount(){
        return ySize;
    }
    
    public int getLayerCount(){
        return data.size();
    }

    public boolean isEmpty() {
        return xSize == 0 || ySize == 0 || data.isEmpty();
    }

    public boolean contains(Object o) {
        for (List<List<E>> l : data){
            if (o == null && l.size() < ySize)
                return true;
            for (List<E> r : l){
                if (o == null && r.size() < xSize)
                    return true;
                if (r.contains(o))
                    return true;
            }
        }
        return false;
    }
    
    public E get(int x, int y, int z){
        Objects.checkIndex(x, xSize);
        Objects.checkIndex(y, ySize);
        Objects.checkIndex(z, data.size());
        List<List<E>> l = data.get(z);
        if (y < l.size()){
            List<E> r = l.get(y);
            if (x < r.size())
                return r.get(x);
        }
        return null;
    }
    
    public E set(int x, int y, int z, E element){
        Objects.checkIndex(x, xSize);
        Objects.checkIndex(y, ySize);
        Objects.checkIndex(z, data.size());
        List<List<E>> l = data.get(z);
        while (l.size() <= y)
            l.add(new ArrayList<>());
        List<E> r = l.get(y);
        while (r.size() <= x)
            r.add(null);
        return r.set(x, element);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>(){
            int x = 0;
            int y = 0;
            int z = 0;
            @Override
            public boolean hasNext() {
                return z < data.size();
            }
            @Override
            public E next() {
                if (hasNext())
                    throw new NoSuchElementException();
                E element = get(x,y,z);
                x++;
                if (x >= xSize){
                    y++;
                    x = 0;
                }
                if (y >= ySize){
                    z++;
                    y = 0;
                }
                return element;
            }
        };
    }
    
    public Object[][][] toArray(){
        Object[][][] table = new Object[xSize][ySize][data.size()];
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                for (int z = 0; z < data.size(); z++)
                    table[x][y][z] = get(x,y,z);
            }
        }
        return table;
    }
    
    public void clear() {
        data.clear();
        xSize = ySize = 0;
    }
    
    public void addEmptyLayer(int index){
        Objects.checkIndex(index, data.size()+1);
        data.add(index,new ArrayList<>());
    }
    
    public void addEmptyLayer(){
        addEmptyLayer(data.size());
    }
    
    public void addEmptyRow(int index){
        Objects.checkIndex(index, ySize+1);
        for (List<List<E>> l : data){
            while (l.size() < index)
                l.add(new ArrayList<>());
            l.add(index,new ArrayList<>());
        }
    }
    
    public void addEmptyRow(){
        addEmptyRow(ySize);
    }
    
    public void addEmptyColumn(int index){
        Objects.checkIndex(index, xSize+1);
        for (List<List<E>> l : data){
            for(List<E> r : l){
                while (r.size() < index)
                    r.add(null);
                r.add(index, null);
            }
        }
    }
    
    public void addEmptyColumn(){
        addEmptyColumn(xSize);
    }
    
    public void removeLayer(int index){
        Objects.checkIndex(index, data.size());
        data.remove(index);
    }
    
    public void removeRow(int index){
        Objects.checkIndex(index, ySize);
        for (List<List<E>> l : data){
            l.remove(index);
        }
    }
    
    public void removeColumn(int index){
        Objects.checkIndex(index, xSize);
        for (List<List<E>> l : data){
            for(List<E> r : l){
                r.remove(index);
            }
        }
    }
    
    public String toString(){
        if (isEmpty()){
            return "[]";
        }
        String str = "[" + System.lineSeparator();
        for (int z = 0; z < data.size(); z++){
            if (z != 0)
                str += ", ["+System.lineSeparator();
            for (int y = 0; y < ySize; y++){
                if (y != 0)
                    str += ","+System.lineSeparator();
                str += "    [";
                for (int x = 0; x < xSize; x++){
                    if (x != 0)
                        str += ", ";
                    str += Objects.toString(get(x,y,z));
                }
                str += "]";
            }
            str += System.lineSeparator() + "]";
        }
        return str;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (!(obj instanceof Table))
            return false;
        Table<?> table = (Table<?>) obj;
        if (xSize != table.xSize || ySize != table.ySize || table.data.size() != data.size())
            return false;
        for (int z = 0; z < data.size(); z++){
            for (int y = 0; y < ySize; y++){
                for (int x = 0; x < xSize; x++){
                    if (!Objects.equals(get(x,y,z),table.get(x, y, z)))
                        return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.xSize;
        hash = 47 * hash + this.ySize;
        hash = 47 * hash + this.data.size();
        for (int z = 0; z < data.size(); z++){
            for (int y = 0; y < ySize; y++){
                for (int x = 0; x < xSize; x++){
                    hash = 47 * hash + Objects.hashCode(get(x,y,z));
                }
            }
        }
        return hash;
    }
}
