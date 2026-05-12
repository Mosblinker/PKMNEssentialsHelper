/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
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
    
    private List<Type> types = new ArrayList<>();
    
    private List<Move> moves = new ArrayList<>();
    
    private List<Ability> abilities = new ArrayList<>();
    
    private List<Item> items = new ArrayList<>();
    
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
            getTypes().add(type);
        }
            // Set strengths and weaknesses for each type
        for (Type type : getTypes()){
            type.finishLoad(getTypes());
        }
    }
    
    public void loadTypes(List<String> lines){
        loadTypes(lines,null);
    }
    
    public void setTypeIcons(List<? extends ImageIcon> icons){
        for (Type type : getTypes()){
            if (type.getIconPosition() >= 0 && type.getIconPosition() < icons.size())
                type.setIcon(icons.get(type.getIconPosition()));
            else
                type.setIcon(null);
        }
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
            getMoves().add(move);
        }
            // Set the type for each move
        for (Move move : getMoves()){
            move.finishLoad(getTypes());
        }
    }
    
    public void loadMoves(List<String> lines){
        loadMoves(lines,null);
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
            getItems().add(item);
        }
            // Set the move for each type
        for (Item item : getItems()){
            item.finishLoad(getMoves());
        }
    }
    
    public void loadItems(List<String> lines){
        loadItems(lines,null);
    }
}
