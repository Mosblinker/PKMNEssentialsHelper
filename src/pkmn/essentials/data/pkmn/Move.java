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
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import pkmn.essentials.data.AbstractPBSElement;
import static pkmn.essentials.data.pkmn.Type.PROPERTIES;

/**
 *
 * @author Mosblinker
 */
public class Move extends AbstractPBSElement implements Comparable<Move>{
    
    public static final String PBS_FILE_TEMPLATE = "moves";
    
    public static final String CATEGORY_ICONS = "Graphics"+File.separator+"UI"+File.separator+"category.png";
    
    public static final String[] DEFAULT_FLAGS = {
        "Contact",
        "CanProtect",
        "CanMirrorMove",
        "ThawsUser",
        "HighCriticalHitRate",
        "Biting",
        "Punching",
        "Sound",
        "Powder",
        "Pulse",
        "Bomb",
        "Dance",
        "CannotMetronome",
        "TramplesMinimize"
    };
    
    public static final String[] DEFAULT_FLAG_DESCRIPTIONS = {
        "The move makes physical contact with the target.",
        "The target can use Protect or Deflect to protect itself from the move.",
        "The move can be copied by Mirror Move.",
        "If the user is frozen, the move will thaw it out before it is used.",
        "The move has a high critical hit rate.",
        "The move is a biting move (powered up by the ability Strong Jaw).",
        "The move is a punching move (powered up by the ability Iron Fist).",
        "The move is a sound-based move.",
        "The move is a powder-based move (Grass-type Pokémon are immune to them).",
        "The move is a pulse-based move (powered up by the ability Mega Launcher).",
        "The move is a bomb-based move (resisted by the ability Bulletproof).",
        "The move is a dance move (repeated by the ability Dancer).",
        "This move cannot be used by the move Metronome. Used for signature moves that are intended to remain exclusive to their associated Pokémon.",
        "This move deals double damage to Pokémon that used Minimize at least once during their stay in the battle. Gen 6+: also skips all accuracy and evasion checks."
    };
    
