package ec.edu.ups.appdis.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import ec.edu.ups.appdis.model.Cliente;
import ec.edu.ups.appdis.model.Cuenta;
import ec.edu.ups.appdis.model.Movimiento;
import ec.edu.ups.appdis.model.Usuario;
import ec.edu.ups.appdis.on.ClienteON;
import ec.edu.ups.appdis.on.CuentaON;
import ec.edu.ups.appdis.on.ResumenCuentaON;
import testjpa.utils.EnviarCorreoElectronico;
import testjpa.utils.PasswordGenerator;

@ManagedBean
public class ClienteBean {

	@Inject
	private ClienteON clion;

	@Inject
	private CuentaON cueon;

	private Cliente newCliente;

	private Cuenta newCuenta;

	private List<Cliente> getClientes;

	@Inject
	private FacesContext facesContext;

	private boolean editable = false;

	String pass = "";

	@PostConstruct
	public void init() {
		newCliente = new Cliente();
		newCuenta = new Cuenta();
		getClientes = clion.getClientes();
	}

	public Cliente getNewCleinte() {
		return newCliente;
	}

	public void setNewCleinte(Cliente newCleinte) {
		this.newCliente = newCleinte;
	}

	public Cuenta getNewCuenta() {
		return newCuenta;
	}

	public void setNewCuenta(Cuenta newCuenta) {
		this.newCuenta = newCuenta;
	}

	public List<Cliente> getGetClientes() {
		return getClientes;
	}

	public void setGetClientes(List<Cliente> getClientes) {
		this.getClientes = getClientes;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Metodo que permite guardar el Cliente, la Cuenta y tambien manda los datos del cliente con la contraseña aleatoria 
	 * @return a la ventana de Login
	 */
	public String guardarClienteCuenta() {

		try {

			guardarCliente();
			guardarCuenta();
			enviarCorreo();

			return "logincliente?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			facesContext.addMessage(null, m);
			e.printStackTrace();
		}

		return null;
	}

		/**
		 * Este metodo permite guardar al cliente con la contraseña aleatoria
		 */
		public void guardarCliente() {

		try {
			pass = PasswordGenerator.getPassword();
			newCliente.setPassword(pass);
			clion.inser(newCliente);
			System.out.println("registro guardado");
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			facesContext.addMessage(null, m);
			e.printStackTrace();
		}
	}

		/**
	 	* Este metodo permite guardar la Cuenta con su respectivo cliente
	 	*/
		public void guardarCuenta() {

		try {
			Cliente c = new Cliente();
			String cedulaCliente = newCliente.getIdcedula();
			c.setIdcedula(cedulaCliente);
			newCuenta.setCliente(c);
			cueon.insert(newCuenta);
			System.out.println("registro guardado 002 ");
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			facesContext.addMessage(null, m);
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que permite Actualizar el Cliente, la Cuenta.
	 * @return a la ventana de la Administracion de Clientes
	 */
	public String updateClienteCuenta() {

		try {

			updateCliente();
			guardarCuenta();
			// enviarCorreo();
			return "admi_clientes?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			facesContext.addMessage(null, m);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este metodo permite actualizar el cliente.
	 */
	public void updateCliente() {

		try {

			clion.inser(newCliente);
			System.out.println("registro guardado");
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			facesContext.addMessage(null, m);
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo nos permite borrar un cliente mediante un parametro.
	 * @param cedula
	 * @return a la ventana de Administracion de los Clientes
	 */
	public String borrar(String cedula) {
		try {
			clion.remove(cedula);
			System.out.println("registro eliminado");
			return "admi_clientes?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este metodo permite borrar la cuenta y el cliente mediante un parametro.
	 * @param codigo
	 * @return a la ventana de Administracion de los Clientes con sus cuentas
	 */
	public String borrarcuentacliente(int codigo) {
		try {
			cueon.remove(codigo);
			System.out.println("registro eliminado");
			return "admi_clientes?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este metodo nos permite editar a los clientes con sus cuentas
	 * @param cli
	 * @param c
	 * @return a la ventana de Actualizar Cliente con su Cuenta
	 */
	public String editar(Cliente cli, Cuenta c) {
		newCliente = cli;
		newCuenta = c;
		editable = true;
		return "update_clientecuenta";
	}

	/**
	 * Este metodo permite enviar un correo electronico al cliente con los datos que se crearon al momento de insertar un nuevo cliente. 
	 */
	public void enviarCorreo() {
		String destinatario = newCliente.getCorreo(); // A quien le quiere escribir.
		String asunto = "Datos del Cliente";
		String cuerpo = "Cedula: " + newCliente.getIdcedula() + " \n Nombre: " + newCliente.getNombre()
				+ " \n Apellido: " + newCliente.getApellido() + " \n Correo: " + newCliente.getCorreo()
				+ " \n Password: " + pass;

		EnviarCorreoElectronico.enviarConGMail(destinatario, asunto, cuerpo);
	}

}
