/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import config.ConfigUtilities;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import utils.SwingExtendedUtilities;

/**
 *
 * @author Mosblinker
 */
public class PkmnHelperConfig {
    /**
     * This is the configuration key for the progress display setting.
     */
    public static final String PROGRESS_DISPLAY_KEY = "DisplayProgress";
    /**
     * 
     */
    public static final String FILE_CHOOSER_SIZE_KEY = "FileChooserSize";
    /**
     * 
     */
    public static final String FILE_CHOOSER_CURRENT_DIRECTORY_KEY = 
            "CurrentDirectory";
    /**
     * 
     */
    public static final String FILE_CHOOSER_FILE_FILTER_KEY = "FileFilter";
    /**
     * 
     */
    public static final String FILE_CHOOSER_SELECTED_FILE_KEY = "SelectedFile";
    
//    public static final String COLOR_SELECTOR_SIZE_KEY = "ColorSelectorSize";
    
    public static final String HIDDEN_FILES_ARE_SHOWN_KEY = "HiddenFilesAreShown";
    /**
     * This is the suffix for the configuration keys for the size of a 
     * component.
     */
    public static final String COMPONENT_SIZE_KEY_SUFFIX = "Size";
    /**
     * This is the suffix for the configuration keys for the location of a 
     * component.
     */
    public static final String COMPONENT_LOCATION_KEY_SUFFIX = "Location";
    /**
     * This is the suffix for the configuration keys for the bounds of a 
     * component.
     */
    public static final String COMPONENT_BOUNDS_KEY_SUFFIX = "Bounds";
    /**
     * 
     */
    public static final String CHECK_FOR_UPDATES_AT_START_KEY = "CheckForUpdatesAtStartup";
    /**
     * 
     */
    public static final String PROGRAM_SELECTED_TAB_INDEX_KEY = "SelectedTabIndex";
    /**
     * 
     */
    public static final String PROGRAM_SIZE_KEY = "ProgramWindowSize";
    /**
     * 
     */
    public static final String PROGRAM_BOUNDS_KEY = "ProgramBounds";
    /**
     * 
     */
    public static final String PKMN_SPRITE_NODE_NAME = "PkmnSprites";
    /**
     * 
     */
    public static final String PKMN_FRONT_SPRITE_NODE_NAME = "Front";
    
    public static final String PKMN_BACK_SPRITE_NODE_NAME = "Back";
    
    public static final String PKMN_FRONT_SHINY_SPRITE_NODE_NAME = "FrontShiny";
    
    public static final String PKMN_BACK_SHINY_SPRITE_NODE_NAME = "BackShiny";
    
    public static final String PKMN_ICONS_SPRITE_NODE_NAME = "Icons";
    
    public static final String PKMN_ICONS_SHINY_SPRITE_NODE_NAME = "IconsShiny";
    
    public static final String PKMN_SPRITE_FILE_NAME_PREFIX = "SpriteFile";
    
    public static final String PKMN_SPRITE_FILE_COUNT = "SpriteCount";
    
    public static final String PKMN_FOOTPRINT_SPRITE_KEY = "Footprints";
    
    public static final String PKMN_EGG_SPRITE_KEY = "Egg";
    
    public static final String PKMN_EGG_ICON_SPRITE_KEY = "EggSprite";
    
    public static final String PKMN_EGG_HATCHING_SPRITE_KEY = "EggCracks";
    
    public static final String PKMN_SPRITE_TARGET_FOLDER_KEY = "TargetFolder";
    
    public static final String PKMN_SPRITE_NAME_KEY = "SpriteName";
    
    public static final String COPY_PKMN_SPRITES_KEY = "CopyPkmnSprites";
    
    public static final String RECOLOR_SPRITE_NODE_NAME = "RecolorSprites";
    
    public static final String RECOLOR_SCALE_INDEX_KEY = "ScaleIndex";
    
    public static final String RECOLOR_PKMN_SPRITE_NODE_NAME = "Sprites";
    
    public static final String RECOLOR_TARGET_FOLDER_KEY = "TargetFolder";
    
    public static final String REFERENCE_SPRITE_KEY = "ReferenceSprite";
    
    public static final String PREVIEW_SHOWN_KEY_SUFFIX = "PreviewShown";
    
    public static final String GAME_PROJECT_FOLDER_KEY = "GameProjectFolder";
    