    public static final String FLAG_FILE = "move_flags.txt";
    
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
        "Type",
        "Category",
        "Power",
        "Accuracy",
        "TotalPP",
        "Target",
        "Priority",
        "FunctionCode",
        "Flags",
        "EffectChance",
        "Description"
    };
    
    private Type type = null;
    
    private String typeStr = null;
    
    private MoveCategory category = null;
    
    private Integer power = null;
    
    private Integer accuracy = null;
    
    private Integer totalPP = null;
    
    private MoveTarget target = null;
    
    private Integer priority = null;
    
    private String functionCode = null;
    
    private List<String> flags = new ArrayList<>();
    
    private Integer effectChance = null;
    
    private String desc = null;
    
    public Move(){
        super();
    }
    
    public Move(String id){
        super(id);
    }
    
    public Move(Move move){
        super(move);
        this.type = move.type;
        this.typeStr = move.typeStr;
        this.category = move.category;
        this.power = move.power;
        this.accuracy = move.accuracy;
        this.totalPP = move.totalPP;
        this.target = move.target;
        this.priority = move.priority;
        this.functionCode = move.functionCode;
        this.flags.addAll(move.flags);
        this.effectChance = move.effectChance;
        this.desc = move.desc;
    }
    /**
     * The internal ID for this move. This is how the scripts will refer to the 
     * move. Each move much have a different ID. This is surrounded by square 
     * brackets in the PBS file.
     * @return 
     */
    @Override
    public String getID(){
        return super.getID();
    }
    /**
     * The name of this move, as seen by the player.
     * @return 
     */
    @Override
    public String getName(){
        return super.getName();
    }
    /**
     * The move's elemental type.
     * @return 
     */
    public Type getType(){
        return type;
    }
    
    public void setType(Type type){
        this.type = type;
    }
    
    public MoveCategory getCategory(){
        return (category != null) ? category : MoveCategory.STATUS;
    }
    
    public void setCategory(MoveCategory category){
        this.category = category;
    }
    
    public boolean isCategorySet(){
        return category != null;
    }
    /**
     * This returns the move's base power value. Moves with a variable base 
     * power are defined here with a base power of 1. For multi-hit moves, this 
     * is the base power of a single hit. Status moves do not need this line, as 
     * their base power is 0 which is the default value. 
     * @return 
     */
    public int getPower(){
        return (power != null) ? power : 0;
    }
    
    public void setPower(Integer power){
        if (power != null && power < 0)
            throw new IllegalArgumentException("Power " + power);
        this.power = power;
    }
    
    public boolean isPowerSet(){
        return power != null;
    }
    
    public int getAccuracy(){
        return (accuracy != null) ? accuracy : 100;
    }
    
    public void setAccuracy(Integer accuracy){
        if (accuracy != null && (accuracy < 0 || accuracy > 100))
            throw new IllegalArgumentException("Accuracy " + accuracy);
        this.accuracy = accuracy;
    }
    
    public boolean isAccuracySet(){
        return accuracy != null;
    }
    
    public int getTotalPP(){
        return (totalPP != null) ? totalPP : 5;
    }
    
    public void setTotalPP(Integer pp){
        if (pp != null && pp < 0)
            throw new IllegalArgumentException("TotalPP " + pp);
        this.totalPP = pp;
    }
    
    public boolean isTotalPPSet(){
        return totalPP != null;
    }
    
    public MoveTarget getTarget(){
        return (target == null) ? MoveTarget.NONE : target;
    }
    
    public void setTarget(MoveTarget target){
        this.target = target;
    }
    
    public boolean isTargetSet(){
        return target != null;
    }
    
    public Integer getPriority(){
        return (priority!=null)?priority:0;
    }
    
    public void setPriority(Integer priority){
        if (priority != null && (priority < -6 || priority > 6))
            throw new IllegalArgumentException("Priority "+ priority);
        this.priority = priority;
    }
    
    public boolean isPrioritySet(){
        return priority != null;
    }
    
    public String getFunctionCode(){
        return functionCode;
    }
    
    public void setFunctionCode(String code){
        this.functionCode = code;
    }
    
    public List<String> getFlags(){
        return flags;
    }
    
    public int getEffectChance(){
        return (effectChance != null) ? effectChance : 0;
    }
    
    public void setEffectChance(Integer chance){
        effectChance = chance;
    }
    
    public boolean isEffectChanceSet(){
        return effectChance != null;
    }
    
    public String getDescription(){
        return desc;
    }
    
    public void setDescription(String description){
        desc = description;
    }

    @Override
    public int compareTo(Move o) {
        return getID().compareTo(o.getID());
    }
    
    @Override
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTIES[0] + " = " + getName();
        if (getType() != null)
            str += System.lineSeparator()+PROPERTIES[1] + " = " + getType().getID();
        else if (typeStr != null)
            str += System.lineSeparator()+PROPERTIES[1] + " = " + typeStr;
        if (isCategorySet())
            str += System.lineSeparator()+PROPERTIES[2] + " = " + getCategory().toString();
        if (isPowerSet())
            str += System.lineSeparator()+PROPERTIES[3] + " = " + getPower();
        if (isAccuracySet())
            str += System.lineSeparator()+PROPERTIES[4] + " = " + getAccuracy();
        if (isTotalPPSet())
            str += System.lineSeparator()+PROPERTIES[5] + " = " + getTotalPP();
        if (isTargetSet())
            str += System.lineSeparator()+PROPERTIES[6] + " = " + getTarget().getValue();
        if (isPrioritySet())
            str += System.lineSeparator()+PROPERTIES[7] + " = " + getPriority();
        if (getFunctionCode() != null)
            str += System.lineSeparator()+PROPERTIES[8] + " = " + getFunctionCode();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTIES[9] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (isEffectChanceSet())
            str += System.lineSeparator()+PROPERTIES[10] + " = " + getEffectChance();
        if (getDescription() != null)
            str += System.lineSeparator()+PROPERTIES[11] + " = " + getDescription();
        return str;
    }

    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                typeStr = value;
                break;
            case(2):
                setCategory(MoveCategory.parseCategory(value));
                break;
            case(3):
                setPower(Integer.valueOf(value));
                break;
            case(4):
                setAccuracy(Integer.valueOf(value));
                break;
            case(5):
                setTotalPP(Integer.valueOf(value));
                break;
            case(6):
                setTarget(MoveTarget.parseTarget(value));
                break;
            case(7):
                setPriority(Integer.valueOf(value));
                break;
            case(8):
                setFunctionCode(value);
                break;
            case(9):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(10):
                setEffectChance(Integer.valueOf(value));
                break;
            case(11):
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
    protected void reset(){
        super.reset();
        type = null;
        typeStr = null;
        category = null;
        power = null;
        accuracy = null;
        totalPP = null;
        target = null;
        priority = null;
        functionCode = null;
        flags.clear();
        effectChance = null;
        desc = null;
    }
    
    public void finishLoad(Collection<Type> types){
        if (typeStr == null || typeStr.isBlank())
            return;
        type = (Type) AbstractPBSElement.getMatch(typeStr, types);
        typeStr = null;
    }
    
    public static Map<MoveCategory, ImageIcon> getCategoryIcons(BufferedImage icons){
        Map<MoveCategory, ImageIcon> iconMap = new LinkedHashMap<>();
        int height = Math.floorDiv(icons.getHeight(), MoveCategory.values().length);
        for (MoveCategory category : MoveCategory.values()){
            BufferedImage img = icons.getSubimage(0, height*category.getIndex(), icons.getWidth(), height);
            iconMap.put(category,new ImageIcon(img, category.toString()));
        }
        return iconMap;
    }
}
