package comercio.movil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CompraFinalizada extends Activity{
 
	private ImageView ivInicio;
	private ImageView ivLlamar;
	private ImageView ivSms;
	
	private String HOST = Valores.HOST;
	private Bundle bundle;
	
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.comprafinalizada);
	   
	   bundle = getIntent().getExtras();
	   
	   enviaCorreo(bundle.getString("idPedido"));
	   
	   if (obtenerCelular() != null){
		   ivSms = (ImageView) findViewById(R.id.ivSms);
		   ivSms.setOnClickListener(ivSmsPres);
	   }
	   
	   ivInicio = (ImageView) findViewById(R.id.ivInicioCompraFin);
	   ivInicio.setOnClickListener(ivInicioPres);
	   
	   ivLlamar = (ImageView) findViewById(R.id.ivLlamar);
	   ivLlamar.setOnClickListener(ivLlamarPres);
	   
	   ivSms = (ImageView) findViewById(R.id.ivSms);
	   ivSms.setOnClickListener(ivSmsPres);
	   
	   
	}
	
	private void enviaCorreo(String idPedido){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#correoOrden";
	    String METHOD_NAME = "correoOrden";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("idPedido", idPedido);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        //result =  (SoapObject) envelope.bodyIn;
	        //SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	    }catch (Exception e) {
	    	Log.e("error enviaCorreo", e.toString());
		}
	}
	
	private String obtenerTelefono(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerTelefono";
	    String METHOD_NAME = "obtenerTelefono";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result =  (SoapObject) envelope.bodyIn;
	        SoapPrimitive spTelefono = (SoapPrimitive) result.getProperty("result");
	        //SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	        Log.i("Telefono", spTelefono.toString());
	        return spTelefono.toString();
	    }catch (Exception e) {
	    	Log.e("error obtener teléfono", e.toString());
	    	return "";
		}
	}
	
	private String obtenerCelular(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerCelular";
	    String METHOD_NAME = "obtenerCelular";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result =  (SoapObject) envelope.bodyIn;
	        SoapPrimitive spCelular = (SoapPrimitive) result.getProperty("cel");
	        //SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	        Log.i("Celular", spCelular.toString());
	        return spCelular.toString();
	    }catch (Exception e) {
	    	Log.e("error obtener teléfono", e.toString());
	    	return null;
		}
	}
	
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
    
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
	        intent.setClass(CompraFinalizada.this, Principal.class);
	        startActivity(intent);
	        finish();	
		}
	};
	
	private OnClickListener ivLlamarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			try {
		        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
		        intentLlamada.setData(Uri.parse("tel:"+obtenerTelefono()));
		        startActivity(intentLlamada);
		    } catch (ActivityNotFoundException err) {
		         Log.e("error llamar", err.toString());
 		    	 Toast.makeText(CompraFinalizada.this, "No se puede establecer la llamada", Toast.LENGTH_LONG).show();
		    }
		}
	};
  
	private OnClickListener ivSmsPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			final AlertDialog.Builder alerta = new AlertDialog.Builder(CompraFinalizada.this);
			final EditText entrada = new EditText(CompraFinalizada.this);
			entrada.setHeight(150);
			entrada.setText("El cliente "+bundle.getString("nombreC")+" ha realizado el pedido "+bundle.getString("idPedido"));
			        alerta.setView(entrada);
			        alerta.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			                SmsManager sms = SmsManager.getDefault();
			                sms.sendTextMessage(obtenerCelular(), null, entrada.getText().toString(), null, null);
			                Toast.makeText(CompraFinalizada.this, "Enviando mensaje", Toast.LENGTH_LONG).show();
			                Log.i("sms", entrada.getText().toString());
			            }
			        });
			        alerta.setNegativeButton("Cancelar",
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int whichButton) {
			                        dialog.cancel();
			                    }
			                });
			        alerta.setTitle("Mensaje");
			        alerta.show();
		}
	};

}
