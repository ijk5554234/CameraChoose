<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Camera Choose-Index</title>
		<meta name="viewport" content="width=1200,user-scalable=no" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="js/jquery.min.js"></script>
		<script src="js/jquery.poptrox.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/init.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-desktop.css" />
			<link rel="stylesheet" href="css/style-noscript.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
	</head>
	<body>
		<div id="wrapper">
		<h1 style="font-size:35px; padding:15px">Having a hard time to choose cameras?<br/> <br/>Why not simple choose pictures taken by them?</h1>
			<div id="main">
				<div id="reel">

					<!-- Header Item -->
					
						<div id="header" class="item" data-width="400">
							<div class="inner">
								<h1>Let's Chooooose</h1>
								<p>A site helps you make camera choices <br />
								by blind testing with pictures taken by them.</p>
							</div>
						</div>
					
					<!-- Thumb Items -->

						<c:forEach var="picture" items="${pictures}">
                    		<article class="item thumb" data-width="282">
                    		<c:set var="pic" value="${fn:split(picture, '*')}" />
								<h2>${pic[1]}</h2>
								<a href="${pic[0]}"><img src="${pic[0]}" alt=""></a>
							</article>
                		</c:forEach>
					<!-- Filler Thumb Items (just for demonstration purposes) -->
						

				</div>
			</div>
		
			<div id="footer">
				<div class="left">
					<h2><a href="login.do?action=login" >Start here with Twitter Account!</a></h2>
				</div>
				<div class="right">
					<ul class="contact">
						<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
						<li><a href="#" class="icon fa-dribbble"><span class="label">Dribbble</span></a></li>
						<li><a href="#" class="icon fa-pinterest"><span class="label">Pinterest</span></a></li>
						<li><a href="#" class="icon fa-envelope"><span class="label">Email</span></a></li>
					</ul>
					<ul class="copyright">
						<li>&copy; CMU</li><li>Design: Team 5 Inspiration</li>
					</ul>
				</div>
			</div>

		</div>
		

	</body>
</html>