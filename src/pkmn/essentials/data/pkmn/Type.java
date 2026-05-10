/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.swing.ImageIcon;
import pkmn.essentials.data.AbstractPBSElement;

/**
 *
 * @author Mosblinker
 */
public class Type extends AbstractPBSElement implements Comparable<Type>{
    
    public static final String PBS_FILE_TEMPLATE = "types";
    
    public static final String ICONS = "Graphics"+File.separator+"UI"+File.separator+"types.png";
    
    public static final String[] PROPERTIES = {
        "Name",
        "IconPosition",
        "IsSpecialType",
        "IsPseudoType",
        "Flags",
        "Weaknesses",
        "Resistances",
        "Immunities"
    };
    
    private Integer iconPosition = null;
    
    private Boolean isSpecialType = null;
    
    private Boolean isPseudoType = null;
    
    private List<String> flags = new ArrayList<>();
    
    private Set<Type> weaknesses = new LinkedHashSet<>();
    
    private String weaknessesStr = null;
    
    private Set<Type> resistances = new LinkedHashSet<>();
    
    private String resistancesStr = null;
    
    private Set<Type> immunities = new LinkedHashSet<>();
    
    private String immunitiesStr = null;
    
    public Type(){
        super();
    }
    
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
        this.weaknessesStr = type.weaknessesStr;
        this.resistancesStr = type.resistancesStr;
        this.immunitiesStr = type.immunitiesStr;
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
        if (position != null && position < 0)
            throw new IllegalArgumentException();
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
     * A set of types that will inflict double damage (or in other words, are 
     * super effective) against a Pokemon of this type.
     * @return 
     */
    public Set<Type> getWeaknesses(){
        return weaknesses;
    }
    /**
     * A set of types that will inflict half damage (or in other words, are 
     * not very effective) against a Pokemon of this type.
     * @return 
     */
    public Set<Type> getResistances(){
        return resistances;
    }
    /**
     * A set of types that cannot inflict any damage on a Pokemon of this type.
     * @return 
     */
    public Set<Type> getImmunities(){
        return immunities;
    }
    @Override
    public int compareTo(Type o) {
        return getID().compareTo(o.getID());
    }
    @Override
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTIES[0] + " = " + getName();
        if (isIconPositionSet())
            str += System.lineSeparator()+PROPERTIES[1] + " = " + getIconPosition();
        if (isSpecialTypeSet())
            str += System.lineSeparator()+PROPERTIES[2] + " = " + isSpecialType();
        if (isPseudoTypeSet())
            str += System.lineSeparator()+PROPERTIES[3] + " = " + isPseudoType();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTIES[4] + " = " + AbstractPBSElement.getFlagString(flags);
        if (!getWeaknesses().isEmpty())
            str += System.lineSeparator()+PROPERTIES[5] + " = " + AbstractPBSElement.toIDString(getWeaknesses());
        else if (weaknessesStr != null)
            str += System.lineSeparator()+PROPERTIES[5] + " = " + weaknessesStr;
        if (!getResistances().isEmpty())
            str += System.lineSeparator()+PROPERTIES[6] + " = " + AbstractPBSElement.toIDString(getResistances());
        else if (resistancesStr != null)
            str += System.lineSeparator()+PROPERTIES[6] + " = " + resistancesStr;
        if (!getImmunities().isEmpty())
            str += System.lineSeparator()+PROPERTIES[7] + " = " + AbstractPBSElement.toIDString(getImmunities());
        else if (resistancesStr != null)
            str += System.lineSeparator()+PROPERTIES[7] + " = " + immunitiesStr;
        return str;
    }
    
    @Override
    protected void reset(){
        super.reset();
        flags.clear();
        weaknesses.clear();
        resistances.clear();
        immunities.clear();
        iconPosition = null;
        isSpecialType = null;
        isPseudoType = null;
        weaknessesStr = null;
        resistancesStr = null;
        immunitiesStr = null;
    }

    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                setIconPosition(Integer.valueOf(value));
                break;
            case(2):
                setSpecialType(Boolean.valueOf(value));
                break;
            case(3):
                setPseudoType(Boolean.valueOf(value));
                break;
            case(4):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(5):
                this.weaknessesStr = value;
                break;
            case(6):
                this.resistancesStr = value;
                break;
           case(7):
               this.immunitiesStr = value;
        }
        if (propertyIndex == getPropertyNames().length)
            setID(value);
    }
    
    public void finishLoad(Collection<Type> types){
        loadTypes(types,weaknessesStr,weaknesses);
        loadTypes(types,resistancesStr,resistances);
        loadTypes(types,immunitiesStr,immunities);
        weaknessesStr = null;
        resistancesStr = null;
        immunitiesStr = null;
    }
    
    private void loadTypes(Collection<Type> types, String values, Collection<Type> target){
        if (values == null || values.isBlank())
            return;
        String[] arr = values.split(",");
        for (String str : arr){
            Type type = (Type) AbstractPBSElement.getMatch(str.trim(), types);
            if (type != null)
                target.add(type);
        }
    }
    @Override
    protected String[] getPropertyNames() {
        return PROPERTIES;
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
