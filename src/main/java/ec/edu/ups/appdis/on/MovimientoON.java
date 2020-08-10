package ec.edu.ups.appdis.on;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ec.edu.ups.appdis.dao.CuentaDAO;
import ec.edu.ups.appdis.dao.MovimientoDao;
import ec.edu.ups.appdis.model.Cuenta;
import ec.edu.ups.appdis.model.Movimiento;

@Stateless
public class MovimientoON {

	@Inject
	private MovimientoDao mdao;

	@Inject
	private CuentaDAO cdao;

	private double saldoActual;

	private double saldoNuevo;

	/**
	 * Permite insertar un nuevo movimiento en una cuenta especifica pudiendo ser un deposito o un retiro
	 * en el caso de los depositos se permite hacer sin restriccion pero en el caso de querer hacer un retiro
	 * por un valor mayor al saldo no permitira mostrando un mensaje que indica la insuficiencia de saldo
	 * @param numCuenta
	 * @param valor
	 * @param tipo
	 * @throws Exception
	 */
	public void insertar(int numCuenta, double valor, String tipo) throws Exception {
		Movimiento m = new Movimiento();

		Cuenta aux = cdao.buscarCuenta(numCuenta);
		
		System.out.println("tipo: " + tipo);

		if (aux != null && tipo.equals("Deposito")) {
			saldoActual = aux.getSaldo();
			saldoNuevo = saldoActual + valor;
			aux.setSaldo(saldoNuevo);
			cdao.actualizarSaldo(aux);

			String fechaActual = getFechaActual();
			m.setFecha(fechaActual);
			m.setMonto(valor);
			m.setTipo(tipo);
			Cuenta cu = new Cuenta();
			cu.setIdcuenta(aux.getIdcuenta());
			m.setCuenta(cu);

			mdao.insertar(m);
			
			throw new Exception("Deposito realizado con ¡Exito!");

		} else if (aux != null && tipo.equals("Retiro")) {
			saldoActual = aux.getSaldo();
			if(valor <= saldoActual ) {
			saldoNuevo = saldoActual - valor;
			aux.setSaldo(saldoNuevo);
			cdao.actualizarSaldo(aux);
			
			//Para imprimir en los movimientos los retiros
			String fechaActual = getFechaActual();
			m.setFecha(fechaActual);
			//m.setFecha(new Date() + "");
			m.setMonto(valor);
			m.setTipo(tipo);
			Cuenta cu = new Cuenta();
			cu.setIdcuenta(aux.getIdcuenta());
			m.setCuenta(cu);

			mdao.insertar(m);
			
			throw new Exception("Retiro realizado con ¡Exito!");
			
			}else {
				throw new Exception("Saldo insuficiente");
			}
			
		}else {
			throw new Exception("Cuenta no Existe");
		}
	}
	
	/**
	 * Nos permite hacer una consulta de los diferente movientos entre fechas
	 * @param fechaInicio
	 * @param fechaFin
	 * @param idCuenta
	 * @return una lista de movimeinto entre fechas
	 */
	public List<Movimiento> getMovimientosFechas(String fechaInicio, String fechaFin, int idCuenta){
		return mdao.getMovimientosFechas(fechaInicio, fechaFin, idCuenta);
		
	}
	
