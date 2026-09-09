/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pkmn.essentials.data.AbstractPBSElement;
import pkmn.essentials.data.Item;

/**
 *
 * @author Mosblinker
 */
public class Form extends AbstractSpecies{
    
    public static final String PBS_FILE_TEMPLATE = "pokemon_forms";
    
    public static final String[] PROPERTY_NAMES = {
        "FormName",
        "PokedexForm",
        "Types",
        "BaseStats",
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
        "Evolutions",
        "MegaStone",
        "MegaMove",
        "MegaMessage",
        "UnmegaForm"
    };
    
    public static final String[] DEFAULT_MEGA_MESSAGES = {
        "{Pokemon}'s {Mega Stone} is reacting to {Player}'s {Mega Ring}!",
        "{Player}'s fervent wish has reached {Pokemon}!"
    };
    
    private static List<String> megaMessages = new ArrayList<>(Arrays.asList(DEFAULT_MEGA_MESSAGES));
    
    public static List<String> getMegaMessages(){
        return megaMessages;
    }
    
    public static final String MEGA_MESSAGES_NAME_FILE = "mega_messages.txt";
    
    private Species species = null;
    
    private Integer pokedexForm = null;
    
    private Item megaStone = null;
    
    private String megaStoneStr = null;
    
    private Move megaMove = null;
    
    private String megaMoveStr = null;
    
    private Integer megaMessage = null;
    
    private Integer unmegaForm = null;
    
    public Form(){
        super();
    }
    
    public Form(String id, int form){
        super(id, form);
    }
    
    public void setForm(int form){
        setVersion(form);
    }
    
    public Species getSpecies(){
        return species;
    }
    
    public void setSpecies(Species species){
        this.species = species;
    }
    
    public Integer getPokedexForm(){
        return pokedexForm;
    }
    
    public void setPokedexForm(Integer form){
        if (form != null && form < 0)
            throw new IllegalArgumentException();
        this.pokedexForm = form;
    }
    
    public boolean isPokedexFormUsed(){
        return pokedexForm != null && pokedexForm > 0;
    }
    
    public Item getMegaStone(){
        return megaStone;
    }
    
    public void setMegaStone(Item item){
        this.megaStone = item;
        this.megaStoneStr = null;
    }
    
    public Move getMegaMove(){
        return megaMove;
    }
    
    public void setMegaMove(Move move){
        this.megaMove = move;
        this.megaMoveStr = null;
    }
    
    public int getMegaMessage(){
        if (megaMessage == null)
            return 0;
        return megaMessage;
    }
    
    public void setMegaMessage(int index){
        if (index < 0)
            throw new IllegalArgumentException();
        megaMessage = index;
    }
    
    public boolean isMegaMessageSet(){
        return megaMessage != null;
    }
    
    public int getUnmegaForm(){
        if (unmegaForm == null)
            return 0;
        return unmegaForm;
    }
    
    public void setUnmegaForm(int form){
        if (form < 0)
            throw new IllegalArgumentException();
        this.unmegaForm = form;
    }
    
