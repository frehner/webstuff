<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Create Account" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<div class="wrapper">
	<h1>Create your MyStuff Account</h1>
	<form method="POST" id="create_form" action="edu.byu.isys413.afreh20.actions.CreateAccount.action">
		<table id="creationtable" style="border:0;">
			<tr>
				<td>
					Email Address
				</td>
				<td>
					<input class="forminput" type="text" name="email1" id="email1">
				</td>
				<td>
					<div id="email1_error" style="display:none; color:red"> Email already in use</div>
				</td>
			</tr>
			
			<tr>
				<td>
					Confirm Email Address
				</td>
				<td>
					<input class="forminput" type="text" name="email2" id="email2">
				</td>
				<td>
					<div id="email2_error" style="display:none; color:red">Emails are not the same</div>
				</td>
			</tr>
			<tr>
				<td>
					First Name
				</td>
				<td>
					<input class="forminput" type="text" name="firstname" id="firstname">
				</td>
				<td>
					<div id="firstname_error" style="display:none; color:red"></div>
				</td>
			</tr>
			<tr>
				<td>
					Last Name
				</td>
				<td>
					<input class="forminput" type="text" name="lastname" id="lastname">
				</td>
				<td>
					<div id="lastname_error" style="display:none; color:red"></div>
				</td>
			</tr>
			<tr>
				<td>
					Address
				</td>
				<td>
					<input class="forminput" type="text" name="address" id="address">
				</td>
				<td>
					<div id="address_error" style="display:none; color:red"></div>
				</td>
			</tr>
			<tr>
				<td>
					Password
				</td>
				<td>
					<input class="forminput" type="password" name="password1" id="password1">
				</td>
				<td>
					<div id="password1_error" style="display:none; color:red"></div>
				</td>
			</tr>
			<tr>
				<td>
					Confirm Password
				</td>
				<td>
					<input class="forminput" type="password" name="password2" id="password2">
				</td>
				<td>
					<div id="password2_error" style="display:none; color:red">Passwords not the same</div>
				</td>
			</tr>
			<tr>
				<td>
					Credit Card Number
				</td>
				<td>
					<input class="forminput" type="text" name="cc1" id="cc1">
				</td>
				<td>
					<div id="cc1_error" style="display:none; color:red"></div>
				</td>
			</tr>
			<tr>
				<td>
					Confirm Credit Card
				</td>
				<td>
					<input class="forminput" type="text" name="cc2" id="cc2">
				</td>
				<td>
					<div id="cc2_error" style="display:none; color:red">CC Numbers are not the same</div>
				</td>
			</tr>
		</table>
		<br>
		<button type="submit" id="create_account" class="button" style="margin-left:7px;">Create Account!</button>
	</form>
	
<script>
	$(function(){
		$('#email2').focusout(function(){
			if($('#email1').val() != $('#email2').val()){
				$('#email2_error').show();
			}else {
				$('#email2_error').hide();
			}
		});
		$('#password2').focusout(function(){
			if($('#password1').val() != $('#password2').val()){
				$('#password2_error').show();
			}else {
				$('#password2_error').hide();
			}
		});
		$('#cc2').focusout(function(){
			if($('#cc1').val() != $('#cc2').val()){
				$('#cc2_error').show();
			}else {
				$('#cc2_error').hide();
			}
		});
	});
</script>
</div>
<jsp:include page="/footer.jsp"/>