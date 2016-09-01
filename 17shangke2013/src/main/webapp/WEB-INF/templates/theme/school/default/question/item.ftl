<!DOCTYPE html>
<html>
	<head>
		<#include "../head.ftl" />
	</head>
	<body>
		<#include "../top.ftl" />
		<div class="container">
			<div class="row-fluid">
				<div class="span9">
					<div class="question-detail" id="qitem${question.questionId}" qid="${question.questionId}" qasker="${question.asker}" node-type="question-item"  item="true">
						<div class="question-header">
							<div class="question-tag">
								<span class="question-status"><i class="status${question.status}"></i>&nbsp;
									${enumHelper.getLabel("question_status",question.status?j_string)} </span><span class="question-status"><!--<a href="javascript:void(0)" id="fullscreen" full="false"  qid="${question.questionId}" onclick="Question.fullscreen(this)">全屏阅读</a>-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="/search/question.html?c=0,${question.kind},9,0" class="tag"><#if question.kind==0>其他<#else>
								${kindHelper.getKindLabel(question.kind?j_string)} </#if></a> <#assign ks=knowledgeHelper.getKnowledgeLink(question.k1,question.k2,question.k3,question.k4,question.k5)![] />
								<#assign klink=question.k1 />
								<#list ks?keys as k>
								<#if (k_index>0)>
								<#assign klink=klink+"-"+k />
								</#if>><a href="/search/question.html?c=0,${question.kind},9,${klink}" class="tag"> ${ks[k]} </a></#list>
							</div>
							<h1  id='questiontitle_${question.questionId}'>${question.title}<a href="javascript:;"><i class="icon-pencil"></i>修改</a></h1>
						</div>
						<div class="question-body">${question.intro!question.title}<a href="javascript:;"><i class="icon-pencil"></i>修改</a><#if (question.asker?string!=USER_ID)&&(question.status!=2&&question.status!=3) > <span class="question-list-answer-button"><#if (question.price>0)><span class="question-price"> ${question.price}
									点</span></#if></span></#if>
						</div>
						<div class="clear"></div>
						<div class="question-footer">
							<span class="link-group float-right"> <#if USER_ID==question.asker?string > <a href="javascript:void(0)" onclick="Question.del(this,${question.questionId},true)"><i class="icon icon-trash"></i>删除</a> |<a href="javascript:void(0)" onclick="Question.edit(${question.questionId})"><i class="icon icon-edit"></i>编辑</a> | </#if><a  href="javascript:;" ><i class="icon icon-ribbon"></i>答案<#if (question.answers>0) >(<em> ${question.answers} </em>)</#if></a> | <a  href="javascript:;" ><i class="icon icon-view"></i>浏览<#if (question.views>0) >(<i> ${question.views} </i>)</#if></a> |<a  href="javascript:void(0)" node-type="question-favor" favor="0"><i class="icon icon-like"></i>收藏</a> </span><span> 提问人：<a href="/u/${question.asker}.html" target="_blank" usercard="${question.asker}"></a> </span><span class="date-mark"> ${flint.timeToDate(question.createTime,"yyyy-MM-dd HH:mm:ss")} </span>
						</div>
					</div>
					<div class="clear"></div>
					<#if !isLogin>
					<#--未登陆显示 -->
					<div class="answer nolgin">
						<strong>只有注册用户才能查看答案哦~</strong>
						<p>
							>>现在注册一起上课即可获得20个
							${MONEY_UNIT}
							，快来注册吧！
						</p>
						<a class="button register" href="${SECURE_DOMAIN}/register.html"></a><a class="button login" href="${SECURE_DOMAIN}/login.html?url=${SITE_DOMAIN}/qitem-${question.questionId}.html"></a>
					</div>
					<#else>
					<div class="answer">
						<#if ((answer?size)>0) >
						<div class="answer-header">
							<strong>${question.answers}个回答</strong><a href="javascript:;" qid="${question.questionId}" class="active" order="0" node-type="question-order">默认按时间排序</a><a href="javascript:;" qid="${question.questionId}" order="1" node-type="question-order">按采纳人数排序</a><a href="javascript:;" qid="${question.questionId}" order="2" node-type="question-order">按好评排序</a>
						</div>
						<div id="question-answer-list" class="answer-list">
							<#list answer as a> <a name='depict_${a.answerId}'></a>
							<dl class="answer-item" id="answer-item-${a.answerId}" node-type="answer-item" answerid="${a.answerId}" answer="${a.answer}" questionid="${question.questionId}">
								<dt class="face">
									<a href="/u/${a.answer}.html" target="_blank"><img usercard="${a.answer}" src="/theme/school/default/images/noface_s.jpg" width="40" height="40" alt=""/></a>
								</dt>
								<dd class="content">
									<p class="answer-top">
										<span class="answer-info gray"> <a href="/u/${a.answer}.html" target="_blank" usercard="${a.answer}"> ${a.answer} </a> ${flint.timeToDate(a.answerTime,"yyyy-MM-dd HH:mm")} </span><span class="favor"> <a>非常有用(<i id="cnums1${a.answerId}"> ${a.mark4} </i>)</a>| <a>有用(<i id="cnums2${a.answerId}"> ${a.mark3} </i>)</a>| <a>一般(<i id="cnums3${a.answerId}"> ${a.mark2} </i>)</a>| <a>作用不大(<i id="cnums4${a.answerId}"> ${a.mark1} </i>)</a> <#if (a.memo=="-1"&&a.price!=0)&&a.status!=0> <strong><a href="javascript:;" id="answer_show_${a.answerId}" style="display:none;" action-type="answer-show-comment">评论</a></strong> <#elseif a.status!=9&&a.status!=0> <strong><a href="javascript:;" id="answer_show_${a.answerId}" action-type="answer-show-comment">评论</a></strong> </#if> </span><strong>采纳人数(<i id="cnums5${a.answerId}"> ${a.adoptions} </i>)</strong>
									</p>
									<#if a.status==0>
									<p class="noview" id="noview${a.answerId}">
										该答案正在审核中
									</p>
									<#elseif a.status==9&&a.memo!="1">
									<p class="noview" id="noview${a.answerId}">
										该答案已被删除
									</p>
									<#else>
									<#--本人-->
									<#if a.status==9&&a.memo=="1">
									<p class="noview" id="noview${a.answerId}">
										该答案已被删除
									</p>
									</#if>
									<#if (a.answer?string=USER_ID)>
									<div class="viewed">
										<p>
											<#if a.type==1> <a href="/v/id_${a.roomId}.html" target="_blank"><img src="/theme/school/default/images/videopic.jpg" width="120" height="90" alt="视频名称"/></a> <#else>
											${a.content}
											</#if>
										</p>
									</div>
									<div class="link-group float-right">
										<#if a.type!=1> <a href="javascript:;" action-type="answer-edit"><i class="icon icon-edit"></i>编辑</a> | </#if> <a href="javascript:;" action-type="answer-delete"><i class="icon icon-trash"></i>删除</a>
									</div>
									<div class="clearfix"></div>
									<#elseif (a.memo=="-1"&&a.price!=0)>
									<#--未查看过的状态-->
									<p class="noview" id="noview${a.answerId}">
										<#if a.type==1>
										此答案为在线解答
										<#else>
										此答案为快速解答
										</#if> <span class="question-price"> ${a.price}
											点</span>
										<button class="btn" onclick="Question.viewAnswer(${a.answerId},${a.price})">
											查看答案
										</button>
										<div id="auth-box${a.answerId}"></div>
									</p>
									<#else>
									<#-- 查看过的状态-->
									<#if a.price==0>
									<div class="nofee">
										免费答案
									</div>
									</#if>
									<div class="viewed">
										<#if a.type==1> <a href="/v/id_${a.roomId}.html" target="_blank"><img src="/theme/school/default/images/videopic.jpg" width="120" height="90" alt="视频名称"/></a> <#else>
										${a.content}
										</#if>
										<#if a.memo=="1">
										<button class="btn" >
											已采纳
										</button>
										<#else>
										<button class="btn btn-primary" <#if a.price==0>onclick="Question.selectAnswer(this,${question.questionId},${a.answerId},0)">
											<#else>onclick="Question.selectAnswer(this,${question.questionId},${a.answerId},1)"></#if>
											采纳为答案
										</button></#if>
									</div>
									</#if>
									</#if>
									<div class="comments box" id="answer_view_${a.answerId}" style="display:none;"></div>
								</dd>
							</dl>
							</#list>
						</div>
						<#else>
						暂时无人解答
						</#if>
					</div>
					</#if>
					<div class="faster-answer">
						<div class="text-right">
							<span class="gray-light">您可以使用语音，绘画，图形等方式，更加详尽友好的讲解。</span><a  class="btn btn-success"  href="${SITE_DOMAIN}/abc/form.html?i=${question.questionId}&t=1&s=${question.title?url("utf-8")}&p=${question.price}"  node-type="pop" setting="{id:'create-room-pop',effect:'mac',css:{width:500}}" title="在线解答" id="createRoom-pop">使用在线教室解答</a>
						</div>
						<div>
							<textarea name="intro" style=" height: 120px;" id="fastanswer_${question.questionId}" placeholder="输入详细内容" node-type="editor"></textarea>
						</div>
						<div class="text-right">
							<button class="btn btn-primary" onclick="Question.fastAnswer(${question.questionId});">
								发布回答
							</button>
						</div>
					</div>
				</div>
				<div class="span3">
					<div class="side-section">
						<div class="side-section-inner">
						<button class="btn btn-success">
							&nbsp;&nbsp;&nbsp;关注&nbsp;&nbsp;&nbsp;
						</button>
						<div>
							<a href="#">1</a>关注该问题
						</div>
						<!--关注人的列表-->
						<div class="list">
							<#list watchers as w>
							<a  href="#"> <img src="http://p1.zhimg.com/f5/c4/f5c4d8c99_s.jpg" > </a>
							</#list>
						</div>
						</div>
					</div>
					<div class="side-section">
						<div class="side-section-inner">
						<h3>相关问题</h3>
						<ul class="question-related">
							<#list relates as r>
							<li>
								<a   href="/qitem-${r.questionId}.html">${r.title}</a><span class="gray-light">${r.answers}个回答</span>
							</li>
							</#list>
						</ul>
						</div>
					</div>
					<div class="side-section">
						<div class="side-section-inner">
						<h3>分享问题</h3>
						</div>
					</div>
				</div>
				<footer>
					<div id="page-footer"></div>
				</footer>
				<#include "../foot.ftl"/>
	</body>
</html>