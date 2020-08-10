package ec.edu.ups.appdis.on;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.appdis.dao.UsuarioDAO;
import ec.edu.ups.appdis.model.Cliente;
import ec.edu.ups.appdis.model.Usuario;

@Stateless
public class UsuarioON {

	@Inject
	private UsuarioDAO uDAO;

	/**
	 * Metodo que permite guardar un  usuario pero primero verifica la existencia del ccliente por medion de su Id
	 * @param usuario
	 */
	public void guardarUsuario(Usuario usuario) {
		Usuario aux = uDAO.read(usuario.getIdusuario());

		if (aux != null) {
			uDAO.update(usuario);
		} else {
			uDAO.insert(usuario);
		}
	}

	/**
	 * Metodo que permite borrar a un usuario por medio de su ID
	 * @param id
	 */
	public void remove(int id) {
		uDAO.remove(id);
	}
	
	/**
	 * Se utiliza para hacer el login del usuario mediante el correo y la contraseña
	 * Se hace una consulta a la base de datos y verificamos que el usuario exista en la BDD
	 * proceso que se hace en los DAO
	 * @param correo
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> getUsuarioLogin(String correo, String pass) throws Exception {
		List<Usuario> getUsuarioLogin = uDAO.getUsuarioLogin(correo, pass);
		if (getUsuarioLogin.isEmpty())
			throw new Exception("Usuario no existe");
		return getUsuarioLogin;

	}

	/**
	 * 
	 * @return
	 */
	public List<Usuario> geUsers() {
		return uDAO.getUsers();
	}

}
