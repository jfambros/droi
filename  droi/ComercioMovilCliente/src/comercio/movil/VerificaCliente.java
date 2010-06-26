package comercio.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class VerificaCliente extends Activity{
	private ImageView ivClienteNuevo;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.verificacliente);
	        
	        //botones
	        ivClienteNuevo = (ImageView)findViewById(R.id.ivClienteNuevo);
	        ivClienteNuevo.setOnClickListener(ivClienteNuevoPres);
	        
	     }
	 
	 private OnClickListener ivClienteNuevoPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(VerificaCliente.this, NuevoCliente.class);
            startActivity(intent);
            finish();
		}
	};
}
