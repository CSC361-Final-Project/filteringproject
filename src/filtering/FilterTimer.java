/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static filtering.Convolution.convolveFIR;
import static filtering.Fourier.fft;
import static filtering.Fourier.ifftDouble;

/**
 *
 * @author kincbe10
 */
public class FilterTimer {
    
    public static long fftTimer(ComplexNumber[] S){
        long startTime = System.nanoTime();
        fft(S);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  
        return duration;
    }
    
    public static long timeDomainConvolveTimer(double[]S, double[]M){
        long startTime = System.nanoTime();
        convolveFIR(S, M);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;  
        return duration;
    }
    
    public static long freqDomainConvolveTimer(double[]S, double[]M){
        long startTime = System.nanoTime();
        ComplexNumber[] a = fft(S);
        ComplexNumber[] b = fft(M);
        ComplexNumber[] c = convolveFIR(a, b);
        double[] d = ifftDouble(c);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        return duration;
    }
}
