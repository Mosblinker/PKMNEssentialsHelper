/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.*;
import javax.swing.ImageIcon;
import pkmn.essentials.data.AbstractPBSElement;
import pkmn.essentials.data.Item;
import pkmn.essentials.data.PBSData;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractSpecies extends AbstractPBSElement implements Comparable<AbstractSpecies> {
    
    public static final String[] DEFAULT_EGG_GROUPS = {
        "Monster",
        "Water1",
        "Bug",
        "Flying",
        "Field",
        "Fairy",
        "Grass",
        "Humanlike",
        "Water3",
        "Mineral",
        "Amorphous",
        "Water2",
        "Ditto",
        "Dragon",
        "Undiscovered"
    };
    
    private static List<String> eggGroupNames = new ArrayList<>(Arrays.asList(DEFAULT_EGG_GROUPS));
    
    public static List<String> getEggGroupNames(){
        return eggGroupNames;
    }
    
    public static final String EGG_GROUP_NAME_FILE = "egg_groups.txt";
    
    public static final String[] DEFAULT_COLOR_NAMES = {
        "Black",
        "Blue",
        "Brown",
        "Gray",
        "Green",
        "Pink",
        "Purple",
        "Red",
        "White",
        "Yellow"
    };
    
    private static List<String> colorNames = new ArrayList<>(Arrays.asList(DEFAULT_COLOR_NAMES));
    
    public static List<String> getColorNames(){
        return colorNames;
    }
    
    public static final String COLOR_NAME_FILE = "pkmn_colors.txt";
    
    public static final String[] DEFAULT_BODY_SHAPES = {
        "Head",
        "Serpentine",
        "Finned",
        "HeadArms",
        "HeadBase",
        "BipedalTail",
        "HeadLegs",
        "Quadruped",
        "Winged",
        "Multiplied",
        "MultiBody",
        "Bipedal",
        "MultiWinged",
        "Insectoid"
    };
    
    private static List<String> bodyShapes = new ArrayList<>(Arrays.asList(DEFAULT_BODY_SHAPES));
    
    public static List<String> getBodyShapes(){
        return bodyShapes;
    }
    
    public static final String BODY_SHAPES_FILE = "pkmn_body_shapes.txt";
    
    public static final String[] DEFAULT_HABITAT_NAMES = {
        "None",
        "Cave",
        "Forest",
        "Grassland",
        "Mountain",
        "Rare",
        "RoughTerain",
        "Sea",
        "Urban",
        "WatersEdge"
    };
    
    private static List<String> habitatNames = new ArrayList<>(Arrays.asList(DEFAULT_HABITAT_NAMES));
    
    public static List<String> getHabitatNames(){
        return habitatNames;
    }
    
    public static final String HABITAT_NAMES_FILE = "pkmn_habitats.txt";
    
    public static final String[] DEFAULT_FLAGS = {
        "Legendary",
        "Mythical",
        "UltraBeast",
        "DefaultForm_"+PBSData.FLAG_NUMBER_TEMPLATE,
        "InheritFormFromMother",
        "InheritFormWithEverStone"
    };
    
    public static final String[] DEFAULT_FLAG_DESCRIPTIONS = {
        "A wild Pokémon with this flag will have an AI skill level of 32 instead of 0.",
        "A wild Pokémon with this flag will have an AI skill level of 32 instead of 0.",
        "The Beast Ball has a different catch rate for Pokémon with this flag. A wild Pokémon with this flag will have an AI skill level of 32 instead of 0.",
        "The form that Pokémon of a species with this flag will have by default.",
        "An egg produced by a mother with this flag will inherit the mother's form.",
        "An egg produced by a parent with this flag will inherit that parent's form if that parent is holding an Ever Stone. The mother's form takes priority."
    };
    
    public static final String FLAG_FILE = "pkmn_flags.txt";
    
    private static Map<String,String> createFlagMap(){
        Map<String, String> map = new LinkedHashMap<>();
        int i;
        for (i = 0; i < DEFAULT_FLAGS.length && i < DEFAULT_FLAG_DESCRIPTIONS.length; i++)
            map.put(DEFAULT_FLAGS[i], DEFAULT_FLAG_DESCRIPTIONS[i]);
        for (; i < DEFAULT_FLAGS.length; i++)
            map.put(DEFAULT_FLAGS[i], null);
        return map;
    }
    
    private static Map<String,String> flagMap = createFlagMap();
    
    public static Map<String, String> getFlagMap(){
        return flagMap;
    }
    
    private String formName = null;
    
    private List<Type> types = new ArrayList<>();
    
    private String typesStr = null;
    
    private Map<Stat, Integer> baseStats = new StatsMap();
    
    private Integer baseXP = null;
    
    private Map<Stat,Integer> evs = new LinkedHashMap<>();
    
    private Integer catchRate = null;
    
    private Integer happiness = null;
    
    private Ability ability1 = null;
    
    private Ability ability2 = null;
    
    private String abilityStr = null;
    
    private List<Ability> hiddenAbilities = new ArrayList<>();
    
    private String hiddenAbilityStr = null;
    
    private List<PkmnLevelUp> moves = new ArrayList<>();
    
    private String moveStr = null;
    
    private Set<Move> tutorMoves = new LinkedHashSet<>();
    
    private String tutorMoveStr = null;
    
    private Set<Move> eggMoves = new LinkedHashSet<>();
    
    private String eggMovesStr = null;
    
    private Set<String> eggGroups = new LinkedHashSet<>();
    
    private Integer hatchSteps = null;
    
    private List<Species> offspring = new ArrayList<>();
    
    private String offspringStr = null;
    
    private Double height = null;
    
    private Double weight = null;
    
    private String color = null;
    
    private String shape = null;
    
    private String habitat = null;
    
    private String category = null;
    
    private String pokedex = null;
    
    private Integer generation = null;
    
    private List<Item> wildItemCommon = new ArrayList<>();
    
    private String wildItemCommonStr = null;
    
    private List<Item> wildItemUncommon = new ArrayList<>();
    
    private String wildItemUncommonStr = null;
    
    private List<Item> wildItemRare = new ArrayList<>();
    
    private String wildItemRareStr = null;
    
    private List<PkmnEvolution> evolutions = new ArrayList<>();
    
    private String evolutionsStr = null;
    
    
    
    private ImageIcon icon = null;
    
    
    
    public AbstractSpecies(){
        super();
    }
    
    public AbstractSpecies(String id){
        super(id);
    }
    
    public AbstractSpecies(String id, int form){
        this(id);
        setVersion(form);
    }
    
    public AbstractSpecies(AbstractSpecies species){
        super(species);
        this.formName = species.formName;
        this.types.addAll(species.types);
        this.typesStr = species.typesStr;
        this.baseStats.putAll(species.baseStats);
        this.baseXP = species.baseXP;
        this.evs.putAll(species.evs);
        this.catchRate = species.catchRate;
        this.happiness = species.happiness;
        this.ability1 = species.ability1;
        this.ability2 = species.ability2;
        this.abilityStr = species.abilityStr;
        this.hiddenAbilities.addAll(species.hiddenAbilities);
        this.hiddenAbilityStr = species.hiddenAbilityStr;
        for (PkmnLevelUp levelUp : species.moves)
            this.moves.add(new PkmnLevelUp(levelUp));
        this.moveStr = species.moveStr;
        this.tutorMoves.addAll(species.tutorMoves);
        this.tutorMoveStr = species.tutorMoveStr;
        this.eggMoves.addAll(species.eggMoves);
        this.eggMovesStr = species.eggMovesStr;
        this.eggGroups.addAll(species.eggGroups);
        this.hatchSteps = species.hatchSteps;
        this.offspring.addAll(species.offspring);
        this.offspringStr = species.offspringStr;
        this.height = species.height;
        this.weight = species.weight;
        this.color = species.color;
        this.shape = species.shape;
        this.habitat = species.habitat;
        this.category = species.category;
        this.pokedex = species.pokedex;
        this.generation = species.generation;
        this.wildItemCommon.addAll(species.wildItemCommon);
        this.wildItemCommonStr = species.wildItemCommonStr;
        this.wildItemUncommon.addAll(species.wildItemUncommon);
        this.wildItemRare.addAll(species.wildItemRare);
        this.wildItemRareStr = species.wildItemRareStr;
        for (PkmnEvolution evo : species.evolutions)
            this.evolutions.add(new PkmnEvolution(evo));
        this.evolutionsStr = species.evolutionsStr;
        this.icon = species.icon;
    }
    
    public int getForm(){
        return getVersion();
    }
    
    public String getFormName(){
        return formName;
    }
    
    public void setFormName(String name){
        this.formName = name;
    }
    
    public List<Type> getTypes(){
        return types;
    }
    
    protected String getTypesStr(){
        return typesStr;
    }
    
    protected void setTypesStr(String types){
        this.typesStr = types;
    }
    
    public Map<Stat, Integer> getBaseStats(){
        return baseStats;
    }
    
    public int getBaseStat(Stat stat){
        return baseStats.getOrDefault(stat, 1);
    }
    
    public void setBaseStat(Stat stat, int value){
        baseStats.put(stat, value);
    }
    
    public int getBaseHP(){
        return getBaseStat(Stat.HP);
    }
    
    public void setBaseHP(int value){
        setBaseStat(Stat.HP, value);
    }
    
    public int getBaseAttack(){
        return getBaseStat(Stat.ATTACK);
    }
    
    public void setBaseAttack(int value){
        setBaseStat(Stat.ATTACK, value);
    }
    
    public int getBaseDefense(){
        return getBaseStat(Stat.DEFENSE);
    }
    
    public void setBaseDefense(int value){
        setBaseStat(Stat.DEFENSE, value);
    }
    
    public int getBaseSpeed(){
        return getBaseStat(Stat.SPEED);
    }
    
    public void setBaseSpeed(int value){
        setBaseStat(Stat.SPEED, value);
    }
    
    public int getBaseSpecialAttack(){
        return getBaseStat(Stat.SPECIAL_ATTACK);
    }
    
    public void setBaseSpecialAttack(int value){
        setBaseStat(Stat.SPECIAL_ATTACK, value);
    }
    
    public int getBaseSpecialDefense(){
        return getBaseStat(Stat.SPECIAL_DEFENSE);
    }
    
    public void setBaseSpecialDefense(int value){
        setBaseStat(Stat.SPECIAL_DEFENSE, value);
    }
    
    protected String getBaseStatsStr(){
        return getBaseHP()+","+getBaseAttack()+","+getBaseDefense()+","+
                getBaseSpeed()+","+getBaseSpecialAttack()+","+
                getBaseSpecialDefense();
    }
    
    protected void readBaseStats(String str){
        String[] arr = str.split(",");
        for (int i = 0; i < str.length() && i < Stat.values().length; i++){
            if (arr[i].isBlank())
                continue;
            setBaseStat(Stat.values()[i],Integer.parseInt(arr[i].trim()));
        }
    }
    
    public int getBaseXP(){
        if (baseXP != null)
            return 100;
        return baseXP;
    }
    
    public void setBaseXP(int xp){
        if (xp < 1)
            throw new IllegalArgumentException();
        baseXP = xp;
    }
    
    public boolean isBaseXPSet(){
        return baseXP != null;
    }
    
    public Map<Stat, Integer> getEVs(){
        return evs;
    }
    
    protected String getEVsStr(){
        if (getEVs().isEmpty())
            return "";
        String str = "";
        for (Map.Entry<Stat, Integer> entry : getEVs().entrySet()){
            str += entry.getKey()+","+entry.getValue()+",";
        }
        return str.substring(0, str.length()-1);
    }
    
    protected void readEVs(String str){
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length && i+1 < arr.length; i+=2){
            if (arr[i] == null || arr[i].isBlank() || arr[i+1] == null && arr[i+1].isBlank())
                continue;
            getEVs().put(Stat.valueOf(arr[i].trim()), Integer.valueOf(arr[i+1].trim()));
        }
    }
    
    public int getCatchRate(){
        if (catchRate == null)
            return 255;
        return catchRate;
    }
    
    public void setCatchRate(int rate){
        if (rate < 0)
            throw new IllegalArgumentException();
        catchRate = rate;
    }
    
    public boolean isCatchRateSet(){
        return catchRate != null;
    }
    
    public int getHappiness(){
        if (happiness == null)
            return 70;
        return happiness;
    }
    
    public void setHappiness(int value){
        if (value < 0)
            throw new IllegalArgumentException();
        happiness = value;
    }
    
    public boolean isHappinessSet(){
        return happiness != null;
    }
    
    public Ability getFirstAbility(){
        return ability1;
    }
    
    public void setFirstAbility(Ability ability){
        this.ability1 = ability;
    }
    
    public Ability getSecondAbility(){
        return ability2;
    }
    
    public void setSecondAbility(Ability ability){
        this.ability2 = ability;
    }
    
    public void setAbilities(Ability ability1, Ability ability2){
        setFirstAbility(ability1);
        setSecondAbility(ability2);
    }
    
    public void setAbilities(Ability ability1){
        setAbilities(ability1, null);
    }
    
    protected String getAbilitiesStr(){
        if (getFirstAbility() != null && getSecondAbility() != null)
            return getFirstAbility().getID()+","+getSecondAbility().getID();
        else if (getFirstAbility() != null)
            return getFirstAbility().getID();
        else if (getSecondAbility() != null)
            return getSecondAbility().getID();
        else
            return abilityStr;
    }
    
    protected String getAbilityStr(){
        return abilityStr;
    }
    
    protected void setAbilityStr(String str){
        abilityStr = str;
    }
    
    public List<Ability> getHiddenAbilities(){
        return hiddenAbilities;
    }
    
    protected String getHiddenAbilitiesStr(){
        return hiddenAbilityStr;
    }
    
    protected void setHiddenAbilitiesStr(String str){
        hiddenAbilityStr = str;
    }
    
    public List<PkmnLevelUp> getMoves(){
        return moves;
    }
    
    protected String getMoveStr(){
        return moveStr;
    }
    
    public void setMovesStr(String str){
        moveStr = str;
    }
    
    protected String getMovesToString(){
        if (!getMoves().isEmpty()){
            String str = "";
            for (PkmnLevelUp move : getMoves()){
                str += move + ",";
            }
            return str.substring(0, str.length()-1);
        }
        return moveStr;
    }
    
    public Set<Move> getTutorMoves(){
        return tutorMoves;
    }
    
    protected String getTutorMovesStr(){
        return tutorMoveStr;
    }
    
    protected void setTutorMovesStr(String str){
        tutorMoveStr = str;
    }
    
    public Set<Move> getEggMoves(){
        return eggMoves;
    }
    
    protected String getEggMovesStr(){
        return eggMovesStr;
    }
    
    protected void setEggMovesStr(String str){
        eggMovesStr = str;
    }
    
    public Set<String> getEggGroups(){
        return eggGroups;
    }
    
    protected void setEggGroups(String str){
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; i++)
            arr[i] = arr[i].trim();
        getEggGroups().addAll(Arrays.asList(arr));
    }
    
    protected String getEggGroupsStr(){
        if (getEggGroups().isEmpty())
            return "";
        String str = "";
        for (String temp : getEggGroups())
            str += temp + ",";
        return str.substring(0, str.length()-1);
    }
    
    public int getHatchSteps(){
        if (hatchSteps == null)
            return 1;
        return hatchSteps;
    }
    
    public void setHatchSteps(int steps){
        if (steps < 1)
            throw new IllegalArgumentException();
        hatchSteps = steps;
    }
    
    public boolean isHatchStepsSet(){
        return hatchSteps != null;
    }
    
    public List<Species> getOffspring(){
        return offspring;
    }
    
    protected String getOffspringStr(){
        return offspringStr;
    }
    
    protected void setOffspringStr(String str){
        offspringStr = str;
    }
    
    public double getHeight(){
        if (height == null)
            return 0.1;
        return height;
    }
    
    public void setHeight(double height){
        this.height = height;
    }
    
    public boolean isHeightSet(){
        return height != null;
    }
    
    
    public double getWeight(){
        if (weight == null)
            return 0.1;
        return weight;
    }
    
    public void setWeight(double weight){
        this.weight = weight;
    }
    
    public boolean isWeightSet(){
        return weight != null;
    }
    
    public String getColor(){
        if (color == null)
            return "Red";
        return color;
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public boolean isColorSet(){
        return color != null;
    }
    
    public String getShape(){
        if (shape == null)
            return "Head";
        return shape;
    }
    
    public void setShape(String shape){
        this.shape = shape;
    }
    
    public boolean isShapeSet(){
        return shape != null;
    }
    
    public String getHabitat(){
        if (habitat == null)
            return "None";
        return habitat;
    }
    
    public void setHabitat(String habitat){
        this.habitat = habitat;
    }
    
    public boolean isHabitatSet(){
        return habitat != null;
    }
    
    public String getCategory(){
        return category;
    }
    
    public void setCategory(String category){
        this.category = category;
    }
    
    public String getPokedexEntry(){
        return pokedex;
    }
    
    public void setPokedexEntry(String entry){
        pokedex = entry;
    }
    
    public int getGeneration(){
        if (generation == null)
            return 0;
        return generation;
    }
    
    public void setGeneration(int generation){
        this.generation = generation;
    }
    
    public boolean isGenerationSet(){
        return generation != null;
    }
    
    @Override
    public List<String> getFlags(){
        return super.getFlags();
    }
    
    public List<Item> getCommonWildItems(){
        return wildItemCommon;
    }
    
    protected String getCommonWildItemsStr(){
        return wildItemCommonStr;
    }
    
    protected void setCommonWildItemsStr(String str){
        wildItemCommonStr = str;
    }
    
    public List<Item> getUncommonWildItems(){
        return wildItemUncommon;
    }
    
    protected String getUncommonWildItemsStr(){
        return wildItemUncommonStr;
    }
    
    protected void setUncommonWildItemsStr(String str){
        wildItemUncommonStr = str;
    }
    
    public List<Item> getRareWildItems(){
        return wildItemRare;
    }
    
    protected String getRareWildItemsStr(){
        return wildItemRareStr;
    }
    
    protected void setRareWildItemsStr(String str){
        wildItemRareStr = str;
    }
    
    public List<PkmnEvolution> getEvolutions(){
        return evolutions;
    }
    
    protected String getEvolutionsStr(){
        return evolutionsStr;
    }
    
    protected void setEvolutionsStr(String str){
        this.evolutionsStr = str;
    }
    
    protected String evolutionsToString(){
        if (getEvolutions().isEmpty())
            return evolutionsStr;
        String str = "";
        for (PkmnEvolution temp : getEvolutions())
            str += temp + ",";
        return str.substring(0, str.length()-1);
    }
    
    public ImageIcon getIcon(){
        return icon;
    }
    
    public void setIcon(ImageIcon icon){
        this.icon = icon;
    }

    @Override
    public int compareTo(AbstractSpecies o) {
        if (Objects.equals(getID(), o.getID()))
            return Integer.compare(getVersion(), o.getVersion());
        return getID().compareTo(o.getID());
    }
    
    @Override
    protected void reset(){
        super.reset();
        setFormName(null);
        getTypes().clear();
        typesStr = null;
        getBaseStats().clear();
        baseXP = null;
        evs.clear();
        catchRate = null;
        happiness = null;
        setAbilities(null);
        abilityStr = null;
        hiddenAbilities.clear();
        hiddenAbilityStr = null;
        moves.clear();
        moveStr = null;
        tutorMoves.clear();
        tutorMoveStr = null;
        eggMoves.clear();
        eggMovesStr = null;
        eggGroups.clear();
        hatchSteps = null;
        offspring.clear();
        offspringStr = null;
        height = null;
        weight = null;
        color = null;
        shape = null;
        habitat = null;
        category = null;
        pokedex = null;
        generation = null;
        wildItemCommon.clear();
        wildItemCommonStr = null;
        wildItemUncommon.clear();
        wildItemUncommonStr = null;
        wildItemRare.clear();
        wildItemRareStr = null;
        evolutions.clear();
        evolutionsStr = null;
        icon = null;
    }
    
    @Override
    public void finishLoading(PBSData data){
        if (typesStr != null && !typesStr.isBlank()){
            String[] tempArr = typesStr.split(",");
            for (String temp : tempArr){
                Type type = (Type) AbstractPBSElement.getMatch(temp, data.getTypes());
                if (type != null)
                    getTypes().add(type);
            }
            typesStr = null;
        }
        if (abilityStr != null && !abilityStr.isBlank()){
            if (abilityStr.contains(",")){
                String[] tempArr = abilityStr.split(",");
                setAbilities((Ability)AbstractPBSElement.getMatch(tempArr[0], data.getAbilities()),
                        (Ability)AbstractPBSElement.getMatch(tempArr[1], data.getAbilities()));
            } else {
                setAbilities((Ability)AbstractPBSElement.getMatch(abilityStr, data.getAbilities()));
            }
            abilityStr = null;
        }
        if (hiddenAbilityStr != null && !hiddenAbilityStr.isBlank()){
            String[] tempArr = hiddenAbilityStr.split(",");
            for (String temp : tempArr){
                Ability ability = (Ability) AbstractPBSElement.getMatch(temp, data.getAbilities());
                if (ability != null)
                    getHiddenAbilities().add(ability);
            }
            hiddenAbilityStr = null;
        }
        if (moveStr != null && !moveStr.isBlank()){
            String[] tempArr = moveStr.split(",");
            for (int i = 0; i < tempArr.length; i+= 2){
                int level = 1;
                try{
                    level = Integer.parseInt(tempArr[i].trim());
                } catch (NumberFormatException ex){
                    i--;
                }
                Move move = (Move) AbstractPBSElement.getMatch(tempArr[i], data.getMoves());
                if (move != null)
                    getMoves().add(new PkmnLevelUp(level,move));
            }
            moveStr = null;
        }
        loadMoves(tutorMoveStr,getTutorMoves(),data.getMoves());
        tutorMoveStr = null;
        loadMoves(eggMovesStr,getEggMoves(),data.getMoves());
        eggMovesStr = null;
        if (offspringStr != null && !offspringStr.isBlank()){
            String[] tempArr = offspringStr.split(",");
            for (String temp : tempArr){
                Species species = (Species) AbstractPBSElement.getMatch(temp, data.getSpecies());
                if (species != null)
                    getOffspring().add(species);
            }
            offspringStr = null;
        }
        loadItems(wildItemCommonStr,getCommonWildItems(),data.getItems());
        wildItemCommonStr = null;
        loadItems(wildItemUncommonStr,getUncommonWildItems(),data.getItems());
        wildItemUncommonStr = null;
        loadItems(wildItemRareStr,getRareWildItems(),data.getItems());
        wildItemRareStr = null;
        if (evolutionsStr != null && !evolutionsStr.isBlank()){
            String[] tempArr = evolutionsStr.split(",");
            for (int i = 0; i < tempArr.length; i+=3){
                Species species = (Species) AbstractPBSElement.getMatch(tempArr[i], data.getSpecies());
                if (species == null)
                    continue;
                EvolutionMethod method = null;
                if (i+1 < tempArr.length)
                    method = EvolutionMethod.getMethod(tempArr[i+1]);
                if (method == null)
                    continue;
                Object parameter = null;
                if (i+2 < tempArr.length && method.getParameterClass() != null){
                    parameter = getEvolutionParameter(tempArr[i+2].trim(),method,data);
                }
                getEvolutions().add(new PkmnEvolution(species,method,parameter));
            }
            evolutionsStr = null;
        }
    }
    
    protected Object getEvolutionParameter(String str, EvolutionMethod method, PBSData data){
        if (str == null || str.isBlank())
            return null;
        if (Integer.class.equals(method.getParameterClass())){
            try{
                return Integer.valueOf(str);
            } catch (NumberFormatException ex) {}
        } else if (Double.class.equals(method.getParameterClass())){
            try{
                return Double.valueOf(str);
            } catch (NumberFormatException ex) {}
        } else if (Move.class.equals(method.getParameterClass())){
            return (Move) AbstractPBSElement.getMatch(str, data.getMoves());
        } else if (Type.class.equals(method.getParameterClass())){
            return (Type) AbstractPBSElement.getMatch(str, data.getTypes());
        } else if (Item.class.equals(method.getParameterClass())){
            return (Item) AbstractPBSElement.getMatch(str, data.getItems());
        } else if (Species.class.equals(method.getParameterClass())){
            return (Species) AbstractPBSElement.getMatch(str, data.getSpecies());
        } else if (Ability.class.equals(method.getParameterClass())){
            return (Ability) AbstractPBSElement.getMatch(str, data.getAbilities());
        }
        return str;
    }
    
    private void loadMoves(String str, Collection<Move> target, List<Move> moves){
        if (str == null || str.isBlank())
            return;
        String[] tempArr = str.split(",");
        for (String temp : tempArr){
            Move move = (Move) AbstractPBSElement.getMatch(temp, moves);
            if (move != null)
                target.add(move);
        }
    }
    
    private void loadItems(String str, Collection<Item> target, List<Item> items){
        if (str == null || str.isBlank())
            return;
        String[] tempArr = str.split(",");
        for (String temp : tempArr){
            Item item = (Item) AbstractPBSElement.getMatch(temp, items);
            if (item != null)
                target.add(item);
        }
    }
    
    @Override
    public String toString(){
        String str = getID();
        if (getForm() > 0) {
            str += "," + getForm();
        }
        return str;
    }
    
    public String getIDString() {
        return "[" + toString() + "]";
    }
    @Override
    public String toData(){
        return getIDString();
    }
    
    private class StatsMap extends AbstractMap<Stat, Integer>{
        
        private Map<Stat, Integer> map = new HashMap<>();
        
        private Set<Entry<Stat, Integer>> entrySet = null;
        
        @Override
        public Integer get(Object key){
            return map.get(Objects.requireNonNull(key));
        }
        @Override
        public Integer put(Stat key, Integer value){
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            if (value < 1)
                throw new IllegalArgumentException();
            return map.put(key, value);
        }
        @Override
        public Integer remove(Object key){
            return map.remove(key);
        }
        @Override
        public Set<Entry<Stat, Integer>> entrySet() {
            if (entrySet == null){
                entrySet = new AbstractSet<>(){
                    Set<Entry<Stat, Integer>> set = map.entrySet();
                    @Override
                    public Iterator<Entry<Stat, Integer>> iterator() {
                        return new Iterator<>(){
                            Iterator<Entry<Stat, Integer>> itr = set.iterator();
                            @Override
                            public boolean hasNext() {
                                return itr.hasNext();
                            }
                            @Override
                            public Entry<Stat, Integer> next() {
                                return itr.next();
                            }
                            @Override
                            public void remove() {
                                itr.remove();
                            }
                        };
                    }
                    @Override
                    public boolean remove(Object value){
                        return set.remove(value);
                    }
                    @Override
                    public int size() {
                        return map.size();
                    }
                };
            }
            return entrySet;
        }
    }
}
