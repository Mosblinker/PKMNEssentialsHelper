/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mosblinker
 */
public class PaletteInvertChangeDialog extends AbstractPaletteChangeDialog{

    @Override
    protected JPanel constructControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select colors to invert:");
        panel.add(label,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
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
    protected String getDefaultAcceptButtonToolTipText() {
        return "Invert colors";
    }
}
