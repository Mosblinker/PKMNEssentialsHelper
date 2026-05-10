/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;
import pkmn.essentials.data.pkmn.*;

/**
 *
 * @author Mosblinker
 */
public class PBSData {
    
    public static final String PBS_FOLDER = "PBS";
    
    public static final String PBS_FILE_EXTENSION = "txt";
    
    public static boolean isID(String str){
        return str.startsWith("[") && str.endsWith("]");
    }
    
    private List<Type> types = new ArrayList<>();
    
    public PBSData(){
        
    }
    
    public PBSData(PBSData data){
        for (Type type : data.types)
            this.types.add(new Type(type));
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
            types.add(type);
        }
            // Set strengths and weaknesses for each type
        for (Type type : types){
            type.loadTypeStrengthsAndWeaknesses(types);
        }
    }
    
    public void loadTypes(List<String> lines){
        loadTypes(lines,null);
    }
}
