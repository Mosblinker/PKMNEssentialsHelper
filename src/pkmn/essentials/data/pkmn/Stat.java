/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data.pkmn;

/**
 *
 * @author Mosblinker
 */
public enum Stat {
    
    HP("HP"),
    
    ATTACK("Attack"),
    
    DEFENSE("Defense"),
    
    SPEED("Speed"),
    
    SPECIAL_ATTACK("Special Attack"),
    
    SPECIAL_DEFENSE("Special Defense");
    
    private String name;
    
    private Stat(String name){
        this.name = name;
    }
    
    public String toString(){
        return name;
    }
}
