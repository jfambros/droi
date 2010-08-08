package comercio.movil;

import java.util.ArrayList;
import java.util.Calendar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosActualizaCliente;
import utils.DatosActualizaClienteKS;
import utils.DatosClienteKS;
import utils.Pais;
import utils.Validaciones;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ModificaDatosCliente extends Activity{
	
	private Bundle bundle;
	private String HOST = Valores.HOST;
	private String emailCliente;
	private String idClienteA;
	private ArrayList<Pais> arregloPaises;
	private ArrayList<String> nombPais;
	private Spinner spinnPais;
	private int pais;
    private String anio, mes, dia;
	private int aniodp, mesdp, diadp;    
	private static final int ID_DATEPICKER = 0;
	private char sexo;
	private DatosActualizaClienteKS dacks = new DatosActualizaClienteKS();
	
	private ImageView ivCalendario;
	private ImageView ivContinuar;
	private RadioGroup rgSexo = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modificadatoscliente);
		
		spinnPais = (Spinner) findViewById(R.id.spinnerPaisModificaDatos);
		ivCalendario = (ImageView) findViewById(R.id.ivCalendarioModificaDatos);
        ivCalendario.setOnClickListener(ivCalendarioPres);
        
        ivContinuar = (ImageView) findViewById(R.id.ivContinuarModificaDatos);
        ivContinuar.setOnClickListener(ivContinuarPres);
        
        rgSexo = (RadioGroup) findViewById(R.id.rgModificaDatos);
 	    rgSexo.setOnCheckedChangeListener(rgSexoPres);
 	    
 	    RadioButton rbFem = (RadioButton)findViewById(R.id.rbFemeninoModificaDatos);
 	    RadioButton rbMas = (RadioButton)findViewById(R.id.rbMasculinoModifcaDatos);
 	    
 	    if (rbFem.isChecked()){
 	    	sexo = 'f';
 	    }
 	    if (rbMas.isChecked()){
 	    	sexo = 'm';
 	    }
		
		bundle = getIntent().getExtras();
		
		emailCliente = bundle.getString("emailCliente");
		
		obtenerDatosCliente();
		llenaPais();
	}
	
	private void obtenerDatosCliente(){
		 //Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result;
	 //Fin definición
	    //objetos a visualizar
	    
	    EditText etNombreCliente = (EditText)findViewById(R.id.etNombreModificaDatos);
	    EditText etApellidoCliente = (EditText)findViewById(R.id.etApellidosModificaDatos);
	    EditText etFechaNacCliente = (EditText)findViewById(R.id.etFechaNacModificaDatos);
	    EditText etEmailCliente = (EditText)findViewById(R.id.etEMailModificaDatos);
	    EditText etEmpresaCliente = (EditText)findViewById(R.id.etEmpresaModificaDatos);
	    EditText etDireccCliente = (EditText)findViewById(R.id.etDireccionModificaDatos);
	    EditText etColoniaCliente = (EditText)findViewById(R.id.etColoniaModificaDatos);
	    EditText etCPCliente = (EditText)findViewById(R.id.etCodPostalModificaDatos);
	    EditText etCiudadCliente = (EditText)findViewById(R.id.etCiudadModificaDatos);
	    EditText etEstadoCliente = (EditText)findViewById(R.id.etEstadoModificaDatos);
	    EditText etTelefonoCliente = (EditText)findViewById(R.id.etTelefonoModificaDatos);
	    EditText etFaxCliente = (EditText)findViewById(R.id.etFaxModificaDatos);
	    
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
            request.addProperty ("emailCliente", emailCliente);	        
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
            result = (SoapObject)envelope.bodyIn;
            SoapObject resultado =  (SoapObject) envelope.getResponse();
            
            SoapPrimitive idCliente = (SoapPrimitive) resultado.getProperty("idCliente");
            SoapPrimitive nombreCliente = (SoapPrimitive) resultado.getProperty("nombreCliente");
            SoapPrimitive apellidoCliente = (SoapPrimitive) resultado.getProperty("apellidoCliente");
            SoapPrimitive fechaNacCliente = (SoapPrimitive) resultado.getProperty("fechaNacCliente");
            SoapPrimitive emailCliente = (SoapPrimitive) resultado.getProperty("emailCliente");
            SoapPrimitive empresaCliente = (SoapPrimitive) resultado.getProperty("empresaCliente");
            SoapPrimitive direccCliente = (SoapPrimitive) resultado.getProperty("direccCliente");
            SoapPrimitive coloniaCliente = (SoapPrimitive) resultado.getProperty("coloniaCliente");
            SoapPrimitive cpCliente = (SoapPrimitive) resultado.getProperty("cpCliente");
            SoapPrimitive ciudadCliente = (SoapPrimitive) resultado.getProperty("ciudadCliente");
            SoapPrimitive estadoCliente = (SoapPrimitive) resultado.getProperty("estadoCliente");
            SoapPrimitive paisCliente = (SoapPrimitive) resultado.getProperty("paisCliente");
            SoapPrimitive telefonoCliente = (SoapPrimitive) resultado.getProperty("telefonoCliente");
            SoapPrimitive faxCliente = (SoapPrimitive) resultado.getProperty("faxCliente");
            SoapPrimitive sexoCliente = (SoapPrimitive) resultado.getProperty("sexoCliente");
            
            idClienteA = idCliente.toString();
            etNombreCliente.setText(nombreCliente.toString());
            etApellidoCliente.setText(apellidoCliente.toString());
            
            etEmailCliente.setText(emailCliente.toString());
            if (empresaCliente.toString().equals("_")== true){
            	etEmpresaCliente.setText("");
            }
            else{
            	etEmpresaCliente.setText(empresaCliente.toString());
            }
            	
            etDireccCliente.setText(direccCliente.toString());
            etColoniaCliente.setText(coloniaCliente.toString());
            etCPCliente.setText(cpCliente.toString());
            etCiudadCliente.setText(ciudadCliente.toString());
            etEstadoCliente.setText(estadoCliente.toString());
            etTelefonoCliente.setText(telefonoCliente.toString());
            if (faxCliente.toString().equals("_")==true){
            	etFaxCliente.setText("");
            }
            else{
            	etFaxCliente.setText(faxCliente.toString());
            }

            if (sexoCliente.toString().equals("m")==true){
            	RadioButton rbMasculino = (RadioButton)findViewById(R.id.rbMasculinoModifcaDatos);
            	rbMasculino.setChecked(true);
            }
            else{
            	RadioButton rbFemenino = (RadioButton)findViewById(R.id.rbFemeninoModificaDatos);
            	rbFemenino.setChecked(true);
            }
            
            String fechaTemp = fechaNacCliente.toString().substring(0, 10);
       	 	String anio = fechaTemp.substring(0,4);
       	 	String mes = fechaTemp.substring(5,7);
       	 	String dia = fechaTemp.substring(8, 10);
       	 	etFechaNacCliente.setText(dia+"/"+mes+"/"+anio);

            
	    }
	    catch (Exception e) {
	    	Log.e("error obtenerdatosCliente", e.toString());

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
		            spinnPais.setSelection(137);
		            
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

	 private boolean validaDatos(){
		 EditText etNombre = (EditText) findViewById(R.id.etNombreModificaDatos);
		 EditText etApellidos = (EditText) findViewById(R.id.etApellidosModificaDatos);
		 EditText etFecha = (EditText) findViewById(R.id.etFechaNacModificaDatos);
		 EditText etEmail = (EditText) findViewById(R.id.etEMailModificaDatos);
		 EditText etEmpresa = (EditText) findViewById(R.id.etEmpresaModificaDatos);
		 EditText etDireccion = (EditText) findViewById(R.id.etDireccionModificaDatos);
		 EditText etColonia = (EditText) findViewById(R.id.etColoniaModificaDatos);
		 EditText etCP = (EditText) findViewById(R.id.etCodPostalModificaDatos);
		 EditText etCiudad = (EditText) findViewById(R.id.etCiudadModificaDatos);
		 EditText etEstado = (EditText) findViewById(R.id.etEstadoModificaDatos);
		 EditText etTelefono = (EditText) findViewById(R.id.etTelefonoModificaDatos);
		 EditText etFax = (EditText) findViewById(R.id.etFaxModificaDatos);
		 
		 if (etApellidos.length()== 0 ||
			 etCiudad.length() == 0 ||
			 etColonia.length() == 0 ||
			 etCP.length() == 0 ||
			 etDireccion.length() == 0 ||
			 etEmail.length() == 0 ||
			 etEstado.length() == 0 ||
			 etFecha.length() == 0 ||
			 etNombre.length() == 0 ||
			 etTelefono.length() == 0 ||
			 !Validaciones.esEmail(etEmail.getText().toString()) ||
			 !Validaciones.esNumero(etCP.getText().toString()) ||
			 !Validaciones.esNumero(etTelefono.getText().toString()) ||
			 !Validaciones.esNumero(etFax.getText().toString()) || 
			 !Validaciones.validaFecha(etFecha.getText().toString(), true, "dd/MM/yyyy")
		     ){

			 if(etApellidos.length() == 0){
		        	mensajeError("Faltan datos","Capture su(s) apellido(s)");
		     }
			 if(etCiudad.length() == 0){
		        	mensajeError("Faltan datos","Capture su ciudad");
		     }
			 if(etColonia.length() == 0){
		        	mensajeError("Faltan datos","Capture su colonia");
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
// 30/05/1977 1977/05/30
			 dacks.setIdCliente(idClienteA);
			 dacks.setNombreCliente(etNombre.getText().toString());
			 dacks.setApellidoCliente(etApellidos.getText().toString());
			 dacks.setCiudadCliente(etCiudad.getText().toString());
			 dacks.setColoniaCliente(etColonia.getText().toString());
			 dacks.setCpCliente(etCP.getText().toString());
			 dacks.setDireccCliente(etDireccion.getText().toString());
			 dacks.setEmailCliente(etEmail.getText().toString());
			 dacks.setEmpresaCliente(etEmpresa.getText().toString());
			 dacks.setEstadoCliente(etEstado.getText().toString());
			 dacks.setFaxCliente(etFax.getText().toString());
			 dacks.setFechaNacCliente(anio+"/"+mes+"/"+dia);
			 dacks.setPaisCliente(String.valueOf(pais));
			 dacks.setSexoCliente(String.valueOf(sexo));
			 dacks.setTelefonoCliente(etTelefono.getText().toString());
			 
			 return true;
		 }

	 }
	 
	 
	 private void actualizaCliente(){
		 if (validaDatos() == true){
			 try{
			 //Definición para servicio Web
				String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#actualizaDatosCliente";
			    String METHOD_NAME = "actualizaDatosCliente";
			    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
			    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
			    SoapSerializationEnvelope envelope;
			    HttpTransportSE httpt;
			 //Fin definición
			    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
   
		        
		        request.addProperty ("cliente", dacks);
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
	            Log.i("Cliente actualizado", id.toString());
	            
	            
	            
			 }
			 catch(Exception err){
				 Log.e("error inserta cliente", err.toString());
			 }
		    
		    
		 }
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
	 
	 protected Dialog onCreateDialog(int id) {
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
	   
			   
			   TextView tvCalendario = (TextView) findViewById(R.id.etFechaNacModificaDatos);
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
		    
		       tvCalendario.setText(fecha);
		   } 
		   
		 };
		 
		 private OnClickListener ivCalendarioPres = new OnClickListener() {
				
				public void onClick(View arg0) {
				   final Calendar c = Calendar.getInstance();
				   //c.setTimeZone(TimeZone.getTimeZone("Mexico/BajaNorte"));

				    aniodp = c.get(Calendar.YEAR);
				    mesdp = c.get(Calendar.MONTH);
				    diadp = c.get(Calendar.DAY_OF_MONTH);
				    showDialog(ID_DATEPICKER);
				}
			};
			
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			actualizaCliente();
		}
	};
	
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(ModificaDatosCliente.this)

     	.setTitle(titulo)

     	.setMessage(msj)

     	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

     	public void onClick(DialogInterface dialog, int whichButton) {

     	setResult(RESULT_OK);
     	  }
     	})
     	.show();   
	 }

	
	
	
}


