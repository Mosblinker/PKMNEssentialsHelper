/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import pkmn.essentials.data.Item;

/**
 *
 * @author Mosblinker
 */
public abstract class EvolutionMethod {
    
    public static final EvolutionMethod[] DEFAULT_EVOLUTION_METHODS = {
            // Levelling up evolution methods
        new LevelEvolutionMethod("Level","Level","The Pokémon will evolve if its level is greater than or equal to the parameter."),
        new LevelEvolutionMethod("LevelMale","Level Male","The Pokémon will evolve if it is male and its level is greater than or equal to the parameter."),
        new LevelEvolutionMethod("LevelFemale","Level Female","The Pokémon will evolve if it is female and its level is greater than or equal to the parameter."),
        new LevelEvolutionMethod("LevelDay","Level Day","The Pokémon will evolve if its level is greater than or equal to the parameter and it is currently daytime."),
        new LevelEvolutionMethod("LevelNight","Level Night","The Pokémon will evolve if its level is greater than or equal to the parameter and it is currently night-time."),
        new LevelEvolutionMethod("LevelMorning","Level Morning","The Pokémon will evolve if its level is greater than or equal to the parameter and it is currently the morning."),
        new LevelEvolutionMethod("LevelAfternoon","Level Afternoon","The Pokémon will evolve if its level is greater than or equal to the parameter and it is currently the afternoon."),
        new LevelEvolutionMethod("LevelEvening","Level Evening","The Pokémon will evolve if its level is greater than or equal to the parameter and it is currently the evening."),
        new LevelEvolutionMethod("LevelNoWeather","Level No Weather","The Pokémon will evolve if its level is greater than or equal to the parameter and the overworld weather is clear (i.e. there is no weather)."),
        new LevelEvolutionMethod("LevelSun","Level Sun","The Pokémon will evolve if its level is greater than or equal to the parameter and the overworld weather is strong sunlight."),
        new LevelEvolutionMethod("LevelRain","Level Rain","The Pokémon will evolve if its level is greater than or equal to the parameter and the overworld weather is rain, heavy rain or stormy."),
        new LevelEvolutionMethod("LevelSnow","Level Snow","The Pokémon will evolve if its level is greater than or equal to the parameter and the overworld weather is snow or a blizzard."),
        new LevelEvolutionMethod("LevelSandstorm","Level Sandstorm","The Pokémon will evolve if its level is greater than or equal to the parameter and the overworld weather is a sandstorm."),
        new LevelEvolutionMethod("LevelCycling","Level Cycling","The Pokémon will evolve if its level is greater than or equal to the parameter and the player is currently cycling."),
        new LevelEvolutionMethod("LevelSurfing","Level Surfing","The Pokémon will evolve if its level is greater than or equal to the parameter and the player is currently surfing."),
        new LevelEvolutionMethod("LevelDiving","Level Diving","The Pokémon will evolve if its level is greater than or equal to the parameter and the player is currently diving."),
        new LevelEvolutionMethod("LevelDarkness","Level Darkness","The Pokémon will evolve if its level is greater than or equal to the parameter and the player is currently in a map that has a darkness circle (regardless of whether Flash has expanded it)."),
        new LevelEvolutionMethod("LevelDarkInParty","Level Dark-Type In Party","The Pokémon will evolve if its level is greater than or equal to the parameter and the player has a Dark-type Pokémon in their party."),
        new LevelEvolutionMethod("AttackGreater","Attack > Defense","The Pokémon will evolve if its level is greater than or equal to the parameter and its Attack stat is greater than its Defense stat."),
        new LevelEvolutionMethod("AtkDefEqual","Attack = Defense","The Pokémon will evolve if its level is greater than or equal to the parameter and its Attack stat is equal to its Defense stat."),
        new LevelEvolutionMethod("DefenseGreater", "Attack < Defense", "The Pokémon will evolve if its level is greater than or equal to the parameter and its Attack stat is lower than its Defense stat."),
        new LevelEvolutionMethod("Silcoon","Silcoon","The Pokémon will evolve if its level is greater than or equal to the parameter and the last digit of the upper half of its decimalised personal ID number is between 0 and 4 inclusive."),
        new LevelEvolutionMethod("Cascoon","Cascoon","The Pokémon will evolve if its level is greater than or equal to the parameter and the last digit of the upper half of its decimalised personal ID number is between 5 and 9 inclusive."),
        new LevelEvolutionMethod("Ninjask","Ninjask","Identical to \"Level\"."),
        new NoParamEvolutionMethod("Happiness","Happiness","The Pokémon will evolve if its happiness is 220 or higher. This number is hardcoded."),
        new NoParamEvolutionMethod("HappinessMale","Happiness Male","The Pokémon will evolve if it is male and its happiness is 220 or higher. This number is hardcoded."),
        new NoParamEvolutionMethod("HappinessFemale","Happiness Female","The Pokémon will evolve if it is female and its happiness is 220 or higher. This number is hardcoded."),
        new NoParamEvolutionMethod("HappinessDay","Happiness Day", "The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it is currently daytime."),
        new NoParamEvolutionMethod("HappinessNight","Happiness Night","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it is currently night-time."),
        new DefinableEvolutionMethod("HappinessMove","Happiness Move","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it knows the move given in the parameter.","Move",Move.class),
        new DefinableEvolutionMethod("HappinessMoveType","Happiness Move Type","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it knows any move of the type given in the parameter.","Type",Type.class),
        new ItemEvolutionMethod("HappinessHoldItem","Happiness Hold Item","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it is holding the item given in the parameter. The item is consumed."),
        new NoParamEvolutionMethod("MaxHappiness","Max Happiness","The Pokémon will evolve if its happiness is 255. This number is hardcoded."),
        new DefinableEvolutionMethod("Beauty","Beauty","The Pokémon will evolve if its beauty is equal to or higher than the parameter.","Beauty",Integer.class),
        new ItemEvolutionMethod("HoldItem","Hold Item","The Pokémon will evolve if it is holding the item given in the parameter. The item is consumed."),
        new ItemEvolutionMethod("HoldItemMale","Hold Item Male","The Pokémon will evolve if it is male and is holding the item given in the parameter. The item is consumed."),
        new ItemEvolutionMethod("HoldItemFemale","Hold Item Female","The Pokémon will evolve if it is female and is holding the item given in the parameter. The item is consumed."),
        new ItemEvolutionMethod("DayHoldItem","Day Hold Item","The Pokémon will evolve if it is holding the item given in the parameter and it is currently daytime. The item is consumed."),
        new ItemEvolutionMethod("NightHoldItem","Night Hold Item", "The Pokémon will evolve if it is holding the item given in the parameter and it is currently night-time. The item is consumed."),
        new ItemEvolutionMethod("HoldItemHappiness","Hold Item Happiness","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and it is holding the item given in the parameter. The item is consumed."),
        new DefinableEvolutionMethod("HasMove","Has Move","The Pokémon will evolve if it knows the move given in the parameter.","Move",Move.class),
        new DefinableEvolutionMethod("HasMoveType","Has Move Type","The Pokémon will evolve if it knows any move of the type given in the parameter.","Type",Type.class),
        new DefinableEvolutionMethod("HasInParty","Has In Party","The Pokémon will evolve if the player's party contains a Pokémon whose species is given in the parameter.","Species",AbstractSpecies.class),
        new DefinableEvolutionMethod("Location","Location","The Pokémon will evolve if the player is currently in a map whose ID number is given in the parameter.","ID",Integer.class),
        new DefinableEvolutionMethod("LocationFlag","Location Flag","The Pokémon will evolve if the player is currently in a map whose map metadata contains a flag that matches the parameter.","Flag",String.class),
        new DefinableEvolutionMethod("Region","Region","The Pokémon will evolve if the player is currently in a map that is part of a region whose ID number is given in the parameter.","ID",Integer.class),
        
        // Item Evolution Methods
        new ItemEvolutionMethod("Item","Item","The Pokémon will evolve if the item used is given in the parameter."),
        new ItemEvolutionMethod("ItemMale","Item Male","The Pokémon will evolve if it is male and the item used is given in the parameter."),
        new ItemEvolutionMethod("ItemFemale","Item Female","The Pokémon will evolve if it is female and the item used is given in the parameter."),
        new ItemEvolutionMethod("ItemDay","Item Day","The Pokémon will evolve if it is currently daytime and the item used is given in the parameter."),
        new ItemEvolutionMethod("ItemNight","Item Night","The Pokémon will evolve if it is currently night-time and the item used is given in the parameter."),
        new ItemEvolutionMethod("ItemHappiness","Item Happiness","The Pokémon will evolve if its happiness is 220 or higher (this number is hardcoded) and the item used is given in the parameter."),
        
        // Trade Evolution Methods
        new NoParamEvolutionMethod("Trade","Trade","The Pokémon will evolve."),
        new NoParamEvolutionMethod("TradeMale","Trade Male","The Pokémon will evolve if it is male."),
        new NoParamEvolutionMethod("TradeFemale","Trade Female","The Pokémon will evolve if it is female."),
        new NoParamEvolutionMethod("TradeDay","Trade Day","The Pokémon will evolve if it is currently daytime."),
        new NoParamEvolutionMethod("TradeNight","Trade Night","The Pokémon will evolve if it is currently night-time."),
        new ItemEvolutionMethod("TradeItem","Trade Item","The Pokémon will evolve if it is holding the item given in the parameter. The item is consumed."),
        new DefinableEvolutionMethod("TradeSpecies","Trade Species","The Pokémon will evolve if the other Pokémon that was traded away was the species given in the parameter and wasn't holding an Everstone.","Species",AbstractSpecies.class),
        
        // After Every Battle Evolution Methods
        new DefinableEvolutionMethod("BattleDealCriticalHit","Battle Deal Critical Hits", "The Pokémon will evolve if the number of critical hits dealt by the Pokémon during the battle is greater than or equal to the number given in the parameter.","Number",Integer.class),
        
        // Manually Triggered Evolution Methods
        new DefinableEvolutionMethod("Event","Event","The Pokémon will evolve when pbEvolutionEvent(number) is called, and number is equal to the number given in the parameter.", "Number", Integer.class),
        new DefinableEvolutionMethod("EventAfterDamageTaken","Event After Damage Taken","The Pokémon will evolve when pbEvolutionEvent(number) is called, and number is equal to the number given in the parameter, but only if pkmn.ready_to_evolve is true. pkmn.ready_to_evolve is set to true after a battle in which the Pokémon took 49 or more total damage (this number is hardcoded) from direct attacks, and is reset to false if the Pokémon is fully healed at a Poké Center or if it faints.", "Number", Integer.class),
        
        // Code run after evolution
        new NoParamEvolutionMethod("Shedinja","Shedinja","If there is an empty space in the player's party and there is a regular Poké Ball in the Bag, the Pokémon that just evolved is duplicated and the copy is turned into the species given for this method (e.g. Shedinja). The Poké Ball is consumed.")
    };
    
