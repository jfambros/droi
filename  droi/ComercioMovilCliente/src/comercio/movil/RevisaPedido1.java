package comercio.movil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class RevisaPedido1 extends Activity{
	private EditText tvComentario = null;
	private ImageView ivContinuar = null;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido1);
        
        //objetos
        tvComentario = (EditText) findViewById(R.id.etComentarioRevisaPed1);
        
        ivContinuar = (ImageView) findViewById(R.id.ivContinuarRevisaPed1);
        ivContinuar.setOnClickListener(ivContinuarPres);

	}
	
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Log.i("comentario",tvComentario.getText().toString());
		}
	};

}
