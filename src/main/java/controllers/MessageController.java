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

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.DistributorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Distributor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {
//	Services --------------------------------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private DistributorService distributorService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Message> messagesInInbox;
		messagesInInbox=messageService.getFromInbox();
		Collection<Message> messagesInOutbox;
		messagesInOutbox=messageService.getFromOutbox();
		
		result = new ModelAndView("message/list");
		result.addObject("messagesInInbox",messagesInInbox);
		result.addObject("messagesInOutbox",messagesInOutbox);
		
		String requestURI="message/list.do";
		result.addObject("requestURI", requestURI);
		
		return result;
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageId){
		ModelAndView result=null;
		
		Message message = messageService.findOne(messageId);

		try{
			messageService.delete(message);
			result= new ModelAndView("redirect:list.do");
		}catch(Throwable oops){
			result= new ModelAndView("redirect:list.do");
		}
		
		return result;
	}
	
	//Edit ----------------------------------------
	
	@RequestMapping(value="/reply",method=RequestMethod.GET)
	public ModelAndView replyMessage(@RequestParam int messageId){
		
		ModelAndView result;
		Message message;
		
		Message m=messageService.findOne(messageId);
		message=messageService.prepareReply(m);
		
		MessageForm messageForm=new MessageForm();
		messageForm.setRecipient(message.getRecipientId());
		messageForm.setSubject(message.getSubject());
		
		result=createEditModelAndView(messageForm);
		result.addObject("isReply",true);

		return result;
	}
	
	//Create ----------------------------------------
		@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create(){
			ModelAndView result;
			MessageForm messa=new MessageForm();
			result=createEditModelAndView(messa);
			
			return result;
		}
		
		// Save--------------------------------------
		
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")		
		public ModelAndView save(@Valid MessageForm messa, BindingResult binding){
			ModelAndView result;
			
			if(binding.hasErrors()){
				result=createEditModelAndView(messa);
			} else{
				try{

					Message message=messageService.reconstruct(messa);
					messageService.save(message);
					messageService.sendMessage(message);
					result=new ModelAndView("redirect:list.do");
				} catch(Throwable oops){
					result=createEditModelAndView(messa,"invoice.commit.error");
				}
			}
			return result;
		}
		@RequestMapping(value="/edit",method=RequestMethod.POST,params="reply")		
		public ModelAndView saveReply(@Valid MessageForm messa, BindingResult binding){
			ModelAndView result;
			
			if(binding.hasErrors()){
				result=createEditModelAndView(messa);
			} else{
				try{
					Message message=messageService.reconstruct(messa);
					messageService.save(message);
					messageService.sendMessage(message);
					result=new ModelAndView("redirect:list.do");
				} catch(Throwable oops){
					result=createEditModelAndView(messa,"invoice.commit.error");
				}
			}
			return result;
		}
		// Ancillary methods
		protected ModelAndView createEditModelAndView(MessageForm messa){
			ModelAndView result;
			result=createEditModelAndView(messa,null);		
			return result;
		}
		
		protected ModelAndView createEditModelAndView(MessageForm messa, String message){
			ModelAndView result;
			Actor sender=actorService.findByUserAccount(LoginService.getPrincipal());
//			Folder folder;
//			Date moment;
					
			Boolean isReply=false;
			
			Collection<Actor> recipients=actorService.findAll();
			recipients.remove(sender);
			
			
			result=new ModelAndView("message/edit");
			result.addObject("requestURI","message/edit.do");
			result.addObject("messageForm", messa);
			result.addObject("recipients",recipients);
			result.addObject("isReply", isReply);
			return result;
		}
		
		protected ModelAndView createEditModelAndViewReply(MessageForm messa){
			ModelAndView result;
			result=createEditModelAndViewReply(messa,null);		
			return result;
		}
		
		protected ModelAndView createEditModelAndViewReply(MessageForm messa, String message){
			ModelAndView result;
			Actor sender=actorService.findByUserAccount(LoginService.getPrincipal());
//			Folder folder;
//			Date moment;
					
			Boolean isReply=true;
			
			Collection<Actor> recipients=actorService.findAll();
			recipients.remove(sender);
			
			result=new ModelAndView("message/edit");
			result.addObject("requestURI","message/reply.do");
			result.addObject("messageForm", messa);
			result.addObject("recipients",recipients);	
			result.addObject("isReply",isReply);
			return result;
		}
	
}
