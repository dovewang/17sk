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
    <div class="span9"><#include "course.search.ftl"/></div>
    <div class="span3">
              <div class="side-section">
        <div class="side-section-inner">
                  <ul>
            <li> <a href="/course/manager/guide.html" class="btn btn-success">&nbsp;&nbsp;&nbsp;发布新课程&nbsp;&nbsp;&nbsp;</a></li>
            <li> <a  href="/course/draft.html"> <i class="icon-leaf"></i>发布的课程 </a> </li>
            <li> <a  href="/course/joined.html"><i class="icon-list-alt"></i> 参加的课程 </a> </li>
            <li> <a  href="/user/fav.html"><i class="icon-star"></i> 收藏的课程 </a></li>
            <li> <a  href="/question/liked"><i class="icon-heart"></i> 喜欢的课程 </a> </li>
          </ul>
                </div>
      </div>
              <div class="side-section">
        <div class="side-section-inner">
                  <ul>
            <li> <a  href="/log/questions"><i ></i>所有课程 </a> </li>
            <li> <a href="/topics"> <i></i> 话题广场 </a> </li>
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
