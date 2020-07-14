package br.com.diego.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = RecuperarToken(request);
		
		filterChain.doFilter(request, response);
		
	}

	private String RecuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		if (token.isEmpty() || token == null || !token.startsWith("Bearer ")) {
			
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
