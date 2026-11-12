/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.audio;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mosblinker
 */
public class PkmnAudioFilePanel extends JPanel{
    
    public static final String FILE_PROPERTY_CHANGED = "FilePropertyChanged";
    
    private AudioPlayerPanel audioPanel;
    
    private JLabel fileLabel;
    
    private File file = null;
    
    private AudioInputStream audioIn = null;
    
    private void initialize(){
        fileLabel = new JLabel();
        audioPanel = new AudioPlayerPanel();
        audioPanel.setOpaque(false);
        
        add(fileLabel,BorderLayout.PAGE_START);
        add(audioPanel,BorderLayout.CENTER);
    }
    
    public PkmnAudioFilePanel(){
        super(new BorderLayout());
        initialize();
    }
    
    public PkmnAudioFilePanel(File file){
        this();
        PkmnAudioFilePanel.this.setFile(file);
    }
    
    public File getFile(){
        return file;
    }
    
    public void setFile(File file){
        if (this.file == file)
            return;
        File old = this.file;
        this.file = file;
        firePropertyChange(FILE_PROPERTY_CHANGED,old,file);
        fileLabel.setText((file != null)?file.getName() : "");
    }
    
    public Clip getClip(){
        return audioPanel.getClip();
    }
    
    public AudioInputStream getAudioInputStream(){
        return audioIn;
    }
    
    public AudioPlayerPanel getAudioPlayerPanel(){
        return audioPanel;
    }
    
//    @Override
//    public void setBackground(Color color){
//        super.setBackground(color);
//        if (audioPanel != null)
//            audioPanel.setBackground(color);
//    }
    
    public void open() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        if (file == null)
            throw new IllegalStateException();
        try{
            close();
        } catch (IOException ex) {}
        audioIn = AudioSystem.getAudioInputStream(file);
        Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,audioIn.getFormat()));
        audioPanel.setClip(clip);
        clip.open(audioIn);
    }
    
    public void close() throws IOException{
        if (audioPanel.getClip() != null)
            audioPanel.getClip().close();
        if (audioIn != null)
            audioIn.close();
    }
    
    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if (audioPanel != null)
            audioPanel.setEnabled(enabled);
    }
}
