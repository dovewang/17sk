<form action="/user/address.save.html" method="post" onsubmit="return false" id="userAddressForm" data-validator-url="/v/user.address.json">
  <input  type="hidden" value="0" name="id">
  <dl class="form label80 add-user-address">
    <dd>
      <label><em class="required">*</em>您的姓名：</label>
      <span>
      <input type="text" id="order_username" name="name"/> <span class="gray1">请准确填写您的姓名，以便与学校确认</span>
      </span> </dd>
    <dd>
      <label><em class="required">*</em>联系电话：</label>
      <span>
      <input type="text" name="phone" id="order_phone"/>
      <span class="gray1">请准确填写您的联系电话，以便与学校确认</span> </span> </dd>
      <dd>
      <label>邮箱地址：</label>
      <span>
      <input type="text" id="order_email" name="email"/>
      <span class="gray1">您可以填写改信息以获取最新的课程信息</span>
      </span> </dd>
      <dd>
      <label>通信地址：</label>
      <span>
       <i  node-type="city-selector" node-name="area"></i> <input type="text" id="order_username" name="address"/>
       <span class="gray1">如果学校发布期刊，您可以通过该地址免费获取期刊</span>
      </span> </dd>
      <dd><label>&nbsp;</label><span><button class="button primary" type="button" node-type="save-user-address">确定</button></span></dd>
  </dl>
</form>