/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static filtering.ComplexNumber.printComplexVectorCSV;
import static filtering.Convolution.convolve;
import static filtering.Fourier.fft;
import static filtering.Fourier.ifft;

/**
 *
 * @author kincbe10
 */
public class Filtering {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N = 8;
        ComplexNumber[] x = new ComplexNumber[N];

        // original data
        System.out.println("Original");
        for (int i = 0; i < N; i++) {
            x[i] = new ComplexNumber(i, 0);
            x[i] = new ComplexNumber(-2*Math.random() + 1, 0);
            System.out.print(x[i]+", ");
        }
        System.out.println();
        

        // FFT of original data
        ComplexNumber[] y = fft(x);
        System.out.println("FFT");
        printComplexVectorCSV(y);
        System.out.println();

        // take inverse FFT
        System.out.println("IFFT");
        ComplexNumber[] z = ifft(y);
        printComplexVectorCSV(z);
        System.out.println();
    //    show(z, "z = ifft(y)");
        
        // circular convolution of x with itself
        System.out.println("X convolve with self");
        ComplexNumber[] c = convolve(x, x);
        printComplexVectorCSV(c);
        System.out.println();
        
        //
        System.out.println("IFFT CONVOLVE FFT");
        ComplexNumber[] id = convolve(y, z);
        printComplexVectorCSV(id);
        System.out.println();
        
    
    }
    
}
