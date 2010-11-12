package comercio.movil;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosDireccion;
import utils.DatosDireccionKS;
import utils.Pais;
import utils.Validaciones;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NuevaDireccionEntrega extends Activity{
	private Bundle bundle = null;
	private String HOST = Valores.HOST;
	private ArrayList<DatosDireccion> arrayDireccCliente = new ArrayList<DatosDireccion>();
	private ArrayList<String> direccionCliente;
	private ArrayList<String> direccActualCliente = new ArrayList<String>();
	private ArrayList<Pais> arregloPaises;
	private ArrayList<String> nombPais;
	private DatosDireccionKS ddks = new DatosDireccionKS(); 

	private int idCliente;
	private String email;
	private int pais;
	private String nombrePais;
	private char sexo = '0';
	
	private RadioGroup rgSexo = null;
	private ImageView ivContinuar = null;
	private ImageView ivContinuarArr = null;
	private ImageView ivGuardarDir = null;
	private ImageView ivInicio = null;
	
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nuevadireccionentrega);
	        
	        rgSexo = (RadioGroup)findViewById(R.id.rgSexoNuevaDirecc1);
	        rgSexo.setOnCheckedChangeListener(rgSexoPres);
	        
	        ivContinuar = (ImageView)findViewById(R.id.ivContinuarNuevaDirecc1);
	        ivContinuar.setOnClickListener(ivContinuarPres);
	        
	        ivContinuarArr = (ImageView)findViewById(R.id.ivContinuarArrNuevaDir1);
	        ivContinuarArr.setOnClickListener(ivContinuarPres);

	        
	        ivGuardarDir = (ImageView)findViewById(R.id.ivGuardarNuevaDirecc1);
	        ivGuardarDir.setOnClickListener(ivGuardarDirPres);
	        
	        ivInicio = (ImageView)findViewById(R.id.ivInicioNuevaDireccEntre);
	        ivInicio.setOnClickListener(ivInicioPres);
	        
	        
	        bundle = getIntent().getExtras();
	        idCliente = bundle.getInt("idCliente");
	        email = bundle.getString("emailCliente");
	        
	        llenaDirecciones(idCliente);
	        llenaPais();
	        
	        direccActualCliente = new ArrayList<String>();
	        direccActualCliente = bundle.getStringArrayList("direccionCliente");
	        llenaDirecc(direccActualCliente);
	        
	 }
	 
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if (keyCode == KeyEvent.KEYCODE_BACK) {
	            return false;
	        }
	    	return super.onKeyDown(keyCode, event);
	    }
	 
	 private void llenaDirecciones(int idClienteP){
		 String empresa = "";
		 
		 Spinner spinnDirecc = (Spinner)findViewById(R.id.spinnerSelDireccNuevaDirecc1);
				 
		//Definición para servicio Web
			String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerLibretaDirecciones";
		    String METHOD_NAME = "obtenerLibretaDirecciones";
		    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
		    String URL = "http://"+HOST+"/servicios/servicios.php";
		    SoapSerializationEnvelope envelope;
		    HttpTransportSE httpt;
		    
		    ArrayList<String> datosDirecc = new ArrayList<String>();

		    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("idCliente", idClienteP);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        
	        SoapObject result =  (SoapObject) envelope.getResponse();
	        

            
	        
	        for(int cont=0; cont< result.getPropertyCount(); cont ++){
           	 SoapObject resultados = (SoapObject) result.getProperty(cont);
           	 //primitivas
           	 SoapPrimitive idLibretaDir = (SoapPrimitive) resultados.getProperty("idLibretaDir");
           	 SoapPrimitive idCliente = (SoapPrimitive) resultados.getProperty("idCliente");
           	 SoapPrimitive sexoCliente = (SoapPrimitive) resultados.getProperty("sexoCliente");
           	if (resultados.getProperty("empresaCliente").toString().equals("anyType{}")){
           		empresa="";
           	}
           	else{
           	   SoapPrimitive empresaCliente = (SoapPrimitive) resultados.getProperty("empresaCliente");
           	   empresa = empresaCliente.toString();
           	}
           	 
           	 SoapPrimitive nombreCliente = (SoapPrimitive) resultados.getProperty("nombreCliente");
           	 SoapPrimitive apellidoCliente = (SoapPrimitive) resultados.getProperty("apellidoCliente");
           	 SoapPrimitive direccCliente = (SoapPrimitive) resultados.getProperty("direccCliente");
           	 SoapPrimitive coloniaCliente = (SoapPrimitive) resultados.getProperty("coloniaCliente");
           	 SoapPrimitive cpCliente = (SoapPrimitive) resultados.getProperty("cpCliente");
           	 SoapPrimitive ciudadCliente = (SoapPrimitive) resultados.getProperty("ciudadCliente");
           	 SoapPrimitive estadoCliente = (SoapPrimitive) resultados.getProperty("estadoCliente");
           	 SoapPrimitive paisCliente = (SoapPrimitive) resultados.getProperty("nombrePais");
           	 SoapPrimitive idZona = (SoapPrimitive) resultados.getProperty("idZona");

           	 DatosDireccion dd = new DatosDireccion();
           	 dd.setNombreCliente(nombreCliente.toString()+" "+apellidoCliente.toString());
           	 dd.setEmpresaCliente(empresa);
           	 dd.setDireccCliente(direccCliente.toString());
           	 dd.setColoniaCliente(coloniaCliente.toString());
           	 dd.setCiudadCliente(ciudadCliente.toString());
           	 dd.setCpCliente(cpCliente.toString());
           	 dd.setEstadoCliente(estadoCliente.toString());
           	 dd.setPaisCliente(paisCliente.toString());
           	 
           	 
           	 
	        arrayDireccCliente.add(dd);
           	 
	        
	        datosDirecc.add(nombreCliente.toString()+" "+apellidoCliente.toString()+","+
    		        direccCliente.toString()+", "+
    		        coloniaCliente.toString()+", "+
    		        ciudadCliente.toString()+", "+
    		        cpCliente.toString()+", "+
    		        estadoCliente.toString()+", "+
    		        paisCliente.toString()
    		        );
	        
	        ArrayAdapter<String> adapterDirecc = new ArrayAdapter<String>( 
	                this,
	                android.R.layout.simple_spinner_item,
	                datosDirecc);
	        
	        adapterDirecc.setDropDownViewResource(
	                android.R.layout.simple_spinner_dropdown_item);
	        
	        spinnDirecc.setAdapter(adapterDirecc);
	        
	        spinnDirecc.setOnItemSelectedListener(
	                new AdapterView.OnItemSelectedListener() {
	                    public void onItemSelected(
	                            AdapterView<?> parent, 
	                            View view, 
	                            int position, 
	                            long id) {
	                        DatosDireccion d = arrayDireccCliente.get(position);
 
	                        direccionCliente  = new ArrayList<String>();
	            	        direccionCliente.add(d.getNombreCliente());
	            	        direccionCliente.add(d.getEmpresaCliente());
	            	        direccionCliente.add(d.getDireccCliente());
	            	        direccionCliente.add(d.getColoniaCliente());
	            	        direccionCliente.add(d.getCiudadCliente());
	            	        direccionCliente.add(d.getCpCliente());
	            	        direccionCliente.add(d.getEstadoCliente());
	            	        direccionCliente.add(d.getPaisCliente());
	            	        llenaDirecc(direccionCliente);
	                    }
	                    
						public void onNothingSelected(AdapterView<?> arg0) {
							//direccionCliente = bundle.getStringArrayList("direccionCliente");
						}
	                }
            );

            }
	        
	        
	        
		    }
		    catch (Exception e) {
		    	Log.e("Error LlenaDir", e.toString());
			}
		    
	 }
	 
	 private void llenaDirecc(ArrayList<String> direccionActualCliente){
		//objetos para visualizar
		    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaNuevaDirecc1);
		    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteNuevaDirecc1);
		    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionNuevaDirecc1);
		    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaNuevaDirecc1);
		    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadCPNuevaDirecc1);
		    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisNuevaDirecc1);
		    
		   
		    
		    tvNombreCte.setText(direccionActualCliente.get(0));
		    tvEmpresaCte.setText(direccionActualCliente.get(1));
	        tvDireccionCte.setText(direccionActualCliente.get(2));
	        tvColoniaCte.setText(direccionActualCliente.get(3));
	        tvCiudadYCPCte.setText(direccionActualCliente.get(4)+", C.P. "+direccionActualCliente.get(5));
	        tvEsatdoYPaisCte.setText(direccionActualCliente.get(6)+", "+direccionActualCliente.get(7));
	 }
	 
	 private void llenaPais(){
		 //Definición para servicio Web
			String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerPaises";
		    String METHOD_NAME = "obtenerPaises";
		    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
		    String URL = "http://"+HOST+"/servicios/servicios.php";
		    SoapSerializationEnvelope envelope;
		    HttpTransportSE httpt;
		    SoapObject result;
		 //Fin definición
		    
		    Spinner spinnPais = (Spinner)findViewById(R.id.spinnerPaisNuevaDirecc1);
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
		                        nombrePais = d.getNombrePais();
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
	 
	 private void guardaDireccion(){
		 if (validaDatos() == true){
		//Definición para servicio Web
			String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#insertaLibretaDireccion";
		    String METHOD_NAME = "insertaLibretaDireccion";
		    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
		    String URL = "http://"+HOST+"/servicios/servicios.php";
		    SoapSerializationEnvelope envelope;
		    HttpTransportSE httpt;
		 //Fin definición
		 //objetos
		    try{
		    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        request.addProperty ("direccion", ddks);
		        
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            SoapObject result =  (SoapObject) envelope.bodyIn;
	            SoapPrimitive id = (SoapPrimitive) result.getProperty("result");
	            Log.i("Dirección insertada", id.toString());
	            
	            //cambiamos a la nueva dirección
                direccionCliente  = new ArrayList<String>();
    	        direccionCliente.add(ddks.getNombreCliente()+" "+ddks.getApellidoCliente());
    	        direccionCliente.add(ddks.getEmpresaCliente());
    	        direccionCliente.add(ddks.getDireccCliente());
    	        direccionCliente.add(ddks.getColoniaCliente());
    	        direccionCliente.add(ddks.getCiudadCliente());
    	        direccionCliente.add(ddks.getCpCliente());
    	        direccionCliente.add(ddks.getEstadoCliente());
    	        direccionCliente.add(nombrePais);
    	        
    	        Intent intent = new Intent();
    	        intent.putExtra("idCliente", idCliente);
    	        intent.putExtra("emailCliente", email);
    	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
    	        if (bundle.getString("comentario") != null ){
    	        	intent.putExtra("comentario", bundle.getString("comentario"));
    	        }
    	        intent.setClass(NuevaDireccionEntrega.this, RevisaPedido1.class);
    	        Toast.makeText(this, "Dirección actualizada", Toast.LENGTH_SHORT);
    	        startActivity(intent);
    	        finish();
		    	
		    }
		    catch (Exception e) {
		    	Log.e("error guardaDirecc", e.toString());
			}
		    
		 }
		    
		    
		    
	 }
	 
	 private boolean validaDatos(){
		 EditText etNombre = (EditText) findViewById(R.id.etNombreNuevaDirecc1);
		 EditText etApellidos = (EditText) findViewById(R.id.etApellidosNuevaDirecc1);
		 EditText etEmpresa = (EditText) findViewById(R.id.etEmpresaNuevaDirecc1);
		 EditText etDireccion = (EditText) findViewById(R.id.etDireccionNuevaDirecc1);
		 EditText etColonia = (EditText) findViewById(R.id.etColoniaNuevaDirecc1);
		 EditText etCP = (EditText) findViewById(R.id.etCodPostalNuevaDirecc1);
		 EditText etCiudad = (EditText) findViewById(R.id.etCiudadNuevaDirecc1);
		 EditText etEstado = (EditText) findViewById(R.id.etEstadoNuevaDirecc);
		 
		 if (etApellidos.length()== 0 ||
			 etCiudad.length() == 0 ||
			 etColonia.length() == 0 ||
			 etCP.length() == 0 ||
			 etDireccion.length() == 0 ||
			 etEstado.length() == 0 ||
			 etNombre.length() == 0 ||
			 sexo == '0' ||
			 !Validaciones.esNumero(etCP.getText().toString())){
			 
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
			 if(etCP.length() == 0){
		        	mensajeError("Faltan datos","Capture su código postal");
		     }			 
			 if(etDireccion.length() == 0){
		        	mensajeError("Faltan datos","Capture su dirección");
		     }
			 if(etEstado.length() == 0){
		        	mensajeError("Faltan datos","Capture su estado");
		     }
			 if(etNombre.length() == 0){
		        	mensajeError("Faltan datos","Capture su nombre");
		     }
			 if (!Validaciones.esNumero(etCP.getText().toString())){
				 mensajeError("Error","Código postal no válido");
			 }
			 return false;
			 
		 }
		 else{
			 ddks.setIdLibretaDir(0);
			 ddks.setIdCliente(idCliente);
			 ddks.setSexoCliente(String.valueOf(sexo));
			 ddks.setEmpresaCliente(etEmpresa.getText().toString());
			 ddks.setNombreCliente(etNombre.getText().toString());
			 ddks.setApellidoCliente(etApellidos.getText().toString());
			 ddks.setDireccCliente(etDireccion.getText().toString());
			 ddks.setColoniaCliente(etColonia.getText().toString());
			 ddks.setCpCliente(etCP.getText().toString());
			 ddks.setCiudadCliente(etCiudad.getText().toString());
			 ddks.setEstadoCliente(etEstado.getText().toString());
			 ddks.setNombrePais(pais);
			 ddks.setIdZona(0);

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
		 new AlertDialog.Builder(NuevaDireccionEntrega.this)

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
			if(check == R.id.rgFemeninoNuevaDirecc1){
				Log.i("Sexo","Femenino f");
				sexo='f';
			}
			if (check == R.id.rgMasculinoNuevaDirecc1){
				Log.i("Sexo","Masculino m");
				sexo='m';
			}			
		}
	};
	 
	 private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.putExtra("idCliente", idCliente);
	        intent.putExtra("emailCliente", email);
	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        if (bundle.getStringArrayList("direccionFactura")!= null){
		        intent.putStringArrayListExtra("direccionFactura", bundle.getStringArrayList("direccionFactura"));
	        }
	        if (bundle.getString("comentario") != null ){
	        	intent.putExtra("comentario", bundle.getString("comentario"));
	        }
	        intent.setClass(NuevaDireccionEntrega.this, RevisaPedido1.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivGuardarDirPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			guardaDireccion();
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.setClass(NuevaDireccionEntrega.this, Principal.class);
	        startActivity(intent);
	        finish();
		
		}
	};
	 
}
