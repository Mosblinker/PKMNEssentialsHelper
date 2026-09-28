/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Mosblinker
 */
public abstract class AbstractPaletteChanger implements PaletteChanger{
    
    protected EventListenerList listenerList = new EventListenerList();
    
    private boolean enabled = true;
    
    private JPaletteChangerPanel panel = null;
    
    private String name = null;
    
    private String title = null;
    
    private boolean colorEnabled = false;

    @Override
    public void addChangeListener(ChangeListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(ChangeListener.class, l);
    }
    @Override
    public void removeChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }
    @Override
    public ChangeListener[] getChangeListeners(){
        return listenerList.getListeners(ChangeListener.class);
    }
    /**
     * This notifies all the {@code ActionListener}s that have been added to 
     * this panel if the given {@code ActionEvent} is not null. If the source 
     * of the given {@code ActionEvent} is not this panel, then the {@code 
     * ActionEvent} will be redirected to have this panel as its source.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     * @see #fireActionPerformed(String, long, int) 
     * @see #fireActionPerformed(String) 
     */
    protected void fireChangeEvent(){
        ChangeEvent evt = new ChangeEvent(this);
            // A for loop to go through the action listeners
        for (ChangeListener l : listenerList.getListeners(ChangeListener.class)){
            if (l != null)  // If the listener is not null
                l.stateChanged(evt);
        }
    }
    @Override
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
        updateColorElementsEnabled(enabled && colorEnabled);
    }
    @Override
    public boolean isEnabled(){
        return enabled;
    }
    @Override
    public JPaletteChangerPanel getDialogPanel(){
        return panel;
    }
    @Override
    public void setDialogPanel(JPaletteChangerPanel panel){
        this.panel = panel;
    }
    
    protected abstract String getDefaultCardName();
    
    protected abstract String getDefaultDialogTitle();
    
    @Override
    public String getCardName(){
        if (name == null)
            return getDefaultCardName();
        return name;
    }
    @Override
    public boolean isCardNameSet(){
        return name != null;
    }
    @Override
    public void setCardName(String name){
        this.name = name;
    }
    @Override
    public String getDialogTitle(){
        if (title == null)
            return getDefaultDialogTitle();
        return title;
    }
    @Override
    public boolean isDialogTitleSet(){
        return title!=null;
    }
    @Override
    public void setDialogTitle(String title){
        this.title = title;
    }
    @Override
    public void setColorElementsEnabled(boolean enabled){
        colorEnabled = enabled;
        updateColorElementsEnabled(enabled && isEnabled());
    }
    @Override
    public boolean getColorElementsEnabled(){
        return colorEnabled;
    }
    
    protected void updateColorElementsEnabled(boolean enabled){
        
    }
}
