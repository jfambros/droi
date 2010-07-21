package comercio.movil;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Portada extends Activity {

	private ImageView categorias;
	private TextView txtVerCategorias;
	URL rutaUrl;

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portada);
       // String url = "http://www.terra.es/personal3/javaja/android/img/logoAndroid_2_camiseta.png";
        categorias = (ImageView)findViewById(R.id.imgCategorias);
        categorias.setOnClickListener(imgCategoriasPres);
        
        txtVerCategorias = (TextView)findViewById(R.id.txtVerCategorias);
        txtVerCategorias.setOnClickListener(txtVerCatPres);
        /*
        try{
        HttpURLConnection conn= (HttpURLConnection)rutaUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        int length = conn.getContentLength();
        int[] bitmapData =new int[length];
        byte[] bitmapData2 =new byte[length];
        InputStream is = conn.getInputStream();
        Bitmap bmImg = BitmapFactory.decodeStream(is);
        ImageView img = (ImageView) findViewById(R.id.imgRemoto1);
        img.setImageBitmap(bmImg);
        }
        catch(IOException err){

        }
*/
        //http://www.terra.es/personal3/javaja/android/img/logoAndroid_2_camiseta.png
         
    }
    
    	
	private OnClickListener imgCategoriasPres = new OnClickListener() {
		
		public void onClick(View v) {
			verIntent();
			finish();
 		}
	};
	
	private OnClickListener txtVerCatPres = new OnClickListener() {
		
		public void onClick(View v) {
		   verIntent();
		   finish();
		}
	};
	
	private void verIntent(){
        Intent intent = new Intent();
        intent.setClass(Portada.this, MuestraCategorias.class);
        startActivity(intent);					
	}
}
