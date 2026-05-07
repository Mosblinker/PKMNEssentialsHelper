/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pkmn.essentials;

import components.ArrayListModel;
import components.JColorSelector;
import components.ListModelList;
import components.disable.DisableGUIInput;
import components.progress.JProgressDisplayMenu;
import components.text.CompoundUndoManager;
import components.text.action.commands.TextComponentCommands;
import components.text.action.commands.UndoManagerCommands;
import files.FilesExtended;
import files.extensions.ImageExtensions;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.JTextComponent;
import pkmn.essentials.colorchooser.*;
import pkmn.essentials.sprite.*;
import utils.SwingExtendedUtilities;

/**
 *
 * @author Mosblinker
 */
public class PkmnHelper extends javax.swing.JFrame implements DisableGUIInput{
    /**
     * This has the name of this program.
     */
    public static final String PROGRAM_NAME = "Pokemon Essentials Helper Tool";
    /**
     * This is the internal name for the program.
     */
    protected static final String INTERNAL_PROGRAM_NAME = "PKMNEssentialsHelper";
    /**
     * This is the pattern for the file handler to use for the log files of this 
     * program.
     */
    private static final String PROGRAM_LOG_PATTERN = 
            "%h/.mosblinker/logs/"+INTERNAL_PROGRAM_NAME+"-%u.%g.log";
    /**
     * This has the version information of this program.
     */
    public static final String VERSION_INFO = "0.1.0";
    /**
     * This is the name of the preference node used to store the settings for 
     * this program.
     */
    private static final String PREFERENCE_NODE_NAME = 
            "mosblinker/essentials/PkmnEssentialsHelper";
    
    protected static final String POKEMON_GRAPHICS_FOLDER = "Graphics"+File.separator+"Pokemon";
    
    protected static final String FRONT_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Front";
    
    protected static final String BACK_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Back";
    
    protected static final String FRONT_SHINY_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Front shiny";
    
    protected static final String BACK_SHINY_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Back shiny";
    
    protected static final String ICONS_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Icons";
    
    protected static final String ICONS_SHINY_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Icons shiny";
    
    protected static final String FOOTPRINTS_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Footprints";
    
    protected static final String SHADOW_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Shadow";
    
    protected static final String EGG_SPRITE_FOLDER = POKEMON_GRAPHICS_FOLDER+File.separator+"Eggs";
    
    protected static final String FEMALE_SPRITE_SUFFIX = "_female";
    
    protected static final String EGG_ICON_SPRITE_SUFFIX = "_icon";
    
    protected static final String EGG_HATCHING_SPRITE_SUFFIX = "_cracks";
    
    protected static final String PKMN_CRIES_FOLDER = "Audio"+File.separator+"SE"+File.separator+"Cries";
    
    protected static final String FAINTED_CRY_SUFFIX = "_fainted";
    
    protected static final String PKMN_CRY_FILE_EXTENSION = ".ogg";
    
    private static final long LIST_DOUBLE_CLICK_THRESHOLD = 500;
    
    private static final double[] SPRITE_SCALE_VALUES = {
        0.25,
        0.375,
        0.50,
        0.625,
        0.75,
        0.875,
        1,
        1.5,
        2,
        2.5,
        3,
        3.5,
        4
    };
    
    private static final Double[] DEFAULT_ZOOM_VALUES = {
        8.0, 4.0, 2.0, 1.0, 0.5, 0.25, 0.125
    };
    
    public static final String DROPPER_CURSOR_IMAGE = "/images/dropper.png";
    
    /**
     * This is a logger used to log messages for the program.
     */
    private static final Logger logger = 
            Logger.getLogger(INTERNAL_PROGRAM_NAME+"-"+VERSION_INFO);
    /**
     * This returns the logger used to log messages for this program.
     * @return The logger used to log message for the program.
     */
    public static Logger getLogger(){
        return logger;
    }
    /**
     * This adds the given commands to the given popup menu. This will also 
     * apply the commands to the text component that was registered with the 
     * text edit commands.
     * @param menu The popup menu to add the commands to.
     * @param undoCommands The undo commands to add to the popup menu.
     * @param editCommands The text edit commands to add to the popup menu.
     */
    public static void addToPopupMenu(JPopupMenu menu, 
            UndoManagerCommands undoCommands,TextComponentCommands editCommands){
        undoCommands.addToTextComponent(editCommands.getTextComponent());
        editCommands.addToTextComponent();
        menu.add(undoCommands.getUndoOrRedoAction());
        menu.addSeparator();
        menu.add(editCommands.getCutAction());
        menu.add(editCommands.getCopyAction());
        menu.add(editCommands.getPasteAction());
        menu.add(editCommands.getDeleteAction());
        menu.addSeparator();
        menu.add(editCommands.getSelectAllAction());
    }
    /**
     * Creates new form Helper
     */
    public PkmnHelper() {
            // This will get the preference node for the program
        Preferences node = null;
        try{    // Try to get the preference node used for the program
            node = Preferences.userRoot().node(PREFERENCE_NODE_NAME);
        } catch (SecurityException | IllegalStateException ex){
            getLogger().log(Level.SEVERE, "Unable to load preference node", ex);
            // TODO: Error message window
        }
        config = new PkmnHelperConfig(node);
        editCommands = new HashMap<>();
        undoCommands = new HashMap<>();
        textPopupMenus = new HashMap<>();
        Handler handler = new Handler();
        initComponents();
        progressDisplay.setUpdateEnabled(true);
        progressDisplay.setProgressDisplayed(true);
        progressBar.addChangeListener(progressDisplay);
        progressBar.addPropertyChangeListener(progressDisplay);
        for (JTextField field : new JTextField[]{spriteFolderField,spriteNameField,recolorSaveField}){
            JPopupMenu popup = new JPopupMenu();
            textPopupMenus.put(field, popup);
            TextComponentCommands textCmd = new TextComponentCommands(field);
            editCommands.put(field, textCmd);
            UndoManagerCommands undoCmd = new UndoManagerCommands(
                    new CompoundUndoManager(),false);
            undoCommands.put(field, undoCmd);
            addToPopupMenu(popup,undoCmd,textCmd);
            field.setComponentPopupMenu(popup);
        }
        pkmnSpritePanels = new PkmnSpriteNameListPanel[]{
            fSpritePanel,bSpritePanel,
            fsSpritePanel,bsSpritePanel,
            iSpritePanel,isSpritePanel
        };
        for (PkmnSpriteNameListPanel panel : pkmnSpritePanels)
            panel.addListDataListener(handler);
        recolorSpriteListPanel.addListDataListener(handler);
        recolorSpritePalette.addListDataListener(handler);
        
        recolorPaletteColorChooser = new PaletteColorChooserPanel();
        recolorReferencePaletteColorChooser = new PaletteColorChooserPanel("Reference Palette");
        recolorDropperColorChooser = new DropperColorChooserPanel();
        
        Image dropperCursorImage = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource(DROPPER_CURSOR_IMAGE));
        java.awt.Dimension dropperCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(128, 128);
        dropperCursorImage = dropperCursorImage.getScaledInstance(dropperCursorSize.width, 
                dropperCursorSize.height, Image.SCALE_AREA_AVERAGING);
        
