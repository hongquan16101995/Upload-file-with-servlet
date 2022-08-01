package test_filter.filter;

import test_filter.model.Login;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin"})
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Login.account == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("filter/login.jsp");
            dispatcher.forward(request, response);
        } else {
            if (Login.account.getRole().equals("ADMIN")) {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
