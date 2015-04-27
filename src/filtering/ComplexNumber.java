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
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kincbe10
 * Complex Number java class for use in Fourier and Convolution
 */
public class ComplexNumber {
    /******************************************
     * 
     * Private member attributes
     * 
     ******************************************/
    //member attributes for real and complex parts of #
    public final double real;
    public final double imag;
  //  private DecimalFormat df = new DecimalFormat("###.##");
    /******************************************
     * 
     * Constructors
     * 
     * @param r
     * @param i
     ******************************************/
    //basic constructor
    public ComplexNumber(double r, double i){
        this.real = /*Math.round(r*1000.0)/1000.0;*/ r;
        this.imag = /*Math.round(i*1000.0)/1000.0;*/ i;
    
    }
    /******************************************
     * 
     * Important manipulations
     * 
     * @return 
     ******************************************/
    //return absolute value of complex number---sqrt(r*r+i*i) as a double
    public double magnitudeDouble(){
        return Math.sqrt(this.real*this.real + this.imag*this.imag);
    }
    //return magnitude as a ComplexNumber
    public ComplexNumber magnitude(){
        return new ComplexNumber(this.magnitudeDouble(),0);
    }
    //uses Math.atan2 to return theta from polar coord/phase
    public double getTheta(){
        return Math.atan2(imag, real);
    }
    //return conjugate complex number of the form real, -imaginary
    public ComplexNumber conjugate(){
        return new ComplexNumber(this.real, -this.imag);
    }
    //returns reciprocal (real/scale, -imaginary/scale
    public ComplexNumber reciprocal(){
        return new ComplexNumber(this.real/(this.real*this.real+this.imag*this.imag), -this.imag/(this.real*this.real+this.imag*this.imag));
    }
    public String complexNumberToString(){
        if (this.imag == 0) return this.real + "";
        if (this.real == 0) return this.imag + "i";
        if (this.imag <  0) return this.real + " - " + (-this.imag) + "i";
        return this.real + " + " + this.imag + "i";
    }
    /******************************************
     * 
     * Arithmetic
     * 
     * @param t
     * @return 
     ******************************************/
    //addition of complex numbers
    public ComplexNumber add(ComplexNumber t){
        double r = this.real+t.real;
        double i = this.imag+t.imag;
        return new ComplexNumber(r, i);
    }
    //addition of a complex number and a double
    public ComplexNumber add(double d){
        return new ComplexNumber(d+this.real, this.imag);
    }
    //subtraction of complex numbers
    public ComplexNumber subtract(ComplexNumber t){
        double r = this.real-t.real;
        double i = this.imag-t.imag;
        return new ComplexNumber(r, i);
    }
    //subtraction of a complex number and a double
    public ComplexNumber subtract(double d){
        return new ComplexNumber(this.real-d, this.imag);
    }
    //multiplication of two complex numbers
    public ComplexNumber multiply(ComplexNumber t){
        double r = this.real*t.real - this.imag*t.imag;
        double i = this.real*t.imag + this.imag*t.imag;
        return new ComplexNumber(r,i);
    }
    //multiplication of a complex number by a double
     public ComplexNumber multiply(double d) {
        return new ComplexNumber(this.real*d, this.imag*d);
    }
    //division via multiplication by the reciprocal
    public ComplexNumber divide(ComplexNumber t){
        return this.multiply(t.reciprocal());
    }
    
    //division of a complex number by a double
    public ComplexNumber divide(double d){
        return new ComplexNumber(this.real/d, this.imag );
    }
    
    //exp function get complex number x^this
    public ComplexNumber exp(){
        return new ComplexNumber(Math.exp(this.real)*Math.cos(this.imag), Math.exp(this.real)*Math.sin(this.imag));
    }
        /******************************************
     * 
     * Trig
     * 
     ******************************************/
    
    // return sine of a complex number

