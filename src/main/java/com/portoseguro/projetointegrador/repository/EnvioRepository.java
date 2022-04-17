package com.portoseguro.projetointegrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.portoseguro.projetointegrador.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

	Optional<Envio> findByCodigoEnvio(Long codigoEnvio);	

}
