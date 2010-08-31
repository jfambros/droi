package comercio.movil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.Valores;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CompraFinalizada extends Activity{
 
	private ImageView ivInicio;
	private String HOST = Valores.HOST;
	private Bundle bundle;
	
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.comprafinalizada);
	   
	   bundle = getIntent().getExtras();
	   
	   enviaCorreo(bundle.getString("idPedido"));
	   
	   ivInicio = (ImageView) findViewById(R.id.ivInicioCompraFin);
	   ivInicio.setOnClickListener(ivInicioPres);
	   
	}
	
	private void enviaCorreo(String idPedido){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#correoOrden";
	    String METHOD_NAME = "correoOrden";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
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
	        result =  (SoapObject) envelope.bodyIn;
	        //SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	    }catch (Exception e) {
	    	Log.e("error enviaCorreo", e.toString());
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
	
	


}
