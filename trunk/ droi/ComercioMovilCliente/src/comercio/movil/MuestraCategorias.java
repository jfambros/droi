package comercio.movil;

import java.io.InputStream;
import java.util.ArrayList;

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

import utils.Categorias;
import utils.DatosCategorias;
import utils.ParcheInputStream;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MuestraCategorias extends Activity {
	private static final String HOST = Valores.HOST; //esto es para el equipo local
	private static final String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerCategorias";
    private static final String METHOD_NAME = "obtenerCategorias";
    private static final String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
    //private static final String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
    private static final String URL = "http://"+HOST+"/servicios/servicios.php";
    private SoapSerializationEnvelope envelope;
    private HttpTransportSE httpt;
    private Bundle bundleResult=new Bundle();
    private String resultData;
    
    public ArrayList<Categorias> listaCategorias;
    
    private SoapObject result;
    
    //botones
    private ImageView ivCesta = null;
    private ImageView ivRegresar = null;
    
    
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
		
		ivCesta = (ImageView) findViewById(R.id.ivCestaMuestraCat);
		ivRegresar = (ImageView)findViewById(R.id.ivRegresarMuestraCat);
		
		ivCesta.setOnClickListener(ivCestaPres);
		ivRegresar.setOnClickListener(ivRegresarPres);
		
		
		
     }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
    
	private OnItemClickListener clickList = new OnItemClickListener() {

		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			try{
				String val = ((Categorias) parent.getAdapter().getItem(position)).getNombreCat();
				String idCat =((Categorias) parent.getAdapter().getItem(position)).getIdCat();
				//Toast.makeText(MuestraCategorias.this, "Id: " + idCat +" categoria "+  val,Toast.LENGTH_SHORT).show();
		      //Toast.makeText(Servicio3.this, "Dato: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
				Intent i = new Intent(); 
				i.putExtra("idCategoria", idCat);
				i.setClass(MuestraCategorias.this, ProductosListCatLV.class);
				startActivity(i);
				finish();
		   
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
    
    
    public void inicializaCat(){
    	listaCategorias = new ArrayList<Categorias>();
    	try{
            httpt.call(SOAP_ACTION, envelope);
             result = (SoapObject)envelope.bodyIn;
             
             SoapObject result2 =  (SoapObject) envelope.getResponse();
             Log.i("Datos:",result2.toString());
             
             for(int cont=0; cont< result2.getPropertyCount(); cont ++){
            	 SoapObject resultados = (SoapObject) result2.getProperty(cont);
            	 //primitivas
            	 SoapPrimitive id = (SoapPrimitive) resultados.getProperty("idCat");
            	 SoapPrimitive nombre = (SoapPrimitive) resultados.getProperty("nombreCat");
            	 SoapPrimitive imagen = (SoapPrimitive) resultados.getProperty("imagenCat");
            	 
                 Categorias oCategorias = new Categorias();
                 oCategorias.setIdCat(id.toString());
                 oCategorias.setNombreCat(nombre.toString());
                 oCategorias.setImagenCat(imagen.toString());
 
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
    
	private OnClickListener ivCestaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent i = new Intent(); 
			i.setClass(MuestraCategorias.this, Cesta.class);
			startActivity(i);	
			finish();
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent i = new Intent(); 
			i.setClass(MuestraCategorias.this, Principal.class);
			startActivity(i);	
			finish();			
		}
	};
	
    
public class ImageAdapter extends BaseAdapter {
		
		private Context myContext; 
		private int numCat;
		private ArrayList<Categorias> listaCat;
        //private String url = "http://"+Valores.HOST+"/tienda/catalog/images/";
		private String url = "http://"+Valores.HOST+"/catalog/images/";
		
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
			
			DatosCategorias holder;
			if (convertView == null) 
			{ 
			convertView = inflater.inflate(R.layout.cattex, null); 
			holder = new DatosCategorias(); 
			holder.setImagen((ImageView)convertView.findViewById(R.id.imagenCat));
			holder.setTexto((TextView)convertView.findViewById(R.id.textoCat));
			convertView.setTag(holder); 
			} 
			else 
			{ 
			holder = (DatosCategorias) convertView.getTag(); 

			} 
			try{
				
				
			ImageView i = new ImageView(this.myContext);
			
			
			

			//cambio
			HttpGet httpRequest = null;
			try {
				httpRequest = new HttpGet(url+imagenes[position].getImagenCat());
			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
	        HttpEntity entity = response.getEntity();
	        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity); 
	        InputStream instream = bufHttpEntity.getContent();
	        
	        BitmapFactory.Options bfOpt = new BitmapFactory.Options();

	        bfOpt.inScaled = true;
	        bfOpt.inSampleSize = 1;
	        bfOpt.inPurgeable = true;
	        
	        //Bitmap bm = BitmapFactory.decodeStream(instream,null,bfOpt);
	        Bitmap bm = BitmapFactory.decodeStream(new ParcheInputStream(instream),null,bfOpt);
	        if (bm == null){
	        	Log.e("error", "Error en BitMap");
	        	Resources res = getResources();
	        	Drawable drawable = res.getDrawable(R.drawable.categorias64x64);
	        	i.setImageDrawable(drawable);
	        	Bitmap bmp=((BitmapDrawable)drawable).getBitmap();
	        	bm= Bitmap.createScaledBitmap(bmp, 64,64, true);
	        }
	        else{
	          i.setImageBitmap(bm);	
	        }
	        instream.close();
			//fin
	        //original
	        /*
	         * URL aURL = new URL(url+imagenes[position].getImagenCat());
            URLConnection conn = aURL.openConnection(); 
            conn.connect();
            InputStream is = conn.getInputStream();                  
            BufferedInputStream bis = new BufferedInputStream(is); 
            Bitmap bm = BitmapFactory.decodeStream(bis); 
            
            bis.close(); 
            is.close();  
	         */
            
            //i.setImageBitmap(bm);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER); 
            i.setLayoutParams(new GridView.LayoutParams(80, 80)); 
            holder.getImagen().setImageBitmap(bm);
			
			holder.getTexto().setText(((Categorias) getItem(position)).getNombreCat());

			}
			catch(Exception err){
				Log.e("Error en imagen", err.toString());
			}
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
	
    
    
 }