function addCookie(sName,sValue,day) { 
	var expireDate = new Date(); 
	expireDate.setDate(expireDate.getDate() + day);; 
	document.cookie = escape(sName) + '=' + escape(sValue) +';expires=' + expireDate.toGMTString();
} 

function writeCookie(name, value, hours) //name表示写入的变量，Value表示name变量的值，hours表示保存时间。
{
	var expire = "";
	if(hours != null)
	{
		expire = new Date((new Date()).getTime() + hours * 3600000);
		expire = "; expires=" + expire.toGMTString();
	}
	document.cookie = name + "=" + escape(value) + expire + ";path=/";
}
function readCookie(name)
{
	var cookieValue = "";
	var search = name + "=";
	if(document.cookie.length > 0)
	{
		offset = document.cookie.indexOf(search);
		if (offset != -1)
		{
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1) end = document.cookie.length;
			cookieValue = (document.cookie.substring(offset, end))
		}
	}
	return cookieValue;
}