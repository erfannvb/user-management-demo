package nvb.dev.usermanagementdemo.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import nvb.dev.usermanagementdemo.exception.UserNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UserNotFoundException | NoDataFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
