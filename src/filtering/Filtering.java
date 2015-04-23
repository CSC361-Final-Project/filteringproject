/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtering;

import static filtering.ComplexNumber.printComplexVectorCSV;
import static filtering.Convolution.convolveFIR;
import static filtering.Fourier.fft;
import static filtering.Fourier.ifft;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author kincbe10
 */
public class Filtering {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedAudioFileException {
        // TODO code application logic here
//        
        AudioSignal test = new AudioSignal("Beethoven.wav");
//        int size = test.b.length;
//        for(int i = 0; i < size;  i++){
//            System.out.println("Byte "+test.b[i]);
//        }
        int dubsize = test.impulseCoeff.length;
        for(int j = 0; j < dubsize; j++ ){
            System.out.println("Double " + test.impulseCoeff[j]);
        }
    }
    
}
