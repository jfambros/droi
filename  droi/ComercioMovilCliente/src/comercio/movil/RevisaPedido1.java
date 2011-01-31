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

public class RevisaPedido1 extends Activity{
	private EditText etComentario = null;
	private ImageView ivContinuar = null;
	private RadioGroup rgSiNoEnvio = null;
	private ImageView ivCambiaDirecc = null;
	private ImageView ivInicio = null;
	private ArrayList<String> direccionCliente = new ArrayList<String>();
	
	private String HOST = Valores.HOST;
	private Bundle bundle= null;
	private String email=null;
	private int idClienteA = 0;
	private double envioProd=0.0;
	
	


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido1);
        
        //objetos
        etComentario = (EditText) findViewById(R.id.etComentarioRevisaPed1);
        
        ivContinuar = (ImageView) findViewById(R.id.ivContinuarRevisaPed1);
        ivContinuar.setOnClickListener(ivContinuarPres);
        
        rgSiNoEnvio = (RadioGroup) findViewById(R.id.rgSiNoEnvioRevisaPed1);
        rgSiNoEnvio.setOnCheckedChangeListener(rgSiNoEnvioPres);
        
        ivCambiaDirecc = (ImageView) findViewById(R.id.ivCambiaDireccRevisaPed1);
        ivCambiaDirecc.setOnClickListener(ivCambiaDireccPres);
        
		ivInicio = (ImageView)findViewById(R.id.ivInicioRevisaPed1);
		ivInicio.setOnClickListener(ivInicioPres);
        
        bundle = getIntent().getExtras();
        //Log.i("email",bundle.getString("emailCliente"));
        if (bundle.getString("emailCliente") != null){
        	email = bundle.getString("emailCliente");
        }
        
        if (bundle.getString("comentario")!= null){
        	etComentario.setText(bundle.getString("comentario"));
        }
        if (bundle.getString("envioProd")!= null){
        	obtieneCostoEnvio();
        }        
        
        envioProd = 0.0;
        
        if (bundle.getStringArrayList("direccionCliente") == null){
        	llenaDireccion(email);
        }
        else{
        	llenaDirNueva();
        	idClienteA = bundle.getInt("idCliente");
        }

	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private void llenaDireccion(String email){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	    
		
		
		
	    
	    //objetos para visualizar
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed1);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed1);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed1);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed1);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadCPRevisaPed1);
	    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisRevisaPed1);
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
	        
	        SoapPrimitive idCliente = (SoapPrimitive) resultSoap.getProperty("idCliente");
	        SoapPrimitive empresaCliente = (SoapPrimitive) resultSoap.getProperty("empresaCliente");
	        SoapPrimitive nombreCliente = (SoapPrimitive) resultSoap.getProperty("nombreCliente");
	        SoapPrimitive apellidoCliente = (SoapPrimitive) resultSoap.getProperty("apellidoCliente");
	        SoapPrimitive direccCliente = (SoapPrimitive) resultSoap.getProperty("direccCliente");
	        SoapPrimitive coloniaCliente = (SoapPrimitive) resultSoap.getProperty("coloniaCliente");
	        SoapPrimitive cpCliente = (SoapPrimitive) resultSoap.getProperty("cpCliente");
	        SoapPrimitive ciudadCliente = (SoapPrimitive) resultSoap.getProperty("ciudadCliente");
	        SoapPrimitive estadoCliente = (SoapPrimitive) resultSoap.getProperty("estadoCliente");
	        SoapPrimitive paisCliente = (SoapPrimitive) resultSoap.getProperty("paisCliente");
	       // SoapPrimitive telefonoCliente = (SoapPrimitive) resultSoap.getProperty("telefonoCliente");
	        
	        idClienteA = Integer.parseInt(idCliente.toString());
	        tvEmpresaCte.setText(empresaCliente.toString());
	        tvNombreCte.setText(nombreCliente.toString() + " "+apellidoCliente.toString());
	        tvDireccionCte.setText(direccCliente.toString());
	        tvColoniaCte.setText(coloniaCliente.toString());
	        tvCiudadYCPCte.setText(ciudadCliente.toString()+", C.P. "+cpCliente.toString());
	        tvEsatdoYPaisCte.setText(estadoCliente.toString()+", "+paisCliente.toString());
	        
	        direccionCliente.add(nombreCliente.toString()+" "+apellidoCliente.toString());
	        direccionCliente.add(empresaCliente.toString());
	        direccionCliente.add(direccCliente.toString());
	        direccionCliente.add(coloniaCliente.toString());
	        direccionCliente.add(ciudadCliente.toString());
	        direccionCliente.add(cpCliente.toString());
	        direccionCliente.add(estadoCliente.toString());
	        direccionCliente.add(paisCliente.toString());
	        //direccionCliente.add(telefonoCliente.toString());
	        

	    }
	    catch(Exception err){
	    	Log.e("error llena dir ped1", err.toString());
	    }
	}
	
	private void llenaDirNueva(){
		//objetos para visualizar
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed1);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed1);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed1);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed1);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadCPRevisaPed1);
	    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisRevisaPed1);
	    //
        
	    direccionCliente = bundle.getStringArrayList("direccionCliente");
	    
	    tvNombreCte.setText(direccionCliente.get(0));
	    tvEmpresaCte.setText(direccionCliente.get(1));
        tvDireccionCte.setText(direccionCliente.get(2));
        tvColoniaCte.setText(direccionCliente.get(3));
        tvCiudadYCPCte.setText(direccionCliente.get(4)+", C.P. "+direccionCliente.get(5));
        tvEsatdoYPaisCte.setText(direccionCliente.get(6)+", "+direccionCliente.get(7));
	    
	}
	
	private void obtieneCostoEnvio(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerCostoEnvio";
	    String METHOD_NAME = "obtenerCostoEnvio";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
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
		        	envioProd = Double.parseDouble(costo.toString());
		        }
		        //envioProd = "Si";
		        
	    }
	    catch(Exception err){
	    	Log.e("Error obtieneCostoEnvio", err.toString());
	    }
	}
	
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Log.i("comentario",etComentario.getText().toString());
			
	        Intent intent = new Intent();
	        intent.putExtra("idCliente", idClienteA);
	        intent.putExtra("comentario", etComentario.getText().toString());
	        intent.putExtra("emailCliente", email);
	        intent.putExtra("envioProd", envioProd);
	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        if (bundle.getStringArrayList("direccionFactura")!= null){
		        intent.putStringArrayListExtra("direccionFactura", bundle.getStringArrayList("direccionFactura"));
	        }
	        intent.setClass(RevisaPedido1.this, RevisaPedido2.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnCheckedChangeListener rgSiNoEnvioPres = new OnCheckedChangeListener() {
		
		public void onCheckedChanged(RadioGroup arg0, int check) {
			if (check == R.id.rgNoEnvioRevisaPed1){
				TextView tvCostoEnvio = (TextView) findViewById(R.id.tvCostoEnvioRevisaPed1);
				tvCostoEnvio.setText("$0");
				envioProd = 0.0;
			}
			else{
				obtieneCostoEnvio();
			}
		}
	};
	
	private OnClickListener ivCambiaDireccPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			for (int i=0; i<direccionCliente.size(); i++){
				Log.i("datos: ", direccionCliente.get(i));
			}
			Intent intent = new Intent();
	        intent.putExtra("idCliente", idClienteA);
	        intent.putExtra("emailCliente", email);
	        intent.putExtra("comentario", etComentario.getText().toString());
	        intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        if (bundle.getStringArrayList("direccionFactura")!= null){
		        intent.putStringArrayListExtra("direccionFactura", bundle.getStringArrayList("direccionFactura"));
	        }
	        intent.setClass(RevisaPedido1.this, NuevaDireccionEntrega.class);
	        startActivity(intent);
	        finish();

		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(RevisaPedido1.this, Principal.class);
            startActivity(intent);
            finish();
			
		}
	};

}
