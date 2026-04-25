/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author Mosblinker
 */
public class RGBInfoPanel extends JPanel{
    
    private static final int LAST_X_VALUE = 2;
    
    private static final int LAST_Y_VALUE = 4;
    
    private JTextField rField;
    private JTextField gField;
    private JTextField bField;
    private JTextField aField;
    private JTextField hexField;
    private JTextField hField;
    private JTextField sField;
    private JTextField vField;
    private JTextField alphaField;
    private Color color = null;
    
    private JTextField addValueField(String name, int length, int x, int y, int fieldWidth){
        JLabel nameLabel = new javax.swing.JLabel(name+":");
        java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.fill = java.awt.GridBagConstraints.VERTICAL;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, (y < LAST_Y_VALUE) ? 7 : 0, 6);
        java.awt.Dimension dim = nameLabel.getPreferredSize();
        dim.width = Math.max(dim.width, 26);
        nameLabel.setPreferredSize(dim);
        add(nameLabel,constraints);
        
        JTextField field = new JTextField();
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setEditable(false);
        nameLabel.setLabelFor(field);
        dim = field.getPreferredSize();
        dim.width = Math.max(dim.width, length);
        field.setPreferredSize(dim);
        constraints = new java.awt.GridBagConstraints();
        constraints.gridx = x+1;
        constraints.gridy = y;
        constraints.gridwidth = fieldWidth;
        constraints.fill = java.awt.GridBagConstraints.BOTH;
        constraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        constraints.insets = new java.awt.Insets(0, 0, (y < LAST_Y_VALUE) ? 7 : 0, (x < LAST_X_VALUE) ? 6 : 0);
        add(field,constraints);
        
        return field;
    }
    
    private void initialize(){
        rField = addValueField("R", 75, 0, 0, 1);
        gField = addValueField("G", 75, 0, 1, 1);
        bField = addValueField("B", 75, 0, 2, 1);
        aField = addValueField("A", 75, 0, 3, 1);
        hField = addValueField("H", 75, 2, 0, 1);
        sField = addValueField("S", 75, 2, 1, 1);
        vField = addValueField("V", 75, 2, 2, 1);
        alphaField = addValueField("A", 75, 2, 3, 1);
        hexField = addValueField("Hex",75,0,4,3);
    }
    
    public RGBInfoPanel(){
        super(new java.awt.GridBagLayout());
        initialize();
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        if (this.color == color)
            return;
        Color old = this.color;
        this.color = color;
        firePropertyChange("color",old,color);
        updateTextFields(color);
    }
    
    protected void updateTextFields(Color color){
        if (color == null){
            for (JTextField field : new JTextField[]{rField,gField,bField,aField,
                hexField,hField,sField,vField,alphaField}){
                field.setText("");
            }
            return;
        }
        rField.setText(""+color.getRed());
        gField.setText(""+color.getGreen());
        bField.setText(""+color.getBlue());
        aField.setText(""+color.getAlpha());
        hexField.setText(String.format("%08X", color.getRGB()));
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hField.setText(String.format("%3.1f °",hsb[0]*360.0));
        sField.setText(String.format("%3.1f %%", hsb[1]*100.0));
        vField.setText(String.format("%3.1f %%", hsb[2]*100.0));
        alphaField.setText(String.format("%3.1f %%", color.getAlpha()/255.0));
    }
}
