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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="comments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column title="${sessionsCol}">
			<a href="comment/customer/edit.do?commentId=${row.id}"><spring:message code="comment.edit"/></a>
			<br/>
			<a href="item/details.do?itemId=${row.itemSource.id}"><spring:message code="comment.viewItem"/></a>
		</display:column>
	</security:authorize>
	
	<spring:message code="comment.text" var="text"/>
	<display:column property="text" title="${text}" sortable="true" />
	
	<spring:message code="comment.itemSource.name" var="itemSource.name"/>
	<display:column property="itemSource.name" title="${itemSource.name}" sortable="true" />
</display:table>