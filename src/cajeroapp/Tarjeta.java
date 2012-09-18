package cajeroapp;
import java.util.HashMap;
import java.util.Map;

public class Tarjeta {
	String vPass, vBanco, vTipoTarjeta;
	 float vBalance;
	   int vConteo = 0;
	  float vMontoDia = 0f;
	/**
	 * @param args
	 */
	
	public Tarjeta(String pPass, String pBanco,String pTipoTarjeta, float pBalance, int pConteo, float pMontoDia){
		this.vBalance 	  = pBalance;
		this.vBanco   	  = pBanco;
		this.vPass    	  = pPass;
		this.vTipoTarjeta = pTipoTarjeta;
		this.vConteo      = pConteo;
		this.vMontoDia    = pMontoDia;
	}//constructor
	
	public static Map<String, Tarjeta> tarjetas = new HashMap<String, Tarjeta>();
	
	//crear data de tarjetas.
	public static void generarData(){
		tarjetas.put("5325-6782-9022-0512", new Tarjeta("Omar05","Banco de Reservas","Visa", 34825.3f,0,0f));
		tarjetas.put("1234-0987-5667-8758", new Tarjeta("90102245","Banco de Reservas","MasterCard", 500.00f,0,0f));
		tarjetas.put("8784-3345-9803-2839", new Tarjeta("Salami","Banco de Reservas", "Maestro", 3456.5f,0,0f));
		tarjetas.put("0930-5768-8909-2345", new Tarjeta("B@c123","Banco de Reservas","American Express", 15000.35f,0,0f));
		tarjetas.put("9095-5049-3847-2345", new Tarjeta("O$09","Banco de Reservas", "Visa", 8482.56f,0,0f));
	}
}
