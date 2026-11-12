/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.audio;

import components.ListModelList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Mosblinker
 */
public class PkmnAudioFileListPanel extends JPanel{
    /**
     * This identifies that the accessory component to the side of the list has 
     * changed.
     */
    public static final String SIDE_ACCESSORY_PROPERTY_CHANGED = 
            "SideAccessoryPropertyChanged";
    /**
     * This identifies that the accessory component under the list has changed.
     */
    public static final String BOTTOM_ACCESSORY_PROPERTY_CHANGED = 
            "BottomAccessoryPropertyChanged";
    /**
     * This identifies that the remove button has been set to be shown or 
     * hidden.
     */
    public static final String REMOVE_BUTTON_IS_SHOWN_PROPERTY_CHANGED = 
            "RemoveButttonIsShownPropertyChanged";
    /**
     * This identifies that the move buttons have been set to be shown or 
     * hidden.
     */
    public static final String MOVE_BUTTONS_ARE_SHOWN_PROPERTY_CHANGED = 
            "MoveButtonsAreShownPropertyChanged";
    /**
     * This identifies that the move to edge buttons have been set to be shown 
     * or hidden when the move buttons are shown. These are the buttons to move 
     * elements to the top or bottom of the list.
     */
    public static final String MOVE_TO_EDGE_BUTTONS_ARE_SHOWN_PROPERTY_CHANGED = 
            "MoveToEdgeButtonsAreShownPropertyChanged";
    /**
     * This identifies that the reverse button has been set to be shown or 
     * hidden.
     */
    public static final String REVERSE_BUTTON_IS_SHOWN_PROPERTY_CHANGED = 
            "ReverseButttonIsShownPropertyChanged";
    /**
     * This identifies that a change has been made to whether the list buttons 
     * use icons or text to denote the action they perform.
     */
    public static final String ICONS_ARE_SHOWN_PROPERTY_CHANGED = 
            "IconsAreShownPropertyChanged";
    /**
     * This identifies that a change has been made to the amount by which 
     * selected items are moved in the list.
     */
    public static final String SHIFT_DISTANCE_PROPERTY_CHANGED = 
            "ShiftDistancePropertyChanged";
    /**
     * This is the instruction to move the selected items in the list, if any, 
     * backwards (towards the start of the list).
     */
    public static final String MOVE_SELECTION_BACKWARD ="MoveSelectionBackward";
    /**
     * This is the instruction to move the selected items in the list, if any, 
     * forwards (towards the end of the list).
     */
    public static final String MOVE_SELECTION_FORWARD = "MoveSelectionForward";
    /**
     * This is the instruction to move the selected items in the list, if any, 
     * to the start of the list.
     */
    public static final String MOVE_SELECTION_TO_START ="MoveSelectionToStart";
    /**
     * This is the instruction to move the selected items in the list, if any, 
     * to the end of the list.
     */
    public static final String MOVE_SELECTION_TO_END = "MoveSelectionToEnd";
    /**
     * This is the instruction to remove the selected items, if any, from the 
     * list.
     */
    public static final String REMOVE_SELECTION = "RemoveSelection";
    /**
     * This is the instruction to reverse the order in which the selected items, 
     * if any, appear in the list.
     */
    public static final String REVERSE_SELECTION_ORDER ="ReverseSelectionOrder";
    /**
     * This is the instruction to revert any changes made to the list model 
     * since the last time it was set or changes to it were accepted.
     */
    public static final String RESET_LIST_CONTENTS = "ResetListContents";
    
    public static final String BROWSE_ACTION_COMMAND = "Browse";
    
    public static final String DUPLICATE_ACTION_COMMAND = "Duplicate";
    
    public static final String CLEAR_ACTION_COMMAND = "Clear";
    
