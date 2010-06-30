package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

	
	public static boolean esEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        mat = pat.matcher(correo); 
        if (mat.find()) {
            //System.out.println("[" + mat.group() + "]");
            return true;
        }else{
            return false;
        }        
    }
	
	public static boolean esNumero(String cadena){
		if (cadena.matches("[0-9]*")){
			return true;
		}
		else{
			return false;
		}

	}

	public static boolean validaFecha(String fechaStr, boolean permitePas, String formato)	{

		if (formato == null) 
			return false; // or throw some kinda exception, possibly a InvalidArgumentException
		SimpleDateFormat df = new SimpleDateFormat(formato);
		Date testDate = null;
		try
		{
			testDate = df.parse(fechaStr);
		}
		catch (ParseException e)
		{
		// invalid date format
			return false;
		}
		if (!permitePas)
		{
			// initialise the calendar to midnight to prevent 
			// the current day from being rejected
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			if (cal.getTime().after(testDate)) 
				return false;
		}
		// now test for legal values of parameters
		if (!df.format(testDate).equals(fechaStr)) return false;
		return true;
		}
	}
