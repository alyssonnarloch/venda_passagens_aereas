<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Hor�rios</h1>
        
        <c:forEach items="${agrupedSchedules}" var="scheduleByDate">
        	<h4>${scheduleByDate.key}</h4>
        	
       		<table class="table table-striped">
	        	<thead>
	        		<tr>
	        			<th>Sa�da</th>
	        			<th>Chegada</th>
	        			<th>Pre�o</th>
	        			<th>A��o</th>
	        		</tr>
	        	</thead>
	        	<c:forEach items="${scheduleByDate.value}" var="schedule">		        
		        	<tbody>
		        		<tr>
		        			<td><fmt:formatDate pattern="H:m" value="${schedule.startAt}" /></td>
		        			<td><fmt:formatDate pattern="H:m" value="${schedule.endAt}" /></td>
		        			<td>${schedule.priceVerbose}</td>
		        			<td>+</td>
		        		</tr>
		        	</tbody>
	        	</c:forEach>
	        </table>
        </c:forEach>
    </div>    
</div>