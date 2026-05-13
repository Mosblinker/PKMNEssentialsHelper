/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import pkmn.essentials.data.pkmn.Move;
import pkmn.essentials.data.pkmn.Type;

/**
 *
 * @author Mosblinker
 */
public class Item extends AbstractPBSElement implements Comparable<Item>{
    
    public static final String PBS_FILE_TEMPLATE = "items";
    
    public static final String SPRITE_FOLDER = "Graphics"+File.separator+"Items";
    
    public static final String SPRITE_FILE_EXTENSION = "png";
    
    public static final String DEFAULT_SPRITE_NAME = "000";
    
    public static final String HM_SPRITE_NAME_PREFIX = "machine_hm_";
    
    public static final String TM_SPRITE_NAME_PREFIX = "machine_";
    
    public static final String TR_SPRITE_NAME_PREFIX = "machine_tr_";
    /**
     * The Natural Gift flag has a type and a number and thus should be handled 
     * in a special way.
     */
    public static final String NATURAL_GIFT_FLAG_PREFIX = "NaturalGift";
    
    public static final String KEY_ITEM_FLAG = "KeyItem";
    
    public static final String[] DEFAULT_FLAGS = {
        "Mail",
        "IconMail",
        "PokeBall",
        "SnagBall",
        "Berry",
        KEY_ITEM_FLAG,
        "EvolutionStone",
        "Fossil",
        "Apricorn",
        "TypeGen",
        "Mulch",
        "MegaStone",
        "Repel",
        "Fling_"+NUMBER_REGEX,
        NATURAL_GIFT_FLAG_PREFIX+"_.+_"+NUMBER_REGEX
    };
    
    public static final String[] DEFAULT_FLAG_DESCRIPTIONS = {
        "The item is a Mail item.",
        "The item is a Mail item, and the images of the holder and two other party Pokémon appear on the Mail.",
        "The item is a Poké Ball item.",
        "The item is a Snag Ball (i.e. it can capture enemy trainers' Shadow Pokémon).",
        "The item is a berry that can be planted.",
        "The item is a Key Item.",
        "The item is an evolution stone.",
        "The item is a fossil that can be revived. Not to be used for the incomplete fossils from Gen 8 which are pieced together to revive a Pokémon.",
        "The item is an Apricorn that can be converted into a Poké Ball.",
        "The item is an elemental power-raising Gem.",
        "The item is mulch that can be spread on berry patches.",
        "The item is a Mega Stone. This does NOT include the Red/Blue Orbs.",
        "The item is a Mega Ring, which allows Mega Evolution.",
        "The item is a Repel, and can be used automatically when a Repel runs out.",
        "The item can be thrown by the move Fling. The number is Fling's base power when used with the item. The number can be any number, but should be greater than 0.",
        "The item can be used by the move Natural Gift. The type and number are Natural Gift's type and base power when used with the item. The type can be any type, and the number can be any number but should be greater than 0."
    };
    
    public static final String FLAG_FILE = "item_flags.txt";
    
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
    
    public static final String[] DEFAULT_BAG_POCKET_NAMES = {
        "Items",
        "Medicine",
        "Poké Balls",
        "TMs & HMs",
        "Berries",
        "Mail",
        "Battle Items",
        "Key Items"
    };
    
    public static final String BAG_POCKETS_NAME_FILE = "bag_pockets.txt";
    
    private static List<String> bagPockets = new ArrayList<>(Arrays.asList(DEFAULT_BAG_POCKET_NAMES));
    
