/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import pkmn.essentials.PkmnHelperUtilities;
import pkmn.essentials.data.pkmn.*;

/**
 *
 * @author Mosblinker
 */
public class PBSData {
    
    public static final String PBS_FOLDER = "PBS";
    
    public static final String PBS_FILE_EXTENSION = "txt";
    
    public static final String FILE_HEADER = "# See the documentation on the wiki to learn how to edit this file.";
    
    public static final String DATA_SEPARATOR = "#-------------------------------";
    
    public static boolean isID(String str){
        return str.startsWith("[") && str.endsWith("]");
    }
    
    public static final String NUMBER_REGEX = "[0-9]+";
    
    public static final String TEXT_REGEX = ".+";
    
    public static final String FLAG_NUMBER_TEMPLATE = "[##]";
    
    public static final String FLAG_TYPE_TEMPLATE = "[TYPE]";
    
    public static final String[][] FLAG_TEMPLATE_DESCRIPTIONS = {
        {FLAG_NUMBER_TEMPLATE,"Number"},
        {FLAG_TYPE_TEMPLATE,"Type"}
    };
    
    private static final String[][] FLAG_TEMPLATE_REGEX = {
        {FLAG_NUMBER_TEMPLATE,NUMBER_REGEX},
        {FLAG_TYPE_TEMPLATE,TEXT_REGEX}
    };
    
    private static Map<String,String> getFlagTemplateToRegexMap(){
        Map<String,String> map = new LinkedHashMap<>();
        for (String[] entry : FLAG_TEMPLATE_REGEX){
            map.put(entry[0],entry[1]);
        }
        return Collections.unmodifiableMap(map);
    }
    
    public static final Map<String,String> FLAG_TEMPLATE_TO_REGEX_MAP = getFlagTemplateToRegexMap();
    
    private static Map<String, String> flagRegexMap = new LinkedHashMap<>();
    
    public static Map<String, String> getFlagRegexMap(){
        return flagRegexMap;
    }
    
    public static void addFlagsToRegexMap(Collection<String> flags){
        for (String flag : flags){
            if (getFlagRegexMap().containsKey(flag))
                continue;
            String regex = flag;
            boolean changed = false;
            for (Map.Entry<String,String> entry : FLAG_TEMPLATE_TO_REGEX_MAP.entrySet()){
                int index = 0;
                do {
                    index = regex.indexOf(entry.getKey(), index);
                    if (index >= 0){
                        if (index > 0 && regex.charAt(index-1) == '\\'){
                            index += entry.getKey().length();
                            continue;
                        }
                        regex = regex.substring(0, index)+entry.getValue()+regex.substring(index+entry.getKey().length());
                        changed = true;
                    }
                } while (index < regex.length() && index >= 0);
            }
            if (changed)
                getFlagRegexMap().put(flag, regex);
        }
    }
    
    public static void addFlagsToRegexMap(Map<String,String> flags){
        addFlagsToRegexMap(flags.keySet());
    }
    
    public static void writePBSDataFile(PrintWriter out, Collection<? extends AbstractPBSElement> data, JProgressBar progressBar){
        out.println(PBSData.FILE_HEADER);
        out.println(PBSData.DATA_SEPARATOR);
        for (AbstractPBSElement temp : data){
            out.println(temp.toData());
            out.println(PBSData.DATA_SEPARATOR);
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
    }
    
    public static void writePBSDataFile(PrintWriter out, Collection<? extends AbstractPBSElement> data){
        writePBSDataFile(out,data,null);
    }
    
    private List<Type> types = new ArrayList<>();
    
    private List<Move> moves = new ArrayList<>();
    
    private List<Ability> abilities = new ArrayList<>();
    
    private List<Item> items = new ArrayList<>();
    
    private List<Species> species = new ArrayList<>();
    
    private Map<AbstractSpecies, Metrics> metrics = new TreeMap<>();
    
    private List<TownMap> regions = new ArrayList<>();
    
    private List<ImageIcon> typeIcons = new ArrayList<>();
    
    private Map<MoveCategory, ImageIcon> moveCategoryIcons = new HashMap<>();
    
    private Map<String, File> townMapFileNameMap = new LinkedHashMap<>();
    
    private Map<String, BufferedImage> townMapFileNameImages = new LinkedHashMap<>();
    
    public PBSData(){
        
    }
    
    public PBSData(PBSData data){
        for (Type type : data.types)
            this.types.add(new Type(type));
        for (Move move : data.moves){
            Move temp = new Move(move);
            int index = data.types.indexOf(move.getType());
            if (index >= 0)
                temp.setType(this.types.get(index));
            this.moves.add(temp);
        }
        for (Ability ability : data.abilities){
            this.abilities.add(new Ability(ability));
        }
        for (Item item : data.items){
            Item temp = new Item(item);
            int index = data.moves.indexOf(temp.getMove());
            if (index >= 0)
                temp.setMove(this.moves.get(index));
            this.items.add(temp);
        }
        
        this.typeIcons.addAll(data.typeIcons);
        this.moveCategoryIcons.putAll(data.moveCategoryIcons);
    } 
    
    public List<Type> getTypes(){
        return types;
    }
    
    public void loadTypes(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next type
            Type type = new Type();
            index = type.fromData(lines, index, progressBar);
            if (type.getID() != null)
                getTypes().add(type);
        }
    }
    
    public void loadTypes(List<String> lines){
        loadTypes(lines,null);
    }
    
    public List<ImageIcon> getTypeIcons(){
        return typeIcons;
    }
    
    public void setTypeIcons(List<? extends ImageIcon> icons){
        getTypeIcons().clear();
        getTypeIcons().addAll(icons);
        for (Type type : getTypes()){
            if (type.getIconPosition() >= 0 && type.getIconPosition() < icons.size())
                type.setIcon(icons.get(type.getIconPosition()));
            else
                type.setIcon(null);
        }
    }
    
