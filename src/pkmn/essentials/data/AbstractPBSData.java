/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractPBSData {
    /**
     * The internal ID for this value. This is how the scripts will refer to the 
     * value. Each value much have a different ID. This is surrounded by square 
     * brackets in the file.
     */
    private String id;
    /**
     * The name of the data, as seen often by the player.
     */
    private String name = "Unnamed";
    /**
     * An optional version number. This is not always used.
     */
    private int version = 0;
    
    protected AbstractPBSData(String id){
        this.id = Objects.requireNonNull(id);
    };
    /**
     * 
     * @param data 
     */
    protected AbstractPBSData(AbstractPBSData data){
        this.id = data.id;
        this.name = data.name;
        this.version = data.version;
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
    
    @Override
    public boolean equals(Object o){
        if (o instanceof AbstractPBSData){
            AbstractPBSData data = (AbstractPBSData) o;
            return data.getID().equals(getID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    public boolean matchesID(String str){
        return getID().equals(str);
    }
}
