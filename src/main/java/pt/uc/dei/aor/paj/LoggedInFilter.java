package pt.uc.dei.aor.paj;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/login.xhtml")
public class LoggedInFilter implements Filter {

	@Inject
	private Login login;
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException { 
    	HttpServletRequest req = (HttpServletRequest) request;
    	
        if (login.isLoggedin()) {
            // User is logged in, so just continue request.
        	HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath() + "/calculator/index.xhtml");
            
        } else {
            // User is not logged in, so redirect to index.
        	chain.doFilter(request, response);
        }
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
