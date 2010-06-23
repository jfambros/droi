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

import android.app.AlertDialog;
import android.app.ListActivity;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductosListCatLV extends ListActivity{

	private ArrayList<ProductosCat> listaProductos = null;
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
    
    private SoapObject result;
    private IconListViewAdapter adaptador;
    
    //botones
    private ImageView ivCesta = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
		try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.productoslistcatlv); 
	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        
	        Bundle bundle = getIntent().getExtras();
	        /*
	        TextView nombreCat = (TextView) findViewById(R.id.tvCatNombCategoria);
	        nombreCat.setText(bundle.getString("nombreCat"));
	        */ 
	        request.addProperty ("idCategoria", bundle.getString("idCategoria"));
	        envelope.setOutputSoapObject(request);
	        inicializaProd();
	        this.adaptador = new IconListViewAdapter(this, R.layout.productoslistcatlvrow, listaProductos);
	        setListAdapter(this.adaptador);
	        
	        ivCesta = (ImageView)findViewById(R.id.ivCestaListCatLV);
	        ivCesta.setOnClickListener(ivCestaPres);
	        

		}
		catch (Exception e) {
	    	new AlertDialog.Builder(ProductosListCatLV.this)

	    	.setTitle("error en valores()")

	    	.setMessage(e.toString())

	    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	    	public void onClick(DialogInterface dialog, int whichButton) {

	    	setResult(RESULT_OK);
	    	  }
	    	})
	    	.show();
	    }
        
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        ProductosCat producto = (ProductosCat) l.getItemAtPosition(position);        
        //Toast.makeText(this, producto.getNombreProd(), Toast.LENGTH_LONG).show();
        if (producto.getIdProd() != null){
			Intent i = new Intent(); 
			i.putExtra("idProducto",producto.getIdProd());
			i.setClass(ProductosListCatLV.this, DescripcionProdSelec.class);
			startActivity(i);     
        }
    }
	
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
     	         Log.i(Key,bundleResult.getString(Key)); 
     	    }
         }
    } catch (Exception e) {
    	new AlertDialog.Builder(ProductosListCatLV.this)

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

	
	private void inicializaProd(){
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
	      	 listaProductos.add(productosCat);
	       }
       }
   	catch(Exception err){
    	new AlertDialog.Builder(ProductosListCatLV.this)

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
	
	 public class IconListViewAdapter extends ArrayAdapter<ProductosCat> {

	        private ArrayList<ProductosCat> items;
	        private String url = "http://"+HOST+"/tienda/catalog/images/";

	        public IconListViewAdapter(Context context, int textViewResourceId, ArrayList<ProductosCat> items) {
	                super(context, textViewResourceId, items);
	                this.items = items;
	        }
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	                View v = convertView;
	                if (v == null) {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.productoslistcatlvrow, null);
	                }
	                ProductosCat productosCat = items.get(position);
	                if (productosCat != null) {
	                	
	                	//poblamos la lista de elementos
	                	
	                        TextView nombreProd = (TextView) v.findViewById(R.id.tvNombreProdLv);
	                        ImageView imagenProd = (ImageView) v.findViewById(R.id.ivImagenProdLv);
	                        TextView precioProd = (TextView) v.findViewById(R.id.tvPrecioProdLv);
	                        //TextView fabricanteProd = (TextView) v.findViewById(R.id.tv);
	                        
	            			try{
	            				ImageView i = new ImageView(ProductosListCatLV.this);
	            				URL aURL = new URL(url+productosCat.getImagenProd()); 
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
	            	            
		            			if (imagenProd != null){
			            			   imagenProd.setImageBitmap(bm);
			            		}

	            			}
	            			catch(Exception err)
	            			{}
	            			if (nombreProd != null){
	            			   nombreProd.setText(productosCat.getNombreProd());
	            			}
	            			if (precioProd != null){
		            			   precioProd.setText(productosCat.getPrecioProd());
		            			}
	            			
	                        /*
	                        if (im!= null) {
	                        	im.setImageResource(o.getLocalImage());
	                        }                        
	                        if (tt != null) {             
	                            tt.setText(o.getLocalName());                             
	                        } 
	                        */   	                    	                        
	                }
	                return v;
	        }
	}
	 
	 private OnClickListener ivCestaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent i = new Intent(); 
			i.setClass(ProductosListCatLV.this, Cesta.class);
			startActivity(i);	
			finish();
		}
	};
	
	
}
