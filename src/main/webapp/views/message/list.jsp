<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fieldset>
	<legend align="left">
		<spring:message code="message.inbox"/>
	</legend>
	<display:table name="messagesInInbox" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="message.moment" var="moment"/>
		<display:column property="moment" title="${moment}" sortable="true" />
		
		<spring:message code="message.subject" var="subject"/>
		<display:column property="subject" title="${subject}" sortable="true" />
		
		<spring:message code="message.body" var="body"/>
		<display:column property="body" title="${body}" sortable="true" />

		<display:column>
			<a href="message/reply.do?messageId=${row.id}">
				<spring:message code="message.reply"></spring:message>
			</a>

		</display:column>	
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="message.outbox"/>
	</legend>
	<display:table name="messagesInOutbox" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="message.moment" var="moment"/>
		<display:column property="moment" title="${moment}" sortable="true" />
		
		<spring:message code="message.subject" var="subject"/>
		<display:column property="subject" title="${subject}" sortable="true" />
		
		<spring:message code="message.body" var="body"/>
		<display:column property="body" title="${body}" sortable="true" />
	</display:table>
</fieldset>

<div>
	<input type="button" name="create"
		value="<spring:message code="message.create"/>"
		onClick="javascript: window.location.replace('message/create.do');"/>
&nbsp; 
</div>