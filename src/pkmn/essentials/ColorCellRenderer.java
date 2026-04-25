/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.border.Border;

/**
 *
 * @author Mosblinker
 */
public class ColorCellRenderer extends DefaultListCellRenderer{
    
    public static final Color SELECTION_BORDER_COLOR = new Color(0x62A2E4);
    
    private static final int BORDER_LINE_THICKNESS = 2;
    
    private Border colorBorder;
    
    private Border selColorBorder;
    
    private boolean isTransparentColor = false;
    
    public ColorCellRenderer(){
        colorBorder = BorderFactory.createLineBorder(Color.BLACK,BORDER_LINE_THICKNESS);
        selColorBorder = BorderFactory.createLineBorder(SELECTION_BORDER_COLOR,BORDER_LINE_THICKNESS);
    }
    
    
    
    @Override
    public Component getListCellRendererComponent(JList<?> list,Object value,
            int index,boolean isSelected,boolean cellHasFocus){
        Color color = null;
        if (value instanceof Color){
            color = (Color) value;
            value = null;
        }
        Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        if (color != null){
            isTransparentColor = PkmnHelperUtilities.hasNoColor(color);
            if (!isTransparentColor)
                setBackground(color);
            Border outside = BorderFactory.createLineBorder((isSelected)?list.getSelectionBackground():list.getBackground());
            Border inside = (isSelected) ? selColorBorder : colorBorder;
            setBorder(BorderFactory.createCompoundBorder(outside, inside));
        }
        return c;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (isTransparentColor && g != null){
            BufferedImage img = null;
            Graphics2D g2D;
            if (g instanceof Graphics2D)
                g2D = (Graphics2D) g.create(2,2,getWidth()-4,getHeight()-4);
            else{
                img = new BufferedImage(getWidth()-4,getHeight()-4,BufferedImage.TYPE_INT_ARGB);
                g2D = img.createGraphics();
            }
            g2D.setColor(Color.LIGHT_GRAY);
            int halfX = Math.floorDiv(getWidth()-4, 2);
            int halfY = Math.floorDiv(getHeight()-4, 2);
            g2D.fillRect(halfX, 0, getWidth()-halfX, halfY);
            g2D.fillRect(0, halfY, halfX, getHeight()-halfY);
            g2D.dispose();
            if (img != null)
                g.drawImage(img, 2, 2, null);
        }
    }
    
}
