/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static java.lang.System.arraycopy;
import java.util.Arrays;

/**
 *
 * @author kincbe10
 * Class of static methods to perform Fourier Transforms and 
 * Inverse Fourier Transforms on a polynomial/signal
 * 
 */
public class Fourier {
    
    /******************************************
     * 
     * Methods to build Fourier matrices and inverse Fourier matrices of size N 
     * for Fourier Transform by matrix vector multiplication
     * using methods from ComplexNumber class
     * 
     * @param N
     * @return 
     ******************************************/
    
    /*Generate Fourier matrix by calculating real and imaginary components
      for each coordinate ij
    */
    public static ComplexNumber[][] fourierMatrix(int N){
        ComplexNumber[][] F = new ComplexNumber[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                double r = Math.cos((-2*Math.PI*i*j)/N);
                double im = Math.sin((-2*Math.PI*i*j)/N);
                F[i][j] = new ComplexNumber(r,im);
            }
        }
        return F;
    }
    
    /*
      Generate inverse Fourier matrix by multiplying the conjugate of each
       position in the Fourier matrix by 1/N
    */
    public static ComplexNumber[][] inverseFourierMatrix(int N){
           ComplexNumber[][] F = fourierMatrix(N);
           ComplexNumber[][] G = new ComplexNumber[F.length][F.length];
           for(int i = 0; i < F.length; i++){
               for(int j = 0; j < F[0].length; j++){
                   G[i][j] = F[i][j].conjugate().multiply(1.0/N);
           }
           }
           return G;
           

    }
     /******************************************
     * 
     * Methods to compute Discrete Fourier Transform and Inverse Fourier Transform by
     * matrix vector multiplication of a signal/polynomial by Fourier Matrix
     * or Inverse Fourier Matrix.
     * 
     * Returns a vector that is the result of multiplying signal/coefficient
     * vector by Fourier or Inverse Fourier matrix
     * 
     * Support for both ComplexNumber and double vectors
     * 
     * Expected O(n^2) efficiency
     * 
     * @param S
     * @return 
     ******************************************/
    
    /*Calculate Discrete Fourier Transform by multiplying coefficient vector by Fourier
     *Matrix of the same length
     */
    
    //Matrix DFT
    
    //ComplexNumber param
    public static ComplexNumber[] matrixVectorDFT(ComplexNumber[] S){
        ComplexNumber[][] FM = fourierMatrix(S.length);
        ComplexNumber[] res = ComplexNumber.matrixVectorMultiply(FM, S);
        return res;
    
    }
    
    //Double param
    public static ComplexNumber[] matrixVectorDFT(double[] S){
        ComplexNumber[][] FM = fourierMatrix(S.length);
        ComplexNumber[] res = ComplexNumber.matrixVectorMultiply(FM, S);
        return res;
    
    }
        
    //Matrix IFT
        
    //ComplexNumber param
    public static ComplexNumber[] matrixVectorIFT(ComplexNumber[] S){
        ComplexNumber[][] FM = inverseFourierMatrix(S.length);
        ComplexNumber[] res = ComplexNumber.matrixVectorMultiply(FM, S);
        return res;
    
    }
    
    //Double param
    public static ComplexNumber[] matrixVectorIFT(double[] S){
        ComplexNumber[][] FM = inverseFourierMatrix(S.length);
        ComplexNumber[] res = ComplexNumber.matrixVectorMultiply(FM, S);
        return res;
    
    }
    
     /******************************************
     * 
     * Methods to compute Fast Fourier Transform and Inverse Fast Fourier Transform
     * 
     * Returns a vector that is the result of computing FFT or IFFT on input
     * polynomial/signal
     * 
     * Support for both ComplexNumber and double vectors
     * 
     * Expected O(nlogn) efficiency
     * 
     * @param S
     * @return 
     ******************************************/
    
    //FFT w/ ComplexNumber Param only works on matrices where length%2 = 0
    public static ComplexNumber[] fft(ComplexNumber[] S){
        //base case
        int N = S.length;
        if(N == 1){
            return new ComplexNumber[] {S[0]};
        }
        //error checking for vector length--> must be a power of 2
        if(N%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold even input coefficients
        ComplexNumber[] evens = new ComplexNumber[N/2];
        //take coefficients at 2*i to get even indexed values from input
        for(int i = 0; i < N/2; i++ ){
            evens[i] = S[i*2];
        }
        //recurse on evens
        ComplexNumber[] evenRec = fft(evens);
        
        //array to hold odd input coefficients
        ComplexNumber[] odds = new ComplexNumber[N/2];
        //take coefficients at 2*k+1 to get odd indexed values from input
        for(int j = 0; j < N/2; j++ ){
            odds[j] = S[(2*j)+1];
        }
        //recurse on odds
        ComplexNumber[] oddRec = fft(odds);
        
        //array to hold result
        ComplexNumber[] res = new ComplexNumber[N];
        //combine computing transform at each index
        for(int k = 0; k < N/2; k++){
            //calc Fourier Multiple for each index
            double mult = -2*k*Math.PI/N;
            ComplexNumber root = new ComplexNumber(Math.cos(mult), Math.sin(mult));
            //add sum of even and temp*odd to first half of output
            res[k] = evenRec[k].add(root.multiply(oddRec[k]));
            //add difference of even and temp*odd to second half of output
            res[k+ N/2] = evenRec[k].subtract(root.multiply(oddRec[k]));
        }
        
        return res;
    }
    
    //FFT w/ Double Param only works on matrices where length%2 = 0
    public static ComplexNumber[] fft(double[] S){
        ComplexNumber[] r = new ComplexNumber[S.length];
        for(int i = 0; i < S.length; i++)
            r[i] = new ComplexNumber(S[i], 0);
        
        return fft(r);
    }
    
    public static ComplexNumber[] ifft(ComplexNumber[] S){
        int N = S.length;
        ComplexNumber[] res = new ComplexNumber[N];
        for(int i = 0; i < N; i++){
            res[i] = S[i].conjugate();
        }
        
        res = fft(res);
        
        for(int j = 0; j < N; j++){
            res[j] = res[j].conjugate().multiply(1.0/N);
        }
        
        return res;
        
    }
    
    public static double[] ifftDouble(ComplexNumber[] S){
        int N = S.length;
        ComplexNumber[] res = new ComplexNumber[N];
        double[] resD = new double[N];
        for(int i = 0; i < N; i++){
            res[i] = S[i].conjugate();
        }
        
        res = fft(res);
        
        for(int j = 0; j < N; j++){
           resD[j] = res[j].conjugate().multiply(1.0/N).magnitudeDouble();
        }
        
        return resD;
        
    }

     public static double[] dft(double[] samp, int N){
         double[] F = new double[N/2];
         Arrays.fill(F, 0.0);
         double[] I = new double[N/2];
         Arrays.fill(I, 0.0);
         double[] R = new double[N/2];
         Arrays.fill(R, 0.0);
         for(int i = 0; i < N/2; i++){
             double cosTot = 0;
             double sinTot = 0;
             for(int j = 0; j < N; j++){
                 cosTot = cosTot+samp[j]*Math.cos((2*Math.PI*i*j)/N);
                 sinTot = sinTot+samp[j]*Math.sin((2*Math.PI*i*j)/N);
             }
             I[i] = (1.0/N)*cosTot;
             R[i] = (1.0/N)*sinTot;
             F[i] = Math.sqrt(Math.pow(I[i],2)+Math.pow(R[i],2));
         }
         
         return F;
         
     }
}

