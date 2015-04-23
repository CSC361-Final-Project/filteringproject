/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static java.lang.System.arraycopy;

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
        if(S.length == 1){
            ComplexNumber[] bc = new ComplexNumber[1];
            arraycopy(S, 0, bc, 0, 1);
            return bc;
        }
        //error checking for vector length--> must be a power of 2
        if(S.length%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold even input coefficients
        ComplexNumber[] E = new ComplexNumber[S.length/2];
        //take coefficients at 2*i to get even indexed values from input
        for(int i = 0; i < E.length; i++ ){
            E[i] = S[i*2];
        }
        //recurse on evens
        ComplexNumber[] ER = fft(E);
        
        //array to hold odd input coefficients
        ComplexNumber[] O = new ComplexNumber[S.length/2];
        //take coefficients at 2*k+1 to get odd indexed values from input
        for(int k = 0; k < E.length; k++ ){
            O[k] = S[k*2+1];
        }
        //recurse on odds
        ComplexNumber[] OR = fft(O);
        
        //array to hold result
        ComplexNumber[] res = new ComplexNumber[S.length];
        //combine computing transform at each index
        for(int j = 0; j < S.length/2; j++){
            //calc Fourier Multiple for each index
            ComplexNumber temp = new ComplexNumber(Math.cos(-2*j*Math.PI/S.length), Math.sin(-2*j*Math.PI/S.length));
            //add sum of even and temp*odd to first half of output
            res[j] = ER[j].add(temp.multiply(OR[j]));
            //add difference of even and temp*odd to second half of output
            res[j + S.length/2] = ER[j].subtract(temp.multiply(OR[j]));
        }
        
        return res;
    }
    
    //FFT w/ Double Param only works on matrices where length%2 = 0
    public static ComplexNumber[] fft(double[] S){
        //base case
        if(S.length == 1){
            ComplexNumber[] bc = new ComplexNumber[1];
            arraycopy(S, 0, bc, 0, 1);
        }
        //error checking for vector length--> must be a power of 2
        if(S.length%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold even input coefficients
        ComplexNumber[] E = new ComplexNumber[S.length/2];
        //take coefficients at 2*i to get even indexed values from input
        for(int i = 0; i < E.length; i++ ){
            E[i] = new ComplexNumber(S[i*2], 0);
        }
        //recurse on evens
        ComplexNumber[] ER = fft(E);
        
        //array to hold odd input coefficients
        ComplexNumber[] O = new ComplexNumber[S.length/2];
        //take coefficients at 2*k+1 to get odd indexed values from input
        for(int k = 0; k < E.length; k++ ){
            O[k] = new ComplexNumber(S[k*2+1], 0);
        }
        //recurse on odds
        ComplexNumber[] OR = fft(O);
        
        //array to hold result
        ComplexNumber[] res = new ComplexNumber[S.length];
        //combine computing transform at each index
        for(int j = 0; j < S.length/2; j++){
            //calc Fourier Multiple for each index
            ComplexNumber temp = new ComplexNumber(Math.cos(-2*j*Math.PI/S.length), Math.sin(-2*j*Math.PI/S.length));
            //add sum of even and temp*odd to first half of output
            res[j] = ER[j].add(temp.multiply(OR[j]));
            //add difference of even and temp*odd to second half of output
            res[j + S.length/2] = ER[j].subtract(temp.multiply(OR[j]));
        }
        
        return res;
    }
    
    /*Compute IFFT by taking conjugate of input computing fft, 
     *then the conjugate of that result and multiplying each 
     *entry by 1/N
    */
    //IFFT w/ ComplexNumber Param only works on matrices where length%2 = 0
    public static ComplexNumber[] ifft(ComplexNumber[] S){
        //error checking for vector length--> must be a power of 2
        if(S.length%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold output
        ComplexNumber[] res = new ComplexNumber[S.length];
        for(int i = 0; i < S.length; i++ ){
            res[i] = S[i].conjugate();
        }
        
        //call fft
        res = fft(res);
        
        //take conjugate and divide by n
        for(int j = 0; j < S.length; j++ ){
            ComplexNumber temp = res[j].conjugate();
            res[j] = res[j].divide((double)S.length);
        }
        
        return res;
    }
    
    //IFFT with double param only works on matrices where length%2 = 0
    public static ComplexNumber[] ifft(double[] S){
        //error checking for vector length--> must be a power of 2
        if(S.length%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold output
        ComplexNumber[] res = new ComplexNumber[S.length];
        for(int i = 0; i < S.length; i++ ){
            res[i] = new ComplexNumber(1/S[i], 0);
        }
        
        //call fft
        res = fft(res);
        
        //take conjugate and divide by n
        for(int j = 0; j < S.length; j++ ){
            ComplexNumber temp = res[j].conjugate();
            res[j] = res[j].divide((double)S.length);
        }
        
        return res;
    }
    
     public static double[] ifftDouble(ComplexNumber[] S){
        //error checking for vector length--> must be a power of 2
        if(S.length%2 != 0){
            throw new RuntimeException("Coefficient vector length must be a power of 2 to use FFT");
        }
        //array to hold output
        ComplexNumber[] resC = new ComplexNumber[S.length];
        double[] res = new double[S.length];
        for(int i = 0; i < S.length; i++ ){
            resC[i] = S[i].conjugate();
        }
        
        //call fft
        resC = fft(resC);
        
        //take conjugate and divide by n
        for(int j = 0; j < S.length; j++ ){
            ComplexNumber temp = resC[j].conjugate();
            resC[j] = resC[j].divide((double)S.length);
            res[j] = resC[j].magnitudeDouble();
            
        }
        
        return res;
    }
}
