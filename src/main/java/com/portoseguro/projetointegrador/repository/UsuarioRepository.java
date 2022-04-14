package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, UUID> {
	
	public List<Usuario> findAllByIdContainingIgnoreCase(UUID idUsuario);

	public List<Usuario> findAllByNameContainingIgnoreCase(String nomeUsuario);
	
	public List<Usuario> findAllByCpfContainingIgnoreCase(String cpfUsuario);
	
	public List<Usuario> findAllByAddressContainingIgnoreCase(String enderecoUsuario);
	
	public List<Usuario> findAllByEmailContainingIgnoreCase(String emailUsuario);
	
}
