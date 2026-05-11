/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data;

/**
 *
 * @author Mosblinker
 */
public enum ItemFieldUse {
    
    ON_POKEMON("OnPokemon","On Pokemon", "The item can be used on a Pokémon (e.g. Potions, Elixirs). The party screen will appear when using this item, allowing you to choose the Pokémon to use it on. Not for TMs, TRs and HMs, though."),
    
    DIRECT("Direct", "The item can be used out of battle, but it isn't used on a Pokémon (e.g. Repel, Escape Rope, usable Key Items)."),
    
    TR("TR","The item is a TR. It teaches a move to a Pokémon, and disappears after use."),
    
    TM("TM","The item is a TM. It teaches a move to a Pokémon, but does not disappear after use."),
    
    HM("HM","The item is a HM. It teaches a move to a Pokémon, but does not disappear after use. Moves taught by a HM cannot be forgotten.");
    
    private String name;
    
    private String value;
    
    private String desc;
    
    private ItemFieldUse(String value, String name, String desc){
        this.name = name;
        this.value = value;
        this.desc = desc;
    }
    
    private ItemFieldUse(String value, String desc){
        this(value,value,desc);
    }
    
    public String getValue(){
        return value;
    }
    
    public String getDescription(){
        return desc;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public ItemFieldUse parseFieldUse(String name){
        for (ItemFieldUse use : values()){
            if (use.getValue().equals(name))
                return use;
        }
        throw new IllegalArgumentException();
    }
}
