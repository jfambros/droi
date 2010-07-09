package comercio.movil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
import utils.ListaCesta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RevisaPedido3 extends Activity{
	private TextView tvComentario = null;
	private TextView tvTipoPago = null;
	
	private String HOST = "10.0.2.2";
	private String email = null;
	private Bundle bundle = null;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido3);
        
        bundle = getIntent().getExtras();
        email = bundle.getString("emailCliente");
        
        //objetos del xml
        tvComentario = (TextView) findViewById(R.id.tvComentarioRevisaPed3);
        tvComentario.setText(bundle.getString("comentario"));
        
        tvTipoPago = (TextView) findViewById(R.id.tvTipoPagoRevisaPed3);
//REVISAR EL Tipo pago, tiene null        
        //mostrar el tipo de pago
        if (bundle.getString("TipoPago").equals("Tienda")){
        	tvTipoPago.setText("Pago en tienda");
        }
        
        if (bundle.getString("TipoPago").equals("Depósito")){
        	pagoBancario();
        }
        
        llenaDireccion(email);
        //llenaProductos();
		}
	    catch(Exception err){
	    	Log.e("error create", err.toString());
	    	
	    }
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
	    TextView tvEmpresaCte = (TextView)findViewById(R.id.tvEmpresaRevisaPed3);
	    TextView tvNombreCte = (TextView)findViewById(R.id.tvNombCteRevisaPed3);
	    TextView tvDireccionCte = (TextView)findViewById(R.id.tvDireccionRevisaPed3);
	    TextView tvColoniaCte = (TextView)findViewById(R.id.tvColoniaRevisaPed3);
	    TextView tvCiudadYCPCte = (TextView)findViewById(R.id.tvCiudadYCPRevisaPed3);
	    TextView tvEsatdoYPaisCte = (TextView)findViewById(R.id.tvEstadoYPaisRevisaPed3);
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
	    	Log.e("error", err.toString());
	    	
	    }
	}
	
	private void llenaProductos(){
		Set set = ListaCesta.arregloCesta.entrySet();

        Iterator i = set.iterator();

        while(i.hasNext()){
            Map.Entry<String,DatosCesta> me = (Map.Entry<String, DatosCesta>)i.next();
            Log.i("Datos map: ",me.getKey() + " : " + ((DatosCesta) me.getValue()).getNombreProducto()+" cantidad: "+ ((DatosCesta) me.getValue()).getCantidadProd()+" Precio:"+((DatosCesta) me.getValue()).getPrecioProd() );
        }
        
	}
	
	private void pagoBancario(){
		
	}

}
