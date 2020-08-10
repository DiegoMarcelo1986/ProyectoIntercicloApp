package ec.edu.ups.appdis.on;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.appdis.dao.ClienteDAO;
import ec.edu.ups.appdis.model.Cliente;

@Stateless
public class ClienteON {

	@Inject
	private ClienteDAO cdao;

	/**
	 * 
	 * @param cliente
	 */
	public void inser(Cliente cliente) {
		Cliente aux = cdao.read(cliente.getIdcedula());

		if (aux != null) {
			cdao.update(cliente);
		} else {
			cdao.insert(cliente);
		}
	}

	/**
	 * 
	 * @param cedula
	 */
	public void remove(String cedula) {
		cdao.remove(cedula);
	}

	/**
	 * 
	 * @return
	 */
	public List<Cliente> getClientes() {
		return cdao.getClientes();
	}

}
