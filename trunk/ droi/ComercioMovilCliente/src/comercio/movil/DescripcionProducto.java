package comercio.movil;

import utils.OpBd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DescripcionProducto extends Activity {
	private OpBd opBd;
	private Cursor cu;
	private TextView nombreProd;
	private TextView modeloProd;
	private TextView descripProd;
	private ImageView imagenProd;
	private TextView precioProd;
	private TextView fabricanteProd;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcionproducto);
        //consultaUnProducto(ctx, id);
        opBd = new OpBd(this);
        
        nombreProd = (TextView)findViewById(R.id.txt_nombProdDesc);
        modeloProd = (TextView)findViewById(R.id.txt_modeloProdDesc);
        descripProd = (TextView)findViewById(R.id.txt_descripProdDesc);
        imagenProd = (ImageView)findViewById(R.id.imagenProdDesc);
        //precioProd = (TextView)findViewById(R.id.txt_precioProdDesc);
        //fabricanteProd = (TextView)findViewById(R.id.txt_fabricanteProdDesc);
        
        
        Button btnAgregarProd = (Button)findViewById(R.id.btnAgregarProdDesc);
        Button btnRegresarCat = (Button)findViewById(R.id.btnRegCatProdDesc);
        Button btnSalir = (Button)findViewById(R.id.btnSalirProdDesc);
        
        btnSalir.setOnClickListener(btnSalirPres);
        btnRegresarCat.setOnClickListener(btnRegresaCatPres);
        
        try{
           muestraDescripcionProd();
        }catch(Exception err){
			new AlertDialog.Builder(DescripcionProducto.this)

  	    	.setTitle("Alerta")

  	    	.setMessage("Error: "+err.toString())
  	    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {

  	         public void onClick(DialogInterface dialog, int whichButton) {

  	         setResult(RESULT_OK);
  	          }
  	         })
  	    	.show();  
		}

	}
	
	private void muestraDescripcionProd(){
		
		   Bundle bundle = getIntent().getExtras();
		   if(bundle!=null){
              String id = bundle.getString("ID");
		      cu = opBd.consultaUnProducto(DescripcionProducto.this, Integer.parseInt(id) );
		      if (cu != null && cu.getCount() != 0) {
 	             cu.moveToFirst();
 	            //p._id, p.nombreProd, p.descripcionProd, p.precioProd, p.imagenProd, p.modeloProd, f.nombreFabricante
 	             int columNombreProd = cu.getColumnIndexOrThrow("p.nombreProd");
 	             int columModeloProd = cu.getColumnIndexOrThrow("p.modeloProd");
 	             int columDescripProd = cu.getColumnIndexOrThrow("p.descripcionProd");
 	             int columImagenProd = cu.getColumnIndexOrThrow("p.imagenProd");
 	             int columPrecioProd = cu.getColumnIndexOrThrow("p.precioProd");
 	             int columFabricanteProd = cu.getColumnIndexOrThrow("f.nombreFabricante");

 	             String valColumNombre = cu.getString(columNombreProd);
 	             String valColumModelo = cu.getString(columModeloProd);
 	             String valColumDescrip = cu.getString(columDescripProd);
 	             String valColumImagen = cu.getString(columImagenProd);
 	             String valColumPrecio = cu.getString(columPrecioProd);
 	             String valColumFabricante = cu.getString(columFabricanteProd);
 	               
 	             nombreProd.setText(valColumNombre);
 	             modeloProd.setText(valColumModelo);
 	             descripProd.setText(valColumDescrip);
 	             int im = this.getResources().getIdentifier(valColumImagen,"drawable","comercio.movil");
 	             imagenProd.setImageResource(im);
 	             precioProd.setText(valColumPrecio);
 	             fabricanteProd.setText(valColumFabricante);

 	           }
		   }
	   }
	
	
	private OnClickListener btnSalirPres = new OnClickListener() {
		
		public void onClick(View v) {
		   //TODO: buscar como salir de toda la aplicación
		}
	};
	
	private OnClickListener btnRegresaCatPres = new OnClickListener() {
		
		public void onClick(View v) {
			finish();
			
		}
	};
	
}
