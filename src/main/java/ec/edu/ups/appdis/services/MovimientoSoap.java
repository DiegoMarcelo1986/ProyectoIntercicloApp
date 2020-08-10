package ec.edu.ups.appdis.services;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ec.edu.ups.appdis.dao.CuentaDAO;
import ec.edu.ups.appdis.dao.MovimientoDao;
import ec.edu.ups.appdis.on.MovimientoON;

@WebService
public class MovimientoSoap {
	
	@Inject
	private MovimientoON mon;
	
	@Inject
	private CuentaDAO cdao;
	
	@Inject
	private MovimientoDao mdao;
		
	@WebMethod
	public String movimiento(@QueryParam("corigen") int corigen,@QueryParam("cdestino") int cdestino, @QueryParam("valor") double valor ) {
		Response.ResponseBuilder builder=null;
		String respuestaFinal = "";
		Map<String, String> data=new HashMap<>();
		try {
			respuestaFinal = mon.Transferencia(corigen, cdestino, valor);
			data.put("code", "1");
			data.put("message", "OK ACTUALIZADO");
			builder=Response.status(Response.Status.OK).entity(data);
		}
		catch(Exception e) {
			data.put("code", "99");
			data.put("message", e.getMessage());
			builder=Response.status(Response.Status.OK).entity(data);
			e.printStackTrace();		
		}
		return respuestaFinal;	
	}
	
	@WebMethod
	public String deposito(@QueryParam("corigen") int corigen, @QueryParam("valor") double valor ) {
		Response.ResponseBuilder builder=null;
		String respuestaFinal = "";
		Map<String, String> data=new HashMap<>();
		try {
			respuestaFinal = mon.deposito(corigen, valor);
			data.put("code", "1");
			data.put("message", "OK ACTUALIZADO");
			builder=Response.status(Response.Status.OK).entity(data);
		}
		catch(Exception e) {
			data.put("code", "99");
			data.put("message", e.getMessage());
			builder=Response.status(Response.Status.OK).entity(data);
			e.printStackTrace();		
		}
	return respuestaFinal;
	}
	
	@WebMethod
	public String retiro(@QueryParam("corigen") int corigen, @QueryParam("valor") double valor ) {
		Response.ResponseBuilder builder=null;
		String respuestaFinal = "";
		Map<String, String> data=new HashMap<>();
		try {
			respuestaFinal = mon.retiro(corigen, valor);
			data.put("code", "1");
			data.put("message", "OK ACTUALIZADO");
			builder=Response.status(Response.Status.OK).entity(data);
		}
		catch(Exception e) {
			data.put("code", "99");
			data.put("message", e.getMessage());
			builder=Response.status(Response.Status.OK).entity(data);
			e.printStackTrace();		
		}
	return respuestaFinal;
	}
}