    /**
     * Trig
     * @return
     */
        public ComplexNumber sin() {
        return new ComplexNumber(Math.sin(real) * Math.cosh(imag), Math.cos(real) * Math.sinh(imag));
    }

    // return cosine of a complex number
    public ComplexNumber cos() {
        return new ComplexNumber(Math.cos(real) * Math.cosh(imag), -Math.sin(real) * Math.sinh(imag));
    }

    // return tangent of a complex number
    public ComplexNumber tan() {
        return sin().divide(cos());
    }
    
    public static ComplexNumber[] vectorDotMultiply(ComplexNumber[] A, ComplexNumber[] B){
        int size;
        if(A.length > B.length){
            size = B.length;
        }
        else size = A.length;
        
        ComplexNumber[] C = new ComplexNumber[size];
        
        
        for(int i = 0; i < size; i++){
            C[i] = A[i].multiply(B[i]);
        }
        return C;
    }
    
     
    public static ComplexNumber[][] matrixMultiply(ComplexNumber[][] A, ComplexNumber[][] B){
        int mA = A.length;
        int nA = A[1].length;
        int mB = B.length;
        int nB = B[1].length;
        if(A[0].length != B[0].length){
            throw new RuntimeException("Illegal Matrices");
        }
        ComplexNumber[][] C = new ComplexNumber[mA][nB];
        for(int m = 0; m < mA; m++){
            for(int n = 0; n < nB; n++){
                C[m][n] = new ComplexNumber(0,0);
            }
        }
        for(int i = 0; i < mA; i++){
            for(int j = 0; j < nB; j++){
                for(int k = 0; k < nA; k++ ){
                    C[i][j] = C[i][j].add(A[i][k].multiply(B[k][j]));
                }
            }
        }
        return C;
    }
    public static ComplexNumber[] matrixVectorMultiply(ComplexNumber[][]A, double[] D ){
        if(D.length != A.length){
            throw new RuntimeException("Illegal matrices");
        }
        ComplexNumber[] B = new ComplexNumber[A.length];
        Arrays.fill(B, new ComplexNumber(0,0));

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                B[j] = B[j].add(A[i][j].multiply(D[i]));
            }
        }
        return B;
    }
        public static ComplexNumber[] matrixVectorMultiply(ComplexNumber[][]A, ComplexNumber[] D ){
        if(D.length != A.length){
            throw new RuntimeException("Illegal matrices");
        }
        ComplexNumber[] B = new ComplexNumber[A.length];
        Arrays.fill(B, new ComplexNumber(0,0));
//        for (int w = 0; w < B.length; w++){
//            B[w] = new ComplexNumber(0,0);
//        }
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                B[j] = B[j].add(A[i][j].multiply(D[i]));
            }
        }
        return B;
    }

    public static void printComplexMatrix(ComplexNumber[][] F){
        for (ComplexNumber[] F1 : F) {
            System.out.print("||");
            for (ComplexNumber F11 : F1) {
                String ij = F11.complexNumberToString();
                int whiteSpace = 17-ij.length();
                System.out.print(" "+ij);
                for(int w = 0; w < whiteSpace; w++){
                    System.out.print(" ");
                }
                System.out.print(" |");
            }
            System.out.print("|");
            System.out.println();
        }
    }
    public static void printComplexMatrixCSV(ComplexNumber[][] F){
        for (ComplexNumber[] F1 : F) {
            for (int j = 0; j < F1.length; j++) {
                String ij = F1[j].complexNumberToString();
                if (j < F1.length - 1) {
                    System.out.print(ij+", ");
                } else {
                    System.out.print(ij);
                }                    
            }
            System.out.println();
        }
    }
    public static void printComplexVectorCSV(ComplexNumber[]F){
        for(int i = 0; i < F.length; i++){
               // double temp = F[i].magnitudeDouble();
               String ij = F[i].complexNumberToString();
                System.out.println(ij);
                   // System.out.println(temp);
                    
            
            System.out.println();
        }
    }
        

//    public String complexNumberToString() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
