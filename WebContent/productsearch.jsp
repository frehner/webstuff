<jsp:include page="/header.jsp">
  <jsp:param name="title" value="Find Products" />
</jsp:include>
<jsp:include page="/navigation.jsp" />
<%@ page import="java.util.LinkedList" %>
<%@ page import="edu.byu.isys413.afreh20.mystuff.Store" %>
<div id="topbar">
	Store:

	<select id="storebox" style="margin-left:.25em; min-width:100px;">
		<%
			LinkedList<Store> storeList = (LinkedList<Store>)request.getAttribute("stores");
			for(Store s: storeList){
				out.println("<option value='"+s.getId()+"'>"+s.getLocation()+"</option>");
			}
		%>
	</select>

	<span style="margin-left:.2em;">
	Product Search:

	<input type="text" id="searchbox" style="margin-left:.25em;min-width:160px;">
	</span>	
	<span style="margin-left:.2em;">
	<!-- <button class="button" id="getpics">Get Photos</button> -->
	</span>
</div>
<form id="purchase_form" type="GET" action="edu.byu.isys413.afreh20.actions.PurchaseProd.action">
	<div id="product_list">
		<div class="prodtitle"> Products </div>
		<div class="product">
			Look at these great products!
		</div>
		<div class="product">
			Look at these great products!
		</div>
		<div class="product">
			Look at these great products!
		</div>
		<div class="product">
			Look at these great products!
		</div>
	</div>
</form>

<!--<form method="GET" action="">
	<div id="product_info">
		  <div class="prodtitle"> Details </div>
		Description:<br>
		<div id="prod_description" class="hideproddetail"></div><br>
		Manufacturer:<br>
		<div id="prod_manufacturer" class="hideproddetail"></div><br>
		Price:<br>
		<div id="prod_price" class="hideproddetail"></div><br>
		Pickup:<br>
		<div id="prod_pickup">
			<input type="radio" name="storedelivery" value="instore" checked> In Store
			<input type="radio" name="storedelivery" value="delivery"> Delivery
		</div><br>
		<button class="button" type="submit">Purchase</button>
	</div>
</form>-->

<script type="text/javascript">
	$(function(){
		$('#searchbox').keyup(function(){
			var inputtext = $(this).val();
			if(inputtext != ""){
				var storeid = $('#storebox').val();
				// console.log(inputtext);
				// console.log(storeid);
				$.ajax({
					type:"POST",
					url:"edu.byu.isys413.afreh20.actions.ProdAjax.action",
					data:{text:inputtext, store:storeid}
				}).done(function(data){
					//console.log('success');
					//console.log(data);
					console.log($.parseJSON(data));
					var htmlstring = "";
					//var parsedjson = $.parseJSON(data);
					var jsonlength = $.parseJSON(data).length;
					//console.log(jsonlength);
					var parsedData = $.parseJSON(data);
					
					for(var ii=0;ii<jsonlength;ii++){
						//console.log(ii);
						htmlstring += '<div class="prodtitle"> Products </div><div class="product"> Product Name: '+parsedData[ii].name+'<br>Product Price: $'+parsedData[ii].price+'<br><button type="button" class="purchasebutton" id="'+parsedData[ii].id+'">Purchase!</button> </div>';
					}
					
					$('#product_list').html(htmlstring);
				}).fail(function(data){
					console.log('failure');
				});
			}
		});
		
		$('body').delegate('.purchasebutton','click', function(){
			var prodid = event.target.id;
			console.log(prodid);
			var hidden = '<input type="hidden" name="prodid" value='+prodid+'>';
			hidden += '<input type="hidden" name="storeid" value="'+$('#storebox').val()+'">';
			console.log(hidden);
			$('#product_list').append(hidden);
			$('#purchase_form').submit();
		});
	});
</script>

<jsp:include page="/footer.jsp"/>