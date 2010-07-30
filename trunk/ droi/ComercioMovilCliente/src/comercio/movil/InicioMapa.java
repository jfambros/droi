package comercio.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class InicioMapa extends Activity{
	private ImageView ivMapa;
	
	public void onCreate(Bundle savedInstanceState) {
		try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciomapa);
        
        ivMapa = (ImageView) findViewById(R.id.ivMapa);
        ivMapa.setOnClickListener(ivMapaPres);
        
		}
		catch(Exception err){
			Log.e("error create", err.toString());
		}
        
	}
	
	private OnClickListener ivMapaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(InicioMapa.this, Mapa.class);
			startActivity(intent);
			finish();
			
		}
	};
	
	

}
