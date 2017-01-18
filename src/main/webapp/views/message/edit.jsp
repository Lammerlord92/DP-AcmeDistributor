<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI }" modelAttribute="messageForm">

	<jstl:if test="${isReply=='false'}">
		<div>
		<spring:message code="message.recipient" />:
			<form:select path="recipient">
				<form:option label="----" value="0" />
				<form:options items="${recipients}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<div>
			<acme:textbox code="message.subject" path="subject"/>
		</div>
	</jstl:if>
	
	<jstl:if test="${isReply=='true'}">
		<form:hidden path="recipient"/>
		<form:hidden path="subject"/>
	</jstl:if>
	
	
	<acme:textarea code="message.body" path="body"/>
	
	
	
	<div>
	<jstl:if test="${isReply=='false'}">
		<input type="submit" name="save" value="<spring:message code="message.create"/>" />
	</jstl:if>
	<jstl:if test="${isReply=='true'}">
		<input type="submit" name="reply" value="<spring:message code="message.reply"/>" />
	</jstl:if>
		&nbsp;
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel"/>"
			onClick="javascript: window.location.replace('message/list.do');" />
	</div>
</form:form>