        Cursor dropperCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                dropperCursorImage, new java.awt.Point(0,dropperCursorSize.height-1), 
                "dropper");
        recolorDropperColorChooser.setImageCursor(dropperCursor);
        
        recolorColorSelector.addChooserPanel(recolorPaletteColorChooser);
        recolorColorSelector.addChooserPanel(recolorReferencePaletteColorChooser);
        recolorColorSelector.addChooserPanel(recolorDropperColorChooser);
        recolorColorExchange.setColorSelector(recolorColorSelector);
        recolorBlendChange.setColorSelector(recolorColorSelector);
        
        blendPaletteColorChooser = new PaletteColorChooserPanel();
        blendReferencePaletteColorChooser = new PaletteColorChooserPanel("Reference Palette");
        blendDropperColorChooser = new DropperColorChooserPanel();
        blendDropperColorChooser.setImageCursor(dropperCursor);
        blendColorSelector.addChooserPanel(blendPaletteColorChooser);
        blendColorSelector.addChooserPanel(blendReferencePaletteColorChooser);
        blendColorSelector.addChooserPanel(blendDropperColorChooser);
        recolorBlendColorChooser = new BlendColorChooserPanel(blendColorSelector);
        recolorColorSelector.addChooserPanel(recolorBlendColorChooser);
        
        spriteFolderField.setText(config.getPkmnSpriteTargetFolder());
        spriteNameField.setText(config.getPkmnSpriteName());
        recolorSaveField.setText(config.getRecolorTargetFolder());
        spriteFolderField.getDocument().addDocumentListener(handler);
        spriteNameField.getDocument().addDocumentListener(handler);
        recolorSaveField.getDocument().addDocumentListener(handler);
        for (JFileChooser fc : new JFileChooser[]{
            spriteFC,spriteFolderFC,gameFolderFC,recolorFolderFC
        }){
            config.addFileChooser(fc, fc.getName());
            config.loadFileChooser(fc);
        }
        for (Component comp : new Component[]{
            recolorColorSelector,blendColorSelector,recolorColorExchange,recolorBlendChange
        }){
            config.getComponentNames().put(comp, comp.getName());
            SwingExtendedUtilities.setComponentSize(comp, config.getComponentSize(comp));
        }
        
        showHiddenFilesToggle.setSelected(config.getHiddenFilesAreShown());
        setFilesAreHidden(showHiddenFilesToggle.isSelected());
        progressDisplay.setDisplaySettings(config.getProgressDisplaySetting(progressDisplay.getDisplaySettings()));
        copySpritesToggle.setSelected(config.getPkmnSpritesAreCopied());
        config.getProgramBounds(PkmnHelper.this);
        recolorScaleSlider.setValue(config.getRecolorScaleIndex(recolorScaleSlider.getValue()));
        recolorColorExchange.setPreviewShown(config.getRecolorPreviewShown(recolorColorExchange));
        recolorBlendChange.setPreviewShown(config.getRecolorPreviewShown(recolorBlendChange));
        jTabbedPane1.setSelectedIndex(config.getSelectedTabIndex());
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        spriteFC = new javax.swing.JFileChooser();
        spritePreview = new components.JFileDisplayPanel();
        spriteFolderFC = new javax.swing.JFileChooser();
        gameFolderFC = new javax.swing.JFileChooser();
        recolorColorSelector = new components.JColorSelector();
        recolorColorExchange = new pkmn.essentials.PaletteColorExchangeDialog();
        blendColorSelector = new components.JColorSelector();
        recolorBlendChange = new pkmn.essentials.PaletteBlendChangeDialog();
        recolorSpriteListAccessory = new javax.swing.JPanel();
        recolorUndoButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(32767, 7));
        recolorRedoButton = new javax.swing.JButton();
        recolorFolderFC = new javax.swing.JFileChooser();
        scalePanelAccessory = new javax.swing.JPanel();
        scaleFrontToggle = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 7), new java.awt.Dimension(0, 7), new java.awt.Dimension(32767, 7));
        scaleBackToggle = new javax.swing.JToggleButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        fSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        bSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        fsSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        bsSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        iSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        isSpritePanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        footSpritePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        shadowSpritePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        eggSpritePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        eggIconSpritePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        eggCracksSpritePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        jLabel1 = new javax.swing.JLabel();
        spriteFolderField = new javax.swing.JTextField();
        copySpritesToggle = new javax.swing.JCheckBox();
        spriteFolderBrowseButton = new javax.swing.JButton();
        spriteApplyButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        spriteNameField = new javax.swing.JTextField();
        spriteResetButton = new javax.swing.JButton();
        spriteUseGameToggle = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        recolorScaleSlider = new javax.swing.JSlider();
        recolorScaleLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recolorPaletteList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        recolorColorExchangeButton = new javax.swing.JButton();
        recolorBlendChangeButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel16 = new javax.swing.JLabel();
        recolorSaveButton = new javax.swing.JButton();
        recolorBrowseButton = new javax.swing.JButton();
        recolorSaveField = new javax.swing.JTextField();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        recolorSpriteListPanel = new pkmn.essentials.sprite.PkmnSpriteNameListPanel();
        recolorReferencePanel = new pkmn.essentials.sprite.SinglePkmnSpriteNamePanel();
        progressBar = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        gameBrowseButton = new javax.swing.JButton();
        gameFolderField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        progressDisplay = new components.progress.JProgressDisplayMenu();
        showHiddenFilesToggle = new javax.swing.JCheckBoxMenuItem();
        debugMenu = new javax.swing.JMenu();
        inputEnabledTest = new javax.swing.JCheckBoxMenuItem();
        printTest = new javax.swing.JMenuItem();

        spriteFC.setAcceptAllFileFilterUsed(false);
        spriteFC.setAccessory(spritePreview);
        spriteFC.setFileFilter(ImageExtensions.PNG_FILTER);
        spriteFC.setName("spriteFC"); // NOI18N

        spritePreview.setFileChooser(spriteFC);

        spriteFolderFC.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        spriteFolderFC.setDialogTitle("Save Sprites To...");
        spriteFolderFC.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        spriteFolderFC.setName("spriteFolderFC"); // NOI18N

        gameFolderFC.setDialogTitle("Select Game Project Folder...");
        gameFolderFC.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        gameFolderFC.setName("gameFolderFC"); // NOI18N

        recolorColorSelector.setName("recolorColorSelector"); // NOI18N

        recolorColorExchange.setDialogTitle("Color Exchange");
        recolorColorExchange.setName("recolorColorExchange"); // NOI18N
        recolorColorExchange.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                recolorColorExchangeStateChanged(evt);
            }
        });
        recolorColorExchange.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                recolorColorExchangePropertyChange(evt);
            }
        });

        blendColorSelector.setName("blendColorSelector"); // NOI18N

        recolorBlendChange.setName("recolorBlendChange"); // NOI18N
        recolorBlendChange.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                recolorBlendChangeStateChanged(evt);
            }
        });
        recolorBlendChange.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                recolorBlendChangePropertyChange(evt);
            }
        });

        recolorSpriteListAccessory.setLayout(new javax.swing.BoxLayout(recolorSpriteListAccessory, javax.swing.BoxLayout.Y_AXIS));

        recolorUndoButton.setText("Undo");
        recolorUndoButton.setEnabled(false);
        recolorUndoButton.setMaximumSize(new java.awt.Dimension(100, 25));
        recolorUndoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorUndoButtonActionPerformed(evt);
            }
        });
        recolorSpriteListAccessory.add(recolorUndoButton);
        recolorSpriteListAccessory.add(filler4);

        recolorRedoButton.setText("Redo");
        recolorRedoButton.setEnabled(false);
        recolorRedoButton.setMaximumSize(new java.awt.Dimension(100, 25));
        recolorRedoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorRedoButtonActionPerformed(evt);
            }
        });
        recolorSpriteListAccessory.add(recolorRedoButton);

        recolorFolderFC.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        recolorFolderFC.setName("recolorFolderFC"); // NOI18N

        scalePanelAccessory.setLayout(new javax.swing.BoxLayout(scalePanelAccessory, javax.swing.BoxLayout.Y_AXIS));

        scaleFrontToggle.setText("Front Sprites");
        scaleFrontToggle.setEnabled(false);
        scalePanelAccessory.add(scaleFrontToggle);
        scalePanelAccessory.add(filler1);

        scaleBackToggle.setText("Back Sprites");
        scaleBackToggle.setEnabled(false);
        scaleBackToggle.setMaximumSize(new java.awt.Dimension(200, 25));
        scalePanelAccessory.add(scaleBackToggle);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pokemon Essentials Helper Tool");

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        fSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Front", fSpritePanel);

        bSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Back", bSpritePanel);

        fsSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fsSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Front Shiny", fsSpritePanel);

        bsSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Back Shiny", bsSpritePanel);

        iSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Icons", iSpritePanel);

        isSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSpritePanelActionPerformed(evt);
            }
        });
        jTabbedPane2.addTab("Icons Shiny", isSpritePanel);

        footSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                footSpritePanelActionPerformed(evt);
            }
        });
        footSpritePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                singleSpritePanelPropertyChange(evt);
            }
        });
        jTabbedPane2.addTab("Footprints", footSpritePanel);

        shadowSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowSpritePanelActionPerformed(evt);
            }
        });
        shadowSpritePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                singleSpritePanelPropertyChange(evt);
            }
        });
        jTabbedPane2.addTab("Shadow", shadowSpritePanel);

        eggSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eggSpritePanelActionPerformed(evt);
            }
        });
        eggSpritePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                singleSpritePanelPropertyChange(evt);
            }
        });
        jTabbedPane2.addTab("Egg", eggSpritePanel);

        eggIconSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eggIconSpritePanelActionPerformed(evt);
            }
        });
        eggIconSpritePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                singleSpritePanelPropertyChange(evt);
            }
        });
        jTabbedPane2.addTab("Egg Icon", eggIconSpritePanel);

        eggCracksSpritePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eggCracksSpritePanelActionPerformed(evt);
            }
        });
        eggCracksSpritePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                singleSpritePanelPropertyChange(evt);
            }
        });
        jTabbedPane2.addTab("Egg Hatching", eggCracksSpritePanel);

        jLabel1.setText("Target Folder:");

        copySpritesToggle.setText("Copy Files");
        copySpritesToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copySpritesToggleActionPerformed(evt);
            }
        });

        spriteFolderBrowseButton.setText("Browse");
        spriteFolderBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteFolderBrowseButtonActionPerformed(evt);
            }
        });

        spriteApplyButton.setText("Apply");
        spriteApplyButton.setEnabled(false);
        spriteApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteApplyButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Sprite Name:");

        spriteResetButton.setText("Reset");
        spriteResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteResetButtonActionPerformed(evt);
            }
        });

        spriteUseGameToggle.setText("Use Projject Folder");
        spriteUseGameToggle.setEnabled(false);
        spriteUseGameToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteUseGameToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spriteFolderField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spriteFolderBrowseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spriteUseGameToggle))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spriteNameField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(copySpritesToggle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spriteApplyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spriteResetButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spriteFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spriteFolderBrowseButton)
                    .addComponent(spriteUseGameToggle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spriteApplyButton)
                    .addComponent(copySpritesToggle)
                    .addComponent(jLabel2)
                    .addComponent(spriteNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spriteResetButton))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pokemon", jPanel1);

        recolorScaleSlider.setMajorTickSpacing(1);
        recolorScaleSlider.setMaximum(12);
        recolorScaleSlider.setPaintTicks(true);
        recolorScaleSlider.setSnapToTicks(true);
        recolorScaleSlider.setToolTipText("Scale");
        recolorScaleSlider.setValue(6);
        recolorScaleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                recolorScaleSliderStateChanged(evt);
            }
        });

        recolorScaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        recolorScaleLabel.setText("100%");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Color Palette"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        recolorPaletteList.setModel(recolorSpritePalette);
        recolorPaletteList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        recolorPaletteList.setCellRenderer(new ColorCellRenderer());
        recolorPaletteList.setFixedCellHeight(20);
        recolorPaletteList.setFixedCellWidth(20);
        recolorPaletteList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        recolorPaletteList.setVisibleRowCount(-1);
        recolorPaletteList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recolorPaletteListMouseClicked(evt);
            }
        });
        recolorPaletteList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                recolorPaletteListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(recolorPaletteList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridLayout(0, 1, 0, 6));

        recolorColorExchangeButton.setText("Color Exchange");
        recolorColorExchangeButton.setEnabled(false);
        recolorColorExchangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorColorExchangeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(recolorColorExchangeButton);

        recolorBlendChangeButton.setText("Color Blend Exchange");
        recolorBlendChangeButton.setEnabled(false);
        recolorBlendChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorBlendChangeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(recolorBlendChangeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 11, 10);
        jPanel3.add(jPanel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        jPanel3.add(filler2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        jPanel3.add(filler3, gridBagConstraints);

        jLabel16.setText("Save To:");

        recolorSaveButton.setText("Save");
        recolorSaveButton.setEnabled(false);
        recolorSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorSaveButtonActionPerformed(evt);
            }
        });

        recolorBrowseButton.setText("Browse");
        recolorBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorBrowseButtonActionPerformed(evt);
            }
        });

        recolorSpriteListPanel.setMoveButtonsAreShown(false);
        recolorSpriteListPanel.setReverseButtonIsShown(false);
        recolorSpriteListPanel.setSideAccessory(recolorSpriteListAccessory);
        recolorSpriteListPanel.setDuplicateButtonShown(false);
        recolorSpriteListPanel.setFemaleToggleShown(false);
        recolorSpriteListPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorSpriteListPanelActionPerformed(evt);
            }
        });
        jTabbedPane3.addTab("Sprites", recolorSpriteListPanel);

        recolorReferencePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 10, 11, 10));
        recolorReferencePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recolorReferencePanelActionPerformed(evt);
            }
        });
        recolorReferencePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                recolorReferencePanelPropertyChange(evt);
            }
        });
        jTabbedPane3.addTab("Reference", recolorReferencePanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recolorSaveField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recolorBrowseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recolorSaveButton))
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(recolorScaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recolorScaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addComponent(jTabbedPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(recolorBrowseButton)
                        .addComponent(recolorSaveField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(recolorSaveButton))
                    .addComponent(recolorScaleSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recolorScaleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Recolor Sprite", jPanel2);

        jLabel3.setText("Game Project Folder:");

        gameBrowseButton.setText("Browse");
        gameBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameBrowseButtonActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Options");

        progressDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressDisplayActionPerformed(evt);
            }
        });
        jMenu2.add(progressDisplay);

        showHiddenFilesToggle.setSelected(true);
        showHiddenFilesToggle.setText("Show Hidden Files");
        showHiddenFilesToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHiddenFilesToggleActionPerformed(evt);
            }
        });
        jMenu2.add(showHiddenFilesToggle);

        jMenuBar1.add(jMenu2);

        debugMenu.setText("Debug");

        inputEnabledTest.setSelected(true);
        inputEnabledTest.setText("Input Enabled");
        inputEnabledTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputEnabledTestActionPerformed(evt);
            }
        });
        debugMenu.add(inputEnabledTest);

        printTest.setText("Print Data");
        printTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTestActionPerformed(evt);
            }
        });
        debugMenu.add(printTest);

        jMenuBar1.add(debugMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gameFolderField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gameBrowseButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(gameBrowseButton)
                    .addComponent(gameFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void footSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_footSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(0);
        }
    }//GEN-LAST:event_footSpritePanelActionPerformed

    private void bsSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(3);
        }
    }//GEN-LAST:event_bsSpritePanelActionPerformed

    private void fsSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fsSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(2);
        }
    }//GEN-LAST:event_fsSpritePanelActionPerformed

    private void bSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(1);
        }
    }//GEN-LAST:event_bSpritePanelActionPerformed

    private void fSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(0);
        }
    }//GEN-LAST:event_fSpritePanelActionPerformed

    private void spriteApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteApplyButtonActionPerformed
        File folder = new File(spriteFolderField.getText());
        String name = spriteNameField.getText();
        new SpriteRenamer(folder,name).execute();
    }//GEN-LAST:event_spriteApplyButtonActionPerformed

    private void spriteFolderBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteFolderBrowseButtonActionPerformed
        if (spriteFolderFC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            spriteFolderField.setText(spriteFolderFC.getSelectedFile().toString());
        }
        spriteFolderFC.setPreferredSize(spriteFolderFC.getSize());
            // Set the file chooser's size in the config if it's saved
        config.storeFileChooser(spriteFolderFC);
    }//GEN-LAST:event_spriteFolderBrowseButtonActionPerformed

    private void iSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(4);
        }
    }//GEN-LAST:event_iSpritePanelActionPerformed

    private void isSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(5);
        }
    }//GEN-LAST:event_isSpritePanelActionPerformed

    private void spriteResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteResetButtonActionPerformed
        for (PkmnSpriteNameListPanel panel : pkmnSpritePanels)
            panel.clear();
        footSpritePanel.setIcon(null);
        shadowSpritePanel.setIcon(null);
        eggSpritePanel.setIcon(null);
        eggIconSpritePanel.setIcon(null);
        eggCracksSpritePanel.setIcon(null);
        spriteFolderField.setText("");
        spriteNameField.setText("");
    }//GEN-LAST:event_spriteResetButtonActionPerformed

    private void progressDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progressDisplayActionPerformed
        progressDisplay.updateProgressString(progressBar);
        if (progressDisplay.isProgressDisplayed() && config != null){
            config.setProgressDisplaySetting(progressDisplay.getDisplaySettings());
        }
    }//GEN-LAST:event_progressDisplayActionPerformed

    private void inputEnabledTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputEnabledTestActionPerformed
        setInputEnabled(inputEnabledTest.isSelected());
    }//GEN-LAST:event_inputEnabledTestActionPerformed

    private void printTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_printTestActionPerformed

    private void showHiddenFilesToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHiddenFilesToggleActionPerformed
        config.setHiddenFilesAreShown(showHiddenFilesToggle.isSelected());
        setFilesAreHidden(showHiddenFilesToggle.isSelected());
    }//GEN-LAST:event_showHiddenFilesToggleActionPerformed

    private void copySpritesToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copySpritesToggleActionPerformed
        config.setPkmnSpritesAreCopied(copySpritesToggle.isSelected());
    }//GEN-LAST:event_copySpritesToggleActionPerformed

    private void singleSpritePanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_singleSpritePanelPropertyChange
        switch(evt.getPropertyName()){
            case(SinglePkmnSpriteNamePanel.ICON_PROPERTY_CHANGED):
                updateSpriteApplyButton();
        }
    }//GEN-LAST:event_singleSpritePanelPropertyChange

    private void gameBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameBrowseButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gameBrowseButtonActionPerformed

    private void spriteUseGameToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteUseGameToggleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spriteUseGameToggleActionPerformed
    
    private void recolorScaleSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_recolorScaleSliderStateChanged
        int index = recolorScaleSlider.getValue();
        double value = SPRITE_SCALE_VALUES[index];
        recolorScaleLabel.setText((value*100)+"%");
        recolorSpriteListPanel.setScale(value);
        config.setRecolorScaleIndex(index);
        recolorReferencePanel.setScale(value);
    }//GEN-LAST:event_recolorScaleSliderStateChanged

    private void recolorColorExchangeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorColorExchangeButtonActionPerformed
        ListModelList<PkmnSpriteIcon> icons = recolorSpriteListPanel.getModelList();
        List<PalettePkmnSpriteIcon> preview = recolorColorExchange.setSprites(icons);
        recolorPaletteColorChooser.setPalette(recolorSpritePalette);
        updateRecolorDropperImages();
        for (PalettePkmnSpriteIcon icon : preview){
            recolorDropperColorChooser.getImages().add(icon.getBufferedImage());
        }
        recolorSpriteListPanel.setModel(new ListModelList<>());
        if (recolorColorExchange.isPreviewShown()){
            setShownRecolorSprites(0);
        }
        blendPaletteColorChooser.setPalette(recolorSpritePalette);
        blendDropperColorChooser.setImages(recolorDropperColorChooser.getImages());
        int option = recolorColorExchange.showDialog(this, recolorSpritePalette);
        recolorSpriteListPanel.setModel(icons);
        if (option == PaletteColorExchangeDialog.ACCEPT_OPTION){
            recolorChangeColorsUpdate(recolorColorExchange.getPaletteChanges());
        }
        config.setComponentSize(recolorColorExchange);
        config.setComponentSize(recolorColorSelector);
        config.setComponentSize(blendColorSelector);
    }//GEN-LAST:event_recolorColorExchangeButtonActionPerformed

    private void recolorUndoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorUndoButtonActionPerformed
        recolorUndoOrRedo(recolorSpriteUndo,recolorSpriteRedo);
    }//GEN-LAST:event_recolorUndoButtonActionPerformed

    private void recolorRedoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorRedoButtonActionPerformed
        recolorUndoOrRedo(recolorSpriteRedo,recolorSpriteUndo);
    }//GEN-LAST:event_recolorRedoButtonActionPerformed

    private void recolorPaletteListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recolorPaletteListMouseClicked
        int selected = recolorPaletteList.locationToIndex(evt.getPoint());
        if (selected >= 0 && !recolorPaletteList.getCellBounds(selected, selected).contains(evt.getPoint()))
            selected = -1;
        if (selected >= 0 && (evt.getClickCount() >= 2 || (selected == recolorClickedIndex && 
                (evt.getWhen()-recolorClickedMillis) <= LIST_DOUBLE_CLICK_THRESHOLD))){
            recolorClickedIndex = -1;
            Color oldColor = recolorSpritePalette.get(selected);
            recolorPaletteColorChooser.setPalette(recolorSpritePalette);
            updateRecolorDropperImages();
            blendPaletteColorChooser.setPalette(recolorSpritePalette);
            blendDropperColorChooser.setImages(recolorDropperColorChooser.getImages());
            if (recolorColorSelector.showDialog(this, oldColor) == JColorSelector.ACCEPT_OPTION){
                recolorChangeColor(selected,recolorColorSelector.getSelectedColor());
            }
            config.setComponentSize(recolorColorSelector);
            config.setComponentSize(blendColorSelector);
        } else {
            recolorClickedIndex = selected;
            recolorClickedMillis = evt.getWhen();
        }
    }//GEN-LAST:event_recolorPaletteListMouseClicked

    private void recolorPaletteListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_recolorPaletteListValueChanged
        
    }//GEN-LAST:event_recolorPaletteListValueChanged

    private void recolorSpriteListPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorSpriteListPanelActionPerformed
        switch(evt.getActionCommand()){
            case(PkmnSpriteNameListPanel.BROWSE_ACTION_COMMAND):
                browseMultipleSprites(6);
                break;
            case(PkmnSpriteNameListPanel.CLEAR_ACTION_COMMAND):
                recolorSpritePalette.clear();
                recolorSpriteUndo.clear();
                recolorSpriteRedo.clear();
        }
    }//GEN-LAST:event_recolorSpriteListPanelActionPerformed

    private void recolorColorExchangeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_recolorColorExchangeStateChanged
        if (recolorColorExchange.isPreviewShown())
            recolorSpriteListPanel.repaint();
    }//GEN-LAST:event_recolorColorExchangeStateChanged
    
    private void setShownRecolorSprites(int mode){
        recolorSpriteListPanel.getModelList().clear();
        switch(mode){
            case(0):
                recolorSpriteListPanel.getModelList().addAll(
                        (recolorColorExchange.isPreviewShown())?
                                recolorColorExchange.getPreviewSprites():
                                recolorColorExchange.getSprites());
                break;
            case(1):
                recolorSpriteListPanel.getModelList().addAll(
                        (recolorBlendChange.isPreviewShown())?
                                recolorBlendChange.getPreviewSprites():
                                recolorBlendChange.getSprites());
        }
    }
    
    private void recolorColorExchangePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_recolorColorExchangePropertyChange
        if ("previewShown".equals(evt.getPropertyName())){
            setShownRecolorSprites(0);
            config.setRecolorPreviewShown(recolorColorExchange, recolorColorExchange.isPreviewShown());
        }
    }//GEN-LAST:event_recolorColorExchangePropertyChange

    private void shadowSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shadowSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(1);
        }
    }//GEN-LAST:event_shadowSpritePanelActionPerformed

    private void eggSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eggSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(2);
        }
    }//GEN-LAST:event_eggSpritePanelActionPerformed

    private void eggIconSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eggIconSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(3);
        }
    }//GEN-LAST:event_eggIconSpritePanelActionPerformed

    private void eggCracksSpritePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eggCracksSpritePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(4);
        }
    }//GEN-LAST:event_eggCracksSpritePanelActionPerformed

    private void recolorBlendChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorBlendChangeButtonActionPerformed
        ListModelList<PkmnSpriteIcon> icons = recolorSpriteListPanel.getModelList();
        List<PalettePkmnSpriteIcon> preview = recolorBlendChange.setSprites(icons);
        recolorPaletteColorChooser.setPalette(recolorSpritePalette);
        updateRecolorDropperImages();
        for (PalettePkmnSpriteIcon icon : preview){
            recolorDropperColorChooser.getImages().add(icon.getBufferedImage());
        }
        recolorSpriteListPanel.setModel(new ListModelList<>());
        if (recolorBlendChange.isPreviewShown()){
            setShownRecolorSprites(1);
        }
        blendPaletteColorChooser.setPalette(recolorSpritePalette);
        blendDropperColorChooser.setImages(recolorDropperColorChooser.getImages());
        int option = recolorBlendChange.showDialog(this, recolorSpritePalette);
        recolorSpriteListPanel.setModel(icons);
        if (option == PaletteColorExchangeDialog.ACCEPT_OPTION){
            recolorChangeColorsUpdate(recolorBlendChange.getPaletteChanges());
        }
        config.setComponentSize(recolorBlendChange);
        config.setComponentSize(recolorColorSelector);
        config.setComponentSize(blendColorSelector);
    }//GEN-LAST:event_recolorBlendChangeButtonActionPerformed

    private void recolorBlendChangeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_recolorBlendChangeStateChanged
        if (recolorBlendChange.isPreviewShown())
            recolorSpriteListPanel.repaint();
    }//GEN-LAST:event_recolorBlendChangeStateChanged

    private void recolorBlendChangePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_recolorBlendChangePropertyChange
        if ("previewShown".equals(evt.getPropertyName())){
            setShownRecolorSprites(1);
            config.setRecolorPreviewShown(recolorBlendChange, recolorBlendChange.isPreviewShown());
        }
    }//GEN-LAST:event_recolorBlendChangePropertyChange

    private void recolorSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorSaveButtonActionPerformed
        File folder = new File(recolorSaveField.getText());
        new RecolorSpriteSaver(folder).execute();
    }//GEN-LAST:event_recolorSaveButtonActionPerformed

    private void recolorBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorBrowseButtonActionPerformed
        if (recolorFolderFC.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            recolorSaveField.setText(recolorFolderFC.getSelectedFile().toString());
        }
        recolorFolderFC.setPreferredSize(recolorFolderFC.getSize());
            // Set the file chooser's size in the config if it's saved
        config.storeFileChooser(recolorFolderFC);
    }//GEN-LAST:event_recolorBrowseButtonActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        config.setSelectedTabIndex(jTabbedPane1.getSelectedIndex());
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void recolorReferencePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recolorReferencePanelActionPerformed
        switch(evt.getActionCommand()){
            case(SinglePkmnSpriteNamePanel.BROWSE_ACTION_COMMAND):
                browseSingleSprite(5);
                break;
            case(PkmnSpriteNameListPanel.CLEAR_ACTION_COMMAND):
                recolorReferencePaletteColorChooser.getPalette().clear();
                blendReferencePaletteColorChooser.getPalette().clear();
        }
    }//GEN-LAST:event_recolorReferencePanelActionPerformed

    private void recolorReferencePanelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_recolorReferencePanelPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_recolorReferencePanelPropertyChange
    
    private void setFilesAreHidden(boolean value){
        spriteFC.setFileHidingEnabled(!value);
        spriteFolderFC.setFileHidingEnabled(!value);
    }
    
    private String getSpriteName(String name, PkmnSpriteIcon icon){
        if (icon.getIndex() > 0)
            name += "_"+icon.getIndex();
        if (icon.isFemale())
            name += FEMALE_SPRITE_SUFFIX;
        return name;
    }
    
    private File renameSprite(File source, File target, boolean copy) throws IOException{
        Path path = null;
        Path sPath = source.toPath();
        Path tPath = target.toPath();
        try{
            path = moveOrCopy(sPath,tPath,copy,false);
        } catch (FileAlreadyExistsException ex){
            if (JOptionPane.showConfirmDialog(this, "\""+target+"\"\n"
                    + "already exists. Overwrite it?", "File Already Exists", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 
                    JOptionPane.YES_OPTION){
                path = moveOrCopy(sPath,tPath,copySpritesToggle.isSelected(),true);
            }
        }
        return (path != null) ? path.toFile() : source;
    }
    
    private boolean shouldCopyFile(Path path1, Path path2){
        try{
            return Files.isSameFile(path1, path2);
        } catch (IOException ex){
            getLogger().log(Level.WARNING, "Error occurred while checking files", ex);
            return true;
        }
    }
    
    private Path moveOrCopy(Path source, Path target, boolean copy, boolean replace) throws IOException{
        if (copy){
            if (replace)
                return Files.copy(source, target, 
                        StandardCopyOption.COPY_ATTRIBUTES, 
                        StandardCopyOption.REPLACE_EXISTING);
            else
                return Files.copy(source, target, 
                        StandardCopyOption.COPY_ATTRIBUTES);
        } else if (replace){
            return Files.move(source, target, 
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return Files.move(source, target);
    }
    
    private BufferedImage loadSpriteImage(File file){
        BufferedImage image = null;
        try{
            image = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(PkmnHelper.class.getName()).log(Level.INFO, null, ex);
        }
        if (image == null){
            JOptionPane.showMessageDialog(this, 
                    "Failed to load file\n\""+file.getName()+"\"", 
                     "Failed to Load Image", JOptionPane.WARNING_MESSAGE);
        }
        return image;
    }
        
    private PkmnSpriteIcon loadSprite(File file){
        BufferedImage image = loadSpriteImage(file);
        if (image == null){
            return null;
        }
        return new PkmnSpriteIcon(file,image);
    }
    
    private PalettePkmnSpriteIcon loadPaletteSprite(File file, Collection<Color> palette){
        BufferedImage image = loadSpriteImage(file);
        if (image == null){
            return null;
        }
        PalettePkmnSpriteIcon icon = new PalettePkmnSpriteIcon(file,image,palette);
        if (palette != null)
            palette.addAll(icon.getPalette());
        return icon;
    }
    
    private int browseSprites(String title, boolean multiSelection){
        spriteFC.setMultiSelectionEnabled(multiSelection);
        spriteFC.setDialogTitle(title);
        int option;
        boolean noFile;
        do{
            noFile = false;
            option = spriteFC.showOpenDialog(this);
            spriteFC.setPreferredSize(spriteFC.getSize());
                // Set the file chooser's size in the config if it's saved
            config.storeFileChooser(spriteFC);
            if (option == JFileChooser.APPROVE_OPTION){
                for (File file : spriteFC.getSelectedFiles()){
                    if (!file.exists()){
                        JOptionPane.showMessageDialog(this, 
                            "\""+file.getName()+"\"\nFile not found.\n"
                                    + "Check the file name and try again.", 
                            "File Not Found", JOptionPane.WARNING_MESSAGE);
                        noFile = true;
                        break;
                    }
                }
            }
        } while (option == JFileChooser.APPROVE_OPTION && noFile);
        return option;
    }
    
    private Map<Integer,Color> recolorChangeColors(Map<Integer,Color> changes){
        for (PkmnSpriteIcon icon : recolorSpriteListPanel.getModelList()){
            ((PalettePkmnSpriteIcon) icon).changeColors(changes);
        }
        Map<Integer,Color> inverse = new HashMap<>();
        for (Map.Entry<Integer,Color> entry : changes.entrySet()){
            inverse.put(entry.getKey(), recolorSpritePalette.set(entry.getKey(), entry.getValue()));
        }
        return inverse;
    }
    
    private void recolorChangeColorsUpdate(Map<Integer,Color> changes){
        recolorSpriteUndo.push(recolorChangeColors(changes));
        recolorSpriteRedo.clear();
        recolorSpriteListPanel.repaint();
        updateRecolorUndoRedoButtons();
    }
    
    private void recolorChangeColor(int index, Color color){
        Color oldColor = recolorSpritePalette.get(index);
        if (color.equals(oldColor))
            return;
        recolorSpritePalette.set(index, color);
        Map<Integer,Color> replacement = Collections.singletonMap(index, oldColor);
        recolorSpriteUndo.push(replacement);
        recolorSpriteRedo.clear();
        for (PkmnSpriteIcon icon : recolorSpriteListPanel.getModelList()){
            ((PalettePkmnSpriteIcon)icon).getPalette().set(index, color);
        }
        recolorSpriteListPanel.repaint();
        updateRecolorUndoRedoButtons();
    }
    
    private void recolorUndoOrRedo(ArrayDeque<Map<Integer,Color>> source, ArrayDeque<Map<Integer,Color>> target){
        if (source.isEmpty())
            return;
        target.push(recolorChangeColors(source.pop()));
        recolorSpriteListPanel.repaint();
        updateRecolorUndoRedoButtons();
    }
    
    private void updateRecolorDropperImages(){
        List<BufferedImage> images = new ArrayList<>();
        for (PkmnSpriteIcon icon : recolorSpriteListPanel.getModelList()){
            images.add(((PalettePkmnSpriteIcon)icon).getBufferedImage());
        }
        if (recolorReferencePanel.getIcon() != null)
            images.add(((PalettePkmnSpriteIcon)recolorReferencePanel.getIcon()).getBufferedImage());
        int index = recolorDropperColorChooser.getImageIndex();
        double scale = recolorDropperColorChooser.getScale();
        recolorDropperColorChooser.setImages(images);
        recolorDropperColorChooser.setImageIndex(Math.min(Math.max(index, 0), images.size()-1));
        recolorDropperColorChooser.setScale(scale);
    }
    
    private void browseSingleSprite(int mode){
        String title = null;
        switch (mode){
            case(0):
                title = "Select Footprint Sprite";
                break;
            case(1):
                title = "Select Shadow Sprite";
                break;
            case(2):
                title = "Select Egg Sprite";
                break;
            case(3):
                title = "Select Egg Icon";
                break;
            case(4):
                title = "Select Egg Hatching Graphics";
                break;
            case(5):
                title = "Select Reference";
        }
        if (browseSprites(title,false) == JFileChooser.APPROVE_OPTION){
            File file = spriteFC.getSelectedFile();
            PkmnSpriteIcon icon;
            switch(mode){
                case(5):
                    icon = loadPaletteSprite(file, null);
                    break;
                default:
                    icon = loadSprite(file);
            }
            if (icon != null){
                switch (mode){
                    case(0):
                        footSpritePanel.setIcon(icon);
                        updateSpriteApplyButton();
                        break;
                    case(1):
                        shadowSpritePanel.setIcon(icon);
                        updateSpriteApplyButton();
                        break;
                    case(2):
                        eggSpritePanel.setIcon(icon);
                        updateSpriteApplyButton();
                        break;
                    case(3):
                        eggIconSpritePanel.setIcon(icon);
                        updateSpriteApplyButton();
                        break;
                    case(4):
                        eggCracksSpritePanel.setIcon(icon);
                        updateSpriteApplyButton();
                        break;
                    case(5):
                        recolorReferencePanel.setIcon(icon);
                        List<Color> palette = ((PalettePkmnSpriteIcon)icon).getPalette();
                        recolorReferencePaletteColorChooser.setPalette(palette);
                        blendReferencePaletteColorChooser.setPalette(palette);
                        break;
                }
            }
        }
    }
    
    private void browseMultipleSprites(int mode){
        String title;
        List<PkmnSpriteIcon> list;
        switch (mode){
            case(0):
                title = "Select Front Sprites";
                list = fSpritePanel.getModelList();
                break;
            case(1):
                title = "Select Back Sprites";
                list = bSpritePanel.getModelList();
                break;
            case(2):
                title = "Select Shiny Front Sprites";
                list = fsSpritePanel.getModelList();
                break;
            case(3):
                title = "Select Shiny Back Sprites"; 
                list = bsSpritePanel.getModelList();
                break;
            case(4):
                title = "Select Icons Sprites"; 
                list = iSpritePanel.getModelList();
                break;
            case(5):
                title = "Select Shiny Icons Sprites"; 
                list = isSpritePanel.getModelList();
                break;
            case(6):
                title = "Select Sprites To Recolor";
                list = recolorSpriteListPanel.getModelList();
                break;
            default:
                return;
        }
        if (list == null)
            return;
        if (browseSprites(title,true) == JFileChooser.APPROVE_OPTION){
            File[] files = spriteFC.getSelectedFiles();
            Arrays.sort(files, fileNameComparator);
            Set<Color> palette;
            switch (mode){
                case(0):
                case(1):
                case(2):
                case(3):
                case(4):
                case(5):
                    for (File file : files){
                        PkmnSpriteIcon icon = loadSprite(file);
                        if (icon != null){
                            list.add(icon);
                        }
                    }
                    updateSpriteApplyButton();
                    break;
                case(6):
                    palette = new LinkedHashSet<>(recolorSpritePalette);
                    for (File file : files){
                        PalettePkmnSpriteIcon icon = loadPaletteSprite(file,palette);
                        if (icon != null){
                            list.add(icon);
                        }
                    }
                    for (PkmnSpriteIcon icon : list){
                        ((PalettePkmnSpriteIcon)icon).addAbsentColors(palette);
                    }
                    palette.removeAll(recolorSpritePalette);
                    recolorSpritePalette.addAll(palette);
                    
//                    recolorSpriteUndo.clear();
//                    recolorSpriteRedo.clear();
                    updateRecolorUndoRedoButtons();
            }
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
            // Set the logger's level to the lowest level in order to log all
        getLogger().setLevel(Level.FINEST);
        try {   // Get the parent file for the log file
            File file = new File(PROGRAM_LOG_PATTERN.replace("%h", 
                    System.getProperty("user.home"))
                    .replace('/', File.separatorChar)).getParentFile();
                // If the parent of the log file doesn't exist
            if (!file.exists()){
                try{    // Try to create the directories for the log file
                    Files.createDirectories(file.toPath());
                } catch (IOException ex){
                    getLogger().log(Level.WARNING, 
                            "Failed to create directories for log file", ex);
                }
            }   // Add a file handler to log messages to a log file
            getLogger().addHandler(new java.util.logging.FileHandler(
                    PROGRAM_LOG_PATTERN,0,16));
        } catch (IOException | SecurityException ex) {
            getLogger().log(Level.SEVERE, "Failed to get log file", ex);
        }   // Log the user's OS name
        getLogger().log(Level.CONFIG, "OS: {0}, version: {1}, arch: {2}", new Object[]{
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch")});
            // Log the Java vendor name and url
        getLogger().log(Level.CONFIG, "Java vendor: {0}, URL: {1}", new Object[]{
                System.getProperty("java.vendor"),
                System.getProperty("java.vendor.url")});
            // Log the Java version
        getLogger().log(Level.CONFIG, "Java version: {0}", 
                System.getProperty("java.version"));
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            getLogger().log(Level.SEVERE, "Failed to load Nimbus LnF", ex);
        }
        //</editor-fold>
            // If there is no look and feel set
        if (UIManager.getLookAndFeel() == null)
            getLogger().log(Level.CONFIG, "Look and Feel: null");
        else    // Log the current Look and Feel
            getLogger().log(Level.CONFIG, "Look and Feel: {0}",
                    UIManager.getLookAndFeel().getName());

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PkmnHelper().setVisible(true);
        });
    }
    /**
     * 
     */
    private void updateSpriteApplyButton(){
        boolean enabled = inputEnabled && spriteFolderField.getText() != null && 
                !spriteFolderField.getText().isBlank() && 
                spriteNameField.getText() != null && 
                !spriteNameField.getText().isBlank();
        if (enabled){
            for (PkmnSpriteNameListPanel panel : pkmnSpritePanels){
                enabled = !panel.getModelList().isEmpty();
                if (enabled)
                    break;
            }
            if (!enabled){
                for (SinglePkmnSpriteNamePanel panel : new SinglePkmnSpriteNamePanel[]{
                    footSpritePanel,shadowSpritePanel,eggSpritePanel,eggIconSpritePanel,eggCracksSpritePanel
                }) {
                    enabled = panel != null;
                    if (enabled)
                        break;
                }
            }
        }
        spriteApplyButton.setEnabled(enabled);
    }
    
    private void updateRecolorPaletteButtons(){
        recolorColorExchangeButton.setEnabled(inputEnabled && !recolorSpritePalette.isEmpty());
        recolorBlendChangeButton.setEnabled(recolorColorExchangeButton.isEnabled());
        updateRecolorSaveButton();
    }
    
    private void updateRecolorUndoRedoButtons(){
        recolorUndoButton.setEnabled(inputEnabled && !recolorSpriteUndo.isEmpty());
        recolorRedoButton.setEnabled(inputEnabled && !recolorSpriteRedo.isEmpty());
    }
    
    private void updateRecolorSaveButton(){
        recolorSaveButton.setEnabled(inputEnabled && 
                recolorSaveField.getText() != null && 
                !recolorSaveField.getText().isEmpty() && 
                !recolorSpriteListPanel.getModelList().isEmpty());
    }
    @Override
    public void setInputEnabled(boolean enabled) {
        inputEnabled = enabled;
        updateSpriteApplyButton();
        spriteNameField.setEnabled(enabled);
        spriteFolderField.setEnabled(enabled);
        copySpritesToggle.setEnabled(enabled);
        spriteFolderBrowseButton.setEnabled(enabled);
        spriteResetButton.setEnabled(enabled);
        for (PkmnSpriteNameListPanel panel : pkmnSpritePanels){
            panel.setEnabled(enabled);
        }
        footSpritePanel.setEnabled(enabled);
        shadowSpritePanel.setEnabled(enabled);
        eggSpritePanel.setEnabled(enabled);
        eggIconSpritePanel.setEnabled(enabled);
        eggCracksSpritePanel.setEnabled(enabled);
        inputEnabledTest.setSelected(enabled);
        recolorSpriteListPanel.setEnabled(enabled);
        updateRecolorPaletteButtons();
        updateRecolorUndoRedoButtons();
        updateRecolorSaveButton();
        recolorBrowseButton.setEnabled(enabled);
        recolorSaveField.setEnabled(enabled);
    }
    @Override
    public boolean isInputEnabled() {
        return inputEnabled;
    }
    @Override
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    @Override
    public JProgressDisplayMenu getProgressDisplayMenu() {
        return progressDisplay;
    }
    @Override
    public void useWaitCursor(boolean isWaiting) {
        setCursor((isWaiting)?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):null);
    }

    /**
     * This is a map that maps the text components to their text edit commands.
     */
    private Map<JTextComponent, TextComponentCommands> editCommands;
    /**
     * This is a map that maps the text components to their undo commands.
     */
    private Map<JTextComponent, UndoManagerCommands> undoCommands;
    /**
     * This is a map that maps the text components to their respective popup 
     * menus.
     */
    private Map<JTextComponent, JPopupMenu> textPopupMenus;
    /**
     * This is used to store and manage the configuration for this program.
     */
    private PkmnHelperConfig config;
    
    private boolean inputEnabled = true;
    
    private FileNameComparator fileNameComparator = new FileNameComparator();
    
    private PkmnSpriteNameListPanel[] pkmnSpritePanels;
    
    private ArrayListModel<Color> recolorSpritePalette = new ArrayListModel<>();
    
    private ArrayDeque<Map<Integer,Color>> recolorSpriteUndo = new ArrayDeque<>();
    
    private ArrayDeque<Map<Integer,Color>> recolorSpriteRedo = new ArrayDeque<>();
    
    private long recolorClickedMillis = 0L;
    
    private int recolorClickedIndex = -1;
    
    private PaletteColorChooserPanel recolorPaletteColorChooser;
    
    private DropperColorChooserPanel recolorDropperColorChooser;
    
    private PaletteColorChooserPanel recolorReferencePaletteColorChooser;
    
    private PaletteColorChooserPanel blendPaletteColorChooser;
    
    private DropperColorChooserPanel blendDropperColorChooser;
    
    private PaletteColorChooserPanel blendReferencePaletteColorChooser;
    
    private BlendColorChooserPanel recolorBlendColorChooser;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel bSpritePanel;
    private components.JColorSelector blendColorSelector;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel bsSpritePanel;
    private javax.swing.JCheckBox copySpritesToggle;
    private javax.swing.JMenu debugMenu;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel eggCracksSpritePanel;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel eggIconSpritePanel;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel eggSpritePanel;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel fSpritePanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel footSpritePanel;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel fsSpritePanel;
    private javax.swing.JButton gameBrowseButton;
    private javax.swing.JFileChooser gameFolderFC;
    private javax.swing.JTextField gameFolderField;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel iSpritePanel;
    private javax.swing.JCheckBoxMenuItem inputEnabledTest;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel isSpritePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JMenuItem printTest;
    private javax.swing.JProgressBar progressBar;
    private components.progress.JProgressDisplayMenu progressDisplay;
    private pkmn.essentials.PaletteBlendChangeDialog recolorBlendChange;
    private javax.swing.JButton recolorBlendChangeButton;
    private javax.swing.JButton recolorBrowseButton;
    private pkmn.essentials.PaletteColorExchangeDialog recolorColorExchange;
    private javax.swing.JButton recolorColorExchangeButton;
    private components.JColorSelector recolorColorSelector;
    private javax.swing.JFileChooser recolorFolderFC;
    private javax.swing.JList<Color> recolorPaletteList;
    private javax.swing.JButton recolorRedoButton;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel recolorReferencePanel;
    private javax.swing.JButton recolorSaveButton;
    private javax.swing.JTextField recolorSaveField;
    private javax.swing.JLabel recolorScaleLabel;
    private javax.swing.JSlider recolorScaleSlider;
    private javax.swing.JPanel recolorSpriteListAccessory;
    private pkmn.essentials.sprite.PkmnSpriteNameListPanel recolorSpriteListPanel;
    private javax.swing.JButton recolorUndoButton;
    private javax.swing.JToggleButton scaleBackToggle;
    private javax.swing.JToggleButton scaleFrontToggle;
    private javax.swing.JPanel scalePanelAccessory;
    private pkmn.essentials.sprite.SinglePkmnSpriteNamePanel shadowSpritePanel;
    private javax.swing.JCheckBoxMenuItem showHiddenFilesToggle;
    private javax.swing.JButton spriteApplyButton;
    private javax.swing.JFileChooser spriteFC;
    private javax.swing.JButton spriteFolderBrowseButton;
    private javax.swing.JFileChooser spriteFolderFC;
    private javax.swing.JTextField spriteFolderField;
    private javax.swing.JTextField spriteNameField;
    private components.JFileDisplayPanel spritePreview;
    private javax.swing.JButton spriteResetButton;
    private javax.swing.JCheckBox spriteUseGameToggle;
    // End of variables declaration//GEN-END:variables

    private void documentUpdate(DocumentEvent evt){
        if (spriteFolderField.getDocument() == evt.getDocument() || 
                spriteNameField.getDocument() == evt.getDocument())
            updateSpriteApplyButton();
        if (spriteFolderField.getDocument() == evt.getDocument())
            config.setPkmnSpriteTargetFolder(spriteFolderField.getText());
        if (spriteNameField.getDocument() == evt.getDocument())
            config.setPkmnSpriteName(spriteNameField.getText());
        if (evt.getDocument() == recolorSaveField.getDocument())
            updateRecolorSaveButton();
    }
    
    private class Handler implements DocumentListener, ListDataListener{
        @Override
        public void insertUpdate(DocumentEvent evt) {
            documentUpdate(evt);
        }
        @Override
        public void removeUpdate(DocumentEvent evt) {
            documentUpdate(evt);
        }
        @Override
        public void changedUpdate(DocumentEvent evt) {
            documentUpdate(evt);
        }
        @Override
        public void intervalAdded(ListDataEvent evt) {
            if (evt.getSource() == recolorSpritePalette){
                updateRecolorPaletteButtons();
            } else if (evt.getSource() == recolorSpriteListPanel){
                updateRecolorSaveButton();
            } else 
                updateSpriteApplyButton();
        }
        @Override
        public void intervalRemoved(ListDataEvent evt) {
            if (evt.getSource() == recolorSpritePalette){
                updateRecolorPaletteButtons();
            } else if (evt.getSource() == recolorSpriteListPanel){
                updateRecolorSaveButton();
            } else 
                updateSpriteApplyButton();
        }
        @Override
        public void contentsChanged(ListDataEvent evt) {
            if (evt.getSource() == recolorSpritePalette){
                updateRecolorPaletteButtons();
            } else if (evt.getSource() == recolorSpriteListPanel){
                updateRecolorSaveButton();
            } else 
                updateSpriteApplyButton();
        }
    }
    /**
     * 
     */
    private abstract class FileWorker extends SwingWorker<Void, Void>{
        /**
         * The file to process.
         */
        protected File file;
        /**
         * Whether this was successful at processing the file.
         */
        protected boolean success = false;
        /**
         * This constructs a FileWorker that will process the given file.
         * @param file The file to process.
         */
        FileWorker(File file){
            this.file = file;
        }
        /**
         * This returns whether this was successful at processing the file. 
         * This will be inaccurate up until the file is finished being 
         * processed.
         * @return Whether this has successfully processed the file.
         */
        public boolean isSuccessful(){
            return success;
        }
        /**
         * This returns the file being processed by this FileWorker.
         * @return The file that will be processed.
         */
        public File getFile(){
            return file;
        }
        /**
         * This returns the String that is displayed for the progress bar.
         * @return The String to display on the progress bar.
         */
        public abstract String getProgressString();
        /**
         * This is used to display a success prompt to the user when the file is 
         * successfully processed.
         * @param file The file that was successfully processed.
         */
        protected void showSuccessPrompt(File file){}
        /**
         * This is used to display a failure prompt to the user when the file 
         * fails to be processed. If the failure prompt is a retry prompt, then 
         * this method should return whether to try processing the file again. 
         * Otherwise, this method should return {@code false}.
         * @param file The file that failed to be processed.
         * @param ex The exception that was thrown, or null if there was no 
         * exception thrown.
         * @return {@code true} if this should attempt to process the file 
         * again, {@code false} otherwise.
         */
        protected boolean showFailurePrompt(File file, IOException ex){
            return false;
        }
        @Override
        protected Void doInBackground() throws Exception {
            getLogger().entering(this.getClass().getName(), "doInBackground");
            setInputEnabled(false);
            progressBar.setValue(0);
            progressBar.setIndeterminate(true);
            progressBar.setStringPainted(true);
            progressDisplay.setString(getProgressString());
                // Whether the user wants this to try processing the file again 
            boolean retry = false;  // if unsuccessful
            do{
                useWaitCursor(true);
                try{
                    success = processFile(file);    // Try to process the file
                    useWaitCursor(false);
                    if (success)    // If the file was successfully processed
                        showSuccessPrompt(file);    // Show the success prompt
                    else            // If the file failed to be processed
                            // Show the failure prompt and get if the user wants 
                        retry = showFailurePrompt(file, null);  // to try again
                } catch (IOException ex){
                    getLogger().log(Level.WARNING,
                            "Error processing file \""+file+"\"", ex);
                    success = false;
                    useWaitCursor(false);
                        // Show the failure prompt and get if the user wants to 
                    retry = showFailurePrompt(file, ex);    // try again
                }
            }   // While the file failed to be processed and the user wants to 
            while(!success && retry);   // try again
            getLogger().exiting(this.getClass().getName(), "doInBackground");
            return null;
        }
        /**
         * 
         * @param file
         * @return
         * @throws IOException 
         */
        protected abstract boolean processFile(File file) throws IOException;
        @Override
        protected void done(){
            System.gc();        // Run the garbage collector
            progressBar.setValue(0);
            progressBar.setIndeterminate(false);
            progressBar.setStringPainted(false);
            progressDisplay.setString(null);
            setInputEnabled(true);
            useWaitCursor(false);
        }
    }
    /**
     * This is an abstract class that provides the framework for loading from 
     * files.
     */
    private abstract class FileLoader extends FileWorker{
        /**
         * Whether this is currently loading a file.
         */
        protected volatile boolean loading = false;
        /**
         * Whether file not found errors should be shown.
         */
        protected boolean showFileNotFound;
        /**
         * This constructs a FileLoader that will load the data from the given 
         * file.
         * @param file The file to load the data from.
         * @param showFileNotFound Whether a file not found error should result 
         * in a popup being shown to the user.
         */
        FileLoader(File file, boolean showFileNotFound){
            super(file);
            this.showFileNotFound = showFileNotFound;
        }
        /**
         * This constructs a FileLoader that will load the data from the given 
         * file.
         * @param file The file to load the data from.
         */
        FileLoader(File file){
            this(file,true);
        }
        @Override
        public String getProgressString(){
            return "Loading";
        }
        /**
         * This returns whether this is currently loading from a file.
         * @return Whether a file is currently being loaded.
         */
        public boolean isLoading(){
            return loading;
        }
        /**
         * This returns whether this was successful at loading from the file. 
         * This will be inaccurate up until the file is loaded.
         * @return Whether this has successfully loaded the file.
         */
        @Override
        public boolean isSuccessful(){
            return super.isSuccessful();
        }
        /**
         * This returns whether this shows a failure prompt when the file is not 
         * found.
         * @return Whether the file not found failure prompt is shown.
         */
        public boolean getShowsFileNotFoundPrompts(){
            return showFileNotFound;
        }
        /**
         * This returns the file being loaded by this FileLoader.
         * @return The file that will be loaded.
         */
        @Override
        public File getFile(){
            return super.getFile();
        }
        /**
         * This loads the data from the given file. This is called by {@link 
         * #processFile(File) processFile} in order to load the file.
         * @param file The file to load the data from.
         * @return Whether the file was successfully loaded.
         * @throws IOException 
         * @see #processFile(File) 
         */
        protected abstract boolean loadFile(File file) throws IOException;
        /**
         * {@inheritDoc } This delegates to {@link #loadFile(File) loadFile}.
         * @see #loadFile(File) 
         */
        @Override
        protected boolean processFile(File file) throws IOException{
            loading = true;
            return loadFile(file);
        }
        /**
         * This is used to display a success prompt to the user when the file is 
         * successfully loaded.
         * @param file The file that was successfully loaded.
         */
        @Override
        protected void showSuccessPrompt(File file){}
        /**
         * This is used to display a failure prompt to the user when the file 
         * fails to be loaded. 
         * @param file The file that failed to load.
         * @param ex
         * @return {@inheritDoc}
         */
        @Override
        protected boolean showFailurePrompt(File file, IOException ex){
                // If the file doesn't exist
            if (!file.exists() || ex instanceof FileNotFoundException){
                    // If this should show file not found prompts
                if (showFileNotFound){
                    JOptionPane.showMessageDialog(PkmnHelper.this, 
                            getFileNotFoundMessage(file,ex), 
                            getFailureTitle(file,ex), 
                            JOptionPane.ERROR_MESSAGE);
                }
                return false;
            }
            else{   // Ask the user if they would like to try loading the file
                    // again
                return JOptionPane.showConfirmDialog(PkmnHelper.this,
                        getFailureMessage(file,ex)+"\nWould you like to try again?",
                        getFailureTitle(file,ex),JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION;
            }
        }
        /**
         * This returns the title for the dialog to display if the file fails to 
         * be saved.
         * @param file The file that failed to load.
         * @return The title for the dialog to display if the file fails to
         * save.
         */
        protected String getFailureTitle(File file, IOException ex){
            return "ERROR - File Failed To Load";
        }
        /**
         * This returns the message to display if the file fails to load.
         * @param file The file that failed to load.
         * @return The message to display if the file fails to load.
         */
        protected String getFailureMessage(File file, IOException ex){
            return "The file failed to load.";
        }
        /**
         * This returns the message to display if the file does not exist.
         * @param file The file that did not exist.
         * @return The message to display if the file does not exist.
         */
        protected String getFileNotFoundMessage(File file, IOException ex){
            return "The file does not exist.";
        }
        @Override
        protected void done(){
            loading = false;
            super.done();
        }
    }
    /**
     * This is an abstract class that provides the framework for saving to a 
     * file.
     */
    private abstract class FileSaver extends FileWorker{
        /**
         * Whether this is currently saving a file.
         */
        protected volatile boolean saving = false;
        /**
         * This stores whether this should exit the program after saving.
         */
        protected volatile boolean exitAfterSaving;
        /**
         * This constructs a FileSaver that will save data to the given file 
         * and, if {@code exit} is {@code true}, will exit the program after 
         * saving the file.
         * @param file The file to save the data to.
         * @param exit Whether the program will exit after saving the file.
         */
        FileSaver(File file, boolean exit){
            super(file);
            exitAfterSaving = exit;
        }
        /**
         * This constructs a FileSaver that will save data to the given file.
         * @param file The file to save the data to.
         */
        FileSaver(File file){
            this(file,false);
        }
        @Override
        public String getProgressString(){
            return "Saving";
        }
        /**
         * This returns whether this is currently saving to a file.
         * @return Whether a file is currently being saved to.
         */
        public boolean isSaving(){
            return saving;
        }
        /**
         * This returns whether this was successful at saving to the file. This 
         * will be inaccurate up until the file is saved.
         * @return Whether this has successfully saved the file.
         */
        @Override
        public boolean isSuccessful(){
            return super.isSuccessful();
        }
        /**
         * This returns whether the program will exit after this finishes saving 
         * the file.
         * @return Whether the program will exit once the file is saved.
         */
        public boolean getExitAfterSaving(){
            return exitAfterSaving;
        }
        /**
         * This returns the file being saved to by this FileSaver.
         * @return The file that will be saved.
         */
        @Override
        public File getFile(){
            return super.getFile();
        }
        /**
         * This returns whether this should consider the file returned by {@link 
         * #getFile() getFile} as a directory in which to save files into.
         * @return Whether the file given to this FileSaver is actually a 
         * directory.
         */
        protected boolean isFileDirectory(){
            return false;
        }
        /**
         * This attempts to save to the given file. This is called by {@link 
         * #processFile(File) processFile} in order to save the file.
         * @param file The file to save.
         * @return Whether the file was successfully saved to.
         * @throws IOException
         * @see #processFile(File) 
         */
        protected abstract boolean saveFile(File file) throws IOException;
        /**
         * {@inheritDoc } This delegates the saving of the file to {@link 
         * #saveFile(File) saveFile}.
         * @see #saveFile(File) 
         */
        @Override
        protected boolean processFile(File file) throws IOException{
            saving = true;
                // Try to create the directories and if that fails, then give up 
                // on saving the file. (If the file is the directory, include it 
                // as a directory to be created. Otherwise, create the parent 
                // file of the file to be saved)
            if (!FilesExtended.createDirectories(PkmnHelper.this, 
                    (isFileDirectory())?file:file.getParentFile()))
                return false;
            return saveFile(file);
        }
        /**
         * This returns the title for the dialog to display if the file is 
         * successfully saved.
         * @param file The file that was successfully saved.
         * @return The title for the dialog to display if the file is 
         * successfully saved.
         */
        protected String getSuccessTitle(File file){
            return "File Saved Successfully";
        }
        /**
         * This returns the message to display if the file is successfully 
         * saved.
         * @param file The file that was successfully saved.
         * @return The message to display if the file is successfully saved.
         */
        protected String getSuccessMessage(File file){
            return "The file was successfully saved.";
        }
        /**
         * This returns the title for the dialog to display if the file fails to 
         * be saved.
         * @param file The file that failed to be saved.
         * @return The title for the dialog to display if the file fails to
         * save.
         */
        protected String getFailureTitle(File file, IOException ex){
            return "ERROR - File Failed To Save";
        }
        /**
         * This returns the message to display if the file fails to be saved.
         * @param file The file that failed to be saved.
         * @return The message to display if the file fails to save.
         */
        protected String getFailureMessage(File file, IOException ex){
            return "The file failed to save.";
        }
        /**
         * This is used to display a success prompt to the user when the file is 
         * successfully saved. The success prompt will display the message 
         * returned by {@link #getSuccessMessage()}. If the program is to exit 
         * after saving the file, then this will show nothing.
         * @param file The file that was successfully saved.
         */
        @Override
        protected void showSuccessPrompt(File file){
                // If the program is not to exit after saving the file
            if (!exitAfterSaving)   
                JOptionPane.showMessageDialog(PkmnHelper.this, 
                        getSuccessMessage(file), getSuccessTitle(file), 
                        JOptionPane.INFORMATION_MESSAGE);
        }
        /**
         * This is used to display a failure and retry prompt to the user when 
         * the file fails to be saved.
         * @param file The file that failed to be saved.
         * @param ex
         * @return {@inheritDoc }
         */
        @Override
        protected boolean showFailurePrompt(File file, IOException ex){
                // Get the message to be displayed. If the file failed to be 
                // backed up, show the backup failed message. Otherwise show the 
                // normal failure message.
            String message = getFailureMessage(file,ex);
                // Show a dialog prompt asking the user if they would like to 
                // try and save the file again and get their input. 
            int option = JOptionPane.showConfirmDialog(PkmnHelper.this, 
                    message+"\nWould you like to try again?",
                    getFailureTitle(file,ex),
                        // If the program is to exit after saving the file, show 
                        // a third "cancel" option to allow the user to cancel 
                        // exiting the program
                    (exitAfterSaving)?JOptionPane.YES_NO_CANCEL_OPTION:
                            JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE);
                // If the program was going to exit after saving the file
            if (exitAfterSaving){   
                    // If the option selected was the cancel option or the user 
                    // closed the dialog without selecting anything, then don't 
                    // exit the program
                exitAfterSaving = option != JOptionPane.CLOSED_OPTION && 
                        option != JOptionPane.CANCEL_OPTION;
            }   // Return whether the user selected yes
            return option == JOptionPane.YES_OPTION;    
        }
        /**
         * This is used to exit the program after this finishes saving the file.
         */
        protected void exitProgram(){
            System.exit(0);         // Exit the program
        }
        @Override
        protected void done(){
            if (exitAfterSaving)    // If the program is to exit after saving
                exitProgram();      // Exit the program
            saving = false;
            super.done();
        }
    }
    
    private class SpriteRenamer extends FileSaver{
        
        private String name;
        
        private Map<PkmnSpriteIcon, File> renamedFiles = new HashMap<>();
        
        private List<Set<Integer>> copiedIndexes = new ArrayList<>();
        
        private Map<File,File> tempFiles = new LinkedHashMap<>();
        
        private File frontFolder;
        private File backFolder;
        private File frontShinyFolder;
        private File backShinyFolder;
        private File iconFolder;
        private File iconShinyFolder;
        private File footprintFolder;
        private File shadowFolder;
        private File eggFolder;
        private File[] folders;
        
        private int stage = 0;
        
        private int index = 0;
        
        private SpriteRenamer(File folder, String name){
            super(folder);
            this.name = name;
        }
        @Override
        public String getProgressString(){
            return "Saving";
        }
        @Override
        protected boolean isFileDirectory(){
            return true;
        }
        @Override
        protected boolean saveFile(File file) throws IOException {
            try{
                switch(stage){
                    case(0):
                        frontFolder = new File(file,FRONT_SPRITE_FOLDER);
                        backFolder = new File(file,BACK_SPRITE_FOLDER);
                        frontShinyFolder = new File(file,FRONT_SHINY_SPRITE_FOLDER);
                        backShinyFolder = new File(file,BACK_SHINY_SPRITE_FOLDER);
                        iconFolder = new File(file,ICONS_SPRITE_FOLDER);
                        iconShinyFolder = new File(file,ICONS_SHINY_SPRITE_FOLDER);
                        footprintFolder = new File(file,FOOTPRINTS_SPRITE_FOLDER);
                        shadowFolder = new File(file,SHADOW_SPRITE_FOLDER);
                        eggFolder = new File(file,EGG_SPRITE_FOLDER);
                        folders = new File[]{
                            frontFolder,
                            backFolder,
                            frontShinyFolder,
                            backShinyFolder,
                            iconFolder,
                            iconShinyFolder,
                            footprintFolder,
                            shadowFolder,
                            eggFolder
                        };
                        
                        List<File> allFiles = new ArrayList<>();

                        for (PkmnSpriteNameListPanel panel : pkmnSpritePanels){
                            copiedIndexes.add(new HashSet<>());
                            for (PkmnSpriteIcon icon : panel.getModelList())
                                allFiles.add(icon.getFile());
                        }
                        for (SinglePkmnSpriteNamePanel panel : new SinglePkmnSpriteNamePanel[]{
                                footSpritePanel,shadowSpritePanel,eggSpritePanel,eggIconSpritePanel,eggCracksSpritePanel
                            }) {
                            if (panel.getIcon() != null)
                                allFiles.add(panel.getIcon().getFile());
                        }

                        int offset = 0;
                        for (int i = 0; i < pkmnSpritePanels.length; i++){
                            Set<Integer> copied = copiedIndexes.get(i);
                            PkmnSpriteNameListPanel panel = pkmnSpritePanels[i];
                            for (int j = 0; j < panel.getModelList().size(); j++){
                                Path path = panel.getModelList().get(j).getFile().toPath();
                                for (int k = offset+j+1; k < allFiles.size(); k++){
                                    if (shouldCopyFile(path, allFiles.get(k).toPath())){
                                        copied.add(j);
                                        break;
                                    }
                                }
                            }
                            offset += panel.getModelList().size();
                        }
                        
                        for (File temp : allFiles){
                            try{
                                tempFiles.put(temp, File.createTempFile("PkmnHelperSprite", ".png"));
                            } catch (IOException ex){
                                getLogger().log(Level.INFO, null, ex);
                            }
                        }
                        progressBar.setMaximum(folders.length + allFiles.size() + tempFiles.size());
                        progressBar.setIndeterminate(false);
                        stage = 1;
                        index = 0;
                    case(1):
                        int i1 = 0;
                        for (Map.Entry<File, File> entry : tempFiles.entrySet()){
                            if (i1 >= index){
                                try{
                                    entry.setValue(moveOrCopy(entry.getKey().toPath(),entry.getValue().toPath(),copySpritesToggle.isSelected(),true).toFile());
                                    progressBar.setValue(progressBar.getValue()+1);
                                } catch (FileNotFoundException ex){}
                                index++;
                            }
                            i1++;
                        }
                        stage = 2;
                        index = 0;
                    case(2):
                        for (int i = index; i < folders.length; i++, index++){
                            if (FilesExtended.createDirectories(PkmnHelper.this, folders[i])){
                                progressBar.setValue(progressBar.getValue()+1);
                            }
                            else
                                return false;
                        }
                        stage = 3;
                        index = 0;
                    case(3):
                    case(4):
                    case(5):
                    case(6):
                    case(7):
                    case(8):
                        while (stage < 9){
                            File folder = folders[stage-3];
                            List<PkmnSpriteIcon> icons = pkmnSpritePanels[stage-3].getListData();
                            Set<Integer> copied = copiedIndexes.get(stage-3);
                            int maleIndex = 0;
                            int femaleIndex = 0;

                            for (int i = index; i < icons.size(); i++,index++){
                                PkmnSpriteIcon icon = icons.get(i);
                                if (icon.isFemale()){
                                    icon.setIndex(femaleIndex);
                                    femaleIndex++;
                                } else {
                                    icon.setIndex(maleIndex);
                                    maleIndex++;
                                }
                                File temp = renameSprite(tempFiles.getOrDefault(icon.getFile(),icon.getFile()),
                                        new File(folder,getSpriteName(name,icon)+".png"),
                                        copied.contains(i));
                                renamedFiles.put(icon, temp);
                                progressBar.setValue(progressBar.getValue()+1);
                            }
                            stage++;
                            index = 0;
                        }
                    case(9):
                        PkmnSpriteIcon footIcon = footSpritePanel.getIcon();
                        if (footIcon != null){
                            File temp = renameSprite(tempFiles.getOrDefault(footIcon.getFile(),footIcon.getFile()),
                                    new File(footprintFolder,name+".png"),false);
                            renamedFiles.put(footIcon, temp);
                            progressBar.setValue(progressBar.getValue()+1);
                        }
                        stage = 10;
                        index = 0;
                    case(10):
                        PkmnSpriteIcon shadow = shadowSpritePanel.getIcon();
                        if (shadow != null){
                            File temp = renameSprite(tempFiles.getOrDefault(shadow.getFile(),shadow.getFile()),
                                    new File(shadowFolder,name+".png"),false);
                            renamedFiles.put(shadow, temp);
                            progressBar.setValue(progressBar.getValue()+1);
                        }
                        stage = 11;
                        index = 0;
                    case(11):
                        for (int i = index; i < 3; i++, index++){
                            PkmnSpriteIcon icon;
                            File temp;
                            switch(i){
                                case(0):
                                    icon = eggSpritePanel.getIcon();
                                    temp = new File(eggFolder,name+".png");
                                    break;
                                case(1):
                                    icon = eggIconSpritePanel.getIcon();
                                    temp = new File(eggFolder,name+EGG_ICON_SPRITE_SUFFIX+".png");
                                    break;
                                case(2):
                                    icon = eggCracksSpritePanel.getIcon();
                                    temp = new File(eggFolder,name+EGG_HATCHING_SPRITE_SUFFIX+".png");
                                    break;
                                default:
                                    continue;
                            }
                            if (icon != null){
                                temp = renameSprite(tempFiles.getOrDefault(icon.getFile(),icon.getFile()),
                                        temp,false);
                                renamedFiles.put(icon, temp);
                                progressBar.setValue(progressBar.getValue()+1);
                            }
                        }
                }
            } catch (IOException ex) {
                getLogger().log(Level.WARNING, "Failed to save PKMN sprites", ex);
                return false;
            } catch (Exception ex){
                getLogger().log(Level.WARNING, "Failed to save PKMN sprites", ex);
            }
            
            return true;
        }
        @Override
        protected void done(){
            super.done();
            for (Map.Entry<PkmnSpriteIcon, File> temp : renamedFiles.entrySet()){
                temp.getKey().setFile(temp.getValue());
            }
            for (PkmnSpriteNameListPanel panel : pkmnSpritePanels)
                panel.repaint();
            footSpritePanel.refreshName();
        }
    }
    
    
    private class RecolorSpriteSaver extends FileSaver{
        
        private Map<PalettePkmnSpriteIcon, File> renamedFiles = new HashMap<>();
        
        private int index = 0;

        public RecolorSpriteSaver(File file) {
            super(file);
        }
        @Override
        protected boolean isFileDirectory(){
            return true;
        }
        @Override
        public String getProgressString(){
            return "Saving";
        }
        @Override
        protected boolean saveFile(File file) throws IOException {
            try{
                progressBar.setMaximum(recolorSpriteListPanel.getModelList().size());
                progressBar.setIndeterminate(false);
                for (int i = index; i < recolorSpriteListPanel.getModelList().size(); i++, index++){
                    PkmnSpriteIcon tempIcon = recolorSpriteListPanel.getModelList().get(i);
                    if (!(tempIcon instanceof PalettePkmnSpriteIcon))
                        continue;
                    PalettePkmnSpriteIcon icon = (PalettePkmnSpriteIcon)tempIcon;
                    BufferedImage image = icon.getBufferedImage();
                    File newFile = new File(file,icon.getFile().getName());
                    if (newFile.exists())
                        newFile = FilesExtended.getNextAvailableFilePath(newFile);
                    renamedFiles.put(icon, newFile);
                    ImageIO.write(image, "png", newFile);
                    progressBar.setValue(progressBar.getValue()+1);
                }
            } catch (IOException ex) {
                getLogger().log(Level.WARNING, "Failed to save PKMN sprites", ex);
                return false;
            }
            
            return true;
        }
        
        @Override
        protected void done(){
            super.done();
            for (Map.Entry<PalettePkmnSpriteIcon, File> temp : renamedFiles.entrySet()){
                temp.getKey().setFile(temp.getValue());
            }
            recolorSpriteListPanel.repaint();
        }
    }
}
