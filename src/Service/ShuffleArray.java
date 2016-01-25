/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Etalon.StdRandom;

/**
 *
 * @author dav
 */
public class ShuffleArray {
    public void suffle(Object[] a) {
        int randomNumber;
        Object temp;
        for (int i = 0; i < a.length; i++) {
            randomNumber = StdRandom.uniform(i+1);
            if (randomNumber != i) {
                temp = a[randomNumber];
                a[randomNumber] = a[i];
                a[i] = temp;
            }
        }
    }
}
