package comercio.movil;

import java.util.ArrayList;

import utils.OpBd;
import utils.ProductosCat;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MuestraProductosCat extends ListActivity{
	private OpBd opBd;
	protected static final int SUB_ACTIVTY_REQUEST_CODE = 1314;
	private Cursor cursorProd = null;
	private TextView nomb; 
	private ArrayList<ProductosCat> listaProductos = null;
	private IconListViewAdapter m_adapter;
	
	private ImageView imgPrueba;
	
	/*
	 *Botones 
	 */
	/*
	private Button btnSalir;
	private Button btnRegresarCat;
	*/
	


	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.muestraproductoscat);
        try{
           opBd = new OpBd(this);
        //cu = opBd.consultaProductosDeCategoria(this, idCategoria);
        
           Bundle bundle = getIntent().getExtras();
          // if(bundle!=null){
             String id = "1";//bundle.getString("2");
              String nombCat = "Teclado";//bundle.getString("Teclado");
              
              nomb = (TextView)findViewById(R.id.txtNombCategoria);
              nomb.setText(nombCat);
              
              cursorProd = opBd.consultaProductosDeCategoria(this, Integer.parseInt(id));
              /*
              startManagingCursor(cu);   
	          SimpleCursorAdapter notes =  
	              new SimpleCursorAdapter(MuestraProductosCat.this, R.layout.muestraproductoscatcol, cu, FROM, TO);  
	          setListAdapter(notes);
	          */
            listaProductos = new ArrayList<ProductosCat>();
  	        this.m_adapter = new IconListViewAdapter(this, R.layout.muestraproductoscatcol, listaProductos);
  	        setListAdapter(this.m_adapter);  
  	        
  	        inicializaListaProd();
           //}
        }
        catch(Exception err){
        	new AlertDialog.Builder(MuestraProductosCat.this)

	    	.setTitle("Alerta")

	    	.setMessage("Error: "+err.toString())
	    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {

	         public void onClick(DialogInterface dialog, int whichButton) {

	         setResult(RESULT_OK);
	          }
	         })
	    	.show(); 	
        }
        
        /*
        btnSalir = (Button) findViewById(R.id.btnSalirMuestraProdCat);
        btnRegresarCat = (Button) findViewById(R.id.btnRegresaMuestraProdCat);
        
        
        btnSalir.setOnClickListener(btnSalirPres);
        btnRegresarCat.setOnClickListener(btnRegresaCatPres);
        */
        imgPrueba = (ImageView) findViewById(R.id.imagenPrueba);
        imgPrueba.setOnClickListener(imgPruebaPres);
        
	}

	protected OnClickListener imgPruebaPres = new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
	};
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ProductosCat productos = (ProductosCat) l.getItemAtPosition(position);
		
		Intent intent = new Intent();
		intent.setClass(MuestraProductosCat.this, DescripcionProducto.class);
		intent.putExtra("ID", productos.getIdProd());
		startActivity(intent);
		/*
		selectedItem = position;
		int columnaIndice = cursorProd.getColumnIndexOrThrow("p._id");
		String valColumnaIndice = cursorProd.getString(columnaIndice);
		Intent intent = new Intent();
		intent.setClass(MuestraProductosCat.this, DescripcionProducto.class);
		intent.putExtra("ID",valColumnaIndice);
		startActivity(intent);
		*/
	}
	/*
	private OnClickListener btnSalirPres = new OnClickListener() {
		
		public void onClick(View v) {
			//TODO: buscar como cerrar toda la aplicación
		}
	};
	
	private OnClickListener btnRegresaCatPres = new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
	};
*/
	public class IconListViewAdapter extends ArrayAdapter<ProductosCat> {

        private ArrayList<ProductosCat> elementos;

        public IconListViewAdapter(Context context, int textViewResourceId, ArrayList<ProductosCat> items) {
                super(context, textViewResourceId, items);
                this.elementos = items;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.muestraproductoscatcol, null);
                }
                ProductosCat prodCat = elementos.get(position);
                if (prodCat != null) {
                	
                	//se llena la lista de elementos
                	
                        TextView txtNombFab = (TextView) v.findViewById(R.id.txt_nombFabricanteConsulProdCat);
                        TextView txtNombProd = (TextView) v.findViewById(R.id.txt_nombConsulProdCat);
                        TextView txtPrecio = (TextView) v.findViewById(R.id.txt_precioConsulProdCat);
                        ImageView imgProd = (ImageView) v.findViewById(R.id.imgchica);
                        
                         //imgProd.setImageResource(prodCat.getImagenProd());
                         txtNombFab.setText(prodCat.getNombreFabricante());
                         txtNombProd.setText(prodCat.getNombreProd());
                         txtPrecio.setText(prodCat.getPrecioProd());
                }
                return v;
        }
   }
	
private void inicializaListaProd(){
    	
    	try {
            listaProductos = new ArrayList<ProductosCat>();
            
            if (cursorProd != null) {
                /* Check if at least one Result was returned. */
                if (cursorProd.moveToFirst()) {

                     /* Loop through all Results */
                     do {

                          /* Retrieve the values of the Entry
                           * the Cursor is pointing to. */
                    	  int idProd = cursorProd.getColumnIndexOrThrow("p._id");
                    	  String valIdCat = cursorProd.getString(idProd);
                    	 
                    	  int fabricanteProd = cursorProd.getColumnIndexOrThrow("f.nombreFabricante");
                          String valFabricanteProd = cursorProd.getString(fabricanteProd);
                          
                          int nombreProd = cursorProd.getColumnIndexOrThrow("p.nombreProd");
                          String valNombreProd = cursorProd.getString(nombreProd);
                          
                          int precioProd = cursorProd.getColumnIndexOrThrow("p.precioProd");
                          String valprecioProd = cursorProd.getString(precioProd);
                          
                          int imagenProd = cursorProd.getColumnIndexOrThrow("p.imagenProd");
                          String valImagenProd = cursorProd.getString(imagenProd);
                          
                          
                          /* We can also receive the Name
                           * of a Column by its Index.
                           * Makes no sense, as we already
                           * know the Name, but just to shwo we can Wink */
                          ProductosCat productosCat = new ProductosCat();
                          
                          productosCat.setIdProd(valIdCat);
                          productosCat.setNombreFabricante(valFabricanteProd);
                          productosCat.setNombreProd(valNombreProd);
                          productosCat.setPrecioProd(valprecioProd);
                          //productosCat.setImagenProd(this.getResources().getIdentifier(valImagenProd,"drawable","comercio.movil"));
                          listaProductos.add(productosCat);
                          
                          /* Add current Entry to results. */
                          //results.add("" + i + ": " + firstName+ " (" + ageColumName + ": " + age + ")");
                     } while (cursorProd.moveToNext());
                }
           }

            
            
            
            Log.i("Lista productos ", ""+ listaProductos.size());
          } catch (Exception e) {
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          
          if(listaProductos != null && listaProductos.size() > 0){
              for(int i=0;i<listaProductos.size();i++)
              m_adapter.add(listaProductos.get(i));
          }

          m_adapter.notifyDataSetChanged();       	
    	
    }  
	
}
