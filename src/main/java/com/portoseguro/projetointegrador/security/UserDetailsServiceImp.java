package com.portoseguro.projetointegrador.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.portoseguro.projetointegrador.model.Usuario;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;

public class UserDetailsServiceImp implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByUsuario(usuario);
        
        user.orElseThrow(() -> new UsernameNotFoundException(usuario + "not found"));
        
        return user.map(UserDetailsImp::new).get(); 
	}
	
}