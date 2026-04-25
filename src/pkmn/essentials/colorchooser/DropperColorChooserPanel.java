/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.ArrayComboBoxModel;
import icons.Icon2D;
import icons.ScaledIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import pkmn.essentials.RGBInfoPanel;

/**
 *
 * @author Mosblinker
 */
public class DropperColorChooserPanel extends AbstractColorChooserPanel{
    
    private static final Double[] DEFAULT_SCALE_VALUES = {
        8.0, 4.0, 2.0, 1.0, 0.5, 0.25, 0.125
    };
    
    private JScrollPane imageScrollPane;
    private Cursor imageCursor = null;
    private JLabel imageLabel;
    private ImageList images = new ImageList();
    private int imageIndex = -1;
    private JButton nextButton;
    private JButton prevButton;
    private ImageIcon imageIcon = null;
    private ScaledIcon icon;
    private double scale = 1.0;
    private JComboBox<Double> scaleCombo;
    private ArrayComboBoxModel<Double> scaleModel = new ArrayComboBoxModel<>(DEFAULT_SCALE_VALUES);
    private RGBInfoPanel rgbInfoPanel;
    private boolean updating = false;
    private boolean initialized = false;
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

    @Override
    public void updateChooser() {
        Color color = getColorFromModel();
        boolean temp = updating;
        updating = true;
        rgbInfoPanel.setColor(color);
        updating = temp;
    }
    
