<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-6">
	    <form role="form" method="POST" action="/purchase/make">
	        <div class="panel panel-info">
	            <div class="panel-heading">
	                Confirmação de compra
	            </div>
	            <div class="panel-body">
	                <b>Partida:</b> ${schedule.startDestination.cityName} (${schedule.startDestination.airportName}) as <b><fmt:formatDate pattern="HH:mm" value="${schedule.startAt}" /></b> <br />
	                <b>Destino:</b> ${schedule.endDestination.cityName} (${schedule.endDestination.airportName}) as <b><fmt:formatDate pattern="HH:mm" value="${schedule.endAt}" /></b> <br />
	                <b>Valor:</b> ${schedule.priceVerbose} <br /><br />

					<b>Dados bancários</b>
					
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-danger">
	                        ${errorMessage}
	                    </div>
                    </c:if>
					<hr />

	            	<input type="hidden" id="schedule_id" name="schedule_id" value="${schedule.id}" />
					<div class="form-group col-md-4">
					    <label>Agência</label>
					    <input class="form-control" type="text" id="agency" name="agency" maxlength="6" />				    
					</div>					
					<div class="form-group col-md-4">
					    <label>Conta</label>
					    <input class="form-control" type="text" id="account" name="account" maxlength="6" />				    
					</div>
	            </div>
	            
	            <div class="panel-footer">
					<div class="form-group">					    
					    <input class="btn btn-success btn-sm" type="submit" value="Confirmar" />				    
					</div>
	            </div>
	        </div>  
		</form>
   	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("#agency, #account").keyup(function () { 
		    this.value = this.value.replace(/[^0-9\.]/g,'');
		});
	});
</script>