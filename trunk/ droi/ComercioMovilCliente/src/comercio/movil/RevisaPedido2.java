package comercio.movil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class RevisaPedido2 extends Activity{
	private Bundle bundle = null;
	private EditText etComentario = null;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido2);
        
        //objetos
        etComentario = (EditText) findViewById(R.id.etComentarioRevisaPed2);
        
        
        bundle = getIntent().getExtras();
        etComentario.setText(bundle.getString("comentario"));
        
        
	}
}
