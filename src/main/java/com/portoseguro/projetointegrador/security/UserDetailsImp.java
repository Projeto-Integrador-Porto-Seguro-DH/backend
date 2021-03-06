package com.portoseguro.projetointegrador.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.portoseguro.projetointegrador.model.Usuario;

public class UserDetailsImp implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

	public UserDetailsImp() {
	}

	public UserDetailsImp(Usuario user) {
		this.userName = user.getEmailUsuario();
		this.password = user.getSenhaUsuario();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}