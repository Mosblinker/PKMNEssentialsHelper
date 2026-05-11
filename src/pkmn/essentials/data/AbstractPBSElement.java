/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.swing.JProgressBar;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractPBSElement {
    
    public static final String FLAG_NUMBER_SEPARATOR = "_";
    
    public static final String FLAG_NUMBER_REGEX_REPLACEMENT = FLAG_NUMBER_SEPARATOR+"[0-9]+";
    
    public static final String FLAG_NUMBER_PLACEHOLDER = FLAG_NUMBER_SEPARATOR+"##";
    /**
     * The internal ID for this value. This is how the scripts will refer to the 
     * value. Each value much have a different ID. This is surrounded by square 
     * brackets in the file.
     */
    private String id = null;
    /**
     * The name of the data, as seen often by the player.
     */
    private String name = "Unnamed";
    /**
     * An optional version number. This is not always used.
     */
    private int version = 0;
    
    private List<String> flags = new ArrayList<>();
    
    protected AbstractPBSElement(){
    }
    
    protected AbstractPBSElement(String id){
        this.id = Objects.requireNonNull(id);
    };
    /**
     * 
     * @param data 
     */
    protected AbstractPBSElement(AbstractPBSElement data){
        this.id = data.id;
        this.name = data.name;
        this.version = data.version;
        this.flags.addAll(data.flags);
    }
    /**
     * This returns the internal ID for this data. This is how the scripts will 
     * refer to the data. Each piece of data much have a different ID. This is 
     * surrounded by square brackets in the PBS file.
     * @return 
     */
    public String getID(){
        return id;
    }
    /**
     * 
     * @param id 
     */
    public void setID(String id){
        this.id = Objects.requireNonNull(id);
    }
    /**
     * This returns the name of the data, often as seen by the player.
     * @return 
     */
    public String getName(){
        return name;
    }
    /**
     * 
     * @param name 
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * 
     * @return 
     */
    protected int getVersion(){
        return version;
    }
    /**
     * 
     * @param version 
     */
    protected void setVersion(int version){
        this.version = version;
    }
    
    protected List<String> getFlags(){
        return flags;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof AbstractPBSElement){
            AbstractPBSElement data = (AbstractPBSElement) o;
            return data.getID().equals(getID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getID());
        return hash;
    }
    
    public boolean matchesID(String str){
        return getID().equals(str);
    }
    
    public String toData(){
        String str = "["+getID() + "]";
        return str;
    }
    
    public int fromData(List<String> data, int startIndex, JProgressBar progressBar){
        int index;
        int propertyIndex = 0;
        boolean gotID = false;
        for (index = startIndex; index < data.size(); index++){
            String value = data.get(index).trim();
            propertyIndex = matchProperty(value);
            if (propertyIndex < 0)
                return index;
            if (propertyIndex < getPropertyNames().length)
                value = value.substring(value.indexOf("=",getPropertyNames()[propertyIndex].length())+1).trim();
            else{
                if (!gotID){
                    gotID = true;
                    reset();
                } else
                    return index;
                value = value.substring(1,value.length()-1);
            }
            if (gotID){
                setProperty(propertyIndex,value);
            }
            if (progressBar != null)
                progressBar.setValue(progressBar.getValue()+1);
        }
        return index;
    }
    
    public int fromData(List<String> data, int startIndex){
        return fromData(data,startIndex,null);
    }
    
    protected void reset(){
        id = null;
        name = null;
        version = 0;
        flags.clear();
    }
    
    protected abstract void setProperty(int propertyIndex, String value);
    
    protected int matchProperty(String str){
        for (int i = 0; i < getPropertyNames().length; i++){
            if (str.startsWith(getPropertyNames()[i]))
                return i;
        }
        if (PBSData.isID(str))
            return getPropertyNames().length;
        return -1;
    }
    
    protected abstract String[] getPropertyNames();
    
    @Override
    public String toString(){
        return getID();
    }
    
    public static String getFlagString(Collection<String> flags){
        if (flags.isEmpty())
            return "";
        String str = "";
        for (String temp : flags){
            str += ", " + temp;
        }
        return str.substring(2);
    }
    
    public static List<String> getFlags(String value, List<String> flags){
        if (flags == null)
            flags = new ArrayList<>();
        String[] arr = value.split(",");
        for (String temp : arr){
            flags.add(temp.trim());
        }
        return flags;
    }
    
    public static String toIDString(Collection<? extends AbstractPBSElement> elements){
        if (elements.isEmpty())
            return "";
        String str = "";
        for (AbstractPBSElement element : elements){
            str += ", " + element.getID();
        }
        return str.substring(2);
    }
    
    public static AbstractPBSElement getMatch(String id, Collection<? extends AbstractPBSElement> elements){
        for (AbstractPBSElement element : elements){
            if (element != null && element.matchesID(id))
                return element;
        }
        return null;
    }
}
