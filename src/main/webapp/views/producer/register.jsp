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

<form:form action="producer/register.do" modelAttribute="producerForm">
	<fieldset>
		<legend align="left">
			<spring:message code="producer.userAccount" />
		</legend>	
		<acme:textbox code="producer.userAccount.username" path="userName"/>
		<acme:password code="producer.userAccount.password" path="password"/>
		<acme:password code="producer.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="producer.personalInfo" />
		</legend>
			
		<acme:textbox code="producer.name" path="name"/>
		<acme:textbox code="producer.surname" path="surname"/>
		<acme:textbox code="producer.email" path="email"/>
		<acme:textbox code="producer.address" path="address"/>
		<acme:textbox code="producer.contactPhone" path="contactPhone"/>
		<acme:textbox code="producer.birthday" path="birthday"/>
	</fieldset>
	
	
	<p><acme:checkbox code="producer.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<input type="submit" name="save" value="<spring:message code="producer.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="producer.cancel"/>" 
	onclick="javascript: window.location.replace('/AcmeDistributor')"/>
</form:form>