	/**
	 * Nos permite obtener la fecha actual para guardar al momento de hacer una transaccion o hacer un login
	 * @return la fecha actual con un formato especifico
	 */
	public String getFechaActual() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		return dtf.format(now);
	}
	
	public String Transferencia(int corigen, int cdestino, Double valor) {
		
		Movimiento mor = new Movimiento();
		Movimiento mdst = new Movimiento();
		
		//Se guarda la cuenta origen y la cuenta destino en 2 variables aux, aux2
		Cuenta aux = cdao.read(corigen);
		System.out.println("aux1 " + aux);
		Cuenta aux2 = cdao.read(cdestino);
		System.out.println("aux 2 " + aux2);
		
		if(aux!=null && aux2!=null) {
			Cuenta destino = cdao.buscarCuenta(cdestino);
			System.out.println("Destino " + destino.toString());
			Double saldo = destino.getSaldo();
			System.out.println("Saldo 0001 " + saldo);
			saldo = saldo + valor;
			System.out.println("saldo 0022 " + saldo);
			
			Cuenta origen = cdao.buscarCuenta(corigen);
			Double saldoo = origen.getSaldo();
			
			//Validamos que el saldo sea mayor o igual al valor para realizar la transferencia
			if(saldoo>= valor) {
			saldoo = saldoo - valor;
			
			origen.setSaldo(saldoo);
			destino.setSaldo(saldo);
			
			//Se actualiza las cuentas una vez se haya hecho la transferencia
			cdao.actualizarSaldo(origen);
			cdao.actualizarSaldo(destino);
			
			mor.setFecha(new Date() + "");
			mor.setMonto(valor);
			mor.setCuenta(origen);
			mor.setTipo("RETIRO");
			
			mdst.setFecha(new Date() + "");
			mdst.setMonto(valor);
			mdst.setCuenta(destino);
			mdst.setTipo("DEPOSITO");
			
			mdao.insertar(mor);
			mdao.insertar(mdst);
			
			origen.agregarMovimiento(mor);
			destino.agregarMovimiento(mdst);
		
			}else {
				return "No tiene saldo suficiente para transferencia \n Su saldo maximo es: " + saldoo;
			}
			}else if(aux ==null) {
				return "No se encuentra la cuenta Origen";			
			}else if (aux2 == null) {
				return "No se encuentra la cuenta Destino";
		}
		return "Transaccion Exitosa";
	}
	
	
	public String deposito(int corigen, Double valor) {
		
		//Se guarda la cuenta origen en la variable aux
				Cuenta aux = cdao.read(corigen);
				System.out.println("aux1 " + aux);
				
				if(aux!=null) {
				Cuenta origen = cdao.buscarCuenta(corigen);
				System.out.println("Origen " + origen.toString());
				Double saldo = origen.getSaldo();
				System.out.println("Saldo 0001 " + saldo);
				saldo = saldo + valor;
				System.out.println("saldo 0022 " + saldo);
					
				origen.setSaldo(saldo);
				Double saldoo = origen.getSaldo();
				
				//Se actualiza las cuentas una vez se haya hecho la transferencia
				cdao.actualizarSaldo(origen);
				
				Movimiento mor = new Movimiento();
				mor.setFecha(new Date() + "");
				mor.setMonto(valor);
				mor.setCuenta(origen);
				mor.setTipo("DEPOSITO");
				
				mdao.insertar(mor);
				origen.agregarMovimiento(mor);

				}else {
					System.out.println("No tiene saldo suficiente para el deposito ");
				}
				return "El deposito se ha realizado con exito";		
			}
	
	public String retiro(int corigen, Double valor) {
	
			//Se guarda la cuenta origen en la variable aux
			Cuenta aux = cdao.read(corigen);
			System.out.println("aux1 " + aux);
			
			if(aux!=null) {
			Cuenta origen = cdao.buscarCuenta(corigen);
			System.out.println("Origen " + origen.toString());
			Double saldo = origen.getSaldo();
			System.out.println("Saldo 0001 " + saldo);
			saldo = saldo - valor;
			System.out.println("saldo 0022 " + saldo);
				
			origen.setSaldo(saldo);
			Double saldoo = origen.getSaldo();
			
			//Se actualiza las cuentas una vez se haya hecho la transferencia
			cdao.actualizarSaldo(origen);
			
			Movimiento mor = new Movimiento();
			mor.setFecha(new Date() + "");
			mor.setMonto(valor);
			mor.setCuenta(origen);
			mor.setTipo("RETIRO");
			
			mdao.insertar(mor);
			origen.agregarMovimiento(mor);

			}else {
				System.out.println("No tiene saldo suficiente para el retiro ");
			}
			return "El retiro se ha realizado con exito";		
		}
}