/*! kiss v0.8.0 | (c) 2012, 2013  @Dove. | hijue.com/license
//@ sourceMappingURL=editor.min.map
*/
(function(){Selection=function(e){this.document=e||document},Selection.index=-1,Selection.items=[],Selection.isTextRange=function(e){return!!e&&e.text!==void 0},Selection.toStandardRange=function(e,t){if(!Selection.isTextRange(e))return e;function n(e,n,i){var o=t.createElement("a"),r=n.duplicate();r.collapse(i);var a=r.parentElement();do a.insertBefore(o,o.previousSibling),r.moveToElementText(o);while(r.compareEndPoints(i?"StartToStart":"StartToEnd",n)>0&&o.previousSibling);-1==r.compareEndPoints(i?"StartToStart":"StartToEnd",n)&&o.nextSibling?(r.setEndPoint(i?"EndToStart":"EndToEnd",n),e[i?"setStart":"setEnd"](o.nextSibling,r.text.length)):e[i?"setStartBefore":"setEndBefore"](o),o.parentNode.removeChild(o)}var i=new Range(t);return n(i,e,!0),n(i,e,!1),i},Selection.prototype={get:function(){if(window.getSelection)this.selection=window.getSelection();else{if(this.document.getSelection)return this.document.getSelection();this.document.selection&&(this.selection=this.document.selection)}return this.selection},text:function(){var e=this.get();return window.getSelection||(e=e.createRange()),""+(null==e.text?e:e.text)},range:function(e){void 0===e&&(e=++Selection.index);var t=this.get();if(t.getRangeAt&&t.rangeCount>0)Selection.items[e]=t.getRangeAt(0);else{var n=this.document.selection.createRange();Selection.items[e]=n}return Selection.items[e]},restore:function(e){void 0===e&&(e=0);var t;window.getSelection?(window.getSelection().removeAllRanges(),window.getSelection().addRange(Selection.items[e])):this.document.body.createTextRange&&(t=this.document.body.createTextRange(),t.moveToBookmark(Selection.items[e]),t.select())},restoreAll:function(){if(window.getSelection){var e=window.getSelection();e.removeAllRanges();for(var t=0;Selection.items.length>t;t++)e.addRange(Selection.items[t])}else alert("\u60a8\u7684\u6d4f\u89c8\u5668\u4e0d\u652f\u6301\u8be5\u64cd\u4f5c")},clear:function(){Selection.items=[]},html:function(e){var t=this.range(),n=this.document;if(Selection.isTextRange(t)&&t.pasteHTML)t.select(),t.pasteHTML(e);else if(t.deleteContents&&t.insertNode){var i=n.createElement("div");i.innerHTML=html;var o=[];for(var r=0;i.childNodes.length>r;r++)o.push(i.childNodes[r]);t.deleteContents();for(var r in o)i.removeChild(o[r]),t.insertNode(o[r])}}}})(),function(){var e=0,t=1,n=2,i=3,o=0;var r=0,a=1,d=2,l=3;Range=function(e){this.startContainer=e,this.startOffset=0,this.endContainer=e,this.endOffset=0,this.collapsed=!0,this.document=e},Range.updateCollapse=function(e){return e.collapsed=e.startContainer&&e.endContainer&&e.startContainer===e.endContainer&&e.startOffset==e.endOffset,e},Range.execContentsAction=function(e,t){var n=e.startContainer,i=e.endContainer,o=e.startOffset,r=e.endOffset,a=e.document,d=a.createDocumentFragment(),l,s;if(1==n.nodeType&&(n=n.childNodes[o]||(l=n.appendChild(a.createTextNode("")))),1==i.nodeType&&(i=i.childNodes[r]||(s=i.appendChild(a.createTextNode("")))),n===i&&3==n.nodeType)return d.appendChild(a.createTextNode(n.substringData(o,r-o))),t&&(n.deleteData(o,r-o),e.collapse(!0)),d;var c,u,m=d,h=$(n).parent().andSelf(),p=$(i).parent().andSelf();for(var f=0;h[f]==p[f];)f++;for(var g=f,b;b=h[g];g++){c=b.nextSibling,b==n?l||(3==e.startContainer.nodeType?(m.appendChild(a.createTextNode(n.nodeValue.slice(o))),t&&n.deleteData(o,n.nodeValue.length-o)):m.appendChild(t?n:n.cloneNode(!0))):(u=b.cloneNode(!1),m.appendChild(u));while(c){if(c===i||c===p[g])break;b=c.nextSibling,m.appendChild(t?c:c.cloneNode(!0)),c=b}m=u}m=d,h[f]||(m.appendChild(h[f-1].cloneNode(!1)),m=m.firstChild);for(var g=f,v;v=p[g];g++){if(c=v.previousSibling,v==i?s||3!=e.endContainer.nodeType||(m.appendChild(a.createTextNode(i.substringData(0,r))),t&&i.deleteData(0,r)):(u=v.cloneNode(!1),m.appendChild(u)),g!=f||!h[f])while(c){if(c===n)break;v=c.previousSibling,m.insertBefore(t?c:c.cloneNode(!0),m.firstChild),c=v}m=u}return t&&e.setStartBefore(p[f]?h[f]?p[f]:h[f-1]:p[f-1]).collapse(!0),l&&$(l).remove(),s&&$(s).remove(),d},Range.prototype={getCommonAncestor:function(){function e(e){var t=[];while(e)t.push(e),e=e.parentNode;return t}var t=e(this.startContainer),n=e(this.endContainer),i=0,o=t.length,r=n.length,a,d;while(++i)if(a=t[o-i],d=n[r-i],!a||!d||a!==d)break;return t[o-i+1]},setStart:function(e,t){var n=this,i=n.document;return n.startContainer=e,n.startOffset=t,n.endContainer===i&&(n.endContainer=e,n.endOffset=t),Range.updateCollapse(n)},setEnd:function(e,t){var n=this,i=n.document;return n.endContainer=e,n.endOffset=t,n.startContainer===i&&(n.startContainer=e,n.startOffset=t),Range.updateCollapse(n)},setStartBefore:function(e){return this.setStart(e.parentNode||this.document,$(e).index())},setStartAfter:function(e){return this.setStart(e.parentNode||this.document,$(e).index()+1)},setEndBefore:function(e){return this.setEnd(e.parentNode||this.document,$(e).index())},setEndAfter:function(e){return this.setEnd(e.parentNode||this.document,$(e).index()+1)},setStartAtFirst:function(e){return this.setStart(e,0)},setStartAtLast:function(e){return this.setStart(e,3==e.nodeType?e.nodeValue.length:e.childNodes.length)},selectNode:function(e){return this.setStartBefore(e).setEndAfter(e)},selectNodeContents:function(e){return this.setStart(e,0).setEndAtLast(e)},collapse:function(e){return e?this.setEnd(this.startContainer,this.startOffset):this.setStart(this.endContainer,this.endOffset)},compareBoundaryPoints:function(o,r){var a,d,l,s;switch(o){case e:case t:a=this.startContainer,d=this.startOffset;break;case n:case i:a=this.endContainer,d=this.endOffset}switch(o){case e:case i:l=r.startContainer,s=r.startOffset;break;case t:case n:l=r.endContainer,s=r.endOffset}return a.sourceIndex<l.sourceIndex?-1:a.sourceIndex==l.sourceIndex?s>d?-1:d==s?0:1:1},cloneRange:function(){return new Range(this.document).setStart(this.startContainer,this.startOffset).setEnd(this.endContainer,this.endOffset)},cloneContents:function(){return this.collapsed?null:Range.execContentsAction(this,0)},deleteContents:function(){var e;return this.collapsed||Range.execContentsAction(this,1),webkit&&(e=this.startContainer,3!=e.nodeType||e.nodeValue.length||(this.setStartBefore(e).collapse(!0),$(e).remove())),this},extractContents:function(){return this.collapsed?null:Range.execContentsAction(this,2)},insertNode:function(e){var t=this,i=t.startContainer,o=t.startOffset,r=t.endContainer,a=t.endOffset,d,l,s,c=1;return"#document-fragment"===e.nodeName.toLowerCase()&&(d=e.firstChild,l=e.lastChild,c=e.childNodes.length),1==i.nodeType?(s=i.childNodes[o],s?(i.insertBefore(e,s),i===r&&(a+=c)):i.appendChild(e)):3==i.nodeType&&(0===o?(i.parentNode.insertBefore(e,i),i.parentNode===r&&(a+=c)):o>=i.nodeValue.length?i.nextSibling?i.parentNode.insertBefore(e,i.nextSibling):i.parentNode.appendChild(e):(s=o>0?i.splitText(o):i,i.parentNode.insertBefore(e,s),i===r&&(r=s,a-=o))),d?t.setStartBefore(d).setEndAfter(l):t.selectNode(e),t.compareBoundaryPoints(n,t.cloneRange().setEnd(r,a))>=1?t:t.setEnd(r,a)},surroundContents:function(e){return e.appendChild(this.extractContents()),this.insertNode(e).selectNode(e)},html:function(){return $(this.cloneContents()).wrap("<div></div>").html()},enlarge:function(e,t){if(tmp=this.document.createTextNode(""),e){node=this.startContainer,1==node.nodeType?node.childNodes[this.startOffset]?pre=node=node.childNodes[this.startOffset]:(node.appendChild(tmp),pre=node=tmp):pre=node;while(1){if(dom.isBlock(node)){node=pre;while((pre=node.previousSibling)&&!dom.isBlock(pre))node=pre;this.setStartBefore(node);break}pre=node,node=node.parentNode}node=this.endContainer,1==node.nodeType?((pre=node.childNodes[this.endOffset])?node.insertBefore(tmp,pre):node.appendChild(tmp),pre=node=tmp):pre=node;while(1){if(dom.isBlock(node)){node=pre;while((pre=node.nextSibling)&&!dom.isBlock(pre))node=pre;this.setEndAfter(node);break}pre=node,node=node.parentNode}tmp.parentNode===this.endContainer&&this.endOffset--,$(tmp).remove()}if(!this.collapsed){while(0==this.startOffset){if(t&&t(this.startContainer))break;if($(this.startContainer).is("body"))break;this.setStartBefore(this.startContainer)}while(this.endOffset==(1==this.endContainer.nodeType?this.endContainer.childNodes.length:this.endContainer.nodeValue.length)){if(t&&t(this.endContainer))break;if($(this.endContainer).is("body"))break;this.setEndAfter(this.endContainer)}}return this},shrink:function(){var e=this,t,n=e.collapsed;function i(e){return 1==e.nodeType&&!Editor.dtd.$empty[e.tagName]&&!Editor.dtd.$nonChild[e.tagName]}while(1==e.startContainer.nodeType&&(t=e.startContainer.childNodes[e.startOffset])&&i(t))e.setStart(t,0);if(n)return e.collapse(n);while(1==e.endContainer.nodeType&&e.endOffset>0&&(t=e.endContainer.childNodes[e.endOffset-1])&&i(t))e.setEnd(t,t.childNodes.length);return e},createContextualFragment:function(e){var t=(dom.isDataNode(this.startContainer)?this.startContainer.parentNode:this.startContainer).cloneNode(!1);$(t).html(e);for(var n=this.document.createDocumentFragment();t.firstChild;)n.appendChild(t.firstChild);return n},optimize:function(){},trim:function(e,t){},createBookmark:function(e){var t=this,n=t.document,i,r=$('<span style="display:none;"></span>',n)[0];return r.id="__editor_bookmark_start_"+o++ +"__",t.collapsed||(i=r.cloneNode(!0),i.id="__editor_bookmark_end_"+o++ +"__"),i&&t.cloneRange().collapse(!1).insertNode(i).setEndBefore(i),t.insertNode(r).setStartAfter(r),{start:e?"#"+r.id:r,end:i?e?"#"+i.id:i:null}},moveToBookmark:function(e){var t=this,n=t.document,i=$(e.start,n),o=e.end?$(e.end,n):null;return!i||1>i.length?t:(t.setStartBefore(i[0]),i.remove(),o&&o.length>0?(t.setEndBefore(o[0]),o.remove()):t.collapse(!0),t)},scroll:function(e,t){}}}(),function(){window.ie=UA.browser.msie,window.chrome=UA.browser.chrome,window.safari=UA.browser.safari,window.opera=UA.browser.opera,window.mozilla=UA.browser.mozilla,Editor=function(e,t){return this.textarea=$(e),t=$.extend({id:"editor"+$.now(),base:Editor.getPath(),focus:!1,designMode:!0,fullscreen:!1,filter:!0,wellFormat:!0,shadow:!0,loadStyleMode:!0,resizeable:!0,readonly:this.textarea.attr("readonly"),disabled:this.textarea.attr("disabled"),content:"\u521d\u59cb\u5316\u7684\u5185\u5bb9\uff08\u4f8b\u5982\u4f60\u8fd8\u6ca1\u767b\u5f55\uff0c\u8bf7\u81ea\u884c\u4fee\u6539\uff09",theme:"default",height:"auto",minHeight:"60px",width:"auto",minWidth:"120px",count:{label:"\u60a8\u5df2\u7ecf\u8f93\u5165#wordcount#\u4e2a\u5b57",mode:"text",min:0,max:5e3,size:function(e,t){}},image:{cropabel:!1,resizeable:!1,capture:!0,url:"/upload/image.html",allowType:/\.(gif|jpg|jpeg|bmp|png)$/,manager:"/filemanager/image.html"},audio:{url:"/upload/image.html",allowType:"mp3|wav|",manager:"/filemanager/audio.html"},video:{uploadable:!1,snapshot:!1,capture:!0,convertible:!1,url:"",allowType:"",manager:"/filemanager/video.html"},voice:{url:""},handwriting:{url:""},plugins:["bold","italic","underline","strikethrough","subscript","superscript","insertorderedlist","insertunorderedlist","justifyleft","justifycenter","justifyright","justifyfull","|","image","sketchpad","camera","fullscreen"],language:"zh_CN",newlineTag:"p",layout:'<div class="editor"><div class="editor-header"></div><div class="editor-body"></div><div class="editor-footer"><div class="wordcount"></div><div class="resize"></div></div></div>',onload:function(){trace("onload")}},t),this.init(e,t),this},Editor.getPath=function(){var e=document.getElementsByTagName("script"),t;for(var n=0,i=e.length;i>n;n++)if(t=e[n].src||"",/editor[\w\-\.]*\.js/.test(t))return t.substring(0,t.lastIndexOf("/")+1);return""},Editor.template=function(e,t){var n=["",'<html><head><meta charset="utf-8" />','<style type="text/css">',"html {margin:0;padding:0;word-wrap:break-word;cursor:text;}","body {margin:0;padding:5px;background:#fff;display: block;}",'body, td {font:14px/1.5 "sans serif",tahoma,verdana,helvetica;}',"body, p, div {word-wrap: break-word;}","p {margin:5px 0;}","table {border-collapse:collapse;}","img {border:0;}","noscript {display:none;}","table.zeroborder td {border:1px dotted #AAA;}",".placeholder{color:#999;}","</style>"];return e&&$.each(e,function(e,t){t&&n.push('<link href="'+t+'" rel="stylesheet" />')}),t&&n.push("<style>"+t+"</style>"),n.push("</head><body></body></html>"),n.join("\n")},Editor.language={},Editor.plugins={},Editor.plugin=function(e,t,n){return $.each(e.split(","),function(e,i){n&&n[e],Editor.plugins[i]=t})},Editor.prototype={contextmenu:!1,init:function(e,t){var n=this,i=location.host.replace(/:\d+/,"")!==document.domain;n.id=t.id,n.designMode=t.designMode,n.language=t.language,n.options=t,n.data={history:{},plugins:{}};var o=n.textarea.attr("width");o=o?o:t.width;var r=n.textarea.attr("height");r=r?r:t.height,n.container=$(t.layout).css({width:o}),n.header=n.container.find(".editor-header").on("contextmenu",function(e){e.preventDefault()}),n.body=n.container.find(".editor-body"),n.footer=n.container.find(".editor-footer"),n.wordcount=n.footer.find(".wordcount"),n.iframe=$('<iframe class="editor-iframe" hidefocus="true" frameborder="0"   src="about:blank" height="100%" width="100%"></iframe>'),n.body.append(n.iframe),n.textarea.after(n.container),n.body.css("height",r),n.iframe.css("height",r),n.data.oldvalue=e.value;var a=n.textarea.attr("placeholder");n.data.placeholder=isEmpty(a)?"":'<div class="placeholder">'+a+"</div>",n.designMode?n.textarea.hide():n.iframe.hide(),i&&n.iframe.on("load.editor",function(){n.iframe.off("load"),setTimeout(function(){d()},0)});function d(e){n.window=n.iframe[0].contentWindow;var i=$.iframe.getDocument(n.iframe[0]);i.open(),i.write(Editor.template()),i.close(),ie?(i.body.disabled=!0,i.body.contentEditable=!0,i.body.disabled=!1):(i.body.contentEditable=!0,i.body.spellcheck=!1),n.document=i,n.cmd=new Editor.cmd(n),n.count("text",!0),$(n.document).on("click.editor",function(e){trace("click"),n.textarea.trigger("click")}).on("keydown.editor",function(e){n.textarea.trigger("keydown"),13==e.which&&(e.preventDefault(),n.cmd.inserthtml("<br/>\u200b")),e.ctrlKey&&86===e.which}).on("keypress.editor",function(e){trace("keypress"),n.textarea.trigger("keypress")}).on("keyup.editor",function(e){trace("keyup"),n.textarea.trigger("keyup"),n.count("text",!0)}).on("beforepaste.editor",function(e){trace("beforepaste")}).on("paste.editor",function(e){trace("paste"),n.count("text",!0),n.textarea.trigger("paste")}).on("mouseenter",function(e){trace("mouseenter")}).on("mouseleave",function(e){trace("mouseleave")}).on("dragenter",function(){trace("dragenter")}).on("dragover",function(){trace("dragover")}),$(n.window).on("focus.editor",function(e){trace("focus"),n.html()==n.data.placeholder&&n.html("")}).on("blur.editor",function(e){trace("blur"),n.isEmpty()&&n.html(n.data.placeholder)}),n.isReady=!0,t.readonly&&n.readonly(),t.disabled?(t.content&&n.html(t.content),n.disable()):n.html(isBlank(n.data.oldvalue)?n.data.placeholder:n.data.oldvalue),isBlank(n.data.oldvalue)||n.count("text",!0)}!i&&d(),n.toolbar.init(n,t)},ready:function(e){var t=this},toolbar:{init:function(e,t){$.each(t.plugins,function(t,n){if("|"==n)e.header.append('<span class="divider"></span>');else{var i=Editor.plugins[n];if(i){var o=i(n,e);o.toolbar&&(o.toolbar=o.toolbar.attr("unselectable","on"),e.header.append(o.toolbar)),e.data.plugins[n]=o}else trace("\u63d2\u4ef6\u672a\u5b9a\u4e49:"+n)}})},enable:function(e){this.editor.data.plugins[e].toolbar.removeClass("disabled")},disable:function(e){this.editor.data.plugins[e].toolbar.addClass("disabled")}},render:function(){},remove:function(){},get:function(e){return this.data.plugins[e]},sync:function(){},i18n:function(e){return Editor.language[this.language][e]},text:function(e){var t=this,n=t.document;return void 0===e?$(n.body).text():($(n.body).text(e),t)},html:function(e,t){var n=this,i=n.document;return void 0===e?n.isReady?$(i.body).html():n.textarea[0].value:(n.isReady?$(i.body).html(e):n.textarea[0].value=e,n)},append:function(e){var t=this,n=t.document;return $(n.body).append(e),t},before:function(e){var t=this,n=t.document;return $(n.body).before(e),t},disable:function(){var e=this;e.isReady&&(e.document.body.disabled=!0,e.document.body.contentEditable=!1,e.header.addClass("disabled"),$(e.document.body).css({opacity:.3}))},enable:function(){var e=this;e.isReady&&(e.document.body.disabled=!1,e.document.body.contentEditable=!0,e.header.removeClass("disabled"),$(e.document.body).css({opacity:1}))},readonly:function(){this.isReady&&(this.document.body.contentEditable=!1)},isEmpty:function(){return""===this.html().replace(/<(?!img|embed).*?>/gi,"").replace(/&nbsp;/gi," ").trim()},isDirty:function(){return this.data.oldvalue.replace(/\r\n|\n|\r|t/g,"").trim()!==this.html().replace(/\r\n|\n|\r|t/g,"").trim()},selected:function(e){return e=isEmpty(e,"html"),this.isReady?this.cmd.range.html():""},count:function(e,t){var n=this,i=0;if(e=(e||"html").toLowerCase(),"text"===e){if(i=n.text().replace(/<(?:img|embed).*?>/gi,"E").replace(/\r\n|\n|\r/g,"").length,t){var o=i;-1!=n.options.count.max&&(trace(o),o="<em"+(n.options.count.max>=i?"":' class="over" ')+"><span>"+i+"</span>/"+n.options.count.max+"</em>"),n.wordcount.html(n.options.count.label.replace("#wordcount#",o))}return i}return i},focus:function(){var e=this;return e.designMode?e.window.focus():e.textarea[0].focus(),e},blur:function(){var e=this;if(ie){var t=$('<input type="text" style="float:left;width:0;height:0;padding:0;margin:0;border:0;" value="" />');e.container.append(t),t[0].focus(),t.remove()}else e.designMode?e.window.blur():e.textarea[0].blur();return e},show:function(e){this.container.show(e)},hide:function(){this.container.hide(fn)},height:function(e){},width:function(e){},resize:function(e,t){}},Editor.cmd=function(e){return this.init(e),this},Editor.cmd.getWindow=function(e){if(!e)return window;var t=Editor.cmd.getDocument(e);return t.parentWindow||t.defaultView},Editor.cmd.getDocument=function(e){return e?e.ownerDocument||e.document||e:document},Editor.cmd.getSelection=function(e){return Editor.selection||(Editor.selection=new Selection(e)),Editor.selection},Editor.cmd.getRange=function(e){return new Range(e)},Editor.cmd.prototype={init:function(e){var t=this;t.editor=e,t.document=e.document,t.window=e.window,t.sel=Editor.cmd.getSelection(t.document),t.range=Editor.cmd.getRange(t.document)},val:function(e){var t="";try{t=this.document.queryCommandValue(e)}catch(n){}return"string"!=typeof t&&(t=""),t},exec:function(e,t){try{this.document.execCommand(e,!1,t)}catch(n){}},state:function(e){var t=this,n=t.document,i=!1;try{i=n.queryCommandState(e)}catch(o){}return i},selection:function(e){},select:function(){},toggle:function(e,t){},bold:function(e){this.exec("Bold")},italic:function(e){this.exec("Italic")},underline:function(e){this.exec("Underline")},strikethrough:function(e){this.exec("Strikethrough")},forecolor:function(e){},hilitecolor:function(e){},fontsize:function(e){},fontname:function(e){return this.fontfamily(e)},removeformat:function(){},inserthtml:function(e,t){return this.editor.focus(),ie?(this.sel.html(e),this):(this.exec("inserthtml",e),this)},hr:function(){return this.inserthtml("<hr/>")},print:function(){return this.window.print(),this},insertimage:function(e,t,n,i,o,r){t=isEmpty(t,"");var a=['<img src="'+e+'" source="'+e+'" '];return n&&a.push('width="'+n+'" '),i&&a.push('height="'+i+'" '),t&&a.push('title="'+t+'" '),r&&a.push('align="'+r+'" '),a.push('  alt="'+t+'"/>'),this.inserthtml(a.join("  "))},link:function(e,t){},unlink:function(){},selectall:function(){}},$.each("formatblock,selectall,justifyleft,justifycenter,justifyright,justifyfull,insertorderedlist,insertunorderedlist,indent,outdent,subscript,superscript".split(","),function(e,t){Editor.cmd.prototype[t]=function(e){var n=this;return n.exec(t,e),(!ie||$.inArray(t,"formatblock,selectall,insertorderedlist,insertunorderedlist".split(","))>=0)&&n.selection(),n}}),Editor.plugin("justifyleft,justifycenter,justifyright,justifyfull,insertorderedlist,insertunorderedlist,subscript,superscript,bold,italic,underline,strikethrough",function(e,t){var n={};return n.toolbar=$('<span   class="'+e+'"  title="'+t.i18n(e)+'"></span>').click(function(){t.cmd[e]()}),n}),Editor.plugin("fullscreen",function(e,t){var n={};return n.toolbar=$('<span   class="'+e+'"  title="'+t.i18n(e)+'"  ></span>').click(function(){t.cmd.inserthtml("\u6d4b\u8bd5<b>21312312312</b>\u63d2\u5165<strong>chuti</strong>")}),n}),Editor.plugin("image",function(e,t){var n={};return n.toolbar=$('<span   class="'+e+'"  title="'+t.i18n(e)+'"></span>').click(function(){$(this).hasClass("disabled")||t.options.disabled||Pop.show({id:t.id+"editor-image",content:['<form  id="'+t.id+'editor-image-form" method="post" onsubmit="return false;" action="/upload/image.html" enctype="multipart/form-data">','<button class="button"  action="upload" file-name="filedata">\u6d4f\u89c8</button>',"</form>"].join(""),title:"\u56fe\u7247\u4e0a\u4f20",css:{"min-height":"200px",width:"400px"},afterShow:function(e){var n=e.find("form:first");var i=n.find("button[action=upload]").upload();n.find("input:file").change(function(){return isEmpty(this.value)?void 0:this.value.is(t.options.image.allowType)?(n.postForm(function(n){t.cmd.insertimage(n.msg),Pop.hide(e)}),void 0):($.alert.warn("\u60a8\u9009\u62e9\u7684\u56fe\u7247\u683c\u5f0f\u4e0d\u7b26\u5408\u8981\u6c42\uff01"),void 0)})}})}),n.editor={},n}),Editor.plugin("camera",function(name,editor){var plugin={};return plugin.toolbar=$('<span   class="'+name+'"  title="'+editor.i18n(name)+'"></span>').click(function(){Pop.show({id:editor.id+"editor-camera",editor:editor,title:"\u62cd\u7167",destroy:!0,content:'<iframe id="'+editor.id+'camera_iframe_id" name="'+editor.id+'camera_iframe" src="/theme/kiss/editor/plugins/camera/camera.jsp" frameborder="0" style="height: 500px;width: 100%;"></iframe>',css:{height:"500px",width:"600px"},afterShow:function($pop){var iframe=eval(editor.id+"camera_iframe");$("#"+editor.id+"camera_iframe_id").ready(function(){iframe.bind(function(e){editor.cmd.insertimage(e),Pop.hide($pop)})})}})}),plugin}),Editor.plugin("sketchpad",function(name,editor){var plugin={};return plugin.toolbar=$('<span   class="'+name+'"  title="'+editor.i18n(name)+'"></span>').click(function(){Pop.show({id:editor.id+"editor-sketchpad",title:"\u753b\u677f",content:'<iframe   name="'+editor.id+'sketchpad_iframe_name" src="/theme/kiss/sketchpad/sketchpad.htm" frameborder="0" style="height: 480px;width: 100%;" scrolling="no"></iframe>',css:{height:"500px",width:"620px"},footer:'<button class="btn" node-type="pop-close" type="button">\u53d6\u6d88</button><button  type="button"  class="btn btn-primary" action="insert">\u63d2\u5165</button>',afterShow:function($pop){$pop.find("[action=insert]").click(function(){var data=eval(editor.id+"sketchpad_iframe_name").getImageData();$.post("/upload/data.html",{data:data},function(e){editor.cmd.insertimage(e.url),Pop.hide($pop)})})}})}),plugin})}(window),function(e){e.fn.editor=function(e){return this.each(function(){return this.editor||(this.editor=new Editor(this,e)),this})};var t=jQuery.fn.val;e.fn.val=function(e){var n=this,i;return n[0]&&(i=n[0].editor)?i.html(e):void 0===e?t.apply(n):t.apply(n,[e])}}(jQuery),Editor.dtd=function(){function e(e){for(var t in e)e[t.toUpperCase()]=e[t];return e}function t(e){var t=arguments;for(var n=1;t.length>n;n++){var i=t[n];for(var o in i)e.hasOwnProperty(o)||(e[o]=i[o])}return e}var n={isindex:1,fieldset:1},i={input:1,button:1,select:1,textarea:1,label:1},o=t({a:1},i),r=t({iframe:1},o),a={hr:1,ul:1,menu:1,div:1,section:1,header:1,footer:1,nav:1,article:1,aside:1,figure:1,dialog:1,hgroup:1,mark:1,time:1,meter:1,command:1,keygen:1,output:1,progress:1,audio:1,video:1,details:1,datagrid:1,datalist:1,blockquote:1,noscript:1,table:1,center:1,address:1,dir:1,pre:1,h5:1,dl:1,h4:1,noframes:1,h6:1,ol:1,h1:1,h3:1,h2:1},d={ins:1,del:1,script:1,style:1},l=t({b:1,acronym:1,bdo:1,"var":1,"#":1,abbr:1,code:1,br:1,i:1,cite:1,kbd:1,u:1,strike:1,s:1,tt:1,strong:1,q:1,samp:1,em:1,dfn:1,span:1,wbr:1},d),s=t({sub:1,img:1,object:1,sup:1,basefont:1,map:1,applet:1,font:1,big:1,small:1,mark:1},l),c=t({p:1},s),u=t({iframe:1},s,i),m={img:1,noscript:1,br:1,kbd:1,center:1,button:1,basefont:1,h5:1,h4:1,samp:1,h6:1,ol:1,h1:1,h3:1,h2:1,form:1,font:1,"#":1,select:1,menu:1,ins:1,abbr:1,label:1,code:1,table:1,script:1,cite:1,input:1,iframe:1,strong:1,textarea:1,noframes:1,big:1,small:1,span:1,hr:1,sub:1,bdo:1,"var":1,div:1,section:1,header:1,footer:1,nav:1,article:1,aside:1,figure:1,dialog:1,hgroup:1,mark:1,time:1,meter:1,menu:1,command:1,keygen:1,output:1,progress:1,audio:1,video:1,details:1,datagrid:1,datalist:1,object:1,sup:1,strike:1,dir:1,map:1,dl:1,applet:1,del:1,isindex:1,fieldset:1,ul:1,b:1,acronym:1,a:1,blockquote:1,i:1,u:1,s:1,tt:1,address:1,q:1,pre:1,p:1,em:1,dfn:1},h=t({a:1},u),p={tr:1},f={"#":1},g=t({param:1},m),b=t({form:1},n,r,a,c),v={li:1},y={style:1,script:1},w={base:1,link:1,meta:1,title:1},C=t(w,y),k={head:1,body:1},x={html:1};var S={address:1,blockquote:1,center:1,dir:1,div:1,section:1,header:1,footer:1,nav:1,article:1,aside:1,figure:1,dialog:1,hgroup:1,time:1,meter:1,menu:1,command:1,keygen:1,output:1,progress:1,audio:1,video:1,details:1,datagrid:1,datalist:1,dl:1,fieldset:1,form:1,h1:1,h2:1,h3:1,h4:1,h5:1,h6:1,hr:1,isindex:1,noframes:1,ol:1,p:1,pre:1,table:1,ul:1};return{$nonBodyContent:t(x,k,w),$block:S,$blockLimit:{body:1,div:1,section:1,header:1,footer:1,nav:1,article:1,aside:1,figure:1,dialog:1,hgroup:1,time:1,meter:1,menu:1,command:1,keygen:1,output:1,progress:1,audio:1,video:1,details:1,datagrid:1,datalist:1,td:1,th:1,caption:1,form:1},$inline:h,$body:t({script:1,style:1},S),$cdata:{script:1,style:1},$empty:{area:1,base:1,br:1,col:1,hr:1,img:1,input:1,link:1,meta:1,param:1,wbr:1},$listItem:{dd:1,dt:1,li:1},$list:{ul:1,ol:1,dl:1},$nonEditable:{applet:1,button:1,embed:1,iframe:1,map:1,object:1,option:1,script:1,textarea:1,param:1,audio:1,video:1},$captionBlock:{caption:1,legend:1},$removeEmpty:{abbr:1,acronym:1,address:1,b:1,bdo:1,big:1,cite:1,code:1,del:1,dfn:1,em:1,font:1,i:1,ins:1,label:1,kbd:1,q:1,s:1,samp:1,small:1,span:1,strike:1,strong:1,sub:1,sup:1,tt:1,u:1,"var":1,mark:1},$tabIndex:{a:1,area:1,button:1,input:1,object:1,select:1,textarea:1},$tableContent:{caption:1,col:1,colgroup:1,tbody:1,td:1,tfoot:1,th:1,thead:1,tr:1},html:k,head:C,style:f,script:f,body:b,base:{},link:{},meta:{},title:f,col:{},tr:{td:1,th:1},img:{},colgroup:{col:1},noscript:b,td:b,br:{},wbr:{},th:b,center:b,kbd:h,button:t(c,a),basefont:{},h5:h,h4:h,samp:h,h6:h,ol:v,h1:h,h3:h,option:f,h2:h,form:t(n,r,a,c),select:{optgroup:1,option:1},font:h,ins:h,menu:v,abbr:h,label:h,table:{thead:1,col:1,tbody:1,tr:1,colgroup:1,caption:1,tfoot:1},code:h,tfoot:p,cite:h,li:b,input:{},iframe:b,strong:h,textarea:f,noframes:b,big:h,small:h,span:h,hr:{},dt:h,sub:h,optgroup:{option:1},param:{},bdo:h,"var":h,div:b,object:g,sup:h,dd:b,strike:h,area:{},dir:v,map:t({area:1,form:1,p:1},n,d,a),applet:g,dl:{dt:1,dd:1},del:h,isindex:{},fieldset:t({legend:1},m),thead:p,ul:v,acronym:h,b:h,a:u,blockquote:b,caption:h,i:h,u:h,tbody:p,s:h,address:t(r,c),tt:h,legend:h,q:h,pre:t(l,o),p:h,em:h,dfn:h,section:b,header:b,footer:b,nav:b,article:b,aside:b,figure:b,dialog:b,hgroup:b,mark:h,time:h,meter:h,menu:h,command:h,keygen:h,output:h,progress:g,audio:g,video:g,details:g,datagrid:g,datalist:g}}(),Editor.language.zh_CN={source:"HTML\u4ee3\u7801",preview:"\u9884\u89c8",undo:"\u540e\u9000(Ctrl+Z)",redo:"\u524d\u8fdb(Ctrl+Y)",cut:"\u526a\u5207(Ctrl+X)",copy:"\u590d\u5236(Ctrl+C)",paste:"\u7c98\u8d34(Ctrl+V)",plainpaste:"\u7c98\u8d34\u4e3a\u65e0\u683c\u5f0f\u6587\u672c",wordpaste:"\u4eceWord\u7c98\u8d34",selectall:"\u5168\u9009(Ctrl+A)",justifyleft:"\u5de6\u5bf9\u9f50",justifycenter:"\u5c45\u4e2d",justifyright:"\u53f3\u5bf9\u9f50",justifyfull:"\u4e24\u7aef\u5bf9\u9f50",insertorderedlist:"\u7f16\u53f7",insertunorderedlist:"\u9879\u76ee\u7b26\u53f7",indent:"\u589e\u52a0\u7f29\u8fdb",outdent:"\u51cf\u5c11\u7f29\u8fdb",subscript:"\u4e0b\u6807",superscript:"\u4e0a\u6807",formatblock:"\u6bb5\u843d",fontname:"\u5b57\u4f53",fontsize:"\u6587\u5b57\u5927\u5c0f",forecolor:"\u6587\u5b57\u989c\u8272",hilitecolor:"\u6587\u5b57\u80cc\u666f",bold:"\u7c97\u4f53(Ctrl+B)",italic:"\u659c\u4f53(Ctrl+I)",underline:"\u4e0b\u5212\u7ebf(Ctrl+U)",strikethrough:"\u5220\u9664\u7ebf",removeformat:"\u5220\u9664\u683c\u5f0f",image:"\u56fe\u7247",multiimage:"\u6279\u91cf\u56fe\u7247\u4e0a\u4f20",flash:"Flash",video:"\u89c6\u9891",audio:"\u97f3\u9891",sketchpad:"\u753b\u677f",camera:"\u62cd\u7167",table:"\u8868\u683c",tablecell:"\u5355\u5143\u683c",hr:"\u63d2\u5165\u6a2a\u7ebf",emoticons:"\u63d2\u5165\u8868\u60c5",link:"\u8d85\u7ea7\u94fe\u63a5",unlink:"\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",fullscreen:"\u5168\u5c4f\u663e\u793a(Esc)",about:"\u5173\u4e8e",print:"\u6253\u5370(Ctrl+P)",filemanager:"\u6587\u4ef6\u7a7a\u95f4",code:"\u63d2\u5165\u7a0b\u5e8f\u4ee3\u7801",map:"Google\u5730\u56fe",baidumap:"\u767e\u5ea6\u5730\u56fe",lineheight:"\u884c\u8ddd",clearhtml:"\u6e05\u7406HTML\u4ee3\u7801",pagebreak:"\u63d2\u5165\u5206\u9875\u7b26",quickformat:"\u4e00\u952e\u6392\u7248",attachment:"\u9644\u4ef6",template:"\u63d2\u5165\u6a21\u677f",anchor:"\u951a\u70b9",yes:"\u786e\u5b9a",no:"\u53d6\u6d88",close:"\u5173\u95ed",editImage:"\u56fe\u7247\u5c5e\u6027",deleteImage:"\u5220\u9664\u56fe\u7247",editFlash:"Flash\u5c5e\u6027",deleteFlash:"\u5220\u9664Flash",editMedia:"\u89c6\u97f3\u9891\u5c5e\u6027",deleteMedia:"\u5220\u9664\u89c6\u97f3\u9891",editLink:"\u8d85\u7ea7\u94fe\u63a5\u5c5e\u6027",deleteLink:"\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",editAnchor:"\u951a\u70b9\u5c5e\u6027",deleteAnchor:"\u5220\u9664\u951a\u70b9",tableprop:"\u8868\u683c\u5c5e\u6027",tablecellprop:"\u5355\u5143\u683c\u5c5e\u6027",tableinsert:"\u63d2\u5165\u8868\u683c",tabledelete:"\u5220\u9664\u8868\u683c",tablecolinsertleft:"\u5de6\u4fa7\u63d2\u5165\u5217",tablecolinsertright:"\u53f3\u4fa7\u63d2\u5165\u5217",tablerowinsertabove:"\u4e0a\u65b9\u63d2\u5165\u884c",tablerowinsertbelow:"\u4e0b\u65b9\u63d2\u5165\u884c",tablerowmerge:"\u5411\u4e0b\u5408\u5e76\u5355\u5143\u683c",tablecolmerge:"\u5411\u53f3\u5408\u5e76\u5355\u5143\u683c",tablerowsplit:"\u62c6\u5206\u884c",tablecolsplit:"\u62c6\u5206\u5217",tablecoldelete:"\u5220\u9664\u5217",tablerowdelete:"\u5220\u9664\u884c",noColor:"\u65e0\u989c\u8272",pleaseSelectFile:"\u8bf7\u9009\u62e9\u6587\u4ef6\u3002",invalidImg:"\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8jpg,gif,bmp,png\u683c\u5f0f\u3002",invalidMedia:"\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb\u683c\u5f0f\u3002",invalidWidth:"\u5bbd\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",invalidHeight:"\u9ad8\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",invalidBorder:"\u8fb9\u6846\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",invalidUrl:"\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002",invalidRows:"\u884c\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",invalidCols:"\u5217\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",invalidPadding:"\u8fb9\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",invalidSpacing:"\u95f4\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",invalidJson:"\u670d\u52a1\u5668\u53d1\u751f\u6545\u969c\u3002",uploadSuccess:"\u4e0a\u4f20\u6210\u529f\u3002",cutError:"\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u526a\u5207\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+X)\u6765\u5b8c\u6210\u3002",copyError:"\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u590d\u5236\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+C)\u6765\u5b8c\u6210\u3002",pasteError:"\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u7c98\u8d34\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u6765\u5b8c\u6210\u3002",ajaxLoading:"\u52a0\u8f7d\u4e2d\uff0c\u8bf7\u7a0d\u5019 ...",uploadLoading:"\u4e0a\u4f20\u4e2d\uff0c\u8bf7\u7a0d\u5019 ...",uploadError:"\u4e0a\u4f20\u9519\u8bef","plainpaste.comment":"\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002","wordpaste.comment":"\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002","link.url":"URL","link.linkType":"\u6253\u5f00\u7c7b\u578b","link.newWindow":"\u65b0\u7a97\u53e3","link.selfWindow":"\u5f53\u524d\u7a97\u53e3","flash.url":"URL","flash.width":"\u5bbd\u5ea6","flash.height":"\u9ad8\u5ea6","flash.upload":"\u4e0a\u4f20","flash.viewServer":"\u6587\u4ef6\u7a7a\u95f4","media.url":"URL","media.width":"\u5bbd\u5ea6","media.height":"\u9ad8\u5ea6","media.autostart":"\u81ea\u52a8\u64ad\u653e","media.upload":"\u4e0a\u4f20","media.viewServer":"\u6587\u4ef6\u7a7a\u95f4","image.remoteImage":"\u7f51\u7edc\u56fe\u7247","image.localImage":"\u672c\u5730\u4e0a\u4f20","image.remoteUrl":"\u56fe\u7247\u5730\u5740","image.localUrl":"\u4e0a\u4f20\u6587\u4ef6","image.size":"\u56fe\u7247\u5927\u5c0f","image.width":"\u5bbd","image.height":"\u9ad8","image.resetSize":"\u91cd\u7f6e\u5927\u5c0f","image.align":"\u5bf9\u9f50\u65b9\u5f0f","image.defaultAlign":"\u9ed8\u8ba4\u65b9\u5f0f","image.leftAlign":"\u5de6\u5bf9\u9f50","image.rightAlign":"\u53f3\u5bf9\u9f50","image.imgTitle":"\u56fe\u7247\u8bf4\u660e","image.upload":"\u6d4f\u89c8...","image.viewServer":"\u56fe\u7247\u7a7a\u95f4","multiimage.uploadDesc":"\u5141\u8bb8\u7528\u6237\u540c\u65f6\u4e0a\u4f20<%=uploadLimit%>\u5f20\u56fe\u7247\uff0c\u5355\u5f20\u56fe\u7247\u5bb9\u91cf\u4e0d\u8d85\u8fc7<%=sizeLimit%>","multiimage.startUpload":"\u5f00\u59cb\u4e0a\u4f20","multiimage.clearAll":"\u5168\u90e8\u6e05\u7a7a","multiimage.insertAll":"\u5168\u90e8\u63d2\u5165","multiimage.queueLimitExceeded":"\u6587\u4ef6\u6570\u91cf\u8d85\u8fc7\u9650\u5236\u3002","multiimage.fileExceedsSizeLimit":"\u6587\u4ef6\u5927\u5c0f\u8d85\u8fc7\u9650\u5236\u3002","multiimage.zeroByteFile":"\u65e0\u6cd5\u4e0a\u4f20\u7a7a\u6587\u4ef6\u3002","multiimage.invalidFiletype":"\u6587\u4ef6\u7c7b\u578b\u4e0d\u6b63\u786e\u3002","multiimage.unknownError":"\u53d1\u751f\u5f02\u5e38\uff0c\u65e0\u6cd5\u4e0a\u4f20\u3002","multiimage.pending":"\u7b49\u5f85\u4e0a\u4f20","multiimage.uploadError":"\u4e0a\u4f20\u5931\u8d25","filemanager.emptyFolder":"\u7a7a\u6587\u4ef6\u5939","filemanager.moveup":"\u79fb\u5230\u4e0a\u4e00\u7ea7\u6587\u4ef6\u5939","filemanager.viewType":"\u663e\u793a\u65b9\u5f0f\uff1a","filemanager.viewImage":"\u7f29\u7565\u56fe","filemanager.listImage":"\u8be6\u7ec6\u4fe1\u606f","filemanager.orderType":"\u6392\u5e8f\u65b9\u5f0f\uff1a","filemanager.fileName":"\u540d\u79f0","filemanager.fileSize":"\u5927\u5c0f","filemanager.fileType":"\u7c7b\u578b","insertfile.url":"URL","insertfile.title":"\u6587\u4ef6\u8bf4\u660e","insertfile.upload":"\u4e0a\u4f20","insertfile.viewServer":"\u6587\u4ef6\u7a7a\u95f4","table.cells":"\u5355\u5143\u683c\u6570","table.rows":"\u884c\u6570","table.cols":"\u5217\u6570","table.size":"\u5927\u5c0f","table.width":"\u5bbd\u5ea6","table.height":"\u9ad8\u5ea6","table.percent":"%","table.px":"px","table.space":"\u8fb9\u8ddd\u95f4\u8ddd","table.padding":"\u8fb9\u8ddd","table.spacing":"\u95f4\u8ddd","table.align":"\u5bf9\u9f50\u65b9\u5f0f","table.textAlign":"\u6c34\u5e73\u5bf9\u9f50","table.verticalAlign":"\u5782\u76f4\u5bf9\u9f50","table.alignDefault":"\u9ed8\u8ba4","table.alignLeft":"\u5de6\u5bf9\u9f50","table.alignCenter":"\u5c45\u4e2d","table.alignRight":"\u53f3\u5bf9\u9f50","table.alignTop":"\u9876\u90e8","table.alignMiddle":"\u4e2d\u90e8","table.alignBottom":"\u5e95\u90e8","table.alignBaseline":"\u57fa\u7ebf","table.border":"\u8fb9\u6846","table.borderWidth":"\u8fb9\u6846","table.borderColor":"\u989c\u8272","table.backgroundColor":"\u80cc\u666f\u989c\u8272","map.address":"\u5730\u5740: ","map.search":"\u641c\u7d22","baidumap.address":"\u5730\u5740: ","baidumap.search":"\u641c\u7d22","anchor.name":"\u951a\u70b9\u540d\u79f0","formatblock.formatBlock":{h1:"\u6807\u9898 1",h2:"\u6807\u9898 2",h3:"\u6807\u9898 3",h4:"\u6807\u9898 4",p:"\u6b63 \u6587"},"fontname.fontName":{SimSun:"\u5b8b\u4f53",NSimSun:"\u65b0\u5b8b\u4f53",FangSong_GB2312:"\u4eff\u5b8b_GB2312",KaiTi_GB2312:"\u6977\u4f53_GB2312",SimHei:"\u9ed1\u4f53","Microsoft YaHei":"\u5fae\u8f6f\u96c5\u9ed1",Arial:"Arial","Arial Black":"Arial Black","Times New Roman":"Times New Roman","Courier New":"Courier New",Tahoma:"Tahoma",Verdana:"Verdana"},"lineheight.lineHeight":[{1:"\u5355\u500d\u884c\u8ddd"},{1.5:"1.5\u500d\u884c\u8ddd"},{2:"2\u500d\u884c\u8ddd"},{2.5:"2.5\u500d\u884c\u8ddd"},{3:"3\u500d\u884c\u8ddd"}],"template.selectTemplate":"\u53ef\u9009\u6a21\u677f","template.replaceContent":"\u66ff\u6362\u5f53\u524d\u5185\u5bb9","template.fileList":{"1.html":"\u56fe\u7247\u548c\u6587\u5b57","2.html":"\u8868\u683c","3.html":"\u9879\u76ee\u7f16\u53f7"}};
