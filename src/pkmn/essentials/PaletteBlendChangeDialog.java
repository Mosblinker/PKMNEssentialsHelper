/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

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
import pkmn.essentials.sprite.PalettePkmnSpriteIcon;
import pkmn.essentials.sprite.PkmnSpriteIcon;

/**
 *
 * @author Mosblinker
 */
public class PaletteBlendChangeDialog extends AbstractConfirmDialogPanel{
    
    private void initialize(){
        Handler handler = new Handler();
        
        fromColor1Button = new JButton();
        fromColor1Button.setBackground(fromColor1);
        fromColor1Button.setActionCommand("FromColor1");
        fromColor1Button.setRolloverEnabled(false);
        fromColor1Button.addActionListener(handler);
        fromColor1Button.setEnabled(false);
        fromColor2Button = new JButton();
        fromColor2Button.setBackground(fromColor2);
        fromColor2Button.setActionCommand("FromColor2");
        fromColor2Button.setRolloverEnabled(false);
        fromColor2Button.addActionListener(handler);
        fromColor2Button.setEnabled(false);
        JLabel fromColorLabel = new JLabel("From Color:");
        fromColorLabel.setLabelFor(fromColor1Button);
        
        toColor1Button = new JButton();
        toColor1Button.setBackground(toColor1);
        toColor1Button.setActionCommand("ToColor1");
        toColor1Button.setRolloverEnabled(false);
        toColor1Button.addActionListener(handler);
        toColor1Button.setEnabled(false);
        toColor2Button = new JButton();
        toColor2Button.setBackground(toColor2);
        toColor2Button.setActionCommand("ToColor2");
        toColor2Button.setRolloverEnabled(false);
        toColor2Button.addActionListener(handler);
        toColor2Button.setEnabled(false);
        JLabel toColorLabel = new JLabel("To Color:");
        toColorLabel.setLabelFor(toColor1Button);
        
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
        constraints = new java.awt.GridBagConstraints();
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
        constraints.weightx = 0.5;
        add(fromColor1Button, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.ipady = 15;
        constraints.weightx = 0.5;
        add(fromColor2Button, constraints);
        
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
        constraints.weightx = 0.5;
        add(toColor1Button, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.ipady = 15;
        constraints.weightx = 0.5;
        add(toColor2Button, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 6);
        constraints.gridwidth = 3;
        constraints.weighty = 0.9;
        add(applyPanel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 7, 6);
        constraints.gridwidth = 3;
        add(bottomPanel, constraints);
        
        applyAddSelector = new JListSelector<>(new DefaultListModel<>());
        applyAddSelector.setCellRenderer(new ColorIndexCellRenderer());
        applyAddSelector.setFixedCellHeight(20);
        applyAddSelector.setFixedCellWidth(20);
        applyAddSelector.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        applyAddSelector.setVisibleRowCount(-1);
        
        setPreferredSize(new java.awt.Dimension(320, 320));
    }
    
    public PaletteBlendChangeDialog(){
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
    
    public Color getFromColor1(){
        return fromColor1;
    }
    
    public void setFromColor1(Color color){
        if (Objects.equals(fromColor1, color))
            return;
        Color old = fromColor1;
        fromColor1 = color;
        fromColor1Button.setBackground(fromColor1);
        firePropertyChange("fromColor1",old,color);
        updatePaletteChanges(applyToModel);
    }
    
    public Color getFromColor2(){
        return fromColor2;
    }
    
    public void setFromColor2(Color color){
        if (Objects.equals(fromColor2, color))
            return;
        Color old = fromColor2;
        fromColor2 = color;
        fromColor2Button.setBackground(fromColor2);
        firePropertyChange("fromColor2",old,color);
        updatePaletteChanges(applyToModel);
    }
    
    public Color getToColor1(){
        return toColor1;
    }
    
    public void setToColor1(Color color){
        if (Objects.equals(toColor1, color))
            return;
        Color old = toColor1;
        toColor1 = color;
        toColor1Button.setBackground(toColor1);
        firePropertyChange("toColor1",old,color);
        updatePaletteChanges(applyToModel);
    }
    
    public Color getToColor2(){
        return toColor2;
    }
    
    public void setToColor2(Color color){
        if (Objects.equals(toColor2, color))
            return;
        Color old = toColor2;
        toColor2 = color;
        toColor2Button.setBackground(toColor2);
        firePropertyChange("toColor2",old,color);
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
    
    public int showDialog(Component parent, List<Color> palette){
            // If the dialog is not showing
        if (getDialog() == null){
            paletteChanges.clear();
            if (palette != null)
                setPalette(palette);
            resetColors();
        }
        return super.showDialog(parent);
    }
    
    @Override
    public int showDialog(Component parent){
        return showDialog(parent,null);
    }

    @Override
    protected String getDefaultAcceptButtonToolTipText() {
        return "Apply changes";
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
    
    private void resetColors(){
        setFromColor1(Color.BLACK);
        setFromColor2(Color.WHITE);
        setToColor1(Color.RED);
        setToColor2(Color.BLUE);
    }
    
    public void reset(){
        applyToModel.clear();
        resetColors();
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
        for (Integer i : list){
            double blend = PkmnHelperUtilities.getColorRatio(fromColor1, fromColor2, palette.get(i));
            paletteChanges.put(i, PkmnHelperUtilities.blendColor(toColor1, toColor2, blend));
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
        fromColor1Button.setEnabled(isEnabled() && colorSelector != null);
        fromColor2Button.setEnabled(fromColor1Button.isEnabled());
        toColor1Button.setEnabled(fromColor1Button.isEnabled());
        toColor2Button.setEnabled(fromColor1Button.isEnabled());
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
    
    private JButton fromColor1Button;
    private JButton fromColor2Button;
    private JButton toColor1Button;
    private JButton toColor2Button;
    
    private Color fromColor1 = Color.BLACK;
    private Color fromColor2 = Color.WHITE;
    private Color toColor1 = Color.RED;
    private Color toColor2 = Color.BLUE;
    
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
                case("FromColor1"):
                    setFromColor1(showColorSelector(fromColor1));
                    break;
                case("FromColor2"):
                    setFromColor2(showColorSelector(fromColor2));
                    break;
                case("ToColor1"):
                    setToColor1(showColorSelector(toColor1));
                    break;
                case("ToColor2"):
                    setToColor2(showColorSelector(toColor2));
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
