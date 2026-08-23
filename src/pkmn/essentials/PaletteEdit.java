/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import java.util.Map;
import java.util.Objects;
import pkmn.essentials.sprite.PkmnSpriteIcon;

/**
 *
 * @author Mosblinker
 */
public class PaletteEdit {
    
    private Map<Integer,Color> colors = null;
    
    private Map<Integer,PkmnSpriteIcon> icons = null;
    
    public static PaletteEdit getColorOnlyEdit(Map<Integer,Color> colors){
        return new PaletteEdit(colors,null);
    }
    
    public static PaletteEdit getIconOnlyEdit(Map<Integer,PkmnSpriteIcon> icons){
        return new PaletteEdit(null,icons);
    }
    
    public PaletteEdit(Map<Integer,Color> colors, Map<Integer,PkmnSpriteIcon> icons){
        this.colors = colors;
        this.icons = icons;
    }
    
    public PaletteEdit(PaletteEdit obj){
        colors = obj.colors;
        icons = obj.icons;
    }
    
    public Map<Integer,Color> getColors(){
        return colors;
    }
    
    public Map<Integer,PkmnSpriteIcon> getIcons(){
        return icons;
    }
    
    public void setColors(Map<Integer,Color> colors){
        this.colors = colors;
    }
    
    public void setIcons(Map<Integer,PkmnSpriteIcon> icons){
        this.icons = icons;
    }
    
    @Override
    public String toString(){
        String str = "";
        if (colors != null){
            str = "colors="+colors;
            if (icons != null)
                str += ",";
        }
        if (icons != null)
            str += "icons="+icons;
        return str;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        else if (!(obj instanceof PaletteEdit))
            return false;
        PaletteEdit temp = (PaletteEdit) obj;
        return Objects.equals(colors, temp.colors) && Objects.equals(icons, temp.icons);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.colors);
        hash = 59 * hash + Objects.hashCode(this.icons);
        return hash;
    }
}
