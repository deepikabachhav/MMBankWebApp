<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fund Transfer</title>
</head>
<body>
	<form action="fundtransfer.mm">
		Enter sender's Account Number: <input type="text" name="senderAccountNumber" maxlength="14" /> <br /> <br />
		Enter receiver's Account Number: <input type="text" name="receiverAccountNumber" maxlength="14" /> <br /> <br />
		Enter Amount: <input type="number" name="amount" /> <br /> <br />
		<input type="submit" value="submit">
	</form><br />
	<div>
		<jsp:include page="HomeLink.html"></jsp:include>
	</div>
</body>
</html>