    public void setTypeIcons(BufferedImage image){
        setTypeIcons(PkmnHelperUtilities.splitIcons(image, getTypes().size(), 1));
    }
    
    public List<Move> getMoves(){
        return moves;
    }
    
    public void loadMoves(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next move
            Move move = new Move();
            index = move.fromData(lines, index, progressBar);
            if (move.getID() != null)
                getMoves().add(move);
        }
    }
    
    public void loadMoves(List<String> lines){
        loadMoves(lines,null);
    }
    
    public Map<MoveCategory, ImageIcon> getMoveCategoryIcons(){
        return moveCategoryIcons;
    }
    
    public void setMoveCategoryIcons(BufferedImage image){
        getMoveCategoryIcons().clear();
        List<ImageIcon> icons = PkmnHelperUtilities.splitIcons(image, MoveCategory.values().length, 1);
        for (int i = 0; i < MoveCategory.values().length; i++){
            getMoveCategoryIcons().put(MoveCategory.values()[i], icons.get(i));
            icons.get(i).setDescription(MoveCategory.values()[i].toString());
        }
    }
    
    public List<Ability> getAbilities(){
        return abilities;
    }
    
    public void loadAbilities(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next ability
            Ability ability = new Ability();
            index = ability.fromData(lines, index, progressBar);
            if (ability.getID() != null)
                getAbilities().add(ability);
        }
    }
    
    public void loadAbilities(List<String> lines){
        loadAbilities(lines,null);
    }
    
    public List<Item> getItems(){
        return items;
    }
    
    public void loadItems(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next type
            Item item = new Item();
            index = item.fromData(lines, index, progressBar);
            if (item.getID() != null)
                getItems().add(item);
        }
    }
    
    public void loadItems(List<String> lines){
        loadItems(lines,null);
    }
    
    public List<TownMap> getRegionMaps(){
        return regions;
    }
    
    public void loadRegionMaps(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next map
            TownMap map = new TownMap();
            index = map.fromData(lines, index, progressBar);
            if (map.getID() != null)
                getRegionMaps().add(map);
        }
    }
    
    public void loadRegionMaps(List<String> lines){
        loadRegionMaps(lines,null);
    }
    
    public Map<String, File> getRegionMapFiles(){
        return townMapFileNameMap;
    }
    
    public void setRegionMapFiles(File projectFolder){
        getRegionMapFiles().clear();
        File imageFolder = new File(projectFolder, TownMap.MAP_IMAGES_LOCATION);
        for (TownMap map : getRegionMaps()){
            String name = map.getFileName();
            if (name != null && !name.isBlank())
                getRegionMapFiles().put(name, new File(imageFolder, name));
        }
    }
    
    public Map<String, BufferedImage> getRegionMapImages(){
        return townMapFileNameImages;
    }
    
    public void putRegionMapFile(String name, File file, BufferedImage image){
        getRegionMapFiles().put(name, file);
        getRegionMapImages().put(name, image);
    }
    
    public void removeRegionMapFile(String name){
        getRegionMapFiles().remove(name);
        getRegionMapImages().remove(name);
    }
    
    public void setRegionMapFileName(String oldName, String newName){
        File file = getRegionMapFiles().remove(oldName);
        BufferedImage img = getRegionMapImages().remove(oldName);
        putRegionMapFile(newName,file,img);
        for (TownMap map : getRegionMaps()){
            if (Objects.equals(map.getFileName(),oldName))
                map.setFileName(newName);
        }
    }
    
    public List<Species> getSpecies(){
        return species;
    }
    
    public void loadSpecies(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next species
            Species s = new Species();
            index = s.fromData(lines, index, progressBar);
            if (s.getID() != null)
                getSpecies().add(s);
        }
    }
    
    public void loadSpecies(List<String> lines){
        loadSpecies(lines,null);
    }
    
    public Map<AbstractSpecies, Metrics> getMetrics(){
        return metrics;
    }
    
    public void loadMetrics(List<String> lines, JProgressBar progressBar){
        int index = 0;
        while (index < lines.size()){
                // Skip until the next ID in the list
            while(index < lines.size() && !isID(lines.get(index))){
                index++;
                if (progressBar != null)
                    progressBar.setValue(progressBar.getValue()+1);
            }
            
            // Load the next Metrics
            Metrics m = new Metrics();
            index = m.fromData(lines, index, progressBar);
            if (m.getID() != null){
                String id = m.toString();
                for (Species s : getSpecies()){
                    if (s != null && s.matchesID(id)){
                        getMetrics().put(s, m);
                        break;
                    }
                }
            }
        }
    }
    
    public void loadMetrics(List<String> lines){
        loadMetrics(lines,null);
    }
    
    public void finishLoadingData(JProgressBar progressBar){
        if (progressBar != null){
            progressBar.setMaximum(getTypes().size() + getMoves().size() + getItems().size() + getSpecies().size());
            progressBar.setIndeterminate(false);
        }
            // Set strengths and weaknesses for each type
        for (Type type : getTypes()){
            type.finishLoading(this);
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
            // Set the type for each move
        for (Move move : getMoves()){
            move.finishLoading(this);
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
            // Set the move for each type
        for (Item item : getItems()){
            item.finishLoading(this);
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
            // Set the data for each species
        for (Species s : getSpecies()){
            s.finishLoading(this);
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
        if (progressBar != null){
            progressBar.setValue(0);
            progressBar.setIndeterminate(true);
        }
        getRegionMapFiles().clear();
        getRegionMapImages().clear();
    }
    
    public void finishLoadingData(){
        finishLoadingData(null);
    }
}