    private void addFiller(int x, int y, int maxWidth, int maxHeight){
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 0);
        constraints.weightx = (maxWidth > 0) ? 0.5 : 0;
        constraints.weighty = (maxHeight > 0) ? 0.5 : 0;
        add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(maxWidth, maxHeight)),constraints);
    }

    @Override
    protected void buildChooser() {
        if (initialized)
            return;
        setBorder(javax.swing.BorderFactory.createEmptyBorder(13, 12, 13, 12));
        setLayout(new java.awt.GridBagLayout());
        
        Handler handler = new Handler();
        
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.addMouseListener(handler);
        imageLabel.addMouseWheelListener(handler);
        if (imageCursor != null)
            imageLabel.setCursor(imageCursor);
        imageScrollPane = new JScrollPane(imageLabel);
        imageScrollPane.setMaximumSize(new java.awt.Dimension(32767,32767));
        imageScrollPane.setPreferredSize(new java.awt.Dimension(360,240));
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 7, 6);
        constraints.gridwidth = 5;
        constraints.gridheight = 2;
        constraints.weightx = 0.9;
        constraints.weighty = 0.9;
        add(imageScrollPane,constraints);
        
        rgbInfoPanel = new RGBInfoPanel();
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 7, 0);
        add(rgbInfoPanel,constraints);
        
        addFiller(5,1,0,32767);
        
        prevButton = new JButton(new DirectionIcon(-1));
        prevButton.setActionCommand("Previous");
        prevButton.setEnabled(false);
        prevButton.setToolTipText("Previous image");
        prevButton.addActionListener(handler);
        
        
        nextButton = new JButton(new DirectionIcon(1));
        nextButton.setActionCommand("Next");
        nextButton.setEnabled(false);
        nextButton.setToolTipText("Next image");
        nextButton.addActionListener(handler);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 6);
        add(nextButton,constraints);
        
        addFiller(1,2,32767,0);
        
        addFiller(3,2,32767,0);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 6);
        add(prevButton,constraints);
        
        scaleCombo = new JComboBox<>(scaleModel);
        scaleCombo.setRenderer(new PercentRenderer());
        scaleCombo.addActionListener(handler);
        scaleCombo.setPrototypeDisplayValue(100000.9999);
        scaleCombo.setEditable(false);
        scaleCombo.setSelectedItem(1.0);
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 0, 6);
        add(scaleCombo,constraints);
        
        initialized = true;
    }

    @Override
    public String getDisplayName() {
        return "Color Dropper";
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }
    
    public void setImageCursor(Cursor cursor){
        imageCursor = cursor;
        if (imageLabel != null)
            imageLabel.setCursor(cursor);
    }
    
    public Cursor getImageCursor(){
        return imageCursor;
    }
    
    private void updateImage(){
        if (imageIcon != null){
            icon = new ScaledIcon(imageIcon,scale);
            if (scale > 1.0)
                icon.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }
        imageLabel.setIcon(icon);
    }
    
    private void updateIndexButtons(){
        nextButton.setEnabled(isEnabled() && !images.isEmpty() && imageIndex < images.size()-1);
        prevButton.setEnabled(isEnabled() && !images.isEmpty() && imageIndex > 0);
    }
    
    private void setImageIndex(int index){
        if (index != imageIndex){
            scale = 1.0;
            scaleModel.setSelectedItem(1.0);
        }
        imageIndex = index;
        if (index < 0 || index >= images.size())
            imageIcon = null;
        else
            imageIcon = new ImageIcon(images.get(index));
        updateImage();
        updateIndexButtons();
    }
    
    public List<BufferedImage> getImages(){
        return images;
    }
    
    public void setImages(Collection<? extends BufferedImage> images){
        this.images.clear();
        this.images.addAll(images);
    }
    
    private void setSelectedColor(Color color){
        boolean temp = updating;
        updating = true;
        rgbInfoPanel.setColor(color);
        getColorSelectionModel().setSelectedColor(color);
        updating = temp;
    }
    
    private class PercentRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list,Object value,
                int index,boolean isSelected,boolean cellHasFocus){
            if (value instanceof Number){
                double temp = ((Number)value).doubleValue();
                value = temp*100.0 + " %";
            }
            return super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
        }
    }
    
    private class Handler extends MouseAdapter implements ActionListener{
        @Override
        public void mouseWheelMoved(MouseWheelEvent evt){
            if (evt.isControlDown()){
                int index = scaleCombo.getSelectedIndex() + evt.getWheelRotation();
                if (index < 0){
                    scaleModel.add(0, scale * 2);
                    index = 0;
                } else if (index >= scaleModel.size()){
                    scaleModel.add(scale/2.0);
                }
                scaleCombo.setSelectedIndex(index);
            } else {
                Rectangle rect = imageScrollPane.getViewport().getViewRect();
                int scrollUnits = evt.getUnitsToScroll() * imageScrollInc;
                if (evt.isShiftDown())
                    rect.x += scrollUnits;
                else
                    rect.y += scrollUnits;
                imageLabel.scrollRectToVisible(rect);
            }
        }
        @Override
        public void mouseClicked(MouseEvent evt) {
            if (imageIndex < 0 || imageIndex >= images.size())
                return;
            int iconX = (imageLabel.getWidth()-icon.getIconWidth())/2;
            int iconY = (imageLabel.getHeight()-icon.getIconHeight())/2;
            double x = (evt.getX() - iconX);
            double y = (evt.getY() - iconY);
            if (x < 0 || y < 0 || x >= icon.getIconWidth() || y >= icon.getIconHeight())
                return;
            setSelectedColor(new Color(images.get(imageIndex).getRGB((int)(x/scale), (int)(y/scale)),true));
        }
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == scaleCombo){
                double value = scaleModel.get(scaleCombo.getSelectedIndex());
                if (value != scale){
                    scale = value;
                    updateImage();
                }
            } else {
                switch(evt.getActionCommand()){
                    case("Next"):
                        setImageIndex(imageIndex+1);
                        break;
                    case("Previous"):
                        setImageIndex(imageIndex-1);
                }
            }
        }
    }
    
    private class ImageList extends AbstractList<BufferedImage>{
        
        private List<BufferedImage> list = new ArrayList<>();
        
        @Override
        public void add(int index, BufferedImage element){
            boolean wasEmpty = isEmpty();
            list.add(element);
            if (wasEmpty)
                setImageIndex(0);
            else if (imageIndex >= 0 && index <= imageIndex)
                setImageIndex(imageIndex);
            else 
               updateIndexButtons();
        }
        @Override
        public BufferedImage set(int index, BufferedImage element){
            BufferedImage old = list.set(index, element);
            if (index == imageIndex)
                setImageIndex(imageIndex);
            return old;
        }
        @Override
        public BufferedImage remove(int index){
            BufferedImage old = list.remove(index);
            if (imageIndex >= 0){
                if (imageIndex > index)
                    imageIndex--;
                setImageIndex(Math.max(imageIndex, size()-1));
            }
            return old;
        }
        @Override
        public void clear(){
            list.clear();
            setImageIndex(-1);
        }
        @Override
        public boolean remove(Object o){
            int index = indexOf(o);
            if (index < 0)
                return false;
            remove(index);
            return true;
        }
        @Override
        public int indexOf(Object o){
            return list.indexOf(o);
        }
        @Override
        public int lastIndexOf(Object o){
            return list.lastIndexOf(o);
        }
        @Override
        public boolean contains(Object o){
            return list.contains(o);
        }
        @Override
        public BufferedImage get(int index) {
            return list.get(index);
        }
        @Override
        public int size() {
            return list.size();
        }
    }
    
    private class DirectionIcon implements Icon2D{
        
        private int dir;
        /**
         * The Path2D object used to draw the arrow.
         */
        private final Path2D arrow;
        
        private DirectionIcon(int direction){
            this.dir = direction;
            arrow = new Path2D.Double();
            boolean rightward = dir > 0;
                // If this is pointing to the right, place the point on the right of 
                // the icon. Otherwise, place it on the left. This will be the side 
                // with only one point
            arrow.moveTo((rightward) ? (getIconWidth()-1) : 0, getIconHeight()/2.0);
                // If this is pointing to the right, place the next two points on 
                // the left of this icon. Otherwise, place them on the right.
            arrow.lineTo((rightward) ? 0 : (getIconWidth()-1), 0);
            arrow.lineTo(arrow.getCurrentPoint().getX(), getIconHeight()-1);
            arrow.closePath();
        }
        @Override
        public void paintIcon2D(Component c, Graphics2D g, int x, int y) {
            g.translate(x, y);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                // If this is the enabled version of the icon, use the color black. 
                // Otherwise, use gray to indicate that this icon appears grayed out
            g.setColor((c.isEnabled())?java.awt.Color.BLACK:java.awt.Color.GRAY);
            g.fill(arrow);
        }
        @Override
        public int getIconWidth() {
            return 16;
        }
        @Override
        public int getIconHeight() {
            return 16;
        }
    }
}
