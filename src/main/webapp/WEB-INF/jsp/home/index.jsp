<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="pt-BR" scope="session" />

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Home</h1>
		  <div class="row">
   		    <div class="col-lg-3 col-md-6">
		        <div class="panel panel-green">
		            <div class="panel-heading">
		                <div class="row">
		                    <div class="col-xs-3">
		                        <i class="fa fa-tasks fa-5x"></i>
		                    </div>
		                    <div class="col-xs-9 text-right">
		                        <div class="huge">${numDestinations}</div>
		                        <div>Destinos</div>
		                    </div>
		                </div>
		            </div>
		            <a href="/destinations">
		                <div class="panel-footer">
		                    <span class="pull-left">Visualizar</span>
		                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
		                    <div class="clearfix"></div>
		                </div>
		            </a>
		        </div>
		    </div>
		    <div class="col-lg-3 col-md-6">
		        <div class="panel panel-yellow">
		            <div class="panel-heading">
		                <div class="row">
		                    <div class="col-xs-3">
		                        <i class="fa fa-shopping-cart fa-5x"></i>
		                    </div>
		                    <div class="col-xs-9 text-right">
		                        <div class="huge">124</div>
		                        <div>Minhas compras</div>
		                    </div>
		                </div>
		            </div>
		            <a href="#">
		                <div class="panel-footer">
		                    <span class="pull-left">Visualizar</span>
		                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
		                    <div class="clearfix"></div>
		                </div>
		            </a>
		        </div>
		    </div>		    
		</div>
    </div>
</div>