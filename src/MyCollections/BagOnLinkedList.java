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
public class BagOnLinkedList<Item> implements Bag_API<Item> {
    private class MyIterator<Item> implements Iterator<Item>{
        Object[] myArray;
        int currElement;
        public MyIterator() {
            myArray = new Object[count];
            currElement = -1;
            Node currNode = root;
            for (int i=0;i<count;i++){
                myArray[i] = currNode.getItem();
                currNode = currNode.getNext();
            }
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
    Node root;
    Node end;
    int count;

    public BagOnLinkedList() {
        root = null;
        count = 0;
    }
    
    
    @Override
    public void add(Item item) {
        Node newNode = new Node(item);
        if (end!=null) end.setNext(newNode);
        end = newNode;
        if (root == null) root = end;
        count++;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new MyIterator<>();
    }
    
}
