<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>userInfo</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>用户名</th>
				<th>年龄</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users }" var="user">
				<tr>
					<td><c:out value="${user.userName }"></c:out></td>
					<td><c:out value="${user.age }"></c:out></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>