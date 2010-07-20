package comercio.movil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
import utils.DatosOrdenKS;
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
	private ImageView ivFinalizar = null;
	
	private String HOST = "10.0.2.2";
	private String email = null;
	private String tipoPago = null;
	private String comentario = null;
	private double envioProd = 0.0;
	private double subTotal = 0.0;
	private double total = 0.0;
	private ArrayList<String> direccionCliente = new ArrayList<String>();
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
        
        ivFinalizar = (ImageView)findViewById(R.id.ivFinalizarRevisaPed3);
        ivFinalizar.setOnClickListener(ivFinalizarPres);
        
        //ontener el tipo de pago
        tipoPago = bundle.getString("tipoPago");
        obtieneTipoPago(tipoPago);
        
        direccionCliente = bundle.getStringArrayList("direccionCliente");
        
        envioProd = bundle.getDouble("envioProd");
        
        comentario = bundle.getString("comentario");
        
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
		total = 0.0;
		try{
			Set set = ListaCesta.arregloCesta.entrySet();
			TableLayout tlProductos = (TableLayout)findViewById(R.id.tlProductosRevisaPed3);
			TextView tvSubtotal = (TextView)findViewById(R.id.tvSubtotalEnvioRevisaPed3);
			TextView tvTotal = (TextView)findViewById(R.id.tvTotalRevisaPed3);
			TextView tvCostoEnvio = (TextView) findViewById(R.id.tvCostoEnvioRevisaPed3);
			
	
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
		        
		        TextView tvSubTotalProd = new TextView(this);
	        	tvSubTotalProd.setText(Double.toString(subTot));
		        tvSubTotalProd.setTextColor(Color.BLACK);
		        tvSubTotalProd.setLayoutParams(new LayoutParams(
		        LayoutParams.FILL_PARENT,
		        LayoutParams.FILL_PARENT));
		        tvSubTotalProd.setGravity(Gravity.RIGHT);
	            
		        row.addView(tvCantidad);
		        row.addView(tvNombre);
		        row.addView(tvSubTotalProd);
		        row.setBackgroundColor(Color.WHITE);
		        
		        tlProductos.addView(row);
		        
		        subTotal +=  Double.parseDouble(((TextView)(row.getChildAt(2))).getText().toString());
		        tvSubtotal.setText("Subtotal: $"+Double.toString(subTotal)+" ");
		        
	        }
	        if (envioProd == 0.0){
	        	tvCostoEnvio.setText("Costo envío: $"+Double.toString(envioProd)+" ");
	        }
	        else{
	        	tvCostoEnvio.setText("Costo envío: $"+Double.toString(envioProd)+" ");
	        	total = subTotal + envioProd;
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
    	        
    	        TextView tvTitulo = (TextView)findViewById(R.id.tvTituloTipoPagoRevisaPed3);
    	        tvTitulo.setText("Información para el depósito bancario");
    	        
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
    	        
    	        filaNumCta.setBackgroundColor(Color.WHITE);
    	        filaNumCta.addView(tvNumCta);
    	        //tvNumCta.setWidth(50);
    	        
    	        TableRow filaNombreBanco = new TableRow(this);
    	        filaNombreBanco.setLayoutParams(new LayoutParams(
            	LayoutParams.FILL_PARENT,
            	LayoutParams.WRAP_CONTENT));
    	        
            	TextView tvNombreBanco = new TextView(this);
            	tvNombreBanco.setText("Banco: "+nombreBanco.toString());
            	tvNombreBanco.setTextColor(Color.BLACK);
            	tvNombreBanco.setLayoutParams(new LayoutParams(
    	        LayoutParams.FILL_PARENT,
    	        LayoutParams.FILL_PARENT));
            	tvNombreBanco.setGravity(Gravity.CENTER_VERTICAL);
            	//tvNombreBanco.setWidth(50);

            	filaNombreBanco.setBackgroundColor(Color.WHITE);
    	        filaNombreBanco.addView(tvNombreBanco);
    	        
    	        TableRow filaTitular = new TableRow(this);
    	        filaTitular.setLayoutParams(new LayoutParams(
            	LayoutParams.FILL_PARENT,
            	LayoutParams.WRAP_CONTENT));

            	TextView tvTitular = new TextView(this);
            	tvTitular.setText("Titular: "+titularCuenta.toString());
            	tvTitular.setTextColor(Color.BLACK);
            	tvTitular.setLayoutParams(new LayoutParams(
    	        LayoutParams.FILL_PARENT,
    	        LayoutParams.FILL_PARENT));
            	tvTitular.setGravity(Gravity.CENTER_VERTICAL);
            	//tvNombreBanco.setWidth(50);
            	
            	filaTitular.setBackgroundColor(Color.WHITE);
    	        filaTitular.addView(tvTitular);
            	
    	        
    	        tlTipoPago.addView(filaNumCta);
    	        tlTipoPago.addView(filaNombreBanco);
    	        tlTipoPago.addView(filaTitular);

    	        
    	        
    	        //Log.i("cuenta: ", numeroCuenta.toString()+" "+nombreBanco.toString()+" "+titularCuenta.toString());
        		
        	}
        	catch(Exception err){
        		Log.e("error deposito", err.toString());
        	}
        	
        	
        }

	}
    
	//insertar los productos comprados
	private void insertaProductos(String ordenId){
        try{
			Set set = ListaCesta.arregloCesta.entrySet();
			Iterator i = set.iterator();
	    
	        while(i.hasNext()){
	            Map.Entry<String,DatosCesta> me = (Map.Entry<String, DatosCesta>)i.next();
	            int cant = ((DatosCesta) me.getValue()).getCantidadProd();
	            insertaProdSW(Integer.parseInt(ordenId), Integer.parseInt(me.getKey()), cant);
	        }
		}
		catch (Exception err) {
			Log.e("Error insertaProd iterator", err.toString());
		}
			
	}
	
	//método para servicio Web que inserta productos
	private void insertaProdSW(int ordenId, int idProd, int cant){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#insertaProductoOrden";
	    String METHOD_NAME = "insertaProductoOrden";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;

	        request.addProperty ("ordenId", ordenId);
	        request.addProperty ("idProd", idProd);
	        request.addProperty ("cantidadProd", cant);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        SoapObject result =  (SoapObject) envelope.bodyIn;
	        SoapPrimitive actualizado = (SoapPrimitive) result.getProperty("result");;
	        Log.i("insertado", actualizado.toString());

	    }
	    catch (Exception err) {
          Log.i("error insertaProdSW", err.toString());
		}
	}
	
	
	private DatosOrdenKS llenaOrden(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;

	    DatosOrdenKS orden = new DatosOrdenKS();
	    
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
	        SoapPrimitive nombreCliente = (SoapPrimitive) resultSoap.getProperty("nombreCliente");
	        SoapPrimitive apellidoCliente = (SoapPrimitive) resultSoap.getProperty("apellidoCliente");
	        SoapPrimitive empresaCliente = (SoapPrimitive) resultSoap.getProperty("empresaCliente");
	        SoapPrimitive direccCliente = (SoapPrimitive) resultSoap.getProperty("direccCliente");
	        SoapPrimitive coloniaCliente = (SoapPrimitive) resultSoap.getProperty("coloniaCliente");
	        SoapPrimitive ciudadCliente = (SoapPrimitive) resultSoap.getProperty("ciudadCliente");
	        SoapPrimitive cpCliente = (SoapPrimitive) resultSoap.getProperty("cpCliente");
	        SoapPrimitive estadoCliente = (SoapPrimitive) resultSoap.getProperty("estadoCliente");
	        SoapPrimitive paisCliente = (SoapPrimitive) resultSoap.getProperty("paisCliente");
	        SoapPrimitive telefonoCliente = (SoapPrimitive) resultSoap.getProperty("telefonoCliente");

	        /*
			orden.setIdCliente(idCliente.toString());
			orden.setNombreCliente(nombreCliente.toString()+" "+apellidoCliente.toString());
			orden.setEmpresaCliente(empresaCliente.toString());
			orden.setDireccCliente(direccCliente.toString());
			orden.setColoniaCliente(coloniaCliente.toString());
			orden.setCiudadCliente(ciudadCliente.toString());
			orden.setCpCliente(cpCliente.toString());
			orden.setEstadoCliente(estadoCliente.toString());
			orden.setPaisCliente(paisCliente.toString());
			orden.setTelefonoCliente(telefonoCliente.toString());
			orden.setEmailCliente(email);
			orden.setIdDireccFormatCliente("1");
			*/
			orden.setIdCliente(direccionCliente.get(0));
			orden.setNombreCliente(direccionCliente.get(1));
			orden.setEmpresaCliente(direccionCliente.get(2));
			orden.setDireccCliente(direccionCliente.get(3));
			orden.setColoniaCliente(direccionCliente.get(4));
			orden.setCiudadCliente(direccionCliente.get(5));
			orden.setCpCliente(direccionCliente.get(6));
			orden.setEstadoCliente(direccionCliente.get(7));
			orden.setPaisCliente(direccionCliente.get(8));
			orden.setTelefonoCliente(direccionCliente.get(9));
			orden.setEmailCliente(email);
			orden.setIdDireccFormatCliente("1");
			
			orden.setNombreEntrega(direccionCliente.get(1));
			orden.setEmpresaEntrega(direccionCliente.get(2));
			orden.setDireccEntrega(direccionCliente.get(3));
			orden.setColoniaEntrega(direccionCliente.get(4));
			orden.setCiudadEntrega(direccionCliente.get(5));
			orden.setCpEntrega(direccionCliente.get(6));
			orden.setEstadoEntrega(direccionCliente.get(7));
			orden.setPaisEntrega(direccionCliente.get(8));
			orden.setIdDireccFormatEntrega("1");

			orden.setNombreFactura(direccionCliente.get(1));
			orden.setEmpresaFactura(direccionCliente.get(2));
			orden.setDireccFactura(direccionCliente.get(3));
			orden.setColoniaFactura(direccionCliente.get(4));
			orden.setCiudadFactura(direccionCliente.get(5));
			orden.setCpFactura(direccionCliente.get(6));
			orden.setEstadoFactura(direccionCliente.get(7));
			orden.setPaisFactura(direccionCliente.get(8));
			orden.setIdDireccFormatFactura("1");
		
			if (tipoPago.equals("Tienda")){
				orden.setFormaPago("Pagar en tienda");	
			}
			if (tipoPago.equals("Depósito")){
				orden.setFormaPago("Transferencia Bancaria");
			}
			
			orden.setTipoTarjetaCred("");
			orden.setPropietarioTarjetaCred("");
			orden.setNumeroTarjetaCred("");
			orden.setExpiraTarjetaCred("");
			orden.setUltimaModificacion("");

			orden.setEstadoOrden("1");
			orden.setFechaOrdenTerminada("");
			orden.setMoneda("MX");
			orden.setValorMoneda("1");
			orden.setComentario(comentario);
			orden.setSubTotal(Double.toString(subTotal));
			orden.setTarifa(Double.toString(envioProd));
			if (envioProd == 0.0){
				orden.setTipoEnvio("No");	
			}
			
			if (envioProd > 0.0){
				orden.setTipoEnvio("Si");
			}
		
/*
 * 
 * <idCliente>String</idCliente>
				<nombreCliente>String</nombreCliente>
				<empresaCliente>String</empresaCliente>
				<direccCliente>String</direccCliente>
				<coloniaCliente>String</coloniaCliente>
				<ciudadCliente>String</ciudadCliente>
				<cpCliente>String</cpCliente>
				<estadoCliente>String</estadoCliente>
				<paisCliente>String</paisCliente>
				<telefonoCliente>String</telefonoCliente>
				<emailCliente>String</emailCliente>
				<idDireccFormatCliente>String</idDireccFormatCliente>
				<nombreEntrega>String</nombreEntrega>
				<empresaEntrega>String</empresaEntrega>
				<direccEntrega>String</direccEntrega>
				<coloniaEntrega>String</coloniaEntrega>
				<ciudadEntrega>String</ciudadEntrega>
				<cpEntrega>String</cpEntrega>
				<estadoEntrega>String</estadoEntrega>
				<paisEntrega>String</paisEntrega>
				<idDireccFormatEntrega>String</idDireccFormatEntrega>
				<nombreFactura>String</nombreFactura>
				<empresaFactura>String</empresaFactura>
				<direccFactura>String</direccFactura>
				<coloniaFactura>String</coloniaFactura>
				<ciudadFactura>String</ciudadFactura>
				<cpFactura>String</cpFactura>
				<estadoFactura>String</estadoFactura>
				<paisFactura>String</paisFactura>
				<idDireccFormatFactura>String</idDireccFormatFactura>
				<formaPago>String</formaPago>
				<tipoTarjetaCred>String</tipoTarjetaCred>
				<propietarioTarjetaCred>String</propietarioTarjetaCred>
				<numeroTarjetaCred>String</numeroTarjetaCred>
				<expiraTarjetaCred>String</expiraTarjetaCred>
				<ultimaModificacion>String</ultimaModificacion>
				<estadoOrden>String</estadoOrden>
				<fechaOrdenTerminada>String</fechaOrdenTerminada>
				<moneda>String</moneda>
				<valorMoneda>String</valorMoneda>
				<comentario>String</comentario>
				<subTotal>String</subTotal>
				<tarifa>String</tarifa>
				<tipoEnvio>String</tipoEnvio>	        
 */
	    
	        
	    }
	    catch(Exception err){
	    	Log.e("error", err.toString());
	    	return null;
	    }
	    
	    return orden;
	}
	
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View v) {
			TextView tvComentario = (TextView) findViewById(R.id.tvComentarioRevisaPed3);
			Intent intent = new Intent();
			intent.putExtra("comentario", tvComentario.getText().toString());
			intent.putExtra("emailCliente", email);
			intent.putExtra("envioProd", envioProd);
			intent.putStringArrayListExtra("direccionCliente", direccionCliente);
	        intent.setClass(RevisaPedido3.this, RevisaPedido2.class);
	        finish();
	        startActivity(intent);
		}
	};
	
	private OnClickListener ivFinalizarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           try{
        	 //Definición para servicio Web
				String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#insertaOrden";
			    String METHOD_NAME = "insertaOrden";
			    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
			    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
			    SoapSerializationEnvelope envelope;
			    HttpTransportSE httpt;
			 //Fin definición
			    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        httpt = new HttpTransportSE(URL);
		        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
		        envelope.dotNet = false;
  
		        
		        request.addProperty ("orden", llenaOrden());
		        
		        envelope.setOutputSoapObject(request);
		        httpt.call(SOAP_ACTION, envelope);
		        SoapObject result =  (SoapObject) envelope.bodyIn;
	            SoapPrimitive idPedido = (SoapPrimitive) result.getProperty("result");
	            insertaProductos(idPedido.toString());
           }
           catch(Exception err){
        	   Log.e("Error en finalizar",err.toString());
           }
		}
	};

}
