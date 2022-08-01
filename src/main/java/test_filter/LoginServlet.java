package test_filter;

import test_filter.model.Account;
import test_filter.model.Login;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private ArrayList<Account> accounts;

    @Override
    public void init() throws ServletException {
        accounts = new ArrayList<>();
        accounts.add(new Account("user", "123456", "USER"));
        accounts.add(new Account("admin", "123456", "ADMIN"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Login.account = null;
        response.sendRedirect("filter/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Account account = null;
        for (Account acc : accounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                account = acc;
            }
        }
        if (account != null) {
            Login.account = account;
            if (account.getRole().equals("USER")) {
                response.sendRedirect("/user");
            } else if  (account.getRole().equals("ADMIN")){
                response.sendRedirect("/admin");
            }
        }
    }
}
