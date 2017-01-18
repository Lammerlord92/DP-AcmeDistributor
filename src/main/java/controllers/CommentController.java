package controllers;
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
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import domain.Comment;
import domain.Item;
import services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private CommentService commentService;
	
	//Listing------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(@Valid Item item) {
			ModelAndView result;
		    Collection<Comment> comments=commentService.findByItemId(item.getId());
		    String requestURI="comment/list.do";
		    
		    result = new ModelAndView("comment/list");
		    result.addObject("comments",comments);
		    result.addObject("requestURI",requestURI);
			return result;
		}
}
