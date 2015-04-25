/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author kincbe10
 */
public class WavFile {
    private final AudioSignal sig;
    private final String filepath;
    private final File file;
    private static SourceDataLine line;
    
    public WavFile(AudioSignal as, String fp) throws LineUnavailableException{
        this.filepath = fp;
        this.sig = as;
        this.file = new File(filepath);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.sig.getSignalFormat());
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(this.sig.getSignalFormat(), this.sig.getSampleBytes().length);
        line.start();
    }
    
    public void close(){
        line.drain();
        line.stop();
    }
    
    public void playLines(){
       // for(int i = 0; i < this.sig.getSamples().length; i ++){
            line.write(this.sig.getSampleBytes(), 0, this.sig.getSampleBytes().length);
        //}
        this.close();
    
    }
    
    public boolean save(){
        AudioFormat format = sig.getSignalFormat();
        
        ByteArrayInputStream bis = new ByteArrayInputStream(this.sig.getSampleBytes());
        AudioInputStream ais = new AudioInputStream(bis, format, this.sig.getSamples().length);
        try {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, this.file);
        } catch (IOException ex) {
            Logger.getLogger(WavFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            
        return true;        
    }
    
    public static void play(byte[] b){
        
    }
    
    public void play(){
        try {
            AudioClip clip = (AudioClip) Applet.newAudioClip(this.file.toURI().toURL());
            //for(int i = 0; i < this.sig.getSamples().length; i++)
                clip.play();
        } catch (MalformedURLException ex) {
            Logger.getLogger(WavFile.class.getName()).log(Level.SEVERE, null, ex);
           // return false;
        }
        //return true;
    }
    
}
