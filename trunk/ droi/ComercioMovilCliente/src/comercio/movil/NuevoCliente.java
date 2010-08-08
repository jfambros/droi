package comercio.movil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.Categorias;
import utils.DatosCliente;
import utils.DatosClienteKS;
import utils.Pais;
import utils.Validaciones;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NuevoCliente extends Activity{
	private Spinner spinnPais;
	private ArrayList<Pais> arregloPaises;
	private ArrayList<String> nombPais;
	private ImageView ivCalendarioNvoCte;
	private ImageView ivContinuarNvoCte;
	//calendario
	private int aniodp, mesdp, diadp;
	private static final int ID_DATEPICKER = 0;
	private DatePickerDialog dp;
	
	//botones
	private RadioGroup rgSexo = null;
	private ImageView ivInicio;
	private ImageView ivCesta;
	   
	private char sexo;
	private int pais;
    private String anio, mes, dia;
	private DatosClienteKS dcks = new DatosClienteKS();


	private String HOST = "10.0.2.2"; //esto es para el equipo local
	 public void onCreate(Bundle savedInstanceState) {
		 try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nuevocliente);
            //botones
	        spinnPais = (Spinner) findViewById(R.id.spinnerNvoCte);
	        ivCalendarioNvoCte = (ImageView) findViewById(R.id.ivCalendarioNvoCte);
	        ivCalendarioNvoCte.setOnClickListener(ivCalendarioNvoCtePres);
	        
	        ivContinuarNvoCte = (ImageView)findViewById(R.id.ivContinuarNvoCte);
	        ivContinuarNvoCte.setOnClickListener(ivContinuarNvoCtePres);
	        
	        ivInicio = (ImageView)findViewById(R.id.ivInicioNuevoCliente);
	        ivInicio.setOnClickListener(ivInicioPres);
	        
	        ivCesta = (ImageView)findViewById(R.id.ivCestaNuevoCliente);
	        ivCesta.setOnClickListener(ivCestaPres);
           
	       rgSexo = (RadioGroup) findViewById(R.id.rgNuevoCliente);
	 	   rgSexo.setOnCheckedChangeListener(rgSexoPres);
	 	   
	 	   sexo='0';
	 	   
	        llenaPais();
		 }
		 catch(Exception err){
			 Log.e("Error",err.toString());
		 }
	  }
	 
	 
	 
	 
	 private void llenaPais(){
		 //Definición para servicio Web
			String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerPaises";
		    String METHOD_NAME = "obtenerPaises";
		    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
		    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
		    SoapSerializationEnvelope envelope;
		    HttpTransportSE httpt;
		    SoapObject result;
		 //Fin definición
		    try{
			    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            result = (SoapObject)envelope.bodyIn;
	            SoapObject result2 =  (SoapObject) envelope.getResponse();
	            arregloPaises = new ArrayList<Pais>();
		        nombPais = new ArrayList<String>();
	            for(int cont=0; cont< result2.getPropertyCount(); cont ++){
	            	 SoapObject resultados = (SoapObject) result2.getProperty(cont);
	            	 //primitivas
	            	 SoapPrimitive idPais = (SoapPrimitive) resultados.getProperty("idPais");
	            	 SoapPrimitive nombrePais = (SoapPrimitive) resultados.getProperty("nombrePais");

	            	 Pais p = new Pais();
		        	 p.setIdPais(Integer.parseInt(idPais.toString()));
		        	 p.setNombrePais(nombrePais.toString());
		        	 arregloPaises.add(p);
		        	 nombPais.add(nombrePais.toString());
	             }
	            
		        
		        
		        

		        
		        ArrayAdapter<String> adapterPais = new ArrayAdapter<String>( 
		                this,
		                android.R.layout.simple_spinner_item,
		                nombPais );
		
		        adapterPais.setDropDownViewResource(
		                android.R.layout.simple_spinner_dropdown_item);
		        
		            spinnPais.setAdapter(adapterPais);
		            
		            spinnPais.setOnItemSelectedListener(
		                new AdapterView.OnItemSelectedListener() {
		                    public void onItemSelected(
		                            AdapterView<?> parent, 
		                            View view, 
		                            int position, 
		                            long id) {
		                        Pais d = arregloPaises.get(position);
		                        //Log.i("Pais seleccionado", d.getNombrePais());
		                        pais = d.getIdPais();
		                    }
		
							public void onNothingSelected(AdapterView<?> arg0) {
							
							}
		                }
		            );
		    }
		    catch(Exception err){
		    	Log.e("Error en llena paises", err.toString());
		    }
        
	 }
	 
	 private void altaCliente(){
		 if (validaDatos() == true){
			 try{
			 //Definición para servicio Web
				String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#nuevoCliente";
			    String METHOD_NAME = "nuevoCliente";
			    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
			    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
			    SoapSerializationEnvelope envelope;
			    HttpTransportSE httpt;
			 //Fin definición
			    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
   
		        
		        request.addProperty ("cliente", dcks);
	            /*
	             * <sexoCliente>String</sexoCliente>
				<nombreCliente>String</nombreCliente>
				<apellidoCliente>String</apellidoCliente>
				<fechaNacCliente>String</fechaNacCliente>
				<emailCliente>String</emailCliente>
				<empresaCliente>String</empresaCliente>
				<direccCliente>String</direccCliente>
				<coloniaCliente>String</coloniaCliente>
				<cpCliente>String</cpCliente>
				<ciudadCliente>String</ciudadCliente>
				<estadoCliente>String</estadoCliente>
				<paisCliente>String</paisCliente>
				<telefonoCliente>String</telefonoCliente>
				<faxCliente>String</faxCliente>
				<noticiasCliente>String</noticiasCliente>
				<passwdCliente>String</passwdCliente>
	             */
	            
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            SoapObject result =  (SoapObject) envelope.bodyIn;
	            SoapPrimitive id = (SoapPrimitive) result.getProperty("result");
	            Log.i("Cliente insertado", id.toString());
	            
	            
	            
			 }
			 catch(Exception err){
				 Log.e("error inserta cliente", err.toString());
			 }
		    
		    
		 }
	 }
	 
	 private boolean validaDatos(){
		 EditText etNombre = (EditText) findViewById(R.id.etNombreNvoCte);
		 EditText etApellidos = (EditText) findViewById(R.id.etApellidosNvoCte);
		 EditText etFecha = (EditText) findViewById(R.id.etFechaNacNvoCte);
		 EditText etEmail = (EditText) findViewById(R.id.etEMailNvoCte);
		 EditText etEmpresa = (EditText) findViewById(R.id.etEmpresaNvoCte);
		 EditText etDireccion = (EditText) findViewById(R.id.etDireccionNvoCte);
		 EditText etColonia = (EditText) findViewById(R.id.etColoniaNvoCte);
		 EditText etCP = (EditText) findViewById(R.id.etCodPostalNvoCte);
		 EditText etCiudad = (EditText) findViewById(R.id.etCiudadNvoCte);
		 EditText etEstado = (EditText) findViewById(R.id.etEstadoNvoCte);
		 EditText etTelefono = (EditText) findViewById(R.id.etTelefonoNvoCte);
		 EditText etFax = (EditText) findViewById(R.id.etFaxNvoCte);
		 EditText etContra = (EditText) findViewById(R.id.etContraNvoCte);
		 EditText etConfContra = (EditText) findViewById(R.id.etConfContraNvoCte);
		 
		 if (etApellidos.length()== 0 ||
			 etCiudad.length() == 0 ||
			 etColonia.length() == 0 ||
			 etContra.length() == 0 ||
			 etConfContra.length() == 0 ||
			 etCP.length() == 0 ||
			 etDireccion.length() == 0 ||
			 etEmail.length() == 0 ||
			 etEstado.length() == 0 ||
			 etFecha.length() == 0 ||
			 etNombre.length() == 0 ||
			 etTelefono.length() == 0 ||
			 sexo == '0' ||
			 !etContra.getText().toString().equals(etConfContra.getText().toString()) ||
			 !Validaciones.esEmail(etEmail.getText().toString()) ||
			 !Validaciones.esNumero(etCP.getText().toString()) ||
			 !Validaciones.esNumero(etTelefono.getText().toString()) ||
			 !Validaciones.esNumero(etFax.getText().toString()) || 
			 !Validaciones.validaFecha(etFecha.getText().toString(), true, "dd/MM/yyyy")
		     ){
			 
			 if(sexo == '0'){
		        	mensajeError("Faltan datos","Seleccione el sexo");
		     }
			 if(etApellidos.length() == 0){
		        	mensajeError("Faltan datos","Capture su(s) apellido(s)");
		     }
			 if(etCiudad.length() == 0){
		        	mensajeError("Faltan datos","Capture su ciudad");
		     }
			 if(etColonia.length() == 0){
		        	mensajeError("Faltan datos","Capture su colonia");
		     }
			 if(etConfContra.length() == 0){
		        	mensajeError("Faltan datos","Confirme su contraseña");
		     }
			 if(etContra.length() == 0){
		        	mensajeError("Faltan datos","Capture su contraseña");
		     }
			 
			 if(!etContra.getText().toString().equals(etConfContra.getText().toString())){
		        	mensajeError("Faltan datos","No coincide la contraseña");
		     }
			 if(etCP.length() == 0){
		        	mensajeError("Faltan datos","Capture su código postal");
		     }			 
			 if(etDireccion.length() == 0){
		        	mensajeError("Faltan datos","Capture su dirección");
		     }
			 if(etEmail.length() == 0){
		        	mensajeError("Faltan datos","Capture su e-mail");
		     }
			 if (!Validaciones.esEmail(etEmail.getText().toString())){
				 mensajeError("Faltan datos","No es un e-mail válido");
			 }
			 
			 if(etEstado.length() == 0){
		        	mensajeError("Faltan datos","Capture su estado");
		     }
			 if(etFecha.length() == 0){
		        	mensajeError("Faltan datos","Capture su fecha de nacimiento");
		     }			
			 if(etNombre.length() == 0){
		        	mensajeError("Faltan datos","Capture su nombre");
		     }
			 if(etTelefono.length() == 0){
		        	mensajeError("Faltan datos","Capture su teléfono");
		     }
			 if (!Validaciones.esNumero(etTelefono.getText().toString())){
				 mensajeError("Error","Teléfono no válido");
			 }
			 if (!Validaciones.esNumero(etFax.getText().toString())){
				 mensajeError("Error","Fax no válido");
			 }
			 if (!Validaciones.esNumero(etCP.getText().toString())){
				 mensajeError("Error","Código postal no válido");
			 }
			 if (!Validaciones.validaFecha(etFecha.getText().toString(), true, "dd/MM/yyyy")){
				 mensajeError("Error","Fecha inválida, escriba con formato dd/mm/aaaa");
			 }
			 return false;
			 
		 }
		 else{
			 String fechaTemp = etFecha.getText().toString();
        	 String dia = fechaTemp.substring(0,2);
        	 String mes = fechaTemp.substring(3,5);
        	 String anio = fechaTemp.substring(6, 10);

			 dcks.setApellidoCliente(etApellidos.getText().toString());
			 dcks.setCiudadCliente(etCiudad.getText().toString());
			 dcks.setColoniaCliente(etColonia.getText().toString());
			 dcks.setCpCliente(etCP.getText().toString());
			 dcks.setDireccCliente(etDireccion.getText().toString());
			 dcks.setEmailCliente(etEmail.getText().toString());
			 dcks.setEmpresaCliente(etEmpresa.getText().toString());
			 dcks.setEstadoCliente(etEstado.getText().toString());
			 dcks.setFaxCliente(etFax.getText().toString());
			 dcks.setFechaNacCliente(anio+"/"+mes+"/"+dia);
			 dcks.setNombreCliente(etNombre.getText().toString());
			 dcks.setNoticiasCliente("0");
			 dcks.setPaisCliente(String.valueOf(pais));
			 dcks.setPasswdCliente(etContra.getText().toString());
			 dcks.setSexoCliente(String.valueOf(sexo));
			 dcks.setTelefonoCliente(etTelefono.getText().toString());
			 
			 
			 return true;
		 }
/*		 
        if(sexo == '0'){
            
        	mensajeError("Seleccione el sexo");
        }
        else{
        	Log.i("sexo",String.valueOf(sexo));
        }
        */
	 }
	 
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(NuevoCliente.this)

     	.setTitle(titulo)

     	.setMessage(msj)

     	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

     	public void onClick(DialogInterface dialog, int whichButton) {

     	setResult(RESULT_OK);
     	  }
     	})
     	.show();   
	 }
	 
	 
	 private OnCheckedChangeListener rgSexoPres = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup arg0, int check) {
			if(check == R.id.rgFemenino){
				Log.i("Sexo","Femenino f");
				sexo='f';
			}
			else{
				Log.i("Sexo","Masculino m");
				sexo='m';
			}
			//Log.i("seleccionado","Masculino m");
		}
	};
	 
	 
	 private OnClickListener ivCalendarioNvoCtePres = new OnClickListener() {
		
		public void onClick(View arg0) {
		   final Calendar c = Calendar.getInstance();
		   //c.setTimeZone(TimeZone.getTimeZone("Mexico/BajaNorte"));

		    aniodp = c.get(Calendar.YEAR);
		    mesdp = c.get(Calendar.MONTH);
		    diadp = c.get(Calendar.DAY_OF_MONTH);
		    showDialog(ID_DATEPICKER);
		}
	};
	
	private OnClickListener ivContinuarNvoCtePres = new OnClickListener() {
		
		public void onClick(View arg0) {
			altaCliente();
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(NuevoCliente.this, Principal.class);
			startActivity(intent);
			finish();
		}
	};
	
	private OnClickListener ivCestaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(NuevoCliente.this, Cesta.class);
			startActivity(intent);
			finish();			
		}
	};
	
	
	protected Dialog onCreateDialog(int id) {
		/*
		SimpleDateFormat formatF = new SimpleDateFormat("dd/MM/yyyy");
		switch(id){
		   case ID_DATEPICKER:
			   final Calendar c = Calendar.getInstance();
			    anio = c.get(Calendar.YEAR);
			    mes = c.get(Calendar.MONTH);
			    dia = c.get(Calendar.DAY_OF_MONTH);
			    dp = new DatePickerDialog(this, myDateSetListener, anio, mes, dia);

			   try{
			   Date date = formatF.parse(formatF.format(c.getTime()));
			   c.setTime(date);
			   dp.setTitle(formatF.format(date));
			   Log.i("Fecha: ",formatF.format(date));
			   dp.updateDate(
					   c.get(Calendar.YEAR),
					   c.get(Calendar.MONTH),
					   c.get(Calendar.DAY_OF_MONTH)
					   );
			   }
			   catch (ParseException e) {
				   dp.updateDate(anio, mes,dia);
			   }
			   return dp;
		   default:
			    return null;
		}
		*/
		
		  switch(id){
		   case ID_DATEPICKER:
		    return new DatePickerDialog(this,
		      myDateSetListener,
		      aniodp, mesdp, diadp);
		   default:
		    return null;
		  }
		  
	}

	private DatePickerDialog.OnDateSetListener myDateSetListener
	  = new DatePickerDialog.OnDateSetListener(){
	   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
   
		   
		   TextView tvCalendarioNvoCte = (TextView) findViewById(R.id.etFechaNacNvoCte);
		   dia = String.valueOf(dayOfMonth);
		   mes = String.valueOf(monthOfYear+1);
		   anio = String.valueOf(year);
		   if (dia.length() == 1){
			   dia = "0"+dia;
		   }
		   if (mes.length() == 1){
			   mes = "0"+mes;
		   }
		   
		   String fecha = dia+"/"+
		   				  mes+ "/"+
		   				  year;
	    
	       tvCalendarioNvoCte.setText(fecha);
	       
	    
	   } 
	   
	 };
	 
}
