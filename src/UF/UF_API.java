/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UF;

/**
 *
 * @author dav
 */
public interface UF_API {
    void union (int р, int q)   ;
    int find(int p)             ; //идентификатор компонента для р (от 0 до  N-1)
    boolean connected(int p, int q); //возвращает  true, если р и q принадлежат одному компоненту
    int count()                 ; //количество компонентов
}
