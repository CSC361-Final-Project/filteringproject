/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static filtering.ComplexNumber.vectorDotMultiply;
import static filtering.Convolution.convolveFIR;
import static filtering.Convolution.fourierConvolveFIR;
import static filtering.Fourier.fft;
import static filtering.Fourier.ifftDouble;
import static filtering.Fourier.matrixVectorDFT;

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
}
