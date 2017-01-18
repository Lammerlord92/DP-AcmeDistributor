package controllers.customer;
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

import security.LoginService;
import services.CommentService;
import services.CustomerService;
import services.ItemService;
import controllers.AbstractController;
import domain.Comment;
import domain.Customer;
import domain.Item;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private CommentService commentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ItemService itemService;
	
	//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Customer c=customerService.findByUserAccount(LoginService.getPrincipal());
		
		Collection<Comment> comments;
		comments=commentService.findByCustomerId(c.getId());
		String requestURI="comment/customer/list.do";
			
		result = new ModelAndView("comment/customer/list");
		result.addObject("comments",comments);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
	//Deleting------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")		
	public ModelAndView delete(Comment comment, BindingResult binding){
		ModelAndView result;
		try{
			commentService.delete(comment);
			result=new ModelAndView("redirect:list.do");
		} catch(Throwable oops){
			result=createEditModelAndView(comment,"comment.commit.error");
		}
		return result;
	}
	
	//Saving------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid Comment comment, BindingResult binding){
		ModelAndView result;
		Assert.notNull(comment);
		if(binding.hasErrors()){
			result=createEditModelAndView(comment);
		} else{
			try{
				commentService.save(comment);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(comment,"request.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST,params="save")		
	public ModelAndView saveCreate(@Valid Comment comment, BindingResult binding){
		ModelAndView result;
		Assert.notNull(comment);
		if(binding.hasErrors()){
			result=createEditModelAndView(comment);
		} else{
			try{
				commentService.save(comment);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(comment,"request.commit.error");
			}
		}
		return result;
	}
	//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int itemId){
		ModelAndView result;
		Comment comment;
		
		Item item=itemService.findOne(itemId);
		comment = commentService.create(item);
		Assert.notNull(comment);
		result = createEditModelAndView(comment);
		
		return result;
		
	}
	
	//Edition------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int commentId){
		ModelAndView result;
		Comment comment;
		
		comment = commentService.findOne(commentId);
		Assert.notNull(comment);
		result = createEditModelAndView(comment);
		
		return result;
	}


//Ancillary methods
	protected ModelAndView createEditModelAndView(Comment comment){
		ModelAndView result;
		result=createEditModelAndView(comment,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Comment comment, String message){
		ModelAndView result;
		
		result=new ModelAndView("comment/customer/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;
	}
	
}