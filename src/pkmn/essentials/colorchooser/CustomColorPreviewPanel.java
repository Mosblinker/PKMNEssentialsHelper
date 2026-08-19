/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.JColorSelector;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mosblinker
 */
public class CustomColorPreviewPanel extends JPanel implements ChangeListener{
    
    private static final int SQUARE_SIZE = 25;
    
    private static final int SQUARE_GAP = 5;
    
    private static final Font SAMPLE_TEXT_FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
    
    private static final Font HEX_TEXT_FONT = new Font(Font.MONOSPACED,Font.PLAIN, 12);
    
    private static final int DISPLAY_ICON_WIDTH = 25;
    
    private static final int DISPLAY_ICON_HEIGHT = 28;
    
    public CustomColorPreviewPanel(){
        
    }
    
    protected JColorChooser getColorChooser(){
        return (JColorChooser)SwingUtilities.getAncestorOfClass(JColorChooser.class, this);
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() instanceof JColorChooser || evt.getSource() instanceof JColorSelector)
            repaint();
    }
    
    
    
}
