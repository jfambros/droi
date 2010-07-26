package comercio.movil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class Buscar  extends Activity{
	private ImageView ivBuscar;
	private EditText etBuscar;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.buscar);
	        
	        ivBuscar = (ImageView) findViewById(R.id.ivBuscarBuscar);
	        ivBuscar.setOnClickListener(ivBuscarPres);
	        
	        etBuscar = (EditText) findViewById(R.id.etBuscar);
	        
	 }
	 
   private OnClickListener ivBuscarPres = new OnClickListener() {
	
	public void onClick(View v) {
		Intent intent = new Intent();
		if (etBuscar.getText().toString().length()!=0){
			intent.putExtra("palabra", etBuscar.getText().toString());
	        intent.setClass(Buscar.this, ProductosListCatLVBuscar.class);
	        startActivity(intent);
	        finish();
        }
		else{
			mensajeError("Error", "Introduzca una palabra");
		}
			
	}	
   };
   
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(Buscar.this)

     	.setTitle(titulo)

     	.setMessage(msj)

     	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

     	public void onClick(DialogInterface dialog, int whichButton) {

     	setResult(RESULT_OK);
     	  }
     	})
     	.show();   
	 }
}
