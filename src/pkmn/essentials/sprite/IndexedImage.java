/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import pkmn.essentials.ColorRGBList;
import pkmn.essentials.ColorRGBMap;

/**
 *
 * @author Mosblinker
 */
public class IndexedImage{
    
    private BufferedImage image;
    
    private PaletteList palette = new PaletteList();
    
    private List<Integer> paletteRGB = new ColorRGBList(palette);
    
    private int[][] indexes;
    
    public IndexedImage(BufferedImage image){
        this.image = image;
        indexes = new int[image.getWidth()][image.getHeight()];
        IndexedImage.this.updatePalette();
    }
    
    public IndexedImage(int width, int height, int imageType){
        this(new BufferedImage(width,height,imageType));
    }
    
    public IndexedImage(int width, int height){
        this(width,height,BufferedImage.TYPE_INT_ARGB);
    }
    
    public IndexedImage(IndexedImage image){
        this.image = new BufferedImage(image.getWidth(),image.getHeight(),image.getBufferedImage().getType());
        image.image.copyData(this.image.getRaster());
        this.palette.palette.addAll(image.palette.palette);
        indexes = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++){
            System.arraycopy(indexes[x], 0, image.indexes[x], 0, image.getHeight());
        }
    }

    public BufferedImage getBufferedImage(){
        return image;
    }
    
    public int getRGB(int x, int y){
        return image.getRGB(x, y);
    }
    
    public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize){
        return image.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
    }
    
    public List<Color> getPalette(){
        return palette;
    }
    
    public List<Integer> getPaletteRGB(){
        return paletteRGB;
    }
    
    public int getIndex(int x, int y){
        return indexes[x][y];
    }
    
    public int[] getIndex(int startX, int startY, int w, int h, int[] indexes, int offset, int scansize){
        Objects.checkFromIndexSize(startX, w, getWidth());
        Objects.checkFromIndexSize(startY, h, getHeight());
        if (indexes == null || indexes.length+offset < w*h*scansize)
            indexes = new int[offset+w*h*scansize];
        int index = offset;
        for (int y = startY; y < startY+h; y++, index+=scansize){
            for (int x = startX; x < startX+w; x++){
                indexes[index] = this.indexes[x][y];
            }
        }
        return indexes;
    }
    
    private int setIndexRGB(int x, int y, int rgb){
        int index = paletteRGB.indexOf(rgb);
        if (index < 0){
            index = palette.size();
            paletteRGB.add(rgb);
        }
        indexes[x][y] = index;
        return index;
    }
    
    public void setRGB(int x, int y, int rgb){
        image.setRGB(x, y, rgb);
        setIndexRGB(x,y,rgb);
    }
    
    public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize){
        Objects.checkFromIndexSize(startX, w, getWidth());
        Objects.checkFromIndexSize(startY, h, getHeight());
        image.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
        HashMap<Integer,Integer> paletteIndexes = new HashMap<>();
        int index = offset;
        for (int y = startY; y < startY+h; y++, index+=scansize){
            for (int x = startX; x < startX+w; x++){
                Integer paletteIndex = paletteIndexes.get(rgbArray[index]);
                if (paletteIndex == null){
                    paletteIndex = setIndexRGB(x,y,rgbArray[index]);
                    paletteIndexes.put(rgbArray[index], paletteIndex);
                } else {
                    indexes[x][y] = paletteIndex;
                }
            }
        }
    }
    
    public void setIndex(int x, int y, int index){
        Objects.checkIndex(index, palette.size());
        indexes[x][y] = index;
        image.setRGB(x, y, paletteRGB.get(index));
    }
    
    public void setIndex(int startX, int startY, int w, int h, int[] indexes, int offset, int scansize){
        Objects.checkFromIndexSize(startX, w, getWidth());
        Objects.checkFromIndexSize(startY, h, getHeight());
        for (int i : indexes)
            Objects.checkIndex(i, palette.size());
        int index = offset;
        int[] rgb = new int[indexes.length];
        for (int y = startY; y < startY+h; y++, index+=scansize){
            for (int x = startX; x < startX+w; x++){
                this.indexes[x][y] = indexes[index];
                rgb[index] = paletteRGB.get(indexes[index]);
            }
        }
        image.setRGB(startX, startY, w, h, rgb, offset, scansize);
    }
    
    public int getWidth(){
        return image.getWidth();
    }
    public int getHeight(){
        return image.getHeight();
    }
    
    public void updatePalette(){
        Map<Integer,Integer> rgb = new LinkedHashMap<>();
        for (int i = 0; i < paletteRGB.size(); i++)
            rgb.put(paletteRGB.get(i), i);
        int[] rgbData = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++){
            rgbData = image.getRGB(0, y, rgbData.length, 1, rgbData, 0, 1);
            for (int x = 0; x < rgbData.length; x++){
                Integer index = rgb.get(rgbData[x]);
                if (index == null){
                    index = rgb.size();
                    rgb.put(rgbData[x], index);
                }
                indexes[x][y] = index;
            }
        }
        rgb.keySet().removeAll(paletteRGB);
        for (Integer i : rgb.keySet()){
            palette.palette.add(new Color(i,true));
        }
    }
    
    public Map<Integer,Color> changeColors(Map<Integer,Color> change){
        Map<Integer,Color> inverse = new HashMap<>();
        for (Map.Entry<Integer,Color> entry : change.entrySet()){
            Color old = palette.palette.set(entry.getKey(), entry.getValue());
            inverse.put(entry.getKey(), old);
        }
        ColorRGBMap<Integer> rgb = new ColorRGBMap<>(change);
        int[] rgbData = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++){
            rgbData = image.getRGB(0, y, image.getWidth(), 1, rgbData, 0, 1);
            for (int x = 0; x < image.getWidth(); x++){
                for (Map.Entry<Integer,Integer> entry : rgb.entrySet()){
                    if (indexes[x][y] == entry.getKey()){
                        rgbData[x] = entry.getValue();
                    }
                }
            }
            image.setRGB(0, y, image.getWidth(), 1, rgbData, 0, 1);
        }
        return inverse;
    }
    
    private class PaletteList extends AbstractList<Color>{
        
        private List<Color> palette = new ArrayList<>();
        
        private void changeColor(int index, int rgb, boolean remove){
            int[] rgbData = new int[image.getWidth()];
            for (int y = 0; y < image.getHeight(); y++){
                rgbData = image.getRGB(0, y, image.getWidth(), 1, rgbData, 0, 1);
                for (int x = 0; x < image.getWidth(); x++){
                    if (indexes[x][y] == index){
                        rgbData[x] = rgb;
                        if (remove)
                            indexes[x][y] = 0;
                    } else if (remove && indexes[x][y] > index)
                        indexes[x][y]--;
                }
                image.setRGB(0, y, image.getWidth(), 1, rgbData, 0, 1);
            }
        }

        @Override
        public Color get(int index) {
            return palette.get(index);
        }
        @Override
        public int size() {
            return palette.size();
        }
        @Override
        public void add(int index, Color element){
            palette.add(index, Objects.requireNonNull(element));
            for (int[] indexe : indexes) {
                for (int y = 0; y < indexe.length; y++) {
                    if (indexe[y] >= index) {
                        indexe[y]++;
                    }
                }
            }
        }
        @Override
        public Color set(int index, Color element){
            Color old = palette.set(index, Objects.requireNonNull(element));
            changeColor(index,element.getRGB(),false);
            return old;
        }
        @Override
        public Color remove(int index){
            Color color = palette.remove(index);
            int rgb = 0;
            if (!palette.isEmpty())
                rgb = palette.get(0).getRGB();
            changeColor(index,rgb,true);
            return color;
        }
        @Override
        public boolean remove(Object o){
            int index = indexOf(o);
            if (index < 0)
                return false;
            remove(index);
            return true;
        }
    }
}
