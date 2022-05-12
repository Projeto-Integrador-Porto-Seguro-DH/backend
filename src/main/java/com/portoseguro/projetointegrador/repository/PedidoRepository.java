package com.portoseguro.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	public Optional<Pedido> findByPedido(String codigoEnvio);	
	
    public List<Pedido> findAllByPedido(Long idPedido);
    
    //public List<Pedido> findAllByUsuario();
	

}

