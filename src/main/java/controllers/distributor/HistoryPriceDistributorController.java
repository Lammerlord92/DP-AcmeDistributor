package controllers.distributor;
import java.util.Collection;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.HistoryPriceService;
import controllers.AbstractController;
import domain.HistoryPrice;
import domain.Item;


@Controller
@RequestMapping("/history/distributor")
public class HistoryPriceDistributorController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private HistoryPriceService historyPriceService;
	
//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<HistoryPrice> history;
		history=historyPriceService.findAll();
		String requestURI="history/distributor/listAll.do";
			
		result = new ModelAndView("history/distributor/list");
		result.addObject("history",history);
		result.addObject("requestURI",requestURI);
		return result;
	}
	@RequestMapping("/listFromItem")
	public ModelAndView listFromItem(@Valid Item item) {
		ModelAndView result;
		
		Collection<HistoryPrice> history;
		history=historyPriceService.getHistoryPriceFromItemId(item.getId());
		String requestURI="history/distributor/listFromItem.do";
			
		result = new ModelAndView("history/distributor/list");
		result.addObject("history",history);
		result.addObject("requestURI",requestURI);
		return result;
	}
	@RequestMapping("/listFromItemDate")
	public ModelAndView listFromItemDate(@Valid Item item,@Valid Date date) {
		ModelAndView result;
		
		Collection<HistoryPrice> history;
		history=historyPriceService.getHistoryFromItemAndDate(item.getId(), date);
		String requestURI="history/distributor/listFromItemDate.do";
			
		result = new ModelAndView("history/distributor/list");
		result.addObject("history",history);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
//Deleting------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")		
	public ModelAndView delete(HistoryPrice history, BindingResult binding){
		ModelAndView result;
		try{
			historyPriceService.delete(history);
			result=new ModelAndView("redirect:list.do");
		} catch(Throwable oops){
			result=createEditModelAndView(history,"comment.commit.error");
		}
		return result;
	}
	
//Saving------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid HistoryPrice history, BindingResult binding){
		ModelAndView result;
		Assert.notNull(history);
		if(binding.hasErrors()){
			result=createEditModelAndView(history);
		} else{
			try{
				historyPriceService.save(history);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(history,"request.commit.error");
			}
		}
		return result;
	}
	
//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(@Valid Item item){
		ModelAndView result;
		HistoryPrice history;
		
		history = historyPriceService.create(item);
		Assert.notNull(history);
		result = createEditModelAndView(history);
		
		return result;
		
	}
	
//Edition------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int historyId){
		ModelAndView result;
		HistoryPrice history;
		
		history = historyPriceService.findOne(historyId);
		Assert.notNull(history);
		result = createEditModelAndView(history);
		
		return result;
	}
	
//Ancillary methods
	protected ModelAndView createEditModelAndView(HistoryPrice history){
		ModelAndView result;
		result=createEditModelAndView(history,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(HistoryPrice history, String message){
		ModelAndView result;
		
		result=new ModelAndView("history/distributor/edit");
		result.addObject("history", history);
		result.addObject("message", message);
		
		return result;
	}
}