    /**
     * This is a preference node to store the settings for this program.
     */
    private final Preferences node;
    /**
     * 
     */
    private final Map<JFileChooser, Preferences> fcNodes;
    
//    private final Map<JColorSelector, Preferences> colorSelNodes;
    /**
     * This is a map used to map components to the names of the component in 
     * the settings.
     */
    private final Map<Component, String> compNameMap;
    /**
     * 
     */
    private final Preferences pkmnSpriteNode;
    
    private final PkmnSpriteFileList pkmnSpritesF;
    
    private final PkmnSpriteFileList pkmnSpritesB;
    
    private final PkmnSpriteFileList pkmnSpritesFS;
    
    private final PkmnSpriteFileList pkmnSpritesBS;
    
    private final PkmnSpriteFileList pkmnSpritesI;
    
    private final PkmnSpriteFileList pkmnSpritesIS;
    
    private final Preferences recolorNode;
    
    private final PkmnSpriteFileList recolorSprites;
    /**
     * 
     * @param node 
     */
    public PkmnHelperConfig(Preferences node){
        this.node = Objects.requireNonNull(node);
        fcNodes = new HashMap<>();
        compNameMap = new HashMap<>();
        pkmnSpriteNode = node.node(PKMN_SPRITE_NODE_NAME);
        pkmnSpritesF = new PkmnSpriteFileList(PKMN_FRONT_SPRITE_NODE_NAME);
        pkmnSpritesB = new PkmnSpriteFileList(PKMN_BACK_SPRITE_NODE_NAME);
        pkmnSpritesFS = new PkmnSpriteFileList(PKMN_FRONT_SHINY_SPRITE_NODE_NAME);
        pkmnSpritesBS = new PkmnSpriteFileList(PKMN_BACK_SHINY_SPRITE_NODE_NAME);
        pkmnSpritesI = new PkmnSpriteFileList(PKMN_ICONS_SPRITE_NODE_NAME);
        pkmnSpritesIS = new PkmnSpriteFileList(PKMN_ICONS_SHINY_SPRITE_NODE_NAME);
        recolorNode = node.node(RECOLOR_SPRITE_NODE_NAME);
        recolorSprites = new PkmnSpriteFileList(recolorNode.node(RECOLOR_PKMN_SPRITE_NODE_NAME));
    }
    /**
     * 
     * @return 
     */
    public Preferences getPreferences(){
        return node;
    }
    /**
     * 
     * @return 
     */
    public Map<JFileChooser, Preferences> getFileChooserPreferenceMap(){
        return fcNodes;
    }
    /**
     * 
     * @param fc
     * @param name
     */
    public void addFileChooser(JFileChooser fc, String name){
        if (!fcNodes.containsKey(fc))
            fcNodes.put(fc, getPreferences().node(name));
    }
    /**
     * 
     * @param fc
     * @return 
     */
    public Preferences getFileChooserPreferences(JFileChooser fc){
        return fcNodes.get(fc);
    }
    /**
     * This returns a map used to map components in LinkManager to the names of 
     * the component in the settings that relate to that component.
     * @return A map that maps components to the names for those components.
     */
    public Map<Component, String> getComponentNames(){
        return compNameMap;
    }
    /**
     * 
     * @param key
     * @param defaultValue
     * @return 
     */
    public Dimension getDimension(String key, Dimension defaultValue){
        return ConfigUtilities.getDimension(node, key, defaultValue);
    }
    /**
     * 
     * @param key
     * @return 
     */
    public Dimension getDimension(String key){
        return getDimension(key,null);
    }
    /**
     * 
     * @param key
     * @param width
     * @param height 
     */
    public void putDimension(String key, int width, int height){
        ConfigUtilities.putDimension(node, key, width, height);
    }
    /**
     * 
     * @param key
     * @param dim 
     */
    public void putDimension(String key, Dimension dim){
        ConfigUtilities.putDimension(node, key, dim);
    }
    /**
     * 
     * @param key
     * @param comp 
     */
    public void putDimension(String key, Component comp){
        putDimension(key,comp.getWidth(),comp.getHeight());
    }
    /**
     * 
     * @param key
     * @param defaultValue
     * @return 
     */
    public Rectangle getRectangle(String key, Rectangle defaultValue){
        return ConfigUtilities.getRectangle(node, key, defaultValue);
    }
    /**
     * 
     * @param key
     * @return 
     */
    public Rectangle getRectangle(String key){
        return getRectangle(key,null);
    }
    /**
     * 
     * @param key
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public void putRectangle(String key, int x, int y, int width, int height){
        ConfigUtilities.putRectangle(node, key, x, y, width, height);
    }
    /**
     * 
     * @param key
     * @param width
     * @param height 
     */
    public void putRectangle(String key, int width, int height){
        putRectangle(key,0,0,width,height);
    }
    /**
     * 
     * @param key
     * @param value 
     */
    public void putRectangle(String key, Rectangle value){
        ConfigUtilities.putRectangle(node, key, value);
    }
    /**
     * 
     * @param key
     * @param comp 
     */
    public void putRectangle(String key, Component comp){
        putRectangle(key,comp.getX(),comp.getY(),comp.getWidth(),comp.getHeight());
    }
    /**
     * 
     * @param node
     * @param key
     * @param defaultFile
     * @return 
     */
    protected File getFile(Preferences node, String key, File defaultFile){
            // Get the name of the file from the preference node, or null
        String name = node.get(key, null);
            // If there is no value set for that key
        if (name == null)
            return defaultFile;
        return new File(name);
    }
    /**
     * 
     * @param key
     * @param defaultFile
     * @return 
     */
    public File getFile(String key, File defaultFile){
        return getFile(getPreferences(),key,defaultFile);
    }
    /**
     * 
     * @param key
     * @return 
     */
    public File getFile(String key){
        return getFile(key,null);
    }
    /**
     * 
     * @param node
     * @param key
     * @param value 
     */
    protected void putFile(Preferences node, String key, File value){
        if (value == null)
            node.remove(key);
        else
            node.put(key, value.toString());
    }
    /**
     * 
     * @param key
     * @param value 
     */
    public void putFile(String key, File value){
        putFile(getPreferences(),key,value);
    }
    /**
     * 
     * @return 
     */
    public Dimension getProgramSize(){
        return getDimension(PROGRAM_SIZE_KEY);
    }
    /**
     * 
     * @param comp
     * @return 
     */
    public Dimension getProgramSize(PkmnHelper comp){
        Dimension dim = getProgramSize();
        SwingExtendedUtilities.setComponentSize(comp,dim);
        return dim;
    }
    /**
     * 
     * @param comp 
     */
    public void setProgramSize(PkmnHelper comp){
        putDimension(PROGRAM_SIZE_KEY,comp);
    }
    /**
     * 
     * @return 
     */
    public Rectangle getProgramBounds(){
        return getRectangle(PROGRAM_BOUNDS_KEY);
    }
    /**
     * 
     * @param comp
     * @return 
     */
    public Rectangle getProgramBounds(PkmnHelper comp){
        Rectangle rect = getProgramBounds();
        SwingExtendedUtilities.setComponentBounds(comp, rect);
        return rect;
    }
    /**
     * 
     * @param comp 
     */
    public void setProgramBounds(PkmnHelper comp){
        putRectangle(PROGRAM_BOUNDS_KEY,comp);
    }
    /**
     * 
     * @param comp
     * @param defaultValue
     * @return 
     */
    public Dimension getComponentSize(Component comp, Dimension defaultValue){
        return getDimension(getComponentNames().get(comp)+COMPONENT_SIZE_KEY_SUFFIX,defaultValue);
    }
    /**
     * 
     * @param comp
     * @return 
     */
    public Dimension getComponentSize(Component comp){
        return getComponentSize(comp,null);
    }
    /**
     * 
     * @param comp
     * @param width
     * @param height 
     */
    public void setComponentSize(Component comp, int width, int height){
        putDimension(getComponentNames().get(comp)+COMPONENT_SIZE_KEY_SUFFIX,width,height);
    }
    /**
     * 
     * @param comp 
     * @param dim 
     */
    public void setComponentSize(Component comp, Dimension dim){
        putDimension(getComponentNames().get(comp)+COMPONENT_SIZE_KEY_SUFFIX,dim);
    }
    /**
     * 
     * @param comp 
     */
    public void setComponentSize(Component comp){
        putDimension(getComponentNames().get(comp)+COMPONENT_SIZE_KEY_SUFFIX,comp);
    }
    /**
     * 
     * @param fc
     * @return 
     */
    public FileFilter getFileFilter(JFileChooser fc){
        return ConfigUtilities.getFileFilter(getFileChooserPreferences(fc), fc, 
                FILE_CHOOSER_FILE_FILTER_KEY, null);
    }
    /**
     * 
     * @param fc
     * @param filter 
     */
    public void setFileFilter(JFileChooser fc, FileFilter filter){
        ConfigUtilities.putFileFilter(getFileChooserPreferences(fc), fc, 
                FILE_CHOOSER_FILE_FILTER_KEY, filter);
    }
    /**
     * 
     * @param fc 
     */
    public void setFileFilter(JFileChooser fc){
        setFileFilter(fc,fc.getFileFilter());
    }
    /**
     * 
     * @param fc
     * @param defaultValue
     * @return 
     */
    public File getSelectedFile(JFileChooser fc, File defaultValue){
        return getFile(getFileChooserPreferences(fc), 
                FILE_CHOOSER_SELECTED_FILE_KEY,defaultValue);
    }
    /**
     * 
     * @param fc
     * @return 
     */
    public File getSelectedFile(JFileChooser fc){
        return getSelectedFile(fc,null);
    }
    /**
     * 
     * @param fc
     * @param file 
     */
    public void setSelectedFile(JFileChooser fc, File file){
        putFile(getFileChooserPreferences(fc), FILE_CHOOSER_SELECTED_FILE_KEY,file);
    }
    /**
     * 
     * @param fc 
     */
    public void setSelectedFile(JFileChooser fc){
        setSelectedFile(fc,fc.getSelectedFile());
    }
    /**
     * 
     * @param fc
     * @return 
     */
    public Dimension getFileChooserSize(JFileChooser fc){
        return ConfigUtilities.getDimension(getFileChooserPreferences(fc), 
                FILE_CHOOSER_SIZE_KEY, null);
    }
    /**
     * 
     * @param fc
     */
    public void setFileChooserSize(JFileChooser fc){
        ConfigUtilities.putDimension(getFileChooserPreferences(fc), 
                FILE_CHOOSER_SIZE_KEY,fc);
    }
    /**
     * 
     * @param fc
     * @return 
     */
    public File getCurrentDirectory(JFileChooser fc){
        return getFile(getFileChooserPreferences(fc), 
                FILE_CHOOSER_CURRENT_DIRECTORY_KEY, null);
    }
    /**
     * 
     * @param fc 
     */
    public void setCurrentDirectory(JFileChooser fc){
        putFile(getFileChooserPreferences(fc),FILE_CHOOSER_CURRENT_DIRECTORY_KEY,
                fc.getCurrentDirectory());
    }
    /**
     * 
     * @param fc 
     */
    public void storeFileChooser(JFileChooser fc){
            // Put the file chooser's size in the preference node
        setFileChooserSize(fc);
            // Put the file chooser's current directory in the preference node
        setCurrentDirectory(fc);
    }
    /**
     * 
     * @param fc 
     */
    public void loadFileChooser(JFileChooser fc){
            // Get the current directory for the file chooser
        File dir = getCurrentDirectory(fc);
            // If there is a current directory for the file chooser and it exists
        if (dir != null && dir.exists()){
                // Set the file chooser's current directory
            fc.setCurrentDirectory(dir);
        }   // Get the selected file for the file chooser, or null
        File file = getSelectedFile(fc);
            // If there is a selected file for the file chooser
        if (file != null){
                // Select that file in the file chooser
            fc.setSelectedFile(file);
        }   // Load the file chooser's size from the preference node
        SwingExtendedUtilities.setComponentSize(fc,getFileChooserSize(fc));
        FileFilter filter = getFileFilter(fc);
        if (filter != null)
            fc.setFileFilter(filter);
    }
    /**
     * 
     * @param value 
     */
    public void setProgressDisplaySetting(Integer value){
        if (value == null)
            getPreferences().remove(PROGRESS_DISPLAY_KEY);
        else
            getPreferences().putInt(PROGRESS_DISPLAY_KEY, value);
    }
    /**
     * 
     * @param defaultValue
     * @return 
     */
    public int getProgressDisplaySetting(int defaultValue){
        return getPreferences().getInt(PROGRESS_DISPLAY_KEY, defaultValue);
    }
    /**
     * 
     * @param index 
     */
    public void setSelectedTabIndex(int index){
        getPreferences().putInt(PROGRAM_SELECTED_TAB_INDEX_KEY, index);
    }
    /**
     * 
     * @return 
     */
    public int getSelectedTabIndex(){
        return getPreferences().getInt(PROGRAM_SELECTED_TAB_INDEX_KEY, 0);
    }
    /**
     * 
     * @param value 
     */
    public void setHiddenFilesAreShown(boolean value){
        getPreferences().putBoolean(HIDDEN_FILES_ARE_SHOWN_KEY, value);
    }
    /**
     * 
     * @return 
     */
    public boolean getHiddenFilesAreShown(){
        return getPreferences().getBoolean(HIDDEN_FILES_ARE_SHOWN_KEY,true);
    }
    /**
     * 
     * @return 
     */
    public Preferences getPkmnSpritePreferences(){
        return pkmnSpriteNode;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnFrontSprites(){
        return pkmnSpritesF;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnBackSprites(){
        return pkmnSpritesB;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnShinyFrontSprites(){
        return pkmnSpritesFS;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnShinyBackSprites(){
        return pkmnSpritesBS;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnIconsSprites(){
        return pkmnSpritesI;
    }
    /**
     * 
     * @return 
     */
    public List<File> getPkmnShinyIconsSprites(){
        return pkmnSpritesIS;
    }
    /**
     * 
     * @return 
     */
    public File getPkmnFootprintSprite(){
        return getFile(getPkmnSpritePreferences(),PKMN_FOOTPRINT_SPRITE_KEY,null);
    }
    /**
     * 
     * @param file 
     */
    public void setPkmnFootprintSprite(File file){
        putFile(getPkmnSpritePreferences(),PKMN_FOOTPRINT_SPRITE_KEY,file);
    }
    /**
     * 
     * @return 
     */
    public File getPkmnEggSprite(){
        return getFile(getPkmnSpritePreferences(),PKMN_EGG_SPRITE_KEY,null);
    }
    /**
     * 
     * @param file 
     */
    public void setPkmnEggSprite(File file){
        putFile(getPkmnSpritePreferences(),PKMN_EGG_SPRITE_KEY,file);
    }
    /**
     * 
     * @return 
     */
    public File getPkmnEggIconSprite(){
        return getFile(getPkmnSpritePreferences(),PKMN_EGG_ICON_SPRITE_KEY,null);
    }
    /**
     * 
     * @param file 
     */
    public void setPkmnEggIconSprite(File file){
        putFile(getPkmnSpritePreferences(),PKMN_EGG_ICON_SPRITE_KEY,file);
    }
    /**
     * 
     * @return 
     */
    public File getPkmnEggHatchingSprite(){
        return getFile(getPkmnSpritePreferences(),PKMN_EGG_HATCHING_SPRITE_KEY,null);
    }
    /**
     * 
     * @param file 
     */
    public void setPkmnEggHatchingSprite(File file){
        putFile(getPkmnSpritePreferences(),PKMN_EGG_HATCHING_SPRITE_KEY,file);
    }
    
    public String getPkmnSpriteTargetFolder(){
        return getPkmnSpritePreferences().get(PKMN_SPRITE_TARGET_FOLDER_KEY, null);
    }
    
    public void setPkmnSpriteTargetFolder(String path){
        if (path == null)
            getPkmnSpritePreferences().remove(PKMN_SPRITE_TARGET_FOLDER_KEY);
        else
            getPkmnSpritePreferences().put(PKMN_SPRITE_TARGET_FOLDER_KEY, path);
    }
    
    public String getPkmnSpriteName(){
        return getPkmnSpritePreferences().get(PKMN_SPRITE_NAME_KEY, null);
    }
    
    public void setPkmnSpriteName(String path){
        if (path == null)
            getPkmnSpritePreferences().remove(PKMN_SPRITE_NAME_KEY);
        else
            getPkmnSpritePreferences().put(PKMN_SPRITE_NAME_KEY, path);
    }
    
    public boolean getPkmnSpritesAreCopied(){
        return getPkmnSpritePreferences().getBoolean(COPY_PKMN_SPRITES_KEY, false);
    }
    
    public void setPkmnSpritesAreCopied(boolean value){
        getPkmnSpritePreferences().putBoolean(COPY_PKMN_SPRITES_KEY, value);
    }
    
    public Preferences getRecolorSpritePreferences(){
        return recolorNode;
    }
    
    public int getRecolorScaleIndex(int defaultValue){
        return getRecolorSpritePreferences().getInt(RECOLOR_SCALE_INDEX_KEY,defaultValue);
    }
    
    public void setRecolorScaleIndex(int value){
        getRecolorSpritePreferences().putInt(RECOLOR_SCALE_INDEX_KEY, value);
    }
    
    public List<File> getRecolorSprites(){
        return recolorSprites;
    }
    
    public String getRecolorTargetFolder(){
        return getRecolorSpritePreferences().get(RECOLOR_TARGET_FOLDER_KEY, null);
    }
    
    public void setRecolorTargetFolder(String path){
        if (path == null)
            getRecolorSpritePreferences().remove(RECOLOR_TARGET_FOLDER_KEY);
        else
            getRecolorSpritePreferences().put(RECOLOR_TARGET_FOLDER_KEY, path);
    }
    
    public boolean getRecolorPreviewShown(Component comp){
        return getRecolorSpritePreferences().getBoolean(getComponentNames().get(comp)+PREVIEW_SHOWN_KEY_SUFFIX, true);
    }
    
    public void setRecolorPreviewShown(Component comp, boolean value){
        getRecolorSpritePreferences().putBoolean(getComponentNames().get(comp)+PREVIEW_SHOWN_KEY_SUFFIX, value);
    }
    /**
     * 
     * @return 
     */
    public File getRecolorReferenceSprite(){
        return getFile(getRecolorSpritePreferences(),REFERENCE_SPRITE_KEY,null);
    }
    /**
     * 
     * @param file 
     */
    public void setRecolorReferenceSprite(File file){
        putFile(getRecolorSpritePreferences(),REFERENCE_SPRITE_KEY,file);
    }
    
    public File getGameProjectFolder(){
        return getFile(GAME_PROJECT_FOLDER_KEY, null);
    }
    
    public void setGameProjectFolder(File folder){
        putFile(GAME_PROJECT_FOLDER_KEY, folder);
    }
    
    private class PkmnSpriteFileList extends AbstractList<File>{
        
        private final Preferences node;
        
        protected PkmnSpriteFileList(Preferences node){
            this.node = node;
        }
        
        protected PkmnSpriteFileList(String name){
            this(getPkmnSpritePreferences().node(name));
        }

        public Preferences getPreferences(){
            return node;
        }
        
        
        
        protected File getFile(int index){
            return PkmnHelperConfig.this.getFile(getPreferences(),
                    PKMN_SPRITE_FILE_NAME_PREFIX+index,null);
        }
        
        protected void putFile(int index, File element){
            PkmnHelperConfig.this.putFile(getPreferences(),
                    PKMN_SPRITE_FILE_NAME_PREFIX+index,element);
        }
        /**
         * 
         * @param size 
         */
        protected void setSize(int size){
            getPreferences().putInt(PKMN_SPRITE_FILE_COUNT, size);
        }
        @Override
        public int size() {
            return getPreferences().getInt(PKMN_SPRITE_FILE_COUNT, 0);
        }
        @Override
        public File get(int index) {
            Objects.checkIndex(index, size());
            return getFile(index);
        }
        @Override
        public void add(int index, File element){
            Objects.requireNonNull(element);
            int size = size()+1;
            Objects.checkIndex(index, size);
            for (int i = size-1; i >= index; i--)
                getPreferences().put(PKMN_SPRITE_FILE_NAME_PREFIX+(i+1), 
                        getPreferences().get(PKMN_SPRITE_FILE_NAME_PREFIX+i, null));
            putFile(index,element);
            setSize(size);
        }
        @Override
        public File remove(int index){
            int size = size();
            Objects.checkIndex(index, size);
            File file = getFile(index);
            size--;
            for (int i = index; i < size; i++){
                getPreferences().put(PKMN_SPRITE_FILE_NAME_PREFIX+i, 
                        getPreferences().get(PKMN_SPRITE_FILE_NAME_PREFIX+(i+1), null));
            }
            setSize(size);
            return file;
        }
        @Override
        public boolean remove(Object o){
            int index = indexOf(o);
            if (index >= 0){
                remove(index);
                return true;
            }
            return false;
        }
        @Override
        public File set(int index, File element){
            Objects.requireNonNull(element);
            Objects.checkIndex(index, size());
            File file = getFile(index);
            putFile(index,element);
            return file;
        }
        @Override
        public void clear(){
            setSize(0);
        }
    }
}
