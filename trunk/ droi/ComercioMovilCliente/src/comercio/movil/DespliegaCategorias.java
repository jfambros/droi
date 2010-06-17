package comercio.movil;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DespliegaCategorias extends Activity {
    private GridView gv;
    private ImageView imgInicio;
    private ArrayList<Categorias> categorias = null;
    private OpBd opbd;
    private Cursor cursor;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.despliegacategorias);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gv = gridview;
		gridview.setAdapter(new ImageAdapter(this));
		gv.setOnItemClickListener(clickList);
		
        imgInicio = (ImageView)findViewById(R.id.inicioDespliegaCat);
        imgInicio.setOnClickListener(imgInicioPres);
        
        cursor = opbd.consultaCategorias();
    
        inicializaCat();
	}
	
	private void inicializaCat(){
	    categorias = new ArrayList<Categorias>();
	    
	    if (cursor.moveToFirst()) {
	    	do{
	           int idCat = cursor.getColumnIndexOrThrow("_id");
  	           String valIdCat = cursor.getString(idCat);
  	 
		        int nombreCat = cursor.getColumnIndexOrThrow("nombreCat");
		        String valNombreCat = cursor.getString(nombreCat);
		        
		        int imagenCat = cursor.getColumnIndexOrThrow("imagenCat");
		        String valImagenCat = cursor.getString(imagenCat);
		        
		        Categorias cat = new Categorias();
		        cat.setIdCat(valIdCat);
		        cat.setNombreCat(valNombreCat);
		       // cat.setImagenCat(this.getResources().getIdentifier(valImagenCat,"drawable","comercio.movil"));
		        categorias.add(cat);
	    	} while (cursor.moveToNext());
	    }
	}
	
	
	private OnClickListener imgInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
	};
	
	private OnItemClickListener clickList = new OnItemClickListener() {

		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			Toast.makeText(DespliegaCategorias.this, "Cat: " + position,
					Toast.LENGTH_SHORT).show();

			
		}
	};
	
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
        
		private Integer[] mThumbIds = { 
				R.drawable.categoria_almacenamiento, R.drawable.categoria_computadoras,
				R.drawable.categoria_cpu, R.drawable.categoria_memoria,
				R.drawable.categoria_monitor, R.drawable.categoria_mouse,
				R.drawable.categoria_teclado, R.drawable.categoria_otros,
				};

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) { // if it's not recycled, initialize some
				// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(105, 70));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);

				
		
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(mThumbIds[position]);
		
			
			return imageView;
		}

		// references to our images
	
	}
	
	
}
