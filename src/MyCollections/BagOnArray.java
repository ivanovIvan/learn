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
 * @param <Item>
 */
public class BagOnArray<Item> implements Bag_API<Item>{
    private class MyIterator<Item extends Object> implements Iterator<Item>{
        Object[] myArray;
        int currElement;
        public MyIterator() {
            myArray = new Object[count];
            System.arraycopy(innerArray, 0,this.myArray , 0, count);
            currElement = -1;
        }
        
        @Override
        public boolean hasNext() {
            return currElement<myArray.length-1;
        }

        @Override
        public Item next() {
            if (hasNext()) return (Item)myArray[++currElement];
                    else {
                new NoSuchElementException("No element");
                return null;
            }
        }
    
    };

    Object[] innerArray;
    static final int INITIAL_SIZE = 100;
    int count;// count items
    public BagOnArray(int capacity){
        innerArray = new Object[capacity];
        count = -1;
    }
    
    public BagOnArray(){
        this(INITIAL_SIZE);
    }
    
    void Resize(int newCapacity){
        //if (newCapacity <innerArray.length) 
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(innerArray,0,newArray,0,Integer.min(newCapacity, innerArray.length));
        innerArray = newArray;
    }
    
    @Override
    public void add(Object item) {
        count++;
        if (count>=innerArray.length) {
            Resize(innerArray.length*2);
        }
        innerArray[count] = item;
    }

    @Override
    public boolean isEmpty() {
        return count ==0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
