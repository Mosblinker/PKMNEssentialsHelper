/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author Mosblinker
 */
public class PkmnSpriteCellRenderer extends DefaultListCellRenderer{
    
    public PkmnSpriteCellRenderer(){
        super.setHorizontalAlignment(SwingConstants.CENTER);
        super.setVerticalAlignment(SwingConstants.CENTER);
        super.setHorizontalTextPosition(SwingConstants.CENTER);
        super.setVerticalTextPosition(SwingConstants.BOTTOM);
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list,Object value,
            int index,boolean isSelected,boolean cellHasFocus){
        Icon icon = null;
        if (value instanceof PkmnSpriteIcon){
            PkmnSpriteIcon temp = (PkmnSpriteIcon) value;
            icon = temp;
            value = temp.getDescription();
            if (temp.isFemale())
                value = "F: "+value;
//            if (temp.getIndex() >= 0)
//                value = temp.getIndex() + ". " + value;
        } else if (value instanceof Icon){
            icon = (Icon) value;
            value = null;
        }
        Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        setIcon(icon);
        return c;
    }
    
}
