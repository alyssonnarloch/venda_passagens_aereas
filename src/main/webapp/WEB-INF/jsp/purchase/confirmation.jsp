<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-4">
	    <form role="form">
	        <div class="panel panel-info">
	            <div class="panel-heading">
	                Confirma��o de compra
	            </div>
	            <div class="panel-body">
	                <b>Partida:</b> ${schedule.startDestination.cityName} (${schedule.startDestination.airportName}) as <b><fmt:formatDate pattern="H:m" value="${schedule.startAt}" /></b> <br />
	                <b>Destino:</b> ${schedule.endDestination.cityName} (${schedule.endDestination.airportName}) as <b><fmt:formatDate pattern="H:m" value="${schedule.endAt}" /></b> <br />
	                <b>Valor:</b> ${schedule.priceVerbose} <br /><br />

					<b>Dados banc�rios</b>
					<hr />

	            	<input type="hidden" id="schedule_id" name="schedule_id" value="${schedule.id}" />
					<div class="form-group col-md-4">
					    <label>Ag�ncia</label>
					    <input class="form-control" type="text" id="agency" name="agency" />				    
					</div>					
					<div class="form-group col-md-4">
					    <label>Conta</label>
					    <input class="form-control" type="text" id="agency" name="agency" />				    
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