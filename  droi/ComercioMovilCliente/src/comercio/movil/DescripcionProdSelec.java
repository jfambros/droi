package comercio.movil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
import utils.ListaCesta;
import utils.Producto;
import utils.Validaciones;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard.Key;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DescripcionProdSelec extends Activity{

	private static final String HOST = Valores.HOST; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerProducto";
    private static final String METHOD_NAME = "obtenerProducto";
    private static final String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
    private static final String URL = "http://"+HOST+"/servicios/servicios.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private Producto producto;
    Bundle bundle = null;
    
    //botones
    private ImageView ivCesta = null;
    private ImageView ivRegresa = null;
    private ImageView ivInicio = null;
   
    
    
      
    private SoapObject result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcionprodselec);
        
        //acciones
        ivRegresa = (ImageView)findViewById(R.id.ivRegresarDescripProd);
        ivRegresa.setOnClickListener(ivRegresaPres);
        ivCesta = (ImageView) findViewById(R.id.ivAgregaCestaDescripProd);
        ivCesta.setOnClickListener(ivCestaPress);
        ivInicio = (ImageView) findViewById(R.id.ivInicioDescripProdSelec);
        ivInicio.setOnClickListener(ivInicioPres);       
        
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        httpt = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
        envelope.dotNet = false;
        
        bundle = getIntent().getExtras();
           
        request.addProperty ("idProd", bundle.getString("idProducto"));
        envelope.setOutputSoapObject(request);
        producto = new Producto();
        inicializaCat();
     }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
    
    private void inicializaCat(){
    	//ObtenerValoresJSon obtenerValores = new ObtenerValoresJSon();
    	//Bundle prodObtenidos = new Bundle();
        final String url = "http://"+HOST+"/catalog/images/";
    	TextView tvNombreProd = (TextView) findViewById(R.id.tvNombProdDescripProd);
    	TextView tvModeloProd = (TextView) findViewById(R.id.tvModeloProdDescripProd);
    	TextView tvDescripProd = (TextView) findViewById(R.id.tvDescripProdDescripProd);
    	TextView tvPrecioProd = (TextView) findViewById(R.id.tvPrecioProdDescripProd);
    	TextView tvFabricanteProd = (TextView) findViewById(R.id.tvFabricanteDescripProd);
    	ImageView ivImagenProd = (ImageView) findViewById(R.id.ivImgProdDescripProd);
     
    	
    	try{
             httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject resultSoap =  (SoapObject) envelope.getResponse();
             
             //obtenemos las primitivas
             SoapPrimitive id = (SoapPrimitive) resultSoap.getProperty("idProd");
             SoapPrimitive nombre = (SoapPrimitive) resultSoap.getProperty("nombreProd");
             SoapPrimitive descripcion = (SoapPrimitive) resultSoap.getProperty("descripProd");
             SoapPrimitive modelo = (SoapPrimitive) resultSoap.getProperty("modeloProd");
             SoapPrimitive cantidad = (SoapPrimitive) resultSoap.getProperty("cantidadProd");
             SoapPrimitive imagen = (SoapPrimitive) resultSoap.getProperty("imagenProd");
             SoapPrimitive precio = (SoapPrimitive) resultSoap.getProperty("precioProd");
             SoapPrimitive fabricante = (SoapPrimitive) resultSoap.getProperty("fabricanteProd");
     	
             double precioProd = Double.parseDouble(precio.toString());
            	 producto.setIdProd(id.toString());
            	 producto.setNombreProd(nombre.toString());
            	 producto.setDescripProd(descripcion.toString());
            	 producto.setModeloProd(modelo.toString());
            	 producto.setCantidadProd(cantidad.toString());
            	 producto.setImagenProd(imagen.toString());
            	 producto.setPrecioProd(Double.toString(precioProd));
            	 producto.setFabricanteProd(fabricante.toString());
            	 
            	 
            	 tvNombreProd.setText(producto.getNombreProd());
            	 tvModeloProd.setText("Modelo: ["+producto.getModeloProd()+"]");
            	 tvDescripProd.setText(producto.getDescripProd());
            	 tvPrecioProd.setText("$"+producto.getPrecioProd());
            	 tvFabricanteProd.setText(producto.getFabricanteProd());
            	 
            	 ImageView i = new ImageView(DescripcionProdSelec.this);
            	 
            	 
            	 /*
     			 URL aURL = new URL(url+producto.getImagenProd()); 
                 URLConnection conn = aURL.openConnection(); 
                 conn.connect();
                 InputStream is = conn.getInputStream();                  
                 BufferedInputStream bis = new BufferedInputStream(is); 
                 Bitmap bm = BitmapFactory.decodeStream(bis); 
                 bis.close(); 
                 is.close();
                 */
		        	//cambio
            	String rutaUrl = url+producto.getImagenProd();
				HttpGet httpRequest = null;
				try {
					httpRequest = new HttpGet(rutaUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
				HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
		        HttpEntity entity = response.getEntity();
		        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity); 
		        InputStream instream = bufHttpEntity.getContent();
		        Bitmap bm = BitmapFactory.decodeStream(instream);
				//fin
		        i.setImageBitmap(bm);
		        ivImagenProd.setImageBitmap(bm);
            	 
 
    	}
    	catch(Exception err){
        	new AlertDialog.Builder(DescripcionProdSelec.this)

        	.setTitle("error en inicializaCat()")

        	.setMessage(err.toString())

        	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

        	public void onClick(DialogInterface dialog, int whichButton) {

        	setResult(RESULT_OK);
        	  }
        	})
        	.show();   
        	Log.e("Error", err.toString());
    	}
	
    }
    
    private boolean validaCantidad(int idProd, int cantidad){
    	 //Definición para servicio Web
		String SOAP_ACTIONV = "capeconnect:servicios:serviciosPortType#validaCantidad";
	    String METHOD_NAMEV = "validaCantidad";
	    String NAMESPACEV = "http://www.your-company.com/servicios.wsdl";
	    String URLV = "http://"+HOST+"/servicios/servicios.php";
	    SoapSerializationEnvelope envelopeValida;
	    HttpTransportSE httptValida;
	    SoapObject resultValida;
	    
	    SoapObject requestValida = new SoapObject(NAMESPACEV, METHOD_NAMEV);
		try{
	        httptValida = new HttpTransportSE(URLV);
	        envelopeValida = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelopeValida.dotNet = false;
	        requestValida.addProperty ("idProd", idProd );
	        requestValida.addProperty ("cantidad", cantidad);
	        envelopeValida.setOutputSoapObject(requestValida);
	        httptValida.call(SOAP_ACTIONV, envelopeValida);
	        resultValida = (SoapObject)envelopeValida.bodyIn;
	        SoapPrimitive result = (SoapPrimitive) resultValida.getProperty("result");
	        //Log.e("resultado valida cant", result.toString());
	        if (Integer.parseInt(result.toString()) == 1){
	        	return true;
	        }
	        else{
	        	return false;
	        }
	    	
	    }catch (Exception e) {
	    	return false;
		}
    }
    
    private OnClickListener ivCestaPress = new OnClickListener() {
		
		public void onClick(View arg0) {
			EditText cantidad = (EditText) findViewById(R.id.etxtCantidadDescripProd);
			
			if (!Validaciones.esNumero(cantidad.getText().toString())){
				   mensajeError("Error", "Capture la cantidad");
				   /*
				if (Validaciones.esNumero(cantidad.getText().toString())){
				   if (Integer.parseInt(cantidad.getText().toString()) <= 0 )
					   mensajeError("Error", "La cantidad debe ser mayor o igual a 1");
				}
				*/
			}
			else{
				if (Integer.parseInt(cantidad.getText().toString())!= 0 ){
					if (validaCantidad(Integer.parseInt(producto.getIdProd()), Integer.parseInt(cantidad.getText().toString())) == true){
						DatosCesta dc = new DatosCesta();
						dc.setIdProducto(producto.getIdProd());
						dc.setImagenProducto(producto.getImagenProd());
						dc.setNombreProducto(producto.getNombreProd());
						dc.setCantidadProd(Integer.parseInt(cantidad.getText().toString()));
						dc.setPrecioProd(Double.parseDouble(producto.getPrecioProd()));
						
						DatosCesta sumaCant =   ListaCesta.arregloCesta.get(producto.getIdProd());
						if (sumaCant != null){
							int cantidadDatos = sumaCant.getCantidadProd();
							sumaCant.setCantidadProd(cantidadDatos+Integer.parseInt(cantidad.getText().toString()));
							ListaCesta.arregloCesta.put(producto.getIdProd(), sumaCant);
						}
						else{
							ListaCesta.arregloCesta.put(dc.getIdProducto(), dc);	
						}
						
						 
			           Intent intent = new Intent();
			           intent.putExtra("idProducto", producto.getIdProd());
					   intent.putExtra("cantidad", cantidad.getText().toString());
			           intent.setClass(DescripcionProdSelec.this, Cesta.class);
			           startActivity(intent);
			           finish();
					}
					else{
						mensajeError("Error", "La cantidad capturada es mayor que la existente en inventario");
					}
			}
			else{
			   mensajeError("Error", "Capture cantidad");	
			}
		  }
		}
	};
	
	private OnClickListener ivRegresaPres = new OnClickListener() {
		
		public void onClick(View v) {
           Intent intent = new Intent();
           if (bundle.getInt("busca") != 0){
	           intent.putExtra("palabra", bundle.getString("palabra"));
	           intent.setClass(DescripcionProdSelec.this, ProductosListCatLVBuscar.class);
	           startActivity(intent);
	           finish();
           }
           else{
     
	           intent.putExtra("idCategoria", bundle.getString("idCategoria"));
	           intent.setClass(DescripcionProdSelec.this, ProductosListCatLV.class);
	           startActivity(intent);
	           finish();
           }
		}
	};
    
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           Intent intent = new Intent();
           intent.setClass(DescripcionProdSelec.this, Principal.class);
           startActivity(intent);
           finish();		
		}
	};
	
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(DescripcionProdSelec.this)

     	.setTitle(titulo)

     	.setMessage(msj)

     	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

     	public void onClick(DialogInterface dialog, int whichButton) {

     	setResult(RESULT_OK);
     	  }
     	})
     	.show();   
	 }
    
}
