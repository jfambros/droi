package comercio.movil;

import utils.Validaciones;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Portada extends Activity {

	private ImageView categorias;
	private ImageView ivDatosCuenta;
	private TextView txtVerCategorias;
	private ImageView ivConf;

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portada);
        categorias = (ImageView)findViewById(R.id.imgCategorias);
        categorias.setOnClickListener(imgCategoriasPres);
        
        txtVerCategorias = (TextView)findViewById(R.id.txtVerCategorias);
        txtVerCategorias.setOnClickListener(txtVerCatPres);
        
        ivDatosCuenta = (ImageView)findViewById(R.id.ivDatosCuenta);
        ivDatosCuenta.setOnClickListener(ivDatosCuentaPres);
        
        ivConf = (ImageView)findViewById(R.id.ivConf);
        ivConf.setOnClickListener(ivConfPres); 
    }    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
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
	
	private OnClickListener ivDatosCuentaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.setClass(Portada.this, VerificaDatosCliente.class);
	        startActivity(intent);				
		}
	};
	
	private OnClickListener ivConfPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			final AlertDialog.Builder alerta = new AlertDialog.Builder(Portada.this);
			final EditText entrada = new EditText(Portada.this);
			        alerta.setView(entrada);
			        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			                Valores.HOST = entrada.getText().toString();
			            }
			        });
			        alerta.setNegativeButton("Cancelar",
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int whichButton) {
			                        dialog.cancel();
			                    }
			                });
			        alerta.setTitle("Ip");
			        alerta.show();
		}
	};
	
	private void verIntent(){
        Intent intent = new Intent();
        intent.setClass(Portada.this, MuestraCategorias.class);
        startActivity(intent);					
	}
}
