package comercio.movil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.Valores;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActualizaContra extends Activity{
	private ImageView ivInicio;
	private ImageView ivRegresar;
	private ImageView ivContinuar;
	
	private Bundle bundle;
	private String HOST = Valores.HOST;  
	private String contraAc;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actualizacontra);

		bundle = getIntent().getExtras();
		
		ivContinuar = (ImageView)findViewById(R.id.ivContinuarActualizaContra);
		ivContinuar.setOnClickListener(ivContinuarPres);
		
		ivRegresar = (ImageView)findViewById(R.id.ivRegresarActualizaContra);
		ivRegresar.setOnClickListener(ivRegresarPres);
		
		ivInicio= (ImageView)findViewById(R.id.ivInicioActualizaContra);
		ivInicio.setOnClickListener(ivInicioPres);
		
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private boolean validaContra(){
		EditText etContra = (EditText)findViewById(R.id.etContraActualActualizaContra);
		EditText etNuevaContra = (EditText)findViewById(R.id.etNuevaContraActualizaContra);
		EditText etConfContra = (EditText)findViewById(R.id.etConfirmaContraActualizaContra);
		
		if (etNuevaContra.length() == 0 || 
		    etConfContra.length() == 0 ||
		    etContra.length() == 0 ||
		    !etNuevaContra.getText().toString().equals(etConfContra.getText().toString())
			){
			
			if (etNuevaContra.length() == 0 || 
				    etConfContra.length() == 0 ||
				    etContra.getText().length() == 0){
				mensaje("Error", "Escriba la contraseña");
			}
			
			if (!etNuevaContra.getText().toString().equals(etConfContra.getText().toString())){
				mensaje("Error", "No coincide la nueva contraseña");
			}
			
			return false;
		}
		else{
			return true;
		}
	}
	
	/*
	private boolean validaContra(){
		EditText etContra = (EditText)findViewById(R.id.etContraActualActualizaContra);
		EditText etNuevaContra = (EditText)findViewById(R.id.etNuevaContraActualizaContra);
		EditText etConfContra = (EditText)findViewById(R.id.etConfirmaContraActualizaContra);
		
		if (etNuevaContra.length() == 0 || 
		    etConfContra.length() == 0 ||
		    etContra.length() == 0 ||
		    !etContra.getText().toString().equals(bundle.getString("contra")) ||
		    !etNuevaContra.getText().toString().equals(etConfContra.getText().toString())
			){
			
			if (etNuevaContra.length() == 0 || 
				    etConfContra.length() == 0 ||
				    etContra.getText().length() == 0){
				mensaje("Error", "Escriba la contraseña");
			}
			
			if (!etContra.getText().toString().equals(bundle.getString("contra"))){
				mensaje("Error", "No coincide la contraseña actual");
			}
			
			if (!etNuevaContra.getText().toString().equals(etConfContra.getText().toString())){
				mensaje("Error", "No coincide la nueva contraseña");
			}
			
			return false;
		}
		else{
			return true;
		}
	}
	*/
	
	private boolean actualizaContra(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#actualizaContrasenia";
	    String METHOD_NAME = "actualizaContrasenia";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
		
		if (validaContra() == true){
			EditText etNuevaContra = (EditText)findViewById(R.id.etNuevaContraActualizaContra);
			EditText etContraActual = (EditText)findViewById(R.id.etContraActualActualizaContra);

			try{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        request.addProperty ("emailCliente", bundle.getString("emailCliente"));
		        request.addProperty ("contraActual",etContraActual.getText().toString());		        
		        request.addProperty ("nuevaContra",etNuevaContra.getText().toString());
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            result =  (SoapObject) envelope.bodyIn;
	            SoapPrimitive id = (SoapPrimitive) result.getProperty("result");
	            if (Integer.parseInt(id.toString()) == 1){
		            //Log.i("Contraseña actualizada", id.toString());
		            contraAc = etNuevaContra.getText().toString();
		            return true;
	            }
	            else{
	            	mensaje("Error", "No coincide la contraseña actual");
	            	return false;
	            }
	            
			}
			catch (Exception e) {
				Log.e("error actualizaContra", e.toString());
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	/*
	private boolean actualizaContra(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#actualizaContra";
	    String METHOD_NAME = "actualizaContra";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
		
		if (validaContra() == true){
			EditText etNuevaContra = (EditText)findViewById(R.id.etNuevaContraActualizaContra);

			try{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        request.addProperty ("emailCliente", bundle.getString("emailCliente"));
		        request.addProperty ("nuevaContra",etNuevaContra.getText().toString());
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            result =  (SoapObject) envelope.bodyIn;
	            SoapPrimitive id = (SoapPrimitive) result.getProperty("result");
	            Log.i("Contraseña actualizado", id.toString());
	            contraAc = etNuevaContra.getText().toString();
	            
			}
			catch (Exception e) {
				Log.e("error actualizaContra", e.toString());
				return false;
			}
			return true;
		}
		else{
			return false;
		}
		
	}
		
	*/
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			if (actualizaContra()== true){
				Toast.makeText(ActualizaContra.this, "Contraseña actualizada", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("emailCliente", bundle.getString("emailCliente"));
				intent.putExtra("contra", contraAc);
				intent.setClass(ActualizaContra.this, DatosCuenta.class);
				startActivity(intent);
				finish();			
			}
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.putExtra("emailCliente", bundle.getString("emailCliente"));
			intent.putExtra("contra", bundle.getString("contra"));
			intent.setClass(ActualizaContra.this, DatosCuenta.class);
			startActivity(intent);
			finish();			
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(ActualizaContra.this, Principal.class);
			startActivity(intent);
			finish();			
		}
	};
	
	 private void mensaje(String titulo, String msj){
		 new AlertDialog.Builder(ActualizaContra.this)

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
