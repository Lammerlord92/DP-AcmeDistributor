package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import services.ProducerService;
import domain.Customer;
import domain.Producer;
import forms.ProducerForm;

@Controller
@RequestMapping("/producer")
public class ProducerController extends AbstractController {

	public ProducerController() {
		super();
	}
	//	Services --------------------------------------------------------------
	@Autowired 
	private ProducerService producerService;
	@Autowired
	private FolderService folderService;
	//	Create ----------------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProducerForm producerForm;
		producerForm=new ProducerForm();
		
		result= createEditModelAndView(producerForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid ProducerForm producerForm, BindingResult binding) {
		ModelAndView result;
		Producer producer;
		if(binding.hasErrors()){
			result=createEditModelAndView(producerForm);
		}else{
			try{
				producer=producerService.reconstruct(producerForm);
				producerService.save(producer);
				List<Producer> c=(List<Producer>) producerService.findAll();
				folderService.saveFolders(c.get(c.size()-1));
				result=new ModelAndView("redirect:../security/login.do");
			}catch (Throwable oops) {
				result= createEditModelAndView(producerForm,"producer.commit.error");
			}
		}
		
		return result;
		
	}
	
	// Ancillary methods
	protected ModelAndView createEditModelAndView(ProducerForm producerForm){
		ModelAndView result;
		result=createEditModelAndView(producerForm,null);
		return result;
	}
	protected ModelAndView createEditModelAndView(ProducerForm producerForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("producer/register");
		result.addObject("producerForm",producerForm);
		result.addObject("message",message);
		return result;
	}
}
