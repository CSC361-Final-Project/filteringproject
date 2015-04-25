/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;


import static filtering.ComplexNumber.printComplexVectorCSV;
import static filtering.Convolution.convolveFIR;
import static filtering.FilterBuilder.bandstopFilter;
import static filtering.FilterBuilder.firHighPassFilter;
import static filtering.FilterBuilder.firLowPassFilter;
import static filtering.FilterTimer.dftTimer;
import static filtering.FilterTimer.fftTimer;
import static filtering.FilterTimer.freqDomainConvolveTimer;
import static filtering.FilterTimer.timeDomainConvolveTimer;
import static filtering.Fourier.fft;
import static filtering.Fourier.ifft;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kincbe10
 */
public class Filtering {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, FileNotFoundException, IOException, LineUnavailableException {
        // TODO code application logic here
        AudioSignal beethoven = new AudioSignal("Beethoven.wav");
//        byte[] pre = beethoven.getSampleBytes();
//        double[] d = beethoven.getSamples();
//        byte[] post = beethoven.sampleToBytes(d);
//        for(int i = 0; i < pre.length; i++){
//         //   if(pre[i] != post[i])
//            System.out.println("i: "+i+ " Pre: "+ pre[i] + " Post: " + post[i]  );
//        }
        double[] mask = bandstopFilter(beethoven, 30000, 28000, 33);
        
        double[] beethovenConv = convolveFIR(beethoven.getSamples(), mask);
        
        AudioSignal beethoven2 = new AudioSignal(beethoven, beethovenConv);
        
        WavFile test = new WavFile (beethoven2, "test.wav");
        test.save();
        //for(int i = 0; i < 100000; i++)        
        //    test.play();
        test.playLines();
        test.close();
        

    
    }

    private static double[] bandpassFilter(AudioSignal beethoven, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
