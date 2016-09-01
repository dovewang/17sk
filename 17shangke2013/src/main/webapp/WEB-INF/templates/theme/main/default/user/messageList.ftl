<#assign messageType=messageType!0 />
<#assign allowReplay=allowReplay!true />
<#if result.totalCount==0>
<div  class="nodata">
	没有任何消息
</div>
<#else>
<!-- 学校公告 或者 系统消息 -->
<#if (messageType==1) || (messageType==2)>
<#list result.result as m>
<dl class="list-item message-item" id="messageInfo_dl_${m.message_id}" action-type="message-item" messageid="${m.message_id}">
	<dt class="face">
		<#if messageType==1> <img  src="/theme/school/default/images/message_school.jpg" width="40" height="40"/></a>
		<#elseif messageType==2> <img  src="/theme/school/default/images/message_sys.jpg" width="40" height="40"/></a>
        <#else>
         <a href="/u/${m.sender}.html" target="_blank"><img usercard="${m.sender}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt="学生名"/></a>
        </#if>
	</dt>
	<dd class="content">
		<p>			
			<#if messageType==1>
			学校公告
			<#elseif messageType==2>
			系统消息
            <#else>
            <a href="/u/${m.sender}.html" target="_blank" usercard="${m.sender}" >${m.sender}</a>
			</#if>：
            <span class="message-status${m.receive_status}">
            	<#if m.subject??>
            		${m.subject}
            	</#if>
            	${bbcodeHelper.doFilter(m.message,"1").getReplaceContent()}
            </span>			
		</p>
		<p class="data-info">
			<span class="link-group float-right">
				<a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.del(${m.message_id});">删除</a>
             </span>${flint.timeToDate(m.send_time,"yyyy-MM-dd HH:mm:ss")}
		</p>
	</dd>
</dl>
</#list>
<!-- 私人消息 -->
<#elseif messageType==0>
<div style="font-size:14px;font-weight:500;text-align:left;padding:10px;"><font>我的私信</font>(已有${result.totalCount}个联系人)</div>
<#list result.result as m>
<dl class="list-item message-item"
	 <#if m.sender?c!=USER_ID>
	 	 id="mymessageList_${m.sender}"
	 	 onclick = "MC.getMessagePage(9,${m.sender});"
	 <#else>
	     id="mymessageList_${m.receiver}"
	  	 onclick = "MC.getMessagePage(9,${m.receiver});"
	 </#if>
	 onmouseover="$(this).addClass('mouse-over');" onmouseout="$(this).removeClass('mouse-over');">
	<dt class="face">
        	<#if m.sender?c!=USER_ID>
        		<a href="/u/${m.sender}.html"  target="_blank"><img  usercard="${m.sender}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt="学生名"/></a>
        	<#else>
        		<a href="/u/${m.receiver}.html"  target="_blank"><img usercard="${m.receiver}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt="学生名"/></a>
        	</#if>
	</dt>
	<dd class="content">
		<p>			
        	<#if m.sender?c!=USER_ID>
        		<a id="message_${m.message_id}" href="/u/${m.sender}.html" target="_blank" usercard="${m.sender}" >${m.sender}</a>
        	<#else>
        		<font style='color:blue;font-size:12px;'>发给</font><a id="message_${m.message_id}" href="/u/${m.receiver}.html" target="_blank" usercard="${m.receiver}" >${m.receiver}</a>
        	</#if>：
            <span class="message-status${m.receive_status}">${bbcodeHelper.doFilter(m.message,"1").getReplaceContent()}</span>		
		</p>
		<p class="data-info">
			<span class="link-group float-right">
				共有信息${m.count_message}条
					<#if m.sender?c!=USER_ID>
						<a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delAll(${m.sender});">删除</a>|
						<a href="javascript:;" onclick="MC.messagelistShowForm(event,${m.sender},$('#message_${m.message_id}[usercard=${m.sender}]').text());return false;">回复</a>
					<#else>
						<a href="javascript:;" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delAll(${m.receiver});">删除</a>|
						 <a href="javascript:;" onclick="MC.messagelistShowForm(event,${m.receiver},$('#message_${m.message_id}[usercard=${m.receiver}]').text());return false;">回复</a>
					</#if>
             </span>${flint.timeToDate(m.send_time,"yyyy-MM-dd HH:mm:ss")}
		</p>
	</dd>
