<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() +
"://"
+ request.getServerName() + ":"
+ 	request.getServerPort()
+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function (){
			if(window.top!=window){
				window.top.location=window.location;
			}
			$("#loginAct").val("");
			$("#loginPwd").val("");
			$("#loginAct").focus();

			//定义按键事件
			$("#submitBtn").click(function (){
				login();
			})
		//	为当前登录窗口绑定敲键盘事件
			$(window).keydown(function (event){
				if(event.keyCode==13){
					login();
				}
			})
			$("#createBtn").click(function () {
				$("#createUserModal").modal("show");
			})
			$("#saveBtn").click(function (){
				$.ajax({
					url: "user/add.do",
					data: {
						"loginAct":$.trim($("#create-loginact").val()),
						"name":$.trim($("#create-name").val()),
						"email":$.trim($("#create-email").val()),
						"loginPwd":$.trim($("#create-loginPwd").val()),
						"deptno":$.trim($("#create-deptno").val()),
					},
					type: "post",
					success:function (data){
						if (data.success==true)
						{
							$("#userAddForm")[0].reset();
							$("#createUserModal").modal("hide");
							alert("创建成功");

						}
						else {
							alert(data.msg);
						}
					}
				})
			})
		})
		function login(){
			// alert("执行登录操作123");
		//	验证账号密码不能为空,将左右空格去掉
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());

			if(loginAct =="" ||loginPwd ==""){
				$("#msg").html("您的账号密码为空");
			//	如果账号密码为空，则需要强制终止该方法
				return false;
			}
			$.ajax({
				url:"user/login.do",
				data:{
					"loginAct":loginAct,
					"loginPwd":loginPwd
				},
				dataType:"json",
				type:"post",
				success:function (data){
				//	返回登录成功或者失败
					if(data.success){
					//	跳转到工作台初始页
						window.location.href="workbench/index.jsp";
					}
					else{
						$("#msg").html(data.msg);
					}
				}
			})
		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;by-Xp</span></div>
	</div>
	<div class="modal fade" id="createUserModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建员工账号</h4>
				</div>
				<div class="modal-body">

					<form id="userAddForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-name" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-name">
							</div>
							<label for="create-loginact" class="col-sm-2 control-label">登录名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-loginact">
							</div>
						</div>

						<div class="form-group">
							<label for="create-loginPwd" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-loginPwd">
							</div>
							<label for="create-email" class="col-sm-2 control-label ">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-email">
							</div>
						</div>
						<div class="form-group">

							<label for="create-cost" class="col-sm-2 control-label">部门</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-deptno">
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">创建</button>
				</div>
			</div>
		</div>
	</div>


	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="#" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
<%--					按钮写在表单中 默认的行为就是提交表单
                        一定要将按钮的类型设置为button
                        按钮触发的行为应该是由我们自己定义--%>
					<button type="button" id="submitBtn"  class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
					<button type="button" id="createBtn"  class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">创建账号</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>