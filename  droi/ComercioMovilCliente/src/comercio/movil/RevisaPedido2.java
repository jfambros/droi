package comercio.movil;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
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
	private ImageView ivCambiaDir = null;
	private ImageView ivInicio = null;
	
	
	private String email = null;
	private String tipoPago = null;
	private ArrayList<String> direccionCliente = new ArrayList<String>();
	private ArrayList<String> direccionFactura = new ArrayList<String>();
	private String HOST = Valores.HOST;
	private double envioProd = 0.0;
	private int idClienteA = 0;

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
        
        ivCambiaDir = (ImageView)findViewById(R.id.ivCambiaDireccRevisaPed2);
        ivCambiaDir.setOnClickListener(ivCambiaDirPres);
        
		ivInicio = (ImageView)findViewById(R.id.ivInicioRevisaPed2);
		ivInicio.setOnClickListener(ivInicioPres);
        
        bundle = getIntent().getExtras();
        email = bundle.getString("emailCliente");
        
        direccionCliente = bundle.getStringArrayList("direccionCliente");
        
        if (bundle.getString("comentario") != null){
        	etComentario.setText(bundle.getString("comentario"));
        }
        
        tipoPago = "Tienda";
        
        envioProd = bundle.getDouble("envioProd");
    	idClienteA = bundle.getInt("idCliente");
    	
        if (bundle.getStringArrayList("direccionFactura") == null){
        	llenaDireccion(email);
        }
        else{
        	llenaDirNueva();
        }
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private void llenaDireccion(String email){
		//Definici�n para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definici�n
	    //objetos para visualizar
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed2);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed2);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed2);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed2);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadCPRevisaPed2);
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
	    	Log.e("error llena dir ", err.toString());
	    }
	}
	
	private void llenaDirNueva(){
		//objetos para visualizar
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed2);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed2);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed2);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed2);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadCPRevisaPed2);
	    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisRevisaPed2);
	    //
        
	    direccionFactura = bundle.getStringArrayList("direccionFactura");
	    
	    tvNombreCte.setText(direccionFactura.get(0));
	    tvEmpresaCte.setText(direccionFactura.get(1));
        tvDireccionCte.setText(direccionFactura.get(2));
        tvColoniaCte.setText(direccionFactura.get(3));
        tvCiudadYCPCte.setText(direccionFactura.get(4)+", C.P. "+direccionFactura.get(5));
        tvEsatdoYPaisCte.setText(direccionFactura.get(6)+", "+direccionFactura.get(7));
	    
	}
	
	
	private OnCheckedChangeListener rgFormaPagoPres = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup arg0, int check) {
			if (check == R.id.rgEnTiendaEnvioRevisaPed2){
				tipoPago = "Tienda";
			}
			
			if (check == R.id.rgDepositoEnvioRevisaPed2){
				tipoPago = "Dep�sito";
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
			intent.putExtra("idCliente", idClienteA);
			//direccion del cliente
			intent.putStringArrayListExtra("direccionCliente", direccionCliente);
			if (bundle.getStringArrayList("direccionFactura")==null){
				intent.putStringArrayListExtra("direccionFactura", direccionCliente);
			}
			else{
				intent.putStringArrayListExtra("direccionFactura", direccionFactura);
			}
	        intent.setClass(RevisaPedido2.this, RevisaPedido3.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
	        intent.putExtra("idCliente", idClienteA);			
			intent.putExtra("emailCliente", email);			
			intent.putExtra("comentario", etComentario.getText().toString());
			intent.putStringArrayListExtra("direccionCliente", direccionCliente);
			if (bundle.getStringArrayList("direccionFactura")==null){
				intent.putStringArrayListExtra("direccionFactura", direccionCliente);
			}
			else{
				intent.putStringArrayListExtra("direccionFactura", bundle.getStringArrayList("direccionFactura"));
			}			
	        intent.setClass(RevisaPedido2.this, RevisaPedido1.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivCambiaDirPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.putExtra("idCliente", idClienteA);
			intent.putExtra("emailCliente", email);		
			intent.putExtra("comentario", etComentario.getText().toString());
	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        intent.setClass(RevisaPedido2.this, NuevaDireccionFactura.class);
	        startActivity(intent);
	        finish();			
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(RevisaPedido2.this, Principal.class);
            startActivity(intent);
            finish();
			
		}
	};
	
}
