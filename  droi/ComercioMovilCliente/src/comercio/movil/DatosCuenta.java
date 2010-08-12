package comercio.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DatosCuenta extends Activity{
	private ImageView ivInicio;
	private ImageView ivVerPedidos;
	private ImageView ivModificaDatos;
	private ImageView ivCambiaContra;
	
	private Bundle bundle;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datoscuenta);
		
		ivInicio = (ImageView)findViewById(R.id.ivInicioDatosCliente);
		ivInicio.setOnClickListener(ivInicioPres);
		
		ivVerPedidos = (ImageView)findViewById(R.id.ivVerPedidosCliente);
		ivVerPedidos.setOnClickListener(ivVerPedidosPres);

		ivModificaDatos = (ImageView)findViewById(R.id.ivModificaDatosCliente);
		ivModificaDatos.setOnClickListener(ivModificaDatosPres);
		
		ivCambiaContra = (ImageView)findViewById(R.id.ivModificaPasswordCliente);
		ivCambiaContra.setOnClickListener(ivCambiaContraPres);


		
		bundle = getIntent().getExtras();
		
		
	}
	
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.setClass(DatosCuenta.this, Principal.class);
            startActivity(intent);
            finish();				
		}
	};
	
	private OnClickListener ivVerPedidosPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.putExtra("emailCliente", bundle.getString("emailCliente"));
            intent.putExtra("contra", bundle.getString("contra"));  
            intent.setClass(DatosCuenta.this, RevisaPedidosCliente.class);
            startActivity(intent);
            finish();				
		}
	};
	
	private OnClickListener ivModificaDatosPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.putExtra("emailCliente", bundle.getString("emailCliente"));
            intent.putExtra("contra", bundle.getString("contra"));            
            intent.setClass(DatosCuenta.this, ModificaDatosCliente.class);
            startActivity(intent);
            finish();				
		}
	};
	
	private OnClickListener ivCambiaContraPres = new OnClickListener() {
		
		public void onClick(View arg0) {
            Intent intent = new Intent();
            intent.putExtra("emailCliente", bundle.getString("emailCliente"));
            intent.putExtra("contra", bundle.getString("contra"));
            intent.setClass(DatosCuenta.this, ActualizaContra.class);
            startActivity(intent);
            finish();				
		}
	};

}
