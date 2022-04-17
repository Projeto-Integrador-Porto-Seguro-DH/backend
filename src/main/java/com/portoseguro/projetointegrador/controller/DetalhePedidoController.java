package com.portoseguro.projetointegrador.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portoseguro.projetointegrador.model.DetalhePedido;
import com.portoseguro.projetointegrador.repository.DetalhePedidoRepository;


	@RestController
	@RequestMapping("/detalhepedido")
	@CrossOrigin("*")
	public class DetalhePedidoController {
	
	@Autowired
	private DetalhePedidoRepository detalhePedidoRepository;
	
	@GetMapping
	public ResponseEntity<List<DetalhePedido>> getAllDetalhePedido(){
		return ResponseEntity.ok(detalhePedidoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhePedido> getById(@PathVariable Long id){
		return detalhePedidoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/datapedido/{dataPedido}")
	public ResponseEntity<List<DetalhePedido>> getBydataPedido(@PathVariable Date datapedido){
		return ResponseEntity.ok(detalhePedidoRepository.findAllByDataPedidoContaining(datapedido));
	}

	
	@PostMapping
	public ResponseEntity<DetalhePedido> post (@RequestBody DetalhePedido detalhepedido){
		return ResponseEntity.status(HttpStatus.CREATED).body(detalhePedidoRepository.save(detalhepedido));
	}
	
	@PutMapping
	public ResponseEntity<DetalhePedido> put (@RequestBody DetalhePedido detalhepedido){
		return ResponseEntity.status(HttpStatus.OK).body(detalhePedidoRepository.save(detalhepedido));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		detalhePedidoRepository.deleteById(id);
	}
	
	
	
}
