function populateReasonDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllReasonAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Reason</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.reasonId + "'>"
							+ item.reasonDesc + "</option>";
				});
				$('#reasonDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateBranchTransmittalDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllBranchAction?forTransmittal=Y",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Branch</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.branchId + "'>"
							+ item.branchName + "</option>";
				});
				$('#branchDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateBranchDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllBranchAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Branch</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.branchId + "'>"
							+ item.branchName + "</option>";
				});
				$('#branchDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateLBCBranchDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllLBCBranchAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Branch</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.branchId + "'>"
							+ item.branchName + "</option>";
				});
				$('#branchDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateBaranggayDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllBaranggayAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Baranggay</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.brgyId + "'>"
							+ item.brgyName + "</option>";
				});
				$('#brgyDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateCityDropDownByProvId(provId) {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllCityAction?provId="+provId,
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select City</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.cityId + "'>"
							+ item.cityName + "</option>";
				});
				$('#cityDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateCityDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllCityAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select City</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.cityId + "'>"
							+ item.cityName + "</option>";
				});
				$('#cityDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateProvinceDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllProvinceAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Province</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.provId + "'>"
							+ item.provinceName + "</option>";
				});
				$('#provinceDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateRiderDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllRiderPerBranchAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Rider</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.riderId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#riderDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateAllRiderDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllRiderPerBranchAction?forManageWaybill=Y",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Rider</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.riderId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#riderDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateCSRDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllCSRPerBranchAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select CSR</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.userId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#csrDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateCorporateAccountsDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllCorporateAccountAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Customer</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.customerId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#customerDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populatePickUpDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllRiderPerBranchAction?forManageWaybill=Y",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Rider</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.riderId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#pickUpDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};


function populateHeadRiderDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllRiderPerBranchAction",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Rider</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.riderId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#headRiderDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};

function populateAllHeadRiderDropDown() {
	
	var oAjaxCall = $.ajax({
		type : "POST",
		url : "GetAllRiderPerBranchAction?forManageWaybill=Y",
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
			var items = '<option value="0">Select Rider</option>';
			if (data.Records.length) {
				$.each(data.Records, function(i, item) {
					items += "<option value='" + item.riderId + "'>"
							+ item.firstname + " " + item.lastname + "</option>";
				});
				$('#headRiderDropDownID').html(items);
			}
			;

		},
		error : function(data) {
			alert('error: ' + data);
		}

	});
};



//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindowCloseParentWithScrollBar 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------
function openNewPopUpWindowCloseParentWithScrollBar(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
		window.close();
	} else {			
		window.opener = top;
		window.close();			
	}
}

//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindowWithScrollBar 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------
function openNewPopUpWindowWithScrollBar(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;
	}
}

//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindowCloseParent 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------	
function openNewPopUpWindowCloseParent(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
		window.close();		
	} else {			
		window.opener = top;
		window.close();					
	}

}

//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindow 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------	
function openNewPopUpWindow(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,fullscreen=true,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_blank", winParameters);
	o.legallyOpened = true;
	
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;			
	}

}

//----------------------------------------------------------------- 
//Function Name      : openNewPopUpWindow 
//Function Purpose   : Removes the menus, toolbar, address bar,  and status bar from 
//						the login page and fits it to specified size
//Passed Parameters  : URL, SCREEN WIDTH, SCREEN HEIGHT 
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//----------------------------------------------------------------	
function openNewPopUpWindowSelf(url, swidth, sheight){		
		
	var w = swidth;
	var h = sheight;
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);		
		
	var winParameters = "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,fullscreen=true,width="+w+",height="+h+",top="+top+",left="+left;
	var o = window.open(url, "_self", winParameters);
	o.legallyOpened = true;
	
	
	var ver = getInternetExplorerVersion();	
	if ( ver >= 7.0 )    {			
		window.open('', '_self', '');
	} else {			
		window.opener = top;			
	}

}

//-----------------------------------------------------------------
//Function Name      : getInternetExplorerVersion
//Function Purpose   : Returns the version of Internet Explorer or a -1
//                (indicating the use of another browser).
//Passed Parameters  : The Select element id, the option id, and the input id
//Retuned Parameters : <none> 
//Author             : Ian Orozco
//-----------------------------------------------------------------
function getInternetExplorerVersion()

{
	var rv = -1; // Return value assumes failure.
	if (navigator.appName == 'Microsoft Internet Explorer')
	{
		var ua = navigator.userAgent;
		var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		if (re.exec(ua) != null)
			rv = parseFloat( RegExp.$1 );
	}
	return rv;
}
