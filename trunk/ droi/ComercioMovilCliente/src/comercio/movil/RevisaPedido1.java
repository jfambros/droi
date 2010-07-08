package comercio.movil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RevisaPedido1 extends Activity{
	private EditText tvComentario = null;
	private ImageView ivContinuar = null;
	private RadioGroup rgSiNoEnvio = null; 
	
	private String HOST = "10.0.2.2";
	private Bundle bundle= null;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido1);
        
        //objetos
        tvComentario = (EditText) findViewById(R.id.etComentarioRevisaPed1);
        
        ivContinuar = (ImageView) findViewById(R.id.ivContinuarRevisaPed1);
        ivContinuar.setOnClickListener(ivContinuarPres);
        
        rgSiNoEnvio = (RadioGroup) findViewById(R.id.rgSiNoEnvioRevisaPed1);
        rgSiNoEnvio.setOnCheckedChangeListener(rgSiNoEnvioPres);
        
        bundle = getIntent().getExtras();
        Log.i("email",bundle.getString("emailCliente"));
        llenaDireccion(bundle.getString("emailCliente"));

	}
	
	private void llenaDireccion(String email){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    //objetos para visualizar
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed1);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed1);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed1);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed1);
	    TextView tvCiudadCte = (TextView)findViewById(R.id.tvCiudadRevisaPed1);
	    TextView tvCPCte = (TextView)findViewById(R.id.tvCPRevisaPed1);
	    TextView tvPaisCte = (TextView)findViewById(R.id.tvPaisRevisaPed1);
	    //
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("emailCliente", email);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result =  (SoapObject) envelope.bodyIn;
	        SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	        
	        SoapPrimitive empresaCliente = (SoapPrimitive) resultSoap.getProperty("empresaCliente");
	        SoapPrimitive nombreCliente = (SoapPrimitive) resultSoap.getProperty("nombreCliente");
	        SoapPrimitive apellidoCliente = (SoapPrimitive) resultSoap.getProperty("apellidoCliente");
	        SoapPrimitive direccCliente = (SoapPrimitive) resultSoap.getProperty("direccCliente");
	        SoapPrimitive coloniaCliente = (SoapPrimitive) resultSoap.getProperty("coloniaCliente");
	        SoapPrimitive cpCliente = (SoapPrimitive) resultSoap.getProperty("cpCliente");
	        SoapPrimitive ciudadCliente = (SoapPrimitive) resultSoap.getProperty("ciudadCliente");
	        SoapPrimitive paisCliente = (SoapPrimitive) resultSoap.getProperty("paisCliente");
	        
	        tvEmpresaCte.setText(empresaCliente.toString());
	        tvNombreCte.setText(nombreCliente.toString() + " "+apellidoCliente.toString());
	        tvDireccionCte.setText(direccCliente.toString());
	        tvColoniaCte.setText(coloniaCliente.toString());
	        tvCiudadCte.setText(ciudadCliente.toString());
	        tvCPCte.setText(cpCliente.toString());
	        tvPaisCte.setText(paisCliente.toString());
	        
	    }
	    catch(Exception err){
	    	
	    }
	}
	
	private void obtieneCostoEnvio(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerCostoEnvio";
	    String METHOD_NAME = "obtenerCostoEnvio";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición	
	  //objetos para visualizar
	    TextView tvCostoEnvio = (TextView) findViewById(R.id.tvCostoEnvioRevisaPed1);
	    
	    try{
	    	 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
		        result =  (SoapObject) envelope.bodyIn;   
		        if (!result.getProperty("costo").toString().equals("anyType{}")){
		        	SoapPrimitive costo = (SoapPrimitive) result.getProperty("costo");
		        	tvCostoEnvio.setText("$"+costo.toString());
		        }
		        
	    }
	    catch(Exception err){
	    	Log.e("Error obtieneCostoEnvio", err.toString());
	    }
	}
	
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Log.i("comentario",tvComentario.getText().toString());
		}
	};
	
	private OnCheckedChangeListener rgSiNoEnvioPres = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup arg0, int check) {
			if (check == R.id.rgNoEnvioRevisaPed1){
				TextView tvCostoEnvio = (TextView) findViewById(R.id.tvCostoEnvioRevisaPed1);
				tvCostoEnvio.setText("$0");
			}
			else{
				obtieneCostoEnvio();
			}
		}
	};

}
