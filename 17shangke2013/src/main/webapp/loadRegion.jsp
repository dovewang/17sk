<% long start=System.currentTimeMillis();%>
<%@ page language="java"  import="org.springframework.web.context.WebApplicationContext,
org.springframework.web.context.support.WebApplicationContextUtils,
flint.context.RegionHelper,flint.context.Region,java.util.*"
	pageEncoding="utf-8"%>
<%
    ServletContext servletContext = pageContext.getServletContext();
	WebApplicationContext wac = WebApplicationContextUtils
			.getRequiredWebApplicationContext(servletContext);
	RegionHelper  regionHelper =wac.getBean(RegionHelper.class);
%>
<% List<Region> list=regionHelper.getProvices();
int r_index=0;
for(Region r:list){
    out.print("{code:"+r.getCode()+",name:\""+r.getName()+"\",jp:\""+r.getJp()+"\",areas:[");
    List<Region> areas=regionHelper.getAreas(r.getCode());
    int a_index=0;
    for(Region a:areas){
        out.print("{code:"+a.getCode()+",name:\""+a.getName()+"\",citys:[");
         List<Region> citys=regionHelper.getCitys(a.getCode());
         int c_index=0;
         for(Region c:citys){
            out.print("{code:"+c.getCode()+",name:\""+c.getName()+"\"}");
            if(c_index!=citys.size()-1){
            out.print(",");
          }
          c_index++;
         }
         out.print("]}");
          if(a_index!=areas.size()-1){
           out.print(",");
         }
          a_index++;
    }
      out.print("]}");
      if(r_index!=list.size()-1){
           out.print(",");
      }
      r_index++;
}
%>