<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Destinos</h1>
        
		<div class="panel-body col-lg-4">
            <div class="list-group">
		        <c:forEach items="${cities}" var="city">
	                <a href="#" class="list-group-item">
	                	${city.cityName} (${city.airportName})
<!-- 	                    <i class="fa fa-comment fa-fw"></i>	                     -->
	                    </span>
	                </a>
	    	    </c:forEach>
            </div>
        </div>	        
    </div>    
</div>