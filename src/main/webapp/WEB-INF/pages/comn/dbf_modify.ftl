<#import "/spring.ftl" as spring />  
<#import "/macro/commonMacros.ftl" as commonMacros />
<#setting classic_compatible=true>
<html>
	<head>
	<meta charset="utf-8"/>
		<title>${curMenu.title }</title>
		<style type="text/css">
		div.datagrid_div{
			border: 1px solid #999;
		}
		table.datagrid{
			border-collapse: separate;
			width: 100%;
		}
		table.datagrid th{
			text-align: left;
			background: #ddd;
			padding: 1px;
		}
		table.datagrid td{
			padding: 2px;
		}
		table.datagrid tr.odd{
		    background: #fff;
		}
		
		table.datagrid tr.even{
		    background: #eee;
		}
		
		table.datagrid tr:hover,
		table.datagrid tr.hover{
		    background: #9cf;
		}
		
		table.datagrid tr.marked{
		    background: #ee9;
		}
		
		.selector_table{
			width: 100%;
		}
		
		.datagrid_meta {
			border: 1px solid #ccc;
		}
		
		.selector_table .src,
		.selector_table .dst{
			width: 50%;
		}
		
		.selector_table table.datagrid tr:hover,
		.selector_table table.datagrid tr.hover{
			cursor: pointer;
		}
		
		.textbox{
			padding: 1px;
			border: 1px solid #bbb;
		}
		
		#sel_div{
			margin: 6px 120px;
			padding: 0px;
			border: 1px solid #999;
			width: 575px;
		}
		
		.TableView .datagrid_meta .filter {
			margin: 0 4px;
			padding: 1px;
			border: 1px solid #ccc;
		}
	</style>
	</head>
	<body>
		<!-- BEGIN PAGE HEADER-->
		<@commonMacros.breadcrumbNavigation />
		<div class="tab-pane active" id="tab_0">
			<div class="portlet box <#if showStyle=="tplread">blue-steel<#else>green-seagreen</#if>">
			
				<div class="portlet-title">
					<div class="caption">
						<span class="caption">${curMenu.title } </span>
					</div>
				</div>
				
				<div class="portlet-body form">
				<!-- BEGIN FORM-->
					<form class="form-horizontal valid_form" id="templateForm" name="templateForm" method="post" action="" onsubmit="">
					<input type="hidden" id="file_format" name="file_format" value="${fileFormat}"/>
					<input type="hidden" name="fileFormat" value="${fileFormat}"/>
					<input type="hidden" name="type" value="${type}"/>
					<input type="hidden" name="id" value="${template.id}"/>
					<div class="form-body">
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2">模板类别：</label>
										<div class="col-md-10">
										<label style="padding-left: 0px;margin-top:8px;">${curMenu.title }</label>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2"><span style="color:red">*&nbsp;</span>模板名称：</label>
											<div class="col-md-10">
												<@commonMacros.multiInput "add" "title" template.title "required illchar" "rangelength=2,16 " />
											</div>
										</div>
									</div>
							</div>
							
							<#if columnCommentTarget??>
									<#if (columnCommentTarget?size >0)>
									<script>
							   			var dataList = new Array();
							   		</script>
										<#list columnCommentTarget as columnCommentMap>
												<#list columnCommentMap?keys as key>
									    			<script>
														var obj = {colFileName : '${columnCommentMap[key]}',colDbName:'${key}'};
														dataList.push(obj);
													</script>
										    	</#list>
								    	</#list>
						    			
								    </#if>	
							</#if>
							
							<#if linkedMapList??>
									<#if (linkedMapList?size >0)>
									<script>
							   			var dataDstList = new Array();
							   		</script>
										<#list linkedMapList as linkedMap>
										<#if linkedMap??>
											
									    		<script>
													var objDst = {colFileName : '${linkedMap.showName}',colDbName:'${linkedMap.detail}',dbfFileName:'${linkedMap.colFileName}'};
													dataDstList.push(objDst);
												</script>
									    	
							    		</#if>
							    		</#list>
									</#if>
								</#if>
		                    <div id="sel_div"></div>
										
									
							<div class="row">
								<div class="col-md-8">
										<div class="form-group">
											<label class="control-label col-md-2">模板描述：</label>
											<div class="col-md-10">
												<@commonMacros.multiTextarea "add" "description" template.description "illchar" 'rangelength="0,1024"' />
											</div>
										</div>
									</div>
							</div>
							
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<a class="btn green-seagreen" href="javascript:;" onclick="saveDbfExportTemplate('${template.id}');">保存</a>
											<a href="${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}" class="btn default btn_cancel">取消</a>
										</div>
									</div>
								</div>
						</div>		
					</form>
					<!-- END FORM-->
				</div>
			</div>
		</div>
		<jsplugininner>
			<script type="text/javascript" src="${contextPath}/resources/js/jqueryTableView/TableView.js"></script>
			<script type="text/javascript" src="${contextPath}/resources/js/jqueryTableView/SelectorView.js"></script>
		</jsplugininner>
		<jsinner>
			<script>
				$(document).ready(function() {
					var sel = new SelectorView('sel_div'); // sel_div 是 HTML 节点 id
					sel.src.header = {
						id:'id',
						colFileName	: '导出字段'
					};
					sel.dst.header = {
						id:'id',
						colFileName	: '导出字段',
						input		: 'dbf字段名',
						hidden2   :'  '
					};
					sel.src.dataKey = 'id';
					sel.src.title = '可选';
					sel.src.display.filter = false;
					sel.dst.dataKey = 'id';
					sel.dst.title = '已选';
				//	var input_html = '<input type="text" name="dbfFileName" size="10" class="textbox required illchar" rangelength="2,10" />';
					// Therefore, convert it to a real array
					var realArray = $.makeArray( dataList );
					var realArrayLength = realArray.length+1;
					 
					// Now it can be used reliably with $.map()
					$.map( realArray, function( val, i ) {
						var input_html = '<span style="color:red">*&nbsp;</span><input type="text" id="' + i +'" name="dbfFileName" size="10" class="textbox required nameFormat" rangelength="2,10" />';
					 	var hidden2_value = val.colDbName;
						var input_html2 = "<input type='hidden' name='hidden2Value' size='10'  class='textbox' value='" + hidden2_value +"'/>";
						sel.src.add({id:i+1, colFileName: val.colFileName,input: input_html,hidden2:input_html2});
					});
					
					//给右边的MultiSelect 赋值
					for(var i = 0 ; i < dataDstList.length ; i++){
						var hidden2_value = dataDstList[i].colDbName;
						var input_html2 = "<input type='hidden' name='hidden2Value' size='10'  class='textbox' value='" + hidden2_value +"'/>";
						var input_html = "<span style='color:red'>*&nbsp;</span><input type='text' id='" + i + "' name='dbfFileName' size='10'   class='textbox required nameFormat' rangelength='2,10' value='" + dataDstList[i].dbfFileName +"'/>";
						sel.dst.add({id:i+realArrayLength, colFileName: dataDstList[i].colFileName,input: input_html,hidden2:input_html2});
					} 
					sel.render();
				});//ready()
				
				
		
	
				function saveDbfExportTemplate(id){
				
							if ((!$("input[name='dbfFileName']").valid()) || (!$("input[name='title']").valid())){
								return;
							}
							var desc = document.getElementsByTagName("textarea").description.value;
							var dbfFileNameArr  = $("input[name='dbfFileName']");
				            var dbfFileNameData=new Array();  
							
							$(dbfFileNameArr).each(function(index,value){  
							    dbfFileNameData.push(value.value);  
							}); 
				            
				            var hidden2ValueArr = $("input[name='hidden2Value']");  
							var info = "";
							$(hidden2ValueArr).each(function(index,item){  
							     // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
     							info = (info + item.value) + (((index + 1)== hidden2ValueArr.length) ? '':';'); 
     
							}); 
						   var param = {
								dbfFileName:dbfFileNameData.toString(),
								info:info,
								id:$("input[name='id']").val(),
								file_format:$("#file_format").val(),
								fileFormat:$("#file_format").val(),
								type:$("input[name='type']").val(),
								title:$("input[name='title']").val(),
								description:desc
						        };
						   
						  var url = "${contextPath}/${curMenu.pUrl}/" + id + "/tplsave?module=${curMenu.rule}&pUrl=${curMenu.pUrl}";
						  jQuery.ajax({
								url : url,
								method: "POST",
								async:false, 
								cache:false, 
					    		dataType : "json",
					    		data :param,
					    		success : function(data,textStatus,jqXHR ) {
					    		},
								error : function( jqXHR,  textStatus,  errorThrown) {
								
					    		}
					    	});	
			
			 		window.location.href = "${contextPath}/${curMenu.pUrl}/all/tpllist?module=${curMenu.rule}&pUrl=${curMenu.pUrl}";
			 
				}
		
		
			</script>
		</jsinner>


	</body>
</html>
