/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.BorderLayout;
import javax.swing.JLabel;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractPaletteChangePanelWithLabel extends AbstractPaletteChangePanel{
    
    protected JLabel label;

    @Override
    protected void constructPanel() {
        setLayout(new BorderLayout());
        label = new JLabel(getLabelText());
        add(label,BorderLayout.CENTER);
    }
    
    protected abstract String getLabelText();
}
