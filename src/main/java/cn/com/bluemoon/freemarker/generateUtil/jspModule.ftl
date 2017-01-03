<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>${jspTitle}</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<%@include file="/nui/common.jsp"%>
</head>
<body style="overflow:hidden;width:100%;">
	<div class="search-condition">
		<fieldset style="text-align:center;">
			<legend><label><font style="color:#3366CC" size="2">查询条件</font></label></legend>
			<div id="form1">
          		<!-- 数据实体的名称 -->
				<input class="nui-hidden" name="criteria._entity" value="${entityName}">
          		<!-- 排序字段 -->
	          	<!-- 排序字段 -->
	          	<!--
		          	<input class="nui-hidden" name="criteria._orderby[0]._property" value="pushTime">
		       	 	<input class="nui-hidden" name="criteria._orderby[0]._sort" value="desc">
	       	 	-->
	       	 	
				<table style="width:100%;height:100%;padding:0px!important;line-height:25px;border-collapse: collapse;border:none;padding-left:5px;">
					<#-- 
						默认每一行排列的字段是四个，然后根据字段类型来判断该字段是通过什么组件来进行显示（日期、数据字典...）
					 -->
					<tr style="background: none repeat scroll 0 0 #F8F8F8;">
						<th style="width:8%;text-align:right;">推送日期：</th>
						<td style="text-align:left;width:25%;" colspan="2">
							<input class="nui-hidden" name="criteria._expr[0]._property" value="pushTime"/>
			                <input class="nui-hidden" name="criteria._expr[0]._op" value="between"/>
			                <input id="startDate1" class="nui-datepickerx " name="criteria._expr[0]._min"  style="width: 110px"/> - 
			                <input id="endDate1" class="nui-datepickerx" name="criteria._expr[0]._max"  style="width: 110px"/>
						</td>
						</td>
						<th style="text-align:right;width:8%;" class="fontStyle">推送状态：</th> 
						<td style="width:12%;text-align:left;">
							<input  name="criteria._expr[1]._value" id="msgStatus" showNullItem="true" emptyText="---请选择---" nullItemText="---请选择---" 
								class="nui-dictcombobox" dictTypeId="MM_MSG_STATUS" textField="dictName" valueField="dictID"/>
							<input class="nui-hidden" name="criteria._expr[1]._op" value="="/>
							<input class="nui-hidden" name="criteria._expr[1]._property" value="msgStatus" >
						</td>
						<th style="text-align:right;width:8%;" class="fontStyle">推送范围：</th> 
						<td style="width:12%;text-align:left;">
							<input  name="criteria._expr[2]._value" id="msgRange" showNullItem="true" emptyText="---请选择---" nullItemText="---请选择---" 
								class="nui-dictcombobox" dictTypeId="PUSH_RANGE" textField="dictName" valueField="dictID"/>
							<input class="nui-hidden" name="criteria._expr[2]._op" value="="/>
							<input class="nui-hidden" name="criteria._expr[2]._property" value="msgRange" >
						</td>
						<th style="text-align:right;width:8%;" class="fontStyle" valign="top">推送方式：</th> 
						<td style="width:12%;text-align:left;">
							<input class="nui-dictcheckboxgroup" dictTypeId="PUSH_TYPE" id="pushStyle" width="190px"/>
							<!-- <input class="nui-checkbox" style="float: left" name="criteria._expr[3]._value" dictTypeId="MM_APP_MOBILE" textField="dictName"
							 valueField="dictID" id="pushApp" valign="middel" text="App内部推送"/>
							<input class="nui-hidden" name="criteria._expr[3]._op" value="in"/>
							<input class="nui-hidden" name="criteria._expr[3]._property" value="pushApp" >
							<input class="nui-checkbox" style="float: left" name="criteria._expr[4]._value" dictTypeId="MM_APP_MOBILE" textField="dictName"
							 valueField="dictID" id="pushMobile" valign="middel" text="手机消息推送"/>
							<input class="nui-hidden" name="criteria._expr[4]._op" value="in"/>
							<input class="nui-hidden" name="criteria._expr[4]._property" value="pushMobile" > -->
						</td>
					</tr>
					<tr style="background: none repeat scroll 0 0 #F8F8F8;">
						<th style="width:8%;text-align:right;">创建日期：</th>
						<td style="text-align:left;width:25%;" colspan="2">
							<input class="nui-hidden" name="criteria._expr[3]._property" value="createTime"/>
			                <input class="nui-hidden" name="criteria._expr[3]._op" value="between"/>
			                <input id="startDate2" class="nui-datepickerx " name="criteria._expr[3]._min"  style="width: 110px"/> - 
			                <input id="endDate2" class="nui-datepickerx" name="criteria._expr[3]._max"  style="width: 110px"/>
						</td>
						</td>
						<th style="text-align:right;width:8%;" class="fontStyle">创建人编码：</th> 
						<td style="width:12%;text-align:left;">
							<input class="nui-hidden" name="criteria._expr[4]._property" value="createCode"/>
							<input class="nui-hidden" name="criteria._expr[4]._op" value="like"/>
							<input class="nui-hidden" name="criteria._expr[4]._likeRule" value="all"/>
							<input class="nui-textbox" name="criteria._expr[4]._value"/>
						</td>
						<th style="text-align:right;width:8%;" class="fontStyle">创建人姓名：</th> 
						<td style="width:12%;text-align:left;">
							<input class="nui-hidden" name="criteria._expr[5]._property" value="createName"/>
							<input class="nui-hidden" name="criteria._expr[5]._op" value="like"/>
							<input class="nui-hidden" name="criteria._expr[5]._likeRule" value="all"/>
							<input class="nui-textbox" name="criteria._expr[5]._value"/>
						</td>
						<td style="width:28%;text-align:center;" colspan="2">
							<a class="nui-button" onclick="search()" style="margin-right:20px;" iconCls="icon-search"> 查询  </a>
							<a class="nui-button" onclick="reset()" iconCls="icon-reset"> 重置 </a>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>
	
	<div style="width:100%;">
		<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" id="addMsg" name="addMsg" onclick="addMsg();">新增消息</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-edit" id="editMsg" name="editMsg" onclick="editMsg();">编辑消息</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-edit" id="cancelMsg" name="cancelMsg" onclick="cancelMsg();">取消发送</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-user" id="closeMsg" name="closeMsg" onclick="closeMsg();">归档</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-user" id="showMsg" name="showMsg" onclick="showMsg();">查看</a>
                    </td>
                </tr>
            </table>           
        </div>
	</div>
	
	<div  class="nui-fit">
		<div id="datagrid1" class="nui-datagrid" style="width:100%;height:100%;" url="<%=request.getContextPath() %>/criteria/query" dataField="criteria"
	        allowResize="true" pageSize="15"  multiSelect="false" showPager="true" allowCellValid="true">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div type="indexcolumn" align="center"  headerAlign="center">序号</div>
	            <div field="msgId" name="msgId" visible="false" align="center" headerAlign="center">消息id</div>
	            <div field="pushTime" align="center" width="110px" headerAlign="center" renderer="getDict">推送时间</div>
	            <div field="msgStatus" align="center" width="60px" headerAlign="center" renderer="gridBmwzDict">推送状态</div>
	            <div field="pushApp" align="center" width="70px" headerAlign="center" renderer="gridBmwzDict">App内部推送</div>
	            <div field="pushMobile" align="center" width="70px" headerAlign="center" renderer="gridBmwzDict">手机消息推送</div>
	            <div field="msgRange" align="center" width="110px" headerAlign="center" renderer="gridBmwzDict">推送范围</div>
	            <div field="msgTotal" align="center" width="80px" headerAlign="center">消息总人数</div>
	            <div field="msgContent" align="center" width="200px" headerAlign="center">消息内容</div>
	            <div field="createCode" align="center" width="80px" headerAlign="center">创建人编码</div>
	            <div field="createName" align="center" width="80px" headerAlign="center">创建人姓名</div>
	            <div field="createTime" align="center" width="120px" headerAlign="center" renderer="getDict">创建时间</div>
	        </div>
	    </div>
	</div>
	
