<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-8">
			<div class="panel panel-info">
                <div class="panel-heading">
                    Minhas compras
                </div>
                <div class="panel-body">
                    <div class="panel-group" id="accordion">

						<c:forEach items="${purchases}" var="purchase">
               				<c:choose>
								<c:when test="${purchase.status == purchase.effectedPurchase}">
									<div class="panel panel-success">	
								</c:when>
								<c:when test="${purchase.status == purchase.canceledPurchase}">
									<div class="panel panel-danger">
								</c:when>
							</c:choose>
	                            <div class="panel-heading">
	                                <h4 class="panel-title">
	                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${purchase.id}" aria-expanded="false" class="collapsed">
	                                    	<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${purchase.createdAt}" /> - ${purchase.statusVerbose}
                                    	</a>
	                                </h4>
	                            </div>
	                            <div id="collapse${purchase.id}" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
	                                <div class="panel-body">
                  	                	<b>Partida:</b> ${purchase.schedule.startDestination.cityName} (${purchase.schedule.startDestination.airportName}) as <b><fmt:formatDate pattern="HH:mm" value="${purchase.schedule.startAt}" /></b> <br />
                  	                	<b>Destino:</b> ${purchase.schedule.endDestination.cityName} (${purchase.schedule.endDestination.airportName}) as <b><fmt:formatDate pattern="HH:mm" value="${purchase.schedule.endAt}" /></b> <br />
						                <b>Valor:</b> ${purchase.priceVerbose} <br /><br />
										
										<c:if test="${purchase.status == purchase.effectedPurchase}">						                
						                	<a class="btn btn-danger" href="/purchase/cancel?id=${purchase.id}">Cancelar compra</a>
						                </c:if>
						                
	                                </div>
	                            </div>
	                        </div>
						</c:forEach>    
						                                           
                    </div>
                </div>
                <!-- .panel-body -->
            </div>
   	</div>
</div>