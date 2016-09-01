/*学校部分*/
School = {}
School.loadUsers = function(div, type, nums) {
	$.get("/getUsers.html?t=" + type + "&n=" + nums, function(result) {
		var result = eval("(" + result.result + ")");
		var html = []
		html.push('<ul>');
		$(result).each(function(index, data) {
			html.push('	<li><a href="/u/' + data.id + '.html" target="_blank"><img usercard="' + data.id + '" src="' +  data.face + '" width="40" height="40" alt="' + data.name + '"/></a>');
			html.push('	<div>');
			html.push('	<a href="/u/' + data.id + '.html" target="_blank" usercard="' + data.id + '">' + data.name + '</a>');
			html.push('	</div></li>');
		});
		html.push('</ul><div class="clear"></div>');
		$div = $(div);
		$div.html(html.join("")).reload();
	});
}


/*保存班级信息*/
School.saveClass = function(btn) {
	$("#classForm").postForm(function(data) {
		$.alert.show(data,function(){
			if (data.status > 0) {
				$(btn).trigger("pop-close");
				//location.reload();			
			}
		});
	})
}
/*删除班级信息*/
School.deleteClass = function(classId,obj) {
	$.post("/admin/class/delete.html",{id : classId}, function(data) {
		$.alert.show(data,function(){
			$("#classItem" + classId).remove();
		},{effect : "mouse",trigger:$(obj)});
	});
};
/**
 * 检查用户是否存在
 */
School.checkUser = function(email) {
	$.post("/admin/user/email.html", {
		email : email
	}, function(data) {
		if(data.global || data.school) {
			Alert.confirm("您输入的邮箱用户信息已存在，是否自动填充？", function() {
				$("#exists").val(1);
				/*自动填充用户信息*/
				$.post("/admin/user/json.html", {
					email : email
				}, function(data) {
					data = data.result;
					data.userType = undefined;
					$("#userForm").bindForm(data);
				})
			});
		}
	});
}

/*保存用户信息*/
School.saveUser = function(btn) {
	$("#userForm").postForm(function(data) {
		$.alert.show(data,function() {
			if (data.status > 0) {
				$(btn).trigger("pop-close");
				location.reload();	
			}
		});
	})
}

