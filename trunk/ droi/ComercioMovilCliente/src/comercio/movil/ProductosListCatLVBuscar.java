package comercio.movil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProductosListCatLVBuscar extends ListActivity{

	private ArrayList<ProductosCat> listaProductos = null;
	private static final String HOST = "10.0.2.2"; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#buscaProductos";
    private static final String METHOD_NAME = "buscaProductos";
    private static final String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
    private static final String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
    private SoapSerializationEnvelope envelope; 
    private HttpTransportSE httpt;
    private Bundle bundleResult=new Bundle();
    Bundle bundle = null;
    private String resultData;
    
    private SoapObject result;
    private IconListViewAdapter adaptador;
    
    //botones
    private ImageView ivCesta = null;
    private ImageView ivRegresa = null;
    private ImageView ivInicio = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
		try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.productoslistcatlvbuscar);
	        
	        //acciones
	        ivRegresa = (ImageView)findViewById(R.id.ivRegresarProListCatLVBuscar);
	        ivRegresa.setOnClickListener(ivRegresaPres);
	        ivInicio = (ImageView) findViewById(R.id.ivInicioProdListCatLVBuscar);
	        ivInicio.setOnClickListener(ivInicioPres);
	        
	        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	
	        httpt = new HttpTransportSE(URL);
	        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelope.dotNet = false;
	        
	        bundle = getIntent().getExtras();
	        /*
	        TextView nombreCat = (TextView) findViewById(R.id.tvCatNombCategoria);
	        nombreCat.setText(bundle.getString("nombreCat"));
	        */ 
	        request.addProperty ("palabra", bundle.getString("palabra"));
	        envelope.setOutputSoapObject(request);
	        inicializaProd();
	        this.adaptador = new IconListViewAdapter(this, R.layout.productoslistcatlvrowbuscar, listaProductos);
	        setListAdapter(this.adaptador);
	        
	        ivCesta = (ImageView)findViewById(R.id.ivCestaListCatLVBuscar);
	        ivCesta.setOnClickListener(ivCestaPres);
	        

		}
		catch (Exception e) {
	    	new AlertDialog.Builder(ProductosListCatLVBuscar.this)

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
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        ProductosCat producto = (ProductosCat) l.getItemAtPosition(position);        
        //Toast.makeText(this, producto.getNombreProd(), Toast.LENGTH_LONG).show();
        if (producto.getIdProd() != null){
			Intent i = new Intent(); 
			i.putExtra("idProducto",producto.getIdProd());

			if (bundle.getString("palabra")!= null){
				i.putExtra("palabra", bundle.getString("palabra"));
				i.putExtra("busca", 1);
			}
			i.setClass(ProductosListCatLVBuscar.this, DescripcionProdSelec.class);
			startActivity(i);     
        }
    }
	/*
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
*/
	
	private void inicializaProd(){
	   listaProductos = new ArrayList<ProductosCat>();
       try{
	       httpt.call(SOAP_ACTION, envelope);
	       result = (SoapObject)envelope.bodyIn;
	       
	       SoapObject result2 =  (SoapObject) envelope.getResponse();
	      
		   SoapObject prod = (SoapObject) result2.getProperty("productos");
		   if (prod.getPropertyCount()!=0){
		       for(int cont=0; cont< result2.getPropertyCount(); cont ++){
		    	 SoapObject resultados = (SoapObject) result2.getProperty(cont);
		    	 SoapPrimitive id = (SoapPrimitive) resultados.getProperty("idProd");
	          	 SoapPrimitive nombre = (SoapPrimitive) resultados.getProperty("nombreProd");
	          	 SoapPrimitive imagen = (SoapPrimitive) resultados.getProperty("imagenProd");
	          	 SoapPrimitive precio = (SoapPrimitive) resultados.getProperty("precioProd");
	          	 SoapPrimitive fabricante = (SoapPrimitive) resultados.getProperty("fabricanteProd");
		    	   
		      	 
		      	 ProductosCat productosCat = new ProductosCat();
		      	 productosCat.setIdProd(id.toString());
		      	 productosCat.setNombreProd(nombre.toString());
		      	 productosCat.setImagenProd(imagen.toString());
		      	 productosCat.setPrecioProd(precio.toString());
		      	 productosCat.setNombreFabricante(fabricante.toString());
		      	 listaProductos.add(productosCat);
		       }
		   }
		   else{
		      	 ProductosCat productosCat = new ProductosCat();
		      	 productosCat.setIdProd(null);
		      	 productosCat.setNombreProd(null);
		      	 productosCat.setImagenProd(null);
		      	 productosCat.setPrecioProd(null);
		      	 productosCat.setNombreFabricante(null);
		      	 listaProductos.add(productosCat);			   
		   }
			   
       }
   	catch(Exception err){
    	new AlertDialog.Builder(ProductosListCatLVBuscar.this)

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
	                    v = vi.inflate(R.layout.productoslistcatlvrowbuscar, null);
	                }
	                ProductosCat productosCat = items.get(position);
	                if (productosCat != null) {
	                	
	                	//poblamos la lista de elementos
	                	
	                        TextView nombreProd = (TextView) v.findViewById(R.id.tvNombreProdLvBuscar);
	                        ImageView imagenProd = (ImageView) v.findViewById(R.id.ivImagenProdLvBuscar);
	                        TextView precioProd = (TextView) v.findViewById(R.id.tvPrecioProdLvBuscar);
	                        //TextView fabricanteProd = (TextView) v.findViewById(R.id.tv);
	                        
	            			try{
	            				ImageView i = new ImageView(ProductosListCatLVBuscar.this);
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
			i.setClass(ProductosListCatLVBuscar.this, Cesta.class);
			startActivity(i);	
			finish();
		}
	};

	private OnClickListener ivRegresaPres = new OnClickListener() {
		
		public void onClick(View v) {
           Intent intent = new Intent();
           intent.putExtra("band", "buscar");
           intent.setClass(ProductosListCatLVBuscar.this, Principal.class);
           startActivity(intent);
           finish();		   
		}
	};
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           Intent intent = new Intent();
           intent.setClass(ProductosListCatLVBuscar.this, Principal.class);
           startActivity(intent);
           finish();		
		}
	};
	
}
