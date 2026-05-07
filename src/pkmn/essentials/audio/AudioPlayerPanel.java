/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.audio;

import icons.Icon2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mosblinker
 */
public class AudioPlayerPanel extends JPanel{
    
    public static final long MICROSECONDS_IN_SECOND = 1000000;
    
    public static final String VOLUME_PROPERTY_CHANGED = "VolumePropertyChanged";
    
    public static final String MUTED_PROPERTY_CHANGED = "MutedPropertyChanged";
    
    public static final String CLIP_PROPERTY_CHANGED = "ClipPropertyChanged";
    
    private static final String PLAY_TOGGLE_ACTION_COMMAND = "PlayPause";
    
    private static final String MUTE_TOGGLE_ACTION_COMMAND = "Mute";
    
    public static String formatMicroSecond(long microSecond, boolean showHours){
        microSecond = microSecond/MICROSECONDS_IN_SECOND;  // Convert it into seconds
        String str = "";
        if (showHours){
            str = (microSecond/3600) + ":";
            microSecond %= 60;
        }
        return str+String.format("%02d:%02d", microSecond/60, microSecond%60);
    }
    
    public static String formatMicroSecond(long microSecond){
        return formatMicroSecond(microSecond,microSecond/3600 > 0);
    }
    
    private JSlider timeSlider;
    
    private JToggleButton playToggle;
    
    private JLabel timeLabel;
    
    private JLabel slashLabel;
    
    private JLabel lengthLabel;
    
    private JToggleButton muteToggle;
    
    private JSlider volumeSlider;
    
    private JLabel volumeLabel;
    
    private boolean muted = false;
    
    private int volume = 100;
    
    private Icon playIcon;
    
    private Icon pauseIcon;
    
    private Icon volumeIcon;
    
    private Icon muteIcon;
    
    private volatile FloatControl volCtrl = null;
    
    private volatile BooleanControl muteCtrl = null;
    
    private volatile Clip audioClip = null;
    
    private AudioListener audioListener = new AudioListener();
    
    private AudioLocationObserver audioObserver = null;
    
    private void checkAudioClip(){
        if (audioClip == null || !audioClip.isOpen())
            throw new IllegalStateException();
    }
    
