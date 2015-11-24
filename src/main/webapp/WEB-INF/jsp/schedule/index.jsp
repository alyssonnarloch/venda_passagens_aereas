<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Horários</h1>
        
        <c:forEach items="${agrupedSchedules}" var="scheduleByDate">
			<div class="panel panel-green">     
				<div class="panel-heading">${scheduleByDate.key}</div>   	
	       		<table class="table table-striped">
		        	<thead>
		        		<tr>
		        			<th><span class="label label-primary">${startDestination.cityName} - ${startDestination.airportName} <span class="fa fa-plane"></span></span></th>
		        			<th><span class="label label-success">${endDestination.cityName} - ${endDestination.airportName} <span class="glyphicon glyphicon-plane"></span></span></th>
		        			<th><span class="label label-default">Preço <span class="fa fa-money"></span></span></th>
		        			<th><span class="fa fa-gear"></span></th>
		        		</tr>
		        	</thead>
		        	<c:forEach items="${scheduleByDate.value}" var="schedule">		        
			        	<tbody>
			        		<tr>
			        			<td><fmt:formatDate pattern="H:m" value="${schedule.startAt}" /></td>
			        			<td><fmt:formatDate pattern="H:m" value="${schedule.endAt}" /></td>
			        			<td>${schedule.priceVerbose}</td>
			        			<td><a href="#" class="btn btn-success btn-sm" type="button" style="color: white;">Comprar <span class="glyphicon glyphicon-shopping-cart"></span></a></td>
			        		</tr>
			        	</tbody>
		        	</c:forEach>
		        </table>
	        </div>
	        
        </c:forEach>
    </div>    
</div>