    private static List<EvolutionMethod> methods = new ArrayList<>(Arrays.asList(DEFAULT_EVOLUTION_METHODS));
    
    public static List<EvolutionMethod> getMethods(){
        return methods;
    }
    
    public static final String METHODS_DATA_FILE = "evolution_methods.txt";
    
    protected String value;
    
    protected String text;
    
    protected String desc;
    
    protected EvolutionMethod(String value, String text, String desc){
        this.value = Objects.requireNonNull(value);
        this.text = Objects.requireNonNull(text);
        this.desc = desc;
    }
    
    protected EvolutionMethod(String value, String desc){
        this(value,value,desc);
    }
    
    protected EvolutionMethod(String value){
        this(value,null);
    }
    
    protected EvolutionMethod(EvolutionMethod method){
        this.value = method.value;
        this.text = method.text;
        this.desc = method.desc;
    }
    
    public String getValue(){
        return value;
    }
    
    @Override
    public String toString(){
        return text;
    }
    
    public String getDescription(){
        return desc;
    }
    
    public boolean match(String str){
        return getValue().equals(str);
    }
    
    public abstract String getParameterName();
    
    public Class<?> getParameterClass(){
        return String.class;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        else if (obj instanceof EvolutionMethod)
            return getValue().equals(((EvolutionMethod)obj).getValue()) && 
                    Objects.equals(getParameterClass(), ((EvolutionMethod)obj).getParameterClass());
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.getValue());
        hash = 67 * hash + Objects.hashCode(this.getParameterClass());
        return hash;
    }
    
    public static class DefinableEvolutionMethod extends EvolutionMethod{
        
        protected String paramName;
    
        protected Class<?> paramClass;
        
        public DefinableEvolutionMethod(String value, String text, String desc, String paramName, Class<?> paramClass){
            super(value,text,desc);
            this.paramName = paramName;
            this.paramClass = paramClass;
        }
        
        protected DefinableEvolutionMethod(String value, String text, String desc){
            this(value,text,desc,"String",String.class);
        }

        protected DefinableEvolutionMethod(String value, String desc, String paramName, Class<?> paramClass){
            this(value,value,desc,paramName,paramClass);
        }

        protected DefinableEvolutionMethod(String value, String desc){
            this(value,desc,"String",String.class);
        }

        protected DefinableEvolutionMethod(String value, String paramName, Class<?> paramClass){
            this(value,null,paramName,paramClass);
        }

        protected DefinableEvolutionMethod(String value){
            this(value,null,"String",String.class);
        }

        protected DefinableEvolutionMethod(EvolutionMethod method){
            super(method);
            this.paramName = method.getParameterName();
            this.paramClass = method.getParameterClass();
        }

        @Override
        public String getParameterName() {
            return paramName;
        }
        
        @Override
        public Class<?> getParameterClass(){
            return paramClass;
        }
    }
    
    public static class LevelEvolutionMethod extends EvolutionMethod{

        public LevelEvolutionMethod(String value, String text, String desc) {
            super(value, text, desc);
        }
        
        public LevelEvolutionMethod(String value, String desc) {
            super(value,desc);
        }
        
        public LevelEvolutionMethod(String value){
            super(value);
        }
        
        public LevelEvolutionMethod(LevelEvolutionMethod method){
            super(method);
        }

        @Override
        public String getParameterName() {
            return "Level";
        }
        
        @Override
        public Class<?> getParameterClass(){
            return Integer.class;
        }
    }
    
    public static class NoParamEvolutionMethod extends EvolutionMethod{
        
        public NoParamEvolutionMethod(String value, String text, String desc) {
            super(value, text, desc);
        }
        
        public NoParamEvolutionMethod(String value, String desc) {
            super(value,desc);
        }
        
        public NoParamEvolutionMethod(String value){
            super(value);
        }
        
        public NoParamEvolutionMethod(NoParamEvolutionMethod method){
            super(method);
        }

        @Override
        public String getParameterName() {
            return null;
        }
        @Override
        public Class<?> getParameterClass(){
            return null;
        }
    }
    
    public static class ItemEvolutionMethod extends EvolutionMethod{
        
        public ItemEvolutionMethod(String value, String text, String desc) {
            super(value, text, desc);
        }
        
        public ItemEvolutionMethod(String value, String desc) {
            super(value,desc);
        }
        
        public ItemEvolutionMethod(String value){
            super(value);
        }
        
        public ItemEvolutionMethod(ItemEvolutionMethod method){
            super(method);
        }

        @Override
        public String getParameterName() {
            return "Item";
        }
        @Override
        public Class<?> getParameterClass(){
            return Item.class;
        }
    }
}
