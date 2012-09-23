package cajeroapp;

import java.util.HashMap;
import java.util.Map;

public class Denominaciones {
	
	// Variables que los metodos usaran para manejo de los parametros.
	public int   vConteo;
	public float vMonto;
	public int   vUsado = 0;
	//El map sirve como tabla o array donde guardaremos las denominaciones organizadas por un indiceprivate static Tarjeta vTarjeta;
	private static float blcMax = 100000f;
	private static String vBanco = "Banco De Reservas";	
	
	public static Map<Integer, Denominaciones> qty = new HashMap<Integer, Denominaciones>();
	
	//Se crean los objetos que serviran como parametros necesarios para las denominaciones del dinero.
	//Constructor
	public Denominaciones(float pMonto, int pQty, int pUsado){
		this.vMonto = pMonto;
		this.vConteo = pQty;
		this.vUsado = pUsado;

	}
	// Parametros
	public static void data(){
		qty.put(0, new Denominaciones(2000,0,0));
		qty.put(1, new Denominaciones(1000,20,0));
		qty.put(2, new Denominaciones(500,30,0));
		qty.put(3, new Denominaciones(200,50,0));
		qty.put(4, new Denominaciones(100,150,0));
	}
	
}
