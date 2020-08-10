package ec.edu.ups.appdis.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import ec.edu.ups.appdis.model.Cliente;
import ec.edu.ups.appdis.model.Cuenta;
import ec.edu.ups.appdis.model.Usuario;

@Stateless
public class ClienteDAO {
	
	@Inject
	private EntityManager em;
	
	/**
	 * Metodo para insertar un Objeto Cliente
	 * @param cliente pasa como parametro todo el objeto cliente
	 */
	public void insert(Cliente cliente) {
		em.persist(cliente);
	}
	
	/**
	 * Metodo para actualizar un Objeto Cliente
	 * @param cliente pasa como parametro todo el objeto cliente
	 */
	public void update(Cliente cliente) {
		em.merge(cliente);
	}
	
	/**
	 * Metodo para eliminar un Objeto Cliente
	 * @param cedula pasa como parametro la cedula para eliminar el objeto cliente
	 */
	public void remove(String cedula) {
		em.remove(read(cedula));
	}
	
	/***
	 * Metodo para leer un Objeto Cliente
	 * @param cedula Obtener el objecto cliente por el parametro cedula
	 * @return Devuelve todo el obejeto cliente
	 */
	public Cliente read(String cedula) {
		Cliente aux = em.find(Cliente.class, cedula);
		return aux;
	}
	
	/**
	 * Metodo para Obtener todos los clientes
	 * @return Devuelve una lista de clientes
	 */
	public List<Cliente> getClientes(){
		String jpql = "SELECT c FROM Cliente c";
		Query q = em.createQuery(jpql, Cliente.class);
		List<Cliente> cli = q.getResultList();
		return cli;
	}

}
