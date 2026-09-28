/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mosblinker
 */
public class InvertPaletteChanger extends AbstractPaletteChanger{

    @Override
    protected String getDefaultCardName() {
        return "paletteInverter";
    }

    @Override
    protected String getDefaultDialogTitle() {
        return "Invert Colors";
    }

    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer index : list){
            Color color = palette.get(index);
            
            changes.put(index, new Color(
                    (~color.getRed())&0xFF,
                    (~color.getGreen())&0xFF,
                    (~color.getBlue())&0xFF,
                    color.getAlpha()));
        }
    }

    @Override
    public String getAcceptButtonTooltipText() {
        return "Invert colors";
    }

    @Override
    public String getLabelText() {
        return "Select colors to invert:";
    }
}
