package br.com.diego.forum.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.diego.forum.model.Usuario;
import br.com.diego.forum.repository.UsuarioRepository;

//Como não tem anotations para filter no spring, devemos herdar a classe OncePerRequestFilter e sobreescrever doFilterInternal
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	//Dentro dessa classe filter, não funciona a injeção de dependencia atraves de anotações, somente via construtor
	private TokenService tokenService;
	
	private UsuarioRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = RecuperarToken(request);
		
		if(tokenService.isTokenValido(token)) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		
		Long idUsuario = tokenService.getIdusuario(token);
		
		Usuario usuario = repository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.getNome(), null, usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}


	private String RecuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		if (token.isEmpty() || token == null || !token.startsWith("Bearer ")) {
			
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
