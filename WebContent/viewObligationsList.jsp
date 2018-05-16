<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Obligation Request List</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">
<link rel="stylesheet" type="text/css" href="css/calendarmodal.css" />
<link rel="stylesheet" type="text/css" href="css/navstyle.css" />
<link href='css/timeentry/fullcalendar.css' rel='stylesheet' />
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<link href='css/timeentry/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='js/common.js'></script>

<script src='js/timeentry/lib/moment.min.js'></script>
<script src='js/timeentry/lib/jquery.min.js'></script>
<script src='js/timeentry/fullcalendar.min.js'></script>
<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>

<script src="js/employee.js"></script>



<link rel="stylesheet" type="text/css" href="css/styleTables.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/dai.css" />
<link rel="stylesheet" href="css/datePickerStyle.css">

<script type="text/javascript" src="js/common.js"></script>
<!-- Tabs -->
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<script type="text/javascript" src="js/jquery-ui-1.10.0.min.js"></script>
<script src="js/jquery-1.10.2.js"></script>
<script src="js/common.js"></script>
<script src="js/employee.js"></script>
<script src="js/jquery-ui-1.11.4/jquery-ui.js"></script>

<!-- Pagination -->
<link rel="stylesheet" type="text/css" href="css/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/jqpagination.css" />

<!-- scripts Pagination -->
<script src="js/jquery.jqpagination.js"></script>

<script>

	$(document).ready(function() {
		
		
		var oAjaxCall = $.ajax({
			type : "POST",
			url : "GetObligationRequestListAction",
			cache : false,
			async : false,
			dataType : "json",
			success : function(data) {
				var items = "<table id='box-table-a' summary='Employee Pay Sheet' width='820px;'>";
				items += "<thead>";
				items += "<tr>";
				items += "<th scope='col'>Obligation Request Date</th>";
				items += "<th scope='col'>Payee</th>";
				items += "<th scope='col'>Particulars</th>";
				items += "<th scope='col'>Amount Due</th>";
				items += "<th scope='col'>&nbsp;</th>";
				items += "</tr>";
				items += "</thead>";
				items += "<tbody>";
				
				if (data.Records.length) {
					$.each(data.Records, function(i, item) {
						items += "<tr class='resultItem'>";
						items += "<td>" + item.obligationDate + "</td>";
						items += "<td>" + item.payee + "</td>";
						items += "<td>" + item.particulars + "</td>";
						items += "<td>" + item.totalAmount + "</td>";						
						items += "<td><img src='images/edit_blue.png' alt='' width='30px' height='30px' onclick='editObligationRequest(" + item.obligationRequestId + ");' /></td>";
						
						items += "</tr>";
					});				
				}
				;
				
				items += "</tbody>";			
				items += "</table>";
				
				$('#empSchedDetailsTable').html(items);

			},
			error : function(data) {
				alert('error: ' + data);
			}

		})
		
		
		var items = $(".resultItem");

	    var numItems = items.length;
	    var perPage = 10;
	    
	    var numPages = numItems;
	    var remainingItems = 0;
	    
	    if(numItems < perPage){
	    	numPages = 1;
	    } else {    	
	    	remainingItems = numItems % perPage;
	    	
	    	if(remainingItems > 0){
	    		numPages = ((numItems - remainingItems) / perPage) + 1;	
	    	} else {
	    		numPages = (numItems - remainingItems) / perPage;
	    	}
	    	
	    }    
	    
	    // only show the first 2 (or "first per_page") items initially
	    items.slice(perPage).hide();

	    // now setup your pagination
	    // you need that .pagination-page div before/after your table
	   $('.pagination').jqPagination({        
			max_page	: numPages,
			paged		: function(page) {			
				var showFrom = perPage * (page - 1);
	            var showTo = showFrom + perPage;

	            items.hide() // first hide everything, then show for the new page
	            .slice(showFrom, showTo).show();
			}        
	    });
		

		//if close button is clicked
		$('.window .close').click(function(e) {
			//Cancel the link behavior
			e.preventDefault();

			$('#mask').hide();
			$('.window').hide();
		});

		//if mask is clicked
		$('#mask').click(function() {
			$(this).hide();
			$('.window').hide();
		});

		$(window).resize(function() {

			var box = $('#boxes .window');

			//Get the screen height and width
			var maskHeight = $(document).height();
			var maskWidth = $(window).width();

			//Set height and width to mask to fill up the whole screen
			$('#mask').css({
				'width' : maskWidth,
				'height' : maskHeight
			});

			//Get the window height and width
			var winH = $(window).height();
			var winW = $(window).width();

			//Set the popup window to center
			box.css('top', winH / 2 - box.height() / 2);
			box.css('left', winW / 2 - box.width() / 2);
			
			

		});
		
	});
	
	
	function editObligationRequest(obligationRequestId) {
		window.location = "obligationRequestEdit.jsp?obligationRequestId=" + obligationRequestId;		
	}
	
	
	
	function setDialogWindow(){
		var id = "#dialog";			
		
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();

		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({
			'width' : maskWidth,
			'height' : maskHeight
		});

		//transition effect		
		$('#mask').fadeIn(1000);
		$('#mask').fadeTo("slow", 0.8);

		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();

		//Set the popup window to center
		$(id).css('top', winH / 2 - $(id).height() / 2);
		$(id).css('left', winW / 2 - $(id).width() / 2);

		//transition effect
		$(id).fadeIn(2000);
	}
	
	
	

</script>


</head>
<body>
	<div>
		<c:import url="header.jsp" />
	</div>
	<input type="hidden" name="empIdLoggedIn" id="empIdLoggedIn" value="${employeeLoggedIn.empId}" />
	
	<div id="content">
		<div style="width: 820px; margin: 0 auto;">
				<div id="clockEntryArea">					
					<div class="headerDashboardTable" style="float: left;">					
						Disbursement Voucher List
						<input id="addEmployeeButton" value="Add Obligation Request" border="0" onclick="javascript:window.location='obligationRequestEntry.jsp';">					
						
					</div>
					<div class="pagination" style="float: left; margin-top: 25px; margin-left: 10px;">
				    		<a href="#" class="first" data-action="first">&laquo;</a>
				    		<a href="#" class="previous" data-action="previous">&lsaquo;</a>
				    		<input type="text" readonly="readonly" />
				    		<a href="#" class="next" data-action="next">&rsaquo;</a>
				    		<a href="#" class="last" data-action="last">&raquo;</a>
					</div>	
					<div id="empSchedDetailsTable"></div>				
				</div>
			</div>
	</div>
	<div>
		<c:import url="footer.jsp" />
	</div>
</body>
</html>