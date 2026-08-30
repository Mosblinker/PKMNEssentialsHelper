/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.rxdata;

import java.awt.Color;

/**
 *
 * @author Mosblinker
 */
public class RPGColor {
    
    private double r;
    
    private double g;
    
    private double b;
    
    private double a;
    
    public RPGColor(double r, double g, double b, double a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public RPGColor(double r, double g, double b){
        this(r,g,b,255.0);
    }
    
    public RPGColor(Color color){
        this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }
    
    public RPGColor(RPGColor color){
        this(color.r,color.g,color.b,color.a);
    }
    
    public double getRed(){
        return r;
    }
    
    public double getGreen(){
        return g;
    }
    
    public double getBlue(){
        return b;
    }
    
    public double getAlpha(){
        return a;
    }
    
    private int shrinkColorRange(double value){
        return Math.max(Math.min((int)Math.floor(value), 0xFF), 0);
    }
    
    public Color toColor(){
        return new Color(shrinkColorRange(r),shrinkColorRange(g),
                shrinkColorRange(b),shrinkColorRange(a));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.r) ^ (Double.doubleToLongBits(this.r) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.g) ^ (Double.doubleToLongBits(this.g) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.b) ^ (Double.doubleToLongBits(this.b) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.a) ^ (Double.doubleToLongBits(this.a) >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (!(obj instanceof RPGColor))
            return false;
        RPGColor c = (RPGColor) obj;
        return a == c.a && g == c.g && b == c.b && r == c.r;
    }
}