    public boolean isUnmegaFormSet(){
        return unmegaForm != null;
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
                setPokedexForm(Integer.valueOf(value.trim()));
                break;
            case(3):
                setTypesStr(value);
                break;
            case(4):
                readBaseStats(value);
                break;
            case(5):
                setBaseXP(Integer.parseInt(value));
                break;
            case(6):
                readEVs(value);
                break;
            case(7):
                setCatchRate(Integer.parseInt(value));
                break;
            case(8):
                setHappiness(Integer.parseInt(value));
                break;
            case(9):
                setAbilityStr(value);
                break;
            case(10):
                setHiddenAbilitiesStr(value);
                break;
            case(11):
                setMovesStr(value);
                break;
            case(12):
                setTutorMovesStr(value);
                break;
            case(13):
                setEggMovesStr(value);
                break;
            case(14):
                setEggGroups(value);
                break;
            case(15):
                setHatchSteps(Integer.parseInt(value));
                break;
            case(16):
                setOffspringStr(value);
                break;
            case(17):
                setHeight(Double.parseDouble(value));
                break;
            case(18):
                setWeight(Double.parseDouble(value));
                break;
            case(19):
                setColor(value);
                break;
            case(20):
                setShape(value);
                break;
            case(21):
                setHabitat(value);
                break;
            case(22):
                setCategory(value);
                break;
            case(23):
                setPokedexEntry(value);
                break;
            case(24):
                setGeneration(Integer.parseInt(value));
                break;
            case(25):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(26):
                setCommonWildItemsStr(value);
                break;
            case(27):
                setUncommonWildItemsStr(value);
                break;
            case(28):
                setRareWildItemsStr(value);
                break;
            case(29):
                setEvolutionsStr(value);
                break;
            case(30):
                megaStoneStr = value.trim();
                break;
            case(31):
                megaMoveStr = value.trim();
                break;
            case(32):
                setMegaMessage(Integer.parseInt(value.trim()));
                break;
            case(33):
                setUnmegaForm(Integer.parseInt(value.trim()));
        }
        if (propertyIndex == getPropertyNames().length){
            int temp = value.lastIndexOf(",");
            if (temp > 0){
                setID(value.substring(0, temp).trim());
                setForm(Integer.parseInt(value.substring(temp+1).trim()));
            } else 
                setID(value);
        }
    }
    @Override
    protected String[] getPropertyNames() {
        return PROPERTY_NAMES;
    }
    @Override
    protected void reset(){
        super.reset();
        setSpecies(null);
        setPokedexForm(null);
        megaStone = null;
        megaStoneStr = null;
        megaMove = null;
        megaMoveStr = null;
        megaMessage = null;
        unmegaForm = null;
    }
    @Override
    public String toData(){
        String str = super.toData();
        if (isPokedexFormUsed())
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getPokedexForm();
        else if (getFormName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getFormName();
        if (!getTypes().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + toIDString(getTypes());
        else if (getTypesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + getTypesStr();
        if (!getBaseStats().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + getBaseStatsStr();
        if (isBaseXPSet())
            str += System.lineSeparator()+PROPERTY_NAMES[4] + " = " + getBaseXP();
        if (!getEVs().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[5] + " = " + getEVsStr();
        if (isCatchRateSet())
            str += System.lineSeparator()+PROPERTY_NAMES[6] + " = " + getCatchRate();
        if (isHappinessSet())
            str += System.lineSeparator()+PROPERTY_NAMES[7] + " = " + getHappiness();
        String abilities = getAbilitiesStr();
        if (abilities != null)
            str += System.lineSeparator()+PROPERTY_NAMES[8] + " = " + abilities;
        if (!getHiddenAbilities().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[9] + " = " + toIDString(getHiddenAbilities());
        else if (getHiddenAbilitiesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[9] + " = " + getHiddenAbilitiesStr();
        String moves = getMovesToString();
        if (moves != null)
            str += System.lineSeparator()+PROPERTY_NAMES[10] + " = " + moves;
        if (!getTutorMoves().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[11] + " = " + toIDString(getTutorMoves());
        else if (getTutorMovesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[11] + " = " + getTutorMovesStr();
        if (!getEggMoves().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[12] + " = " + toIDString(getEggMoves());
        else if (getEggMovesStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[12] + " = " + getEggMovesStr();
        if (!getEggGroups().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[13] + " = " + getEggGroupsStr();
        if (isHatchStepsSet())
            str += System.lineSeparator()+PROPERTY_NAMES[14] + " = " + getHatchSteps();
        if (!getOffspring().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[15] + " = " + toIDString(getOffspring());
        else if (getOffspringStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[15] + " = " + getOffspringStr();
        if (isHeightSet())
            str += System.lineSeparator()+PROPERTY_NAMES[16] + " = " + getHeight();
        if (isWeightSet())
            str += System.lineSeparator()+PROPERTY_NAMES[17] + " = " + getWeight();
        if (isColorSet())
            str += System.lineSeparator()+PROPERTY_NAMES[18] + " = " + getColor();
        if (isShapeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[19] + " = " + getShape();
        if (isHabitatSet())
            str += System.lineSeparator()+PROPERTY_NAMES[20] + " = " + getHabitat();
        if (getCategory() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[21] + " = " + getCategory();
        if (getPokedexEntry() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[22] + " = " + getPokedexEntry();
        if (isGenerationSet())
            str += System.lineSeparator()+PROPERTY_NAMES[23] + " = " + getGeneration();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[24] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (!getCommonWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[25] + " = " + toIDString(getCommonWildItems());
        else if (getCommonWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[25] + " = " + getCommonWildItemsStr();
        if (!getUncommonWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[26] + " = " + toIDString(getUncommonWildItems());
        else if (getUncommonWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[26] + " = " + getUncommonWildItemsStr();
        if (!getRareWildItems().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[27] + " = " + toIDString(getRareWildItems());
        else if (getRareWildItemsStr() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[27] + " = " + getRareWildItemsStr();
        String evolutions = evolutionsToString();
        if (evolutions != null)
            str += System.lineSeparator()+PROPERTY_NAMES[28] + " = " + evolutions;
        if (getMegaStone() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[29] + " = " + getMegaStone();
        else if (megaStoneStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[29] + " = " + megaStoneStr;
        if (getMegaMove() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[30] + " = " + getMegaMove();
        else if (megaMoveStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[30] + " = " + megaMoveStr;
        if (isMegaMessageSet())
            str += System.lineSeparator()+PROPERTY_NAMES[31] + " = " + getMegaMessage();
        if (isUnmegaFormSet())
            str += System.lineSeparator()+PROPERTY_NAMES[32] + " = " + getUnmegaForm();
        return str;
    }
}
