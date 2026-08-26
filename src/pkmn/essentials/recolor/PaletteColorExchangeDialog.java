/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import components.AbstractConfirmDialogPanel;
import components.ArrayListModel;
import components.JColorSelector;
import components.JListSelector;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pkmn.essentials.ColorCellRenderer;
import pkmn.essentials.PkmnHelperUtilities;
import pkmn.essentials.sprite.PalettePkmnSpriteIcon;
import pkmn.essentials.sprite.PkmnSpriteIcon;

/**
 *
 * @author Mosblinker
 */
public class PaletteColorExchangeDialog extends AbstractConfirmDialogPanel{
    
    private void initialize(){
        Handler handler = new Handler();
        
        fromColorButton = new JButton();
        fromColorButton.setBackground(fromColor);
        fromColorButton.setActionCommand("FromColor");
        fromColorButton.setRolloverEnabled(false);
        fromColorButton.addActionListener(handler);
        fromColorButton.setEnabled(false);
        JLabel fromColorLabel = new JLabel("From Color:");
        fromColorLabel.setLabelFor(fromColorButton);
        toColorButton = new JButton();
        toColorButton.setBackground(toColor);
        toColorButton.setActionCommand("ToColor");
        toColorButton.setRolloverEnabled(false);
        toColorButton.addActionListener(handler);
        toColorButton.setEnabled(false);
        JLabel toColorLabel = new JLabel("To Color:");
        toColorLabel.setLabelFor(toColorButton);
        
        applyToModel = new ArrayListModel<>();
        applyToModel.addListDataListener(handler);
        applyToList = new JList<>(applyToModel);
        applyToList.setCellRenderer(new ColorIndexCellRenderer());
        applyToList.setFixedCellHeight(20);
        applyToList.setFixedCellWidth(20);
        applyToList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        applyToList.setVisibleRowCount(-1);
        applyToList.addListSelectionListener(handler);
        applyToScrollPane = new JScrollPane(applyToList);
        applyAddButton = new JButton("Add");
        applyAddButton.setActionCommand("ApplyAdd");
        applyAddButton.setEnabled(false);
        applyAddButton.setMaximumSize(new java.awt.Dimension(32767, applyAddButton.getMaximumSize().height));
        applyAddButton.addActionListener(handler);
        applyRemoveButton = new JButton("Remove");
        applyRemoveButton.setActionCommand("ApplyRemove");
        applyRemoveButton.setEnabled(false);
        applyRemoveButton.addActionListener(handler);
        JPanel applyButtonPanel = new JPanel();
        applyButtonPanel.setLayout(new javax.swing.BoxLayout(applyButtonPanel, javax.swing.BoxLayout.Y_AXIS));
        applyButtonPanel.add(applyAddButton);
        applyButtonPanel.add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(32767, 7)));
        applyButtonPanel.add(applyRemoveButton);
        JPanel applyPanel = new JPanel(new java.awt.BorderLayout(7,6));
        applyPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Apply To "), BorderFactory.createEmptyBorder(7, 6, 7, 6)));
        applyPanel.add(applyToScrollPane,java.awt.BorderLayout.CENTER);
        applyPanel.add(applyButtonPanel,java.awt.BorderLayout.LINE_END);
        
        previewToggle = new JCheckBox("Preview");
        previewToggle.setActionCommand("PreviewToggle");
        previewToggle.setSelected(true);
        previewToggle.addActionListener(handler);
        
        resetButton = new JButton("Reset");
        resetButton.setActionCommand("Reset");
        resetButton.addActionListener(handler);
        
        JPanel bottomButtonPanel = new JPanel(new java.awt.GridLayout(1, 0, 6, 0));
        bottomButtonPanel.add(resetButton);
        bottomButtonPanel.add(acceptButton);
        bottomButtonPanel.add(cancelButton);
        
        JPanel bottomPanel = new JPanel(new java.awt.BorderLayout(7,6));
        bottomPanel.add(previewToggle,java.awt.BorderLayout.LINE_START);
        bottomPanel.add(bottomButtonPanel,java.awt.BorderLayout.LINE_END);
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        add(fromColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.ipady = 15;
        add(fromColorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        add(toColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.ipady = 15;
        add(toColorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.gridwidth = 2;
        constraints.weightx = 0.9;
        constraints.weighty = 0.9;
        add(applyPanel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 7, 6);
        constraints.gridwidth = 2;
        add(bottomPanel, constraints);
        
        applyAddSelector = new JListSelector<>(new DefaultListModel<>());
        applyAddSelector.setCellRenderer(new ColorIndexCellRenderer());
        applyAddSelector.setFixedCellHeight(20);
        applyAddSelector.setFixedCellWidth(20);
        applyAddSelector.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        applyAddSelector.setVisibleRowCount(-1);
        
        setPreferredSize(new java.awt.Dimension(320, 320));
    }
    
    public PaletteColorExchangeDialog(){
        super(new java.awt.GridBagLayout());
        initialize();
    }
    
    public void setColorSelector(JColorSelector colorSelector){
        if (colorSelector == this.colorSelector)
            return;
        JColorSelector old = this.colorSelector;
        this.colorSelector = colorSelector;
        firePropertyChange("colorSelector",old,colorSelector);
        updateColorButtonsEnabled();
    }
    
    public JColorSelector getColorSelector(){
        return colorSelector;
    }
    
    public List<PalettePkmnSpriteIcon> getPreviewSprites(){
        return previewIcons;
    }
    
    public List<PkmnSpriteIcon> getSprites(){
        return icons;
    }
    
    public List<PalettePkmnSpriteIcon> setSprites(Collection<? extends PkmnSpriteIcon> sprites){
        icons.clear();
        icons.addAll(sprites);
        previewIcons.clear();
        for (PkmnSpriteIcon sprite : sprites){
            previewIcons.add(new PalettePkmnSpriteIcon(sprite));
        }
        return previewIcons;
    }
    
    public List<Color> getPalette(){
        return palette;
    }
    
    public List<Color> getAlteredPalette(){
        return changedPalette;
    }
    
    public Map<Integer, Color> getPaletteChanges(){
        return paletteChanges;
    }
    
    public void setPalette(Collection<? extends Color> palette){
        this.palette.clear();
        this.palette.addAll(palette);
        paletteChanges.clear();
        applyToModel.clear();
        for (int i = 0; i < palette.size(); i++){
            applyToModel.add(i);
        }
        prevPalette = new ArrayList<>(palette);
        updatePaletteChanges(applyToModel);
    }
    
    public Color getFromColor(){
        return fromColor;
    }
    
    public void setFromColor(Color color){
        if (Objects.equals(fromColor, color))
            return;
        Color old = fromColor;
        fromColor = color;
        fromColorButton.setBackground(fromColor);
        firePropertyChange("fromColor",old,color);
        updatePaletteChanges(applyToModel);
    }
    
    public Color getToColor(){
        return toColor;
    }
    
    public void setToColor(Color color){
        if (Objects.equals(toColor, color))
            return;
        Color old = toColor;
        toColor = color;
        toColorButton.setBackground(toColor);
        firePropertyChange("toColor",old,color);
        updatePaletteChanges(applyToModel);
    }
    
    public boolean isPreviewShown(){
        return previewToggle.isSelected();
    }
    
    public void setPreviewShown(boolean value){
        if (isPreviewShown() == value)
            return;
        previewToggle.setSelected(value);
        firePropertyChange("previewShown",!value,value);
    }
    
    public int showDialog(Component parent, List<Color> palette, Color fromColor){
            // If the dialog is not showing
        if (getDialog() == null){
            paletteChanges.clear();
            if (palette != null)
                setPalette(palette);
            setFromColor((fromColor != null)?fromColor:Color.WHITE);
            setToColor(Color.BLACK);
        }
        return super.showDialog(parent);
    }
    
    public int showDialog(Component parent, List<Color> palette){
        return showDialog(parent,palette,null);
    }
    
    @Override
    public int showDialog(Component parent){
        return showDialog(parent,null);
    }

    @Override
    protected String getDefaultAcceptButtonToolTipText() {
        return "Apply color exchange";
    }

    @Override
    protected String getDefaultCancelButtonToolTipText() {
        return "Close the window";
    }
    
    @Override
    protected String getDefaultAcceptButtonText(){
        return "Apply";
    }
    
    public void addChangeListener(ChangeListener l){
        if (l != null)
            listenerList.add(ChangeListener.class, l);
    }
    
    public void removeChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }
    
    public ChangeListener[] getChangeListeners(){
        return listenerList.getListeners(ChangeListener.class);
    }
    
    protected void fireChangeEvent(){
        ChangeEvent evt = new ChangeEvent(this);
        for (ChangeListener l : getChangeListeners())
            l.stateChanged(evt);
    }
    
    @Override
    public void accept(){
        super.accept();
   }
    
    public void reset(){
        applyToModel.clear();
        setFromColor(Color.WHITE);
        setToColor(Color.BLACK);
        for (int i = 0; i < palette.size(); i++){
            applyToModel.add(i);
        }
        paletteChanges.clear();
        updatePaletteChanges(applyToModel);
    }
    
    protected Color showColorSelector(Color color){
        if (colorSelector == null)
            return color;
        PkmnHelperUtilities.resetColorPreviewColor(colorSelector);
        if (colorSelector.showDialog(this, color) == JColorSelector.ACCEPT_OPTION){
            return colorSelector.getSelectedColor();
        }
        return color;
    }
    
    protected void addPaletteColors(){
        applyAddSelector.getModelList().clear();
        for (int i = 0; i < palette.size(); i++){
            applyAddSelector.getModelList().add(i);
        }
        applyAddSelector.getModelList().removeAll(applyToModel);
        if (applyAddSelector.showDialog(this) == JListSelector.ACCEPT_OPTION){
            List<Integer> selection = applyAddSelector.getSelectedValuesList();
            applyToModel.addAll(selection);
            updatePaletteChanges(selection);
            applyToModel.sort(null);
        }
        updateAddButtonEnabled();
    }
    
    protected void removePaletteColors(){
        int[] selection = applyToList.getSelectedIndices();
        for (int i = selection.length-1; i >= 0; i--){
            Integer index = applyToModel.get(selection[i]);
            paletteChanges.remove(index);
            applyToModel.remove(index);
        }
        updatePreviewIcons();
        updateRemoveButtonEnabled();
    }
    
    protected void updatePaletteChanges(List<Integer> list){
        int[][] inputColors = {
            {fromColor.getRed(),fromColor.getGreen(),fromColor.getBlue()},
            {toColor.getRed(),toColor.getGreen(),toColor.getBlue()}
        };
        double[] colorDiff = new double[3];
        double[][] cutoff = new double[2][3];
        for (int i = 0; i < 3; i++){
            double fromC = inputColors[0][i]/255.0;
            colorDiff[i] = (inputColors[1][i]/255.0) - fromC;
            cutoff[0][i] = Math.min(Math.max(fromC - 1.0,0.0),1.0);
            cutoff[1][i] = Math.min(Math.max(fromC + 1.0,0.0),1.0);
        }
        for (Integer index : list){
            Color color = palette.get(index);
            double[] values = {color.getRed(),color.getGreen(),color.getBlue()};
            for (int i = 0; i < 3; i++){
                values[i] = Math.min(Math.max((values[i]/255.0) + colorDiff[i],0),1);
            }
            Color newColor = new Color((float)values[0],(float)values[1],(float)values[2],(float)(color.getAlpha()/255.0));
            paletteChanges.put(index, newColor);
        }
        for (int i = 0; i < palette.size(); i++){
            if (palette.get(i).equals(paletteChanges.get(i)))
                paletteChanges.remove(i);
        }
        updatePreviewIcons();
    }
    
    public void updatePreviewIcons(){
        Map<Integer,Color> changes = new HashMap<>();
        for (int i = 0; i < changedPalette.size(); i++){
            changes.put(i, changedPalette.get(i));
        }
        for (int i = 0; i < prevPalette.size(); i++){
            changes.remove(i, prevPalette.get(i));
        }
        for (PalettePkmnSpriteIcon icon : previewIcons){
            icon.changeColors(changes);
        }
        prevPalette = new ArrayList<>(changedPalette);
        fireChangeEvent();
    }
    
    private void updateAddButtonEnabled(){
        applyAddButton.setEnabled(isEnabled() && !palette.isEmpty() && applyToModel.size() < palette.size());
    }
    
    private void updateRemoveButtonEnabled(){
        applyRemoveButton.setEnabled(isEnabled() && !applyToModel.isEmpty() && 
                !applyToList.isSelectionEmpty());
    }
    
    private void updateColorButtonsEnabled(){
        fromColorButton.setEnabled(isEnabled() && colorSelector != null);
        toColorButton.setEnabled(fromColorButton.isEnabled());
    }
    
    @Override
    protected boolean isAcceptEnabled(){
        return super.isAcceptEnabled() && applyToModel != null && !applyToModel.isEmpty();
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            updateAddButtonEnabled();
            updateRemoveButtonEnabled();
            updateColorButtonsEnabled();
            resetButton.setEnabled(enabled);
            
            
        } catch (NullPointerException ex) {}
    }
    
    private JButton fromColorButton;
    
    private JButton toColorButton;
    
    private Color fromColor = Color.WHITE;
    
    private Color toColor = Color.BLACK;
    
    private JScrollPane applyToScrollPane;
    
    private JList<Integer> applyToList;
    
    private ArrayListModel<Integer> applyToModel;
    
    private JButton applyAddButton;
    
    private JButton applyRemoveButton;
    
    private JCheckBox previewToggle;
    
    private JButton resetButton;
    
    private JColorSelector colorSelector = null;
    
    private JListSelector<Integer> applyAddSelector;
    
    private List<Color> palette = new ArrayList<>();
    
    private Map<Integer,Color> paletteChanges = new HashMap<>();
    
    private List<Color> changedPalette = new ChangedPaletteList();
    
    private List<Color> prevPalette;
    
    private List<PkmnSpriteIcon> icons = new ArrayList<>();
    
    private List<PalettePkmnSpriteIcon> previewIcons = new ArrayList<>();
    
    private class Handler implements ActionListener, ListSelectionListener, 
            ListDataListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case("FromColor"):
                    setFromColor(showColorSelector(fromColor));
                    break;
                case("ToColor"):
                    setToColor(showColorSelector(toColor));
                    break;
                case("ApplyAdd"):
                    addPaletteColors();
                    break;
                case("ApplyRemove"):
                    removePaletteColors();
                    break;
                case("PreviewToggle"):
                    firePropertyChange("previewShown",!previewToggle.isSelected(),previewToggle.isSelected());
                    break;
                case("Reset"):
                    reset();
            }
        }
        @Override
        public void valueChanged(ListSelectionEvent evt) {
            updateRemoveButtonEnabled();
        }
        @Override
        public void intervalAdded(ListDataEvent evt) {
            if (evt.getSource() == applyToModel){
                updateRemoveButtonEnabled();
                updateAddButtonEnabled();
                updateAcceptEnabled();
            }
        }
        @Override
        public void intervalRemoved(ListDataEvent evt) {
            if (evt.getSource() == applyToModel){
                updateRemoveButtonEnabled();
                updateAddButtonEnabled();
                updateAcceptEnabled();
            }
        }
        @Override
        public void contentsChanged(ListDataEvent evt) {
            if (evt.getSource() == applyToModel){
                updateRemoveButtonEnabled();
                updateAddButtonEnabled();
                updateAcceptEnabled();
            }
        }
    }
    
    private class ColorIndexCellRenderer extends ColorCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list,Object value,
                int index,boolean isSelected,boolean cellHasFocus){
            
            if (value instanceof Integer){
                int colorIndex = (Integer) value;
                if (colorIndex < palette.size())
                    value = palette.get(colorIndex);
            }
            
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
    
    private class ChangedPaletteList extends AbstractList<Color>{
        @Override
        public Color get(int index) {
            Objects.checkIndex(index, size());
            return paletteChanges.getOrDefault(index, palette.get(index));
        }
        @Override
        public int size() {
            return palette.size();
        }
    }
    
}