    /**
     * This is the first row in the side panel that is reserved for current or 
     * future use by {@code JListManipulator}. Subclasses are free to add 
     * additional components to the side panel before this row and after {@link 
     * #LAST_RESERVED_BUTTON_ROW}. However, the rows at {@link 
     * #SIDE_ACCESSORY_PANEL_ROW} and {@link #RESET_BUTTON_ROW} are also 
     * reserved by {@code JListManipulator} for the side accessory panel and 
     * reset button respectively.
     * @see #LAST_RESERVED_BUTTON_ROW
     * @see #SIDE_ACCESSORY_PANEL_ROW
     * @see #RESET_BUTTON_ROW
     */
    protected static final int FIRST_RESERVED_BUTTON_ROW = 96;
    /**
     * This is the row in the side panel where the add item button will be 
     * located once implemented.
     */
    protected static final int BROWSE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW;
    /**
     * This is the row in the side panel where the edit item button will be 
     * located once implemented.
     */
    protected static final int EDIT_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW+1;
    /**
     * This is the row in the side panel where the move backwards button is 
     * located.
     */
    protected static final int MOVE_BACKWARD_BUTTON_ROW = 
            FIRST_RESERVED_BUTTON_ROW+2;
    /**
     * This is the row in the side panel where the move forwards button is 
     * located.
     */
    protected static final int MOVE_FORWARD_BUTTON_ROW = 
            FIRST_RESERVED_BUTTON_ROW+3;
    /**
     * This is the row in the side panel where the move to start button is 
     * located.
     */
    protected static final int MOVE_TO_START_BUTTON_ROW = 
            FIRST_RESERVED_BUTTON_ROW+4;
    /**
     * This is the row in the side panel where the move to end button is 
     * located.
     */
    protected static final int MOVE_TO_END_BUTTON_ROW = 
            FIRST_RESERVED_BUTTON_ROW+5;
    /**
     * This is the row in the side panel where the reverse button is located.
     */
    protected static final int REVERSE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW+6;
    /**
     * This is the row in the side panel where the remove button is located.
     */
    protected static final int REMOVE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW+7;
    
    protected static final int DUPLICATE_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW+8;
    
    protected static final int CLEAR_BUTTON_ROW = FIRST_RESERVED_BUTTON_ROW+9;
    /**
     * This is the last row in the side panel that is reserved for current or 
     * future use by {@code JListManipulator}, not including {@link 
     * #SIDE_ACCESSORY_PANEL_ROW} and {@link #RESET_BUTTON_ROW}. Subclasses are 
     * free to add additional components to the side panel before {@link 
     * #FIRST_RESERVED_BUTTON_ROW} and after this row, with the exception of 
     * {@code SIDE_ACCESSORY_PANEL_ROW} and {@code RESET_BUTTON_ROW}.
     * @see #LAST_RESERVED_BUTTON_ROW
     * @see #SIDE_ACCESSORY_PANEL_ROW
     * @see #RESET_BUTTON_ROW
     */
    protected static final int LAST_RESERVED_BUTTON_ROW = 
            FIRST_RESERVED_BUTTON_ROW+31;
    /**
     * This is the row in the side panel where the side accessory panel is 
     * located.
     */
    protected static final int SIDE_ACCESSORY_PANEL_ROW = 256;
    
