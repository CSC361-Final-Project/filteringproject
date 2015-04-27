/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static filtering.ComplexNumber.vectorDotMultiply;
import static filtering.Convolution.convolveFIR;
import static filtering.Convolution.fourierConvolveFIR;
import static filtering.FilterBuilder.cutSignal;
import static filtering.FilterBuilder.padFilter;
import static filtering.FilterBuilder.padSignal;
import static filtering.Fourier.fft;
//import static filtering.Fourier.ifftDouble;
import static filtering.Fourier.matrixVectorDFT;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author kincbe10
 */
public class FilterTimer {
    
    public static double fftTimer(double[] S){
        long startTime = System.nanoTime();
        fft(S);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;  
        return duration;
    }
    
    public static double dftTimer(double[] S){
        long startTime = System.nanoTime();
        matrixVectorDFT(S);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;  
        return duration;
    }
    
    
    public static double fftTimer(ComplexNumber[] S){
        long startTime = System.nanoTime();
        fft(S);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;  
        return duration;
    }
    
    public static double timeDomainConvolveTimer(double[]S, double[]M){
        long startTime = System.nanoTime();
        double[]d = convolveFIR(S, M);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;  
        return duration;
    }
    
    public static double freqDomainConvolveTimer(double[]S, double[]M){
        long startTime = System.nanoTime();
        double [] d = fourierConvolveFIR(S, M);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;
        return duration;
    }
    
    public static double timeAndSaveTD(AudioSignal S, double[]M, String filepath ) throws LineUnavailableException{
        
        long startTime = System.nanoTime();
        double[] d = convolveFIR(S.getSamples(), M);
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;
        AudioSignal post = new AudioSignal(S, d);
        WavFile postWav = new WavFile(post, filepath);
        postWav.save();
        postWav.playLines();
        postWav.close();
        return duration;
    }
    
    public static double timeAndSaveFD(AudioSignal S, double[]M, String filepath) throws LineUnavailableException{
        
  
      double[] M1 = padFilter(S.getSamples(), M);
      ComplexNumber[] u = new ComplexNumber[S.getSamples().length];
      ComplexNumber[] v = new ComplexNumber[S.getSamples().length];
        for(int i = 0; i < S.getSamples().length; i++){
            u[i] = new ComplexNumber(S.getSamples()[i], 0);
            v[i] = new ComplexNumber(M1[i], 0);
        }
      long startTime = System.nanoTime();
        
      double[] d = fourierConvolveFIR(S.getSamples(), M1);
//        for(int i = 0; i < d.length; i++){
//            System.out.println("S "+S.getSamples()[i]+ " m "+ M1[i]);
//           // System.out.println(d[i]);
//        }
        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime)/1000000;
        AudioSignal post = new AudioSignal(S, d);
        WavFile postWav = new WavFile(post, filepath);
        postWav.save();
        postWav.playLines();
        postWav.close();
        return duration;
    }

}
    