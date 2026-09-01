/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.audio;

import components.JListManipulator;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;

/**
 *
 * @author Mosblinker
 */
public class TempPkmnAudioFileListPanel extends JListManipulator<File>{
    /**
     * 
     */
    public static final int BROWSE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW-1;
    
    public static final int DUPLICATE_BUTTON_ROW = LAST_RESERVED_BUTTON_ROW+1;
    
    public static final int CLEAR_BUTTON_ROW = LAST_RESERVED_BUTTON_ROW+2;
    /**
     * 
     */
    public static final String BROWSE_ACTION_COMMAND = "Browse";
    
    public static final String DUPLICATE_ACTION_COMMAND = "Duplicate";
    
    public static final String CLEAR_ACTION_COMMAND = "Clear";
    /**
     * 
     */
    public TempPkmnAudioFileListPanel(){
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
        setCellRenderer(new FileCellRenderer());
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
        }catch(NullPointerException ex) {}
    }
    @Override
    protected void fireIntervalAdded(int index0, int index1){
        super.fireIntervalAdded(index0, index1);
        try{
            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
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
            int[] sel = getSelectedIndices();
            for (int j = sel.length-1; j >= 0; j--){
                int i = sel[j];
                getModelList().add(i+1,getModelList().get(i));
            }
            for (int i = 0; i < sel.length; i++){
                sel[i]+= i+1;
            }
            setSelectedIndices(sel);
        }
    }
    @Override
    protected void fireSelectionChanged(int firstIndex, int lastIndex, 
            boolean isAdjusting){
        super.fireSelectionChanged(firstIndex, lastIndex, isAdjusting);
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
            }
        }
        
    }
    
    private class FileCellRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list,Object value,
                int index,boolean isSelected,boolean cellHasFocus){
            
            if (value instanceof File){
                value = ((File)value).getName();
            }
            
            return super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        }
    }
}
