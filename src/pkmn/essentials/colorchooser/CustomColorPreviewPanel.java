/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.JColorSelector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        g = g.create();
            // An image to render to if the given graphics context is not a 
        BufferedImage img = null;       // Graphics2D object
        Graphics2D g2D;                 // This will get the Graphics2D object to render to.
        if (g instanceof Graphics2D)    // If the graphics context is a Graphics2D object
            g2D = (Graphics2D) g;
        else if (g != null){            // If the graphics context is not null
            Dimension dim = getPreferredSize();
                // Create the image to render to
            img = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
                // Create a graphics context for the image
            g2D = img.createGraphics();
                // Configure the image graphics context to match the given 
                // graphics context
            g2D.setFont(g.getFont());
            g2D.setColor(g.getColor());
        } else          // If the graphics conext is somehow null
            return;
        
        
        if (img != null){               // If this rendered to an image
            g2D.dispose();
            g.drawImage(img, 0, 0, this);
        }
        g.dispose();
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() instanceof JColorChooser || evt.getSource() instanceof JColorSelector)
            repaint();
    }
    
    
    
}
