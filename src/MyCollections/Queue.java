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
public class Queue<Item> implements Queue_API<Item> {
    protected Node root;
    protected Node end;
    protected int count; 
   
    protected class MyIterator extends AbstractMyIterator<Item>
    {

        public MyIterator(int count) {
            super(count);
        }
        
        @Override
        void initialInnerArray() {
            if (end != null) {
                int i =0;
                Node curr = end;
                while(curr!=null){
                    myArray[i++] = curr.getItem();
                    curr = curr.getPrev();
                }
        
            }
        }
    }
    
    private class Node{
        private Item item;
        private Node next;
        private Node prev;
        
        public Node(Item item) {
            this.item = item;
            next = null;
        }

        /**
         * @return the item
         */
        public Item getItem() {
            return item;
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
         * @return the prev
         */
        public Node getPrev() {
            return prev;
        }

        /**
         * @param prev the prev to set
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }
        
        
    }

    public Queue() {
        count = 0;
        root = null;
        end = null;
    }
    
    
    
    @Override
    public void enqueue(Item item) {
        Node newNode = new Node(item);
        if (root != null) root.setPrev(newNode);
        newNode.setNext(root);
        if (end == null) end = newNode;
        root = newNode;
        count++;
    }

    @Override
    public Item dequeue() {
        Item rez;
        if (end != null) {
            rez = end.getItem();
            if (end.getPrev() == null) root = null;
            end = end.getPrev();
            count--;
        } else throw new NoSuchElementException("No element");
        return rez;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        return root == null;
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
