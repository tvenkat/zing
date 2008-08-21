function checkBlankPhoto()
{
	if(document.getElementById('fileField').value == "")
		return false;
	else
		return true;
}

function toggleAppDiv()
{
	if(document.getElementById('left_app_div').style.display=="none")
	{
		document.getElementById('left_app_div').style.display="block";
	}
	else
	{
		document.getElementById('left_app_div').style.display="none";
	}
}

function showIframe(iframe_id, load_id)
{
//	alert(iframe_id);
	document.getElementById(iframe_id).style.display="block";
	document.getElementById(load_id).style.display="none";
}

function checkBlankSearch()
{
	if(document.getElementById('textfield').value == "")
		return false;
	else
		return true;
}