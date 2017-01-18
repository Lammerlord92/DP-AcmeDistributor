package controllers.distributor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ProducerService;
import domain.Producer;

@Controller
@RequestMapping("/producer")
public class ProducerDistributorController {
	// ----------------------- Managed service -----------------------
	
		@Autowired
		private ProducerService producerService;
		
		// ------------------------- Constructor -------------------------
		
		// --------------------------- Listing ---------------------------
		
		@RequestMapping("/list")
		public ModelAndView listAll() {
			ModelAndView result;
			
			Collection<Producer> producers=producerService.findAll();
			
			String requestURI="producer/list.do";
			result = new ModelAndView("producer/list");
			result.addObject("producers",producers);
			result.addObject("requestURI", requestURI);
			return result;
		}
}
