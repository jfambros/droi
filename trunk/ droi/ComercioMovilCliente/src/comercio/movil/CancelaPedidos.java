package comercio.movil;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.ListadoPedidos;
import utils.Valores;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CancelaPedidos extends ListActivity{
	private ImageView ivInicio;
	private ImageView ivRegresar;
	private IconListViewAdapter adaptador;
	private ArrayList<ListadoPedidos> listadoPedidos = null;
	private String HOST = Valores.HOST; 
	
	private Bundle bundle;
	private int idClienteActual;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cancelapedidos);
		
		bundle = getIntent().getExtras(); 
			
		ivInicio = (ImageView)findViewById(R.id.ivInicioCancelaPedidos);
		ivInicio.setOnClickListener(ivInicioPres);
		
		ivRegresar = (ImageView)findViewById(R.id.ivRegresarCancelaPedidos);
		ivRegresar.setOnClickListener(ivRegresarPres);
		
		obtieneIdCliente();
		inicializaPedidos();
		
		this.adaptador = new IconListViewAdapter(this, R.layout.cancelapedidorow, listadoPedidos);
		setListAdapter(this.adaptador);
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
    
    private void inicializaPedidos(){
		listadoPedidos = new ArrayList<ListadoPedidos>();
		
		 //Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerEstadoPedidos";
	    String METHOD_NAME = "obtenerEstadoPedidos";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result;
	 //Fin definición
	    try{
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        request.addProperty ("idCliente", idClienteActual);
	        request.addProperty ("estado", "Pendiente");
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
            result = (SoapObject)envelope.bodyIn;
            SoapObject result2 =  (SoapObject) envelope.getResponse();
 		   SoapObject ped = (SoapObject) result2.getProperty("pedidos");
		   if (ped.getPropertyCount()!=0){
            for(int cont=0; cont< result2.getPropertyCount(); cont ++){
            	 SoapObject resultados = (SoapObject) result2.getProperty(cont);
            	 SoapPrimitive idOrden = (SoapPrimitive) resultados.getProperty("idOrden");
            	 SoapPrimitive fechaOrden = (SoapPrimitive) resultados.getProperty("fechaOrden");
            	 SoapPrimitive cantidadProd = (SoapPrimitive) resultados.getProperty("cantidadProd");
            	 SoapPrimitive totalOrden = (SoapPrimitive) resultados.getProperty("totalOrden");
            	 SoapPrimitive estadoOrden = (SoapPrimitive) resultados.getProperty("estadoOrden");
            	 
            	 String fechaTemp = fechaOrden.toString().substring(0, 10);
            	 String anio = fechaTemp.substring(0,4);
            	 String mes = fechaTemp.substring(5,7);
            	 String dia = fechaTemp.substring(8, 10);
            	 
            	 ListadoPedidos lp = new ListadoPedidos();
            	 lp.setIdPedido(Integer.parseInt(idOrden.toString()));
            	 lp.setFecha(dia+"/"+mes+"/"+anio);
            	 lp.setCantProd(Integer.parseInt(cantidadProd.toString()));
            	 lp.setPrecioTot(Double.parseDouble(totalOrden.toString()));
            	 lp.setEstado(estadoOrden.toString());
            	 
            	 listadoPedidos.add(lp);
            	 
            }
		   }
		   else{
          	 //ListadoPedidos lp = new ListadoPedidos();
          	 listadoPedidos.add(null);

			   /*
            	TableLayout tlRevisaPed = (TableLayout)findViewById(R.id.tlRevisaPedidos);
               	tlRevisaPed.removeAllViewsInLayout();
            	
            	TableRow row = new TableRow(RevisaPedidosCliente.this);
            	row.setLayoutParams(new LayoutParams(
            	LayoutParams.FILL_PARENT,
            	LayoutParams.WRAP_CONTENT));
            	
            	Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
            			   R.drawable.sinpedidos100x100);
                Bitmap bMapScala = Bitmap.createScaledBitmap(mBitmap, 100, 100, true);
                BitmapDrawable bmd = new BitmapDrawable(bMapScala);
            	
            	ImageView imagenSinPed = new ImageView(RevisaPedidosCliente.this);
    	            imagenSinPed.setLayoutParams(new LayoutParams(
    	            LayoutParams.FILL_PARENT,
    	            LayoutParams.FILL_PARENT));
    	            imagenSinPed.setImageDrawable(bmd);
    	            
    	            row.addView(imagenSinPed);
    	            row.setBackgroundColor(Color.WHITE);

    	            tlRevisaPed.addView(row);     
    	            */     	
		   }
	    }
	    catch (Exception err) {
			Log.e("Error inicializa Pedidos", err.toString());
		}
	    
		
	}
	
    private void obtieneIdCliente(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
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
	        request.addProperty ("emailCliente", bundle.getString("emailCliente"));
	        envelope.setOutputSoapObject(request);
	        httpt.call(SOAP_ACTION, envelope);
	        result =  (SoapObject) envelope.bodyIn;
	        SoapObject resultSoap =  (SoapObject) envelope.getResponse();
	        
	        SoapPrimitive idCliente = (SoapPrimitive) resultSoap.getProperty("idCliente");
	        idClienteActual = Integer.parseInt(idCliente.toString());
	        
	    }
	    catch (Exception err) {
	    	Log.e("Error obtieneId",err.toString());

		}
	}
	
	public class IconListViewAdapter extends ArrayAdapter<ListadoPedidos> {

        private ArrayList<ListadoPedidos> items;
        private String url = "http://"+HOST+"/tienda/catalog/images/";

        public IconListViewAdapter(Context context, int textViewResourceId, ArrayList<ListadoPedidos> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.cancelapedidorow, null);
                }
                ListadoPedidos lp = items.get(position);
                if (lp != null) {
                	TextView tvNoPedido = (TextView)v.findViewById(R.id.tvNoPedidoCancelaPedido);
                	TextView tvFechaPedido = (TextView)v.findViewById(R.id.tvFechaCancelaPedido);
                	TextView tvCantProd = (TextView)v.findViewById(R.id.tvCantidadProdCancelaPedido);
                	TextView tvTotalPedido = (TextView)v.findViewById(R.id.tvTotalCancelaPedido);
                	ImageView ivPedido = (ImageView)v.findViewById(R.id.ivLogoPedidoCancelaPedido);
                	ImageView ivVerMas = (ImageView)v.findViewById(R.id.ivVerMasCancelaPedido);
                	
                	if (tvNoPedido != null){	
                		tvNoPedido.setText("No. pedido: "+Integer.toString(lp.getIdPedido())+"  ");
                	}
            		
                	if (tvFechaPedido != null){
                		tvFechaPedido.setText("Fecha compra: "+lp.getFecha()+"  ");
                	}
            		
                	if (tvCantProd != null){
                		tvCantProd.setText("Productos: "+Integer.toString(lp.getCantProd())+"  ");
                	}
            			
                	if (tvTotalPedido != null){
                		tvTotalPedido.setText("Total: "+Double.toString(lp.getPrecioTot())+" ");
                	}
                	
                	Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pedido32x32);
             			   Bitmap bMapScala = Bitmap.createScaledBitmap(mBitmap, 32, 32, true);
             			   BitmapDrawable bmd = new BitmapDrawable(bMapScala);
             			   
             		ivPedido.setImageDrawable(bmd);
             		
             		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vermas32x32);
             		bMapScala = Bitmap.createScaledBitmap(mBitmap, 32, 32, true);
             		bmd = new BitmapDrawable(bMapScala);
             		
             		ivVerMas.setImageDrawable(bmd);
             			   
                	
                	
                }
                else{
                	/*
     			   ImageView ivMensaje = (ImageView)v.findViewById(R.id.ivLogoPedidoListadoPedidos);
     			   Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
           			R.drawable.sinpedidos100x100);
     			   Bitmap bMapScala = Bitmap.createScaledBitmap(mBitmap, 100, 100, true);
     			   BitmapDrawable bmd = new BitmapDrawable(bMapScala);
     			   ivMensaje.setImageDrawable(bmd);
     			   */
                }
            
                return v;
        }
	}
	
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListadoPedidos pedido = (ListadoPedidos) l.getItemAtPosition(position);    
        Log.i("id", Integer.toString(pedido.getIdPedido()) );
        //Toast.makeText(this, producto.getNombreProd(), Toast.LENGTH_LONG).show();
        if (pedido.getIdPedido() != 0){
        	
			Intent intent = new Intent(); 
			intent.putExtra("idPedido",pedido.getIdPedido());
			intent.putExtra("emailCliente", bundle.getString("emailCliente"));
			intent.putExtra("idCliente", idClienteActual);
			intent.putExtra("contra", bundle.getString("contra"));  
			intent.setClass(CancelaPedidos.this, DetalleCancelaPedido.class);
			startActivity(intent);  
			finish();
			
        }
    }	
	
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(CancelaPedidos.this, Principal.class);
            startActivity(intent);
            finish();				
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.putExtra("emailCliente", bundle.getString("emailCliente"));
            intent.putExtra("contra", bundle.getString("contra"));  
            intent.setClass(CancelaPedidos.this, DatosCuenta.class);
            startActivity(intent);
            finish();				
		}
	};
}
