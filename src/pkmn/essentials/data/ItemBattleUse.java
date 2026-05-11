/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pkmn.essentials.data;

/**
 *
 * @author Mosblinker
 */
public enum ItemBattleUse {
    
    ON_POKEMON("OnPokemon","On Pokemon","The item can be used on one of your party Pokémon (e.g. Potions, Elixirs, Blue Flute). The party screen will appear when using this item, allowing you to choose the Pokémon to use it on."),
    
    ON_MOVE("OnMove","On Move", "The item can be used on one of the moves known by one of your party Pokémon (e.g. Ether). The party screen will appear when using this item, followed by a list of moves to choose from."),
    
    ON_BATTLER("OnBattler","On Battler", "The item is used on the Pokémon in battle that you are choosing a command for (e.g. X Accuracy, Red/Yellow Flutes)."),
    
    ON_FOE("OnFoe","On Foe","The item is used on an opposing Pokémon in battle (Poké Balls). If there is more than one opposing Pokémon, you will be able to choose which of them to use it on."),
    
    DIRECT("Direct","The item is used with no target (e.g. Poké Doll, Guard Spec., Poké Flute).");
    
    private String name;
    
    private String value;
    
    private String desc;
    
    private ItemBattleUse(String value, String name, String desc){
        this.name = name;
        this.value = value;
        this.desc = desc;
    }
    
    private ItemBattleUse(String value, String desc){
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
    
    public ItemBattleUse parseBattleUse(String name){
        for (ItemBattleUse use : values()){
            if (use.getValue().equals(name))
                return use;
        }
        throw new IllegalArgumentException();
    }
}
