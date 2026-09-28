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
public class RemoveAlphaChannelPaletteChanger extends AbstractPaletteChanger{

    @Override
    public String getLabelText() {
        return "Remove alpha channel from:";
    }

    @Override
    protected String getDefaultCardName() {
        return "alphaChannelRemover";
    }

    @Override
    protected String getDefaultDialogTitle() {
        return "Remove Alpha Channel";
    }

    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer index : list){
            Color color = palette.get(index);
            
            changes.put(index, new Color(color.getRed(),color.getGreen(),color.getBlue()));
        }
    }

    @Override
    public String getAcceptButtonTooltipText() {
        return "Remove alpha channel";
    }
    
}
