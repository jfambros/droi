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

import utils.ProductosCat;
import utils.ProductosListCat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MuestraProductosPorCat extends Activity {
	private static final String HOST = "10.0.2.2"; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:categorias:categoriasPortType#obtenerProductosPorCategoria";
    private static final String METHOD_NAME = "obtenerProductosPorCategorias";
    private static final String NAMESPACE = "http://www.your-company.com/categorias.wsdl";
    private static final String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private JSONObject JSONObj;
    private Bundle bundleResult=new Bundle();
    private String resultData;
    
    private ArrayList<ProductosCat> listaProductos;
    
    private SoapObject result;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productoslistcat);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        httpt = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
        envelope.dotNet = false;
        
        Bundle bundle = getIntent().getExtras();
         //String id = "1";//bundle.getString("2");
           
        request.addProperty ("idCategoria", bundle.getString("idCategoria"));
       // envelope.bodyOut = request; 
        envelope.setOutputSoapObject(request);
        inicializaCat();
        
        
        GridView gridview = (GridView) findViewById(R.id.gridviewCat);
        ((GridView) findViewById(R.id.gridviewCat)).setOnItemClickListener(clickList);
		
		try{
		gridview.setAdapter(new ImageAdapter(this, listaProductos.size(), listaProductos));
		}
		catch (Exception e) {
	    	new AlertDialog.Builder(MuestraProductosPorCat.this)

	    	.setTitle("error en OnCreate")

	    	.setMessage(e.toString())

	    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	    	public void onClick(DialogInterface dialog, int whichButton) {

	    	setResult(RESULT_OK);
	    	  }
	    	})
	    	.show();
	    }
		
		
     }
    
    
	private OnItemClickListener clickList = new OnItemClickListener() {

		public void onItemClick(AdapterView parent, View v, int position, long id) {
			try{
				String val = ((ProductosCat) parent.getAdapter().getItem(position)).getNombreProd();
				String idProd =((ProductosCat) parent.getAdapter().getItem(position)).getIdProd();
				Toast.makeText(MuestraProductosPorCat.this, "Id: " + idProd +" Nombre "+  val,Toast.LENGTH_SHORT).show();
				Intent i = new Intent(); 
				i.putExtra("idProducto", idProd);
				i.setClass(MuestraProductosPorCat.this, DescripcionProdSelec.class);
				startActivity(i);
			}
			catch (Exception e) {
		    	new AlertDialog.Builder(MuestraProductosPorCat.this)

		    	.setTitle("error en OnItemClic()")

		    	.setMessage(e.toString())

		    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

		    	public void onClick(DialogInterface dialog, int whichButton) {

		    	setResult(RESULT_OK);
		    	  }
		    	})
		    	.show();
		    }

		   
		}
	};  
    
    public Bundle valores(String cadena){
    	String value;
    	try{
        if (resultData.startsWith("anyType")) { // if JSON string is an object 
     	    JSONObj = new JSONObject(cadena); 
     	    Iterator<String> itr = JSONObj.keys(); 
     	    while (itr.hasNext()) { 
     	        String Key = (String) itr.next(); 
     	        value = JSONObj.getString(Key); 
     	        bundleResult.putString(Key, value);
     	         Log.e(Key,bundleResult.getString(Key)); 
     	    }
         }
    } catch (Exception e) {
    	new AlertDialog.Builder(MuestraProductosPorCat.this)

    	.setTitle("error en valores()")

    	.setMessage(e.toString())

    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

    	public void onClick(DialogInterface dialog, int whichButton) {

    	setResult(RESULT_OK);
    	  }
    	})
    	.show();
    }
        return bundleResult;
    }
    
    public void inicializaCat(){
    	listaProductos = new ArrayList<ProductosCat>();
    	try{
             httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject result2 =  (SoapObject) envelope.getResponse();
             Log.e("cadena",result2.toString());
             for(int cont=0; cont< result2.getPropertyCount(); cont ++){
            	 resultData = result2.getProperty(cont).toString();
            	 String resul2 = resultData.substring(7);
            	 Bundle prodObtenidos =  valores(resul2);
            	 
            	 ProductosCat productosCat = new ProductosCat();
            	 productosCat.setIdProd(prodObtenidos.getString("idProd"));
            	 productosCat.setNombreProd(prodObtenidos.getString("nombreProd"));
            	 productosCat.setImagenProd(prodObtenidos.getString("imagenProd"));
            	 productosCat.setPrecioProd(prodObtenidos.getString("precioProd"));
            	 productosCat.setNombreFabricante(prodObtenidos.getString("fabricanteProd"));
            	 
            	 //Log.e(, msg)
            	 
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
 
            	 listaProductos.add(productosCat);
             }
    	}
    	catch(Exception err){
        	new AlertDialog.Builder(MuestraProductosPorCat.this)

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
    
    
public class ImageAdapter extends BaseAdapter {
		
		private Context myContext; 
		private int numCat;
		private ArrayList<ProductosCat> listaProd;
        private String url = "http://"+HOST+"/tienda/catalog/images/";
		
		private ProductosCat imagenes[];
		
		private LayoutInflater inflater; 
		
        public ImageAdapter(Context c, int numCat, ArrayList<ProductosCat> listaProd) { 
        	this.myContext = c;
        	this.numCat = numCat;
        	this.listaProd = listaProd;
        	imagenes = new ProductosCat[numCat]; 
        	for (int cont=0; cont<numCat; cont++){
               imagenes[cont] = listaProd.get(cont);
        	}
        	inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        } 

        /** Returns the amount of images we have defined. */ 
        public int getCount() { return this.imagenes.length; } 

        /* Use the array-Positions as unique IDs */ 
        public Object getItem(int position) { return listaProd.get(position); } 
        public long getItemId(int position) { return position; } 

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ProductosListCat productosListCat;
			if (convertView == null) 
			{ 
			convertView = inflater.inflate(R.layout.catprod, null); 
			productosListCat = new ProductosListCat();
			productosListCat.setTvNombre((TextView)convertView.findViewById(R.id.tvCatNombreProd));
			productosListCat.setIvImagen((ImageView)convertView.findViewById(R.id.ivCatImagenProd));
			productosListCat.setTvPrecio((TextView)convertView.findViewById(R.id.tvCatPrecioProd));
			//productosListCat.setTvFabricante((TextView)convertView.findViewById(R.id.tv))
			

			//holder.setImagen((ImageView)convertView.findViewById(R.id.imagenCat));
			//holder.setTexto((TextView)convertView.findViewById(R.id.textoCat));
			convertView.setTag(productosListCat); 
			} 
			else 
			{ 
				productosListCat = (ProductosListCat) convertView.getTag();
			//holder = (CatText) convertView.getTag(); 

			} 
			try{
			ImageView i = new ImageView(this.myContext);
			URL aURL = new URL(url+imagenes[position].getImagenProd()); 
            URLConnection conn = aURL.openConnection(); 
            conn.connect();
            InputStream is = conn.getInputStream();                  
            BufferedInputStream bis = new BufferedInputStream(is); 
            Bitmap bm = BitmapFactory.decodeStream(bis); 
            bis.close(); 
            is.close();  
            i.setImageBitmap(bm);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER); 
            i.setLayoutParams(new GridView.LayoutParams(50, 50));
            
            
            productosListCat.getIvImagen().setImageBitmap(bm);
            productosListCat.getTvNombre().setText(((ProductosCat) getItem(position)).getNombreProd());
            productosListCat.getTvPrecio().setText(((ProductosCat) getItem(position)).getPrecioProd());
            
            
			//holder.getImagen().setImageBitmap(bm);
			
			//holder.getTexto().setText(((Categorias) getItem(position)).getNombreCat());

			}
			catch(Exception err)
			{}
			return convertView; 

    } 
		
	
	}
}
