/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author jeremy calderon yataco FB: Jeremi kun mendoza  cell 968 07 88 03 jeremykun85@gmail.com
 */
public class Existencia {
    
    /*CREAREMOS UN METODO QUE SE PONDRA A 
    BUSCAR  SI EXISTE LA PALABRA EN UNA FRASE*/
    
    public void esta(String palabra, String frase){
        
        for (int i = 0; i < palabra.length(); i++) {
            char p = palabra.charAt(i);
            
            for (int j = 0; j < frase.length(); j++) {
                char f = frase.charAt(j);
                if(p == f ){
                    for (int k = 0; k < palabra.length(); k++) {
                        //if(palabra.charAt(k) == )
                    }
                }
                
            }
        }
        
        
    }
    
    
    
    
}
