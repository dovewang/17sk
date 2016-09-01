/*
Copyright 2012, KISS UI Library v0.7.1
MIT Licensed
build time: Jun 1 14:13
*//*头像切割*/
Avatar = {
	options : {
		minSize:[50,50],
		selector : {
			width : 180,
			height : 180
		},
		source : "picture_original",
		previews : [{
			id : "picture_180",
			width : 180,
			height : 180
		}, {
			id : "picture_75",
			width : 75,
			height : 75
		}, {
			id : "picture_50",
			width : 50,
			height : 50
		}],
		updatePreview : function(c) {
			var x = c.x, y = c.y, w = c.w, h = c.h;
			$(Avatar.options.previews).each(function() {
				var p = this;
				var rx = p.width / c.w;
				var ry = p.height / c.h;
				$('#' + p.id + " img").css({
					width : Math.round(rx * Avatar.width) + 'px',
					height : Math.round(ry * Avatar.height) + 'px',
					marginLeft : '-' + Math.round(rx * x) + 'px',
					marginTop : '-' + Math.round(ry * y) + 'px'
				});
				Avatar.data = {
					x : x,
					y : y,
					w : w,
					h : h
				};
			});
		}
	},
	create : function(options) {
		Avatar.options = $.extend(Avatar.options, options);
		if(!isEmpty(Avatar.options.avator)) {
			$("#" + Avatar.options.source).html("<img src='" + Avatar.options.avator + ".o.jpg?t=" + new Date().getTime() + "'>");
			$(Avatar.options.previews).each(function() {
				$("#" + this.id + " img").attr("src", Avatar.options.avator + ".o.jpg?t=" + new Date().getTime());
			});
		}
		$("#" + Avatar.options.source + " img").Jcrop({
			onChange : Avatar.options.updatePreview,
			onSelect : Avatar.options.updatePreview,
			aspectRatio : 1
		}, function() {
			var bounds = this.getBounds();
			Avatar.width = bounds[0];
			Avatar.height = bounds[1];
			Avatar.api = this;
			var w = Avatar.options.selector.width, h = Avatar.options.selector.height, x = (Avatar.width - w) / 2, y = (Avatar.height - h) / 2;
			if(!isEmpty(Avatar.options.avator)) {
				Avatar.api.animateTo([x, y, x + w, y + h]);
			}
		});
	},
	load : function(avator) {
		if(Avatar.api)
			Avatar.api.destroy();
		Avatar.create({
			avator : avator
		});
	}
};
