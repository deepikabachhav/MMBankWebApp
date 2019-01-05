<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="updateAccountDB.mm">
 
	<table>
		<tr> 
			<th> Account Number </th>
			<th> Holder Name</th>
			<th>Account Balance </th>
			<th> Salary </th>
			<th> Over Draft Limit </th>
			<th> Type </th>
		</tr>
		<jstl:if test="${requestScope.account !=null}">
				<tr>
				<td name="accountNumber">${requestScope.account.bankAccount.accountNumber}</td>
				<td><input type="text" name="name" value="${requestScope.account.bankAccount.accountHolderName}"></td>
				<td>${account.bankAccount.accountBalance}</td>
				<jstl:if test="${requestScope.account.salary==true}">
					<td><select name="salary">
							<option value="salaryTrue">Yes</option>
							<option value="salaryFalse">No</option></td>
					<td>${"N/A"}</td>
				<td>${"Savings"}</td>
				</jstl:if>
				<jstl:if test="${requestScope.account.salary==false}">
					<td><select name="salary">
							<option value="salaryFalse">No</option>
							<option value="salaryTrue">Yes</option></td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</jstl:if>
			</tr>
		</jstl:if>
	</table>
	<input type = "submit" name="submit"> 	&nbsp &nbsp&nbsp
	<input type = "reset" name="reset"> <br>
	</form>
	<div>
		<jsp:include page="HomeLink.html"></jsp:include>
	</div>
</body>
</html>