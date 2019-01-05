package com.capgemini.mmbankapp.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
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
	private SavingsAccount savingsAccount;
	private SavingsAccount account;
	private RequestDispatcher dispatcher;
	boolean salary;
	int result= 0;
	private boolean sort = false;
	@Override
	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			/*PreparedStatement preparedStatement = 
					connection.prepareStatement("DELETE FROM ACCOUNT");
			preparedStatement.execute();*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public AccountController() {
        super();   
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getServletPath();
		System.out.println(path);
		
		double checkAccount = 0;
		switch(path){
		case "/addNewAccount.mm" :
			response.sendRedirect("addNewAccount.jsp");
			break;
		case "/createNewAccount.mm" :
			String name=request.getParameter("accountHolderName");
			double balance=Double.parseDouble(request.getParameter("accountBalance"));
			salary=request.getParameter("salaried").equalsIgnoreCase("yes") ? true : false;
			System.out.println(balance);
			System.out.println(name);
			System.out.println(salary);
			try {
				savingsAccountService.createNewAccount(name, balance, salary);
				response.sendRedirect("getAllAccountDetails.mm");
			} catch (ClassNotFoundException |SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;		
		case "/closeAccount.mm":
			response.sendRedirect("closeAccount.jsp");
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
			response.sendRedirect("getCurrentAccount.jsp");
			break;
		case "/checkCurrentAccount.mm":
			int accountNo=Integer.parseInt(request.getParameter("accountNumber"));
			try {
				 checkAccount = savingsAccountService.checkCurrentBalance(accountNo);
				PrintWriter out = response.getWriter();
				out.println("Your current Balance is:" +checkAccount);
			} catch (ClassNotFoundException | AccountNotFoundException| SQLException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/withdraw.mm":
			response.sendRedirect("withdraw.jsp");
			break;
		case "/withdrawAmount.mm":
			int accountNumbertoWithdraw=Integer.parseInt(request.getParameter("accountNumber"));
			double amount=Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumbertoWithdraw);
				savingsAccountService.withdraw(savingsAccount, amount);
				DBUtil.commit();
				response.sendRedirect("getCurrentAccount.mm");
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
			response.sendRedirect("deposit.jsp");
			break;
		case "/depositAmount.mm":
			int accountNumberToDeposit=Integer.parseInt(request.getParameter("accountNumber"));
			double amountdeposit=Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumberToDeposit);
				savingsAccountService.deposit(savingsAccount, amountdeposit);
				DBUtil.commit();
				PrintWriter out = response.getWriter();
				out.println("Your current Balance is:" +checkAccount);
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
			response.sendRedirect("fundTransfer.jsp");
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
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			int accountNumberToSearch = Integer.parseInt(request.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService.getAccountById(accountNumberToSearch);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/getAllAccountDetails.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/sortByName.mm":
			sort =!sort;
			 result = sort ? 1 : -1;
			try {
					List<SavingsAccount> accountList = new ArrayList<SavingsAccount>();
					accountList = savingsAccountService.getAllSavingsAccount();
					Collections.sort(accountList ,new Comparator<SavingsAccount>(){
							@Override
							public int compare(SavingsAccount arg0, SavingsAccount arg1) {
								return  result*arg0.getBankAccount().getAccountHolderName().compareTo
										(arg1.getBankAccount().getAccountHolderName());
							}	
					});
				request.setAttribute("accounts", accountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/sortByAccountNumber.mm":
			sort =!sort;
			 result = sort ? 1 : -1;
			try {
					List<SavingsAccount> accountList = new ArrayList<SavingsAccount>();
					accountList = savingsAccountService.getAllSavingsAccount();
					Collections.sort(accountList ,new Comparator<SavingsAccount>(){
							@Override
							public int compare(SavingsAccount arg0, SavingsAccount arg1) {
								return  result*(arg0.getBankAccount().getAccountNumber()-
										(arg1.getBankAccount().getAccountNumber()));
							}	
					});
				request.setAttribute("accounts", accountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/sortByAccountBalance.mm":
			sort =!sort;
			 result = sort ? 1 : -1;
			try {
					List<SavingsAccount> accountList = new ArrayList<SavingsAccount>();
					accountList = savingsAccountService.getAllSavingsAccount();
					Collections.sort(accountList ,new Comparator<SavingsAccount>(){
							@Override
							public int compare(SavingsAccount arg0, SavingsAccount arg1) {
								return (int) (result*(arg0.getBankAccount().getAccountBalance()-
										(arg1.getBankAccount().getAccountBalance())));
							}	
					});
				request.setAttribute("accounts", accountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/sortBySalary.mm":
			sort =!sort;
			 result = sort ? 1 : -1;
			try {
					List<SavingsAccount> accountList = new ArrayList<SavingsAccount>();
					accountList = savingsAccountService.getAllSavingsAccount();
					Collections.sort(accountList ,new Comparator<SavingsAccount>(){
							@Override
							public int compare(SavingsAccount arg0, SavingsAccount arg1) {
								if(arg0.isSalary())
									return 1*result;
								else
									return -1*result;
							}	
					});
				request.setAttribute("accounts", accountList);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/updateAccount.mm":
			response.sendRedirect("UpdateAccount.jsp");
			break;
		case "/update.mm":
			int accountNumberToUpdate=Integer.parseInt(request.getParameter("accountNumber"));
			try {
				SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumberToUpdate);
				request.setAttribute("account", savingsAccount);
				dispatcher = request.getRequestDispatcher("UpdateDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case "/updateAccountDB.mm":
			String accountHolderNameToUpdate = request.getParameter("name");
			salary = request.getParameter("salary").equalsIgnoreCase(
					"salaryTrue") ? true : false;
			System.out.println(salary);

			try {
				account.getBankAccount().setAccountHolderName(accountHolderNameToUpdate);
				account.setSalary(salary);
				SavingsAccount savingsAccount = savingsAccountService.updateAccount(account);
				request.setAttribute("account", savingsAccount);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
