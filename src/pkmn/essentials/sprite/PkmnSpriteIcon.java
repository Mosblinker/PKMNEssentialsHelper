/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import icons.ScaledIcon;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.util.Objects;
import javax.swing.ImageIcon;

/**
 *
 * @author Mosblinker
 */
public class PkmnSpriteIcon extends ScaledIcon{
    
    private static String getDescription(String fileName){
        int index = fileName.lastIndexOf(File.separator);
        if (index >= 0)
            return fileName.substring(index);
        return fileName;
    }
    
    private File file;
    
    private boolean female;
    
    private int index = -1;
    
    public PkmnSpriteIcon(File file, ImageIcon icon, double scale){
        super(icon,scale);
        this.file = Objects.requireNonNull(file);
        setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }
    
    public PkmnSpriteIcon(File file, ImageIcon icon){
        this(file,icon,1);
    }
    
    public PkmnSpriteIcon(File file, String description, Image image, double scale){
        this(file,new ImageIcon(image,description),scale);
    }
    
    public PkmnSpriteIcon(File file, String description, Image image){
        this(file,description,image,1);
    }
    
    public PkmnSpriteIcon(String fileName, String description, Image image, double scale){
        this(new File(fileName),description, image,scale);
    }
    
    public PkmnSpriteIcon(String fileName, String description, Image image){
        this(fileName,description,image,1);
    }
    
    public PkmnSpriteIcon(String fileName, String description, double scale){
        this(new File(fileName),new ImageIcon(fileName,description),scale);
    }
    
    public PkmnSpriteIcon(String fileName, String description){
        this(fileName,description,1);
    }
    
    public PkmnSpriteIcon(String fileName, double scale){
        this(fileName,getDescription(fileName),scale);
    }
    
    public PkmnSpriteIcon(String fileName){
        this(fileName,1);
    }
    
    public PkmnSpriteIcon(File file, double scale){
        this(file, new ImageIcon(file.toString(),file.getName()),scale);
    }
    
    public PkmnSpriteIcon(File file){
        this(file,1);
    }
    
    public PkmnSpriteIcon(File file, Image image, double scale){
        this(file, new ImageIcon(image,file.getName()),scale);
    }
    
    public PkmnSpriteIcon(File file, Image image){
        this(file,image,1);
    }
    
    public Image getImage(){
        return getIcon().getImage();
    }
    
    @Override
    public ImageIcon getIcon(){
        return (ImageIcon) super.getIcon();
    }
    
    public String getDescription(){
        return getIcon().getDescription();
    }
    
    public void setDescription(String description){
        getIcon().setDescription(description);
    }
    
    public File getFile(){
        return file;
    }
    
    public void setFile(File file){
        Objects.requireNonNull(file);
        if (getDescription() == null || this.file.getName().equals(getDescription()))
            setDescription(file.getName());
        this.file = file;
    }
    
    public boolean isFemale(){
        return female;
    }
    
    public void setFemale(boolean value){
        this.female = value;
    }
    
    public int getIndex(){
        return index;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    @Override
    public String toString(){
        if (getDescription() != null)
            return getDescription();
        return super.toString();
    }
    
}