    private void initialize(){
        Handler handler = new Handler();
        
        timeSlider = new JSlider();
        timeSlider.setPaintTicks(false);
        timeSlider.setPaintLabels(false);
        timeSlider.setValue(0);
        timeSlider.addChangeListener(handler);
        
        playIcon = new PlayIcon();
        pauseIcon = new PauseIcon();
        playToggle = new JToggleButton(playIcon);
        playToggle.setSelectedIcon(pauseIcon);
        playToggle.setDisabledIcon(playIcon);
        playToggle.setDisabledSelectedIcon(pauseIcon);
        playToggle.setActionCommand(PLAY_TOGGLE_ACTION_COMMAND);
        playToggle.addActionListener(handler);
        
        volumeSlider = new JSlider();
        volumeSlider.setMaximum(100);
        volumeSlider.setValue(volume);
        volumeSlider.setMajorTickSpacing(50);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(false);
        volumeSlider.addChangeListener(handler);
        
        Ellipse2D e = new Ellipse2D.Double();
        e.setFrameFromDiagonal(20/2.0+1, 0, 20-1, 20);
        e.setFrameFromCenter(e.getCenterX(), 20/2.0, e.getMinX(), (20-e.getWidth())/2.0);
        Path2D disableShape = new Path2D.Double(e);
            // Get the slope of the line
        double k = (e.getMaxY() - e.getMinY()) / (e.getMaxX() - e.getMinX());
            // Get the coefficient for the line
        double m = e.getMaxY() - k * e.getMaxX();
            // Get half of the circle's width, squared
        double s = Math.pow(e.getWidth()/2, 2);
            // Get the a coefficient for the quadratic equation
        double a = 1/s + Math.pow(k, 2)/s;
            // Get the b coefficient for the quadratic equation
        double b = (2*k*(m-e.getCenterY()))/s - (2*e.getCenterX())/s;
            // Get the c coefficient for the quadratic equation
        double c = Math.pow(m-e.getCenterY(), 2)/s - 1 + 
                Math.pow(e.getCenterX(), 2)/s;
            // This will get the roots of the quadratic equation
        double[] roots = new double[2];
            // Solve the quadratic equation
        QuadCurve2D.solveQuadratic(new double[]{c,b,a}, roots);
        
        disableShape.moveTo(roots[0], k*roots[0]+m);
        disableShape.lineTo(roots[1], k*roots[1]+m);
        
        Arc2D arc = new Arc2D.Double();
        arc.setArcByCenter(20-7.5, e.getCenterY(), 7.5, 300, 120, Arc2D.OPEN);
        Path2D soundWaves = new Path2D.Double(arc);
        arc.setArcByCenter(arc.getCenterX()-2.5, arc.getCenterY(), 5, 300, 120, Arc2D.OPEN);
        soundWaves.append(arc, false);
        
        volumeIcon = new SpeakerIcon(soundWaves,Color.BLACK);
        muteIcon = new SpeakerIcon(disableShape,Color.RED);
        
        muteToggle = new JToggleButton(volumeIcon);
        muteToggle.setSelectedIcon(muteIcon);
        muteToggle.setDisabledIcon(volumeIcon);
        muteToggle.setDisabledSelectedIcon(muteIcon);
        muteToggle.setActionCommand(MUTE_TOGGLE_ACTION_COMMAND);
        muteToggle.addActionListener(handler);
        
        timeLabel = new JLabel("00:00");
        timeLabel.setMinimumSize(timeLabel.getMinimumSize());
        
        lengthLabel = new JLabel("00:00");
        lengthLabel.setMinimumSize(lengthLabel.getMinimumSize());
        
        slashLabel = new JLabel(" / ");
        
        volumeLabel = new JLabel("100%");
        volumeLabel.setMinimumSize(volumeLabel.getMinimumSize());
        
        timeSlider.setEnabled(false);
        playToggle.setEnabled(false);
        volumeSlider.setEnabled(false);
        muteToggle.setEnabled(false);
        
        addComponent(playToggle,0,0,0);
        addComponent(timeSlider,1,0.75,6);
        addComponent(timeLabel,2,0,6);
        addComponent(slashLabel,3,0,0);
        addComponent(lengthLabel,4,0,0);
        addComponent(muteToggle,5,0,6);
        addComponent(volumeSlider,6,0.25,6);
    }
    
    private void addComponent(Component c, int x, double weightX, int leftInset){
           // This is the constraints object to use to place the component
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = 0;
        constraints.weightx = weightX;
        if (leftInset != 0)
            constraints.insets = new Insets(0,leftInset,0,0);
        if (c instanceof JToggleButton)
            constraints.weighty = 0.9;
        if (c instanceof JToggleButton || c instanceof JSlider)
            constraints.fill = GridBagConstraints.BOTH;
        add(c,constraints);
    }
    
    public AudioPlayerPanel(){
        super(new GridBagLayout());
        initialize();
    }
    
    public Clip getClip(){
        return audioClip;
    }
    
    public void setClip(Clip clip){
        if (audioClip == clip)
            return;
        Clip old = audioClip;
        audioClip = clip;
        if (old != null)
            old.removeLineListener(audioListener);
        if (audioClip != null)
            audioClip.addLineListener(audioListener);
        firePropertyChange(CLIP_PROPERTY_CHANGED,old,clip);
        if (audioClip != null && audioClip.isOpen()){
            lineOpened();
            if (audioClip.isRunning())
                lineStarted();
            else
                lineStopped();
        } else {
            lineStopped();
            lineClosed();
        }
    }
    
    public void start(){
        checkAudioClip();
        audioClip.start();
    }
    
    public void stop(){
        checkAudioClip();
        audioClip.stop();
    }
    
    public boolean isPlaying(){
        return audioClip != null && audioClip.isOpen() && audioClip.isRunning();
    }
    
    public boolean isMuted(){
        return muted;
    }
    
    public void setMuted(boolean value){
        if (muted != value){
            muted = value;
            muteToggle.setSelected(value);
            firePropertyChange(MUTED_PROPERTY_CHANGED,!value,value);
        }
        if (volume == 0 && !muted)
            setVolume(50);
        else
            volumeSlider.setValue((muted)?0:volume);
    }
    
