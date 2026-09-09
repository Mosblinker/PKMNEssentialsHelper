/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import pkmn.essentials.data.AbstractPBSElement;
import pkmn.essentials.data.Item;
import pkmn.essentials.data.PBSData;

/**
 *
 * @author Mosblinker
 */
public class Species extends AbstractSpecies{
    
    public static final String PBS_FILE_TEMPLATE = "pokemon";
    
    public static final String PBS_FILES_EXCLUDE_LIST_FILE = "pkmn_exclude_files.txt";
    
    private static List<String> pbsFilesExclude = new ArrayList<>();
    
    public static List<String> getExcludedPBSFiles(){
        return pbsFilesExclude;
    }
    
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
        if (genderRatio == null)
            return GenderRatio.FEMALE_50_PERCENT;
        return genderRatio;
    }
    
    public void setGenderRatio(GenderRatio ratio){
        this.genderRatio = ratio;
    }
    
    public boolean isGenderRatioSet(){
        return genderRatio != null;
    }
    
    public GrowthRate getGrowthRate(){
        if (growthRate == null)
            return GrowthRate.MEDIUM;
        return growthRate;
    }
    
    public void setGrowthRate(GrowthRate rate){
        growthRate = rate;
    }
    
    public boolean isGrowthRateSet(){
        return growthRate != null;
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
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getName();
        if (getFormName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getFormName();
        if (!getTypes().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + toIDString(getTypes());
        else if (getTypesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + getTypesStr();
        if (!getBaseStats().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + getBaseStatsStr();
        if (isGenderRatioSet())
            str += System.lineSeparator()+PROPERTY_NAMES[4] + " = " + getGenderRatio().getValue();
        if (isGrowthRateSet())
            str += System.lineSeparator()+PROPERTY_NAMES[5] + " = " + getGrowthRate().toString();
        if (isBaseXPSet())
            str += System.lineSeparator()+PROPERTY_NAMES[6] + " = " + getBaseXP();
        if (!getEVs().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[7] + " = " + getEVsStr();
        if (isCatchRateSet())
            str += System.lineSeparator()+PROPERTY_NAMES[8] + " = " + getCatchRate();
        if (isHappinessSet())
            str += System.lineSeparator()+PROPERTY_NAMES[9] + " = " + getHappiness();
        String abilities = getAbilitiesStr();
        if (abilities != null)
            str += System.lineSeparator()+PROPERTY_NAMES[10] + " = " + abilities;
        if (!getHiddenAbilities().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[11] + " = " + toIDString(getHiddenAbilities());
        else if (getHiddenAbilitiesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[11] + " = " + getHiddenAbilitiesStr();
        String moves = getMovesToString();
        if (moves != null)
            str += System.lineSeparator()+PROPERTY_NAMES[12] + " = " + moves;
        if (!getTutorMoves().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[13] + " = " + toIDString(getTutorMoves());
        else if (getTutorMovesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[13] + " = " + getTutorMovesStr();
        if (!getEggMoves().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[14] + " = " + toIDString(getEggMoves());
        else if (getEggMovesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[14] + " = " + getEggMovesStr();
        if (!getEggGroups().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[15] + " = " + getEggGroupsStr();
        if (isHatchStepsSet())
            str += System.lineSeparator()+PROPERTY_NAMES[16] + " = " + getHatchSteps();
        if (getIncense() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[17] + " = " + getIncense().getID();
        else if (incenseStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[17] + " = " + incenseStr;
        if (!getOffspring().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[18] + " = " + toIDString(getOffspring());
        else if (getOffspringStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[18] + " = " + getOffspringStr();
        if (isHeightSet())
            str += System.lineSeparator()+PROPERTY_NAMES[19] + " = " + getHeight();
        if (isWeightSet())
            str += System.lineSeparator()+PROPERTY_NAMES[20] + " = " + getWeight();
        if (isColorSet())
            str += System.lineSeparator()+PROPERTY_NAMES[21] + " = " + getColor();
        if (isShapeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[22] + " = " + getShape();
        if (isHabitatSet())
            str += System.lineSeparator()+PROPERTY_NAMES[23] + " = " + getHabitat();
        if (getCategory() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[24] + " = " + getCategory();
        if (getPokedexEntry() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[25] + " = " + getPokedexEntry();
        if (isGenerationSet())
            str += System.lineSeparator()+PROPERTY_NAMES[26] + " = " + getGeneration();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[27] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (!getCommonWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[28] + " = " + toIDString(getCommonWildItems());
        else if (getCommonWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[28] + " = " + getCommonWildItemsStr();
        if (!getUncommonWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[29] + " = " + toIDString(getUncommonWildItems());
        else if (getUncommonWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[29] + " = " + getUncommonWildItemsStr();
        if (!getRareWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[30] + " = " + toIDString(getRareWildItems());
        else if (getRareWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[30] + " = " + getRareWildItemsStr();
        String evolutions = evolutionsToString();
        if (evolutions != null)
            str += System.lineSeparator()+PROPERTY_NAMES[31] + " = " + evolutions;
        return str;
    }
    
    @Override
    public void finishLoading(PBSData data){
        super.finishLoading(data);
        if (incenseStr != null && !incenseStr.isBlank())
            setIncense((Item) AbstractPBSElement.getMatch(incenseStr, data.getItems()));
        incenseStr = null;
    }
}
