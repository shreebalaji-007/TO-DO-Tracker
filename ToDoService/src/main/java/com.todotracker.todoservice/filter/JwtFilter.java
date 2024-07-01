package com.todotracker.todoservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        ServletOutputStream pw = httpServletResponse.getOutputStream();
        // expects the token to come from header
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            //If token is not coming than set the response status as UNAUTHORIZED
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            pw.println("Missing or invalid Token ");
            pw.close();
        } else {//extract token from the header
            String jwtToken = authHeader.substring(7);//Bearer => 6+1 since token begins with Bearer
            //token validation
            Claims claims = Jwts.parser().setSigningKey("capstoneproject").parseClaimsJws(jwtToken).getBody();
            // set the claims in the request attribute and pass it to the next handler
            httpServletRequest.setAttribute("claims", claims);
            // pass the claims in the request
            filterChain.doFilter(servletRequest, servletResponse); //some more filters , controller}

        }
    }
}
