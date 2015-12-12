<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-4">
			<div class="panel panel-success">
                <div class="panel-heading">
                    Minhas compras
                </div>
                <div class="panel-body">
                    <div class="panel-group" id="accordion">

						<c:forEach items="${purchases}" var="purchase">                    
	                        <div class="panel panel-default">
	                            <div class="panel-heading">
	                                <h4 class="panel-title">
	                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${purchase.id}" aria-expanded="false" class="collapsed">
	                                    	<fmt:formatDate pattern="dd/MM/yyyy H:m:s" value="${purchase.createAt}" /> - ${purchase.statusVerbose}
                                    	</a>
	                                </h4>
	                            </div>
	                            <div id="collapse${purchase.id}" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
	                                <div class="panel-body">
                  	                	<b>Partida:</b> ${purchase.startDestinationName} as <b><fmt:formatDate pattern="H:m" value="${purchase.startAt}" /></b> <br />
                  	                	<b>Destino:</b> ${purchase.endDestinationName} as <b><fmt:formatDate pattern="H:m" value="${purchase.endAt}" /></b> <br />
						                <b>Valor:</b> ${purchase.priceVerbose} <br /><br />
						                <a class="btn btn-danger" href="/purchase/cancel?id=${purchase.id}">Cancelar compra</a>
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