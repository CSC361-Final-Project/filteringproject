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
    public static double[] convolveFIR(double[] S, double[] M) {
        double[] res = new double[S.length];
        Arrays.fill(res, 0);
        for(int n = 0; n < S.length; n++){
            for (int k=0; k<M.length; k++){
                double mult = 0;
                if(S.length - k >= 0){
                   mult = M[k]*S[S.length-k];
                }
                res[n] = res[n]+mult;
            }
        }
        return res;
    }
    public static ComplexNumber[] convolveFIR(ComplexNumber[] S, ComplexNumber[] M) {
        ComplexNumber[] res = new ComplexNumber[S.length];
        Arrays.fill(res, new ComplexNumber(0, 0));
        for(int n = 0; n < S.length; n++){
            for (int k=0; k<M.length; k++){
                ComplexNumber mult = new ComplexNumber(0,0);
                if(S.length - k >= 0){
                   mult = M[k].multiply(S[S.length-k]);
                }
                res[n] = res[n].add(mult);
            }
        } 
        return res;
    }


}
