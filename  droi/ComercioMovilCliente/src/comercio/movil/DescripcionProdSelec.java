package comercio.movil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DescripcionProdSelec extends Activity{

	private static final String HOST = "10.0.2.2"; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:categorias:categoriasPortType#obtenerProductosPorCategoria";
    private static final String METHOD_NAME = "obtenerProducto";
    private static final String NAMESPACE = "http://www.your-company.com/categorias.wsdl";
    private static final String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private JSONObject JSONObj;
    private String resultData;
    
    
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
        inicializaCat();				
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
    	
    	String value = "";
    	try{
             httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject resultSoap =  (SoapObject) envelope.getResponse();
             String resultDatos = resultSoap.toString();
             Log.e("cadena",resultDatos);

            	 
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
            	 Producto producto = new Producto();
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
    
    
}