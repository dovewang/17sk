<h2 class="box-title"><a href="/search/show.html"  class="float-right">更多&gt;</a>I讲台</h2>'
<#if (result?size>0) >
    <#assign n1=result[0] />
         <div class="n1"> 
           <a href="/show/item-${n1.showId}.html" target="_blank" ><img src="${n1.cover}" width="200" height="170"></a>
            <div class="title">
               <h3>${n1.title}</h3>
               <div>分享人：<a href="/u/${n1.userId}.html" usercard="${n1.userId}" title="" class="user">${n1.userId}</a></div>
               <div><span><i class="icon-views"></i>${n1.views}</span>
                <span><i class="icon-comments"></i>${n1.comments}</span>
               <span><i class="icon-ups"></i>${n1.ups}</span></div>
            </div>
          </div>
     <ul class="show-link-list">
      <#list result as show>
        <#if show_index<3>
       <li><i class="hot1">${show_index+1}</i><a href="/show/item-${show.showId}.html" target="_blank" >${show.title}</a><span><i class="icon-ups"></i>${show.ups}</span></li>
       <#else>
       <li><i class="hot2">${show_index+1}</i><a href="/show/item-${show.showId}.html" target="_blank" >${show.title}</a><span><i class="icon-ups"></i>${show.ups}</span></li>
       </#if>
     </#list>
     </ul>
</#if>