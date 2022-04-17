package com.portoseguro.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portoseguro.projetointegrador.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
