package comercio.movil;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
	
	private String HOST = "10.0.2.2";

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revisapedido1);
        
        //objetos
        tvComentario = (EditText) findViewById(R.id.etComentarioRevisaPed1);
        
        ivContinuar = (ImageView) findViewById(R.id.ivContinuarRevisaPed1);
        ivContinuar.setOnClickListener(ivContinuarPres);

	}
	
	private void llenaDireccion(){
		//Definición para servicio Web
		String SOAP_ACTION = "capeconnect:servicios:serviciosPortType#obtenerDatosCliente";
	    String METHOD_NAME = "obtenerDatosCliente";
	    String NAMESPACE = "http://www.your-company.com/servicios.wsdl";
	    String URL = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelope;
	    HttpTransportSE httpt;
	    SoapObject result=null;
	 //Fin definición
	}
	
	private OnClickListener ivContinuarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			Log.i("comentario",tvComentario.getText().toString());
		}
	};

}
