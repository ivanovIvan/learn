/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.io.IOException;
import java.util.HashMap; 
import java.util.Map;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ProductManager;

@org.springframework.stereotype.Controller
public class InventoryController  { 
    protected final Log logger = LogFactory.getLog(getClass()); 
    @Autowired
    @Qualifier("productManager")
    private ProductManager productManager; 
    
    @RequestMapping("/hello")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        //logger.debug("Returning hello view"); 
        String now = (new java.util.Date()).toString();
        logger.info("returning hello view with " + now); 
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("now", now);
        myModel.put("products", this.productManager.getProducts()); 
        return new ModelAndView("hello", "model", myModel);
    }
    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }   
}