<script type="text/javascript">
	nui.parse();
	mini.payload(true);
	var grid = nui.get("datagrid1");
	var form = new nui.Form("form1");
	var right = 0;
	var pid = "";// 级联父ID
	var type = "";
	/**数据初始化*/
	search();
	function search(){
		var data = form.getData(true,true);
		
		var endDate1 = nui.get("endDate1").getValue();
		var stratDate1 = nui.get("startDate1").getValue();
		var endDate2 = nui.get("endDate2").getValue();
		var stratDate2 = nui.get("startDate2").getValue();
		if(stratDate1 != null && stratDate1 != ""){
			stratDate1 = stratDate1.substring(0,10);
			stratDate1 = stratDate1 + " 00:00";
			data.criteria._expr[0]._min=stratDate1;
		}
		if(endDate1 != null && endDate1 != ""){
			endDate1 = endDate1.substring(0,10);
			endDate1 = endDate1 + " 23:59";
			data.criteria._expr[0]._max=endDate1;
		}
		if(stratDate2 != null && stratDate2 != ""){
			stratDate2 = stratDate2.substring(0,10);
			stratDate2 = stratDate2 + " 00:00";
			data.criteria._expr[3]._min=stratDate2;
		}
		if(endDate2 != null && endDate2 != ""){
			endDate2 = endDate2.substring(0,10);
			endDate2 = endDate2 + " 23:59";
			data.criteria._expr[3]._max=endDate2;
		}
		//动态给推送方式赋值
		var criteria = data.criteria;
		var pushStyle=nui.get("pushStyle").getValue();
		if(pushStyle != null &&　pushStyle != ""){
			if(pushStyle == "app" ||  pushStyle.indexOf("app") >= 0){
				var firText1 = {};
				firText1["_property"] = "pushApp";
				firText1["_op"] = "=";
				firText1["_value"] = "1";
				criteria._expr.push(firText1);
			}
			if( pushStyle == "mobile" || pushStyle.indexOf("mobile") >= 0 ){
				var firText2 = {};
				firText2["_property"] = "pushMobile";
				firText2["_op"] = "=";
				firText2["_value"] = "1";
				criteria._expr.push(firText2);
			}
		}
		grid.load({criteria:criteria});
	} 
	//时间显示
	function getDict(e){
		var field = e.field;
		if(e.value != null && e.value != ""){
			var value = e.value.time;
			var dictText = "";
			if(field == "pushTime"){
				 var unixTimestamp = new Date(value);
				 dictText = nui.formatDate(unixTimestamp,"yyyy-MM-dd HH:mm");
			}
			if(field == "createTime"){
				 var unixTimestamp = new Date(value);
				 dictText = nui.formatDate(unixTimestamp,"yyyy-MM-dd HH:mm:ss");
			}
			return dictText;
		}
	}
	//业务字典显示中文名的方法
	function gridBmwzDict(e){
		var dictText="";
		if(this.field=="msgStatus"){
			dictText = nui.getDictText("MM_MSG_STATUS", e.row.msgStatus);
		}else if(this.field=="msgRange"){
			dictText = nui.getDictText("PUSH_RANGE", e.row.msgRange);
		}else if(this.field=="pushApp"){
			dictText = nui.getDictText("MM_APP_MOBILE", e.row.pushApp);
		}else if(this.field=="pushMobile"){
			dictText = nui.getDictText("MM_APP_MOBILE", e.row.pushMobile);
		}
		return dictText;
	}
	
	//新增消息
	function addMsg(){
		nui.open({
            url: "<%=request.getContextPath() %>/mallcrm/msg/addMsg.jsp",
            showMaxButton: false,
            title: "新增消息",
            width: 1105,
            height: 620,
            onload:function(){
			},
			ondestroy:function(action){
				if(action=="success"){
		            search();
		        }
			}
        });
	}
	
	
	//编辑消息
	function editMsg(){
		var rows = grid.getSelecteds();
		for(var i=0;i<rows.length;i++){
			if(rows[i].msgStatus == "already"){
				nui.alert("已发送的消息不能编辑","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "cancel"){
				nui.alert("已取消的消息不能编辑","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "close"){
				nui.alert("已归档的消息不能编辑","温馨提示");
				return;
			}
		}
		if ( rows.length == 0 ){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}else if(rows.length > 1){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}
		nui.open({
            url: "<%=request.getContextPath() %>/mallcrm/msg/editMsg.jsp",
            showMaxButton: false,
            title: "编辑消息",
            width: 1105,
            height: 620,
            onload:function(){
				var iframe = this.getIFrameEl();
				var data = {msgId:rows[0].msgId,type:"edit"};
				iframe.contentWindow.SetData(data);
			},
			ondestroy:function(action){
				if(action=="success"){
		            search();
		        }
			}
        });
	}
	//取消发送消息
	function cancelMsg(){
		var rows = grid.getSelecteds();
		for(var i=0;i<rows.length;i++){
			if(rows[i].msgStatus == "already"){
				nui.alert("已发送的消息不能取消发送","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "cancel"){
				nui.alert("已取消的消息不能取消发送","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "close"){
				nui.alert("已归档的消息不能取消发送","温馨提示");
				return;
			}
		}
		if ( rows.length == 0 ){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}else if(rows.length > 1){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}
		var list = [];
		for(var i=0;i<rows.length;i++){
			list.push({msgId:rows[i].msgId});
		}
		var json = nui.encode({data:list,"type":"cancel"});
		nui.confirm("取消发送后，不可再编辑和发送该消息，是否取消？","温馨提示",function(action){
			if(action == "ok"){
			var messagebox = nui.loading("数据修改中，请稍候……", "温馨提示");
			$.ajax({
				url:"<%=request.getContextPath() %>/message/operateMessage",
				type:'POST',
				data:json,
				cache:false,
				async:false,
				contentType:'application/json',
				success:function(text){
					var result = text.isSuccess;
					if(result == true){
						search();
						nui.alert("取消成功！","温馨提示");
					}else{
						nui.alert("消息取消发送失败","温馨提示");
					}
				}
			});
			mini.hideMessageBox(messagebox);
			}
		});
	}
	//查看消息
	function showMsg(){
		var rows = grid.getSelecteds();
		var data = {Msg:rows};
		var json = nui.encode(data);
		if ( rows.length == 0 ){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}else if(rows.length > 1){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}
		
		nui.open({
            url: "<%=request.getContextPath() %>/mallcrm/msg/showMsg.jsp",
            showMaxButton: false,
            title: "查看消息",
            width: 1105,
            height: 620,
            onload:function(){
            	var iframe = this.getIFrameEl();
				var data = {msgId:rows[0].msgId,type:"show"};
				iframe.contentWindow.SetData(data);
			},
			ondestroy:function(action){
				if(action=="success"){
		            search();
		        }
			}
        });
	}
	//归档消息
	function closeMsg(){
		var rows = grid.getSelecteds();
		for(var i=0;i<rows.length;i++){
			if(rows[i].msgStatus == "wait"){
				nui.alert("待发送的消息不能归档","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "cancel"){
				nui.alert("已取消的消息不能归档","温馨提示");
				return;
			}
			if(rows[i].msgStatus == "close"){
				nui.alert("该消息已归档","温馨提示");
				return;
			}
		}
		if ( rows.length == 0 ){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}else if(rows.length > 1){
			nui.alert("请选择一行记录","温馨提示");
			return;
		}
		var list = [];
		for(var i=0;i<rows.length;i++){
			list.push({msgId:rows[i].msgId});
		}
		var json = nui.encode({data:list,"type":"close"});
		nui.confirm("确定归档该消息？","温馨提示",function(action){
			if(action == "ok"){
			var messagebox = nui.loading("数据修改中，请稍候……", "温馨提示");
			$.ajax({
				url:"<%=request.getContextPath() %>/message/operateMessage",
				type:'POST',
				data:json,
				cache:false,
				async:false,
				contentType:'application/json',
				success:function(text){
					var result = text.isSuccess;
					if(result == true){
						search();
						nui.alert("归档成功！","温馨提示");
					}else{
						nui.alert("消息归档失败","温馨提示");
					}
				}
			});
			mini.hideMessageBox(messagebox);
			}
		});
		
	}
	function reset(){
		form.reset();
	}
</script>
	
</body>
</html>