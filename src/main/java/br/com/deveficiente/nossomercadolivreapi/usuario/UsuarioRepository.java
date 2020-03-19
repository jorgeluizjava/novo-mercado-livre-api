package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {

}