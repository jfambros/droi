package comercio.movil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import utils.DatosCesta;
import utils.ListaCesta;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public class Cesta extends Activity {
    private TableLayout tlCesta;
    private TextView imaProd = null;
    private ImageView imagenProd = null;
    private TextView nombreProd = null;
    private EditText cantidadProd = null;
    private TextView precioProd = null;
    private ImageView ivInicio = null;
    private static final String HOST = "10.0.2.2";
    private String ruta = "http://"+HOST+"/tienda/catalog/images/";
    private Bundle bundle = null;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cesta);
        
        //objetos obtenidos del xml
        ivInicio = (ImageView)findViewById(R.id.ivInicioCesta);
        
        tlCesta = (TableLayout)findViewById(R.id.tlCesta);
        
        //click
        ivInicio.setOnClickListener(ivInicioPres);
        TableRow row=null;

        
        
        
        
        if (!ListaCesta.arregloCesta.isEmpty()){
        	bundle = getIntent().getExtras();
        	if (bundle != null){
	            String id = bundle.getString("idProducto");
		        Log.i("bundle", id+" "+bundle.getString("cantidad"));
		        DatosCesta datos =   ListaCesta.arregloCesta.get(id);
		        Log.i("id",datos.getIdProducto());
		        /*
		        DatosCesta sumaUno =   ListaCesta.arregloCesta.get(id);
		        int cantidad = sumaUno.getCantidadProd();
		        sumaUno.setCantidadProd(cantidad+1);
		        ListaCesta.arregloCesta.put(id, sumaUno);
		        */
        	}
	        try{
	        	int cont=0;

	        	Set set = ListaCesta.arregloCesta.entrySet();

	            Iterator i = set.iterator();

	            while(i.hasNext()){
		            Map.Entry<String,DatosCesta> me = (Map.Entry<String, DatosCesta>)i.next();
		            Log.i("Datos map: ",me.getKey() + " : " + ((DatosCesta) me.getValue()).getNombreProducto());
	            
	        	
	        	
		        	row = new TableRow(this);
		        	row.setId(100+cont);
		        	row.setLayoutParams(new LayoutParams(
		        	LayoutParams.FILL_PARENT,
		        	LayoutParams.WRAP_CONTENT));
		        	
		        	
		        	String rutaUrl = ruta + ((DatosCesta) me.getValue()).getImagenProducto();
		        	URL aURL = new URL(rutaUrl);
		        	HttpURLConnection conn= (HttpURLConnection)aURL.openConnection();
		            conn.setDoInput(true);
		            conn.connect();
		            int length = conn.getContentLength();
		            int[] bitmapData =new int[length];
		            byte[] bitmapData2 =new byte[length];
		            InputStream is = conn.getInputStream();
		            Bitmap bmImg = BitmapFactory.decodeStream(is);
		            Bitmap bMapScala = Bitmap.createScaledBitmap(bmImg, 80, 80, true);
		            
		            imagenProd = new ImageView(this);
		            imagenProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            imagenProd.setImageBitmap(bMapScala);
		            
		        	
		        	
		            imaProd = new TextView(this);
		            imaProd.setText(((DatosCesta) me.getValue()).getImagenProducto());
		            imaProd.setTextColor(Color.BLACK);
		            imaProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            imaProd.setGravity(Gravity.CENTER_VERTICAL);
		            imaProd.setWidth(80);

		
		            nombreProd = new TextView(this);
		            nombreProd.setText(((DatosCesta) me.getValue()).getNombreProducto());
		            nombreProd.setTextColor(Color.BLACK);
		            nombreProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            nombreProd.setGravity(Gravity.CENTER_VERTICAL);
		            nombreProd.setWidth(50);

		            cantidadProd = new EditText(this);
		            
		            cantidadProd.setText( Integer.toString(((DatosCesta) me.getValue()).getCantidadProd()));
		            cantidadProd.setTextColor(Color.BLACK);
		            cantidadProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            cantidadProd.setGravity(Gravity.CENTER_VERTICAL);
		            cantidadProd.setWidth(30);
		            cantidadProd.setHeight(5);
		            cantidadProd.setLines(1);
		            

		            precioProd = new TextView(this);
		            precioProd.setText( Double.toString(((DatosCesta) me.getValue()).getPrecioProd()));
		            precioProd.setTextColor(Color.BLACK);
		            precioProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            precioProd.setGravity(Gravity.CENTER_VERTICAL);
		            precioProd.setWidth(20);
		            
		            
		        	row.addView(imagenProd);
		        	row.addView(nombreProd);
		        	row.addView(cantidadProd);
		        	row.addView(precioProd);
		        	
		        	row.setBackgroundColor(Color.WHITE);
		        	tlCesta.addView(row);
		        	
		        	cont++;
	        	}
	        	
	        	
	        	
	        	
	        }
	        catch(Exception err){
	           Log.e("error", err.toString());
	        }
        }
        else{
        	
        }
	}
	
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           Intent intent = new Intent();
           intent.setClass(Cesta.this, Principal.class);
           startActivity(intent);		
		}
	};
	

}
