<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>EE$2015 - Passagens Aéreas</title>

    <link href="/public/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/public/datepicker/css/datepicker.css" rel="stylesheet">
    <link href="/public/jquery-ui/jquery-ui.min.css" rel="stylesheet">    
    <link href="/public/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <link href="/public/dist/css/timeline.css" rel="stylesheet">
    <link href="/public/dist/css/sb-admin-2.css" rel="stylesheet">
    <link href="/public/bower_components/morrisjs/morris.css" rel="stylesheet">
    <link href="/public/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <script src="/public/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="/public/datepicker/js/bootstrap-datepicker.js"></script>
    <script src="/public/jquery-ui/jquery-ui.min.js"></script>
    <script src="/public/jquery-validation/dist/jquery.validate.min.js"></script>    
    <script src="/public/jquery-validation/dist/additional-methods.min.js"></script>    
    <script src="/public/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="/public/bower_components/metisMenu/dist/metisMenu.min.js"></script>    
    <script src="/public/dist/js/sb-admin-2.js"></script>

</head>
<body>	
    <div id="wrapper">

        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/home">Passagens Aéreas</a>
            </div>

            <ul class="nav navbar-top-links navbar-right">                                
                <li class="dropdown">
                	<c:choose>
                		<c:when test="${not empty user.name}">
		                	<span><b>${user.name}</b></span>
                		</c:when>
                		<c:otherwise>
                			<span>Você não está conectado. Clique<a href="/login">aqui</a>para efetuar seu login.</span>
                		</c:otherwise>
                	</c:choose>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="/mypurchases"><i class="fa fa-shopping-cart fa-fw"></i>Minhas compras</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                </li>
            </ul>

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        
                        <li class="sidebar-search">
                        	<form id="form_search" name="pass_search" method="get" action="/schedules">
	                            <div class="form-group">
	                                <c:choose>
	                            		<c:when test="${not empty startDestination}">
	                                		<input type="text" class="form-control" name="start_destination_name" id="start_destination_name" value="${startDestination.cityName} (${startDestination.airportName})" placeholder="Local de Partida">
	                                	</c:when>
	                                	<c:otherwise>
	                                		<input type="text" class="form-control" name="start_destination_name" id="start_destination_name" value="" placeholder="Local de Partida">
	                                	</c:otherwise>
	                                </c:choose>
	                                <input type="hidden" name="start_destination_id" id="start_destination_id" value="${startDestination.id}">
                                </div>	                                
	                            <div class="form-group">
	                            	<c:choose>
	                            		<c:when test="${not empty endDestination}">
	                                		<input type="text" class="form-control" name="end_destination_name" id="end_destination_name" value="${endDestination.cityName} (${endDestination.airportName})" placeholder="Local de Destino">
	                                	</c:when>
	                                	<c:otherwise>
	                                		<input type="text" class="form-control" name="end_destination_name" id="end_destination_name" value="" placeholder="Local de Destino">
	                                	</c:otherwise>
	                                </c:choose>
	                                <input type="hidden" name="end_destination_id" id="end_destination_id" value="${endDestination.id}">
                                </div>                                
                	            <div class="form-group">
	                                <input type="text" class="form-control" name="start_date" id="start_date" value="${startDate}" placeholder="Data de Partida">
                                </div>
                                <div>
                                	<input class="btn btn-primary" type="submit" value="Buscar">
                                </div>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div id="page-wrapper">            
            <tiles:insertAttribute name="body" /> 
        </div>
    </div>    
</body>

<style>
label.error {
	color: red;
	font-weight: bold;
	font-size: 12px;
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
}
</style>

<script type="text/javascript">
	$(function() {
		$("label.error").prev('input').addClass("error");
		
		$("#start_date").datepicker({
			format: 'dd/mm/yyyy'
		});
		
		$("#start_destination_name").autocomplete({
			source: "/loadcities",
			minLength: 2,
	        open: function(event, ui) {
	        	$(".ui-autocomplete").css("z-index", 1000);
	        },
	        select: function(event, ui) {
	        	$("#start_destination_id").val(ui.item.id);
	        }
		});		
		
		$("#end_destination_name").autocomplete({
			source: "/loadcities",
			minLength: 2,
	        open: function(event, ui) {
	        	$(".ui-autocomplete").css("z-index", 1000);
	        },
	        select: function(event, ui) {
	        	$("#end_destination_id").val(ui.item.id);
	        }
		});		
		
		$("#form_search").validate({
			ignore: [],
			onkeyup: false,
			rules: {
				start_destination_id: {
					required: true,
					number: true
				},
				end_destination_id: {
					required: true,
					number: true
				},
				start_date: {
					//required: true,
					date: true
				}
			},
			messages: {
				start_destination_id: {
					required: "Informe o local de partida."
				},
				end_destination_id: {
					required: "Informe o seu destino."
				},
				start_date: {
					//required: "Informe a data de partida.",
					date: "Data inválida."
				}
			}
		});
	});
</script>