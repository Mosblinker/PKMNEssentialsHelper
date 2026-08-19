/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mosblinker
 */
public class CustomColorPreviewPanel extends JPanel implements ChangeListener{
    
    private static final int SQUARE_SIZE = 25;
    
    private static final int MIDDLE_SQUARE_SIZE = SQUARE_SIZE - 10;
    
    private static final int SMALL_SQUARE_SIZE = MIDDLE_SQUARE_SIZE - 10;
    
    private static final Font DIALOG_FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
    
    private static final int DISPLAY_ICON_WIDTH = 25;
    
    private static final int DISPLAY_ICON_HEIGHT = 28;
    
    public CustomColorPreviewPanel(){
        
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        
    }
    
    
    
}
