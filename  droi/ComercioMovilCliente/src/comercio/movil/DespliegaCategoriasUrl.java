package comercio.movil;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DespliegaCategoriasUrl extends Activity {
    private GridView gv;
    private ImageView imgInicio;
    private ArrayList<Categorias> categorias = null;
    private OpBd opbd;
    private Cursor cursor;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.despliegacategoriasurl);
		
        ((GridView) findViewById(R.id.gridviewUrl)) 
        .setAdapter(new ImageAdapter(this));
        
        ((GridView) findViewById(R.id.gridviewUrl)) 
        .setOnItemClickListener(clickList);
		//gv.setOnItemClickListener(clickList);
		
        imgInicio = (ImageView)findViewById(R.id.inicioDespliegaCat);
        imgInicio.setOnClickListener(imgInicioPres);
        
        
        
       // cursor = opbd.consultaCategorias();
    
        //inicializaCat();
	}
	
	private OnItemClickListener clickList = new OnItemClickListener() {

		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			Toast.makeText(DespliegaCategoriasUrl.this, "Cat: " + position,
					Toast.LENGTH_SHORT).show();
		}
	};  
	
	
	private OnClickListener imgInicioPres = new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
	};
	
	
	private void inicializaCat(){
	    categorias = new ArrayList<Categorias>();
	    		        
		        Categorias cat = new Categorias();
		        //cat.setIdCat(valIdCat);
		        //cat.setNombreCat(valNombreCat);
		        //cat.setImagenCat(this.getResources().getIdentifier(valImagenCat,"drawable","comercio.movil"));
		        categorias.add(cat);
	}
		
		public class ImageAdapter extends BaseAdapter {
		
		private Context myContext; 

        /** URL-Strings to some remote images. */ 
        private String[] myRemoteImages = { 
               "http://www.terra.es/personal3/javaja/android/img/logoAndroid_2_camiseta.png", 
               "http://www.android.com/images/developers.gif", 
               "http://www.android.com/images/opensourceproject.gif" 
        }; 

        public ImageAdapter(Context c) { this.myContext = c; } 

        /** Returns the amount of images we have defined. */ 
        public int getCount() { return this.myRemoteImages.length; } 

        /* Use the array-Positions as unique IDs */ 
        public Object getItem(int position) { return position; } 
        public long getItemId(int position) { return position; } 

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(this.myContext);
			
            try { 
                /* Open a new URL and get the InputStream to load data from it. */ 
                URL aURL = new URL(myRemoteImages[position]); 
                URLConnection conn = aURL.openConnection(); 
                conn.connect(); 
                InputStream is = conn.getInputStream(); 
                /* Buffered is always good for a performance plus. */ 
                BufferedInputStream bis = new BufferedInputStream(is); 
                /* Decode url-data to a bitmap. */ 
                Bitmap bm = BitmapFactory.decodeStream(bis); 
                bis.close(); 
                is.close(); 
                /* Apply the Bitmap to the ImageView that will be returned. */ 
                i.setImageBitmap(bm); 
           } catch (IOException e) { 
                //i.setImageResource(R.drawable.error); 
                Log.e("DEBUGTAG", "Remtoe Image Exception", e); 
           } 
        
        /* Image should be scaled as width/height are set. */ 
        i.setScaleType(ImageView.ScaleType.FIT_CENTER); 
        /* Set the Width/Height of the ImageView. */ 
        i.setLayoutParams(new GridView.LayoutParams(105, 70)); 
        return i; 
    } 
		
		// references to our images
	
	}
	
	
}
