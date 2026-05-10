/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data.pkmn;

/**
 *
 * @author Mosblinker
 */
public enum GenderRatio {
    
    ALWAYS_MALE("AlwaysMale","Always Male",0.0),
    
    FEMALE_ONE_EIGHTH("FemaleOneEighth","87.5%/12.5%",1/8.0),
    
    FEMALE_25_PERCENT("Female25Percent","75%/25%",0.25),
    
    FEMALE_50_PERCENT("Female50Percent","50%/50%",0.5),
    
    FEMALE_75_PERCENT("Female75Percent","75%/25%",0.75),
    
    FEMALE_SEVEN_EIGHTHS("FemaleSevenEighths","12.5%/87.5%",7/8.0),
    
    ALWAYS_FEMALE("AlwaysFemale","Always Female",1.0),
    
    GENDERLESS("Genderless",null);
    
    private String name;
    
    private String value;
    
    private Double ratio;
    
    private GenderRatio(String value, String name, Double genderRatio){
        this.value = value;
        this.name = name;
        this.ratio = genderRatio;
    }
    
    private GenderRatio(String value, Double genderRatio){
        this(value,value,genderRatio);
    }
    
    public Double getGenderRatio(){
        return ratio;
    }
    
    public boolean isGenderless(){
        return ratio == null;
    }
    
    public String getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static GenderRatio parseGenderRatio(String name){
        for (GenderRatio ratio : values()){
            if (ratio.getValue().equals(name))
                return ratio;
        }
        throw new IllegalArgumentException();
    }
}
