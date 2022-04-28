package com.portoseguro.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portoseguro.projetointegrador.model.DetalhePedido;

@Repository
public interface DetalhePedidoRepository extends JpaRepository<DetalhePedido, Long>{
}
