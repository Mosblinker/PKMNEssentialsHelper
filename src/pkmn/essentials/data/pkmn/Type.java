/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import pkmn.essentials.data.AbstractPBSData;

/**
 *
 * @author Mosblinker
 */
public class Type extends AbstractPBSData implements Comparable<Type>{
    
    public static final String PBS_FILE = "types.txt";
    
    public static final String ICONS = "Graphics"+File.separator+"UI"+File.separator+"types.png";
    
    private Integer iconPosition = null;
    
    private Boolean isSpecialType = null;
    
    private Boolean isPseudoType = null;
    
    private List<String> flags = new ArrayList<>();
    
    private List<Type> weaknesses = new ArrayList<>();
    
    private List<Type> resistances = new ArrayList<>();
    
    private List<Type> immunities = new ArrayList<>();
    
    public Type(String id){
        super(id);
    }
    
    public Type(Type type){
        super(type);
        this.iconPosition = type.iconPosition;
        this.isSpecialType = type.isSpecialType;
        this.isPseudoType = type.isPseudoType;
        this.flags.addAll(type.flags);
        this.weaknesses.addAll(type.weaknesses);
        this.resistances.addAll(type.resistances);
        this.immunities.addAll(type.immunities);
    }
    /**
     * The internal ID for this type. This is how the scripts will refer to the 
     * type. Each type much have a different ID. This is surrounded by square 
     * brackets in the PBS file.
     * @return 
     */
    @Override
    public String getID(){
        return super.getID();
    }
    /**
     * The name of this type, as seen by the player.
     * @return 
     */
    @Override
    public String getName(){
        return super.getName();
    }
    /**
     * A number that determines which icon to use to represent this type. This 
     * is also used by the move Hidden Power when determining its type.
     * @return 
     */
    public int getIconPosition(){
        return (iconPosition==null)?0:iconPosition;
    }
    /**
     * 
     * @param position 
     */
    public void setIconPosition(Integer position){
        this.iconPosition = position;
    }
    /**
     * 
     * @return 
     */
    public boolean isIconPositionSet(){
        return iconPosition != null;
    }
    /**
     * Whether moves of this type will use the special stats or physical stats 
     * for damage calculation.
     * @return 
     */
    public boolean isSpecialType(){
        return (isSpecialType!=null)?isSpecialType:false;
    }
    /**
     * 
     * @param specialType 
     */
    public void setSpecialType(Boolean specialType){
        this.isSpecialType = specialType;
    }
    /**
     * 
     * @return 
     */
    public boolean isSpecialTypeSet(){
        return isSpecialType != null;
    }
    /**
     * Whether this type is a pseudotype, or in other words not a "proper" type.
     * No Pokemon should have a pseudotype as one of it's types.
     * @return 
     */
    public boolean isPseudoType(){
        return (isPseudoType!=null)?isPseudoType:false;
    }
    /**
     * 
     * @param value 
     */
    public void setPseudoType(Boolean value){
        isPseudoType = value;
    }
    /**
     * 
     * @return 
     */
    public boolean isPseudoTypeSet(){
        return isPseudoType != null;
    }
    /**
     * Labels that can be applied to a type to make it behave differently.
     * @return 
     */
    public List<String> getFlags(){
        return flags;
    }
    /**
     * A list of types that will inflict double damage (or in other words, are 
     * super effective) against a Pokemon of this type.
     * @return 
     */
    public List<Type> getWeaknesses(){
        return weaknesses;
    }
    /**
     * A list of types that will inflict half damage (or in other words, are 
     * not very effective) against a Pokemon of this type.
     * @return 
     */
    public List<Type> getResistances(){
        return resistances;
    }
    /**
     * A list of types that cannot inflict any damage on a Pokemon of this type.
     * @return 
     */
    public List<Type> getImmunities(){
        return immunities;
    }
    @Override
    public int compareTo(Type o) {
        return getID().compareTo(o.getID());
    }
    
    private static ImageIcon getTypeIcon(BufferedImage icons, int height, int index, String desc){
        BufferedImage img = icons.getSubimage(0, height*index, icons.getHeight(), height);
        return new ImageIcon(img, desc);
    }
    
    public static List<ImageIcon> getTypeIcons(BufferedImage icons, int typeCount){
        Objects.requireNonNull(icons);
        int height = Math.floorDiv(icons.getHeight(), typeCount);
        List<ImageIcon> iconList = new ArrayList<>();
        for (int i = 0; i < typeCount; i++){
            iconList.add(getTypeIcon(icons,height,i,null));
        }
        return iconList;
    }
    
    public static Map<Type,ImageIcon> getTypeIcons(BufferedImage icons, List<Type> types){
        Objects.requireNonNull(icons);
        Objects.requireNonNull(types);
        Map<Type,ImageIcon> iconMap = new LinkedHashMap<>();
        int height = Math.floorDiv(icons.getHeight(), types.size());
        for (Type type : types){
            iconMap.put(type, getTypeIcon(icons,height,type.getIconPosition(),type.getName()));
        }
        return iconMap;
    }
}
