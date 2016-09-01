<div class="popup-header ">
	<a href="javascript:void(0)" class="close" action-type="popup-close">×</a>
</div>
<div class="popup-body">
	<div  class="user-list-box clearfix">
		<ul  id="user-list-box-selected"></ul>
	</div>
	<div class="tab-simple" node-type="tabnavigator" hash="false">
		<ul>
			<li>
				<a  href="#!/user/select.list.html" remote="true" view="user-list-box" >辅导老师</a>
			</li>
			<li>
				|
			</li>
			<li>
				<a  href="#!/user/select.list.html" remote="true" view="user-list-box" >我关注的</a>
			</li>
			<li>
				|
			</li>
			<li>
				<a  href="#!/user/select.list.html" remote="true" view="user-list-box" >我的粉丝</a>
			</li>
		</ul>
	</div>
	<div id="user-list-box"  class="user-list-box">
		<ul>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="2">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="3">
				<img  height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="4">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="5">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="6">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li uid="1">
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
			<li >
				<img height="50" usercard="4" width="50" src="/theme/main/default/images/noface_s.jpg" alt="必须的">
				<label>
					<input type="checkbox">
					<a href="#">必须的</a></label>
			</li>
		</ul>
		<script>
			$(function() {
				var $uselect = $("#user-list-box-selected");
				$("#user-list-box ul li").each(function() {
					var $this = $(this);
					$this.find("input").change(function() {
						if(this.checked) {
							$uselect.append($this.clone(true))
						} else {
							$uselect.find("li[uid='" + $this.attr("uid") + "']").remove()
						}
					})
				})
			})
		</script>
	</div>
</div>