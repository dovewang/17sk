<#include "/conf/config.ftl" />
<#assign id=RequestParameters["id"]!0 />
<#assign answer=RequestParameters["answer"]!0 />
<#if flay==1>
	<div class="comments-box">
	     <form id="commentForm${id}" answer="${answer}" action="/course/postComments.html">
	            <dl>
	            <dd><strong>这答案对您有帮助吗？</strong>
	            <span><input name="anwerScore${id}" id="courseScore3" type="radio" value="2" /><label for="courseScore3" id="cs3">非常有用</label></span>
	            <span><input name="anwerScore${id}" id="courseScore2" type="radio" value="1" /><label for="courseScore2" id="cs2">有用</label></span>
	            <span><input name="anwerScore${id}" id="courseScore1" type="radio" value="0" /><label for="courseScore1" id="cs1">一般</label></span>
	            <span><input name="anwerScore${id}" id="courseScore0" type="radio" value="-1" /><label for="courseScore0" id="cs0">作用不大</label></span>
	            </dd>
	            <dd>
	            <span id="rate"><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a><a href="#" ></a></span></dd>
	            <dd><strong>还想说一句</strong>
	                 <textarea row="1" class="input" name="content" id="newcom_${id}" style="width:98%"></textarea>
	            </dd>
	            <dd>
		            <a href="javascript:void(0)" class="icon-face" onclick="loadFace(this,'newcom_${id}')">表情</a>
		            <button class="button comment float-right" type="button" onclick="Answer.postComments(${id});return false;">评论</button>
	            </dd>
	            </dl>
	      </form>   
	</div>
</#if>
<#if result.totalCount==0>
<p style="text-align:center">没有任何评论信息</p>
</#if>
<#list result.result as m>
<dl class="answer-item" id="answercomment${m.id}">
	<dt class="face">
		<a href="/u/${m.userId}.html" target="_blank"><img usercard="${m.userId}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt="学生名"/></a>
	</dt>
	<dd class="content">
		<p>
			<a href="/u/${m.userId}.html" target="_blank" usercard="${m.userId}">${m.userId}</a>：
			${bbcodeHelper.doFilter(m.comment?default(''),"1").getReplaceContent()}
		</p>
		<p class="data-info">
			<!-- 
			<span class="float-right"> <a href="#" onclick="Question.replyComment(${m.id},${id},${m.userId});return false;">回复</a> </span>${helper.passTime(m.dateline)}
			-->
		</p>
	</dd>
</dl>
</#list>