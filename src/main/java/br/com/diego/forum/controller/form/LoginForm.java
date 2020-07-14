package br.com.diego.forum.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {
	
	@NotNull
	private String email;
	
	@NotNull
	@Min(value = 3)
	private String senha;
	
	
	public String getEmail() {
		return email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converterParaUserNamePassAuth() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
	
	

}
