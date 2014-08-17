/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domian;
import java.io.Serializable; 
public class Product implements Serializable { 
    private String description;
    private Double price;    
    public String getDescription() {
        return description;
    }    
    public void setDescription(String description) {
        this.description = description;
    }    
    public Double getPrice() {
        return price;
    }    
    public void setPrice(Double price) {
        this.price = price;
    }    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Description: " + description + ";");
        buffer.append("Price: " + price);
        return buffer.toString();
    }
}