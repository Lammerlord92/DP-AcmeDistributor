package controllers.distributor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.DistributorService;
import services.FolderService;
import domain.Distributor;
import domain.Producer;
import forms.DistributorForm;

@Controller
@RequestMapping("/distributor")
public class DistributorRegisterController extends AbstractController {
	public DistributorRegisterController() {
		super();
	}
	//	Services --------------------------------------------------------------
	@Autowired 
	private DistributorService distributorService;
	@Autowired
	private FolderService folderService;

	//	Create ----------------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		DistributorForm distributorForm;
		distributorForm=new DistributorForm();
		
		result= createEditModelAndView(distributorForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid DistributorForm distributorForm, BindingResult binding) {
		ModelAndView result;
		Distributor distributor;
		if(binding.hasErrors()){
			result=createEditModelAndView(distributorForm);
		}else{
			try{
				distributor=distributorService.reconstruct(distributorForm);
				distributorService.save(distributor);
				List<Distributor> c=(List<Distributor>) distributorService.findAll();
				folderService.saveFolders(c.get(c.size()-1));
				result=new ModelAndView("redirect:../security/login.do");
			}catch (Throwable oops) {
				result= createEditModelAndView(distributorForm,"distributor.commit.error");
			}
		}
		
		return result;
		
	}
	
	// Ancillary methods
	protected ModelAndView createEditModelAndView(DistributorForm distributorForm){
		ModelAndView result;
		result=createEditModelAndView(distributorForm,null);
		return result;
	}
	protected ModelAndView createEditModelAndView(DistributorForm distributorForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("distributor/register");
		result.addObject("distributorForm",distributorForm);
		result.addObject("message",message);
		return result;
	}
}
