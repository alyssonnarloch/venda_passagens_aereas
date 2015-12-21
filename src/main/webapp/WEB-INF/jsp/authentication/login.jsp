<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
   <div class="col-md-4 col-md-offset-4">
       <div class="login-panel panel panel-default">
           <div class="panel-heading">
               <h3 class="panel-title">Para efetuar sua compra acesse sua conta</h3>
           </div>
           <div class="panel-body">
               <form role="form" method="POST" action="/authentication">
               		<input type="hidden" name="schedule_id" value="${schedule.id}">
                    <fieldset>
	                    <div class="form-group">
	                        <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus>
	                    </div>
	                    <div class="form-group">
	                        <input class="form-control" placeholder="Senha" name="password" type="password" value="">
	                    </div>
	                    <div class="form-group">
	                    		<input type="submit" class="btn btn-lg btn-success btn-block" value="Login">
	                    </div>
	                    
               			<c:if test="${not empty errorMessage}">
							<div class="alert alert-danger">
		                        ${errorMessage}
		                    </div>
                     	</c:if>
	                    
                    </fieldset>
               </form>
           </div>
       </div>
   </div>
</div>