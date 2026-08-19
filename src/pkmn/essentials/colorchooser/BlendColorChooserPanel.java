/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.colorchooser;

import components.JColorSelector;
import icons.box.ColorBoxIcon;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pkmn.essentials.PkmnHelperUtilities;
import pkmn.essentials.PrecisionSlider;
import pkmn.essentials.RGBInfoPanel;

/**
 *
 * @author Mosblinker
 */
public class BlendColorChooserPanel extends AbstractColorChooserPanel{
    
    private JColorSelector selector;
    private JButton color1Button;
    private JButton color2Button;
    private ColorBoxIcon color1Icon;
    private ColorBoxIcon color2Icon;
    private PrecisionSlider slider;
    private RGBInfoPanel rgbInfoPanel;
    private boolean updating = false;
    private boolean initialized = false;
    
    /**
     * 
     * @param colorSelector Different one than the one this panel is being added to.
     */
    public BlendColorChooserPanel(JColorSelector colorSelector){
        this.selector = colorSelector;
    }
    
    public JColorSelector getColorSelector(){
        return selector;
    }

    @Override
    public void updateChooser() {
        
    }

    @Override
    protected void buildChooser() {
        if (initialized)
            return;
        setBorder(javax.swing.BorderFactory.createEmptyBorder(13, 12, 13, 12));
        setLayout(new java.awt.GridBagLayout());
        
        Handler handler = new Handler();
        
        color1Icon = new ColorBoxIcon(32,16,Color.BLACK);
        color2Icon = new ColorBoxIcon(32,16,Color.WHITE);
        
        color1Button = new JButton("From Color", color1Icon);
        color1Button.setActionCommand("Color1");
        color1Button.setHorizontalTextPosition(SwingConstants.CENTER);
        color1Button.setVerticalTextPosition(SwingConstants.TOP);
        color1Button.addActionListener(handler);
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 7, 6);
        add(color1Button,constraints);
        
        color2Button = new JButton("To Color", color2Icon);
        color2Button.setActionCommand("Color2");
        color2Button.setHorizontalTextPosition(SwingConstants.CENTER);
        color2Button.setVerticalTextPosition(SwingConstants.TOP);
        color2Button.addActionListener(handler);
        color2Button.setPreferredSize(color1Button.getPreferredSize());
        color2Button.setMaximumSize(color1Button.getMaximumSize());
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, 7, 0);
        add(color2Button,constraints);
        
        slider = new PrecisionSlider();
        slider.setMinimum(0.0);
        slider.setMaximum(1.0);
        slider.setStepSize(0.0001);
        slider.setMinorTickSpacing(0.05);
        slider.setMajorTickSpacing(0.1);
        slider.addChangeListener(handler);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        constraints.anchor = java.awt.GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(0, 0, 7, 6);
        constraints.weightx = 0.9;
        constraints.ipadx = 100;
        add(slider,constraints);
        
        rgbInfoPanel = new RGBInfoPanel();
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.gridwidth = 3;
        add(rgbInfoPanel,constraints);
        
        slider.setValue(0.5);
        
        initialized = true;
    }

    @Override
    public String getDisplayName() {
        return "Blend";
    }
    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }
    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }
    
    private void updateColor(){
        Color color = PkmnHelperUtilities.blendColor(color1Icon.getColor(), color2Icon.getColor(), slider.getValue());
        rgbInfoPanel.setColor(color);
        getColorSelectionModel().setSelectedColor(color);
    }
    
    private class Handler implements ActionListener, ChangeListener{

        @Override
        public void actionPerformed(ActionEvent evt) {
            Color initColor = null;
            switch(evt.getActionCommand()){
                case("Color1"):
                    initColor = color1Icon.getColor();
                    break;
                case("Color2"):
                    initColor = color2Icon.getColor();
            }
            PkmnHelperUtilities.resetColorPreviewColor(selector);
            if (selector.showDialog(BlendColorChooserPanel.this,initColor) == JColorSelector.ACCEPT_OPTION){
                Color color = selector.getSelectedColor();
                switch(evt.getActionCommand()){
                    case("Color1"):
                        color1Icon.setColor(color);
                        color1Button.repaint();
                        break;
                    case("Color2"):
                        color2Icon.setColor(color);
                        color2Button.repaint();
                }
                updateColor();
            }
        }

        @Override
        public void stateChanged(ChangeEvent evt) {
            updateColor();
        }
        
    }
}
