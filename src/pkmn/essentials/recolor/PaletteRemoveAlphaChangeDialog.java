/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Mosblinker
 */
public class PaletteRemoveAlphaChangeDialog extends AbstractPaletteChangeDialog{

    @Override
    protected JPanel constructControlPanel() {
        return null;
    }

    @Override
    protected void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer index : list){
            Color color = palette.get(index);
            
            changes.put(index, new Color(color.getRed(),color.getGreen(),color.getBlue()));
        }
    }
    
    @Override
    protected String getDefaultAcceptButtonToolTipText() {
        return "Remove alpha channel";
    }
}
