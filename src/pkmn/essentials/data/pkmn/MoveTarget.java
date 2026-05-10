/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data.pkmn;

/**
 *
 * @author Mosblinker
 */
public enum MoveTarget {
    
    NONE("None", "None"),
    
    USER("User"),
    
    NEAR_ALLY("NearAlly", "Near Ally"),
    
    USER_OR_NEAR_ALLY("UserOrNearAlly", "User Or Near Ally"),
    
    ALL_ALLIES("AllAllies", "All Allies"),
    
    USER_AND_ALLIES("UserAndAllies", "User And Allies"),
    
    NEAR_FOE("NearFoe", "Near Foe"),
    
    RANDOM_NEAR_FOE("RandomNearFoe", "Random Near Foe"),
    
    ALL_NEAR_FOES("AllNearFoes", "All Near Foes"),
    
    FOE("Foe"),
    
    ALL_FOES("AllFoes", "All Foes"),
    
    NEAR_OTHER("NearOther", "Near Other"),
    
    ALL_NEAR_OTHERS("AllNearOthers", "All Near Others"),
    
    OTHER("Other"),
    
    ALL_BATTLERS("AllBattlers", "All Battlers"),
    
    USER_SIDE("UserSide", "User Side"),
    
    FOE_SIDE("FoeSide", "Foe Side"),
    
    BOTH_SIDES("BothSides","Both Sides");
    
    private String value;
    
    private String text;
    
    private String desc;
    
    private MoveTarget(String value, String text, String desc){
        this.value = value;
        this.text = text;
        this.desc = desc;
    }
    
    private MoveTarget(String value, String text){
        this(value,text,null);
    }
    
    private MoveTarget(String value){
        this(value,value);
    }
    
    @Override
    public String toString(){
        return text;
    }
    
    public String getValue(){
        return value;
    }
    
    public String getDescription(){
        return desc;
    }
    
    public static MoveTarget getValueFromText(String name){
        for (MoveTarget target : values()){
            if (target.toString().equals(name))
                return target;
        }
        throw new IllegalArgumentException();
    }
}
