<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Purchase Product" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<%@ page import="edu.byu.isys413.afreh20.mystuff.*" %>
<div class="wrapper">
<%Product p1 = (Product)request.getAttribute("product");%>
	<form method="POST" action="edu.byu.isys413.afreh20.actions.CompletePurchase.action">
		<table>
			<tr>
				<td>
					Name:
				</td>
				<td> <%
					out.print(p1.getName());
					%>
				</td>
			</tr>
			<tr>
				<td>
					Quantity Available:
				</td>
				<td><%
					out.print(request.getAttribute("quantity"));
					%>
				</td>
			</tr>
			<tr>
				<td>
					Quantity Buying:
				</td>
				<td>
					<input id="buying_quant" type="text" class="forminput" name="buying_quant" value="1">
				</td>
			</tr>
			<tr>
				<td>
					Price Per Unit:
				</td>
				<td>$<%
					out.print(p1.getPrice()+"");
					%>
				</td>
			</tr>
			<tr>
				<td>
					Subtotal:
				</td>
				<td id="subtotal">$<%
					out.print(p1.getPrice());
					%>
				</td>
			</tr>
			<tr>
				<td>
					Tax:
				</td>
				<td id="tax">$<%
					out.print(request.getAttribute("tax"));
					%>
				</td>
			</tr>
			<tr>
				<td>
					Total:
				</td>
				<td id="total">$<%
					out.print(request.getAttribute("total"));
					%>
				</td>
			</tr>
			<tr>
				<td>
					Store and shelf location:
				</td>
				<td><%
					out.print(request.getAttribute("storelocation")+" at "+request.getAttribute("location"));
					%>
				</td>
			</tr>
			<tr>
				<td>
					Method:
				</td>
				<td>
					<input type="radio" name="delivery" value="delivery" checked> Delivery 
					<input type="radio" name="delivery" value="pickup"> Pickup
			</tr>
			<tr>
				<td>
					Credit Card:
				</td> 
				<td>
					<input class="forminput" type="text" value="<% out.print(request.getAttribute("creditcard")); %>">
				</td>
			</tr>
			<tr>
				<td>
					Shipping Address:
				</td>
				<td>
					<input class="forminput" type="text" value="<% 
						Customer c1 = (Customer)session.getAttribute("customer");
						out.println(c1.getAddress());
					%>">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button style="float:right;" class="button">Purchase!</button>
				</td>
			</tr>
		</table>
		<input type=hidden name="product_type" value="<%out.print(request.getAttribute("producttype"));%>">
		<input type=hidden name="store_id" value="<%out.print(request.getAttribute("storeid"));%>">
		<input type=hidden id="hiddentax" name="tax" value="<%out.print(request.getAttribute("tax"));%>">
		<input type=hidden id="hiddensubtotal" name="subtotal" value="<%out.print(p1.getPrice());%>">
		<input type=hidden id="hiddentotal" name="total" value="<%out.print(request.getAttribute("total"));%>">
		<input type=hidden name="prodid" value="<%out.print(p1.getId());%>">
	</form>
</div>

<script type="text/javascript">
	$(function(){
		var price = <% out.print(p1.getPrice()); %>;
		var tax = <% out.print(request.getAttribute("taxrate")); %>;
		
		$('#buying_quant').keyup(function(){
			if($('this').val != ""){
				try{
					var quantity = $('#buying_quant').val();
					//console.log(quantity);
					var subtotal = quantity * price;
					var taxtotal = subtotal * tax;
					var total = taxtotal + quantity * price;
					//console.log(taxtotal);
					//console.log(total);
					$('#subtotal').html('$'+subtotal);
					$('#tax').html('$'+taxtotal);
					$('#total').html('$'+total);
					$('#hiddensubtotal').val(subtotal);
					$('#hiddentax').val(taxtotal);
					$('#hiddentotal').val(total);
				}catch(err){
					$('#buying_quant').val("");
				}
			}
			
		});
	});
</script>
<jsp:include page="/footer.jsp"/>