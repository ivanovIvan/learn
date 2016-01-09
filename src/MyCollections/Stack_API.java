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
public interface Stack_API<Item> extends Iterable<Item>{
    void push (Item item);  // добавление элемента
    Item pop();             // удаление самого “свежего” элемента
    boolean isEmpty ();     // пуст ли стек?
    int size();             // количество элементов в стеке    
}
