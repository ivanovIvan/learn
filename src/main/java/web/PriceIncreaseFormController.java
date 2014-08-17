/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import service.PriceIncrease;
import service.PriceIncreaseValidator;
import service.ProductManager;

@Controller
@SessionAttributes({"priceIncrease"})
public class PriceIncreaseFormController  { 
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass()); 
    @Autowired
    @Qualifier("productManager")
    private ProductManager productManager; 
    
    @Autowired
    private PriceIncreaseValidator priceValidator;
    
    @InitBinder
    @Qualifier("priceValidator")
    private void initBinder(WebDataBinder binder){
        binder.setValidator(priceValidator);
    }
            
    //@RequestMapping(method={RequestMethod.GET})
    @RequestMapping(value = "/priceincrease", method={RequestMethod.POST})
    public ModelAndView post(@ModelAttribute("priceIncrease") PriceIncrease priceIncrease,
      HttpServletRequest request, HttpServletResponse response) {
        int increase = priceIncrease.getPercentage();
        logger.info("Increasing prices by " + increase + "%."); 
        productManager.increasePrice(increase); 
        logger.info("returning from PriceIncreaseForm view to " + getSuccessView()); 
        return new ModelAndView(new RedirectView(getSuccessView()));
  }
    @RequestMapping(value = "/priceincrease", method={RequestMethod.GET})
    public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("priceincrease");
  }
          
/*    public ModelAndView onSubmit(Object command)
            throws ServletException { 
        int increase = ((PriceIncrease) command).getPercentage();
        logger.info("Increasing prices by " + increase + "%."); 
        productManager.increasePrice(increase); 
        logger.info("returning from PriceIncreaseForm view to " + getSuccessView()); 
        return new ModelAndView(new RedirectView(getSuccessView()));
    } */
    @ModelAttribute("priceIncrease")
    public PriceIncrease createPriceIncrease() {
      PriceIncrease priceIncrease = new PriceIncrease();
      priceIncrease.setPercentage(20);
      return priceIncrease;
    }
    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    } 
    public ProductManager getProductManager() {
        return productManager;
    } 

    private String getSuccessView() {
        return "hello.htm";
    }

    /**
     * @param priceValidator the priceValidator to set
     */
    public void setPriceValidator(PriceIncreaseValidator priceValidator) {
        this.priceValidator = priceValidator;
    }
}