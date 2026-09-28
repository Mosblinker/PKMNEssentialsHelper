/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pkmn.essentials.PrecisionSlider;

/**
 *
 * @author Mosblinker
 */
public class SimplifyPaletteChanger extends AbstractPaletteChangePanel{

    @Override
    protected void constructPanel() {
        Handler handler = new Handler();
        
        setLayout(new java.awt.GridBagLayout());
        
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
        add(colorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        add(colorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        add(sliderLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        add(slider, constraints);
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

    @Override
    protected String getDefaultCardName() {
        return "paletteSimplifier";
    }

    @Override
    protected String getDefaultDialogTitle() {
        return "Simplify Palette";
    }

    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        changes.clear();
        int threshold = (int)Math.floor(getThreshold()*255);
        int[] rgb = new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()};
        int[] minRGB = new int[rgb.length];
        int[] maxRGB = new int[rgb.length];
        for (int i = 0; i < rgb.length; i++){
            minRGB[i] = Math.max(rgb[i] - threshold, 0);
            maxRGB[i] = Math.min(rgb[i] + threshold, 255);
        }
        for (Integer index : list){
            Color temp = palette.get(index);
            int[] tempRGB = new int[]{temp.getRed(), temp.getGreen(), temp.getBlue(), temp.getAlpha()};
            boolean match = true;
            for (int i = 0; i < rgb.length && match; i++){
                match = minRGB[i] <= tempRGB[i] && tempRGB[i] <= maxRGB[i];
            }
            if (match)
                changes.put(index, color);
        }
    }

    @Override
    public void prepareToShow(List<Color> palette, Color color) {
        setColor((color!=null)?color:Color.BLACK);
        setThreshold(0.0);
    }

    @Override
    public String getAcceptButtonTooltipText() {
        return "Simpify";
    }
    
    @Override
    public void reset(){
        setColor(Color.BLACK);
        setThreshold(0.0);
    }

    @Override
    public boolean usesColorChooser() {
        return true;
    }
    @Override
    protected void updateColorElementsEnabled(boolean enabled){
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
