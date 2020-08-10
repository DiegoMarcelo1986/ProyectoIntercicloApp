package ec.edu.ups.appdis.on;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ec.edu.ups.appdis.dao.CuentaDAO;
import ec.edu.ups.appdis.dao.ResumenCuentaDAO;
import ec.edu.ups.appdis.model.Cliente;
import ec.edu.ups.appdis.model.Cuenta;
import ec.edu.ups.appdis.model.Movimiento;
import ec.edu.ups.appdis.model.Usuario;

@Stateless
public class ResumenCuentaON {
	

	@Inject
	private ResumenCuentaDAO rdao;
	
	/**
	 * Obtiene la cuenta con su respectivo cliente pasando como parametro el correo
	 * @param correo
	 * @return instancia de los dao la resumen cuenta y devuelve el correo
	 */
	public Cliente getClienteCuenta(String correo) {
		return rdao.getClienteCuenta(correo);
	}
	
	/** 
	 * Metodo que permite ver el ultimo movimiento de una cuenta pasando como parametro el idCuenta
	 * @param idCuenta
	 * @return el ultimo movimiento de la cuenta 
	 */
	public Movimiento getMovimientoUltimo(int idCuenta) {
		 return rdao.getMovimientoUltimo(idCuenta);
	}
}
