/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import domian.Product;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dav
 */
public interface ProductManager extends Serializable{
    public void increasePrice(int percentage);    
    public List<Product> getProducts();
}
