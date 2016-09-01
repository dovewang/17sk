<!DOCTYPE html>
<html>
<head>
<#include "../../head.ftl" />
</head>
<body>
<#include "../../top.ftl"/>
<div class="container">
  <div class="row-fluid">
    <div class="span3"><#include "../left.ftl"/></div>
    <div class="span9" id="main">
      <form id="schoolForm" method="post" action="/admin/school.save.html" enctype="multipart/form-data" data-validator-url="/v/school.system.json">
        <input type="hidden" name="schoolId" value="${school.schoolId}">
        <input name="status" type="hidden"  value="1" />
        <input name="createTime" type="hidden"  value="${school.createTime!}" />
        <input name="certDir" type="hidden" value="${school.certDir!}"/>
        <input name="logo" type="hidden" value="${school.logo!}"/>
        <dl class="form label120 input-260">
          <dd>
            <label>学校名称：</label>
            <span>
            <input name="name" type="text" id="name" old-data="${school.name}" value="${school.name}" placeholder="请您输入学校名称" readonly/>
            </span> </dd>
          <dd>
            <label>学校简介：</label>
            <span>
            <textarea id="intro" name="intro" node-type="editor"  width="720" height="300">${school.intro!}
</textarea>
            </span> </dd>
          <dd>
            <label>本校主站网站：</label>
            <span>
            <div class="input-prepend"> <i class="add-on">http://</i>
              <input name="homepage" type="text" id="homepage" value="${school.homepage!}"  placeholder="请输入您学校的官方网站" style="width:208px"  maxlength="150"/>
            </div>
            </span> </dd>
          <dd>
            <label>网站LOGO：</label>
            <span>
            <input type="file" name="logoFile" id="logoFile" readOnly="true"/>
            <div><img  width="170" height="48" src="${school.logo!}"/> </div>
            </span> </dd>
          <dd>
            <label>本站地址：</label>
            <span>
            <div class="input-prepend input-append"> <i class="add-on">http://</i>
              <input name="domain" type="text" id="domain" value="${school.domain!}" style="width:100px" readonly/>
              <i class="add-on">.17shangke.com</i> </div>
            </span> </dd>
          <dd>
            <label>网站模板：</label>
            <span>
            <input type="hidden" name="themeId" value="2" />
            <input value="默认风格" disabled="disabled" type="text"/>
            </span> </dd>
          <dd>
            <label>组织机构代码证：</label>
            <span>
            <input name="cert" placeholder="请输入您学校的组织机构代码证编号" type="text" id="cert" value="${school.cert!}" readonly/>
            </span> </dd>
          <dd>
            <label>联系人：</label>
            <span>
            <input name="contact" type="text" id="contact" value="${school.contact!}"  placeholder="请输入联系人" maxlength="30"/>
            </span> </dd>
          <dd>
            <label>联系电话：</label>
            <span>
            <input name="phone" type="text" id="phone" value="${school.phone!}"  placeholder="请输入联系人的电话" maxlength="30"/>
            </span> </dd>
          <dd>
            <label>联系地址：</label>
            <span>
            <input name="address" type="text" id="address" value="${school.address!}"  placeholder="请输入联系的地址" maxlength="100"/>
            </span> </dd>
          <dd>
            <label>联系人邮箱：</label>
            <span>
            <input name="email" type="text" id="email" value="${school.email!}"  placeholder="管理员登录账户" readonly maxlength="30"/>
            </span> </dd>
          <dd>
            <label>传真：</label>
            <span>
            <input name="fax" type="text" id="fax" value="${school.fax!}" maxlength="50"/>
            </span> </dd>
          <dd>
            <label>&nbsp;</label>
            <span>
            <button type="button" class="btn btn-primary" node-type="form-submit" success="location.reload();" form-id="schoolForm"> 保存 </button>
            </span> </dd>
        </dl>
      </form>
    </div>
  </div>
</div>
</div>
<footer>
  <div id="page-footer"></div>
</footer>
<#include "../../../global.footer.ftl"/>
</body>
</html>