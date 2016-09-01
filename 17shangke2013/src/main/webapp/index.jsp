
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%
  //request.getRequestDispatcher("come.htm").forward(request,response);
	/*  本页面只起数据转发作用*/
	String userType = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if ("u_token_t".equals(cookie.getName())) {
				userType = cookie.getValue();
			}
		}
	}
	 
	if (userType == null) {
		request.getRequestDispatcher("home.htm").forward(request,response);
	} else {
		request.getRequestDispatcher("index.html").forward(request,response);
	}
%>