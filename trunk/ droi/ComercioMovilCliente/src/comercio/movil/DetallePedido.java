package comercio.movil;

import java.nio.IntBuffer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
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

public class DetallePedido extends Activity{
	private Bundle bundle;
	private int idPedido;
	private String emailCliente;
	private int idCliente;
	private double tarifa;
	
	private String HOST = Valores.HOST;
	
	private ImageView ivInicio;
	private ImageView ivRegresar;
	private ImageView ivInicioDatosCte;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detallepedido);
		
		ivInicio = (ImageView)findViewById(R.id.ivInicioDetallePedido);
		ivInicio.setOnClickListener(ivInicioPres);
		
		ivRegresar = (ImageView)findViewById(R.id.ivRegresarDetallePedido);
		ivRegresar.setOnClickListener(ivRegresarPres);
		
		ivInicioDatosCte = (ImageView)findViewById(R.id.ivInicioDatosCteDetallePedido);
		ivInicioDatosCte.setOnClickListener(ivInicioDatosCtePres);
		
		
		bundle = getIntent().getExtras();
		idPedido = bundle.getInt("idPedido");
		emailCliente = bundle.getString("emailCliente");
		idCliente = bundle.getInt("idCliente");
		/*
		 * idPedido
		 * emailCliente
		 * idCliente
		 */
		obtenerDetallePedido();
		llenaProductos();
		
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private void obtenerDetallePedido(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDetallePedido";
	    String METHOD_NAME = "obtenerDetallePedido";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	    
	    /*
	     * Objetos para desplegar
	     */
	    TextView tvNoPedido = (TextView)findViewById(R.id.tvNoPedidoDetallePedido);
	    TextView tvFechaPed = (TextView)findViewById(R.id.tvFechaDetallePedido);
	    TextView tvEmpresa = (TextView)findViewById(R.id.tvEmpresaDetallePedido);
	    TextView tvNombre = (TextView)findViewById(R.id.tvNombreDetallePedido);
	    TextView tvDirecc = (TextView)findViewById(R.id.tvDireccionDetallePedido);
	    TextView tvColonia = (TextView)findViewById(R.id.tvColoniaDetallePedido);
	    TextView tvCiudadYCP = (TextView)findViewById(R.id.tvCiudadYCPDetallePedido);
	    TextView tvEstadoyPais = (TextView) findViewById(R.id.tvEstadoYPaisDetallePedido);
	    TextView tvFormaPago = (TextView)findViewById(R.id.tvFormaPagoDetallePedido);
	    TextView tvFormaEnvio = (TextView)findViewById(R.id.tvFormaEnvioDetallePedido);
	    TextView tvComentario = (TextView)findViewById(R.id.tvComentarioDetallePedido);
	    
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("idPedido", idPedido);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result =  (SoapObject) envelope.bodyIn;
	        SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	        
	        SoapPrimitive idCliente = (SoapPrimitive) resultSoap.getProperty("idCliente");
	        SoapPrimitive nombreEntrega = (SoapPrimitive) resultSoap.getProperty("nombreEntrega");
	       
	        String empresaEntrega;
	        if (((SoapPrimitive) resultSoap.getProperty("empresaEntrega")).toString().equals("anyType={}")){
	        	empresaEntrega = "_";
	        }
	        else{
	        	empresaEntrega = ((SoapPrimitive) resultSoap.getProperty("empresaEntrega")).toString();	
	        }
	        
	        SoapPrimitive direccEntrega = (SoapPrimitive) resultSoap.getProperty("direccEntrega");
	        SoapPrimitive coloniaEntrega = (SoapPrimitive) resultSoap.getProperty("coloniaEntrega");
	        SoapPrimitive ciudadEntrega = (SoapPrimitive) resultSoap.getProperty("ciudadEntrega");
	        SoapPrimitive cpEntrega = (SoapPrimitive) resultSoap.getProperty("cpEntrega");
	        SoapPrimitive estadoEntrega = (SoapPrimitive) resultSoap.getProperty("estadoEntrega");
	        SoapPrimitive paisEntrega = (SoapPrimitive) resultSoap.getProperty("paisEntrega");
	        SoapPrimitive nombreFactura = (SoapPrimitive) resultSoap.getProperty("nombreFactura");
	        
	        SoapPrimitive formaPago = (SoapPrimitive) resultSoap.getProperty("formaPago");
	        SoapPrimitive formaEnvio = (SoapPrimitive) resultSoap.getProperty("tarifa");

	        String empresaFactura;
	        if (((SoapPrimitive) resultSoap.getProperty("empresaFactura")).toString().equals("anyType={}")){
	        	empresaFactura = "_";
	        }
	        else{
	        	empresaFactura = ((SoapPrimitive) resultSoap.getProperty("empresaFactura")).toString();	
	        }

	        SoapPrimitive direccFactura = (SoapPrimitive) resultSoap.getProperty("direccFactura");
	        SoapPrimitive coloniaFactura = (SoapPrimitive) resultSoap.getProperty("coloniaFactura");
	        SoapPrimitive ciudadFactura = (SoapPrimitive) resultSoap.getProperty("ciudadFactura");
	        SoapPrimitive cpFactura = (SoapPrimitive) resultSoap.getProperty("cpFactura");
	        SoapPrimitive estadoFactura = (SoapPrimitive) resultSoap.getProperty("estadoFactura");
	        SoapPrimitive paisFactura = (SoapPrimitive) resultSoap.getProperty("paisFactura");
	        
	        SoapPrimitive estadoOrden = (SoapPrimitive) resultSoap.getProperty("estadoOrden");
	        //SoapPrimitive comentario = (SoapPrimitive) resultSoap.getProperty("comentario");
	        
	        String comentarios;
	        if (((SoapPrimitive) resultSoap.getProperty("comentario")).toString().equals("anyType={}")){
	        	comentarios = "_";
	        }
	        else{
	        	comentarios = ((SoapPrimitive) resultSoap.getProperty("comentario")).toString();	
	        }
	        
	        tarifa = Double.parseDouble(((SoapPrimitive) resultSoap.getProperty("tarifa")).toString());
	        
	        
	        SoapPrimitive fechaOrden = (SoapPrimitive) resultSoap.getProperty("fechaOrden");
	        
	        String fechaTemp = fechaOrden.toString().substring(0, 10);
       	 	String anio = fechaTemp.substring(0,4);
       	 	String mes = fechaTemp.substring(5,7);
       	 	String dia = fechaTemp.substring(8, 10);
	        
	        tvNoPedido.setText("No. de pedido: "+idPedido+" ("+estadoOrden+")");
	        tvFechaPed.setText("Fecha de pedido: "+dia+"/"+mes+"/"+"/"+anio);
	        tvEmpresa.setText(empresaEntrega);
	        tvNombre.setText(nombreEntrega.toString());
	        tvDirecc.setText(direccEntrega.toString());
	        tvColonia.setText(coloniaEntrega.toString());
	        tvCiudadYCP.setText(ciudadEntrega.toString()+", "+cpEntrega.toString());
	        tvEstadoyPais.setText(estadoEntrega.toString()+", "+paisEntrega.toString());
	        
	        tvFormaPago.setText(formaPago.toString());
	        if (tarifa == 0.0){
	        	tvFormaEnvio.setText("Recoger en tienda(Sin costo) $"+tarifa);
	        }
	        else{
	        	tvFormaEnvio.setText("Tarifa única $"+tarifa);
	        }
	        
	        tvComentario.setText(comentarios);
	        
	        /*
	         * <idCliente xmlns="">2</idCliente>
				<nombreCliente xmlns="">Jorge Ambr</nombreCliente>
				<empresaCliente xmlns=""/>
				<direccCliente xmlns="">Calle 10</direccCliente>
				<coloniaCliente xmlns="">Quince letras</coloniaCliente>
				<ciudadCliente xmlns="">Huat</ciudadCliente>
				<cpCliente xmlns="">7852</cpCliente>
				<estadoCliente xmlns="">Veracruz</estadoCliente>
				<paisCliente xmlns="">Mexico</paisCliente>
				<telefonoCliente xmlns="">27373123</telefonoCliente>
				<emailCliente xmlns="">jferaa007@gmail.com</emailCliente>
				<idDireccFormatCliente xmlns="">1</idDireccFormatCliente>
				<nombreEntrega xmlns="">Jorge Ambr</nombreEntrega>
				<empresaEntrega xmlns=""/>
				<direccEntrega xmlns="">Calle 10</direccEntrega>
				<coloniaEntrega xmlns="">Quince letras</coloniaEntrega>
				<ciudadEntrega xmlns="">Huat</ciudadEntrega>
				<cpEntrega xmlns="">7852</cpEntrega>
				<estadoEntrega xmlns="">Veracruz</estadoEntrega>
				<paisEntrega xmlns="">Mexico</paisEntrega>
				<idDireccFormatEntrega xmlns="">1</idDireccFormatEntrega>
				<nombreFactura xmlns="">Jorge Ambr</nombreFactura>
				<empresaFactura xmlns=""/>
				<direccFactura xmlns="">Calle 10</direccFactura>
				<coloniaFactura xmlns="">Quince letras</coloniaFactura>
				<ciudadFactura xmlns="">Huat</ciudadFactura>
				<cpFactura xmlns="">7852</cpFactura>
				<estadoFactura xmlns="">Veracruz</estadoFactura>
				<paisFactura xmlns="">Mexico</paisFactura>
				<idDireccFormatFactura xmlns="">1</idDireccFormatFactura>
				<formaPago xmlns="">Pagar en tienda</formaPago>
				<tipoTarjetaCred xmlns=""/>
				<propietarioTarjetaCred xmlns=""/>
				<numeroTarjetaCred xmlns=""/>
				<expiraTarjetaCred xmlns=""/>
				<ultimaModificacion xmlns=""/>
				<fechaOrden xmlns="">2010-08-02 23:22:37</fechaOrden>
				<estadoOrden xmlns="">1</estadoOrden>
				<fechaOrdenTerminada xmlns=""/>
				<comentario xmlns="">s</comentario>
				<subTotal xmlns="">1120.0000</subTotal>
				<tarifa xmlns="">0.0000</tarifa>
				<tipoEnvio xmlns="">Recoger en tienda (Sin costo):</tipoEnvio>
	         */
	    }
	    catch (Exception e) {
	    	Log.e("error obtenerDetalle", e.toString());

		}
		
	}
	
	private void llenaProductos(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerProductosOrden";
	    String METHOD_NAME = "obtenerProductosOrden";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	    
	    double subTot = 0.0;
	    double subTotal = 0.0;
	    
	    /*
	     * Objetos para desplegar
	     */
	    TextView tvSubTotal = (TextView)findViewById(R.id.tvSubtotalEnvioDetallePedido);
	    TextView tvCostoEnvio = (TextView)findViewById(R.id.tvCostoEnvioDetallePedido);
	    TextView tvTotal = (TextView)findViewById(R.id.tvTotalDetallePedido);
	    
	    TableLayout tlProd = (TableLayout)findViewById(R.id.tlProductosDetallePedido);
	    
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("idPedido", idPedido);
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result = (SoapObject)envelope.bodyIn;
	        SoapObject result2 =  (SoapObject) envelope.getResponse();
			SoapObject prod = (SoapObject) result2.getProperty("productos");
		   if (prod.getPropertyCount()!=0){
			   for(int cont=0; cont< result2.getPropertyCount(); cont ++){
				   SoapObject resultados = (SoapObject) result2.getProperty(cont);
				   SoapPrimitive idProd = (SoapPrimitive) resultados.getProperty("idProd");
				   SoapPrimitive modeloProd = (SoapPrimitive) resultados.getProperty("modeloProd");
				   SoapPrimitive nombreProd = (SoapPrimitive) resultados.getProperty("nombreProd");
				   SoapPrimitive precioProd = (SoapPrimitive) resultados.getProperty("precioProd");
				   SoapPrimitive precioFinalProd = (SoapPrimitive) resultados.getProperty("precioFinalProd");
				   SoapPrimitive cantidadProd = (SoapPrimitive) resultados.getProperty("cantidadProd");
				   
				   //llenando los productos
				   TableRow row = new TableRow(this);
		        	row.setLayoutParams(new LayoutParams(
		        	LayoutParams.FILL_PARENT,
		        	LayoutParams.WRAP_CONTENT));
		        	
		        	TextView tvCantidad = new TextView(this);
		        	int cant = Integer.parseInt(cantidadProd.toString());
		        	tvCantidad.setText(Integer.toString(cant));
			        tvCantidad.setTextColor(Color.BLACK);
			        tvCantidad.setLayoutParams(new LayoutParams(
			        LayoutParams.FILL_PARENT,
			        LayoutParams.FILL_PARENT));
			        tvCantidad.setGravity(Gravity.CENTER_VERTICAL);
			        tvCantidad.setWidth(50);
			        
			        TextView tvNombre = new TextView(this);
		        	tvNombre.setText(nombreProd.toString());
			        tvNombre.setTextColor(Color.BLACK);
			        tvNombre.setLayoutParams(new LayoutParams(
			        LayoutParams.FILL_PARENT,
			        LayoutParams.FILL_PARENT));
			        tvNombre.setGravity(Gravity.CENTER_VERTICAL);
			        
			        subTot = Integer.parseInt(cantidadProd.toString()) * Double.parseDouble(precioProd.toString());
			        
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
			        
			        tlProd.addView(row);
			        subTotal += subTot; //Double.parseDouble(((TextView)(row.getChildAt(2))).getText().toString());
				    tvSubTotal.setText("Subtotal "+Double.toString(subTotal)+" ");
				    
			   }
			   if (tarifa == 0.0){
		        	tvCostoEnvio.setText("Costo envío: $"+Double.toString(tarifa)+" ");
		        	tvTotal.setText("Total: "+ Double.toString(subTotal)+" ");
		        }
		        else{
		        	tvCostoEnvio.setText("Costo envío: $"+Double.toString(tarifa)+" ");
		        	double total = subTotal + tarifa;
		        	tvTotal.setText("Total: $"+Double.toString(total)+" ");
		        }
			   
			   
	       }

	    }
	    catch (Exception e) {
	    	Log.e("Error llenaProd", e.toString());

		}
	}
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(DetallePedido.this, Principal.class);
            startActivity(intent);
            finish();
			
		}
	};
	
private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View v) {
           Intent intent = new Intent();
           intent.putExtra("emailCliente", emailCliente);
           intent.putExtra("contra", bundle.getString("contra"));  
           intent.setClass(DetallePedido.this, RevisaPedidosCliente.class);
           startActivity(intent);
           finish();           
		}
	};
	
private OnClickListener ivInicioDatosCtePres = new OnClickListener() {
		
		public void onClick(View v) {
           Intent intent = new Intent();
           intent.putExtra("emailCliente", emailCliente);
           intent.putExtra("contra", bundle.getString("contra"));  
           intent.setClass(DetallePedido.this, DatosCuenta.class);
           startActivity(intent);
           finish();           
		}
	};
	
}
	
