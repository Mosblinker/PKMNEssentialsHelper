/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mosblinker
 */
public interface PaletteChanger {
    
    public default String getLabelText(){
        return null;
    }
    
    public String getCardName();
    
    public void setCardName(String name);
    
    public boolean isCardNameSet();
    
    public String getDialogTitle();
    
    public void setDialogTitle(String title);
    
    public boolean isDialogTitleSet();
    
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer,Color> changes);
    
    public default JPanel getControlPanel(){
        return null;
    }
    /**
     * This returns an array containing all the {@code ActionListener}s that 
     * have been added to this panel.
     * @return An array containing the {@code ActionListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     */
    public ChangeListener[] getChangeListeners();
    /**
     * This adds the given {@code ChangeListener} to this panel.
     * @param l The listener to add.
     * @see #removeActionListener(ChangeListener) 
     * @see #getActionListeners() 
     */
    public void addChangeListener(ChangeListener l);
    /**
     * This removes the given {@code ActionListener} from this panel.
     * @param l The listener to remove.
     * @see #addActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void removeChangeListener(ChangeListener l);
    
    public default void prepareToShow(List<Color> palette, Color color){}
    
    public default void prepareToShow(List<Color> palette){
        prepareToShow(palette,null);
    }
    
    public default String getAcceptButtonTooltipText(){
        return "Apply changes";
    }
    
    public default String getCancelButtonToolTipText() {
        return "Close the window";
    }
    
    public default String getAcceptButtonText(){
        return "Apply";
    }
    
    public default void reset() {}
    
    public void setEnabled(boolean enabled);
    
    public boolean isEnabled();
    
    public void setColorElementsEnabled(boolean enabled);
    
    public boolean getColorElementsEnabled();
    
    public default boolean usesColorChooser(){
        return false;
    }
    
    public JPaletteChangerPanel getDialogPanel();
    
    public void setDialogPanel(JPaletteChangerPanel panel);
    
    public default Color showColorSelector(Color color){
        if (getDialogPanel() != null)
            return getDialogPanel().showColorSelector(color);
        return color;
    }
    
    public default void updatePaletteChanges(List<Integer> list){
        if (getDialogPanel() != null)
            getDialogPanel().updatePaletteChanges(list);
    }
    
    public default void updatePaletteChanges(){
        if (getDialogPanel() != null)
            getDialogPanel().updatePaletteChanges();
    }
}
