/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data.pkmn;

/**
 *
 * @author Mosblinker
 */
public enum GrowthRate {
    
    FAST("Fast"),
    
    MEDIUM("Medium"),
    
    SLOW("Slow"),
    
    PARABOLIC("Parabolic"),
    
    ERRATIC("Erratic"),
    
    FLUCTUATING("Fluctuating");
    
    private String name;
    
    private GrowthRate(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static GrowthRate getValueOfName(String name){
        for (GrowthRate rate : values()){
            if (rate.toString().equals(name))
                return rate;
        }
        throw new IllegalArgumentException();
    }
}
