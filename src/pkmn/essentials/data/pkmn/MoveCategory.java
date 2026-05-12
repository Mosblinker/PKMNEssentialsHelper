/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data.pkmn;

/**
 *
 * @author Mosblinker
 */
public enum MoveCategory {
    
    PHYSICAL("Physical"),
    
    SPECIAL("Special"),
    
    STATUS("Status");
    
    private String name;
    
    private MoveCategory(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static MoveCategory parseCategory(String name){
        for (MoveCategory cat : values()){
            if (cat.toString().equals(name))
                return cat;
        }
        throw new IllegalArgumentException();
    }
}
