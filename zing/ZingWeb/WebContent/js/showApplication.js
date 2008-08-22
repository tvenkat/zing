function hideAppList(link){
	var dataTable = document.getElementById('applicationDataTable');	
	if(dataTable.style.display=="none"){				 
		dataTable.style.display="block";
	}
	else{
	 dataTable.style.display="none";
	}	
	return false;								
}
