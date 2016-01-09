/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCollections;

/**
 *
 * @author dav
 * @param <Item>
 */
public interface Bag_API<Item>  extends Iterable<Item> {
   void add (Item item);
   boolean isEmpty ();
   int size();
}
