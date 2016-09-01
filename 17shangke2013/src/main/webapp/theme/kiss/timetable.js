!(function($) {

	var Timetable = function(t, o) {
		var me = this;
		me.self = t;
		me.table = $(t);
		me.o = o;
		me.editable=me.o.editable;
		me.now = o.now;
		me.init();
	}

	Timetable.prototype = {
		init : function() {
			var me = this;
			me.mode = me.o.mode;
			me.header = $('<div class="timetable-header"><div class="btn-group"><button class="btn" action="prev"><i class="icon-chevron-left"></i></button><button class="btn" action="next"><i class="icon-chevron-right"></i></button><button class="btn btn-primary" action="today">今天</button></div><div class="btn-group pull-right"><button class="btn mode" action="week">周</button><button class="btn mode" action="day">天</button></div></div>');
			me.body = $('<div class="timetable-body ' + me.mode + '"></div>');
			me.footer = $('<div class="timetable-footer"></div>');
			me.table.append(me.header);
			me.table.append(me.body);
			me.table.append(me.footer);
			me.today();

			me.header.find("button[action]").on("click.timetable", function() {
				me[$(this).attr("action")]();
			}).filter(".mode[action=" + me.mode + "]").addClass("active");
		},
		reset : function(mode) {
			var me = this;
			me.body.empty();
			/*初始化作息时间表，这里需要同步处理，检查作息时间表的范围*/
			if (!me.schedule || me.schedule.start < me.current || me.schedule.end > me.current) {
				$.getSynch(me.o.schedule, {
					date : me.current.getTime()
				}, function(data) {
					me.schedule = data;
				});
			}
			if (me.mode == mode) {
				return;
			}
			me.header.find("button.mode").removeClass("active").filter("[action=" + mode + "]").addClass("active");
			me.body.removeClass(me.mode);
			me.mode = mode;
			me.body.addClass(me.mode);
		},
		/*
		 * 根据时间表的模式自动切换到相应的时间
		 */
		today : function() {
			var me = this;
			me.current = new Date(me.now.getTime());
			me[me.mode]();
		},
		month : function() {
			var me = this;
			me.reset("month");
		},
		week : function() {
			var me = this,start,end;
			me.reset("week");
			me.current.setDate(me.current.getDate() - me.current.getDay());
			var date = [];
			date[0] = {
				date : me.current.format("yyyy-mm-dd"),
				short : me.current.format("mm/dd")
			};
			for (var i = 1; i < 7; i++) {
				me.current.setDate(me.current.getDate() + 1);
				date[i] = {
					date : me.current.format("yyyy-mm-dd"),
					short : me.current.format("mm/dd")
				};
			}
			/*还原本周第一天*/
			me.current.setDate(me.current.getDate() - me.current.getDay());
			me.list = $('<table class=" table table-bordered" style="width:100%" cellspacing="0"><thead><tr><th class="number"></th><th>日 ' + date[0].short + '</th><th>一 ' + date[1].short + '</th><th>二 ' + date[2].short + '</th><th>三 ' + date[3].short + '</th><th>四 ' + date[4].short + '</th><th>五 ' + date[5].short + '</th><th>六 ' + date[6].short + '</th></tr></thead></table>');
			me.list.body = $("<tbody></tbody>");
			me.list.append(me.list.body);
			me.body.append(me.list);
			$(me.schedule.list).each(function(index, element) {
				var tr = $("<tr/>");
				tr.attr("label",this.label);
				if (this.rest) {
					tr.append('<td colspan="8" class="rest">' + this.label + '</td>');
				} else {
					tr.append('<td date="label" schedule-index="'+(index+1)+'">' + this.label + '</td>');
					$.each(date, function(n) {
						var td = $(me.editable ? '<td   class="blank">+</td>' : '<td></td>');
						td.attr("date", this.date).attr("schedule-index", index + 1);
						if (me.o.td)
							me.o.td(td, me);
						tr.append(td);
					})
				}
				me.list.body.append(tr);
			});
			/*从这里开始读取数据*/
			start=(date[0].date+" 00:00:00").toSecondsTime();
			end=(date[6].date+" 23:59:59").toSecondsTime();
			me.param({start :start,end:end}).refresh();
		},
		day : function() {
			var me = this,start,end;
			me.reset("day");
			var date = me.current.format("yyyy-mm-dd");
			me.list = $('<table class=" table table-bordered" style="width:100%" cellspacing="0"><thead><tr><th class="number"></th><th>' + me.o.dayNames[me.current.getDay()] + '&nbsp;&nbsp;' + me.current.format("yyyy-mm-dd") + '</th></thead></table>');
			me.list.body = $("<tbody></tbody>");
			me.list.append(me.list.body);
			me.body.append(me.list);
			/*时间表初始化*/
			$(me.schedule.list).each(function(n, element) {
				var tr = $("<tr/>");
				tr.attr("label",this.label);
				if (this.rest) {
					tr.append('<td colspan="2" class="rest">' + this.label + '</td>');
				} else {
					tr.append('<td date="label" schedule-index="'+(n+1)+'">' + this.label + '</td>');
					var td = $(me.editable ? '<td   class="blank">+</td>' : '<td></td>');
					td.attr("date", date).data("time", "").attr("schedule-index", n + 1);
					if (me.o.td)
						me.o.td(td, me);
					tr.append(td);
				}
				me.list.body.append(tr);
			});
			/*从这里开始读取数据*/
			start=(date+" 00:00:00").toSecondsTime();
			end=(date+" 23:59:59").toSecondsTime();
			me.param({start:start,end:start}).refresh();
		},
		prev : function() {
			var me = this;
			if (me.mode == "day") {
				me.current.setDate(me.current.getDate() - 1);
				me.day();
			} else if (me.mode == "week") {
				me.current.setDate(me.current.getDate() - 7);
				me.week();
			}
		},
		next : function() {
			var me = this;
			if (me.mode == "day") {
				me.current.setDate(me.current.getDate() + 1);
				me.day();
			} else if (me.mode == "week") {
				me.current.setDate(me.current.getDate() + 7);
				me.week();
			}
		},
		param:function(params){
			this.params=params;
			return this;
		},
		refresh : function() {
			var me = this
			return $.ajax({
				type : "GET",
				data : me.params,
				success : function(data) {
					$(data.result).each(function(index, element) {
						var s = this.classindex.split(",");
						var date=new Date(this.classtime*1000).format("yyyy-mm-dd");
						var content=[];
						if(this.status==0){
							if(me.o.editable){
							  content.push('<span style="color:red">[草稿]</span>');
							}else{
								/*不显示草稿状态的课件*/
								return ;
							}
						}
						content.push('<a href="javascript:;">['+this.lesson+"]</a>");
						content.push('<a href="javascript:;">'+this.title+"</a>");
						var first = me.list.body.find("td[date=" + date + "][schedule-index=" + s[0] + "]").removeClass("blank")
						/*检查是否多节课程*/
						if (s.length > 1) {
							first.attr("rowspan", s.length);
							first.attr("schedule-index",this.classindex);
							for (var i = 1; i < s.length; i++) {
								me.list.body.find("td[date=" + date + "][schedule-index=" + s[i] + "]").remove();
							}
							
						} 	
						first.html(content.join("")).attr({"published":"true","teacher":this.teacher,"cid":this.chapter_id,lesson:this.lesson,title:this.title});
					});
				},
				url : me.o.url,
				dataType : "json"
			});
		}
	}

	$.fn.timetable = function(options) {
		return this.each(function() {
			if (!this.timetable) {
				options = $.extend({
					data : undefined, /*请求时需要发送的数据*/
					dayNames : ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
					dayNamesShort : ['日', '一', '二', '三', '四', '五', '六'],
					now : new Date(),
					mode : "day", /*默认显示month|week|day*/
					editable : false,
					tr : undefined,
					td : undefined,
					schedule : "/school/timetable.html",
					url : "/courseware/timetable.html?g="+Env.GROUP_ID
				}, options, {

				});
				this.timetable = new Timetable(this, options);
			}
			return this;
		});
	}
})(jQuery);
