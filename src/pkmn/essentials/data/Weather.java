/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data;

/**
 *
 * @author Mosblinker
 */
public enum Weather {
    
    RAIN("Rain"),
    
    HEAVY_RAIN("HeavyRain", "Heavy Rain"),
    
    STORM("Storm"),
    
    SNOW("Snow"),
    
    BLIZZARD("Blizzard"),
    
    SANDSTORM("Sandstorm"),
    
    SUN("Sun"),
    
    FOG("Fog");
    
    
    private String name;
    
    private String value;
    
    private Weather(String value, String name){
        this.name = name;
        this.value = value;
    }
    
    private Weather(String value){
        this(value,value);
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public String getValue(){
        return value;
    }
    
    public static Weather parseWeather(String name){
        for (Weather rate : values()){
            if (rate.getValue().equals(name))
                return rate;
        }
        throw new IllegalArgumentException();
    }
}
