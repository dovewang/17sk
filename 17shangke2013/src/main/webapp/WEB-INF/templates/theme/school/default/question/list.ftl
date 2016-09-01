<#include "/conf/config.ftl"/>
<#list  result.result as  question >
<dl class="list-item question-list" id="qitem${question.questionId}">
	<dt class="face">
		<a href="/u/${question.asker}.html" target="_blank"><img usercard="${question.asker}" src="/theme/school/default/images/noface_s.jpg" width="30" height="30" alt=""/></a>
	</dt>
	<dd class="content question-list-content">
        <div class="mark"><a href="/u/${question.asker}.html" target="_blank" usercard="${question.asker}">${question.asker}</a>
			${helper.passTime(question.createTime)}<#if question.createTime!=question.lastUpdateTime>
                
                 <span style="font-size:10px;color:#ccc">(${helper.passTime(question.lastUpdateTime)}编辑过)</span> 
			</#if>
            <span class="question-status" id="question_class_${question.questionId}"> <i class="status${question.status}"></i>&nbsp;${enumHelper.getLabel("question_status",question.status?j_string)!""} </span></div>
		<h2><a class="question-title" href="${SITE_DOMAIN}/qitem-${question.questionId}.html" target="_blank" id='questiontitle_${question.questionId}'>${question.title}</a></h2>
		<p>
            <div class="clearfix"></div>
			<#--未解决的 -->
			<#if (question.asker?string!=USER_ID)&&(question.status!=2&&question.status!=3) >
			<span class="question-list-answer-button"> <#if (question.price>0)><span class="question-price">${question.price}点</span></#if> </span>
			</#if>
		</p>
		<p class="data-info">          
			<span class="link-group float-right"> 
				<a href="${SITE_DOMAIN}/qitem-${question.questionId}.html" target="_blank" id="qanswer_count${question.questionId}"><i class="kicon-ribbon"></i>答案<#if (question.answers>0) >(<em>${question.answers}</em>)</#if></a>
			 | <a href="${SITE_DOMAIN}/qitem-${question.questionId}.html" target="_blank" ><i class="kicon-view"></i>浏览<#if (question.views>0) >(<i>${question.views}</i>)</#if></a>
			  |<#if (TYPE!0)==3> <a href="javascript:void(0)" node-type="question-favor" favor="1" qid="${question.questionId}"><i class="kicon-like-active"></i>取消收藏</a> <#else>
			   <a href="javascript:void(0)" qid="${question.questionId}" node-type="question-favor" favor="0"><i class="kicon-like"></i>收藏</a></#if>
				<#if (question.asker?string==USER_ID &&(question.status!=2) )>
				|<a href="javascript:void(0)" node-type="question-delete" qid="${question.questionId}"><i class="kicon-trash"></i>删除</a>
				|<a href="javascript:void(0)" onclick="Question.edit(${question.questionId});return false;" node-type="question-edit"><i class="kicon-edit"></i>编辑</a> <#if question.status!=3>
				|<a href="javascript:void(0)" node-type="question-close" qid="${question.questionId}"><i class="kicon-stop"></i>关闭</a> </#if>
				</#if> </span><span><i class="kicon-tag"></i><a href="/search/question.html?is=${TYPE!}&q=${keyword!}&p=1&c=0,${question.kind},9,0"> <#if question.kind==0>其他<#else>${kindHelper.getKindLabel(question.kind?j_string)}</#if></a>
			<#assign ks=knowledgeHelper.getKnowledgeLink(question.k1,question.k2,question.k3,question.k4,question.k5)![] />
			<#assign klink=question.k1 /><#list ks?keys as k><#if (k_index>0)><#assign klink=klink+"-"+k /></#if>>
			<a href="/search/question.html?is=${TYPE!}&q=${keyword!}&p=1&c=0,${question.kind},9,${klink}"> ${ks[k]}</a></#list></span>
		</p>
	</dd>
</dl>
</#list>