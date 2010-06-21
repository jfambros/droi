package comercio.movil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import utils.DatosCesta;
import utils.ListaCesta;
import android.app.Activity;
import android.content.Intent;
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
    private TextView imagenProd = null;
    private TextView nombreProd = null;
    private EditText cantidadProd = null;
    private TextView precioProd = null;
    private ImageView ivInicio = null;
	
	
	
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
        	Bundle bundle = getIntent().getExtras();
        	if (bundle != null){
	            String id = bundle.getString("idProducto");
		        Log.i("bundle", id+" "+bundle.getString("cantidad"));
		        DatosCesta datos =   ListaCesta.arregloCesta.get(id);
		        Log.i("id",datos.getIdProducto());
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
		        	
		        	
		            imagenProd = new TextView(this);
		            imagenProd.setText(((DatosCesta) me.getValue()).getImagenProducto());
		            imagenProd.setTextColor(Color.BLACK);
		            imagenProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            imagenProd.setGravity(Gravity.CENTER_VERTICAL);
		            imagenProd.setWidth(80);

		
		            nombreProd = new TextView(this);
		            nombreProd.setText(((DatosCesta) me.getValue()).getNombreProducto());
		            nombreProd.setTextColor(Color.BLACK);
		            nombreProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            nombreProd.setGravity(Gravity.CENTER_VERTICAL);
		            nombreProd.setWidth(50);

		            cantidadProd = new EditText(this);
		            cantidadProd.setText(((DatosCesta) me.getValue()).getCantidadProd());
		            cantidadProd.setTextColor(Color.BLACK);
		            cantidadProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            cantidadProd.setGravity(Gravity.CENTER_VERTICAL);
		            cantidadProd.setWidth(30);
		            cantidadProd.setHeight(5);
		            cantidadProd.setLines(1);
		            

		            precioProd = new TextView(this);
		            precioProd.setText(((DatosCesta) me.getValue()).getPrecioProd());
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
