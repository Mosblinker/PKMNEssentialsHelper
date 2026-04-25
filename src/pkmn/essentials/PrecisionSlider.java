/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mosblinker
 */
public class PrecisionSlider extends JPanel{
    
    private void initialize(){
        slider = new JSlider();
        slider.setMinimum(0);
        updateSliderValues();
        slider.setValue(0);
        slider.setPaintTicks(true);
        spinnerModel = new SpinnerNumberModel(value,minValue,maxValue,stepValue);
        spinner = new JSpinner(spinnerModel);
        spinner.setEditor(new JSpinner.NumberEditor(spinner, spinnerFormat));
        Handler handler = new Handler();
        slider.addChangeListener(handler);
        spinner.addChangeListener(handler);
        slider.addPropertyChangeListener(handler);
        add(slider, BorderLayout.CENTER);
        add(spinner,BorderLayout.LINE_END);
    }
    
    public PrecisionSlider(){
        super(new BorderLayout(6, 7));
        initialize();
    }
    
    public double getValue(){
        return value;
    }
    
    public void setValue(double value){
        boolean temp = settingValues;
        settingValues = true;
        boolean change = this.value != value;
        this.value = value;
        spinner.setValue(value);
        slider.setValue((int)Math.ceil((value-minValue)/stepValue));
        settingValues = temp;
        if (change)
            fireChangeEvent();
    }
    
    private void updateSliderValues(){
        slider.setMaximum((int)Math.ceil((maxValue-minValue)/stepValue));
        slider.setValue((int)Math.ceil((value-minValue)/stepValue));
        slider.setMajorTickSpacing((int)Math.ceil(majorTickSpacing/stepValue));
        slider.setMinorTickSpacing((int)Math.ceil(minorTickSpacing/stepValue));
    }
    
    public double getMinimum(){
        return minValue;
    }
    
    public void setMinimum(double minimum){
        if (minValue == minimum)
            return;
        boolean temp = settingValues;
        settingValues = true;
        minValue = minimum;
        spinnerModel.setMinimum(minimum);
        updateSliderValues();
        settingValues = temp;
        fireChangeEvent();
    }
    
    public double getMaximum(){
        return maxValue;
    }
    
    public void setMaximum(double maximum){
        if (maxValue == maximum)
            return;
        boolean temp = settingValues;
        settingValues = true;
        maxValue = maximum;
        spinnerModel.setMaximum(maximum);
        updateSliderValues();
        settingValues = temp;
        fireChangeEvent();
    }
    
    public double getStepSize(){
        return stepValue;
    }
    
    public void setStepSize(double stepValue){
        if (this.stepValue == stepValue)
            return;
        boolean temp = settingValues;
        settingValues = true;
        this.stepValue = stepValue;
        spinnerModel.setStepSize(stepValue);
        updateSliderValues();
        settingValues = temp;
        fireChangeEvent();
    }
    
    public double getMajorTickSpacing(){
        return majorTickSpacing;
    }
    
    public void setMajorTickSpacing(double value){
        if (value == majorTickSpacing)
            return;
        double old = majorTickSpacing;
        majorTickSpacing = value;
        slider.setMajorTickSpacing((int)Math.ceil(majorTickSpacing/stepValue));
        firePropertyChange("majorTickSpacing",old,value);
    }
    
    public double getMinorTickSpacing(){
        return minorTickSpacing;
    }
    
    public void setMinorTickSpacing(double value){
        if (value == minorTickSpacing)
            return;
        double old = minorTickSpacing;
        minorTickSpacing = value;
        slider.setMinorTickSpacing((int)Math.ceil(minorTickSpacing/stepValue));
        firePropertyChange("minorTickSpacing",old,value);
    }
    
    public boolean getPaintTicks(){
        return slider.getPaintTicks();
    }
    
    public void setPaintTicks(boolean value){
        slider.setPaintTicks(value);
    }
    
    public boolean getPaintTrack(){
        return slider.getPaintTrack();
    }
    
    public void setPaintTrack(boolean value){
        slider.setPaintTrack(value);
    }
    
    public boolean getSnapToTicks(){
        return slider.getSnapToTicks();
    }
    
    public void setSnapToTicks(boolean value){
        slider.setSnapToTicks(value);
    }
    
    public String getSpinnerDecimalFormatPattern(){
        return spinnerFormat;
    }
    
    public void setSpinnerDecimalFormatPattern(String pattern){
        Objects.requireNonNull(pattern);
        if (spinnerFormat.equals(pattern))
            return;
        String old = spinnerFormat;
        spinnerFormat = pattern;
        spinner.setEditor(new JSpinner.NumberEditor(spinner, spinnerFormat));
        firePropertyChange("spinnerDecimalFormatPattern",old,pattern);
    }
    
    public JSlider getSlider(){
        return slider;
    }
    
    public JSpinner getSpinner(){
        return spinner;
    }
    
    public void addChangeListener(ChangeListener l){
        if (l != null)
            listenerList.add(ChangeListener.class, l);
    }
    
    public void removeChangeListener(ChangeListener l){
        listenerList.remove(ChangeListener.class, l);
    }
    
    public ChangeListener[] getChangeListeners(){
        return listenerList.getListeners(ChangeListener.class);
    }
    
    protected void fireChangeEvent(){
        ChangeEvent evt = new ChangeEvent(this);
        for (ChangeListener l : getChangeListeners())
            if (l != null)
                l.stateChanged(evt);
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        try{
            slider.setEnabled(enabled);
            spinner.setEnabled(enabled);
        } catch (NullPointerException ex){}
    }
    
    private JSlider slider;
    
    private JSpinner spinner;
    
    private SpinnerNumberModel spinnerModel;
    
    private String spinnerFormat = "0.00%";
    
    private boolean settingValues = false;
    
    private double value = 0;
    
    private double minValue = 0.0;
    
    private double maxValue = 1.0;
    
    private double stepValue = 0.0001;
    
    private double majorTickSpacing = 0.1;
    
    private double minorTickSpacing = 0.05;
    
    private class Handler implements ChangeListener, PropertyChangeListener{
        @Override
        public void stateChanged(ChangeEvent evt) {
            if (settingValues)
                return;
            double oldValue = value;
            if (evt.getSource() == slider){
                value = Math.min(minValue+slider.getValue()*stepValue,maxValue);
                spinner.setValue(value);
            } else if (evt.getSource() == spinner){
                value = (double) spinner.getValue();
                if (oldValue != value)
                    slider.setValue((int)Math.ceil((value-minValue)/stepValue));
            }
            if (oldValue != value)
                fireChangeEvent();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() == slider){
                switch(evt.getPropertyName()){
                    case("paintTicks"):
                    case("paintTrack"):
                    case("snapToTicks"):
                        firePropertyChange(evt.getPropertyName(),evt.getOldValue(),evt.getNewValue());
                }
            }
        }
        
    }
}
