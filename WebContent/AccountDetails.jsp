<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr> 
			<th> <a href="sortByAccountNumber.mm">Account Number</a> </th>
			<th> <a href="sortByName.mm">Holder Name</a> </th>
			<th> <a href="sortByAccountBalance.mm"> Account Balance</a>  </th>
			<th> <a href="sortBySalary.mm">Salary</a> </th>
			<th> Over Draft Limit </th>
			<th> Type </th>
		</tr>
		<jstl:if test="${requestScope.account !=null}">
				<tr>
				<td>${requestScope.account.bankAccount.accountNumber}</td>
				<td>${requestScope.account.bankAccount.accountHolderName }</td>
				<td>${requestScope.account.bankAccount.accountBalance}</td>
				<td>${requestScope.account.salary==true?"Yes":"No"}</td>
				<td>${"N/A"}</td>
				<td>${"Savings"}</td>
			</tr>
		</jstl:if>
		<jstl:if test="${requestScope.accounts!=null}">
			<jstl:forEach var="account" items="${requestScope.accounts}">
				<tr>
					<td>${account.bankAccount.accountNumber}</td>
					<td>${account.bankAccount.accountHolderName }</td>
					<td>${account.bankAccount.accountBalance}</td>
					<td>${account.salary==true?"Yes":"No"}</td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</tr>
			</jstl:forEach>
		</jstl:if>
	</table>
	<div>
		<jsp:include page="HomeLink.html"></jsp:include>
	</div>
</body>
</html>