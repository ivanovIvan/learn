/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyCollections;

/**
 *
 * @author dav
 */
public interface Queue_API<Item> extends Iterable<Item>{
    void enqueue (Item item);    //  добавление элемента
    Item dequeue ();             // удаление самого “старого ”  элемента
    boolean isEmpty ();          // пуста ли очередь?
    int size();                  // количество элементов в очереди    
}
