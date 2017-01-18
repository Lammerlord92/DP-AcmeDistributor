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

<form:form action="customer/register.do" modelAttribute="customerForm">
	<fieldset>
		<legend align="left">
			<spring:message code="customer.userAccount" />
		</legend>	
		<acme:textbox code="customer.userAccount.username" path="userName"/>
		<acme:password code="customer.userAccount.password" path="password"/>
		<acme:password code="customer.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="customer.personalInfo" />
		</legend>
			
		<acme:textbox code="customer.name" path="name"/>
		<acme:textbox code="customer.surname" path="surname"/>
		<acme:textbox code="customer.email" path="email"/>
		<acme:textbox code="customer.address" path="address"/>
		<acme:textbox code="customer.contactPhone" path="contactPhone"/>
		<acme:textbox code="customer.birthday" path="birthday"/>
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code="customer.creditCard" />
		</legend>
			
		<acme:textbox code="customer.credit.holderName" path="creditCard.holderName"/>
		<acme:textbox code="customer.credit.brandName" path="creditCard.brandName"/>
		<acme:textbox code="customer.credit.number" path="creditCard.number"/>
		<acme:textbox code="customer.credit.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="customer.credit.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="customer.credit.cvvCode" path="creditCard.cvvCode"/>
	</fieldset>	
	
	<p><acme:checkbox code="customer.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<input type="submit" name="save" value="<spring:message code="customer.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="customer.cancel"/>" 
	onclick="javascript: window.location.replace('/AcmeDistributor')"/>
</form:form>