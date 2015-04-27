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
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kincbe10
 */
public class AudioSignal {
    private double[] samples;//sample data after double conversion
    private byte[] b;//byte array of sample data
    private int channels;//num channels
    private int frameSize;//frame size in bytes
    private float frameRate;
    private float sampleRate;
    private AudioFormat.Encoding encoding;
    private int sampleSizeInBits;
    private boolean endianness;
    private AudioFormat format;
    
    
    public AudioSignal(String filepath) throws UnsupportedAudioFileException{
        this.format = getAudioFormat(filepath);
        if(format == null) throw new RuntimeException("Error generating AudioFormat");
        this.channels = format.getChannels();
        this.frameSize = format.getFrameSize();
        this.frameRate = format.getFrameRate();
        this.sampleRate = format.getSampleRate();
        this.encoding = format.getEncoding();
        this.sampleSizeInBits = format.getSampleSizeInBits();
        this.b = getAudioBytes(filepath);
        this.samples = this.getAudioSamples();
    }
    
    public AudioSignal(AudioSignal formatCopy, double[] samp){
        
       
        this.format = new AudioFormat(formatCopy.getEncoding(), formatCopy.getSampleRateFloat(), 
                                                formatCopy.getSampleSizeInBits(), formatCopy.getChannels(), 
                                                    formatCopy.getFrameSize(), formatCopy.getFrameRateFloat(), formatCopy.getEndianness() );
        this.channels = format.getChannels();
        this.frameSize = format.getFrameSize();
        this.frameRate = format.getFrameRate();
        this.sampleRate = format.getSampleRate();
        this.encoding = format.getEncoding();
        this.sampleSizeInBits = format.getSampleSizeInBits();
        this.samples = samp;
        this.b = this.sampleToBytes(samp);
        
        
    }
    
    public AudioSignal(double[] samp, int ch, int fs, float fr, float sr, AudioFormat.Encoding enc, int ss ){
        this.samples = samp;
        this.b = sampleToBytes(samp);
        this.channels = ch;
        this.frameSize = fs;
        this.frameRate = fr;
        this.sampleRate = sr;
        this.encoding = enc;
        this.sampleSizeInBits = ss;
        this.format = new AudioFormat(this.encoding, this.sampleRate, 
                                                this.sampleSizeInBits, this.channels, 
                                                    this.frameSize, this.frameRate, this.endianness );
    }
    
    public double[] getSamples(){
        return this.samples;
    }
    
    public byte[] getSampleBytes(){
        return this.b;
    }
    
    public int getChannels(){
        return this.channels;
    }
    
    public int getFrameSize(){
        return this.frameSize;
    }
    
    public float getFrameRateFloat(){
        return this.frameRate;
    }
    
    public double getFrameRate(){
        return (double)this.frameRate;
    }
    
    public float getSampleRateFloat(){
        return this.sampleRate;
    }
    
    public double getSampleRate(){
        return (double)this.sampleRate;
    }
    
    public int getSampleSizeInBits(){
        return this.sampleSizeInBits;
    }
    
    public AudioFormat.Encoding getEncoding(){
        return this.encoding;
    }
    
    public boolean getEndianness(){
        return this.endianness;
    }
    
    public AudioFormat getSignalFormat(){
        return this.format;
    }
    
    private static AudioFormat getAudioFormat(String filepath){
        File f = new File(filepath);
        AudioInputStream ais = null;
        AudioFormat af = null;
        try {
            ais = AudioSystem.getAudioInputStream(f);
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ais != null)
            af = ais.getFormat();
       
        return af;
    }
    public static void printAudioFormat(String filepath){
        AudioFormat p = getAudioFormat(filepath);
        String s = p.toString();
        System.out.println("Audio Format for " + " " + filepath + ": " + s);
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
        catch (IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioSignal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return by;
        
    } 
    
    
    private double[] getAudioSamples() {
        int numSamples = this.b.length/this.frameSize;
        double[] res = new double[numSamples];
        int i = 0;
        int k = 0;
        while(i < b.length){
            res[k] = (double)(((this.b[i] & 0xff) | (this.b[i + 1] << 8))/(Math.pow(2,this.frameSize*8 )))*2;
            i = i + 2;
            k++;
        }
        return res;
    }
    
    
    
//    public static byte[] sampleToBytes(double[] d){
//        byte[] by = new byte[8*d.length];
//        for(int i = 0; i < d.length; i++){
//            byte[] temp = new byte[8];
//            ByteBuffer.wrap(temp).putDouble(d[i]);
//            for(int j = 0; j<8; j++)
//                by[j+(8*i)] = temp[j];
//        }
//        return by;
//    }
    public byte[] sampleToBytes(double[] d){
        byte[] by = new byte[2*d.length];
        for(int i = 0; i < d.length; i++){
            int temp =  (int) ((d[i] * Math.pow(2,this.frameSize*8 ))/2);
//            if((byte) temp == 0)
//                by[2*i] = (byte) temp;
//            if((byte) temp > 0)
//                 by[2*i] = (byte) ((byte) temp+1);
//            if((byte) temp < 0)
//                by[2*i] = (byte) ((byte) temp+1);
            by[2*i + 0] = (byte) temp;
            by[2*i + 1] = (byte) (temp>>8);
        }
        return by;
    }
    
}
