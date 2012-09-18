package cajeroapp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class Cajero {
		
	/**
	 * @author nvelasquez
	 *
	 */
	private static Tarjeta vTarjeta;
	private static float blcMax = 100000f;
	private static Map<String, Cajero> qty = new HashMap<String, Cajero>();
	private static String vBanco = "Banco De Reservas";
	private int vConteo;
	private float vMonto;
	private int vUsado = 0;
	
	public Cajero(float pMonto, int pQty, int pUsado){
		this.vMonto = pMonto;
		this.vConteo = pQty;
		this.vUsado = pUsado;

	}
	public static void data(){
		qty.put("2000", new Cajero(2000,20,0));
		qty.put("1000", new Cajero(1000,20,0));
		qty.put("500", new Cajero(500,30,0));
		qty.put("200", new Cajero(200,50,0));
		qty.put("100", new Cajero(100,150,0));
	}	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String vTarjetaIn;
		String vPass;
		String vOperacion;
		 float vRetiro;
		boolean seguir = true;
		Tarjeta.generarData();
		Scanner con = new Scanner(System.in);
		
		while (seguir){
			System.out.println("********** Bienvenido al Banco De Reservas **********");
			System.out.println("Favor ingresar sus datos para iniciar una transaccion");
			System.out.print("Numero de Tarjeta: ");
			vTarjetaIn = con.next();
			System.out.print("Password: ");
			vPass = con.next();
			
			 //Validar login
			vTarjeta = Tarjeta.tarjetas.get(vTarjetaIn);
			
			if (logueo(vTarjetaIn, vPass)){
				while (seguir){
					System.out.println("********* Bienvenido! Favor elegir la operacion que desea realizar.***********");
					
					System.out.println("Tarjeta: "+vTarjetaIn);
					System.out.println("Banco: "+vTarjeta.vBanco);
					System.out.println("Balance de la cuenta: "+vTarjeta.vBalance);
					System.out.println("Balance de disponible: "+(vTarjeta.vBalance - 100));
					System.out.println("Tipo Tarjeta: "+vTarjeta.vTipoTarjeta);
					System.out.println();
					System.out.println("*************Para realizar un retiro introduzca la letra R******************");
					System.out.println();
					System.out.print("Introduzca la operacion deseada: ");
					vOperacion = con.next();
					vOperacion = vOperacion.trim();
					
					if (vOperacion.toUpperCase().equals("R")){
						System.out.print("Favor introducir el monto que desea retirar: ");
						vRetiro = con.nextFloat();
						if (retiro(vRetiro, vTarjeta.vBanco, vTarjetaIn)){
							System.out.println("El retiro se realizo exitosamente");
							distribucion(vRetiro);
			
						}else{
							System.out.println("No se puede entregar el monto solicitado");
						}
						
					}else{
						System.out.println("Debe introducir una operacion válida");
					}
					System.out.print("Si desea continuar Ingrese S de lo contrario N: ");
					System.out.println();
					vOperacion = con.next();
					vOperacion = vOperacion.trim();
					
					if (vOperacion.toUpperCase().equals("S")){
						seguir = true;
					}else {
						seguir = false;
					}
				}
			}else {
				if (vTarjeta.vConteo >= 3){
					System.out.println("Este usuario agoto el máximo de intentos permitidos por dia");
					seguir = true;
				}else {
				System.out.println("La clave o el usuario son incorrectos, favor intente nuevamente!");
				seguir = true;
				}				
			}
			seguir = true;
		}		
	}
	public static boolean logueo(String pTarjeta, String pPass){
		boolean vCommit = true; 
		if (Tarjeta.tarjetas.containsKey(pTarjeta)){				 
			if (Tarjeta.tarjetas.get(pTarjeta).vPass.equals(pPass)){
				vCommit = true;
			}else {
				Tarjeta tar = Tarjeta.tarjetas.get(pTarjeta);
				tar.vConteo = tar.vConteo + 1 ;
				Tarjeta.tarjetas.put(pTarjeta,tar);
			
				vCommit = false;
			}
		}
		return vCommit;
	}
	public static boolean retiro(float pBlc, String pBanco, String pTarjeta){
		boolean result = false;
		Tarjeta tar = Tarjeta.tarjetas.get(pTarjeta);
		if (!(pBanco.equals(vBanco))){
			if((tar.vMontoDia + pBlc) <= 20000){
				tar.vBalance = (tar.vBalance - pBlc);
				tar.vMontoDia = (tar.vMontoDia + pBlc);
				result = true;
			}else{
				System.out.println("El monto maximo permitido por dia es de 20,000");
				result = false;
			}
		}else{
			if((tar.vMontoDia + pBlc) <= 2000){
				tar.vBalance = (tar.vBalance - pBlc);
				tar.vMontoDia = (tar.vMontoDia + pBlc);
				result = true;
			}else{
				System.out.println("El monto maximo permitido es de 2,000");
				result = false;
			}
		}
		if (result){
			blcMax = (blcMax - pBlc);
		}
		return result;
	}
	public static void distribucion(float pBlc){
		int conteo  = 0;
		int vBlc = (int)pBlc;
		Cajero caj;
		Iterator<Entry<String, Cajero>> i = qty.entrySet().iterator();
			while(i.hasNext()){
				caj = Cajero.qty.get(i.next());
				while(caj.vMonto <= vBlc){
					conteo = (int) (vBlc / caj.vMonto);
					vBlc = (int) (vBlc - caj.vMonto);
					caj.vConteo = caj.vConteo - conteo;
					caj.vUsado = conteo;
				}	
			
			qty.put(i.next().toString(),caj );
			System.out.println("Moneda x Cantidad"+caj.vMonto+" x "+caj.vConteo);			
		}
	}
}
