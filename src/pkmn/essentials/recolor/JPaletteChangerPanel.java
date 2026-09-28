/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import components.ArrayListModel;
import components.JColorSelector;
import components.JListSelector;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;
import pkmn.essentials.ColorCellRenderer;
import pkmn.essentials.PkmnHelperUtilities;
import pkmn.essentials.sprite.PalettePkmnSpriteIcon;
import pkmn.essentials.sprite.PkmnSpriteIcon;

/**
 *
 * @author Mosblinker
 */
public class JPaletteChangerPanel extends JPanel{
    
    public static final String ACCEPT_ACTION_COMMAND = "Accept";
    
    public static final String CANCEL_ACTION_COMMAND = "Cancel";
    
    public static final String RESET_ACTION_COMMAND = "Reset";
    
    private static final String PREVIEW_ACTION_COMMAND = "PreviewToggle";
    
    public static final String PREVIEW_SHOWN_PROPERTY_CHANGED = "PreviewShownPropertyChanged";
    
    private static final String APPLY_ADD_ACTION_COMMAND = "ApplyAdd";
    
    private static final String APPLY_REMOVE_ACTION_COMMAND = "ApplyRemove";
    /**
     * This constructs and returns a button to be used as one of the control 
     * buttons.
     * @param command The action command for the button.
     * @param handler The handler to use to listen to the button.
     * @return A button to use for the control buttons.
     */
    private JButton createButton(String text, String command, Handler handler){
            // This is the button to return.
        JButton button = new JButton(text);
        button.setActionCommand(command);
        button.addActionListener(handler);
        return button;
    }
    
