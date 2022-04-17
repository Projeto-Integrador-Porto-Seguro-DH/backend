package com.portoseguro.projetointegrador.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.portoseguro.projetointegrador.model.Envio;
import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.EnvioRepository;
import com.portoseguro.projetointegrador.repository.PedidoRepository;

@RestController
@RequestMapping("/envios")
public class EnvioController {

	@Autowired
	private EnvioRepository envioRepository;	
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PostMapping("/pedidoId/{pedidoId}")
	public ResponseEntity<Envio> save(@PathVariable Long pedidoId, @RequestBody Envio envio){
		
		Optional<Pedido> pedido = this.pedidoRepository.findById(pedidoId);
		
		if (!pedido.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.envioRepository.save(envio);
		pedido.get().setCodigoEnvio(envio.getIdEnvio());
		this.pedidoRepository.save(pedido.get());
		return ResponseEntity.ok(envio);
	}
	
	@GetMapping
	public ResponseEntity<List<Envio>> getAll() {
		return ResponseEntity.ok(envioRepository.findAll());
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Envio> getById(@PathVariable Long id) {
		Optional<Envio>  envio = envioRepository.findById(id);
		
		if (! envio.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Envio>(envioRepository.getById(id), HttpStatus.OK);
	}
	
	@GetMapping("/codigoEnvio/{codigoEnvio}")
	public ResponseEntity<Envio> getBy(@PathVariable Long codigoEnvio) {
		Optional<Envio>  envio = envioRepository.findByCodigoEnvio(codigoEnvio);
		if (!envio.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Envio>(envioRepository.getById(envio.get().getIdEnvio()), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		return envioRepository.findById(id).map(resposta -> {
			envioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }).orElse(ResponseEntity.notFound().build());
	}
	
}
