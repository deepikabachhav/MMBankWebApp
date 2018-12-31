<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Close Account</title>
</head>
<body>
	<form action="deleteAccount.mm">
		Enter Account Number: <input type="text" name="accountNumber" maxlength="14" /> <br /> <br />
		<input type="submit" value="submit">
	</form><br />
	<div>
		<jsp:include page="HomeLink.html"></jsp:include>
	</div>
</body>
</html>