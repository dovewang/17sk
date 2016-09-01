<#assign TITLE=show.title+"-"+show.tag!/>
<#assign CATEGORY="-I讲台-"/>
<#assign description=TITLE/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "../meta.ftl" />
	</head>
	<body>
		<#include "item.header.ftl" />
		<div class="page-container">
			<div class="page-body">
				<div class="page-left">
					<div class="course-detail show-detail">
						<div  class="box">
                        	<h1 id="show-title" showId="${show.showId}">${show.title}</h1>
							<#--在线课程-->
							<#if show.type==1>
							<dl>
								<dt><img src="${show.cover!}" width="300" height="225" />
								</dt>
								<dd>
									<div class="course_conTeacher">
										<span>分享人： <a href="/u/${show.userId}.html" usercard="${show.userId}"></a></span>
										<div class="clear"></div>
									</div>
									<div class="enrolmen_text"></div>
									<a href="${SITE_DOMAIN}/v/id_${show.roomId!"0"}.html"  title="观看" class="button large course-view">观看</a>
								</dd>
							</dl>
                            <div class="clear"></div>
							<#--视频课程-->
							<#elseif show.type==2>
							<object width="700" height="450">
								<param name="movie" value="/theme/base/player/jwplayer/player.swf">
								</param>
								<param name="flashvars" value="file=${show.dir!}&image=${show.cover!}">
								</param>
								<param name="allowFullScreen" value="true">
								</param>
								<param name="allowscriptaccess" value="never">
								</param>
								<param name="wmode" value="transparent">
								</param>
								<embed src="/theme/base/player/jwplayer/player.swf"  type="application/x-shockwave-flash" allowscriptaccess="never"
								allowfullscreen="true" width="700" height="450"
								flashvars="file=${show.dir!}&image=${show.cover!}" wmode="transparent"></embed>
							</object>
							<#elseif show.type==4>
							<embed src="${show.dir}" quality="high" width="700" height="450" align="middle" allowScriptAccess="always" allowFullScreen="true" wmode="transparent" type="application/x-shockwave-flash"></embed>
							</#if>
							<div class="share-bar">
								<div class="data-info">
									<span><i class="icon-views"></i>${show.views}</span>
									<span><i class="icon-comments"></i>${show.comments}</span>
									<span><i class="icon-ups"></i>${show.ups}</span>
								</div>
								<div id="ckepop" class="share">
									<span class="jiathis_txt">分享到：</span>
									<a class="jiathis_button_tsina"></a>
									<a class="jiathis_button_renren"></a>
									<a class="jiathis_button_kaixin001"></a>
									<a class="jiathis_button_taobao"></a>
									<a class="jiathis_button_tqq"></a>
									<a class="jiathis_button_qzone"></a>
									<a class="jiathis_button_googleplus"></a>
									<a class="jiathis_button_ujian"></a>
									<a class="jiathis_button_ishare"></a>
									<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">更多</a>
									<a class="jiathis_counter_style"></a>
								</div>
								<button class="button primary" onclick="Show.vote(${show.showId},1)">
									<i style="font-size:16px">♥</i>喜欢
								</button>
								<!--<button class="button primary" onclick="Show.vote(${show.showId},0)">不喜欢</button>-->
							</div>
						</div>
					</div>
					<div class="tab-header" node-type="tabnavigator">
						<ul>
							<li>
								<a href="#!/show/intro.html" remote="flase" view="introView" autoload="true">秀秀简介</a>
							</li>
							<li>
								<a href="#!/show/comments.${show.showId}.1.html" remote="true" view="commentsListView" once="true" group="true" id="commentTab">评价<#if (show.comments>0) >(<span id="cnums${show.showId}">${show.comments}</span>)</#if></a>
							</li>
						</ul>
					</div>
					<!--tab_title end-->
					<div class="tab-body" id="introView" style="padding:10px">
						${show.intro}
					</div>
					<div class="tab-body"  id="commentsListView"></div>
				</div>

				<div class="page-right" style="margin-top:5px;">
					<div node-type="show-hot" class="show-hot box"></div>
					<div class="box  online-user-box" node-type="online-teacher"></div>
					<div class="box" node-type="guess-member" data-size="3"></div>
					<div class="box" node-type="guess-course" data-size="3"></div>
				</div>
			</div>
		</div>
		</div>
		<#include "../footer.ftl"/>
	</body>
</html>
<!--
<script type="text/javascript" src="广告代码"></script>
-->
<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->