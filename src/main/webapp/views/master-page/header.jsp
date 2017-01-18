<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="AcmeDistributor" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		
		<security:authorize access="hasRole('DISTRIBUTOR')">
			<li><a class="fNiv"><spring:message	code="master.page.warehouses" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="warehouse/list.do"><spring:message code="master.page.warehouses.all" /></a></li>
					<li><a href="warehouse/distributor/list.do"><spring:message code="master.page.warehouses.mine" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv" href="item/list.do"><spring:message code="master.page.items" /></a></li>
			<li><a class="fNiv" href="tax/distributor/list.do"><spring:message code="master.page.taxes" /></a></li>
			<li><a class="fNiv" href="producer/list.do"><spring:message code="master.page.producers" /></a></li>
			<li><a class="fNiv" href="customer/list.do"><spring:message code="master.page.customers" /></a></li>
			<li><a class="fNiv" href="invoice/distributor/list.do"><spring:message code="master.page.invoices" /></a></li>
			
			<li><a class="fNiv" href="distributor/register.do"><spring:message code="master.page.distributor.register" /></a></li>
		</security:authorize>
		
		
		
		<security:authorize access="hasRole('PRODUCER')">
			<li><a class="fNiv" href="warehouse/list.do"><spring:message code="master.page.warehouses" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.items" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a  href="item/list.do"><spring:message code="master.page.item.all" /></a></li>
					<li><a  href="item/producer/list.do"><spring:message code="master.page.item.mine" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv" href="warehouse/list.do"><spring:message code="master.page.warehouses" /></a></li>
			<li><a class="fNiv" href="item/list.do"><spring:message code="master.page.items" /></a></li>
			<li><a class="fNiv" href="invoice/customer/myInvoices.do"><spring:message code="master.page.myInvoices" /></a></li>
			<li><a class="fNiv" href="customer/shoppingCart.do"><spring:message code="master.page.shoppingCart" /></a></li>
			<li><a class="fNiv" href="comment/customer/list.do"><spring:message code="master.page.comments" /></a></li>
		</security:authorize>

		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="warehouse/list.do"><spring:message code="master.page.warehouses" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a  href="customer/register.do"><spring:message code="master.page.register.customer" /></a></li>
					<li><a  href="producer/register.do"><spring:message code="master.page.register.producer" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/list.do"><spring:message code="master.page.profile.messages" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

