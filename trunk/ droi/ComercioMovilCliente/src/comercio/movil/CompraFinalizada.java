package comercio.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CompraFinalizada extends Activity{
 
	private ImageView ivInicio;
	
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.comprafinalizada);
	   
	   ivInicio = (ImageView) findViewById(R.id.ivInicioCompraFin);
	   ivInicio.setOnClickListener(ivInicioPres);
	   
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Intent intent = new Intent();
	        intent.setClass(CompraFinalizada.this, Principal.class);
	        startActivity(intent);
	        finish();	
		}
	};
	
	


}