/*锁定用户*/
School.lockUser = function(obj, userId) {
	var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要锁定吗？", [function() {
		$.post("/admin/user/lock.html?id=" + userId,function(data) {
			if (data.status > 0) {
				$.alert.success(data.message,options);
				$(obj).attr("onclick", "School.unlockUser(this," + userId + ")").text("解锁账号").attr("style","color:red");
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
}

/*解锁用户*/
School.unlockUser = function(obj, userId) {
	var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要解锁吗？", [function() {
		$.post("/admin/user/unlock.html?id=" + userId, function(data) {
			if (data.status > 0) {
				$.alert.success(data.message,options);
				$(obj).attr("onclick", "School.lockUser(this," + userId + ")").text("锁定账号").removeAttr("style");
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
}

/*删除用户*/
School.deleteUser = function(obj, userId, classid) {
	var options={effect:"mouse",trigger:$(obj)};
	$.alert.confirm("确定要删除吗？", [function() {
		$.post("/admin/user/delete.html?id=" + userId +"&classid="+classid,function(data) {
			if (data.status > 0) {
				$.alert.success(data.message,options);
				$("#userItem" + userId).slideUp("slow");
			} else {
				$.alert.show(data,options);
			}
		});
	}],options);
}

School.getRole = function(uid, classid, usertype){
	if(usertype == 1){//学生
		$("#setrole_"+uid).popover(
			{content:'<a href="javascript:;" onclick="School.setRole('+uid+','+classid+',0);">成为<font style="color:red">普通成员</font></a><br/><a href="javascript:;" onclick="School.setRole('+uid+','+classid+',2);">成为<font style="color:red">班长</font></a><br/><a href="javascript:;" onclick="School.setRole('+uid+','+classid+',3);">成为<font style="color:red">副班长</font></a>',
			theme:"top",
			afterShow:function($pop,options){
				$pop.mouseleave(function() {
					Pop.hide($pop);
				});
			}
		})
	}else{
		$("#setrole_"+uid).popover(
			{
			content:'<a href="javascript:;" onclick="School.setRole('+uid+','+classid+',0);">成为<font style="color:red">普通成员</font></a><br/><a href="javascript:;" onclick="School.setRole('+uid+','+classid+',1);">成为<font style="color:red">班主任</font></a><br/>',
			theme:"top",
			afterShow:function($pop,options){
				$pop.mouseleave(function() {
					Pop.hide($pop);
				});
			}
		})
	}
}

School.setRole = function(uid, classid, role){
	$.alert.confirm("您确定要进行操作吗？", [
		function() {
			$.post("/admin/user/setrole.html", {
				userid : uid,
				classid : classid,
				role : role
			}, function(data) {
				$.alert.show(data, function() {
					if (data.status > 0) {
						location.reload();
					}
				});
			});
		}
	]);
}

School.timetable=function(){
	var timetable=$('<div class="timetable edit"><div><h2>作息时间表<div class="pull-right"><a href="javascript:;" class="btn" data-type="edit">编辑</a><a href="javascript:;" class="btn  btn-primary" " data-type="save">保存</a></div></h2></div></div>');
        var table=$('<table class="table table-bordered" style="width:100%" cellspacing="0"><thead><tr><th>名称</th><th>时间</th><th>上课时间</th><th>操作</th></tr></thead><tbody></tbody></table>');
		timetable.append(table);
		$("#main").empty().append(timetable);
		$.get("/school/timetable.html",function(data){
			if(!data.list){
				data.list=[{"label": "早自习(根据您的要求进行修改)","time": "8:00-9:00"},{"label": "早餐","rest": true,"time": "8:00-9:00"},{"label": "第一节","time": "8:00-9:00"},{"label": "第二节","time": "8:00-9:00"},{"label": "第三节","time": "8:00-9:00"},{"label": "第四节","time": "8:00-9:00"},{"label": "午休","time": "8:00-9:00","rest":true},{"label": "第五节","time": "8:00-9:00"},{"label": "第六节","time": "8:00-9:00"},{"label": "第七节","time": "8:00-9:00"},{"label": "晚餐","time": "8:00-9:00","rest":true},{"label": "晚自习","time": "8:00-9:00"}];
			    timetable.attr("new","true");
			}
			$(data.list).each(function(index, element) {
                var tr=$('<tr/>');
				 tr.append('<td><input type="text" name="label" value="'+this.label+'" disabled="disabled"/></td>');
				 tr.append('<td><input type="text" name="time" value="'+this.time+'" disabled="disabled"/></td>');
				 tr.append('<td><input type="checkbox" name="rest" '+(this.rest?"":"checked")+' disabled="disabled"></td>');
				 tr.append('<td><div class="btn-group"><a  href="javascript:;" " data-type="add" class="btn"><i class="icon-plus"></i></a><a href="javascript:;" " data-type="up" class="btn"><i class="icon-arrow-up"></i></a><a href="javascript:;" " data-type="down" class="btn"><i class="icon-arrow-down"></i></a><a href="javascript:;" " data-type="delete" class="btn"><i class=" icon-trash"></i></a></div></td>');
				 table.append(tr);
            });
			
			timetable.find("a[data-type]").each(function(index, element) {
                bindEvent(this);
            });
			
		});
		
		function bindEvent(element){
			var type=$(element).data("type");
				switch(type){
					case 'edit':
					     $(element).click(function(){
					       table.find("input").removeAttr("disabled");
						 });
					     break;
					case 'save':
					     //{"label": "早自习","time": "8:00-9:00","rest": true}
						 $(element).click(function(){
						 var _timetable=[];
						 table.find("tbody tr").each(function(){
							 var o=[];
							 $(this).find("input").each(function(){
								 var name=$(this).attr("name");
								 if(name=="rest"){
									 if(!$(this).attr("checked")){
										   o.push('"rest":true');
									 }
								 }else{
								  o.push('"'+name+'":"'+$(this).val()+'"');
								 }
							 })
							 _timetable.push("{"+o.join(",")+"}")
						 });
					       $.post("/school/timetable.save.html",{isnew:timetable.attr("new")=="true",timetable:encodeURIComponent("["+_timetable.join(",")+"]")},function(result){
							   $.alert.show(result)
						   });						 
						 });
					     break						 
				    case 'add':
						$(element).click(function(){ 
					       var ctr=$(this).closest("tr"); 
						   var tr=$('<tr/>');
							   tr.append('<td><input type="text" name="label" value=""/></td>');
				               tr.append('<td><input type="text" name="time" value=""/></td>');
				               tr.append('<td><input type="checkbox" name="rest"></td>');
				               tr.append('<td><div class="btn-group"><a  href="javascript:;" " data-type="add" class="btn"><i class="icon-plus"></i></a><a href="javascript:;" " data-type="up" class="btn"><i class="icon-arrow-up"></i></a><a href="javascript:;" " data-type="down" class="btn"><i class="icon-arrow-down"></i></a><a href="javascript:;" " data-type="delete" class="btn"><i class=" icon-trash"></i></a></div></td>');
				               tr.insertAfter(ctr);	
							   tr.find("a[data-type]").each(function(){bindEvent(this)});
						});
						 break;
				     case 'delete': 
					    $(element).click(function(){ 
					        $(this).closest("tr").remove(); 	
						});
						 break;
					  case 'up':
					     $(element).click(function(){ 
					        var tr=$(this).closest("tr"); 	
							tr.insertBefore(tr.prev());
						});
						 break;
					  case 'down':
					  	$(element).click(function(){ 
					        var tr=$(this).closest("tr"); 	
							tr.insertAfter(tr.next());
						});
						 break;
				}
		}
}

School.loadGroupUsers=function(obj){
	var $this=$(obj),url=$this.attr("href"),target=$this.data("target");
	if(!$this.hasClass("active")){
		$(target).load(url,function(){
			$("#group-list dl.group-item.active").removeClass("active");
			$this.addClass("active");
		})
	}
}