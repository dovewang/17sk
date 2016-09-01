<div style="width:700px">
   <div class="popup-header"><a href="javascript:void(0)" class="close" action-type="popup-close">×</a></div>
   	<div class="popup-body">
       <h3>您是初次光临本站吗？请在这里设置您关注和擅长的信息，方便您更快捷的获取您要的信息。如果您想长期保留您的信息，请点击这里注册。</h3>
       <form action="/guide/interest.html" id="interestForm" method="post">
       <dl class="form input-xxlarge">
           <dd><label>您关注的信息：<span class="mark">(最多10项)</span></label><input  type="hidden" readonly="readonly" name="focus" id="user-focus" />
           <span class="select-box box" style="width:500px" action-target="user-focus" max="10"><#assign course_category=enumHelper.getEnum("course_category") />
           <#list  course_category?keys as key>
             <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a>
           </#list></span></dd>
           <!--<dd><label>猜您所在的地区：</label><span><input type="text"/></span></dd>-->
         <dd><label>您所在的年级：</label><input type="hidden"  id="user-grade" name="grade" value=""/> <#assign grade=kindHelper.getGrades()/><span class="select-box box" action-target="user-grade" max="1" style="width:500px">
           <#list  grade?keys as key>
             <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${grade[key]}</a>
           </#list></span></dd>
		<dd>
           <dd><label>您擅长的领域：<span class="mark">(最多3项)</span></label><input type="hidden" readonly="readonly" id="user-goodat" name="expert"/>
           <span class="select-box box" action-target="user-goodat" max="3" style="width:500px">
           <#list  course_category?keys as key>
             <a href="javascript:void(0)" action-type="select-item"  action-data="${key}">${course_category[key]}</a>
           </#list></span></dd>
       </dl>
       </form>
    </div>
    <div class="popup-footer"><button action-type="popup-close"  class="button" onclick="Guide.firstCancle();">取消</button>
		<button action-type="popup-close" class="button primary" onclick="Guide.first();">确定</button></div>
</div>