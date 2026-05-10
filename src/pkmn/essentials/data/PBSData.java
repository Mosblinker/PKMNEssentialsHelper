/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.util.ArrayList;
import java.util.List;
import pkmn.essentials.data.pkmn.*;

/**
 *
 * @author Mosblinker
 */
public class PBSData {
    
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
    
}
