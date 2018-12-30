package com.capgemini.mmbankapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("*.mm")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SavingsAccountService savingsAccountService=new SavingsAccountServiceImpl();
	
    public AccountController() {
        super();   
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getServletPath();
		System.out.println(path);
		switch(path){
		case "/addNewAccount.mm" :
			response.sendRedirect("addNewAccount.html");
			break;
		case "/createNewAccount.mm" :
			String name=request.getParameter("accountHolderName");
			double balance=Double.parseDouble(request.getParameter("accountBalance"));
			boolean salary=request.getParameter("salariedTrue").equalsIgnoreCase("Yes") ? true : false;
			System.out.println(balance);
			System.out.println(name);
			System.out.println(salary);
			try {
				savingsAccountService.createNewAccount(name, balance, salary);
			} catch (ClassNotFoundException |SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/closeAccount.mm":
			response.sendRedirect("closeAccount.html");
			break;
		case "/deleteAccount.mm":
			int accountNumber=Integer.parseInt(request.getParameter("accountNumber"));
			System.out.println(accountNumber);
			try {
				savingsAccountService.deleteAccount(accountNumber);
				DBUtil.commit();
				System.out.println("deleted");
			} catch (ClassNotFoundException | AccountNotFoundException | SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/getCurrentAccount.mm":
			response.sendRedirect("getCurrentAccount.html");
			break;
		case "/checkCurrentAccount.mm":
			int accountNo=Integer.parseInt(request.getParameter("accountNumber"));
			try {
				double checkAccount = savingsAccountService.checkCurrentBalance(accountNo);
				PrintWriter out = response.getWriter();
				out.println("Your current Balance is:" +checkAccount);
			} catch (ClassNotFoundException | AccountNotFoundException| SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/withdraw.mm":
			response.sendRedirect("withdraw.html");
			break;
		case "/withdrawAmount.mm":
			int accountNumbertoWithdraw=Integer.parseInt(request.getParameter("accountNumber"));
			double amount=Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumbertoWithdraw);
				savingsAccountService.withdraw(savingsAccount, amount);
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case "/deposit.mm":
			response.sendRedirect("deposit.html");
			break;
		case "/depositAmount.mm":
			int accountNumberToDeposit=Integer.parseInt(request.getParameter("accountNumber"));
			double amountdeposit=Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumberToDeposit);
				savingsAccountService.deposit(savingsAccount, amountdeposit);
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case "/fundTransfer.mm":
			response.sendRedirect("fundTransfer.html");
			break;
		case "/fundtransfer.mm":
			int senderAccountNumber=Integer.parseInt(request.getParameter("senderAccountNumber"));
			int receiverAccountNumber=Integer.parseInt(request.getParameter("receiverAccountNumber"));
			double amountTodeposit=Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amountTodeposit);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
