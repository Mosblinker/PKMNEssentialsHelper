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
    
    PHYSICAL("Physical", 0),
    
    SPECIAL("Special", 1),
    
    STATUS("Status", 2);
    
    private String name;
    
    private int index;
    
    private MoveCategory(String name, int index){
        this.name = name;
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public static MoveCategory getValueOfName(String name){
        for (MoveCategory cat : values()){
            if (cat.toString().equals(name))
                return cat;
        }
        throw new IllegalArgumentException();
    }
}
