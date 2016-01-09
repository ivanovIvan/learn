/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author dav
 */
abstract class AbstractMyIterator<Item> implements Iterator<Item>{
    protected static final int INITIAL_COUNT=100;
    protected final Object[] myArray;
    protected int currElement;
    
    abstract void initialInnerArray();
            
    public AbstractMyIterator(int count) {
        myArray = new Object[count];
        //System.arraycopy(innerArray, 0,this.myArray , 0, count);
        currElement = 0;
        initialInnerArray();
    }
        
    @Override
    public boolean hasNext() {
        return currElement<myArray.length;
    }

    @Override
    public Item next() {
        if (hasNext()) return (Item)myArray[currElement++];
                else {
            new NoSuchElementException("No element");
            return null;
        }
    }
    
};    

