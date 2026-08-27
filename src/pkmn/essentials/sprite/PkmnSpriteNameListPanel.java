/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.sprite;

import components.JListManipulator;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Mosblinker
 */
public class PkmnSpriteNameListPanel extends JListManipulator<PkmnSpriteIcon>{
    /**
     * 
     */
    public static final int BROWSE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW-1;
    
    public static final int DUPLICATE_BUTTON_ROW = LAST_RESERVED_BUTTON_ROW+1;
    
    public static final int CLEAR_BUTTON_ROW = LAST_RESERVED_BUTTON_ROW+2;
    
    public static final int FEMALE_TOGGLE_ROW = LAST_RESERVED_BUTTON_ROW+4;
    /**
     * 
     */
    public static final String BROWSE_ACTION_COMMAND = "Browse";
    
    public static final String DUPLICATE_ACTION_COMMAND = "Duplicate";
    
    public static final String CLEAR_ACTION_COMMAND = "Clear";
    
    public static final String FEMALE_TOGGLE_ACTION_COMMAND = "Female";
    /**
     * 
     */
    public PkmnSpriteNameListPanel(){
        super();
        super.setControlButtonsAreShown(false);
        super.setResetButtonIsShown(false);
//        super.setMoveToEdgeButtonsAreShown(false);
        browseButton = new JButton("Browse");
        browseButton.setActionCommand(BROWSE_ACTION_COMMAND);
        browseButton.addMouseListener(getDisabledComponentListener());
        browseButton.addActionListener(new Handler());
        addSideComponent(browseButton,BROWSE_BUTTON_ROW,true);
        duplicateButton = new JButton("Duplicate");
        duplicateButton.setActionCommand(DUPLICATE_ACTION_COMMAND);
        duplicateButton.addMouseListener(getDisabledComponentListener());
        duplicateButton.addActionListener(new Handler());
        duplicateButton.setEnabled(false);
        addSideComponent(duplicateButton,DUPLICATE_BUTTON_ROW,showDuplicateButton);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand(CLEAR_ACTION_COMMAND);
        clearButton.addMouseListener(getDisabledComponentListener());
        clearButton.addActionListener(new Handler());
        clearButton.setEnabled(false);
        addSideComponent(clearButton,CLEAR_BUTTON_ROW,true);
        setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        setVisibleRowCount(-1);
        setCellRenderer(new PkmnSpriteCellRenderer());
        femaleToggle = new JToggleButton("Female");
        femaleToggle.setActionCommand(FEMALE_TOGGLE_ACTION_COMMAND);
        femaleToggle.addMouseListener(getDisabledComponentListener());
        femaleToggle.addActionListener(new Handler());
        femaleToggle.setEnabled(false);
        addSideComponent(femaleToggle,FEMALE_TOGGLE_ROW,true);
    }
    
    public JButton getDuplicateButton(){
        return duplicateButton;
    }
    /**
     * 
     * @return 
     */
    public JButton getBrowseButton(){
        return browseButton;
    }
    /**
     * 
     * @return 
     */
    public JButton getClearButton(){
        return clearButton;
    }
    /**
     * 
     * @return 
     */
    public JToggleButton getFemaleToggle(){
        return femaleToggle;
    }
    /**
     * 
     * @return 
     */
    public boolean isDuplicateButtonShown(){
        return showDuplicateButton;
    }
    /**
     * 
     * @param value 
     */
    public void setDuplicateButtonShown(boolean value){
        showDuplicateButton = value;
        duplicateButton.setVisible(value);
    }
    /**
     * 
     * @return 
     */
    public boolean isFemaleToggleShown(){
        return showFemaleToggle;
    }
    /**
     * 
     * @param value 
     */
    public void setFemaleToggleShown(boolean value){
        showFemaleToggle = value;
        femaleToggle.setVisible(value);
    }
    /**
     * 
     * @param scale 
     */
    public void setScale(double scale){
        this.scale = scale;
        for (PkmnSpriteIcon icon : getListData())
            icon.setTargetScale(scale);
        scrollPane.revalidate();
        list.repaint();
        list.updateUI();
    }
    
