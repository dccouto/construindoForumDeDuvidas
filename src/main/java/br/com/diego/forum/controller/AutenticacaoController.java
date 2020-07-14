package br.com.diego.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.diego.forum.config.security.TokenService;
import br.com.diego.forum.controller.dto.TokenDto;
import br.com.diego.forum.controller.form.LoginForm;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){
		
		//esse objeto é o que o authManager.authenticate() necessita receber
		UsernamePasswordAuthenticationToken dadosLogin = form.converterParaUserNamePassAuth();
		
		
		try {
			// e ira autenticar o usuário pelo sping.
			//quando chamar essa linha o spring sabe que terá que chamar a classe autenticacaoService
			Authentication authenticate = authManager.authenticate(dadosLogin);
			
			//irá gerar o jtoken, o método recebe o authenticate para poder extrair o usuário que está logado no sistema
			String token = tokenService.gerarToken(authenticate);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));//bearer(para token) é pra avisar o dev do front o tipo de requisição que deve fazer.
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
