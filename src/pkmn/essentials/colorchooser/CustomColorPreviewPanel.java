/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.JColorSelector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
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
    
    private static final Font HEX_TEXT_FONT = new Font(Font.MONOSPACED,Font.PLAIN, 14);
    
    private static final String SAMPLE_TEXT = "Sample Text Sample Text";
    
    private static final int SWATCH_WIDTH = 50;
    
    private static final int SWATCH_HEIGHT = 28;
    
    public Color oldColor = null;
    
    public CustomColorPreviewPanel(){
        
    }
    
    protected JColorChooser getColorChooser(){
        return (JColorChooser)SwingUtilities.getAncestorOfClass(JColorChooser.class, this);
    }
    
    private FontMetrics getFontMetricsInternal(Font font){
        JComponent c = getColorChooser();
        if (c == null)
            c = this;
        return c.getFontMetrics(font);
    }
    
    @Override
    public Dimension getPreferredSize(){
        int width = (SQUARE_SIZE+SQUARE_GAP)*3;
        //int height = (SQUARE_SIZE+SQUARE_GAP)*2;
        
        FontMetrics metrics = getFontMetricsInternal(SAMPLE_TEXT_FONT);
        
        width += metrics.stringWidth(SAMPLE_TEXT)+2;
        int height = SQUARE_GAP * 2 + (metrics.getHeight()+2)*3;
        
        width += SQUARE_GAP+SWATCH_WIDTH+SQUARE_GAP;
        
        metrics = getFontMetricsInternal(HEX_TEXT_FONT);
        
        width += 6+metrics.stringWidth("0x00000000");
        
        return new Dimension(width, height);
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
        
        Dimension dim = getPreferredSize();
        int y1 = (dim.height - (SQUARE_SIZE+SQUARE_GAP+SQUARE_SIZE))/2;
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        Color color = (getColorChooser()!=null)?getColorChooser().getColor():getBackground();
        
        if (oldColor == null)
            oldColor = getForeground();
        
        // Draw squares
        
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, y1, SQUARE_SIZE, SQUARE_SIZE);
        g2D.fillRect(SQUARE_SIZE+SQUARE_GAP, y1+SQUARE_SIZE+SQUARE_GAP, SQUARE_SIZE, SQUARE_SIZE);
        g2D.fillRect((SQUARE_SIZE+SQUARE_GAP)*2, y1, SQUARE_SIZE, SQUARE_SIZE);
        
        g2D.setColor(Color.BLACK);
        g2D.fillRect(SQUARE_SIZE+SQUARE_GAP, y1, SQUARE_SIZE, SQUARE_SIZE);
        g2D.fillRect((SQUARE_SIZE+SQUARE_GAP)*2, y1+SQUARE_SIZE+SQUARE_GAP, SQUARE_SIZE, SQUARE_SIZE);
        
        g2D.setColor(color);
        g2D.fillRect(0, y1+SQUARE_SIZE+SQUARE_GAP, SQUARE_SIZE, SQUARE_SIZE);
        for (int y = 0; y < 2; y++){
            for (int x = 0; x < 3; x++){
                g2D.fillRect(SQUARE_GAP+(SQUARE_SIZE+SQUARE_GAP)*x, 
                        y1+SQUARE_GAP+(SQUARE_SIZE+SQUARE_GAP)*y, 
                        SQUARE_SIZE-SQUARE_GAP-SQUARE_GAP, 
                        SQUARE_SIZE-SQUARE_GAP-SQUARE_GAP);
            }
        }
        
        g2D.setColor(Color.WHITE);
        g2D.fillRect(SQUARE_GAP+SQUARE_GAP, y1+SQUARE_GAP+SQUARE_GAP, 
                SQUARE_SIZE-SQUARE_GAP*4, SQUARE_SIZE-SQUARE_GAP*4);
        g2D.fillRect(SQUARE_GAP+SQUARE_GAP+SQUARE_SIZE+SQUARE_GAP, y1+SQUARE_GAP+SQUARE_GAP, 
                SQUARE_SIZE-SQUARE_GAP*4, SQUARE_SIZE-SQUARE_GAP*4);
        
        g2D.setColor(Color.BLACK);
        g2D.fillRect(SQUARE_GAP+SQUARE_GAP+(SQUARE_SIZE+SQUARE_GAP)*2, y1+SQUARE_GAP+SQUARE_GAP, 
                SQUARE_SIZE-SQUARE_GAP*4, SQUARE_SIZE-SQUARE_GAP*4);
        
        // Draw sample text
        FontMetrics metrics = getFontMetricsInternal(SAMPLE_TEXT_FONT);
        int x = (SQUARE_SIZE+SQUARE_GAP)*3;
        
        g2D.setColor(Color.WHITE);
        int w = metrics.stringWidth(SAMPLE_TEXT);
        g2D.fillRect(x, (metrics.getHeight()+SQUARE_GAP+2)*2, w+2, metrics.getHeight()+2);
        
        g2D.setColor(color);
        g2D.drawString(SAMPLE_TEXT, x+1, metrics.getAscent()+1);
        g2D.drawString(SAMPLE_TEXT, x+1, metrics.getAscent()+1+(metrics.getHeight()+SQUARE_GAP+2)*2);
        g2D.fillRect(x, metrics.getHeight()+SQUARE_GAP+2, w+2, metrics.getHeight()+2);
        
        g2D.setColor(Color.BLACK);
        g2D.drawString(SAMPLE_TEXT, x+1, metrics.getAscent()+metrics.getHeight()+SQUARE_GAP+3);
        
        // Drawing sample swatches
        y1 = dim.height/2 - SWATCH_HEIGHT;
        x += w+2+SQUARE_GAP;
        
        g2D.setColor(oldColor);
        g2D.fillRect(x, y1, SWATCH_WIDTH, SWATCH_HEIGHT);
        
        g2D.setColor(color);
        g2D.fillRect(x, y1+SWATCH_HEIGHT, SWATCH_WIDTH, SWATCH_HEIGHT);
        
        // Draw Hex codes
        x += SQUARE_GAP+SWATCH_WIDTH+1;
        metrics = getFontMetricsInternal(HEX_TEXT_FONT);
        y1 += SWATCH_HEIGHT/2+metrics.getDescent();
        g2D.setColor(Color.BLACK);
        g2D.drawString(String.format("0x%08X", oldColor.getRGB()), x, y1);
        g2D.drawString(String.format("0x%08X", color.getRGB()), x, y1+SWATCH_HEIGHT);
        
        if (img != null){               // If this rendered to an image
            g2D.dispose();
            g.drawImage(img, 0, 0, this);
        }
        g.dispose();
    }
    
    public void setOldColor(Color color){
        this.oldColor = color;
        repaint();
    }
    
    public Color getOldColor(){
        return oldColor;
    }
    
    public void resetOldColor(){
        setOldColor(null);
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() instanceof JColorChooser || evt.getSource() instanceof JColorSelector)
            repaint();
    }
    
    
    
}
