/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.Objects;
import pkmn.essentials.data.AbstractPBSElement;

/**
 *
 * @author Mosblinker
 */
public class Metrics extends AbstractPBSElement implements Comparable<Metrics>{
    
    public static final String PBS_FILE_TEMPLATE = "pokemon_metrics";
    
    public static final String[] PROPERTY_NAMES = {
        "BackSprite",
        "FrontSprite",
        "FrontSpriteAltitude",
        "ShadowX",
        "ShadowSize"
    };
    
    private int backSpriteX = 0;

    private int backSpriteY = 0;

    private boolean backSpriteSet = false;

    private int frontSpriteX = 0;

    private int frontSpriteY = 0;

    private boolean frontSpriteSet = false;

    private Integer frontSpriteAltitude = null;

    private Integer shadowX = null;

    private Integer shadowSize = null;

    public Metrics(){
        super();
    }
    
    public Metrics(String id){
        super(id);
    }
    
    public Metrics(String id, int form){
        this(id);
        setVersion(form);
    }

    public Metrics(Metrics metrics){
        super(metrics);
        this.backSpriteX = metrics.backSpriteX;
        this.backSpriteY = metrics.backSpriteY;
        this.backSpriteSet = metrics.backSpriteSet;
        this.frontSpriteX = metrics.frontSpriteX;
        this.frontSpriteY = metrics.frontSpriteY;
        this.frontSpriteSet = metrics.frontSpriteSet;
        this.frontSpriteAltitude = metrics.frontSpriteAltitude;
        this.shadowX = metrics.shadowX;
        this.shadowSize = metrics.shadowSize;
    }

    public int getForm(){
        return getVersion();
    }
    
    public void setForm(int form){
        setVersion(form);
    }

    public int getBackSpriteX(){
        return backSpriteX;
    }

    public int getBackSpriteY(){
        return backSpriteY;
    }

    public boolean isBackSpriteSet(){
        return backSpriteSet;
    }

    public void setBackSpriteX(int x){
        backSpriteX = x;
        backSpriteSet = true;
    }

    public void setBackSpriteY(int y){
        backSpriteY = y;
        backSpriteSet = true;
    }

    public void setBackSprite(int x, int y){
        setBackSpriteX(x);
        setBackSpriteY(y);
    }

    public int getFrontSpriteX(){
        return frontSpriteX;
    }

    public int getFrontSpriteY(){
        return frontSpriteY;
    }

    public boolean isFrontSpriteSet(){
        return frontSpriteSet;
    }

    public void setFrontSpriteX(int x){
        frontSpriteX = x;
        frontSpriteSet = true;
    }

    public void setFrontSpriteY(int y){
        frontSpriteY = y;
        frontSpriteSet = true;
    }

    public void setFrontSprite(int x, int y){
        setFrontSpriteX(x);
        setFrontSpriteY(y);
    }

    public int getFrontSpriteAltitude(){
        if (frontSpriteAltitude == null)
            return 0;
        return frontSpriteAltitude;
    }

    public void setFrontSpriteAltitude(int value){
        frontSpriteAltitude = value;
    }

    public boolean isFrontSpriteAltitudeSet(){
        return frontSpriteAltitude != null;
    }

    public int getShadowX(){
        if (shadowX == null)
            return 0;
        return shadowX;
    }

    public void setShadowX(int x){
        shadowX = x;
    }

    public boolean isShadowXSet(){
        return shadowX != null;
    }

    public int getShadowSize(){
        if (shadowSize == null)
            return 2;
        return shadowSize;
    }

    public void setShadowSize(int size){
        shadowSize = size;
    }

    public boolean isShadowSizeSet(){
        return shadowSize != null;
    }

    @Override
    protected void setProperty(int propertyIndex, String value) {
        String arr[];
        switch(propertyIndex){
            case(0):
                arr = value.split(",");
                setBackSprite(Integer.parseInt(arr[0].trim()),Integer.parseInt(arr[1].trim()));
                break;
            case(1):
                arr = value.split(",");
                setFrontSprite(Integer.parseInt(arr[0].trim()),Integer.parseInt(arr[1].trim()));
                break;
            case(2):
                setFrontSpriteAltitude(Integer.parseInt(value.trim()));
                break;
            case(3):
                setShadowX(Integer.parseInt(value.trim()));
                break;
            case(4):
                setShadowSize(Integer.parseInt(value.trim()));
                break;
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
        String str = getIDString();
        if (isBackSpriteSet())
            str += System.lineSeparator()+PROPERTY_NAMES[0] + " = " + getBackSpriteX()+","+getBackSpriteY();
        if (isFrontSpriteSet())
            str += System.lineSeparator()+PROPERTY_NAMES[1] + " = " + getFrontSpriteX()+","+getFrontSpriteY();
        if (isFrontSpriteAltitudeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[2] + " = " + getFrontSpriteAltitude();
        if (isShadowXSet())
            str += System.lineSeparator()+PROPERTY_NAMES[3] + " = " + getShadowX();
        if (isShadowSizeSet())
            str += System.lineSeparator()+PROPERTY_NAMES[4] + " = " + getShadowSize();
        return str;
    }
    @Override
    protected void reset(){
        super.reset();
        backSpriteX = backSpriteY = frontSpriteX = frontSpriteY = 0;
        backSpriteSet = false;
        frontSpriteSet = false;
        frontSpriteAltitude = null;
        shadowX = null;
        shadowSize = null;
    }

    @Override
    public int compareTo(Metrics o) {
        if (Objects.equals(getID(), o.getID()))
            return Integer.compare(getForm(), o.getForm());
        return getID().compareTo(o.getID());
    }
}