</dl>
</#list>
<!-- 私人详细消息 -->
<#elseif messageType==9>
<div class="private_shresults">
<#list result.result as m>
  <#if m_index!=0&&m.sender==upSender><!--如果不是第一条记录并且本条发送者等于上一个发送者-->
  		 <div id="messageInfo_line_${upIndex}" class="W_linedot"></div>
  	  	 <div id="messageInfo_content_${m.message_id}" class="R_msg">
	        <div class="private_operate clearfix">
	          <div class="close">
	           <#if m.sender?c!=USER_ID> 
		        <a href="javascript:;" class="hover W_close_color" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delPrivate(${m.message_id},${dlIndex},true);"></a>
	           <#else>
	            <a href="javascript:;" class="hover W_close_color" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delPrivate(${m.message_id},${dlIndex});"></a>
	           </#if>
	          </div>
	          <div class="txt">
	          	<#if m.sender?c!=USER_ID>
	          	 <a href="/u/${m.sender}.html" target="_blank" usercard="${m.sender}" >${m.sender}</a>：
	          	<#else>
	          	   我 ：
	          	</#if>
	          	<span class="message-status${m.receive_status}">
	          	${bbcodeHelper.doFilter(m.message,"1").getReplaceContent()}
	          	</span>
	          </div>
	          <div class="operation">
	            <div class="fl"><em class="W_textb date">${flint.timeToDate(m.send_time,"yyyy-MM-dd HH:mm:ss")}</em></div>
	            <div class="fr">
	            <#if m.sender?c!=USER_ID> 
					<a class="W_linka" href="javascript:;" onclick="MC.showForm(${m.sender},$($('a[usercard=${m.sender}]')[0]).text());return false;">回复</a>
			    </#if>
	            </div>
	          </div>
	        </div>
	      </div>
	  <#assign upIndex=m.message_id /><!--记录ID-->
  <#else>
  	  <#assign upSender=m.sender /><!--记录接收人-->
  	  <#assign upIndex=m.message_id /><!--记录ID-->
  	  <#assign dlIndex=m_index /><!--记录dl块-->
	  <#if m.sender?c!=USER_ID>
	    <dl class="private_SRLl clearfix message-item" id="messageInfo_dl_${dlIndex}"><!--发送者-->
	  <#else>
		<dl class="private_SRLr clearfix message-item" id="messageInfo_dl_${dlIndex}"><!--接收者-->
	  </#if>
	    <dt class="face">
	    	<a href="/u/${m.sender}.html" target="_blank"><img usercard="${m.sender}" src="/theme/school/default/images/noface_s.jpg" width="50" height="50" alt="学生名"/></a> 
	    </dt>
	    <dd class="W_border content">
	      <div id="messageInfo_content_${m.message_id}" class="R_msg">
	        <div class="private_operate clearfix">
	          <div class="close">
	          	 <#if m.sender?c!=USER_ID> 
			       <a href="javascript:;" class="hover W_close_color" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delPrivate(${m.message_id},${dlIndex},true);"></a>
		         <#else>
		           <a href="javascript:;" class="hover W_close_color" node-type="pop-confirm" data-message="您确定要删除该信息？" ok="MC.delPrivate(${m.message_id},${dlIndex});"></a>
		         </#if>
	          </div>
	          <div class="txt">
	          	<#if m.sender?c!=USER_ID>
	          	 <a href="/u/${m.sender}.html" target="_blank" usercard="${m.sender}" >${m.sender}</a>：
	          	<#else>
	          	  我 ：
	          	</#if>
	          	<span class="message-status${m.receive_status}">
	          	 ${bbcodeHelper.doFilter(m.message,"1").getReplaceContent()}
	          	 </span>
	          </div>
	          <div class="operation">
	            <div class="fl"><em class="W_textb date">${flint.timeToDate(m.send_time,"yyyy-MM-dd HH:mm:ss")}</em></div>
	            <div class="fr">
            	<#if m.sender?c!=USER_ID> 
					<a class="W_linka" href="javascript:;" onclick="MC.showForm(${m.sender},$($('a[usercard=${m.sender}]')[0]).text());return false;">回复</a>
			    </#if>
	            </div>
	          </div>
	        </div>
	      </div>
  </#if>
  <#if m_has_next&&result.result[m_index+1].sender!=m.sender || !m_has_next><!--如果下一条记录不是本人-->
  	 	<div class="arrow"></div>
	    </dd>
	  </dl>
	  <div class="W_linedot clearfix" id="messageInfo_dl_line_${dlIndex}"></div>
  </#if>
  </#list>
</div>
</#if>
<@flint.pagination result,"","","" /> </#if>