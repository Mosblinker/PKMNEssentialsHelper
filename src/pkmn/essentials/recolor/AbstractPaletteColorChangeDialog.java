/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import components.JColorSelector;
import java.awt.Color;
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractPaletteColorChangeDialog extends AbstractPaletteChangeDialog{
    
    public void setColorSelector(JColorSelector colorSelector){
        if (colorSelector == this.colorSelector)
            return;
        JColorSelector old = this.colorSelector;
        this.colorSelector = colorSelector;
        firePropertyChange("colorSelector",old,colorSelector);
        updateColorButtonsEnabled();
    }
    
    public JColorSelector getColorSelector(){
        return colorSelector;
    }
    
    protected Color showColorSelector(Color color){
        if (colorSelector == null)
            return color;
        PkmnHelperUtilities.resetColorPreviewColor(colorSelector);
        if (colorSelector.showDialog(this, color) == JColorSelector.ACCEPT_OPTION){
            return colorSelector.getSelectedColor();
        }
        return color;
    }
    
    protected abstract void updateColorButtonsEnabled(boolean enabled);
    
    protected void updateColorButtonsEnabled(){
        updateColorButtonsEnabled(isEnabled() && colorSelector != null);
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            updateColorButtonsEnabled();
        } catch (NullPointerException ex) {}
    }
    
    protected JColorSelector colorSelector = null;
}