    /**
     * This constructs a button to be used to manipulate the contents of the 
     * list in some way.
     * @param text  The text for the button.
     * @param mnemonicIndex The index in the text for the mnemonic for the 
     * button.
     * @param toolTip The tool tip text for the button.
     * @param command The action command for the button.
     * @return A button to be used to manipulate the list.
     */
    private JButton createListButton(String text,int mnemonicIndex, 
            String toolTip, String command){
            // This constructs the button to return
        JButton button = new JButton(text);
        button.setMnemonic(text.charAt(mnemonicIndex));
        button.setToolTipText(toolTip);
        button.setActionCommand(command);
//        button.addActionListener(handler);
//        button.addMouseListener(getDisabledComponentListener());
        return button;
    }
    /**
     * This constructs a button to be used to move selected items in the list. 
     * @param dir The String representation of the direction that the items will 
     * be moved in, used for the text of the button.
     * @param mnemonicIndex The index in the direction text for the mnemonic 
     * for the button. This is the index in {@code dir}.
     * @param toolTipDir The String representation of the direction that the 
     * items will be moved in, used for the text for the tool tip of the button.
     * @param command The action command for the button.
     * @return A button to be used to move selected items in the list.
     * @see #createListButton 
     * @see #createMoveButton(String, String) 
     */
    private JButton createMoveButton(String dir, int mnemonicIndex, 
            String toolTipDir, String command){
        return createListButton("Move "+dir,mnemonicIndex+5,
                "Move the currently selected items "+toolTipDir+" the list",
                command);
    }
    /**
     * This constructs a button to be used to move selected items in the list. 
     * This is equivalent to calling {@link #createMoveButton(String, int, 
     * String, String) createMoveButton}{@code (dir, 0, dir.toLowerCase()+" in", 
     * command)}.
     * @param dir The String representation of the direction that the items will 
     * be moved in.
     * @param command The action command for the button.
     * @return A button to be used to move selected items in the list.
     * @see #createListButton 
     * @see #createMoveButton(String, int, String, String) 
     */
    private JButton createMoveButton(String dir, String command){
        return createMoveButton(dir,0,dir.toLowerCase()+" in",command);
    }
    /**
     * This adds the given component to the side panel at cell {@code (0, y)}, 
     * along with setting whether the component is initially {@code visible}. 
     * <p>
     * 
     * If the component is to be placed at the {@link #SIDE_ACCESSORY_PANEL_ROW 
     * row for the side accessory panel}, then the component will be anchored to 
     * the center and will have a horizontal and vertical weight of 0.9. If the 
     * component is to be placed above the side accessory panel, then it will 
     * have an inset from the bottom of 7 and will be anchored towards the start 
     * (top) of the page. If the component is to be placed below the side 
     * accessory panel, then it will have an inset from the top of 7 and will be 
     * anchored toward the end (bottom) of the page. All components will be 
     * allowed to expand horizontally, but only the side accessory panel will be 
     * allowed to expand vertically as well. <p>
     * 
     * It is recommended for subclasses that add additional components to avoid 
     * using the rows in between {@link #FIRST_RESERVED_BUTTON_ROW} and {@link 
     * #LAST_RESERVED_BUTTON_ROW}, inclusive, and to avoid using the rows for 
     * the {@link #SIDE_ACCESSORY_PANEL_ROW side accessory panel} and the {@link 
     * #RESET_BUTTON_ROW reset button}, as these rows are considered to be 
     * reserved for current or future use by {@code JListManipulator}.
     * 
     * @param c The component to add to the side panel. 
     * @param y The row at which the component will be placed along the side 
     * panel.
     * @param visible Whether the component should be visible initially.
     * @see #FIRST_RESERVED_BUTTON_ROW
     * @see #ADD_BUTTON_ROW
     * @see #EDIT_BUTTON_ROW
     * @see #MOVE_BACKWARD_BUTTON_ROW
     * @see #MOVE_FORWARD_BUTTON_ROW
     * @see #REVERSE_BUTTON_ROW
     * @see #REMOVE_BUTTON_ROW
     * @see #LAST_RESERVED_BUTTON_ROW
     * @see #SIDE_ACCESSORY_PANEL_ROW
     * @see #RESET_BUTTON_ROW
     */
    protected void addSideComponent(Component c,int y,boolean visible){
           // This is the constraints object to use to place the component
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = y;
        constraints.fill = GridBagConstraints.HORIZONTAL;
            // If the component is to be placed above the side accessory panel
        if (y < SIDE_ACCESSORY_PANEL_ROW){
            constraints.insets = new Insets(0, 0, 7, 0);
            constraints.anchor = GridBagConstraints.PAGE_START;
        }   // If the component is to be placed below the side accessory panel
        else if (y > SIDE_ACCESSORY_PANEL_ROW){
            constraints.insets = new Insets(7, 0, 0, 0);
            constraints.anchor = GridBagConstraints.PAGE_END;
        }
        else{
            constraints.weighty = 0.9;
            constraints.weightx = 0.9;
            constraints.fill = GridBagConstraints.BOTH;
        }
        sidePanel.add(c,constraints);
        c.setVisible(visible);
    }
//    /**
//     * 
//     */
//    public PkmnAudioFileListPanel(){
//        super();
//        super.setControlButtonsAreShown(false);
//        super.setResetButtonIsShown(false);
//        super.setMoveToEdgeButtonsAreShown(false);
//        browseButton = new JButton("Browse");
//        browseButton.setActionCommand(BROWSE_ACTION_COMMAND);
//        browseButton.addMouseListener(getDisabledComponentListener());
//        browseButton.addActionListener(new Handler());
//        addSideComponent(browseButton,BROWSE_BUTTON_ROW,true);
//        duplicateButton = new JButton("Duplicate");
//        duplicateButton.setActionCommand(DUPLICATE_ACTION_COMMAND);
//        duplicateButton.addMouseListener(getDisabledComponentListener());
//        duplicateButton.addActionListener(new Handler());
//        duplicateButton.setEnabled(false);
//        addSideComponent(duplicateButton,DUPLICATE_BUTTON_ROW,showDuplicateButton);
//        clearButton = new JButton("Clear");
//        clearButton.setActionCommand(CLEAR_ACTION_COMMAND);
//        clearButton.addMouseListener(getDisabledComponentListener());
//        clearButton.addActionListener(new Handler());
//        clearButton.setEnabled(false);
//        addSideComponent(clearButton,CLEAR_BUTTON_ROW,true);
//        setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
//        setVisibleRowCount(-1);
//        setCellRenderer(new PkmnSpriteCellRenderer());
//    }
//    
//    public JButton getDuplicateButton(){
//        return duplicateButton;
//    }
//    /**
//     * 
//     * @return 
//     */
//    public JButton getBrowseButton(){
//        return browseButton;
//    }
//    /**
//     * 
//     * @return 
//     */
//    public JButton getClearButton(){
//        return clearButton;
//    }
//    /**
//     * 
//     * @return 
//     */
//    public boolean isDuplicateButtonShown(){
//        return showDuplicateButton;
//    }
//    /**
//     * 
//     * @param value 
//     */
//    public void setDuplicateButtonShown(boolean value){
//        showDuplicateButton = value;
//        duplicateButton.setVisible(value);
//    }
//    @Override
//    public void setEnabled(boolean enabled){
//        super.setEnabled(enabled);
//        try{
//            browseButton.setEnabled(enabled);
//            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
//        }catch(NullPointerException ex) {}
//    }
//    @Override
//    protected void updateListButtonsEnabled(){
//        super.updateListButtonsEnabled();
//        try{
//            duplicateButton.setEnabled(isEnabled()&&!isSelectionEmpty());
//        }catch(NullPointerException ex) {}
//    }
//    @Override
//    protected void fireIntervalAdded(int index0, int index1){
//        super.fireIntervalAdded(index0, index1);
//        try{
//            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
//        }catch(NullPointerException ex) {}
//    }
//    @Override
//    protected void fireIntervalRemoved(int index0, int index1){
//        super.fireIntervalRemoved(index0, index1);
//        try{
//            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
//        }catch(NullPointerException ex) {}
//    }
//    @Override
//    protected void fireContentsChanged(int index0, int index1){
//        super.fireContentsChanged(index0, index1);
//        try{
//            clearButton.setEnabled(isEnabled()&&!getModelList().isEmpty());
//        }catch(NullPointerException ex) {}
//    }
//    /**
//     * 
//     */
//    public void clear(){
//        getModelList().clear();
//    }
//    /**
//     * 
//     */
//    public void duplicate(){
//        if (!isSelectionEmpty()){
//            int size = getModelList().size();
//            for (int i = 0; i < size; i++){
//                PkmnSpriteIcon icon = getModelList().get(i);
//                if (icon instanceof PalettePkmnSpriteIcon)
//                    icon = new PalettePkmnSpriteIcon(icon);
//                else
//                    icon = new PkmnSpriteIcon(icon);
//                getModelList().add(icon);
//            }
//            setSelectionInterval(size,getModelList().size()-1);
//        }
//    }
//    @Override
//    protected void fireSelectionChanged(int firstIndex, int lastIndex, 
//            boolean isAdjusting){
//        super.fireSelectionChanged(firstIndex, lastIndex, isAdjusting);
//    }
//    /**
//     * 
//     */
//    private JButton browseButton;
//    /**
//     * 
//     */
//    private JButton duplicateButton;
//    /**
//     * 
//     */
//    private boolean showDuplicateButton = true;
//    /**
//     * 
//     */
//    private JButton clearButton;
//    /**
//     * 
//     */
//    private class Handler implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            switch(evt.getActionCommand()){
//                case(BROWSE_ACTION_COMMAND):
//                    fireActionPerformed(evt);
//                    break;
//                case(CLEAR_ACTION_COMMAND):
//                    clear();
//                    fireActionPerformed(evt);
//                    break;
//                case(DUPLICATE_ACTION_COMMAND):
//                    duplicate();
//            }
//        }
//        
//    }
//    
//    private class PanelRenderer extends JPanel implements ListCellRenderer<Component>{
//
//        @Override
//        public Component getListCellRendererComponent(JList<? extends Component> list, 
//                Component value, int index, boolean isSelected, boolean cellHasFocus) {
//            
//        }
//        
//    }
    
    
    /**
     * This updates the visibility of the given container based off whether any 
     * of the components in the container are visible. This effectively 
     * calls {@link #containsVisibleComponents containsVisibleComponents} with 
     * the given container and sets the container's visibility based of the 
     * value returned.
     * @param c The container to update the visibility of.
     * @see #containsVisibleComponents 
     * @see #updateParentVisibility 
     */
    protected void updateContainerVisibility(Container c){
//        c.setVisible(containsVisibleComponents(c));
    }
    /**
     * This updates the visibility of the parent of the given component. This 
     * effectively calls {@link #updateContainerVisibility 
     * updateContainerVisibility} with the parent of the given component.
     * @param c The component to update the parent's visibility.
     * @see #updateContainerVisibility 
     * @see #containsVisibleComponents 
     * @see Component#getParent 
     */
    protected void updateParentVisibility(Component c){
            // If neither the component or its parent are null.
        if (c == null || c.getParent() == null)
            return;
        updateContainerVisibility(c.getParent());
    }
    /**
     * This updates the panel used to display an accessory to no longer display 
     * the old accessory and display the new accessory instead. The new 
     * accessory is added to the center of the panel.
     * @param oldAccessory The old accessory that will no longer be displayed, 
     * or null.
     * @param newAccessory The new accessory that will be displayed, or null.
     * @param panel The panel displaying the accessory.
     * @see #setSideAccessory 
     * @see #setBottomAccessory 
     */
    protected void updateAccessoryPanel(JComponent oldAccessory, 
            JComponent newAccessory, JPanel panel){
        if (oldAccessory != null)       // If the old accessory is not null
            panel.remove(oldAccessory);
        if (newAccessory != null)       // If the new accessory is not null
            panel.add(newAccessory,BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
//        if (panel.getParent() != null)  // If the panel has a parent
//            panel.getParent().revalidate();
    }
    /**
     * This sets the accessory component to display to the right of the list 
     * above the control panel. The accessory components can be used to add 
     * additional controls such as a button to add items to the list. <p>
     * 
     * Note: If there was a previous accessory, you should remove any listeners 
     * that the accessory may have added to this {@code JListSelector}.
     * 
     * @param accessory The side accessory component.
     * @see #getSideAccessory 
     * @see #setBottomAccessory 
     * @see #getBottomAccessory 
     */
    public void setSideAccessory(JComponent accessory){
            // If the side accessory would not be changed
        if (accessory == sideAccessory)
            return;
        JComponent old = sideAccessory;     // Get the old side accessory
        sideAccessory = accessory;
        firePropertyChange(SIDE_ACCESSORY_PROPERTY_CHANGED,old,accessory);
            // If the side accessory panel is not initialized
//        if (sideAccessoryPanel == null)
//            sideAccessoryPanel = createAccessoryPanel(sidePanel);
        updateAccessoryPanel(old,accessory,sideAccessoryPanel);
    }
    /**
     * This returns the accessory component displayed to the right of the list 
     * above the control panel.
     * @return The side accessory component, or null.
     * @see #setSideAccessory 
     * @see #getBottomAccessory 
     * @see #setBottomAccessory 
     */
    public JComponent getSideAccessory(){
        return sideAccessory;
    }
    /**
     * This sets the accessory component to display at the bottom next to the 
     * control buttons. The accessory components can be used to add additional 
     * controls such as a button to add items to the list. <p>
     * 
     * Note: If there was a previous accessory, you should remove any listeners 
     * that the accessory may have added to this {@code JListSelector}.
     * 
     * @param accessory The bottom accessory component.
     * @see #getBottomAccessory 
     * @see #setSideAccessory 
     * @see #getSideAccessory 
     */
    public void setBottomAccessory(JComponent accessory){
            // If the bottom accessory would not be changed
        if (accessory == bottomAccessory)   
            return;
        JComponent old = bottomAccessory;   // Get the old bottom accessory
        bottomAccessory = accessory;
        firePropertyChange(BOTTOM_ACCESSORY_PROPERTY_CHANGED,old,accessory);
            // If the bottom accessory panel is not initialized
//        if (bottomAccessoryPanel == null)
//            bottomAccessoryPanel = createAccessoryPanel(bottomPanel);
        updateAccessoryPanel(old,accessory,bottomAccessoryPanel);
    }
    /**
     * This returns the accessory component displayed at the bottom of this 
     * panel next to the control buttons.
     * @return The bottom accessory component, or null.
     * @see #setBottomAccessory 
     * @see #getSideAccessory 
     * @see #setSideAccessory 
     */
    public JComponent getBottomAccessory(){
        return bottomAccessory;
    }
    
    private ListModelList<? extends File> model;
    
    private ArrayList<PkmnAudioFilePanel> audioPanels = new ArrayList<>();
    
    private JPanel audioPanelList;
    
    private JScrollPane audioPanelScrollPane;
    
    /**
     * This controls whether the remove button is to be shown.
     */
    private boolean showRemove = true;
    /**
     * This controls whether the move buttons are to be shown.
     */
    private boolean showMove = true;
    /**
     * This controls whether the move to edge buttons ({@code moveToStart} and 
     * {@code moveToEnd} are to be shown.
     */
    private boolean showEdge = true;
    /**
     * This controls whether the reverse button is to be shown.
     */
    private boolean showReverse = true;
    /**
     * This controls whether the reset button is to be shown.
     */
    private boolean showReset = true;
    /**
     * This controls whether the list buttons use icons or text to denote what 
     * the button does.
     */
    private boolean useIcons = false;
    /**
     * This is the amount by which to shift the selected items in the list 
     * forward or backward.
     */
    private int shiftAmount = 1;
    /**
     * This is the filler component used to fill the side accessory panel when 
     * there is no side accessory, so as to prevent layout issues with the side 
     * panel.
     */
    private Box.Filler sideAccessoryFiller;
    /**
     * This is the button used to remove selected items from the list.
     */
    protected JButton removeButton;
    /**
     * This is the button used to shift selected items backward in the list.
     */
    protected JButton backwardButton;
    /**
     * This is the button used to shift selected items forward in the list.
     */
    protected JButton forwardButton;
    /**
     * This is the button used to shift selected items to the start of the list.
     */
    protected JButton moveToStartButton;
    /**
     * This is the button used to shift selected items to the end of the list.
     */
    protected JButton moveToEndButton;
    /**
     * This is the button used to reverse the order of the selected items in the 
     * list.
     */
    protected JButton reverseButton;
    /**
     * This is the button used to add items to the list (currently unused).
     */
    // TODO: Implement the add button
    protected JButton addButton = null;
    /**
     * This is the button used to edit items in the list (currently unused).
     */
    // TODO: Implement the edit button
    protected JButton editButton = null;
    /**
     * This is the handler used to update the visibility of containers when 
     * their child components are shown or hidden.
     */
    private ParentVisibilityHandler visHandler;
    /**
     * This is the panel to the right of the list that is used to contain the 
     * side accessory.
     */
    protected JPanel sidePanel;
    /**
     * This is the panel at the bottom of this panel used to contain the control 
     * panel and the bottom accessory.
     */
    protected JPanel bottomPanel;
    /**
     * This is the panel used to contain the side accessory within the side 
     * panel. This is initialized the first time the side accessory is set to a 
     * non-null value.
     */
    protected JPanel sideAccessoryPanel = null;
    /**
     * This is the panel used to contain the bottom accessory within the bottom 
     * panel. This is initialized the first time the bottom accessory is set to 
     * a non-null value.
     */
    protected JPanel bottomAccessoryPanel = null;
    /**
     * This is the accessory that appears to the side of the list.
     */
    private JComponent sideAccessory = null;
    /**
     * This is the accessory that appears at the bottom next to the control 
     * buttons.
     */
    private JComponent bottomAccessory = null;
    
    
    
    
    
    
    /**
     * This is a handler used to update the visibility of containers based off 
     * their child component's visibility.
     */
    private class ParentVisibilityHandler extends ComponentAdapter implements 
            ContainerListener{
        @Override
        public void componentShown(ComponentEvent evt){
            updateParentVisibility(evt.getComponent());
        }
        @Override
        public void componentHidden(ComponentEvent evt){
            updateParentVisibility(evt.getComponent());
        }
        @Override
        public void componentAdded(ContainerEvent evt) {
            evt.getChild().addComponentListener(this);
            updateContainerVisibility(evt.getContainer());
        }
        @Override
        public void componentRemoved(ContainerEvent evt) {
            evt.getChild().removeComponentListener(this);
            updateContainerVisibility(evt.getContainer());
        }
    }
}