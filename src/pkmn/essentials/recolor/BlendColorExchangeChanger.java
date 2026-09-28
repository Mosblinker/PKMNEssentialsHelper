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
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public class BlendColorExchangeChanger extends AbstractPaletteChangePanel{
    
    public Color getFromColor1(){
        return fromColor1;
    }
    
    public void setFromColor1(Color color){
        Objects.requireNonNull(color);
        fromColor1Button.setBackground(fromColor1);
        if (Objects.equals(fromColor1, color))
            return;
        Color old = fromColor1;
        fromColor1 = color;
        firePropertyChange("fromColor1",old,color);
        updatePaletteChanges();
    }
    
    public Color getFromColor2(){
        return fromColor2;
    }
    
    public void setFromColor2(Color color){
        Objects.requireNonNull(color);
        fromColor2Button.setBackground(fromColor2);
        if (Objects.equals(fromColor2, color))
            return;
        Color old = fromColor2;
        fromColor2 = color;
        firePropertyChange("fromColor2",old,color);
        updatePaletteChanges();
    }
    
    public Color getToColor1(){
        return toColor1;
    }
    
    public void setToColor1(Color color){
        Objects.requireNonNull(color);
        toColor1Button.setBackground(toColor1);
        if (Objects.equals(toColor1, color))
            return;
        Color old = toColor1;
        toColor1 = color;
        firePropertyChange("toColor1",old,color);
        updatePaletteChanges();
    }
    
    public Color getToColor2(){
        return toColor2;
    }
    
    public void setToColor2(Color color){
        Objects.requireNonNull(color);
        toColor2Button.setBackground(toColor2);
        if (Objects.equals(toColor2, color))
            return;
        Color old = toColor2;
        toColor2 = color;
        firePropertyChange("toColor2",old,color);
        updatePaletteChanges();
    }

    @Override
    protected void constructPanel() {
        Handler handler = new Handler();
        setLayout(new java.awt.GridBagLayout());
        fromColor1Button = new JButton();
        fromColor1Button.setActionCommand("FromColor1");
        fromColor1Button.setRolloverEnabled(false);
        fromColor1Button.addActionListener(handler);
        fromColor1Button.setEnabled(false);
        fromColor2Button = new JButton();
        fromColor2Button.setActionCommand("FromColor2");
        fromColor2Button.setRolloverEnabled(false);
        fromColor2Button.addActionListener(handler);
        fromColor2Button.setEnabled(false);
        JLabel fromColorLabel = new JLabel("From Color:");
        fromColorLabel.setLabelFor(fromColor1Button);
        
        toColor1Button = new JButton();
        toColor1Button.setActionCommand("ToColor1");
        toColor1Button.setRolloverEnabled(false);
        toColor1Button.addActionListener(handler);
        toColor1Button.setEnabled(false);
        toColor2Button = new JButton();
        toColor2Button.setActionCommand("ToColor2");
        toColor2Button.setRolloverEnabled(false);
        toColor2Button.addActionListener(handler);
        toColor2Button.setEnabled(false);
        JLabel toColorLabel = new JLabel("To Color:");
        toColorLabel.setLabelFor(toColor1Button);
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(fromColorLabel, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(fromColor1Button, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.5;
        add(fromColor2Button, constraints);
        
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
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(toColor1Button, constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(7, 6, 0, 0);
        constraints.ipady = 15;
        constraints.weightx = 0.5;
        add(toColor2Button, constraints);
        
        fromColor1Button.setBackground(fromColor1);
        fromColor2Button.setBackground(fromColor2);
        toColor1Button.setBackground(toColor1);
        toColor2Button.setBackground(toColor2);
    }

    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer i : list){
            double blend = PkmnHelperUtilities.getColorRatio(fromColor1, fromColor2, palette.get(i));
            changes.put(i, PkmnHelperUtilities.blendColor(toColor1, toColor2, blend));
        }
    }

    @Override
    public void prepareToShow(List<Color> palette, Color color) {
        setFromColor1((color!=null)?color:Color.BLACK);
        setFromColor2(Color.WHITE);
        setToColor1(Color.RED);
        setToColor2(Color.BLUE);
    }
    @Override
    public String getAcceptButtonTooltipText() {
        return "Apply color exchange";
    }
    @Override
    public void reset() {
        setFromColor1(Color.BLACK);
        setFromColor2(Color.WHITE);
        setToColor1(Color.RED);
        setToColor2(Color.BLUE);
    }
    @Override
    protected void updateColorElementsEnabled(boolean enabled) {
        fromColor1Button.setEnabled(enabled);
        fromColor2Button.setEnabled(enabled);
        toColor1Button.setEnabled(enabled);
        toColor2Button.setEnabled(enabled);
    }
    @Override
    public boolean usesColorChooser(){
        return true;
    }
    
    private JButton fromColor1Button;
    private JButton fromColor2Button;
    private JButton toColor1Button;
    private JButton toColor2Button;
    
    private Color fromColor1 = Color.BLACK;
    private Color fromColor2 = Color.WHITE;
    private Color toColor1 = Color.RED;
    private Color toColor2 = Color.BLUE;

    @Override
    protected String getDefaultCardName() {
        return "blendColorExchange";
    }
    @Override
    protected String getDefaultDialogTitle() {
        return "Color Blend Exchange";
    }
    
    private class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case("FromColor1"):
                    setFromColor1(showColorSelector(fromColor1));
                    break;
                case("FromColor2"):
                    setFromColor2(showColorSelector(fromColor2));
                    break;
                case("ToColor1"):
                    setToColor1(showColorSelector(toColor1));
                    break;
                case("ToColor2"):
                    setToColor2(showColorSelector(toColor2));
            }
        }
    }
}
