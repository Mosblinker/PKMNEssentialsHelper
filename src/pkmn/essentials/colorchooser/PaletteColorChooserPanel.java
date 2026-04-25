/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.ListModelList;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pkmn.essentials.ColorCellRenderer;
import pkmn.essentials.RGBInfoPanel;

/**
 *
 * @author Mosblinker
 */
public class PaletteColorChooserPanel extends AbstractColorChooserPanel{
    private RGBInfoPanel rgbInfoPanel;
    private JScrollPane paletteScrollPane;
    private JList<Color> paletteList;
    private ListModelList<Color> paletteModel = new ListModelList<>();
    private boolean updating = false;
    private boolean initialized = false;

    @Override
    public void updateChooser() {
        Color color = getColorFromModel();
        boolean temp = updating;
        updating = true;
        rgbInfoPanel.setColor(color);
        if (paletteModel.contains(color))
            paletteList.setSelectedValue(color, true);
        else
            paletteList.clearSelection();
        updating = temp;
    }

    @Override
    protected void buildChooser() {
        if (initialized)
            return;
        setBorder(javax.swing.BorderFactory.createEmptyBorder(13, 12, 13, 12));
        setLayout(new java.awt.GridBagLayout());
        
        paletteList = new JList<>(paletteModel);
        paletteList.setCellRenderer(new ColorCellRenderer());
        paletteList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        paletteList.setFixedCellHeight(20);
        paletteList.setFixedCellWidth(20);
        paletteList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        paletteList.setVisibleRowCount(-1);
        paletteList.addListSelectionListener(new Handler());
        paletteScrollPane = new JScrollPane(paletteList);
        JPanel palettePanel = new JPanel(new java.awt.BorderLayout());
        palettePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Palette"),
                BorderFactory.createEmptyBorder(7, 6, 7, 6)));
        palettePanel.add(paletteScrollPane,java.awt.BorderLayout.CENTER);
        palettePanel.setMaximumSize(new java.awt.Dimension(32767,32767));
        palettePanel.setPreferredSize(new java.awt.Dimension(360,240));
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 6);
        constraints.gridheight = 2;
        constraints.weightx = 0.9;
        constraints.weighty = 0.9;
        add(palettePanel,constraints);
        
        rgbInfoPanel = new RGBInfoPanel();
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 7, 0);
        add(rgbInfoPanel,constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 0);
        constraints.weighty = 0.5;
        add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767)),constraints);
        initialized = true;
    }
    
    @Override
    public String getDisplayName() {
        return "Palette";
    }
    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }
    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }
    
    public List<Color> getPalette(){
        return paletteModel;
    }
    
    public void setPalette(Collection<? extends Color> palette){
        paletteModel.clear();
        paletteModel.addAll(palette);
    }
    
    public void addListDataListener(ListDataListener l){
        if (l != null)
            listenerList.add(ListDataListener.class, l);
    }
    
    public void removeListDataListener(ListDataListener l){
        listenerList.remove(ListDataListener.class, l);
    }
    
    public ListDataListener[] getListDataListeners(){
        return listenerList.getListeners(ListDataListener.class);
    }
    
    private void fireListDataEvent(ListDataEvent evt){
        int type = evt.getType();
        evt = new ListDataEvent(this,type,evt.getIndex0(),evt.getIndex1());
        for (ListDataListener l : getListDataListeners()){
            if (l != null){
                switch(type){
                    case(ListDataEvent.INTERVAL_ADDED):
                        l.intervalAdded(evt);
                        break;
                    case(ListDataEvent.INTERVAL_REMOVED):
                        l.intervalRemoved(evt);
                        break;
                    case(ListDataEvent.CONTENTS_CHANGED):
                        l.contentsChanged(evt);
                }
            }
        }
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            paletteList.setEnabled(enabled);
        } catch (NullPointerException ex) {}
    }
    
    private class Handler implements ListSelectionListener, ListDataListener{
        @Override
        public void valueChanged(ListSelectionEvent evt) {
            if (updating)
                return;
            boolean temp = updating;
            updating = true;
            Color color = paletteList.getSelectedValue();
            if (color != null){
                rgbInfoPanel.setColor(color);
                getColorSelectionModel().setSelectedColor(color);
            }
            updating = temp;
        }
        @Override
        public void intervalAdded(ListDataEvent e) {
            fireListDataEvent(e);
        }
        @Override
        public void intervalRemoved(ListDataEvent e) {
            fireListDataEvent(e);
        }
        @Override
        public void contentsChanged(ListDataEvent e) {
            fireListDataEvent(e);
        }
    }
}
