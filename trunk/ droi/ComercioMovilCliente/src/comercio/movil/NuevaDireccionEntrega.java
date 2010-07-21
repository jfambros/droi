package comercio.movil;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosDireccion;
import utils.Pais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class NuevaDireccionEntrega extends Activity{
	private Bundle bundle = null;
	private String HOST = "10.0.2.2";
	private ArrayList<DatosDireccion> arrayDireccCliente = new ArrayList<DatosDireccion>();
	private ArrayList<String> direccionCliente;
	private int idCliente;
	
	private ImageView ivContinuar = null;
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nuevadireccionentrega);
	        
	        ivContinuar = (ImageView)findViewById(R.id.ivContinuarNuevaDirecc1);
	        ivContinuar.setOnClickListener(ivContinuarPres);
	        
	        bundle = getIntent().getExtras();
	        idCliente = bundle.getInt("idCliente");
	        llenaDirecciones(idCliente);
	        
	 }
	 
	 private void llenaDirecciones(int idClienteP){
		 String empresa = "";
		 
		 Spinner spinnDirecc = (Spinner)findViewById(R.id.spinnerSelDireccNuevaDirec1);

		 
		//Definición para servicio Web
			String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerLibretaDirecciones";
		    String METHOD_NAME = "obtenerLibretaDirecciones";
		    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
		    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
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
	                    }
	                    
						public void onNothingSelected(AdapterView<?> arg0) {
							
						}
	                }
            );

            }
	        
	        
	        
		    }
		    catch (Exception e) {
		    	Log.e("Error LlenaDir", e.toString());
			}
		    
	 }
	 
	 private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.putExtra("idCliente", idCliente);
	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        intent.setClass(NuevaDireccionEntrega.this, RevisaPedido1.class);
	        startActivity(intent);
	        finish();
		}
	};
	 
}
