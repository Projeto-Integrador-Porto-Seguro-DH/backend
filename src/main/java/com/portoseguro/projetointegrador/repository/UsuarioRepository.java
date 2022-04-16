package com.portoseguro.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String idUsuario);

}

//public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
//
//	public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
//	
//	public List<Usuario> findAllByUserCpfContainingIgnoreCase(String cpfUsuario);
//	
//	public List<Usuario> findAllByUserAddressContainingIgnoreCase(String enderecoUsuario);
//	
//	public List<Usuario> findAllByUserEmailContainingIgnoreCase(String emailUsuario);