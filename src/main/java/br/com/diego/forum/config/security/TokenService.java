package br.com.diego.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.diego.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	//o valor está na application.properties, usamos o @Value(nome da propriedade) para buscar dentro desse aquivo
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authenticate) {
		Usuario logado = (Usuario) authenticate.getPrincipal();
		Date criacaoTokent = new Date();
		Date expiracaoToken = new Date(criacaoTokent.getTime() + Long.parseLong(this.expiration));
		
		return Jwts.builder()
			.setIssuer("API do fórum da Alura") //Identificação da aplicação que gerou o token
			.setSubject(logado.getId().toString()) //Identificação do usuário logado
			.setIssuedAt(criacaoTokent) //A data de criação do token
			.setExpiration(expiracaoToken) //A data de expiração do token
			.signWith(SignatureAlgorithm.HS256, this.secret) //esse método gera o token com o segredo
			.compact();//Compacta do resultado e coloca em uma string
		
	}

	
}