    public double getScale(){
        return scale;
    }
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            browseButton.setEnabled(enabled);
            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
        }catch(NullPointerException ex) {}
    }
    @Override
    protected void updateListButtonsEnabled(){
        super.updateListButtonsEnabled();
        try{
            duplicateButton.setEnabled(isEnabled()&&!isSelectionEmpty());
            femaleToggle.setEnabled(duplicateButton.isEnabled());
        }catch(NullPointerException ex) {}
    }
    @Override
    protected void fireIntervalAdded(int index0, int index1){
        super.fireIntervalAdded(index0, index1);
        try{
            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
            for (int i = index0; i <= index1; i++){
                if (getModelList().get(i) != null)
                    getModelList().get(i).setTargetScale(scale);
            }
        }catch(NullPointerException ex) {}
    }
    @Override
    protected void fireIntervalRemoved(int index0, int index1){
        super.fireIntervalRemoved(index0, index1);
        try{
            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
        }catch(NullPointerException ex) {}
    }
    @Override
    protected void fireContentsChanged(int index0, int index1){
        super.fireContentsChanged(index0, index1);
        try{
            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
            for (int i = index0; i <= index1; i++){
                if (getModelList().size() > i && getModelList().get(i) != null)
                    getModelList().get(i).setTargetScale(scale);
            }
        }catch(NullPointerException ex) {}
    }
    /**
     * 
     */
    public void clear(){
        getModelList().clear();
    }
    /**
     * 
     */
    public void duplicate(){
        if (!isSelectionEmpty()){
            int size = getModelList().size();
            int[] sel = getSelectedIndices();
            for (int j = sel.length-1; j >= 0; j--){
                int i = sel[j];
                PkmnSpriteIcon icon = getModelList().get(i);
                if (icon instanceof PalettePkmnSpriteIcon)
                    icon = new PalettePkmnSpriteIcon(icon);
                else
                    icon = new PkmnSpriteIcon(icon);
                getModelList().add(i+1,icon);
            }
            for (int i = 0; i < sel.length; i++){
                sel[i]+= i+1;
            }
            setSelectedIndices(sel);
        }
    }
    /**
     * 
     * @param value
     */
    protected void setFemale(boolean value){
        if (!isSelectionEmpty()){
            for (PkmnSpriteIcon icon : getSelectedValuesList())
                icon.setFemale(value);
            list.repaint();
        }
    }
    @Override
    protected void fireSelectionChanged(int firstIndex, int lastIndex, 
            boolean isAdjusting){
        super.fireSelectionChanged(firstIndex, lastIndex, isAdjusting);
        boolean femaleSprites = false;
        for (PkmnSpriteIcon icon : getSelectedValuesList()){
            femaleSprites = icon.isFemale();
            if (!femaleSprites)
                break;
        }
        femaleToggle.setSelected(femaleSprites);
    }
    /**
     * 
     */
    private JButton browseButton;
    /**
     * 
     */
    private JButton duplicateButton;
    /**
     * 
     */
    private boolean showDuplicateButton = true;
    /**
     * 
     */
    private JButton clearButton;
    
    /**
     * 
     */
    private JToggleButton femaleToggle;
    
    private boolean showFemaleToggle = true;
    
    
    
    private double scale = 1.0;
    /**
     * 
     */
    private class Handler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case(BROWSE_ACTION_COMMAND):
                    fireActionPerformed(evt);
                    break;
                case(CLEAR_ACTION_COMMAND):
                    clear();
                    fireActionPerformed(evt);
                    break;
                case(DUPLICATE_ACTION_COMMAND):
                    duplicate();
                    break;
                case(FEMALE_TOGGLE_ACTION_COMMAND):
                    setFemale(femaleToggle.isSelected());
            }
        }
        
    }
}
