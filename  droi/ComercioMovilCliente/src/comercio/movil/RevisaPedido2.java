package comercio.movil;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RevisaPedido2 extends Activity{
	private Bundle bundle = null;
	private EditText etComentario = null;
	private ImageView ivContinuar = null;
	private RadioGroup rgFormaPago = null;
	private ImageView ivRegresar = null;
	
	
	private String email = null;
	private String tipoPago = null;
	private ArrayList<String> direccionCliente = new ArrayList<String>();
	private String HOST = "10.0.2.2";
	private double envioProd = 0.0;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido2);
        
        //objetos
        etComentario = (EditText) findViewById(R.id.etComentarioRevisaPed2);
        
        ivContinuar = (ImageView)findViewById(R.id.ivContinuarRevisaPed2);
        ivContinuar.setOnClickListener(ivContinuarPres);
    
        rgFormaPago = (RadioGroup) findViewById(R.id.rgFormaPagoEnvioRevisaPed2);
        rgFormaPago.setOnCheckedChangeListener(rgFormaPagoPres);
        
        ivRegresar = (ImageView) findViewById(R.id.ivRegresaRevisaPed2);
        ivRegresar.setOnClickListener(ivRegresarPres);
        
        bundle = getIntent().getExtras();
        email = bundle.getString("emailCliente");
        
        direccionCliente = bundle.getStringArrayList("direccionCliente");
        
        etComentario.setText(bundle.getString("comentario"));
        tipoPago = "Tienda";
        
        envioProd = bundle.getDouble("envioProd");
        
        
        llenaDireccion(email);
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
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed2);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed2);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed2);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed2);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadYCPRevisaPed2);
	    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisRevisaPed2);
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
	        SoapPrimitive estadoCliente = (SoapPrimitive) resultSoap.getProperty("estadoCliente");
	        SoapPrimitive paisCliente = (SoapPrimitive) resultSoap.getProperty("paisCliente");
	        
	        tvEmpresaCte.setText(empresaCliente.toString());
	        tvNombreCte.setText(nombreCliente.toString() + " "+apellidoCliente.toString());
	        tvDireccionCte.setText(direccCliente.toString());
	        tvColoniaCte.setText(coloniaCliente.toString());
	        tvCiudadYCPCte.setText(ciudadCliente.toString()+", C.P. "+cpCliente.toString());
	        tvEsatdoYPaisCte.setText(estadoCliente.toString()+", "+paisCliente.toString());
	        
	    }
	    catch(Exception err){
	    	
	    }
	}
	
	private OnCheckedChangeListener rgFormaPagoPres = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup arg0, int check) {
			if (check == R.id.rgEnTiendaEnvioRevisaPed2){
				tipoPago = "Tienda";
			}
			
			if (check == R.id.rgDepositoEnvioRevisaPed2){
				tipoPago = "Depósito";
			}
			
		}
	};
	
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.putExtra("tipoPago", tipoPago);
			intent.putExtra("comentario", etComentario.getText().toString());
			intent.putExtra("emailCliente", email);
			intent.putExtra("envioProd", envioProd);
			//direccion del cliente
			intent.putStringArrayListExtra("direccionCliente", direccionCliente);
			//falta dirección de facturacion
	        intent.setClass(RevisaPedido2.this, RevisaPedido3.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.putExtra("emailCliente", email);			
			intent.putExtra("comentario", etComentario.getText().toString());
	        intent.setClass(RevisaPedido2.this, RevisaPedido1.class);
	        startActivity(intent);
	        finish();
		}
	};
	
}
