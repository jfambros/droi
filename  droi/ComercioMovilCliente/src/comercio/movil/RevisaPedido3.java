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
import utils.Valores;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
	private ImageView ivInicio = null;
	
	private String HOST = Valores.HOST;
	private String email = null;
	private String nombreC = null;
	private int idClienteA = 0;
	private String tipoPago = null;
	private String comentario = null;
	private double envioProd = 0.0;
	private double subTotal = 0.0;
	private double total = 0.0;
	private String idPedidoC = null;
	private ArrayList<String> direccionCliente = new ArrayList<String>();
	private ArrayList<String> direccionFactura = new ArrayList<String>();
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
        
		ivInicio = (ImageView)findViewById(R.id.ivInicioRevisaPed3);
		ivInicio.setOnClickListener(ivInicioPres);
        
        envioProd = bundle.getDouble("envioProd");
        direccionCliente = bundle.getStringArrayList("direccionCliente");
        direccionFactura = bundle.getStringArrayList("direccionFactura");
        comentario = bundle.getString("comentario");
        idClienteA =  bundle.getInt("idCliente");

        //obtener el tipo de pago
        tipoPago = bundle.getString("tipoPago");
        
        obtieneTipoPago(tipoPago);
        
                
        llenaDireccion(email);
        llenaProductos();
		}
	    catch(Exception err){
	    	Log.e("error create", err.toString());
	    	
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
		        tvCantidad.setWidth(20);
	            
		        TextView tvNombre = new TextView(this);
	        	tvNombre.setText(((DatosCesta) me.getValue()).getNombreProducto());
		        tvNombre.setTextColor(Color.BLACK);
		        tvNombre.setLayoutParams(new LayoutParams(
		        LayoutParams.FILL_PARENT,
		        LayoutParams.FILL_PARENT));
		        tvNombre.setGravity(Gravity.CENTER_VERTICAL);
		        tvNombre.setWidth(50);
		        
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
	        	tvCostoEnvio.setText("Costo env�o: $"+Double.toString(envioProd)+" ");
	        	tvTotal.setText("Total: $"+Double.toString(subTotal)+" ");
	        }
	        else{
	        	tvCostoEnvio.setText("Costo env�o: $"+Double.toString(envioProd)+" ");
	        	total = subTotal + envioProd;
	        	tvTotal.setText("Total: $"+Double.toString(total)+" ");
	        }
	        
		}
		catch(Exception err){
			Log.e("Error llena prod", err.toString());
		}
        
	}
	
	private void obtieneTipoPago(String tipoPago){
		//Definici�n para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosBanco";
	    String METHOD_NAME = "obtenerDatosBanco";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
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
        
        if (tipoPago.equals("Dep�sito")){
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
    	        tvTitulo.setText("Informaci�n para el dep�sito bancario");
    	        
    	        TableRow filaNumCta = new TableRow(this);
            	filaNumCta.setLayoutParams(new LayoutParams(
            	LayoutParams.FILL_PARENT,
            	LayoutParams.WRAP_CONTENT));  
            	
            	TextView tvNumCta = new TextView(this);
            	tvNumCta.setText("N�mero de cuenta: "+numeroCuenta.toString());
    	        tvNumCta.setTextColor(Color.BLACK);
    	        tvNumCta.setLayoutParams(new LayoutParams(
    	        LayoutParams.FILL_PARENT,
    	        LayoutParams.FILL_PARENT));
    	        tvNumCta.setGravity(Gravity.LEFT);
    	        
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
            	tvNombreBanco.setGravity(Gravity.LEFT);
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
            	tvTitular.setGravity(Gravity.LEFT);
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
        
        if (envioProd == 0.0){
        	TextView tvFormaEnvio = (TextView)findViewById(R.id.tvFormaEnvioRevisaPed3);
        	tvFormaEnvio.setText("Forma de env�o: Recoger en tienda");
        	/*
    		TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(
        	LayoutParams.FILL_PARENT,
        	LayoutParams.WRAP_CONTENT));
        	
        	TextView tvFilaVacia = new TextView(this);
        	tvFilaVacia.setText(" ");
        	tvFilaVacia.setTextColor(Color.BLACK);
        	tvFilaVacia.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
        	tvFilaVacia.setTextSize(16);
        	tvFilaVacia.setGravity(Gravity.CENTER_VERTICAL);
        	
        	TextView tvTituloEnvio = new TextView(this);
        	tvTituloEnvio.setText("Forma de env�o: ");
        	tvTituloEnvio.setTextColor(Color.BLACK);
        	tvTituloEnvio.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
        	tvTituloEnvio.setGravity(Gravity.LEFT);
        	
        	TextView tvTipoEnvio = new TextView(this);
        	tvTipoEnvio.setText("Forma de env�o: Recoger en tienda");
        	tvTipoEnvio.setTextColor(Color.BLACK);
        	tvTipoEnvio.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
        	tvTipoEnvio.setGravity(Gravity.LEFT);
        	
        	row.addView(tvFilaVacia);
        	//row.addView(tvTituloEnvio);
        	row.addView(tvTipoEnvio);
        	row.setBackgroundColor(Color.WHITE);
        	tlTipoPago.addView(row);
        	*/
        }
        else{
        	TextView tvFormaEnvio = (TextView)findViewById(R.id.tvFormaEnvioRevisaPed3);
        	tvFormaEnvio.setText("Forma de env�o: Tarifa �nica "+envioProd);
        	/*
        	TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(
        	LayoutParams.FILL_PARENT,
        	LayoutParams.WRAP_CONTENT));
        	
        	TextView tvTituloEnvio = new TextView(this);
        	tvTituloEnvio.setText("Forma de Env�o:");
        	tvTituloEnvio.setTextColor(Color.BLACK);
        	tvTituloEnvio.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
        	tvTituloEnvio.setGravity(Gravity.CENTER_VERTICAL);
        	
        	TextView tvTipoEnvio = new TextView(this);
        	tvTipoEnvio.setText("Forma de env�o: Tarifa �nica: $"+envioProd);
        	tvTipoEnvio.setTextColor(Color.BLACK);
        	tvTipoEnvio.setLayoutParams(new LayoutParams(
	        LayoutParams.FILL_PARENT,
	        LayoutParams.FILL_PARENT));
        	tvTipoEnvio.setGravity(Gravity.LEFT);
        	
        	//row.addView(tvTituloEnvio);
        	row.addView(tvTipoEnvio);
        	row.setBackgroundColor(Color.WHITE);
        	tlTipoPago.addView(row);
        	*/        	
        }

	}
    
	//insertar los productos comprados
	private void insertaProductos(String ordenId){
        try{
        	
			Set set = ListaCesta.arregloCesta.entrySet();
			Iterator i = set.iterator();
			idPedidoC = ordenId;
	    
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
	
	//m�todo para servicio Web que inserta productos
	private void insertaProdSW(int ordenId, int idProd, int cant){
		//Definici�n para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#insertaProductoOrden";
	    String METHOD_NAME = "insertaProductoOrden";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
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
		//Definici�n para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/servicios/servicios.php";
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
			
			orden.setNombreEntrega(direccionCliente.get(0));
			orden.setEmpresaEntrega(direccionCliente.get(1));
			orden.setDireccEntrega(direccionCliente.get(2));
			orden.setColoniaEntrega(direccionCliente.get(3));
			orden.setCiudadEntrega(direccionCliente.get(4));
			orden.setCpEntrega(direccionCliente.get(5));
			orden.setEstadoEntrega(direccionCliente.get(6));
			orden.setPaisEntrega(direccionCliente.get(7));
			orden.setIdDireccFormatEntrega("1");

			orden.setNombreFactura(direccionFactura.get(0));
			orden.setEmpresaFactura(direccionFactura.get(1));
			orden.setDireccFactura(direccionFactura.get(2));
			orden.setColoniaFactura(direccionFactura.get(3));
			orden.setCiudadFactura(direccionFactura.get(4));
			orden.setCpFactura(direccionFactura.get(5));
			orden.setEstadoFactura(direccionFactura.get(6));
			orden.setPaisFactura(direccionFactura.get(7));
			orden.setIdDireccFormatFactura("1");
		
			nombreC = nombreCliente.toString()+" "+apellidoCliente.toString();
			
			if (tipoPago.equals("Tienda")){
				orden.setFormaPago("Pagar en tienda");	
			}
			if (tipoPago.equals("Dep�sito")){
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
			intent.putExtra("idCliente", idClienteA);
			intent.putStringArrayListExtra("direccionCliente", direccionCliente);
			intent.putStringArrayListExtra("direccionFactura", direccionFactura);
	        intent.setClass(RevisaPedido3.this, RevisaPedido2.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivFinalizarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           try{
        	 //Definici�n para servicio Web
				String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#insertaOrden";
			    String METHOD_NAME = "insertaOrden";
			    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
			    String URL = "http://"+HOST+"/servicios/servicios.php";
			    SoapSerializationEnvelope envelope;
			    HttpTransportSE httpt;
			 //Fin definici�n
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
	            
	            //se limpia la cesta
	            ListaCesta.arregloCesta.clear();
	            
	            Intent intent = new Intent();
	            intent.putExtra("idPedido", idPedidoC);
	            intent.putExtra("nombreC", nombreC);
	            intent.putExtra("emailC", email);
	            intent.setClass(RevisaPedido3.this, CompraFinalizada.class);
		        startActivity(intent);
	             finish();
           }
           catch(Exception err){
        	   Log.e("Error en finalizar",err.toString());
           }
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(RevisaPedido3.this, Principal.class);
            startActivity(intent);
            finish();
			
		}
	};

}
