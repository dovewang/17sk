<style>
.comment {
}
.comment-body {
	border: 1px solid #cccccc;
	border-bottom: none;
}
.comment-body .comment-content {
	width: 100%;
	padding: 0;
	overflow: hidden;
	border: none;
}
.comment-footer {
	position: relative;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.6);
}
.comment-footer .comment-options {
	position: relative;
	margin-right: 100px;
	height: 30px;
	border: 1px solid #cccccc;
	border-right: none;
	border-bottom-color: #bbb;
	border-bottom-left-radius: 3px;
	-webkit-border-bottom-left-radius: 3px;
	-moz-border-bottom-left-radius: 3px;
	background: url(images/bg_sprites.png) 0 -60px repeat-x;
}
.comment-footer .comment-options .comment-sync {
	font-size: 12px;
	color: #999999;
	line-height: 30px;
	position: absolute;
	right: 5px;
}
.comment-footer .comment-options .comment-sync input {
	vertical-align: middle;
	width: auto;
}
.comment-footer .btn {
	font-family: 'Helvetica Neue', Arial, Helvetica, sans-serif;
	position: absolute;
	right: 0;
	top: 0;
	height: 32px;
	width: 100px;
	text-align: center;
	text-shadow: 0 1px 0 #fff;
	color: #555555;
	font-size: 14px;
	font-weight: bold;
	border: 1px solid #cccccc;
	border-bottom-color: #bbbbbb;
	border-bottom-right-radius: 3px;
	-webkit-border-bottom-right-radius: 3px;
	-moz-border-bottom-right-radius: 3px;
	background-color: #e6e6e6;
	background-repeat: no-repeat;
	background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#fcfcfc), color-stop(25%, #fcfcfc), to(#e6e6e6));
	background-image: -webkit-linear-gradient(#fcfcfc, #fcfcfc 25%, #e6e6e6);
	background-image: -moz-linear-gradient(top, #fcfcfc, #fcfcfc 25%, #e6e6e6);
	background-image: -ms-linear-gradient(#fcfcfc, #fcfcfc 25%, #e6e6e6);
	background-image: linear-gradient(#fcfcfc, #fcfcfc 25%, #e6e6e6);
	transition: all 0.15s linear;
	-webkit-transition: all 0.15s linear;
	-moz-transition: all 0.15s linear;
	box-shadow: inset 0 0 1px #fff;
}
.service-icon {
	vertical-align: middle;
	width: 16px;
	height: 16px;
	line-height: 100px;
	display: inline-block;
	background: url(images/connect_ico.png) no-repeat;
	overflow: hidden;
	opacity: .8;
}
.service-icon.active {
	opacity: 1;
}
.service-icon.weibo {
	background-position: 0 -16px;
}
.service-icon.weibo.active {
	background-position: 0 0;
}
.service-icon.sohu {
	background-position: 0 -48px;
}
.service-icon.sohu.active {
	background-position: 0 -32px;
}
.service-icon.renren {
	background-position: 0 -80px;
}
.service-icon.renren.active {
	background-position: 0 -64px;
}
.service-icon.qq {
	background-position: 0 -432px;
}
.service-icon.qq.active {
	background-position: 0 -416px;
}
.service-icon.qzone {
	background-position: 0 -272px;
}
.service-icon.qzone.active {
	background-position: 0 -256px;
}
.service-icon.kaixin {
	background-position: 0 -176px;
}
.service-icon.kaixin.active {
	background-position: 0 -160px;
}
.service-icon.douban {
	background-position: 0 -208px;
}
.service-icon.douban.active {
	background-position: 0 -192px;
	opacity: 1;
}
.service-icon.qqt {
	background-position: 0 -144px;
}
.service-icon.qqt.active {
	background-position: 0 -128px;
}
.service-icon.netease {
	background-position: 0 -112px;
}
.service-icon.netease.active {
	background-position: 0 -96px;
}
.service-icon.baidu {
	background-position: 0 -464px;
}
.service-icon.baidu.active {
	background-position: 0 -448px;
}
.service-icon.taobao {
	background-position: 0 -496px;
}
.service-icon.taobao.active {
	background-position: 0 -480px;
}
.service-icon.msn {
	background-position: 0 -240px;
}
.service-icon.msn.active {
	background-position: 0 -224px;
}
.service-icon.google {
	background-position: 0 -528px;
}
.service-icon.google.-active {
	background-position: 0 -512px;
}
</style>
<#if allowComment>
<div  class="comment">
  <form id="commentForm" action="/course/comment.html">
    <input type="hidden" name="courseId" value="${course.courseId}"/>
    <div class="comment-header">
      <div>您觉得这个课程的有用吗？ <input type="raty" name="courseScore" value="3" number="5" data-scoretip='[{"score":[0,1],"text":"作用不大"},{"score":[2,3],"text":"一般"},{"score":[4],"text":"有用"},{"score":[5],"text":"非常有用"}]'>
</div>
      <!--<div>写个印象标签吧：</div>-->
    </div>
    <div class="comment-body">
      <textarea class="comment-content" name="content" ></textarea>
    </div>
    <div class="comment-footer">
      <div class="comment-options">
        <div class="comment-toolbar"></div>
        <!--
        <div class="comment-sync">
          <label>分享到
            <input type="checkbox">
          </label>
          <a href="javascript:void(0)" class="service-icon qq" data-service="qqt" title="腾讯微博"></a> <a href="javascript:void(0)" class="service-icon weibo  active" data-service="qqt" title="腾讯微博"></a> <a href="javascript:void(0)" class="service-icon google  active" data-service="qqt" title="腾讯微博"></a>
          </div>-->
       </div>
      <button class="btn" type="button"  onclick="Course.postComment()">评论</button>
    </div>
  </form>
</div>
</#if>