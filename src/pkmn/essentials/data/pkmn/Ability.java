/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pkmn.essentials.data.AbstractPBSElement;

/**
 *
 * @author Mosblinker
 */
public class Ability extends AbstractPBSElement implements Comparable<Ability>{
    
    public static final String PBS_FILE_TEMPLATE = "abilities";
    
    public static final String[] DEFAULT_FLAGS = {
        "FasterEggHatching"
    };
    
    public static final String[] DEFAULT_FLAG_DESCRIPTIONS = {
        "Eggs hatch twice as fast when there are 1 or more Pokémon in the party whose species has this flag.",
    };
    
    public static final String FLAG_FILE = "ability_flags.txt";
    
    private static Map<String,String> createFlagMap(){
        Map<String, String> map = new LinkedHashMap<>();
        int i;
        for (i = 0; i < DEFAULT_FLAGS.length && i < DEFAULT_FLAG_DESCRIPTIONS.length; i++)
            map.put(DEFAULT_FLAGS[i], DEFAULT_FLAG_DESCRIPTIONS[i]);
        for (; i < DEFAULT_FLAGS.length; i++)
            map.put(DEFAULT_FLAGS[i], null);
        return map;
    }
    
    private static Map<String,String> flagMap = createFlagMap();
    
    public static Map<String, String> getFlagMap(){
        return flagMap;
    }
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "Flags",
        "Description"
    };
    
    private String desc;
    
    public Ability(){
        super();
    }
    
    public Ability(String id){
        super(id);
    }
    
    public Ability(Ability ability){
        super(ability);
        this.desc = ability.desc;
    }
    /**
     * The internal ID for this ability. This is how the scripts will refer to the 
     * ability. Each ability much have a different ID. This is surrounded by square 
     * brackets in the PBS file.
     * @return 
     */
    @Override
    public String getID(){
        return super.getID();
    }
    /**
     * The name of this ability, as seen by the player.
     * @return 
     */
    @Override
    public String getName(){
        return super.getName();
    }
    @Override
    public List<String> getFlags(){
        return super.getFlags();
    }
    
    public String getDescription(){
        return desc;
    }
    
    public void setDescription(String description){
        desc = description;
    }
    
    @Override
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getName();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (getDescription() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + getDescription();
        return str;
    }

    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(2):
                setDescription(value);
        }
        if (propertyIndex == getPropertyNames().length)
            setID(value);
    }

    @Override
    protected String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public int compareTo(Ability o) {
        return getID().compareTo(o.getID());
    }
    
    @Override
    protected void reset(){
        super.reset();
        desc = null;
    }
}
