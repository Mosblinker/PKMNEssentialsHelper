/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import pkmn.essentials.ColorRGBList;
import pkmn.essentials.ColorRGBMap;
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public class PalettePkmnSpriteIcon extends PkmnSpriteIcon{
    
    private BufferedImage image;
    
    private PaletteList palette = new PaletteList();
    
    private List<Integer> paletteRGB = new ColorRGBList(palette);
    
    private int[][] indexes;
    
    public PalettePkmnSpriteIcon(File file, String description, BufferedImage image, double scale, Collection<? extends Color> palette){
        super(file,description,image,scale);
        this.image = image;
        indexes = new int[image.getWidth()][image.getHeight()];
        if (palette != null)
            this.palette.palette.addAll(palette);
        PalettePkmnSpriteIcon.this.updatePalette();
    }
    
    public PalettePkmnSpriteIcon(File file, String description, BufferedImage image, double scale){
        this(file,description,image,scale,null);
    }
    
    public PalettePkmnSpriteIcon(File file, String description, BufferedImage image, Collection<? extends Color> palette){
        this(file,description,image,1,palette);
    }
    
    public PalettePkmnSpriteIcon(File file, String description, BufferedImage image){
        this(file,description,image,null);
    }
    
    public PalettePkmnSpriteIcon(String fileName, String description, BufferedImage image, double scale, Collection<? extends Color> palette){
        this(new File(fileName),description, image,scale,palette);
    }
    
    public PalettePkmnSpriteIcon(String fileName, String description, BufferedImage image, double scale){
        this(fileName,description,image,scale,null);
    }
    
    public PalettePkmnSpriteIcon(String fileName, String description, BufferedImage image, Collection<? extends Color> palette){
        this(fileName,description,image,1,palette);
    }
    
    public PalettePkmnSpriteIcon(String fileName, String description, BufferedImage image){
        this(fileName,description,image,null);
    }
    
    public PalettePkmnSpriteIcon(File file, BufferedImage image, double scale, Collection<? extends Color> palette){
        super(file, new ImageIcon(image,file.getName()),scale);
        this.image = image;
        indexes = new int[image.getWidth()][image.getHeight()];
        if (palette != null)
            this.palette.palette.addAll(palette);
        PalettePkmnSpriteIcon.this.updatePalette();
    }
    
    public PalettePkmnSpriteIcon(File file, BufferedImage image, double scale){
        this(file,image,scale,null);
    }
    
    public PalettePkmnSpriteIcon(File file, BufferedImage image, Collection<? extends Color> palette){
        this(file,image,1,palette);
    }
    
    public PalettePkmnSpriteIcon(File file, BufferedImage image){
        this(file,image,1);
    }
    
    public PalettePkmnSpriteIcon(PalettePkmnSpriteIcon icon){
        super(icon.getFile(),icon.getDescription(),PkmnHelperUtilities.createCopy(icon.image),
                icon.getScaleX());
        this.image = (BufferedImage)getIcon().getImage();
        super.setIndex(icon.getIndex());
        this.palette.palette.addAll(icon.palette.palette);
        super.setFemale(icon.isFemale());
        indexes = new int[icon.image.getWidth()][icon.image.getHeight()];
        for (int x = 0; x < icon.image.getWidth(); x++){
            System.arraycopy(icon.indexes[x], 0, indexes[x], 0, icon.image.getHeight());
        }
    }
    
    public PalettePkmnSpriteIcon(PkmnSpriteIcon icon, Collection<? extends Color> palette){
        this(icon.getFile(),icon.getDescription(),PkmnHelperUtilities.createCopy(icon.getImage()),
                icon.getScaleX(),palette);
        this.image = (BufferedImage)getIcon().getImage();
        super.setIndex(icon.getIndex());
        super.setFemale(icon.isFemale());
        if (icon instanceof PalettePkmnSpriteIcon){
            PalettePkmnSpriteIcon paletteIcon = (PalettePkmnSpriteIcon) icon;
            this.palette.palette.clear();
            this.palette.palette.addAll(paletteIcon.palette.palette);
            if (palette != null)
                this.palette.palette.addAll(palette);
            for (int x = 0; x < paletteIcon.getWidth(); x++){
                System.arraycopy(paletteIcon.indexes[x], 0, indexes[x], 0, paletteIcon.getHeight());
            }
        }
    }
    
    public PalettePkmnSpriteIcon(PkmnSpriteIcon icon){
        this(icon,null);
    }
    @Override
    public Image getImage(){
        return image;
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
    
    public int getPaletteIndex(int x, int y){
        return indexes[x][y];
    }
    
    public int[] getPaletteIndex(int startX, int startY, int w, int h, int[] indexes, int offset, int scansize){
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
    
    public void setPaletteIndex(int x, int y, int index){
        Objects.checkIndex(index, palette.size());
        indexes[x][y] = index;
        image.setRGB(x, y, paletteRGB.get(index));
    }
    
    public void setPaletteIndex(int startX, int startY, int w, int h, int[] indexes, int offset, int scansize){
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
    
    public boolean updatePalette(){
            // This gets if a change occurred to the image
        boolean changed = false;
            // A map to map the RGB values to their indexes
        Map<Integer,Integer> rgb = new LinkedHashMap<>();
            // Go through and put the existing palette into the map
        for (int i = 0; i < paletteRGB.size(); i++)
                // If the map does not already contain the value
            if (!rgb.containsKey(paletteRGB.get(i)))
                rgb.put(paletteRGB.get(i), i);
            // An array to get a line of data from the image
        int[] rgbData = new int[image.getWidth()];
            // Go through the image line by line
        for (int y = 0; y < image.getHeight(); y++){
                // Get a line of data from the image
            rgbData = image.getRGB(0, y, rgbData.length, 1, rgbData, 0, 1);
                // Go through the line of data
            for (int x = 0; x < rgbData.length; x++){
                    // Get the RGB value of the current pixel
                int rgbValue = rgbData[x];
                    // If the pixel has no color
                if (PkmnHelperUtilities.hasNoColor(rgbValue)){
                        // The pixel is fully transparent
                    rgbValue = 0;
                }   // Get the index of the RGB value
                Integer index = rgb.get(rgbValue);
                    // If there is no index for this RGB value
                if (index == null){
                        // Add it to the map
                    index = rgb.size();
                    rgb.put(rgbValue, index);
                    changed = true;
                }   // If the index of the pixel is to change
                if (indexes[x][y] != index)
                    changed = true;
                    // Set the index of the pixel
                indexes[x][y] = index;
            }
        }   // Remove all existing RGB values
        rgb.keySet().removeAll(paletteRGB);
            // The palette changed if any RGB values are added
        changed |= !rgb.isEmpty();
            // Go through and add all the new colors to the palette
        for (Integer i : rgb.keySet()){
            palette.palette.add(new Color(i,true));
        }
        return changed;
    }
    
    public void addAbsentColors(Collection<? extends Color> c){
        c = new LinkedHashSet<>(c);
        c.removeAll(palette.palette);
        palette.palette.addAll(c);
    }
    
    public Map<Integer,Color> changeColors(Map<Integer,Color> change){
        Map<Integer,Color> inverse = new HashMap<>();
        for (Map.Entry<Integer,Color> entry : change.entrySet()){
            if (entry.getKey() < palette.size()){
                Color old = palette.palette.set(entry.getKey(), entry.getValue());
                inverse.put(entry.getKey(), old);
            }
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
    
//    public void setPalette(List<Color> palette){
//        this.palette.palette.clear();
//        this.palette.palette.addAll(palette);
//        for (int x = 0; x < indexes.length; x++){
//            for (int y = 0; y < indexes.length; y++){
//                
//            }
//        }
//    }
    
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
            int size = size();
            palette.add(index, Objects.requireNonNull(element));
            if (index < size){
                for (int[] indexe : indexes) {
                    for (int y = 0; y < indexe.length; y++) {
                        if (indexe[y] >= index) {
                            indexe[y]++;
                        }
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
        @Override
        public int indexOf(Object o){
            return palette.indexOf(o);
        }
        @Override
        public int lastIndexOf(Object o){
            return palette.lastIndexOf(o);
        }
        @Override
        public boolean contains(Object o){
            return palette.contains(o);
        }
    }
}
