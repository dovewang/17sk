<#if edit?exists><#assign TITLE="编辑课程-"/>
<#else><#assign TITLE="创建课程-"/></#if>
<#assign CATEGORY="发布课程-"/>
<!DOCTYPE html>
<html>
<head>
<#include "../../head.ftl" />
</head>
<body>
<#include "../../top.ftl" />
<div class="container">
  <div class="row-fluid">
    <div class="span3">
      <ul>
        <li><span>1.</span>选择课程类型</li>
        <li><span>2.</span>上传视频文件</li>
        <li><span>3.</span>填写基本信息</li>
        <li><span>4.</span>上传相关课程资料</li>
        <li><span>5.</span>预览发布</li>
        <li><span>6.</span>发送邀请</li>
      </ul>
    </div>
    <div class="span9">
       <div node-type="slider">
       
       
       </div>    
     <#if __USER.hasRole("ADMIN") >
      <#--学校用户特有的-->
      <h2>线下课程</h2>
      <div class="box" style="position:relative;"> <img src="/theme/main/default/images/course_g4.jpg" />
       <a class="btn btn-primary"  href="/course/manager/3/create.html" style="position:absolute;right:50px; top:80px;">创建线下课程</a>
        <div class="text-left"> 1、创建在线课程是为了方便您指定自己的课程计划，同时可以邀请您的学生或好友来参加您的课程以获得收益。 <br/>
          2、课程创建的第一步信息必须填写完整，方便您的管理和学员的查找，文档资料上传和练习部分可以根据自己的需要填写，也可以直接跳过，快速发布。 <br/>
          3、 关于课程发布协议，<a href="javascript:void(0)" onclick="$('#course_agreement').toggle()">点击查看</a>。
          <#include "/agreement/course.ftl"/> </div>
      </div>
      </#if>
      <h2>上传或转发视频课件</h2>
      <div class="box" style="position:relative;"> 
        <!--html4上传--> 
        <img src="/theme/main/default/images/course_g1.jpg"  />
        <a class="btn btn-primary"  href="/course/manager/4/create.html" style="position:absolute;right:180px; top:120px;">转发视频课程</a>
        <div id="video_upload"  style="position:absolute;right:20px; top:120px;">
          <form id="videoUploadForm"  action="/upload/video.html" method="post" enctype="multipart/form-data" >
            <input name="progress" value="true" type="hidden"/>
            <input name="flag" value="video" type="hidden"/>
            <a class="btn btn-primary" node-type="upload-button"  name="file"  onchange="Course.uploadVideo(this);"> 请选择视频文件 </a>
          </form>
        </div>
        <div id="uploadProgress" class="progress progress-info progress-striped active hide" >
          <div class="bar" id="uploadProgress_bar"></div>
        </div>
        <div class="text-left"> 1、为了提高您上传文件的的效率，强烈建议您先将文件格式转换flv,f4v,mp4； <br/>
          2、如果您不知道如何转换视频<a href="#">，请点这里</a>。 <br/>
          3、 关于视频上传协议，<a href="javascript:void(0)" onclick="$('#video_agreement').toggle()">点击查看</a>。
          <#include "/agreement/video.ftl"/> </div>
      </div>
      <h2>在线课程</h2>
      <div class="box" style="position:relative;"> <img src="/theme/main/default/images/course_g3.jpg"/><a class="btn btn-primary"  href="/course/manager/1/create.html" style="position:absolute;right:50px; top:80px;">创建在线课程</a>
        <div class="text-left"> 1、创建在线课程是为了方便您指定自己的课程计划，同时可以邀请您的学生或好友来参加您的课程以获得收益。 <br/>
          2、课程创建的第一步信息必须填写完整，方便您的管理和学员的查找，文档资料上传和练习部分可以根据自己的需要填写，也可以直接跳过，快速发布。 <br/>
          3、 关于课程发布协议，<a href="javascript:void(0)" onclick="$('#course_agreement').toggle()">点击查看</a>。
          <#include "/agreement/course.ftl"/> </div>
      </div>
    </div>
  </div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../../foot.ftl"/>
</body>
</html>