    public int getVolume(){
        return volume;
    }
    
    public void setVolume(int volume){
        if (volume < 0 || volume > 100)
            throw new IllegalArgumentException("Invalid volume " + volume);
        if (this.volume == volume)
            return;
        int old = this.volume;
        this.volume = volume;
        firePropertyChange(VOLUME_PROPERTY_CHANGED,old,volume);
        if (!muted)
            volumeSlider.setValue(volume);
    }
    
    private void updateTimeElapsed(long elapsed){
        timeLabel.setText(formatMicroSecond(elapsed));
        timeSlider.setValue((int)(elapsed/MICROSECONDS_IN_SECOND));
    }
    
    private void updateVolume(){
        int volume = volumeSlider.getValue();//(muteToggle.isSelected()) ? 0 : this.volume;
        if (muteCtrl != null)
            muteCtrl.setValue(volume == 0);
        if (volCtrl != null){
            if (volume == 100)
                volCtrl.setValue(0);
            else
                volCtrl.setValue(-(float)((20*Math.log(100-volume))/(Math.log(2)+Math.log(5))));
        }
        volumeLabel.setText(volume + "%");
    }
    
    private void createVolumeControls(){
        if (audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN))
            volCtrl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        else
            volCtrl = null;
        if (audioClip.isControlSupported(BooleanControl.Type.MUTE))
            muteCtrl = (BooleanControl) audioClip.getControl(BooleanControl.Type.MUTE);
        else
            muteCtrl = null;
        updateAudioControlsEnabled();
        updateVolume();
    }
    
    private void lineStarted(){
        audioObserver = new AudioLocationObserver();
        audioObserver.execute();
        if (!timeSlider.getValueIsAdjusting())
            playToggle.setSelected(true);
    }
    
    private void lineOpened(){
        lengthLabel.setText(formatMicroSecond(audioClip.getMicrosecondLength()));
        timeSlider.setMaximum((int)(audioClip.getMicrosecondLength()/MICROSECONDS_IN_SECOND));
        updateTimeElapsed(audioClip.getMicrosecondPosition());
        createVolumeControls();
        updatePlaybackComponentsEnabled();
    }
    
    private void lineStopped(){
        if (!timeSlider.getValueIsAdjusting())
            playToggle.setSelected(false);
    }
    
    private void lineClosed(){
        volCtrl = null;
        muteCtrl = null;
        volumeSlider.setEnabled(false);
        muteToggle.setEnabled(false);
        updatePlaybackComponentsEnabled();
    }
    
    private void updateAudioControlsEnabled(){
        muteToggle.setEnabled(isEnabled() && muteCtrl != null);
        volumeSlider.setEnabled(isEnabled() && volCtrl != null);
    }
    
    private void updatePlaybackComponentsEnabled(boolean lineOpen){
        timeSlider.setEnabled(isEnabled() && lineOpen);
        playToggle.setEnabled(timeSlider.isEnabled());
    }
    
    private void updatePlaybackComponentsEnabled(){
        updatePlaybackComponentsEnabled(audioClip != null && audioClip.isOpen());
    }
    
    private class AudioListener implements PropertyChangeListener, LineListener{

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            
        }

        @Override
        public void update(LineEvent evt) {
            if (LineEvent.Type.START.equals(evt.getType())){
                lineStarted();
            } else if (LineEvent.Type.OPEN.equals(evt.getType())){
                lineOpened();
            } else if (LineEvent.Type.STOP.equals(evt.getType())){
                lineStopped();
            } else if (LineEvent.Type.CLOSE.equals(evt.getType())){
                lineClosed();
            }
        }
        
    }
    
    private class AudioLocationObserver extends SwingWorker<Void,Void>{

        @Override
        protected Void doInBackground() throws Exception {
            while (isPlaying()) {
                updateTimeElapsed(audioClip.getMicrosecondPosition());
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException ignore){}
            }
            return null;
        }
        
        @Override
        protected synchronized void done(){
            
        }
    }
    
    private class Handler implements ActionListener, ChangeListener{

        @Override
        public void actionPerformed(ActionEvent evt) {
            switch(evt.getActionCommand()){
                case(PLAY_TOGGLE_ACTION_COMMAND):
                    if (playToggle.isSelected())
                        start();
                    else
                        stop();
                    break;
                case(MUTE_TOGGLE_ACTION_COMMAND):
                    setMuted(muteToggle.isSelected());
            }
        }

        @Override
        public void stateChanged(ChangeEvent evt) {
            if (volumeSlider == evt.getSource()){
                if (volumeSlider.getValue() == 0)
                    muteToggle.setSelected(true);
                updateVolume();
            } else if (timeSlider == evt.getSource()){
                if (timeSlider.getValueIsAdjusting()){
                    if (isPlaying())
                        audioClip.stop();
                } else {
                    if (playToggle.isSelected()){
                        audioClip.setMicrosecondPosition(timeSlider.getValue()*MICROSECONDS_IN_SECOND);
                        audioClip.start();
                    }
                }
            }
        }
    }
    
    private abstract class ButtonIcon implements Icon2D{
        
        protected abstract Color getColor();
        
        protected Color getDisabledColor(){
            return Color.GRAY;
        }
        
        protected abstract void paintShapes(Graphics2D g);
        
        @Override
        public void paintIcon2D(Component c, Graphics2D g, int x, int y) {
            g.translate(x, y);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                // If this is the enabled version of the icon, use the color black. 
                // Otherwise, use gray to indicate that this icon appears grayed out
            g.setColor((c.isEnabled())?getColor():getDisabledColor());
            paintShapes(g);
        }
        
        @Override
        public int getIconWidth() {
            return 20;
        }
        @Override
        public int getIconHeight() {
            return 20;
        }
    }
    
    private class PlayIcon extends ButtonIcon{
        /**
         * The Path2D object used to draw the arrow.
         */
        private final Path2D path;
        
        PlayIcon(){
            path = new Path2D.Double();
                // If this is pointing to the right, place the point on the right of 
                // the icon. Otherwise, place it on the left. This will be the side 
                // with only one point
            path.moveTo(getIconWidth()-1, getIconHeight()/2.0);
                // If this is pointing to the right, place the next two points on 
                // the left of this icon. Otherwise, place them on the right.
            path.lineTo(0, 0);
            path.lineTo(path.getCurrentPoint().getX(), getIconHeight()-1);
            path.closePath();
        }
        @Override
        protected Color getColor() {
            return Color.BLACK;
        }
        @Override
        protected void paintShapes(Graphics2D g) {
            g.fill(path);
        }
    }
    
    private class PauseIcon extends ButtonIcon{
        /**
         * The Path2D object used to draw the arrow.
         */
        private final Path2D path;
        
        PauseIcon(){
            path = new Path2D.Double();
            Rectangle2D rect = new Rectangle2D.Double(0,0,getIconWidth()/3.0,getIconHeight());
            path.append(rect, false);
            rect.setFrame(rect.getMaxX()+rect.getWidth(), rect.getY(), 
                    rect.getWidth(), rect.getHeight());
            path.append(rect, false);
        }
        @Override
        protected Color getColor() {
            return Color.BLACK;
        }
        @Override
        protected void paintShapes(Graphics2D g) {
            g.fill(path);
        }
    }
    
    private class SpeakerIcon extends ButtonIcon{
        
        protected final Path2D speakerShape;
        protected final Path2D effectShape;
        protected final Color iconColor;
        
        SpeakerIcon(Path2D effect, Color color){
            double middleSpeaker = getIconWidth()/5.0;
            speakerShape = new Path2D.Double();
            speakerShape.moveTo(getIconWidth()/2.0, 0);
            speakerShape.lineTo(getIconWidth()/2.0, getIconHeight());
            speakerShape.lineTo(middleSpeaker, getIconHeight()-5);
            speakerShape.lineTo(0, getIconHeight()-5);
            speakerShape.lineTo(0, 5);
            speakerShape.lineTo(middleSpeaker, 5);
            speakerShape.closePath();
            
            effectShape = effect;
            iconColor = color;
        }
        
        protected Path2D getEffectShape(){
            return effectShape;
        }
        
        @Override
        protected void paintShapes(Graphics2D g) {
            g.fill(speakerShape);
            g.setStroke(new BasicStroke(2f));
            g.draw(getEffectShape());
        }

        @Override
        protected Color getColor() {
            return iconColor;
        }
    }
}
