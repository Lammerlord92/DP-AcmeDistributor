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

<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=true"></script>
 
<script type="text/javascript">
  function loadMap() {
	var latlng = new google.maps.LatLng("${lon}", "${lat}");

    var myOptions = {
      	zoom: 12,
      	center: latlng,
      	mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
    
    var warehouseOptions = {
			map: map,
			position: latlng,
			content: 
			  '<h1>'+"${name}"+'</h1>'+
			  '<p><spring:message code="warehouse.distributor.name"/>:'+ "${distName}" +'</p>'+
			  '<p><spring:message code="warehouse.contactPhone"/>:'+ "${phone}" +'</p>'+
			  '<p><spring:message code="warehouse.email"/>:'+ "${email}" +'</p>'+
			  '<p><spring:message code="warehouse.address"/>:'+ "${address}" +'</p>'+
			  '<a href="item/listByWarehouse.do?warehouseId=${id}"><spring:message code="warehouse.listItems"/></a>'

	};
    var warehouseInfWin = new google.maps.InfoWindow(warehouseOptions);
    var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        title: "${name}"
    });
    google.maps.event.addListener(marker, 'click', function() {
    	warehouseInfWin.open(map,marker);
    });
    
    if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			
			var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			var marker = new google.maps.Marker({
		        position: pos,
		        map: map,
		        title: 'My location'
		    });

		}, function(){
			handleNoGeolocation(true);
		});
		}else{
			handleNoGeolocation(false);
		}
  }
</script>
 
<body onload="loadMap()">
	<div id="map_container"></div>
	<div id="content"></div>
</body>
<style type="text/css">
	div#map_container{
		width:70%;
		height:500px;
		margin-left: auto;
  		margin-right: auto;
	}
</style>