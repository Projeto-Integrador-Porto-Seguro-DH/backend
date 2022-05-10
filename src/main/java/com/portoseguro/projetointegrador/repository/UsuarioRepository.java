package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByNomeUsuario(String nomeUsuario);
	
	public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
	
	public Optional<Usuario> findByEmailUsuarioContainingIgnoreCase(String emailUsuario);
	
	public Optional<Usuario> findByCpfUsuario(String cpfUsuario);
	
}