    public static List<String> getBagPocketNames(){
        return bagPockets;
    }
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "NamePlural",
        "PortionName",
        "PortionNamePlural",
        "Pocket",
        "Price",
        "SellPrice",
        "BPPrice",
        "FieldUse",
        "BattleUse",
        "Flags",
        "Consumable",
        "ShowQuantity",
        "Move",
        "Description"
    };
    
    private static Map<ItemFieldUse,Map<Type,ImageIcon>> tutorMachineSprites = new HashMap<>();
    
    public static Map<ItemFieldUse,Map<Type,ImageIcon>> getTutorMachineIcons(){
        return tutorMachineSprites;
    }
    
    public static void setTutorMachineIcons(Map<String, BufferedImage> sprites, Collection<? extends Type> types){
        getTutorMachineIcons().clear();
        getTutorMachineIcons().putAll(getTutorMachineIcons(sprites,types));
    }
    
    private static ImageIcon defaultItemIcon = null;
    
    public static ImageIcon getDefaultImageIcon(){
        return defaultItemIcon;
    }
    
    public static void setDefaultImageIcon(ImageIcon icon){
        defaultItemIcon = icon;
    }
    
    private String namePlural = null;
    
    private String portionName = null;
    
    private String portionNamePlural = null;
    
    private Integer pocket = null;
    
    private Integer price = null;
    
    private Integer sellPrice = null;
    
    private Integer bpPrice = null;
    
    private ItemFieldUse fieldUse = null;
    
    private ItemBattleUse battleUse = null;
    
    private Boolean consumable = null;
    
    private Boolean showQuantity = null;
    
    private Move move = null;
    
    private String moveStr = null;
    
    private String desc = null;
    
    private ImageIcon icon = null;
    
    public Item(){
        super();
    }
    
    public Item(String id){
        super(id);
    }
    
    public Item(Item item){
        super(item);
        this.namePlural = item.namePlural;
        this.portionName = item.portionName;
        this.portionNamePlural = item.portionNamePlural;
        this.pocket = item.pocket;
        this.price = item.price;
        this.sellPrice = item.sellPrice;
        this.bpPrice = item.bpPrice;
        this.fieldUse = item.fieldUse;
        this.battleUse = item.battleUse;
        this.consumable = item.consumable;
        this.move = item.move;
        this.moveStr = item.moveStr;
        this.desc = item.desc;
        this.icon = item.icon;
    }
    /**
     * The internal ID for this item. This is how the scripts will refer to the 
     * item. Each item much have a different ID. This is surrounded by square 
     * brackets in the PBS file.
     * @return 
     */
    @Override
    public String getID(){
        return super.getID();
    }
    /**
     * The name of this item, as seen by the player.
     * @return 
     */
    @Override
    public String getName(){
        return super.getName();
    }
    
    public String getNamePlural(){
        return (namePlural != null) ? namePlural : getName();
    }
    
    public void setNamePlural(String name){
        namePlural = name;
    }
    
    public boolean isNamePluralSet(){
        return namePlural != null;
    }
    
    public String getPortionName(){
        return (portionName != null) ? portionName : getName();
    }
    
    public void setPortionName(String name){
        portionName = name;
    }
    
    public boolean isPortionNameSet(){
        return portionName != null;
    }
    
    public String getPortionNamePlural(){
        return (portionNamePlural != null) ? portionNamePlural : getPortionName();
    }
    
    public void setPortionNamePlural(String name){
        portionNamePlural = name;
    }
    
    public boolean isPortionNamePluralSet(){
        return portionNamePlural != null;
    }
    
    public int getBagPocket(){
        return (pocket!=null)?pocket:1;
    }
    
    public void setBagPocket(Integer pocket){
        this.pocket = pocket;
    }
    
    public boolean isBagPocketSet(){
        return pocket != null;
    }
    
    public int getPrice(){
        return (price != null) ? price : null;
    }
    
    public void setPrice(Integer price){
        this.price = price;
    }
    
    public boolean isPriceSet(){
        return price != null;
    }
    
    public int getSellPrice(){
        return (sellPrice != null) ? sellPrice : Math.floorDiv(getPrice(), 2);
    }
    
    public void setSellPrice(Integer price){
        sellPrice = price;
    }
    
    public boolean isSellPriceSet(){
        return sellPrice != null;
    }
    
    public int getBattlePointsPrice(){
        return (bpPrice != null) ? bpPrice : 1;
    }
    
    public void setBattlePointsPrice(Integer price){
        bpPrice = price;
    }
    
    public boolean isBattlePointsPriceSet(){
        return bpPrice != null;
    }
    
    public ItemFieldUse getFieldUse(){
        return fieldUse;
    }
    
    public void setFieldUse(ItemFieldUse use){
        this.fieldUse = use;
    }
    
     public boolean isTutorMachine(){
         return ItemFieldUse.HM.equals(getFieldUse()) || 
                 ItemFieldUse.TM.equals(getFieldUse()) || 
                ItemFieldUse.TR.equals(getFieldUse());
     }
    
    public ItemBattleUse getBattleUse(){
        return battleUse;
    }
    
    public void setBattleUse(ItemBattleUse use){
        this.battleUse = use;
    }
    
    @Override
    public List<String> getFlags(){
        return super.getFlags();
    }
    
    private boolean isKeyOrTmOrHm(){
        return getFlags().contains(KEY_ITEM_FLAG) || 
                ItemFieldUse.TM.equals(getFieldUse()) || 
                ItemFieldUse.HM.equals(getFieldUse());
    }
    
    public boolean isConsumable(){
        if (consumable != null)
            return consumable;
        return !isKeyOrTmOrHm();
    }
    
    public void setConsumable(Boolean value){
        consumable = value;
    }
    
    public boolean isConsumableSet(){
        return consumable != null;
    }
    
    public boolean isQuantityShown(){
        return (showQuantity != null) ? showQuantity : !isKeyOrTmOrHm();
    }
    
    public void setQuantityShown(Boolean value){
        showQuantity = value;
    }
    
    public boolean isQuantityShownSet(){
        return showQuantity != null;
    }
    
    public Move getMove(){
        return move;
    }
    
    public void setMove(Move move){
        this.move = move;
        moveStr = null;
    }
    
    public String getDescription(){
        if (desc == null && getMove() != null && isTutorMachine())
            return getMove().getDescription();
        return desc;
    }
    
    public void setDescription(String description){
        desc = description;
    }
    
    public boolean isDescriptionSet(){
        return desc != null;
    }
    
    public ImageIcon getIcon(){
        if (icon == null){
            if (getMove() != null && getMove().getType() != null && 
                    isTutorMachine())
                return getTutorMachineIcons().get(getFieldUse()).getOrDefault(getMove().getType(), defaultItemIcon);
            return defaultItemIcon;
        }
        return icon;
    }
    
    public void setIcon(ImageIcon icon){
        this.icon = icon;
    }
    
    public boolean isIconSet(){
        return icon != null;
    }
    
    @Override
    public String toData(){
        String str = super.toData();
        if (getName() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getName();
        if (getNamePlural() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getNamePlural();
        if (isPortionNameSet())
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + getPortionName();
        if (isPortionNameSet() && getPortionNamePlural() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + getPortionNamePlural();
        if (isBagPocketSet())
            str += System.lineSeparator()+PROPERTY_NAMES[4] + " = " + getBagPocket();
        if (isPriceSet())
            str += System.lineSeparator()+PROPERTY_NAMES[5] + " = " + getPrice();
        if (isSellPriceSet())
            str += System.lineSeparator()+PROPERTY_NAMES[6] + " = " + getSellPrice();
        if (isBattlePointsPriceSet())
            str += System.lineSeparator()+PROPERTY_NAMES[7] + " = " + getBattlePointsPrice();
        if (getFieldUse() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[8] + " = " + getFieldUse().getValue();
        if (getBattleUse() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[9] + " = " + getBattleUse().getValue();
        if (!getFlags().isEmpty())
            str += System.lineSeparator()+PROPERTY_NAMES[10] + " = " + AbstractPBSElement.getFlagString(getFlags());
        if (isConsumableSet())
            str += System.lineSeparator()+PROPERTY_NAMES[11] + " = " + isConsumable();
        if (isQuantityShownSet())
            str += System.lineSeparator()+PROPERTY_NAMES[12] + " = " + isQuantityShown();
        if (getMove() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[13] + " = " + getMove().getID();
        else if (moveStr != null)
            str += System.lineSeparator()+PROPERTY_NAMES[13] + " = " + moveStr;
        if (getDescription() != null)
            str += System.lineSeparator()+PROPERTY_NAMES[14] + " = " + getDescription();
        return str;
    }
    @Override
    protected void setProperty(int propertyIndex, String value) {
        switch(propertyIndex){
            case(0):
                setName(value);
                break;
            case(1):
                setNamePlural(value);
                break;
            case(2):
                setPortionName(value);
                break;
            case(3):
                setPortionNamePlural(value);
                break;
            case(4):
                setBagPocket(Integer.valueOf(value));
                break;
            case(5):
                setPrice(Integer.valueOf(value));
                break;
            case(6):
                setSellPrice(Integer.valueOf(value));
                break;
            case(7):
                setBattlePointsPrice(Integer.valueOf(value));
                break;
            case(8):
                setFieldUse(ItemFieldUse.parseFieldUse(value));
                break;
            case(9):
                setBattleUse(ItemBattleUse.parseBattleUse(value));
                return;
            case(10):
                AbstractPBSElement.getFlags(value, getFlags());
                break;
            case(11):
                setConsumable(Boolean.valueOf(value));
                break;
            case(12):
                setQuantityShown(Boolean.valueOf(value));
                break;
            case(13):
                moveStr = value;
                break;
            case(14):
                setDescription(value);
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
        setNamePlural(null);
        setPortionName(null);
        setPortionNamePlural(null);
        setBagPocket(null);
        setPrice(null);
        setSellPrice(null);
        setBattlePointsPrice(null);
        setFieldUse(null);
        setBattleUse(null);
        getFlags().clear();
        setConsumable(null);
        setQuantityShown(null);
        setMove(null);
        setDescription(null);
        setIcon(null);
    }

    @Override
    public int compareTo(Item o) {
        return getID().compareTo(o.getID());
    }
    
    public void finishLoading(Collection<? extends Move> moves){
        if (moveStr == null || moveStr.isBlank())
            return;
        setMove((Move)AbstractPBSElement.getMatch(moveStr, moves));
    }
    
    @Override
    public void finishLoading(PBSData data){
        finishLoading(data.getMoves());
    }
    
    public static Map<ItemFieldUse,Map<Type,ImageIcon>> getTutorMachineIcons(
            Map<String, BufferedImage> sprites, Collection<? extends Type> types){
        Map<ItemFieldUse,Map<Type,ImageIcon>> map = new HashMap<>();
        map.put(ItemFieldUse.TR, new HashMap<>());
        map.put(ItemFieldUse.HM, new HashMap<>());
        map.put(ItemFieldUse.TM, new HashMap<>());
        for (Map.Entry<String, BufferedImage> entry : sprites.entrySet()){
            ItemFieldUse use;
            if (entry.getKey().startsWith(HM_SPRITE_NAME_PREFIX))
                use = ItemFieldUse.HM;
            else if (entry.getKey().startsWith(TR_SPRITE_NAME_PREFIX))
                use = ItemFieldUse.TR;
            else if (entry.getKey().startsWith(TM_SPRITE_NAME_PREFIX))
                use = ItemFieldUse.TM;
            else
                continue;
            for (Type type : types){
                if (entry.getKey().endsWith(type.getID())){
                    map.get(use).put(type, new ImageIcon(entry.getValue()));
                    break;
                }
            }
        }
        return map;
    }
}
