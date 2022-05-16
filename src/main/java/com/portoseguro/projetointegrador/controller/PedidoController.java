package com.portoseguro.projetointegrador.controller;

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

import com.portoseguro.projetointegrador.model.Pedido;
import com.portoseguro.projetointegrador.repository.PedidoRepository;
import com.portoseguro.projetointegrador.repository.UsuarioRepository;
import com.portoseguro.projetointegrador.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public ResponseEntity<List<Pedido>> getAllPedido() {
		return ResponseEntity.ok(pedidoRepository.findAll());
	}

	@GetMapping("/{idPedido}")
	public ResponseEntity<Pedido> getAllById(@PathVariable Long idPedido) {
		return pedidoRepository.findById(idPedido).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add")
	public ResponseEntity<Pedido> postPedido(@RequestBody Pedido pedido) {
		return usuarioRepository.findById(pedido.getUsuario().getIdUsuario())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.cadastrarPedido(pedido)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/update")
	public ResponseEntity<Pedido> putPedido(@RequestBody Pedido pedido) {
		if (pedidoRepository.existsById(pedido.getIdPedido())) {
			if (usuarioRepository.existsById(pedido.getUsuario().getIdUsuario()))
				return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedido));
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("delete/{idPedido}")
	public ResponseEntity<Object> deletePedido(@PathVariable Long idPedido) {
		return pedidoRepository.findById(idPedido).map(resposta -> {
			pedidoRepository.deleteById(idPedido);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
