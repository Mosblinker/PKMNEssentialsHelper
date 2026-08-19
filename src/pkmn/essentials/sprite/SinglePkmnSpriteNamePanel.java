/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import javax.swing.*;
import pkmn.essentials.listeners.CtrlMouseWheelListener;

/**
 *
 * @author Mosblinker
 */
public class SinglePkmnSpriteNamePanel extends JPanel{
    /**
     * 
     */
    public static final String BROWSE_ACTION_COMMAND = "Browse";
    
    public static final String CLEAR_ACTION_COMMAND = "Clear";
    
    public static final String ICON_PROPERTY_CHANGED = "IconPropertyChanged";
    
    public static final String SCALE_PROPERTY_CHANGED = "ScalePropertyChanged";
    /**
     * This identifies that the accessory component to the side of the list has 
     * changed.
     */
    public static final String SIDE_ACCESSORY_PROPERTY_CHANGED = 
            "SideAccessoryPropertyChanged";
    
    protected void addComponent(Component c,int y){
           // This is the constraints object to use to place the component
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = y;
        if (!(c instanceof JPanel))
            constraints.fill = GridBagConstraints.HORIZONTAL;
            // If the component is to be placed at the top
        if (y < 3){
            if (y == 1){
                constraints.weighty = 0.5;
                constraints.weightx = 0.5;
            }
            constraints.fill = GridBagConstraints.BOTH;
            constraints.anchor = GridBagConstraints.PAGE_START;
        } else {
            constraints.insets = new Insets(7, 0, 0, 0);
            constraints.anchor = GridBagConstraints.PAGE_END;
        }
        this.add(c,constraints);
    }
    
    private void initialize(){
        Handler handler = new Handler();
        addComponent(new Box.Filler(
                new Dimension(0, 0), 
                new Dimension(0, 0), 
                new Dimension(0, 32767)),0);
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.addMouseListener(handler);
        iconLabel.addMouseWheelListener(handler);
        imageScrollPane = new JScrollPane(iconLabel);
        imageScrollPane.setMaximumSize(new java.awt.Dimension(32767,32767));
        addComponent(imageScrollPane,1);
        addComponent(new Box.Filler(
                new Dimension(0, 0), 
                new Dimension(0, 0), 
                new Dimension(0, 32767)),2);
        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        addComponent(nameLabel,3);
        JPanel panel = new JPanel(new java.awt.GridLayout(1,0,6,0));
        browseButton = new JButton("Browse");
        browseButton.setActionCommand(BROWSE_ACTION_COMMAND);
        browseButton.addActionListener(handler);
        panel.add(browseButton);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_ACTION_COMMAND);
        clearButton.addActionListener(handler);
        clearButton.setEnabled(false);
        panel.add(clearButton);
        addComponent(panel,4);
        
