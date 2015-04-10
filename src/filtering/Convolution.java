/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import java.util.Arrays;

/**
 *
 * @author kincbe10
 * Class to perform 1d convolution on polynomials/signals for use with audio 
 * in the time space domain
 */
public class Convolution {
    //
    public static double[] convolve(double[] S, double[] M) {
        double[] result = new double[S.length];
        Arrays.fill(result, 0);
        for (int i=0; i<S.length; i++){
            for (int j=0; j<S.length; j++){
                if(i < j ){
                    result[i] = result[i] + S[j]*M[i-j+S.length];
                }
                else{
                    result[i] = result[i] + S[j]*M[i-j];
                    }
            }         
        }           
        return result;
    }
    public static ComplexNumber[] convolve(ComplexNumber[] S, ComplexNumber[] M) {
        ComplexNumber[] result = new ComplexNumber[S.length];
        Arrays.fill(result, new ComplexNumber(0, 0));
        for (int i=0; i<S.length; i++){
            for (int j=0; j<S.length; j++){
                if(i < j ){
                    result[i] = result[i].add(S[j].multiply(M[i-j+S.length]));
                }
                else{
                    result[i] = result[i].add(S[j].multiply(M[i-j]));
                    }
            }         
        }           
        return result;
    }


}
