package Gen;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CRFC {
    private static String curp;
    private static String rfc;
  /*
    * function generarRFC
    *
    * recibe:
    * parameters
    *   nombre = Nombre del usuario (Obligatorio)
    *   prim_ap = Primer apellido (Obligatorio)
    *   seg_ap = Segundo apellido (Opcional)
    *   fecha = Fecha de nacimiento (Obligatorio en formato DD-MM-YYYY)
    *
    * regresa:
    *   String con RFC generado
    *
    * */

    public static void generarRFC(String nombre, String prim_ap, String seg_ap, String fecha){
        String salida;
        //Validation: nombre (No vacío, No menos de una letra)
        if (nombre.isBlank() || nombre.length() < 2) {
            System.out.println("Nombre no valido");
            return;
        } else {
            //Validation: primer apellido (No vacío, No menos de una letra)
            if (prim_ap.isBlank() || prim_ap.length() < 2) {
                System.out.println("Primer apellido no valido");
                return;
            } else {
                salida = generarNom(nombre, prim_ap, seg_ap);
                salida = checkInicial(salida);
            }
        }
        //Validation: formato de fecha
        if (validarFecha(fecha)) {
            salida += getFecha(fecha);
        } else {
            System.out.println("Fecha no valida");
            return ;
        }

        curp = salida;

        salida += genClave(); //Generación de Homoclave
        
        rfc = salida;
    }

    public static String getCurp() {
        return curp;
    }

    public static String getRfc() {
        return rfc;
    }

    private static String generarNom(String nombre, String prim_ap, String seg_ap){
        String salida;
        //Validation: primera letra del primer apellido (si es 'ñ' cambia por 'x')
        if (prim_ap.charAt(0) == 'ñ') {
            salida = "x";
        } else {
            salida = Character.toString(prim_ap.charAt(0));
        }
        //Busqueda de primer vocal después de la primera letra en el primer apellido
        for (int i = 1; i < prim_ap.length(); i++) {
            if (!esVocal(prim_ap.charAt(i))) {
                salida += prim_ap.charAt(i);
                break;
            }
        }
        //Validation: primera letra del segundo apellido (si es 'ñ' o vacio cambia por 'x')
        if (seg_ap.isEmpty() || (seg_ap.charAt(0) == 'ñ' || seg_ap.charAt(0) == 'Ñ')) {
            salida += 'x';
        } else {
            salida += seg_ap.charAt(0);

        }
        //Validation: primera letra del nombre (si es 'ñ' cambia por 'x')
        if (nombre.charAt(0) == 'ñ' || nombre.charAt(0) == 'Ñ') {
            salida += 'x';
        } else {
            salida += nombre.charAt(0);
        }

        return salida.toUpperCase();
    }

    private static boolean esVocal(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    private static String getFecha(String fecha){
        String salida;

        String[] temp = fecha.split("-");

        salida = temp[2].substring(2);
        salida += temp[1];
        salida += temp[0];

        return salida;
    }

    private static String genClave(){
        String salida = "";
        String clave = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        salida += clave.charAt(rnd.nextInt(clave.length()));
        salida += clave.charAt(rnd.nextInt(clave.length()));
        salida += clave.charAt(rnd.nextInt(clave.length()));

        return salida;
    }

    private static String checkInicial(final String target) {
        String salida = target;
        String antisonantes = "CACA,CAGA,PITO,PENE,PUTA,PEPA,NACA,LOCA,NEPE,JOTO";

        if (antisonantes.contains(salida)) {
            salida = salida.substring(0,3) + "X";
        }

        return salida;
    }

    private static boolean validarFecha(String fecha) {
        String regex = "^([0-2][0-9]|(3)[0-1])-((0)[0-9]|(1)[0-2])-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.matches();
    }
   
}
