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
public class Stack<Item> implements Stack_API<Item>{
    
    private class Node{
        private Item item;
        private Node next;
        Node(Item item){
            this.item = item;
            next = null;
        }

        /**
         * @return the next
         */
        public Node getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * @return the item
         */
        public Item getItem() {
            return item;
        }
        
    }
    
    protected Node root;
    protected int count;

    public Stack() {
        count = 0;
        root  = null;
    }

    protected class MyIterator extends AbstractMyIterator<Item>
    {

        public MyIterator(int count) {
            super(count);
        }
        
        @Override
        void initialInnerArray() {
            if (root != null) {
                int i =0;
                Node curr = root;
                while(curr!=null){
                    myArray[i++] = curr.getItem();
                    curr = curr.getNext();
                }
        
            }
        }
    }
        
        
    @Override
    public void push(Item item) {
        Node newNode = new Node(item);
        if (root != null)  newNode.setNext(root);
        root = newNode;
        count++;
    }

    @Override
    public Item pop() {
        Item rez = null;
        if (root == null) throw new NoSuchElementException();
        else {
            rez = root.getItem();
            root = root.getNext();
            count--;
        }
        return rez;
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
    public Iterator<Item> iterator() {
        return new MyIterator(count);
    }
    
}
