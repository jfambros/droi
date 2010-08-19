package comercio.movil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
import android.widget.TextView;

public class VerificaCliente extends Activity{
	private ImageView ivClienteNuevo;
	private ImageView ivVerificaCliente;
	
	private String HOST = "10.0.2.2"; //esto es para el equipo local
	private String emailCorrecto = null;
	private String contra = null;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.verificacliente);
	        
	        //botones
	        ivClienteNuevo = (ImageView)findViewById(R.id.ivClienteNuevo);
	        ivClienteNuevo.setOnClickListener(ivClienteNuevoPres);
	        
	        ivVerificaCliente = (ImageView) findViewById(R.id.ivValidaClienteVerificaCte);
	        ivVerificaCliente.setOnClickListener(ivVerificaClientePres);
	        
	     }
	 
	 private OnClickListener ivClienteNuevoPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(VerificaCliente.this, NuevoCliente.class);
            startActivity(intent);
            finish();
		}
	};
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private OnClickListener ivVerificaClientePres = new OnClickListener() {
		
		public void onClick(View arg0) {
			if (validaCliente() == true){
	            Intent intent = new Intent();
	            intent.putExtra("emailCliente", emailCorrecto);
	            intent.putExtra("contra", contra);
	            intent.setClass(VerificaCliente.this, DatosCuenta.class);
	            startActivity(intent);
	            finish();			
			}
		}
	};
	
	private boolean validaCliente(){
		 //Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#validaCliente";
	    String METHOD_NAME = "validaCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    EditText etEmailCliente = (EditText)findViewById(R.id.etEmailVerificaCte);
	    EditText etPasswdCliente = (EditText)findViewById(R.id.etPasswdVerificaCte);
	    
	    if (validaDatos(etEmailCliente.getText().toString(), etPasswdCliente.getText().toString()) == true){
		    try{
		    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        request.addProperty ("emailCliente", etEmailCliente.getText().toString());
		        request.addProperty ("passwdCliente",etPasswdCliente.getText().toString());
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
	            result =  (SoapObject) envelope.bodyIn;
	            if (result.getProperty("email").toString().equals("anyType{}")){
	            	//SoapPrimitive email = (SoapPrimitive) result.getProperty("email");
	            	mensajeError("Error", "e-mail o contraseña incorrecta");
	            	return false;
	            }else{
	            	SoapPrimitive email = (SoapPrimitive) result.getProperty("email");
	            	emailCorrecto = email.toString();
	            	contra = etPasswdCliente.getText().toString();
	            	return true;
	            }
		    }
		    catch(Exception err){
		    	mensajeError("Error", "e-mail o contraseña incorrecta");
		       Log.e("Error", err.toString());
		       return false;
		    }   
	    }//fin valida datos
	    else{
	    	return false;
	    }

	}
	
	private boolean validaDatos(String email, String passwd){
       if (email.length()==0 || passwd.length()==0){
    	   mensajeError("Error", "Faltan datos");
    	   return false;
       }else{
    	   return true;
       }
    	   
	}
	
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(VerificaCliente.this)

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
