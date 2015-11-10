<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Dashboard</h1>
        
        <c:forEach items="${agrupedSchedules}" var="scheduleByDate">
	        <c:forEach items="${scheduleByDate}" var="schedule">
		        <table class="table table-striped">
		        	<thead>
		        		<tr>
		        			<th>Saída</th>
		        			<th>Chegada</th>
		        			<th>Preço</th>
		        			<th></th>
		        		</tr>
		        	</thead>
		        	<tbody>
		        		<tr>
		        			<td>${schedule.startDate}</td>
		        			<td>${schedule.endDate}</td>
		        			<td>${schedule.price}</td>
		        			<td></td>
		        		</tr>
		        	</tbody>
		        </table>
	        </c:forEach>
        </c:forEach>
    </div>    
</div>