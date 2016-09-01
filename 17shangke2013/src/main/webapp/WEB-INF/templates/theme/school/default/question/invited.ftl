<!DOCTYPE html >
<html>
<head>
<#include "../head.ftl" />
		<#assign keyword=RequestParameters["q"]! />
		<#assign searchType=searchType!"error" />
		</head>
		<body>
<#include "../top.ftl" />
<div class="container">
          <div class="row-fluid">
    <div class="span9"> <#if result.totalCount==0>
              <div class="placeholder-box">暂时没有人邀请您回答问题</div>
              <#else>
              ﻿   <#include "list.ftl" />
              </#if> </div>
    <div class="span3">
              <div class="side-section">
        <div class="side-section-inner">
                  <ul>
            <li> <a  href="/question/draft"> <i></i> 我的草稿 <span class="zg-num">3</span> </a> </li>
            <li> <a  href="/question/draft"> <i></i> 我发布的问题 </a> </li>
            <li> <a  href="/user/fav.html"> <i></i> 我的收藏 </a> </li>
            <li> <a  href="/question/watching.html"><i></i> 我关注的问题 </a> </li>
            <li> <a  href="/question/invited.html"><i></i>邀请我回答的问题 </a> </li>
          </ul>
                </div>
      </div>
              <div class="side-section">
        <div class="side-section-inner">
                  <ul>
            <li> <a  href="/search/question.html"> <i></i> 所有问题 </a> </li>
            <li> <a href="/topics.html"><i></i> 话题广场 </a> </li>
          </ul>
                </div>
      </div>
            </div>
  </div>
        </div>
<footer>
          <div id="page-footer"></div>
        </footer>
<#include "../foot.ftl"/>
</body>
</html>
