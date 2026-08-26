/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.recolor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pkmn.essentials.PkmnHelperUtilities;

/**
 *
 * @author Mosblinker
 */
public class PaletteDesaturateChangeDialog extends AbstractPaletteChangeDialog{

    @Override
    protected JPanel constructControlPanel() {
        Handler handler = new Handler();
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel label = new JLabel("Desaturate:");
        panel.add(label,BorderLayout.LINE_START);
        
        modeCombo = new JComboBox<>(new String[]{
            "Luminance",
            "Luma",
            "Lightness (HSL)",
            "Average",
            "Value (HSV)"
        });
        modeCombo.addActionListener(handler);
        panel.add(modeCombo,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected void updatePaletteChanges(List<Integer> list, List<Color> palette, Map<Integer, Color> changes) {
        for (Integer index : list){
            Color color = palette.get(index);
            int gray = Math.round(PkmnHelperUtilities.toGrayscale(color.getRGB(), mode)*255);
            changes.put(index, new Color(gray,gray,gray,color.getAlpha()));
        }
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
    public int showDialog(Component parent, List<Color> palette){
            // If the dialog is not showing
        if (getDialog() == null){
            setDesaturateMode(0);
        }
        return super.showDialog(parent,palette);
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            modeCombo.setEnabled(enabled);
        } catch (NullPointerException ex) {}
    }
    
    @Override
    public void reset(){
        applyToModel.clear();
        setDesaturateMode(0);
        super.reset();
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
