/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Mosblinker
 */
public class PercentRenderer extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList<?> list,Object value,
            int index,boolean isSelected,boolean cellHasFocus){
        if (value instanceof Number){
            double temp = ((Number)value).doubleValue();
            value = temp*100.0 + " %";
        }
        return super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
    }
    
}
