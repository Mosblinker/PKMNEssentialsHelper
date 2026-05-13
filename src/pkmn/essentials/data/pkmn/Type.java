/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import pkmn.essentials.data.AbstractPBSElement;
import pkmn.essentials.data.PBSData;

/**
 *
 * @author Mosblinker
 */
public class Type extends AbstractPBSElement implements Comparable<Type>{
    
    public static final String PBS_FILE_TEMPLATE = "types";
    
    public static final String ICONS = "Graphics"+File.separator+"UI"+File.separator+"types.png";
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "IconPosition",
        "IsSpecialType",
        "IsPseudoType",
        "Flags",
        "Weaknesses",
        "Resistances",
        "Immunities"
    };
    
    public static final String FLAG_FILE = "type_flags.txt";
    
    private static Map<String,String> flagMap = new LinkedHashMap<>();
    
    public static Map<String, String> getFlagMap(){
        return flagMap;
    }
    
    private Integer iconPosition = null;
    
    private Boolean isSpecialType = null;
    
    private Boolean isPseudoType = null;
    
    private Set<Type> weaknesses = new LinkedHashSet<>();
    
    private String weaknessesStr = null;
    
    private Set<Type> resistances = new LinkedHashSet<>();
    
    private String resistancesStr = null;
    
    private Set<Type> immunities = new LinkedHashSet<>();
    
    private String immunitiesStr = null;
    
    private ImageIcon icon = null;
    
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
        this.weaknesses.addAll(type.weaknesses);
        this.resistances.addAll(type.resistances);
        this.immunities.addAll(type.immunities);
        this.weaknessesStr = type.weaknessesStr;
        this.resistancesStr = type.resistancesStr;
        this.immunitiesStr = type.immunitiesStr;
        this.icon = type.icon;
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
            throw new IllegalArgumentException("IconPosition " + position);
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
    @Override
    public List<String> getFlags(){
        return super.getFlags();
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
    
    public ImageIcon getIcon(){
        return icon;
    }
    
    public void setIcon(ImageIcon icon){
        this.icon = icon;
    }
    @Override
    public int compareTo(Type o) {
        return getID().compareTo(o.getID());
    }
    @Override
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getName();
        if (isIconPositionSet())
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getIconPosition();
        if (isSpecialTypeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + isSpecialType();
        if (isPseudoTypeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + isPseudoType();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[4] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (!getWeaknesses().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[5] + " = " + AbstractPBSElement.toIDString(getWeaknesses());
        else if (weaknessesStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[5] + " = " + weaknessesStr;
        if (!getResistances().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[6] + " = " + AbstractPBSElement.toIDString(getResistances());
        else if (resistancesStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[6] + " = " + resistancesStr;
        if (!getImmunities().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[7] + " = " + AbstractPBSElement.toIDString(getImmunities());
        else if (resistancesStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[7] + " = " + immunitiesStr;
        return str;
    }
    
    @Override
    protected void reset(){
        super.reset();
        getWeaknesses().clear();
        getResistances().clear();
        getImmunities().clear();
        setIconPosition(null);
        setSpecialType(null);
        setPseudoType(null);
        weaknessesStr = null;
        resistancesStr = null;
        immunitiesStr = null;
        setIcon(null);
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
    
    public void finishLoading(Collection<? extends Type> types){
        loadTypes(types,weaknessesStr,getWeaknesses());
        loadTypes(types,resistancesStr,getResistances());
        loadTypes(types,immunitiesStr,getImmunities());
        weaknessesStr = null;
        resistancesStr = null;
        immunitiesStr = null;
    }
    @Override
    public void finishLoading(PBSData data){
        finishLoading(data.getTypes());
    }
    
    private void loadTypes(Collection<? extends Type> types, String values, Collection<Type> target){
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
        return PROPERTY_NAMES;
    }
}