    private void initialize(){
        paletteChangers = new ArrayList<>();
        handler = new Handler();
        
        topLabel = new JLabel();
        
        ctrlPanel = new JPanel(new java.awt.CardLayout());
        emptyCtrlPanel = new JPanel();
        ctrlPanel.add(emptyCtrlPanel, "");
        acceptButton = createButton("Apply",ACCEPT_ACTION_COMMAND,handler);
        acceptButton.setToolTipText("Apply changes");
        acceptButton.setEnabled(false);
        cancelButton = createButton("Cancel",CANCEL_ACTION_COMMAND,handler);
        cancelButton.setToolTipText("Close the window");
        
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
        applyAddButton = createButton("Add",APPLY_ADD_ACTION_COMMAND,handler);
        applyAddButton.setEnabled(false);
        applyAddButton.setMaximumSize(new java.awt.Dimension(32767, applyAddButton.getMaximumSize().height));
        applyRemoveButton = createButton("Remove",APPLY_REMOVE_ACTION_COMMAND,handler);
        applyRemoveButton.setEnabled(false);
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
        previewToggle.setActionCommand(PREVIEW_ACTION_COMMAND);
        previewToggle.setSelected(true);
        previewToggle.addActionListener(handler);
        
        resetButton = createButton("Reset",RESET_ACTION_COMMAND,handler);
        
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
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        add(topLabel, constraints);
        topLabel.setVisible(false);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        add(ctrlPanel, constraints);
        ctrlPanel.setVisible(false);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        constraints.weightx = 0.9;
        constraints.weighty = 0.9;
        add(applyPanel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        add(bottomPanel, constraints);
        
        applyAddSelector = new JListSelector<>(new DefaultListModel<>());
        applyAddSelector.setCellRenderer(new ColorIndexCellRenderer());
        applyAddSelector.setFixedCellHeight(20);
        applyAddSelector.setFixedCellWidth(20);
        applyAddSelector.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        applyAddSelector.setVisibleRowCount(-1);
        
        setPreferredSize(new java.awt.Dimension(320, 320));
    }
    
    public JPaletteChangerPanel(){
        super(new java.awt.GridBagLayout());
        initialize();
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
        updatePaletteChanges();
    }
    
    public boolean isPreviewShown(){
        return previewToggle.isSelected();
    }
    
    public void setPreviewShown(boolean value){
        if (isPreviewShown() == value)
            return;
        previewToggle.setSelected(value);
        firePropertyChange(PREVIEW_SHOWN_PROPERTY_CHANGED,!value,value);
    }
    
    public void prepareToShow(List<Color> palette, Color color){
        paletteChanges.clear();
        if (palette != null)
            setPalette(palette);
        for (PaletteChanger changer : paletteChangers)
            changer.prepareToShow(palette, color);
    }
    
    public void prepareToShow(List<Color> palette, Color color, int changerIndex){
        prepareToShow(palette,color);
        setSelectedPaletteChangerIndex(changerIndex);
    }
    
    public void prepareToShow(List<Color> palette){
        prepareToShow(palette,null);
    }
    
    public PaletteChanger[] getPaletteChangers(){
        PaletteChanger[] temp = new PaletteChanger[paletteChangers.size()];
        for (int i = 0; i < paletteChangers.size(); i++)
            temp[i] = paletteChangers.get(i);
        return temp;
    }
    
    public void addPaletteChanger(PaletteChanger changer){
        Objects.requireNonNull(changer);
        paletteChangers.add(changer);
        changer.addChangeListener(handler);
        changer.setDialogPanel(this);
        if (changer.getControlPanel() != null)
            ctrlPanel.add(changer.getControlPanel(), changer.getCardName());
        changer.setColorElementsEnabled(getColorElementsEnabled());
        changer.setEnabled(isEnabled());
    }
    
    public void removePaletteChanger(PaletteChanger changer){
        if (changer == null)
            return;
        int index = paletteChangers.indexOf(changer);
        if (index < 0)
            return;
        if (index < currentPaletteChanger)
            currentPaletteChanger--;
        else if (index == currentPaletteChanger)
            setSelectedPaletteChangerIndex(-1);
        changer.removeChangeListener(handler);
        if (changer.getDialogPanel() == this)
            changer.setDialogPanel(null);
        paletteChangers.remove(changer);
        if (changer.getControlPanel() != null)
            ctrlPanel.remove(changer.getControlPanel());
        changer.setColorElementsEnabled(false);
    }
    
    public int getSelectedPaletteChangerIndex(){
        return currentPaletteChanger;
    }
    
    public PaletteChanger getSelectedPaletteChanger(){
        if (currentPaletteChanger < 0)
            return null;
        return paletteChangers.get(currentPaletteChanger);
    }
    
    public void setSelectedPaletteChangerIndex(int index){
        if (index != -1)
            Objects.checkIndex(index, paletteChangers.size());
        if (currentPaletteChanger == index)
            return;
        currentPaletteChanger = index;
        PaletteChanger changer = getSelectedPaletteChanger();
        changeCtrlCard(changer);
        String acceptText = null;
        String acceptToolTip = null;
        String cancelToolTip = null;
        if (changer != null){
            acceptText = changer.getAcceptButtonText();
            acceptToolTip = changer.getAcceptButtonTooltipText();
            cancelToolTip = changer.getCancelButtonToolTipText();
        }
        acceptButton.setText((acceptText==null)?"Apply":acceptText);
        acceptButton.setToolTipText((acceptToolTip==null)?"Apply changes":acceptToolTip);
        cancelButton.setToolTipText((cancelToolTip==null)?"Close the window":cancelToolTip);
        if (changer != null){
            topLabel.setText(changer.getLabelText());
            topLabel.setVisible(changer.getLabelText() != null);
        } else 
            topLabel.setVisible(false);
        updatePaletteChanges();
    }
    
    public void setSelectedPaletteChanger(PaletteChanger changer){
        if (changer == null){
            setSelectedPaletteChangerIndex(-1);
            return;
        }
        int index = paletteChangers.indexOf(changer);
        if (index < 0){
            index = paletteChangers.size();
            addPaletteChanger(changer);
        }
        setSelectedPaletteChangerIndex(index);
    }
    
    public String getDialogTitle(){
        PaletteChanger changer = getSelectedPaletteChanger();
        if (changer == null)
            return null;
        return changer.getDialogTitle();
    }
    
    protected void changeCtrlCard(PaletteChanger changer){
        if (changer != null && changer.getControlPanel() != null)
            PkmnHelperUtilities.setCard(ctrlPanel, changer.getCardName());
        else
            PkmnHelperUtilities.setCard(ctrlPanel, "");
        ctrlPanel.setVisible(changer != null && changer.getControlPanel() != null);
    }
    
    public void reset(){
        applyToModel.clear();
        for (PaletteChanger changer : paletteChangers)
            changer.reset();
        for (int i = 0; i < palette.size(); i++){
            applyToModel.add(i);
        }
        paletteChanges.clear();
        updatePaletteChanges();
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
    
    public void updatePaletteChanges(List<Integer> list){
        PaletteChanger changer = getSelectedPaletteChanger();
        if (changer == null){
            paletteChanges.clear();
            return;
        }
        changer.updatePaletteChanges(list,palette,paletteChanges);
        for (int i = 0; i < palette.size(); i++){
            if (palette.get(i).equals(paletteChanges.get(i)))
                paletteChanges.remove(i);
        }
        updatePreviewIcons();
    }
    
    public void updatePaletteChanges(){
        updatePaletteChanges(applyToModel);
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
    
    public void setColorSelector(JColorSelector colorSelector){
        if (colorSelector == this.colorSelector)
            return;
        JColorSelector old = this.colorSelector;
        this.colorSelector = colorSelector;
        firePropertyChange("colorSelector",old,colorSelector);
        updateColorElementsEnabled();
    }
    
    public JColorSelector getColorSelector(){
        return colorSelector;
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
    
    protected void updateAddButtonEnabled(){
        applyAddButton.setEnabled(isEnabled() && !palette.isEmpty() && 
                applyToModel.size() < palette.size() && 
                currentPaletteChanger >= 0);
    }
    
    protected void updateRemoveButtonEnabled(){
        applyRemoveButton.setEnabled(isEnabled() && !applyToModel.isEmpty() && 
                !applyToList.isSelectionEmpty() && currentPaletteChanger >= 0);
    }
    
    protected boolean isAcceptEnabled(){
        return isEnabled() && applyToModel != null && !applyToModel.isEmpty() 
                && currentPaletteChanger >= 0;
    }
    /**
     * This updates whether the accept button is enabled. This sets whether the 
     * accept button is enabled based off the value returned by {@link 
     * #isAcceptEnabled() }. 
     * @see #getAcceptButton() 
     * @see #isAcceptEnabled() 
     * @see #setEnabled(boolean) 
     */
    protected void updateAcceptEnabled(){
            // If the accept button has been initialized
        if (acceptButton != null)  
            acceptButton.setEnabled(isAcceptEnabled());
    }
    
    protected boolean getColorElementsEnabled(){
        return isEnabled() && colorSelector != null;
    }
    
    protected void updateColorElementsEnabled(){
        boolean value =getColorElementsEnabled();
        for (PaletteChanger changer : paletteChangers){
            changer.setColorElementsEnabled(value);
        }
    }
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            updateAddButtonEnabled();
            updateRemoveButtonEnabled();
            updateAcceptEnabled();
            resetButton.setEnabled(enabled);
            for (PaletteChanger changer : paletteChangers)
                changer.setEnabled(enabled);
            updateColorElementsEnabled();
        } catch (NullPointerException ex) {}
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
     * This adds the given {@code ChangeListener} to this panel.
     * @param l The listener to add.
     * @see #removeActionListener(ChangeListener) 
     * @see #getActionListeners() 
     */
    public void addChangeListener(ChangeListener l){
        if (l != null)          // If the listener is not null
            listenerList.add(ChangeListener.class, l);
    }
    /**
     * This removes the given {@code ActionListener} from this panel.
     * @param l The listener to remove.
     * @see #addActionListener(ActionListener) 
     * @see #getActionListeners() 
     */
    public void removeChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }
    /**
     * This returns an array containing all the {@code ActionListener}s that 
     * have been added to this panel.
     * @return An array containing the {@code ActionListener}s that have been 
     * added, or an empty array if none have been added.
     * @see #addActionListener(ActionListener) 
     * @see #removeActionListener(ActionListener) 
     */
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
    
    protected JLabel topLabel;
    
    private JPanel emptyCtrlPanel;
    
    private Handler handler;
    
    protected List<PaletteChanger> paletteChangers;
    
    private int currentPaletteChanger = -1;
    
    protected JPanel ctrlPanel;
    
    protected JScrollPane applyToScrollPane;
    
    protected JList<Integer> applyToList;
    
    protected ArrayListModel<Integer> applyToModel;
    
    protected JButton applyAddButton;
    
    protected JButton applyRemoveButton;
    
    protected JCheckBox previewToggle;
    
    protected JButton resetButton;
    
    protected JListSelector<Integer> applyAddSelector;
    
    protected List<Color> palette = new ArrayList<>();
    
    protected Map<Integer,Color> paletteChanges = new HashMap<>();
    
    protected List<Color> changedPalette = new ChangedPaletteList();
    
    protected List<Color> prevPalette;
    
    protected List<PkmnSpriteIcon> icons = new ArrayList<>();
    
    protected List<PalettePkmnSpriteIcon> previewIcons = new ArrayList<>();
    
    protected JColorSelector colorSelector = null;
    
    protected JButton acceptButton;
    
    protected JButton cancelButton;
    
    private class Handler implements ActionListener, ListSelectionListener, 
            ListDataListener, ChangeListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case(ACCEPT_ACTION_COMMAND):
                case(CANCEL_ACTION_COMMAND):
                    fireActionPerformed(evt);
                    break;
                case(RESET_ACTION_COMMAND):
                    reset();
                    break;
                case(PREVIEW_ACTION_COMMAND):
                    firePropertyChange(PREVIEW_SHOWN_PROPERTY_CHANGED,!previewToggle.isSelected(),previewToggle.isSelected());
                    break;
                case(APPLY_ADD_ACTION_COMMAND):
                    addPaletteColors();
                    break;
                case(APPLY_REMOVE_ACTION_COMMAND):
                    removePaletteColors();
                    break;
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
        @Override
        public void stateChanged(ChangeEvent evt) {
            updatePaletteChanges();
        }
    }
    
    protected class ColorIndexCellRenderer extends ColorCellRenderer{
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
    
    protected class ChangedPaletteList extends AbstractList<Color>{
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
