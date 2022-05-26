package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String idUsuario);

	public Optional<Usuario> findByEmailUsuarioIgnoreCase(String emailUsuario);

	public Optional<Usuario> findByNomeUsuarioIgnoreCase(String nomeUsuario);

}
