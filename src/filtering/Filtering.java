/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;


import static filtering.ComplexNumber.printComplexVectorCSV;
import static filtering.ComplexNumber.vectorDotMultiply;
import static filtering.Convolution.convolveFIR;
import static filtering.FilterBuilder.bandpassFilter;
import static filtering.FilterBuilder.bandstopFilter;
import static filtering.FilterBuilder.cutSignal;
import static filtering.FilterBuilder.firHighPassFilter;
import static filtering.FilterBuilder.firLowPassFilter;
import static filtering.FilterBuilder.padFilter;
import static filtering.FilterBuilder.padSignal;
import static filtering.FilterTimer.dftTimer;
import static filtering.FilterTimer.fftTimer;
import static filtering.FilterTimer.freqDomainConvolveTimer;
import static filtering.FilterTimer.timeAndSaveFD;
import static filtering.FilterTimer.timeAndSaveTD;
import static filtering.FilterTimer.timeDomainConvolveTimer;
import static filtering.Fourier.fft;
//import static filtering.Fourier.ifft;
//import static filtering.Fourier.ifftDouble;
import static filtering.StdDraw.BLUE;
import static filtering.StdDraw.RED;
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
       // AudioSignal[] sigArr = new AudioSignal[4];
//        //sigArr[0] = new AudioSignal("beethoven16.wav");
//        sigArr[0] = new AudioSignal("beethoven256.wav");
//        sigArr[1] = new AudioSignal("beethoven4096.wav");
//        sigArr[2] = new AudioSignal("beethoven65536.wav");
//        sigArr[3] = new AudioSignal("beethoven131072.wav");//         StdDraw.setCanvasSize(1024,720);
////         StdDraw.setXscale(0,10);
////         StdDraw.setYscale(0, 10);
////         StdDraw.setPenRadius(0.02);
//        for(int j = 0; j < 4; j++){
//            int log2 = (int) ((int) Math.log(sigArr[j].getSamples().length)/Math.log(2));
//            for(int i = 0; i < log2; i++){    
//        
//            double[] mask = firLowPassFilter(sigArr[j], 10000, (int) (Math.pow(2, i)+1));
//        
//            double timeTD = timeAndSaveTD(sigArr[j], mask, i+"-"+j+"t.wav");
//           
//            double timeFD = timeAndSaveFD(sigArr[j], mask, i+"-"+j+"t.wav");
//            
//            System.out.println(sigArr[j].getSamples().length+" "+mask.length+" "+timeTD+" "+timeFD);
//        }
//    }
//    
//    }
    AudioSignal sig = new AudioSignal("beethoven131072.wav");
    
    double[] low = firLowPassFilter(sig, 1000, 33);
    double[] high = firHighPassFilter(sig, 40000, 33);
    double[] bandp = bandpassFilter(sig, 40000, 1000, 33);
    double[] bands = bandstopFilter(sig, 5000, 4000, 33);
    
    timeAndSaveTD(sig, low, "lowpass1000.wav");
    timeAndSaveTD(sig, high, "highpass40000.wav");
    timeAndSaveTD(sig, bandp, "bandpass1000_40000.wav");
    timeAndSaveTD(sig, bands, "bandstop4000_5000.wav");
    
    }
}
   
    
    