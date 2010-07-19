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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class RevisaPedido3 extends Activity{
	private TextView tvComentario = null;
	private ImageView ivRegresar = null;
	
	private String HOST = "10.0.2.2";
	private String email = null;
	private String tipoPago = null;
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
        
        ivRegresar = (ImageView) findViewById(R.id.ivRegresaRevisaPed3);
        ivRegresar.setOnClickListener(ivRegresarPres);
        
        //ontener el tipo de pago
        tipoPago = bundle.getString("tipoPago");
        obtieneTipoPago(tipoPago);
        
        llenaDireccion(email);
        llenaProductos();
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
		try{
			Set set = ListaCesta.arregloCesta.entrySet();
			TableLayout tlProductos = (TableLayout)findViewById(R.id.tlProductosRevisaPed3);
			TextView tvTotal = (TextView)findViewById(R.id.tvTotalRevisaPed3);
			double total = 0.0;
	
	        Iterator i = set.iterator();
	        
	
	        while(i.hasNext()){
	            Map.Entry<String,DatosCesta> me = (Map.Entry<String, DatosCesta>)i.next();
	            Log.i("Datos map: ",me.getKey() + " : " + ((DatosCesta) me.getValue()).getNombreProducto()+" cantidad: "+ ((DatosCesta) me.getValue()).getCantidadProd()+" Precio:"+((DatosCesta) me.getValue()).getPrecioProd() );
	            
	            TableRow row = new TableRow(this);
	        	row.setLayoutParams(new LayoutParams(
	        	LayoutParams.FILL_PARENT,
	        	LayoutParams.WRAP_CONTENT));
	        	
	        	TextView tvCantidad = new TextView(this);
	        	int cant = ((DatosCesta) me.getValue()).getCantidadProd();
	        	tvCantidad.setText(Integer.toString(cant));
		        tvCantidad.setTextColor(Color.BLACK);
		        tvCantidad.setLayoutParams(new LayoutParams(
		        LayoutParams.FILL_PARENT,
		        LayoutParams.FILL_PARENT));
		        tvCantidad.setGravity(Gravity.CENTER_VERTICAL);
		        tvCantidad.setWidth(50);
	            
		        TextView tvNombre = new TextView(this);
	        	tvNombre.setText(((DatosCesta) me.getValue()).getNombreProducto());
		        tvNombre.setTextColor(Color.BLACK);
		        tvNombre.setLayoutParams(new LayoutParams(
		        LayoutParams.FILL_PARENT,
		        LayoutParams.FILL_PARENT));
		        tvNombre.setGravity(Gravity.CENTER_VERTICAL);
		        //tvNombre.setWidth(50);
		        
		        double subTot = ((DatosCesta) me.getValue()).getCantidadProd() * ((DatosCesta) me.getValue()).getPrecioProd();
		        
		        TextView tvSubTotal = new TextView(this);
	        	tvSubTotal.setText(Double.toString(subTot));
		        tvSubTotal.setTextColor(Color.BLACK);
		        tvSubTotal.setLayoutParams(new LayoutParams(
		        LayoutParams.FILL_PARENT,
		        LayoutParams.FILL_PARENT));
		        tvSubTotal.setGravity(Gravity.CENTER_VERTICAL);
	            
		        row.addView(tvCantidad);
		        row.addView(tvNombre);
		        row.addView(tvSubTotal);
		        row.setBackgroundColor(Color.WHITE);
		        
		        tlProductos.addView(row);
		        
		        total +=  Double.parseDouble(((TextView)(row.getChildAt(2))).getText().toString());
		        tvTotal.setText("Total: $"+Double.toString(total)+" ");
		        
	        }
		}
		catch(Exception err){
			Log.e("Error llena prod", err.toString());
		}
        
	}
	
	private void obtieneTipoPago(String tipoPago){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosBanco";
	    String METHOD_NAME = "obtenerDatosBanco";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
		
		
		TableLayout tlTipoPago = (TableLayout) findViewById(R.id.tlTipoPagoRevisaPed3);
		

		
        if (tipoPago.equals("Tienda")){
    		TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(
        	LayoutParams.FILL_PARENT,
        	LayoutParams.WRAP_CONTENT));
        	
        	TextView tvPagoTienda = new TextView(this);
        	tvPagoTienda.setText("Pago en tienda");
	        tvPagoTienda.setTextColor(Color.BLACK);
	        tvPagoTienda.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
	        tvPagoTienda.setGravity(Gravity.CENTER_VERTICAL);
	        tvPagoTienda.setWidth(50);
	        
	        row.setBackgroundColor(Color.WHITE);
	        row.addView(tvPagoTienda);
	        tlTipoPago.addView(row);
        	//tvTipoPago.setText("Pago en tienda");
        }
        
        if (tipoPago.equals("Depósito")){
        	try{
        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    	        httpt = new HttpTransportSE(URL);
    	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
    	        envelope.dotNet = false;
    	        envelope.setOutputSoapObject(request);
    	        httpt.call(SOAP_ACTION, envelope);
    	        result =  (SoapObject) envelope.bodyIn;
    	        SoapObject resultSoap =  (SoapObject) envelope.getResponse();
    	        
    	        SoapPrimitive numeroCuenta = (SoapPrimitive) resultSoap.getProperty("numeroCuenta");
    	        SoapPrimitive nombreBanco = (SoapPrimitive) resultSoap.getProperty("nombreBanco");
    	        SoapPrimitive titularCuenta = (SoapPrimitive) resultSoap.getProperty("titularCuenta");
    	        
    	        TableRow filaNumCta = new TableRow(this);
            	filaNumCta.setLayoutParams(new LayoutParams(
            	LayoutParams.FILL_PARENT,
            	LayoutParams.WRAP_CONTENT));
            	
            	TextView tvNumCta = new TextView(this);
            	tvNumCta.setText("Número de cuenta: "+numeroCuenta.toString());
    	        tvNumCta.setTextColor(Color.BLACK);
    	        tvNumCta.setLayoutParams(new LayoutParams(
    	        LayoutParams.FILL_PARENT,
    	        LayoutParams.FILL_PARENT));
    	        tvNumCta.setGravity(Gravity.CENTER_VERTICAL);
    	        tvNumCta.setWidth(50);
    	        
    	        filaNumCta.setBackgroundColor(Color.WHITE);
    	        filaNumCta.addView(tvNumCta);
    	        tlTipoPago.addView(filaNumCta);
    	        //Faltan los demás datos
    	        
    	        
    	        //Log.i("cuenta: ", numeroCuenta.toString()+" "+nombreBanco.toString()+" "+titularCuenta.toString());
        		
        	}
        	catch(Exception err){
        		Log.e("error deposito", err.toString());
        	}
        	
        	
        }

	}
	
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View v) {
			TextView tvComentario = (TextView) findViewById(R.id.tvComentarioRevisaPed3);
			Intent intent = new Intent();
			intent.putExtra("comentario", tvComentario.getText().toString());
			intent.putExtra("emailCliente", email);
	        intent.setClass(RevisaPedido3.this, RevisaPedido2.class);
	        finish();
	        startActivity(intent);
		}
	};

}
