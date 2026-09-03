/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data;

/**
 *
 * @author Mosblinker
 */
public enum Environment {
    
    NONE("None"),
    
    GRASS("Grass"),
    
    TALL_GRASS("TallGrass", "Tall Grass"),
    
    MOVING_WATER("MovingWater", "Moving Water"),
    
    STILL_WATER("StillWater", "Still Water"),
    
    PUDDLE("Puddle"),
    
    UNDERWATER("Underwater"),
    
    CAVE("Cave"),
    
    ROCK("Rock"),
    
    SAND("Sand"),
    
    FOREST("Forest"),
    
    FOREST_GRASS("ForestGrass", "Forest Grass"),
    
    SNOW("Snow"),
    
    ICE("Ice"),
    
    VOLCANO("Volcano"),
    
    GRAVEYARD("Graveyard"),
    
    SKY("Sky"),
    
    SPACE("Space"),
    
    ULTRA_SPACE("UltraSpace", "Ultra Space");
    
    
    private String name;
    
    private String value;
    
    private Environment(String value, String name){
        this.name = name;
        this.value = value;
    }
    
    private Environment(String value){
        this(value,value);
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public String getValue(){
        return value;
    }
    
    public static Environment parseEnvironment(String name){
        for (Environment rate : values()){
            if (rate.getValue().equals(name))
                return rate;
        }
        throw new IllegalArgumentException();
    }
}
