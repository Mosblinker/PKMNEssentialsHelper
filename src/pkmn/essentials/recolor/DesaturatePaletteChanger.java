/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public class DesaturatePaletteChanger extends AbstractPaletteChangePanel{

    @Override
    protected void constructPanel() {
        Handler handler = new Handler();
        setLayout(new GridBagLayout());
        
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        JLabel label = new JLabel("Desaturate:");
        add(label,constraints);
        
        modeCombo = new JComboBox<>(new String[]{
            "Luminance",
            "Luma",
            "Lightness (HSL)",
            "Average",
            "Value (HSV)"
        });
        modeCombo.addActionListener(handler);
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.weightx = 0.9;
        constraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(modeCombo,constraints);
        
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.weighty = 0.9;
        add(new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0)), constraints);
    }

    @Override
    protected String getDefaultCardName() {
        return "desaturatePalette";
    }

    @Override
    protected String getDefaultDialogTitle() {
        return "Desaturate Colors";
    }
    
    public void setDesaturateMode(int mode){
        if (this.mode != mode){
            int old = this.mode;
            this.mode = mode;
            firePropertyChange("desaturateMode",old,mode);
            updatePaletteChanges();
        }
        if (modeCombo.getSelectedIndex() != mode)
            modeCombo.setSelectedIndex(mode);
    }
    
    public int getDesaturateMode(){
        return mode;
    }

    @Override
    public void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer index : list){
            Color color = palette.get(index);
            int gray = Math.round(PkmnHelperUtilities.toGrayscale(color.getRGB(), mode)*255);
            changes.put(index, new Color(gray,gray,gray,color.getAlpha()));
        }
    }
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            modeCombo.setEnabled(enabled);
        } catch (NullPointerException ex) {}
    }

    @Override
    public void prepareToShow(List<Color> palette, Color color) {
        setDesaturateMode(0);
    }

    @Override
    public String getAcceptButtonTooltipText() {
        return "Desaturate colors";
    }

    @Override
    public void reset() {
        setDesaturateMode(0);
    }
    
    private int mode = 0;
    
    private JComboBox<String> modeCombo;
    
    private class Handler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt) {
            setDesaturateMode(modeCombo.getSelectedIndex());
        }
    }
}
