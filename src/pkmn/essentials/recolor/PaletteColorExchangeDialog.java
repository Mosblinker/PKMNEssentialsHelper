/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import components.JColorSelector;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public class PaletteColorExchangeDialog extends AbstractPaletteChangeDialog{
    
    @Override
    protected JPanel constructControlPanel() {
        Handler handler = new Handler();
        
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        
        fromColorButton = new JButton();
        fromColorButton.setActionCommand("FromColor");
        fromColorButton.setRolloverEnabled(false);
        fromColorButton.addActionListener(handler);
        fromColorButton.setEnabled(false);
        JLabel fromColorLabel = new JLabel("From Color:");
        fromColorLabel.setLabelFor(fromColorButton);
        toColorButton = new JButton();
        toColorButton.setActionCommand("ToColor");
        toColorButton.setRolloverEnabled(false);
        toColorButton.addActionListener(handler);
        toColorButton.setEnabled(false);
        JLabel toColorLabel = new JLabel("To Color:");
        toColorLabel.setLabelFor(toColorButton);
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        panel.add(fromColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        panel.add(fromColorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        panel.add(toColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        panel.add(toColorButton, constraints);
        return panel;
    }
    
    public PaletteColorExchangeDialog(){
        super();
        fromColorButton.setBackground(fromColor);
        toColorButton.setBackground(toColor);
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
    
    public Color getFromColor(){
        return fromColor;
    }
    
    public void setFromColor(Color color){
        if (Objects.equals(fromColor, color))
            return;
        Color old = fromColor;
        fromColor = color;
        fromColorButton.setBackground(fromColor);
        firePropertyChange("fromColor",old,color);
        updatePaletteChanges();
    }
    
    public Color getToColor(){
        return toColor;
    }
    
    public void setToColor(Color color){
        if (Objects.equals(toColor, color))
            return;
        Color old = toColor;
        toColor = color;
        toColorButton.setBackground(toColor);
        firePropertyChange("toColor",old,color);
        updatePaletteChanges();
    }
    
    public int showDialog(Component parent, List<Color> palette, Color fromColor){
            // If the dialog is not showing
        if (getDialog() == null){
//            paletteChanges.clear();
//            if (palette != null)
//                setPalette(palette);
            setFromColor((fromColor != null)?fromColor:Color.WHITE);
            setToColor(Color.BLACK);
        }
        System.out.println("Hello");
        return super.showDialog(parent,palette);
    }
    
    @Override
    public int showDialog(Component parent, List<Color> palette){
        return showDialog(parent,palette,null);
    }

    @Override
    protected String getDefaultAcceptButtonToolTipText() {
        return "Apply color exchange";
    }
    
    @Override
    public void reset(){
        applyToModel.clear();
        setFromColor(Color.WHITE);
        setToColor(Color.BLACK);
        super.reset();
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
    
    @Override
    protected void updatePaletteChanges(List<Integer> list, List<Color> palette, 
            Map<Integer,Color> changes){
        int[][] inputColors = {
            {fromColor.getRed(),fromColor.getGreen(),fromColor.getBlue()},
            {toColor.getRed(),toColor.getGreen(),toColor.getBlue()}
        };
        double[] colorDiff = new double[3];
        double[][] cutoff = new double[2][3];
        for (int i = 0; i < 3; i++){
            double fromC = inputColors[0][i]/255.0;
            colorDiff[i] = (inputColors[1][i]/255.0) - fromC;
            cutoff[0][i] = Math.min(Math.max(fromC - 1.0,0.0),1.0);
            cutoff[1][i] = Math.min(Math.max(fromC + 1.0,0.0),1.0);
        }
        for (Integer index : list){
            Color color = palette.get(index);
            double[] values = {color.getRed(),color.getGreen(),color.getBlue()};
            for (int i = 0; i < 3; i++){
                values[i] = Math.min(Math.max((values[i]/255.0) + colorDiff[i],0),1);
            }
            Color newColor = new Color((float)values[0],(float)values[1],(float)values[2],(float)(color.getAlpha()/255.0));
            changes.put(index, newColor);
        }
    }
    
    private void updateColorButtonsEnabled(){
        fromColorButton.setEnabled(isEnabled() && colorSelector != null);
        toColorButton.setEnabled(fromColorButton.isEnabled());
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            updateColorButtonsEnabled();
        } catch (NullPointerException ex) {}
    }
    
    private JButton fromColorButton;
    
    private JButton toColorButton;
    
    private Color fromColor = Color.WHITE;
    
    private Color toColor = Color.BLACK;
    
    private JColorSelector colorSelector = null;
    
    private class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case("FromColor"):
                    setFromColor(showColorSelector(fromColor));
                    break;
                case("ToColor"):
                    setToColor(showColorSelector(toColor));
            }
        }
    }
}
