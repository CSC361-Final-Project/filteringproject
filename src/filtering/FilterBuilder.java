/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

/**
 *
 * @author kincbe10
 * abstract class with windowing functions and functions to generate filters for use in convolution
 * each function has 2 implementations one with params for AudioSignal, cutoff frequency, order which uses Hanning Windowing Function by default
 * another with AudioSignal, cutoff frequency, order and an int 0-3 to dictate windowing function
 * Value key for windowing param {0 = rectangular, 1 = Hanning, 2 = Hamming, 3 = Blackman}
 */
public abstract class FilterBuilder {
 
    //Gen lowpass filter params = AudioSignal, cutoff frequency, filter order use Hanning Windowing Function by default
    public static double[] firLowPassFilter(AudioSignal A, double cutoff, int order){
        cutoff = cutoff/A.getSampleRate();
        double[] filter = new double[order];
        double ang = 2*Math.PI*cutoff;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 2*cutoff;
            else
                filter[i] = Math.sin(ang*n)/(Math.PI*n);
        }
        return hanningWindow(filter);
    }
    
    public static double[] firLowPassFilter(AudioSignal A, double cutoff, int order, int window){
        cutoff = cutoff/A.getSampleRate();
        double[] filter = new double[order];
        double ang = 2*Math.PI*cutoff;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 2*cutoff;
            else
                filter[i] = Math.sin(ang*n)/(Math.PI*n);
        }
        if(window == 0) return filter;
        else if(window == 1) return hanningWindow(filter);
        else if(window == 2) return hammingWindow(filter);
        else if(window == 3) return blackmanWindow(filter);
        else{
            System.out.println("Error, illegal window parameter, using hanning method by default");
            return hanningWindow(filter);
        }
           
    }
    
    public static double[] firHighPassFilter(AudioSignal A, double cutoff, int order){
        cutoff = cutoff/A.getSampleRate();
        double[] filter = new double[order];
        double ang = 2*Math.PI*cutoff;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 1-(2*cutoff);
            else
                filter[i] = -1*(Math.sin(ang*n)/(Math.PI*n));
        }
        return hanningWindow(filter);
    }
    
        public static double[] firHighPassFilter(AudioSignal A, double cutoff, int order, int window){
        cutoff = cutoff/A.getSampleRate();
        double[] filter = new double[order];
        double ang = 2*Math.PI*cutoff;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 1-(2*cutoff);
            else
                filter[i] = -1*(Math.sin(ang*n)/(Math.PI*n));
        }
        if(window == 0) return filter;
        else if(window == 1) return hanningWindow(filter);
        else if(window == 2) return hammingWindow(filter);
        else if(window == 3) return blackmanWindow(filter);
        else{
            System.out.println("Error, illegal window parameter, using hanning method by default");
            return hanningWindow(filter);
        }
           
    }
        
    public static double[] bandpassFilter(AudioSignal A, double cutoffHigh, double cutoffLow, int order){
        cutoffHigh = cutoffHigh/A.getSampleRate();
        cutoffLow = cutoffLow/A.getSampleRate();
        double[] filter = new double[order];
        double angHigh = 2*Math.PI*cutoffHigh;
        double angLow = 2*Math.PI*cutoffLow;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 2*(cutoffHigh-cutoffLow);
            else
                filter[i] = cutoffHigh*(Math.sin(angHigh*n)/(Math.PI*n)) - cutoffLow*(Math.sin(angLow*n)/(Math.PI*n));
        }
        return hanningWindow(filter);
    }
    
    public static double[] bandpassFilter(AudioSignal A, double cutoffHigh, double cutoffLow, int order, int window){
        cutoffHigh = cutoffHigh/A.getSampleRate();
        cutoffLow = cutoffLow/A.getSampleRate();
        double[] filter = new double[order];
        double angHigh = 2*Math.PI*cutoffHigh;
        double angLow = 2*Math.PI*cutoffLow;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 2*(cutoffHigh-cutoffLow);
            else
                filter[i] = cutoffHigh*(Math.sin(angHigh*n)/(Math.PI*n)) - cutoffLow*(Math.sin(angLow*n)/(Math.PI*n));
        }
        if(window == 0) return filter;
        else if(window == 1) return hanningWindow(filter);
        else if(window == 2) return hammingWindow(filter);
        else if(window == 3) return blackmanWindow(filter);
        else{
            System.out.println("Error, illegal window parameter, using hanning method by default");
            return hanningWindow(filter);
        }
    }
    
    public static double[] bandstopFilter(AudioSignal A, double cutoffHigh, double cutoffLow, int order){
        cutoffHigh = cutoffHigh/A.getSampleRate();
        cutoffLow = cutoffLow/A.getSampleRate();
        double[] filter = new double[order];
        double angHigh = 2*Math.PI*cutoffHigh;
        double angLow = 2*Math.PI*cutoffLow;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 1-(2*(cutoffHigh-cutoffLow));
            else
                filter[i] = cutoffLow*(Math.sin(angLow*n)/(Math.PI*n)) - cutoffHigh*(Math.sin(angHigh*n)/(Math.PI*n));
        }
        return hanningWindow(filter);
    }
    
    public static double[] bandstopFilter(AudioSignal A, double cutoffHigh, double cutoffLow, int order, int window){
        cutoffHigh = cutoffHigh/A.getSampleRate();
        cutoffLow = cutoffLow/A.getSampleRate();
        double[] filter = new double[order];
        double angHigh = 2*Math.PI*cutoffHigh;
        double angLow = 2*Math.PI*cutoffLow;
        int mid = order/2;
        for(int i = 0; i < order; i++){
            int n = i-mid;
            if(n == 0)
                filter[i] = 1-(2*(cutoffHigh-cutoffLow));
            else
                filter[i] = cutoffLow*(Math.sin(angLow*n)/(Math.PI*n)) - cutoffHigh*(Math.sin(angHigh*n)/(Math.PI*n));
        }
        if(window == 0) return filter;
        else if(window == 1) return hanningWindow(filter);
        else if(window == 2) return hammingWindow(filter);
        else if(window == 3) return blackmanWindow(filter);
        else{
            System.out.println("Error, illegal window parameter, using hanning method by default");
            return hanningWindow(filter);
        }
    }
    
    private static double[] hanningWindow(double[] f){
        for(int i = 0; i < f.length; i++){
            f[i] = f[i]*(0.5 + 0.5*Math.cos((2*Math.PI*i)/f.length));
        }
        return f;
    }
    
    private static double[] hammingWindow(double[] f){
        for(int i = 0; i < f.length; i++){
            f[i] = f[i]*(0.54 + 0.46*Math.cos((2*Math.PI*i)/f.length));
        }
        return f;
    }
    
     private static double[] blackmanWindow(double[] f){
        for(int i = 0; i < f.length; i++){
            f[i] = f[i]*(0.42 + 0.5*Math.cos((2*Math.PI*i)/f.length-1) + 0.08*Math.cos((4*Math.PI*i)/f.length-1));
        }
        return f;
    }
     
    public static double[] padFilter(double[]s, double[]m){
        double[] p = new double [s.length];
        for(int i = 0; i < m.length; i++){
            p[i] = m[i];
        }
        for(int j = m.length; j < s.length; j++){
            p[j] = 0; 
        }
        return p;
    }
    
    public static AudioSignal padSignal(AudioSignal s){
        int len = s.getSamples().length;
        int two = 0;
        int i = 0; 
        while(two < len){
            two = (int) Math.pow(2, i);
            i++;
           // System.out.println(two);
        
        }
        double [] padded = new double[two];
        
        
        for(int j = 0; j < len; j++){
            padded[j] = s.getSamples()[j];
        }
        for(int k = len; k < two; k++){
            padded[k] = 0;
        }
        
        
        System.out.println(padded.length);
        
        return new AudioSignal(s, padded);
    }
    
    public static AudioSignal cutSignal(AudioSignal s){
        int len = s.getSamples().length;
        int two = 0;
        int i = 0; 
        while(two < len){
            two = (int) Math.pow(2, i);
            i++;
           // System.out.println(two);
        
        }
        int prevTwo = (int) Math.pow(2, i-2);
        
        double [] cut = new double[prevTwo];
        
        
        for(int j = 0; j < prevTwo; j++){
            cut[j] = s.getSamples()[j];
        }
      
        
        System.out.println(cut.length);
        
        return new AudioSignal(s, cut);
    }
    
    
}
