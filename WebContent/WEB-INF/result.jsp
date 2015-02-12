<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <h1>Welcome ${twitter.screenName}</h1>
	<h2>${msg}</h2>
	
				<table class="table table-hover">
				<c:forEach var="t" items="${tweets}">
					<tr>
						<td>${t}</td>

					</tr>
				</c:forEach>
			</table>
	
	
    <form action="result.do" method="post">
        <textarea cols="80" rows="2" name="text">
        show the result there and ready to tweet
        
        </textarea>
        <input type="submit" name="action" value="tweet"/> 
    </form>
    <a href="logout.do">logout</a>
</body>
</html>