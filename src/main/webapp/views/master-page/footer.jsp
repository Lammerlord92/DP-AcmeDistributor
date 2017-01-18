<%--
 * footer.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<script>

function nuevo(){
	nuevaVentana=window.open('', '', 'width=330,height=252,scrollbars=NO,statusbar=NO,left=500,top=250');
	nuevaVentana.document.write("<HTML><HEAD><TITLE>"+"Acerca de las cookies</TITLE></HEAD>\n");
	nuevaVentana.document.write("<BODY>Esta página requiere del uso de cookies para almacenar información acerca del idioma y de su sesión, una vez iniciada. Si sigue navengado en esta web, se asumirá que acepta el uso de dichas cookies.<form>\n");
	nuevaVentana.document.write("<input type='button' "+"value='Aceptar' onClick='window.close();'>\n");
	nuevaVentana.document.write("</form>\n");
	nuevaVentana.document.write("</BODY></HTML>\n");
	nuevaVentana.document.close();
}
</script>

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Group 32, DP</b>

<!-- Pop-up de cookies -->
<div id="overbox3">
	<div id="infobox3">
		<p>Esta web utiliza cookies. 
	    	<a onClick="nuevo();" style="cursor:pointer;">(About)</a>
	    </p>
	</div>
</div>

<script>

function condiciones(){
	nuevaVentana=window.open('', '', 'width=330,height=252,scrollbars=NO,statusbar=NO,left=500,top=250');
	nuevaVentana.document.write("<HTML><HEAD><TITLE>"+"Acerca de los términos de uso</TITLE></HEAD>\n");
	nuevaVentana.document.write("<BODY> Este registro implica la aceptacion de las leyes europeas correspondiente a la Directiva 2006-123-EC y a la Regulación 1211-2009, así como las leyes españolas de la LOPD (LO15-1999) de la LSSI (L34-2002) y de las Trasposiciones (RDL-13-2012) .<form>\n");
	nuevaVentana.document.write("<input type='button' "+"value='Aceptar' onClick='window.close();'>\n");
	nuevaVentana.document.write("</form>\n");
	nuevaVentana.document.write("</BODY></HTML>\n");
	nuevaVentana.document.close();
}
</script>