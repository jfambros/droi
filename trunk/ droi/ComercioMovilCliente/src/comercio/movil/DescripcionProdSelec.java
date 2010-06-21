package comercio.movil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
import utils.ListaCesta;
import utils.ObtenerValoresJSon;
import utils.Producto;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class DescripcionProdSelec extends Activity{

	private static final String HOST = "10.0.2.2"; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:categorias:categoriasPortType#obtenerProducto";
    private static final String METHOD_NAME = "obtenerProducto";
    private static final String NAMESPACE = "http://www.your-company.com/categorias.wsdl";
    private static final String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private Producto producto;
    
    
    //botones
    private ImageView ivCesta;
    
    //Tabla

    
    
    private SoapObject result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcionprodselec);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        httpt = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
        envelope.dotNet = false;
        
        Bundle bundle = getIntent().getExtras();
           
        request.addProperty ("idProd", bundle.getString("idProducto"));
        envelope.setOutputSoapObject(request);
        producto = new Producto();
        inicializaCat();
        
        ivCesta = (ImageView) findViewById(R.id.ivAgregaCestaDescripProd);
        ivCesta.setOnClickListener(ivCestaPress);
        

     }
    
    public void inicializaCat(){
    	//ObtenerValoresJSon obtenerValores = new ObtenerValoresJSon();
    	//Bundle prodObtenidos = new Bundle();
        final String url = "http://"+HOST+"/tienda/catalog/images/";
    	TextView tvNombreProd = (TextView) findViewById(R.id.tvNombProdDescripProd);
    	TextView tvModeloProd = (TextView) findViewById(R.id.tvModeloProdDescripProd);
    	TextView tvDescripProd = (TextView) findViewById(R.id.tvDescripProdDescripProd);
    	TextView tvPrecioProd = (TextView) findViewById(R.id.tvPrecioProdDescripProd);
    	TextView tvFabricanteProd = (TextView) findViewById(R.id.tvFabricanteDescripProd);
    	ImageView ivImagenProd = (ImageView) findViewById(R.id.ivImgProdDescripProd);
    
    	
    	ObtenerValoresJSon valoresJSon = new ObtenerValoresJSon();
    	
    	try{
             httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject resultSoap =  (SoapObject) envelope.getResponse();
             String resultDatos = resultSoap.toString(); 
             Log.i("cadena",resultDatos); 

            	 
          	  Bundle prodObtenidos =  valoresJSon.valores(resultDatos.substring(7));
/*
              	    JSONObj = new JSONObject(resultDatos.substring(7)); 
              	    Iterator<String> itr = JSONObj.keys(); 
              	    while (itr.hasNext()) { 
              	        String Key = (String) itr.next(); 
              	        value = JSONObj.getString(Key); 
              	        prodObtenidos.putString(Key, value);
              	         Log.e(Key,prodObtenidos.getString(Key)); 
              	         //System.out.println(Key+" "+bundleResult.getString(Key));
              	    }
 */
            	 
            	 producto.setIdProd(prodObtenidos.getString("idProd"));
            	 producto.setNombreProd(prodObtenidos.getString("nombreProd"));
            	 producto.setDescripProd(prodObtenidos.getString("descripProd"));
            	 producto.setModeloProd(prodObtenidos.getString("modeloProd"));
            	 producto.setCantidadProd(prodObtenidos.getString("cantidadProd"));
            	 producto.setImagenProd(prodObtenidos.getString("imagenProd"));
            	 producto.setPrecioProd(prodObtenidos.getString("precioProd"));
            	 producto.setFabricanteProd(prodObtenidos.getString("fabricanteProd"));
            	 
            	 Log.e("descrip",prodObtenidos.getString("descripProd"));
            	 
            	 tvNombreProd.setText(producto.getNombreProd());
            	 tvModeloProd.setText("Modelo: ["+producto.getModeloProd()+"]");
            	 tvDescripProd.setText(producto.getDescripProd());
            	 tvPrecioProd.setText(producto.getPrecioProd());
            	 tvFabricanteProd.setText(producto.getFabricanteProd());
            	 
            	 ImageView i = new ImageView(DescripcionProdSelec.this);
     			 URL aURL = new URL(url+producto.getImagenProd()); 
                 URLConnection conn = aURL.openConnection(); 
                 conn.connect();
                 InputStream is = conn.getInputStream();                  
                 BufferedInputStream bis = new BufferedInputStream(is); 
                 Bitmap bm = BitmapFactory.decodeStream(bis); 
                 bis.close(); 
                 is.close();  
                 i.setImageBitmap(bm);
                 ivImagenProd.setImageBitmap(bm);
            	 
            	 /*
            	  * <idProd xmlns="">29</idProd>
					<nombreProd xmlns="">Mouse USB negro</nombreProd>
					<imagenProd xmlns="">catmouses.jpg</imagenProd>
					<precioProd xmlns="">60.0000</precioProd>
					<fabricanteProd xmlns="">Logitech</fabricanteProd>
            	  */
            	 //productosCat.setIdProd(prodObtenidos.getString(key));
                 //Categorias oCategorias = new Categorias();
                 //oCategorias.setIdCat(catObtenidas.getString("id"));
                 //oCategorias.setNombreCat(catObtenidas.getString("nombre"));
                 //oCategorias.setImagenCat(catObtenidas.getString("imagen"));
 
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
    	}
	
    }
    
    private OnClickListener ivCestaPress = new OnClickListener() {
		
		public void onClick(View arg0) {
			EditText cantidad = (EditText) findViewById(R.id.etxtCantidadDescripProd);
          
			DatosCesta dc = new DatosCesta();
			dc.setIdProducto(producto.getIdProd());
			dc.setImagenProducto(producto.getImagenProd());
			dc.setNombreProducto(producto.getNombreProd());
			dc.setCantidadProd(cantidad.getText().toString());
			dc.setPrecioProd(producto.getPrecioProd());
			
			ListaCesta.arregloCesta.put(dc.getIdProducto(), dc); 
           Intent intent = new Intent();
           intent.putExtra("idProducto", producto.getIdProd());
		   intent.putExtra("cantidad", cantidad.getText().toString());
           intent.setClass(DescripcionProdSelec.this, Cesta.class);
           startActivity(intent);	
           
           
			
		}
	};
    
    
    
}
