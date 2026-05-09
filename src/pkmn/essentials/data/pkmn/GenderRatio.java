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
    
    ALWAYS_MALE("AlwaysMale",0.0),
    
    FEMALE_ONE_EIGHTH("FemaleOneEighth",1/8.0),
    
    FEMALE_25_PERCENT("Female25Percent",0.25),
    
    FEMALE_50_PERCENT("Female50Percent",0.5),
    
    FEMALE_75_PERCENT("Female75Percent",0.75),
    
    FEMALE_SEVEN_EIGHTHS("FemaleSevenEighths",7/8.0),
    
    ALWAYS_FEMALE("AlwaysFemale",1.0),
    
    GENDERLESS("Genderless",null);
    
    private String name;
    
    private Double ratio;
    
    private GenderRatio(String name, Double genderRatio){
        this.name = name;
        this.ratio = genderRatio;
    }
    
    public Double getGenderRatio(){
        return ratio;
    }
    
    public boolean isGenderless(){
        return ratio == null;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static GenderRatio getValueOfName(String name){
        for (GenderRatio ratio : values()){
            if (ratio.toString().equals(name))
                return ratio;
        }
        throw new IllegalArgumentException();
    }
}