        sideAccessoryPanel = new JPanel(new java.awt.BorderLayout());
           // This is the constraints object to use to place the component
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridheight = 5;
        constraints.insets = new Insets(0, 6, 0, 0);
        constraints.anchor = GridBagConstraints.EAST;
        add(sideAccessoryPanel,constraints);
        sideAccessoryPanel.setVisible(false);
    }
    
    public SinglePkmnSpriteNamePanel(){
        super(new java.awt.GridBagLayout());
        initialize();
    }
    
    public void setIcon(PkmnSpriteIcon icon){
        if (icon != this.icon){
            PkmnSpriteIcon old = this.icon;
            this.icon = icon;
            if (icon != null)
                icon.setTargetScale(scale);
            iconLabel.setIcon(icon);
            clearButton.setEnabled(isEnabled() && icon != null);
            refreshName();
            firePropertyChange(ICON_PROPERTY_CHANGED,old,icon);
        }
        
    }
    
    public PkmnSpriteIcon getIcon(){
        return icon;
    }
    
    public void setScale(double scale){
        if (this.scale == scale)
            return;
        double old = this.scale;
        this.scale = scale;
        if (icon != null)
            icon.setTargetScale(scale);
        firePropertyChange(SCALE_PROPERTY_CHANGED,old,scale);
        imageScrollPane.revalidate();
        revalidate();
        repaint();
    }
    
    public double getScale(){
        return scale;
    }
    
    public void refreshName(){
        nameLabel.setText((icon!=null)?icon.getDescription():null);
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            browseButton.setEnabled(enabled);
            clearButton.setEnabled(enabled && icon != null);
        }catch(NullPointerException ex) {}
    }
    /**
     * This adds the given {@code ActionListener} to this panel.
     * @param l The listener to add.
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void addActionListener(ActionListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(ActionListener.class, l);
    }
    /**
     * This removes the given {@code ActionListener} from this panel.
     * @param l The listener to remove.
     * @see #addActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void removeActionListener(ActionListener l){
        listenerList.remove(ActionListener.class, l);
    }
    /**
     * This returns an array containing all the {@code ActionListener}s that 
     * have been added to this panel.
     * @return An array containing the {@code ActionListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     */
    public ActionListener[] getActionListeners(){
        return listenerList.getListeners(ActionListener.class);
    }
    /**
     * This notifies all the {@code ActionListener}s that have been added to 
     * this panel if the given {@code ActionEvent} is not null. If the source 
     * of the given {@code ActionEvent} is not this panel, then the {@code 
     * ActionEvent} will be redirected to have this panel as its source.
     * @param evt The {@code ActionEvent} to fire.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     * @see #fireActionPerformed(String, long, int) 
     * @see #fireActionPerformed(String) 
     */
    protected void fireActionPerformed(ActionEvent evt){
        if (evt == null)    // If the event is null
            return;
            // If the event's source is not this component
        else if (evt.getSource() != this){
            evt = new ActionEvent(this, evt.getID(), evt.getActionCommand(),
                    evt.getWhen(),evt.getModifiers());
        }
            // A for loop to go through the action listeners
        for (ActionListener l : listenerList.getListeners(ActionListener.class)){
            if (l != null)  // If the listener is not null
                l.actionPerformed(evt);
        }
    }
    /**
     * This adds the given {@code ActionListener} to this panel.
     * @param l The listener to add.
     * @see #removeActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void addCtrlMouseWheelListener(CtrlMouseWheelListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(CtrlMouseWheelListener.class, l);
    }
    /**
     * This removes the given {@code ActionListener} from this panel.
     * @param l The listener to remove.
     * @see #addActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void removeCtrlMouseWheelListener(CtrlMouseWheelListener l){
        listenerList.remove(CtrlMouseWheelListener.class, l);
    }
    /**
     * This returns an array containing all the {@code ActionListener}s that 
     * have been added to this panel.
     * @return An array containing the {@code ActionListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     */
    public CtrlMouseWheelListener[] getCtrlMouseWheelListeners(){
        return listenerList.getListeners(CtrlMouseWheelListener.class);
    }
    
    public JComponent getSideAccessory(){
        return sideAccessory;
    }
    
    public void setSideAccessory(JComponent accessory){
            // If the side accessory would not be changed
        if (accessory == sideAccessory)
            return;
        JComponent old = sideAccessory;     // Get the old side accessory
        sideAccessory = accessory;
        firePropertyChange(SIDE_ACCESSORY_PROPERTY_CHANGED,old,accessory);
        if (old != null)       // If the old accessory is not null
            sideAccessoryPanel.remove(old);
        if (accessory != null)       // If the new accessory is not null
            sideAccessoryPanel.add(accessory,BorderLayout.CENTER);
        sideAccessoryPanel.revalidate();
        sideAccessoryPanel.repaint();
        sideAccessoryPanel.setVisible(accessory != null);
    }
    
    public Cursor getImageCursor(){
        return iconLabel.getCursor();
    }
    
    public void setImageCursor(Cursor cursor){
        iconLabel.setCursor(cursor);
    }
    
    private int imageScrollInc = 3;
    
    public int getImageScrollIncrement(){
        return imageScrollInc;
    }
    
    public void setImageScrollIncrement(int inc){
        if (imageScrollInc == inc)
            return;
        int old  = imageScrollInc;
        imageScrollInc = inc;
        firePropertyChange("imageScrollIncrement",old,inc);
    }
    
    private PkmnSpriteIcon icon = null;
    
    private JLabel iconLabel;
    
    private JLabel nameLabel;
    
    private JButton browseButton;
    
    private JButton clearButton;
    
    private double scale = 1.0;
    
    private JScrollPane imageScrollPane;
    /**
     * This is the panel used to contain the side accessory within the side 
     * panel.
     */
    protected JPanel sideAccessoryPanel;
    /**
     * This is the accessory that appears to the side of the list.
     */
    private JComponent sideAccessory = null;
    /**
     * 
     */
    private class Handler extends MouseAdapter implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case(BROWSE_ACTION_COMMAND):
                    fireActionPerformed(evt);
                    break;
                case(CLEAR_ACTION_COMMAND):
                    setIcon(null);
                    fireActionPerformed(evt);
            }
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent evt){
            if (evt.isControlDown()){
                    // A for loop to go through the mouse wheel listeners
                for (CtrlMouseWheelListener l : listenerList.getListeners(CtrlMouseWheelListener.class)){
                    if (l != null)  // If the listener is not null
                        l.mouseWheelMoved(evt);
                }
            } else {
                Rectangle rect = imageScrollPane.getViewport().getViewRect();
                int scrollUnits = evt.getUnitsToScroll() * imageScrollInc;
                if (evt.isShiftDown())
                    rect.x += scrollUnits;
                else
                    rect.y += scrollUnits;
                iconLabel.scrollRectToVisible(rect);
            }
        }
    }
}
