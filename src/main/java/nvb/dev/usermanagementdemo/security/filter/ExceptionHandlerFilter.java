package nvb.dev.usermanagementdemo.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nvb.dev.usermanagementdemo.exception.EntityNotFoundException;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";

        if (e instanceof EntityNotFoundException || e instanceof NoDataFoundException) {
            statusCode = HttpServletResponse.SC_NOT_FOUND;
            message = e.getLocalizedMessage();
        } else if (e instanceof JWTVerificationException) {
            statusCode = HttpServletResponse.SC_FORBIDDEN;
            message = e.getLocalizedMessage();
        } else if (e instanceof RuntimeException) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        }

        response.setStatus(statusCode);
        response.getWriter().write(message);
        response.getWriter().flush();
    }
}
