<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New Account</title>
</head>
<body>
	<form action="createNewAccount.mm">
		Enter Account Holder Name: <input type="text" name="accountHolderName"/> <br /> <br />
		Enter Account Balance:<input type="text" name="accountBalance" > </br> </br>
		Enter Salaried ?: <input type="radio" name="salaried" value="Yes"> Yes
  						  <input type="radio" name="salaried" value="No"> No<br></br> 
  		<input type="submit" value="submit">
		<input type="Reset" value="Reset">
	</form><br />
	<div>
		<jsp:include page="HomeLink.html"></jsp:include>
	</div>
</body>
</html>