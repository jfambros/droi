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

public class MuestraCategorias extends Activity {
	private static final String HOST = "10.0.2.2"; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:categorias:categoriasPortType#obtenerCategorias";
    private static final String METHOD_NAME = "obtenerCategorias";
    private static final String NAMESPACE = "http://www.your-company.com/categorias.wsdl";
    private static final String URL = "http://"+HOST+"/tienda/servicios/consultacat.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private JSONObject JSONObj;
    private Bundle bundleResult=new Bundle();
    private String resultData;
    
    public ArrayList<Categorias> listaCategorias;
    
    private SoapObject result;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muestracategorias);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        httpt = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
        envelope.dotNet = false;
       // envelope.bodyOut = request; 
        envelope.setOutputSoapObject(request);
        inicializaCat();
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        ((GridView) findViewById(R.id.gridview)).setOnItemClickListener(clickList);
		
		try{
		gridview.setAdapter(new ImageAdapter(this, listaCategorias.size(), listaCategorias));
		}
		catch (Exception e) {
	    	new AlertDialog.Builder(MuestraCategorias.this)

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

		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			try{
				String val = ((Categorias) parent.getAdapter().getItem(position)).getNombreCat();
				String idCat =((Categorias) parent.getAdapter().getItem(position)).getIdCat();
				Toast.makeText(MuestraCategorias.this, "Id: " + idCat +" categoria "+  val,Toast.LENGTH_SHORT).show();
		      //Toast.makeText(Servicio3.this, "Dato: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(); 
				i.putExtra("idCategoria", idCat);
				i.putExtra("nombreCat", val);
				//llamar al gridView
				//i.setClass(MuestraCategorias.this, MuestraProductosPorCat.class);
				//llamar a ListActivity
				i.setClass(MuestraCategorias.this, ProductosListCatLV.class);
				startActivity(i);
		   
			}
			catch (Exception e) {
		    	new AlertDialog.Builder(MuestraCategorias.this)

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
    	new AlertDialog.Builder(MuestraCategorias.this)

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
    	listaCategorias = new ArrayList<Categorias>();
    	try{
            httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject result2 =  (SoapObject) envelope.getResponse();
             
             for(int cont=0/*20*/; cont< result2.getPropertyCount(); cont ++){
            	 resultData = result2.getProperty(cont).toString();
            	 String resul2 = resultData.substring(7);
            	 Bundle catObtenidas =  valores(resul2);
            	 
                 Categorias oCategorias = new Categorias();
                 oCategorias.setIdCat(catObtenidas.getString("idCat"));
                 oCategorias.setNombreCat(catObtenidas.getString("nombreCat"));
                 oCategorias.setImagenCat(catObtenidas.getString("imagenCat"));
 
            	 listaCategorias.add(oCategorias);
             }
    	}
    	catch(Exception err){
        	new AlertDialog.Builder(MuestraCategorias.this)

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
		private ArrayList<Categorias> listaCat;
        private String url = "http://10.0.2.2/tienda/catalog/images/";
		
		private Categorias imagenes[];
		
		private LayoutInflater inflater; 
		
        public ImageAdapter(Context c, int numCat, ArrayList<Categorias> listaCat) { 
        	this.myContext = c;
        	this.numCat = numCat;
        	this.listaCat = listaCat;
        	imagenes = new Categorias[numCat]; 
        	for (int cont=0; cont<numCat; cont++){
               imagenes[cont] = listaCat.get(cont);
        	}
        	inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        } 

        /** Returns the amount of images we have defined. */ 
        public int getCount() { return this.imagenes.length; } 

        /* Use the array-Positions as unique IDs */ 
        public Object getItem(int position) { return listaCat.get(position); } 
        public long getItemId(int position) { return position; } 

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			
			CatText holder;
			if (convertView == null) 
			{ 
			convertView = inflater.inflate(R.layout.cattex, null); 
			holder = new CatText(); 
			holder.setImagen((ImageView)convertView.findViewById(R.id.imagenCat));
			holder.setTexto((TextView)convertView.findViewById(R.id.textoCat));
			convertView.setTag(holder); 
			} 
			else 
			{ 
			holder = (CatText) convertView.getTag(); 

			} 
			try{
			ImageView i = new ImageView(this.myContext);
			URL aURL = new URL(url+imagenes[position].getImagenCat()); 
            URLConnection conn = aURL.openConnection(); 
            conn.connect();
            InputStream is = conn.getInputStream();                  
            BufferedInputStream bis = new BufferedInputStream(is); 
            Bitmap bm = BitmapFactory.decodeStream(bis); 
            bis.close(); 
            is.close();  
            i.setImageBitmap(bm);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER); 
            i.setLayoutParams(new GridView.LayoutParams(48, 48)); 
            
			holder.getImagen().setImageBitmap(bm);
			
			holder.getTexto().setText(((Categorias) getItem(position)).getNombreCat());

			}
			catch(Exception err)
			{}
			return convertView; 

			
			/*
			ImageView i = new ImageView(this.myContext);
            try {  
                URL aURL = new URL(url+imagenes[position].getImagenCat()); 
                URLConnection conn = aURL.openConnection(); 
                conn.connect();
                InputStream is = conn.getInputStream();                  
                BufferedInputStream bis = new BufferedInputStream(is); 
                Bitmap bm = BitmapFactory.decodeStream(bis); 
                bis.close(); 
                is.close();  
                i.setImageBitmap(bm);             
           } catch (Exception e) { 
                Log.e("DEBUGTAG", "Remtoe Image Exception", e);
                new AlertDialog.Builder(Servicio3.this)
            	.setTitle("error getView()")
            	.setMessage(e.toString())
            	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {
            	setResult(RESULT_OK);
            	  }
            	})
            	.show();   		

           } 
        i.setScaleType(ImageView.ScaleType.FIT_CENTER); 
        i.setLayoutParams(new GridView.LayoutParams(105, 70)); 
        return i;
        */
    } 
		
	
	}
	
  class CatText{
	  private ImageView imagen;
	  private TextView texto;
	public ImageView getImagen() {
		return imagen;
	}
	public void setImagen(ImageView imagen) {
		this.imagen = imagen;
	}
	public TextView getTexto() {
		return texto;
	}
	public void setTexto(TextView texto) {
		this.texto = texto;
	}
		
	
  }
    
    
 }