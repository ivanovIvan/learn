/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.stereotype.Controller; 
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    @Qualifier("priceValidator")
    private PriceIncreaseValidator priceValidator;
    
    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.setValidator(priceValidator);
    }
            
    //@RequestMapping(method={RequestMethod.GET})
    @RequestMapping(value = "/priceIncrease.Accept", method={RequestMethod.POST})
    public String post(@Valid @ModelAttribute("priceIncrease") PriceIncrease priceIncrease,BindingResult result,
      HttpServletRequest request, HttpServletResponse response,final RedirectAttributes redirectAttributes) {
        int increase = priceIncrease.getPercentage();
        //ModelAndView mModel = new ModelAndView();
        if (result.hasErrors()) return "priceincrease";//redirectAttributes.addFlashAttribute("Error", "Ошибка проверки");
        
            //return new ModelAndView("priceincrease");
        logger.info("Increasing prices by " + increase + "%."); 
        productManager.increasePrice(increase); 
        logger.info("returning from PriceIncreaseForm view to " + getSuccessView()); 
        
        //mModel.setView(new RedirectView(getSuccessView()));
        return "redirect:"+getSuccessView();
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