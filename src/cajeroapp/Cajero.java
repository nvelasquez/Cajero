package cajeroapp;
import java.util.Scanner;

public class Cajero {
		
	/**
	 * @author nvelasquez
	 *
	 */
	
	/**
	 * @param args
	 */
	//Parametros
	private static Tarjeta vTarjeta;
	private static float blcMax = 100000f;
	private static String vBanco = "Banco De Reservas";	
	//Constructor del objeto cajero.
	public Cajero(){
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
					//Validar operacion
					if (vOperacion.toUpperCase().equals("R")){
						System.out.print("Favor introducir el monto que desea retirar: ");
						vRetiro = con.nextFloat();
						//realizar operacion
						if (vRetiro%100==0){
							if (retiro(vRetiro, vTarjeta.vBanco, vTarjetaIn)){
								System.out.println("El retiro se realizo exitosamente");												
							}else{
								//En caso de que el monto exceda el limite permitido por dia de acuerdo al banco.
								System.out.println("No se puede entregar el monto solicitado");
							}
						}else{
							System.out.println("Debe introducir un monto multiplo de 100");
						}
						
					}else{
						System.out.println("Debe introducir una operacion v�lida");
					}
					System.out.print("Si desea continuar Ingrese S de lo contrario N: ");
					System.out.println();
					vOperacion = con.next();
					vOperacion = vOperacion.trim();
					// Para saber si quiere seguir realizando transacciones
					if (vOperacion.toUpperCase().equals("S")){
						seguir = true;
					}else {
						seguir = false;
					}
				}
			}else {
				if (vTarjeta.vConteo >= 3){
					//Solo se permiten tres intentos diarios por usuario.
					System.out.println("Este usuario agoto el m�ximo de intentos permitidos por dia");
					seguir = true;
				}else {
				System.out.println("La clave o el usuario son incorrectos, favor intente nuevamente!");
				seguir = true;
				}				
			}
			seguir = true;
		}		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cajero inicio = new Cajero();
	}
	// Validaciones necesarias para el logueo de usuario.
	public static boolean logueo(String pTarjeta, String pPass){
		boolean vCommit = true; 
		if (Tarjeta.tarjetas.containsKey(pTarjeta)){				 
			// Se verifica que es un usuario valido.
			if (Tarjeta.tarjetas.get(pTarjeta).vPass.equals(pPass)){
				vCommit = true;
			}else {
				//Se guardan los intentos de logueos invalidos del usuario.
				Tarjeta tar = Tarjeta.tarjetas.get(pTarjeta);
				tar.vConteo = tar.vConteo + 1 ;
				Tarjeta.tarjetas.put(pTarjeta,tar);
			
				vCommit = false;
			}
		}
		return vCommit;
	}
	//Metodo para realizar el retiro del efectivo.
	public static boolean retiro(float pBlc, String pBanco, String pTarjeta){
		boolean result = false;
		Tarjeta tar = Tarjeta.tarjetas.get(pTarjeta);
		if (!(pBanco.equals(vBanco))){
			if((tar.vMontoDia + pBlc) <= 20000){
				//hacemos la distribucion correspondiente a las monedas.
				if (distribucion(pBlc)){
					tar.vBalance = (tar.vBalance - pBlc);
					tar.vMontoDia = (tar.vMontoDia + pBlc);
					result = true;
				}
			}else{
				System.out.println("El monto maximo permitido por dia es de 20,000");
				result = false;
			}
		}else{
			if((tar.vMontoDia + pBlc) <= 2000){
				if (distribucion(pBlc)){
					tar.vBalance = (tar.vBalance - pBlc);
					tar.vMontoDia = (tar.vMontoDia + pBlc);
					result = true;
				}
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
	//Metodo para hacer la distribucion de entrega de las diferentes denominaciones.
	public static boolean distribucion(float pBlc){
		//Se carga la informacion de las denominaciones de monedas a entregar
		Denominaciones.data();
		// declaracion de variables.
		int conteo = 0;
        float vBlc = pBlc;
        boolean result = true;
        Denominaciones dem;

      //Si el cajero se queda sin dinero o el monto solicitado es mayor que el disponible.
        
    	if(vBlc >= blcMax){
    		System.out.println("El cajero no puede entregar el monto solicitado");
    		result = false;
    	}else{        
        //Iteramos de acuerdo a cada denominacion.
	        for(int i = 0; i < 5; i++){
	        	dem = Denominaciones.qty.get(i);
	        	while (vBlc >= dem.vMonto){
	        		conteo = (int)Math.floor(vBlc/dem.vMonto);//cuantos billetes se entregan
	        		//Validamos que no se hallan agotado los billetes de esa denominacion.
	        		if ((dem.vConteo - conteo) >= 0){
		        		vBlc = vBlc - (dem.vMonto*conteo);//Se resta lo entregado al monto.
		        		dem.vConteo = dem.vConteo - conteo;//restamos la cantidad de billetes usados
		        		
	        		}else {
	        			conteo = 0;
	        			break;	        			
	        		}
	        	}
	        	if (conteo != 0){
	        		System.out.println("Se entregaron "+conteo+" billetes de "+dem.vMonto);
	        	}
	        	Denominaciones.qty.put(i, dem);//actualizamos los billetes usados del cajero.
	        	conteo = 0;       
	        }        
    	}
    	return result;
    }
}
