/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kincbe10
 */
public class AudioSignal {
    public double[] impulseCoeff;
    public byte[] b;
    
    public AudioSignal(String filepath) throws UnsupportedAudioFileException{
        this.b = getAudioBytes(filepath);
        this.impulseCoeff = getAudioCoefficients(this.b);
    }
    private static byte[] getAudioBytes(String filepath){
        byte[] by = null;
        File f = new File(filepath);
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(f);
            int size = ais.available();
            by = new byte[size];
            //res = new double[size];
            ais.read(by);
            ais.close();
        }
        catch (IOException ex) {
            Logger.getLogger(AudioSignal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return by;
        
        } 
    
    
    private static double[] getAudioCoefficients(byte[] by) {
        double[] res = new double[by.length/2];
        int i = 0;
        int k = 0;
        while(i < by.length){
            res[k] = (double)((by[i] & 0xff) | (by[i + 1] << 8));
            i = i + 2;
            k++;
        }
        return res;
    }

}
