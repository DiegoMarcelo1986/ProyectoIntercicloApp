package ec.edu.ups.appdis.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

//import modelo.Movimiento;

@Entity
public class Cuenta {
	
	@Id
	@Column(name="id_cuenta")
	private int idcuenta;
	
	@Column(name="cue_tipo")
	private String tipo;
	
	@Column(name="cue_saldo")
	private double saldo;
	
	@OneToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Movimiento> movimiento;
	
	public int getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(int idcuenta) {
		this.idcuenta = idcuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Movimiento> getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(List<Movimiento> movimiento) {
		this.movimiento = movimiento;
	}

	public void agregarMovimiento(Movimiento m) {
		if(movimiento == null) {
			movimiento = new ArrayList<Movimiento>();
		}
		movimiento.add(m);
	}

	@Override
	public String toString() {
		return "Cuenta [idcuenta=" + idcuenta + ", tipo=" + tipo + ", saldo=" + saldo + ", cliente=" + cliente
				+ ", movimiento=" + movimiento + "]";
	}
}
