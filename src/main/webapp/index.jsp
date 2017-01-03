<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>index</title>
<script type="text/javascript" src="${ctxPath}/templateWeb/resource/js/jquery.js"></script>
<script type="text/javascript" src="${ctxPath}/templateWeb/resource/bootstrap/js/bootstrap.min.js"></script>
<link href="${ctxPath}/templateWeb/resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctxPath}/templateWeb/resource/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<form class="form-signin" action="<%=request.getContextPath()%>/index/index" method="post">
			 <input type="email" class="form-control" placeholder="用户名" required autofocus>
	        <input type="password" class="form-control" placeholder="密码" required>
	        <div class="checkbox">
	          <label>
	            <input type="checkbox" value="remember-me"> 保持登陆
	          </label>
	        </div>
	        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		</form>
	</div>
		
	<div class="container">
	
		<form class="form-signin" action="<%=request.getContextPath()%>/index/index2" method="post">
			<input type="submit" value="登录">
		</form>
	</div>
</body>
</html>