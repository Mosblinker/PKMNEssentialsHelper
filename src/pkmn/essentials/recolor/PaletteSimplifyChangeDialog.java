/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pkmn.essentials.PrecisionSlider;

/**
 *
 * @author Mosblinker
 */
public class PaletteSimplifyChangeDialog extends AbstractPaletteColorChangeDialog{
    
    @Override
    protected JPanel constructControlPanel() {
        Handler handler = new Handler();
        
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        
        colorButton = new JButton();
        colorButton.setActionCommand("SourceColor");
        colorButton.setRolloverEnabled(false);
        colorButton.addActionListener(handler);
        colorButton.setEnabled(false);
        JLabel colorLabel = new JLabel("Source Color:");
        colorLabel.setLabelFor(colorButton);
        slider = new PrecisionSlider();
        slider.setStepSize(0.001);
        slider.setMinorTickSpacing(0.05);
        slider.setMajorTickSpacing(0.1);
        JLabel sliderLabel = new JLabel("Threshold:");
        sliderLabel.setLabelFor(slider);
        slider.addChangeListener(handler);
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panel.add(colorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        panel.add(colorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        panel.add(sliderLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        panel.add(slider, constraints);
        
        return panel;
    }
    
    public PaletteSimplifyChangeDialog(){
        super();
        colorButton.setBackground(color);
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        Objects.requireNonNull(color);
        if (Objects.equals(this.color, color))
            return;
        Color old = this.color;
        this.color = color;
        colorButton.setBackground(this.color);
        firePropertyChange("color",old,color);
        updatePaletteChanges();
    }
    
    public double getThreshold(){
        return slider.getValue();
    }
    
    public void setThreshold(double threshold){
        if (threshold == getThreshold())
            return;
        slider.setValue(threshold);
    }
    
    public int showDialog(Component parent, List<Color> palette, Color color){
            // If the dialog is not showing
        if (getDialog() == null){
//            paletteChanges.clear();
//            if (palette != null)
//                setPalette(palette);
            setColor((color != null)?color:Color.BLACK);
            setThreshold(0.0);
        }
        return super.showDialog(parent,palette);
    }
    
    @Override
    public int showDialog(Component parent, List<Color> palette){
        return showDialog(parent,palette,null);
    }
    
    @Override
    public void reset(){
        applyToModel.clear();
        setColor(Color.BLACK);
        setThreshold(0.0);
        super.reset();
    }
    
    @Override
    protected void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        changes.clear();
        int threshold = (int)Math.floor(getThreshold()*255);
        int[] rgb = new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()};
        int[] minRGB = new int[rgb.length];
        int[] maxRGB = new int[rgb.length];
        for (int i = 0; i < rgb.length; i++){
            minRGB[i] = Math.max(rgb[i] - threshold, 0);
            maxRGB[1] = Math.min(rgb[i] + threshold, 255);
        }
        for (Integer index : list){
            Color temp = palette.get(index);
            int[] tempRGB = new int[]{temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha()};
            boolean match = true;
            for (int i = 0; i < rgb.length && match; i++){
                match = minRGB[i] >= tempRGB[i] && tempRGB[i] >= maxRGB[i];
            }
            if (match)
                changes.put(index, color);
        }
    }
    
    @Override
    protected void updateColorButtonsEnabled(boolean enabled) {
        colorButton.setEnabled(enabled);
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            slider.setEnabled(enabled);
        } catch (NullPointerException ex) {}
    }
    
    private JButton colorButton;
    
    private Color color = Color.BLACK;
    
    private PrecisionSlider slider;
    
    private class Handler implements ActionListener, ChangeListener{

        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case("SourceColor"):
                    setColor(showColorSelector(color));
                    break;
            }
        }
        @Override
        public void stateChanged(ChangeEvent evt) {
            updatePaletteChanges();
        }
    }
}
