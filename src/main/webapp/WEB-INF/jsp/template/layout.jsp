<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <title>Travel HTML5 Template</title>
        <meta name="description" content="">

        <meta name="viewport" content="width=device-width">
		<meta name="author" content="templatemo">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,800,700,600,300' rel='stylesheet' type='text/css'>
        
        <link href="<c:url value='/public/css/bootstrap.min.css' />" rel="stylesheet" type="text/css" /><link>
        <link href="<c:url value='/public/css/font-awesome.css' />" rel="stylesheet" type="text/css" /><link>
        <link href="<c:url value='/public/css/animate.css' />" rel="stylesheet" type="text/css" /><link>
        <link href="<c:url value='/public/css/templatemo_misc.css' />" rel="stylesheet" type="text/css" /><link>
        <link href="<c:url value='/public/css/templatemo_style.css' />" rel="stylesheet" type="text/css" /><link>
        
        <script src="<c:url value='/public/js/vendor/modernizr-2.6.1-respond-1.1.0.min.js' />"></script>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="menu" />
		<tiles:insertAttribute name="body" /> 
    </body>
</html>