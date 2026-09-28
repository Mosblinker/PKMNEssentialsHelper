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
import javax.swing.*;

/**
 *
 * @author Mosblinker
 */
public class ColorExchangeChanger extends AbstractPaletteChangePanel{
    
    private JButton fromColorButton;
    
    private JButton toColorButton;
    
    private Color fromColor = Color.WHITE;
    
    private Color toColor = Color.BLACK;

    @Override
    protected void constructPanel() {
        setLayout(new java.awt.GridBagLayout());
        Handler handler = new Handler();
        fromColorButton = new JButton();
        fromColorButton.setActionCommand("FromColor");
        fromColorButton.setRolloverEnabled(false);
        fromColorButton.addActionListener(handler);
        fromColorButton.setEnabled(false);
        fromColorButton.setBackground(fromColor);
        JLabel fromColorLabel = new JLabel("From Color:");
        fromColorLabel.setLabelFor(fromColorButton);
        toColorButton = new JButton();
        toColorButton.setActionCommand("ToColor");
        toColorButton.setRolloverEnabled(false);
        toColorButton.addActionListener(handler);
        toColorButton.setEnabled(false);
        toColorButton.setBackground(toColor);
        JLabel toColorLabel = new JLabel("To Color:");
        toColorLabel.setLabelFor(toColorButton);
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        add(fromColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        add(fromColorButton, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(7, 0, 0, 0);
        add(toColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.9;
        constraints.weighty = 0.5;
        add(toColorButton, constraints);
    }
    
    public Color getFromColor(){
        return fromColor;
    }
    
    public void setFromColor(Color color){
        Objects.requireNonNull(color);
        fromColorButton.setBackground(fromColor);
        if (Objects.equals(fromColor, color))
            return;
        Color old = fromColor;
        fromColor = color;
        firePropertyChange("fromColor",old,color);
        updatePaletteChanges();
    }
    
    public Color getToColor(){
        return toColor;
    }
    
    public void setToColor(Color color){
        Objects.requireNonNull(color);
        toColorButton.setBackground(toColor);
        if (Objects.equals(toColor, color))
            return;
        Color old = toColor;
        toColor = color;
        firePropertyChange("toColor",old,color);
        updatePaletteChanges();
    }
    @Override
    protected String getDefaultCardName(){
        return "Color Exchange";
    }
    @Override
    protected String getDefaultDialogTitle(){
        return "colorExchange";
    }
    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
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

    @Override
    public void prepareToShow(List<Color> palette, Color color) {
        setFromColor((color != null)?color:Color.WHITE);
        setToColor(Color.BLACK);
    }
    @Override
    public String getAcceptButtonTooltipText() {
        return "Apply color exchange";
    }
    @Override
    public void reset() {
        setFromColor(Color.WHITE);
        setToColor(Color.BLACK);
    }
    @Override
    protected void updateColorElementsEnabled(boolean enabled){
        fromColorButton.setEnabled(enabled);
        toColorButton.setEnabled(enabled);
    }
    @Override
    public boolean usesColorChooser(){
        return true;
    }
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
