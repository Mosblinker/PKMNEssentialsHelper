/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.io.File;
import pkmn.essentials.data.AbstractPBSElement;
import pkmn.essentials.data.Item;
import pkmn.essentials.data.PBSData;

/**
 *
 * @author Mosblinker
 */
public class Species extends AbstractSpecies{
    
    public static final String PBS_FILE_TEMPLATE = "pokemon";
    
    public static final String FORM_PBS_FILE_TEMPLATE = "pokemon_forms";
    
    public static final String METRICS_PBS_FILE_TEMPLATE = "pokemon_metrics";
    
    public static final String PBS_FILES_EXCLUDE_LIST_FILE = "pkmn_files.txt";
    
    public static final String SPRITE_FOLDER = "Graphics"+File.separator+"Pokemon"+File.separator+"Front";
    
    public static final String SPRITE_FILE_EXTENSION = "png";
    
    public static final String DEFAULT_SPRITE_NAME = "000";
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "FormName",
        "Types",
        "BaseStats",
        "GenderRatio",
        "GrowthRate",
        "BaseExp",
        "EVs",
        "CatchRate",
        "Happiness",
        "Abilities",
        "HiddenAbilities",
        "Moves",
        "TutorMoves",
        "EggMoves",
        "EggGroups",
        "HatchSteps",
        "Incense",
        "Offspring",
        "Height",
        "Weight",
        "Color",
        "Shape",
        "Habitat",
        "Category",
        "Pokedex",
        "Generation",
        "Flags",
        "WildItemCommon",
        "WildItemUncommon",
        "WildItemRare",
        "Evolutions"
    };
    
    private GenderRatio genderRatio = null;
    
    private GrowthRate growthRate = null;
    
    private Item incense = null;
    
    private String incenseStr = null;
    
    public Species(){
        super();
    }
    
    public Species(String id){
        super(id);
    }
    
    public Species(Species species){
        super(species);
        this.genderRatio = species.genderRatio;
        this.growthRate = species.growthRate;
        this.incense = species.incense;
        this.incenseStr = species.incenseStr;
    }
    
    public GenderRatio getGenderRatio(){
        return genderRatio;
    }
    
    public void setGenderRatio(GenderRatio ratio){
        this.genderRatio = ratio;
    }
    
    public GrowthRate getGrowthRate(){
        return growthRate;
    }
    
    public void setGrowthRate(GrowthRate rate){
        growthRate = rate;
    }
    
    public Item getIncense(){
        return incense;
    }
    
    public void setIncense(Item item){
        incense = item;
        incenseStr = null;
    }

    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                setFormName(value);
                break;
            case(2):
                setTypesStr(value);
                break;
            case(3):
                readBaseStats(value);
                break;
            case(4):
                setGenderRatio(GenderRatio.parseGenderRatio(value));
                break;
            case(5):
                setGrowthRate(GrowthRate.parseGrowthRate(value));
                break;
            case(6):
                setBaseXP(Integer.parseInt(value));
                break;
            case(7):
                readEVs(value);
                break;
            case(8):
                setCatchRate(Integer.parseInt(value));
                break;
            case(9):
                setHappiness(Integer.parseInt(value));
                break;
            case(10):
                setAbilityStr(value);
                break;
            case(11):
                setHiddenAbilitiesStr(value);
                break;
            case(12):
                setMovesStr(value);
                break;
            case(13):
                setTutorMovesStr(value);
                break;
            case(14):
                setEggMovesStr(value);
                break;
            case(15):
                setEggGroups(value);
                break;
            case(16):
                setHatchSteps(Integer.parseInt(value));
                break;
            case(17):
                incenseStr = value;
                break;
            case(18):
                setOffspringStr(value);
                break;
            case(19):
                setHeight(Double.parseDouble(value));
                break;
            case(20):
                setWeight(Double.parseDouble(value));
                break;
            case(21):
                setColor(value);
                break;
            case(22):
                setShape(value);
                break;
            case(23):
                setHabitat(value);
                break;
            case(24):
                setCategory(value);
                break;
            case(25):
                setPokedexEntry(value);
                break;
            case(26):
                setGeneration(Integer.parseInt(value));
                break;
            case(27):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(28):
                setCommonWildItemsStr(value);
                break;
            case(29):
                setUncommonWildItemsStr(value);
                break;
            case(30):
                setRareWildItemsStr(value);
                break;
            case(31):
                setEvolutionsStr(value);
        }
        if (propertyIndex == getPropertyNames().length)
            setID(value);
    }

    @Override
    protected String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }
    
    @Override
    protected void reset(){
        super.reset();
        setGenderRatio(null);
        setGrowthRate(null);
        setIncense(null);
    }
    
    @Override
    public void finishLoading(PBSData data){
        super.finishLoading(data);
        setIncense((Item) AbstractPBSElement.getMatch(incenseStr, data.getItems()));
    }
    
    // Form and metrics are subclasses of Species. Form implements AbstractSpecies
}
