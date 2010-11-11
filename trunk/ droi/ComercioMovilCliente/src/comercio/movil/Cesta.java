package comercio.movil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import utils.DatosCesta;
import utils.ListaCesta;
import utils.Validaciones;
import utils.Valores;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public class Cesta extends Activity {
    private TableLayout tlCesta;
    private CheckBox borrar = null;
    private ImageView imagenProd = null;
    private TextView nombreProd = null;
    private EditText cantidadProd = null;
    private TextView precioProd = null;
    
    private static final String HOST = Valores.HOST;
    private String ruta = "http://"+HOST+"/tienda/catalog/images/";
    private Bundle bundle = null;
    private TableRow row=null;
    private HashMap<String, TableRow> cantidadCesta= new HashMap<String, TableRow>();
    private double total = 0.0;
	
	//botones
    private ImageView ivInicio = null;
    private ImageView ivActualizaCesta=null;
    private ImageView ivLimpiaCesta = null;
    private ImageView ivRegresar = null;  
    private ImageView ivConfirmarPedido = null;
    
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cesta);
        
        //objetos obtenidos del xml
        ivInicio = (ImageView)findViewById(R.id.ivInicioCesta);
        tlCesta = (TableLayout)findViewById(R.id.tlCesta);
 		ivActualizaCesta = (ImageView) findViewById(R.id.ivActualizaCesta);
 		ivLimpiaCesta = (ImageView)findViewById(R.id.ivLimpiaCesta);
 		ivRegresar = (ImageView)findViewById(R.id.ivRegresarCesta);
 		ivConfirmarPedido = (ImageView) findViewById(R.id.ivConfirmaPedido);
        
 		//click
        ivInicio.setOnClickListener(ivInicioPres);
		ivActualizaCesta.setOnClickListener(ivActualizaCestaPres);
		ivLimpiaCesta.setOnClickListener(ivLimpiaCestaPres);
		ivRegresar.setOnClickListener(ivRegresarPres);
		ivConfirmarPedido.setOnClickListener(ivConfirmarPedidoPres);
		
        llenaCesta();
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	public void llenaCesta(){
		double precioCant = 0.0;
		double subTotalProd = 0.0;
		
		total = 0.0;
		TextView tvTotal = (TextView) findViewById(R.id.tvPrecioTotalCesta);
        if (!ListaCesta.arregloCesta.isEmpty()){
        	bundle = getIntent().getExtras();
        	if (bundle != null){
	            String id = bundle.getString("idProducto");
		        Log.i("bundle", id+" "+bundle.getString("cantidad"));
		        DatosCesta datos =   ListaCesta.arregloCesta.get(id);
		        Log.i("id",datos.getIdProducto());
		        /*
		        DatosCesta sumaUno =   ListaCesta.arregloCesta.get(id);
		        int cantidad = sumaUno.getCantidadProd();
		        sumaUno.setCantidadProd(cantidad+1);
		        ListaCesta.arregloCesta.put(id, sumaUno);
		        */
		       
        	}
	        try{
	        	int cont=0;

	        	Set set = ListaCesta.arregloCesta.entrySet();

	            Iterator i = set.iterator();

	            while(i.hasNext()){
		            Map.Entry<String,DatosCesta> me = (Map.Entry<String, DatosCesta>)i.next();
		            //Log.i("Datos map: ",me.getKey() + " : " + ((DatosCesta) me.getValue()).getNombreProducto());
	            
	        	
	        	
		        	row = new TableRow(this);
		        	row.setLayoutParams(new LayoutParams(
		        	LayoutParams.FILL_PARENT,
		        	LayoutParams.WRAP_CONTENT));
		        	
		        	
		        	String rutaUrl = ruta + ((DatosCesta) me.getValue()).getImagenProducto();
		        	URL aURL = new URL(rutaUrl);
		        	HttpURLConnection conn= (HttpURLConnection)aURL.openConnection();
		            conn.setDoInput(true);
		            conn.connect();
		            int length = conn.getContentLength();
		            int[] bitmapData =new int[length];
		            byte[] bitmapData2 =new byte[length];
		            InputStream is = conn.getInputStream();
		            Bitmap bmImg = BitmapFactory.decodeStream(is);
		            Bitmap bMapScala = Bitmap.createScaledBitmap(bmImg, 80, 80, true);
		            
		            
		            borrar = new CheckBox(this);
		            borrar.setLayoutParams(new LayoutParams(
				            30,
				            LayoutParams.FILL_PARENT));
		            
		            
		            imagenProd = new ImageView(this);
		            imagenProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            imagenProd.setImageBitmap(bMapScala);
		            
		            nombreProd = new TextView(this);
		            nombreProd.setText(((DatosCesta) me.getValue()).getNombreProducto());
		            nombreProd.setTextColor(Color.BLACK);
		            nombreProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            nombreProd.setGravity(Gravity.CENTER_VERTICAL);
		            nombreProd.setWidth(50);

		            cantidadProd = new EditText(this);
		            
		            cantidadProd.setText( Integer.toString(((DatosCesta) me.getValue()).getCantidadProd()));
		            cantidadProd.setTextColor(Color.BLACK);
		            cantidadProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            cantidadProd.setGravity(Gravity.CENTER_VERTICAL);
		            cantidadProd.setWidth(30);
		            cantidadProd.setHeight(5);
		            cantidadProd.setLines(1);
		            cantidadProd.setId(100+cont);
		            
                    precioCant = ((DatosCesta) me.getValue()).getPrecioProd();
                    subTotalProd = precioCant * Double.parseDouble(cantidadProd.getText().toString());
		            precioProd = new TextView(this);
		            precioProd.setText(Double.toString(subTotalProd));
		            precioProd.setTextColor(Color.BLACK);
		            precioProd.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.FILL_PARENT));
		            precioProd.setGravity(Gravity.CENTER_VERTICAL);
		            precioProd.setWidth(20);
		            
		            row.addView(borrar);
		        	row.addView(imagenProd);
		        	row.addView(nombreProd);
		        	row.addView(cantidadProd);
		        	row.addView(precioProd);
		        	
		        	row.setBackgroundColor(Color.WHITE);
		        	tlCesta.addView(row);
		        	//arr.add(row);
		        	cantidadCesta.put(((DatosCesta) me.getValue()).getIdProducto(), row);
		        	total +=  Double.parseDouble(((TextView)(row.getChildAt(4))).getText().toString());
		        	//Log.i("total", Double.toString(total));
		        	tvTotal.setText(Double.toString(total));
		        	
		        	cont++;
	        	}
	        	
	        	
	        	
	        	
	        }
	        catch(Exception err){
	           Log.e("error", err.toString());
	        }
        }
        else{
           ImageView ivSinProductos = new ImageView(this);
           ivSinProductos = (ImageView)findViewById(R.id.ivSinProductosCesta);
           
           Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.cestavacia);
           Bitmap bMapScala = Bitmap.createScaledBitmap(bitmapOrg, 192, 100, true);
           BitmapDrawable bmd = new BitmapDrawable(bMapScala);
           ivSinProductos.setImageDrawable(bmd);
          ivRegresar.setOnClickListener(ivRegresarPresIni); 
        }		
	}
	
	private void actualizaCesta(){
		Set set = cantidadCesta.entrySet();

        Iterator i = set.iterator();

       if (validaCesta() == false){
        while(i.hasNext()){
            Map.Entry<String, TableRow> me = (Map.Entry<String, TableRow>)i.next();
/*            
            if (validaCantidad(Integer.parseInt(me.getKey()), Integer.parseInt(((EditText) me.getValue().getChildAt(3)).getText().toString())) == false){
            	mensajeError("Error", "La cantidad capturada es mayor que la existente en inventario del producto "+ ((TextView) me.getValue().getChildAt(2)).getText().toString());
            	break;
            }
  */          
            if (((EditText) me.getValue().getChildAt(3)).getText().toString().length() == 0 || !Validaciones.esNumero(((EditText) me.getValue().getChildAt(3)).getText().toString()) ){
            	mensajeError("Error", "Verifique la cantidad del producto "+ ((TextView) me.getValue().getChildAt(2)).getText().toString());
            	break;
            }
            else{
            
	            String cantProd = ((EditText) me.getValue().getChildAt(3)).getText().toString();
	            
	            
	            
	            Log.i("Cajas texto: ",me.getKey() + " : " + cantProd);
	            if (Integer.parseInt(cantProd) == 0 || ((CheckBox) me.getValue().getChildAt(0)).isChecked()){
	            	Log.i("Borrado","La clave "+me.getKey()+" Ah sido borrado");
	            	ListaCesta.arregloCesta.remove(me.getKey());
	            	
	                Intent intent = new Intent();
	                intent.setClass(Cesta.this, Cesta.class);
	                startActivity(intent);
	                finish();
	            }
	            else{
	            	int nuevaCant = Integer.parseInt(((EditText) me.getValue().getChildAt(3)).getText().toString());
	            	DatosCesta cantidadCambiada =   ListaCesta.arregloCesta.get(me.getKey());
	            	cantidadCambiada.setCantidadProd(nuevaCant); 
	            	ListaCesta.arregloCesta.put(me.getKey(), cantidadCambiada);
	                Intent intent = new Intent();
	                intent.setClass(Cesta.this, Cesta.class);
	                startActivity(intent);
	                finish();
	            	//sumaCant.setCantidadProd(cantidadDatos+Integer.parseInt(cantidad.getText().toString()));
	            }
	            	
	        }
        }
       }
       else{
    	   mensajeError("Error", "Revise cantidad");
       }

	}
	
	private void limpiaCesta(){
		ListaCesta.arregloCesta.clear();
        Intent intent = new Intent();
        intent.setClass(Cesta.this, Cesta.class);
        startActivity(intent);
        finish();
	}
	
	private boolean validaCesta(){
		boolean encontrado = false;
		if (ListaCesta.arregloCesta.isEmpty() == true){
			return true;
		}
		
		if (!cantidadCesta.isEmpty()){
        	Set set = cantidadCesta.entrySet();

            Iterator i = set.iterator();

            while(i.hasNext()){
	            Map.Entry<String,TableRow> me = (Map.Entry<String, TableRow>)i.next();
	            Log.i("Cesta confirmada: ",me.getKey() + " Producto: "+((TextView) me.getValue().getChildAt(2)).getText().toString() +" Cantidad:"+((EditText) me.getValue().getChildAt(3)).getText().toString() );
	            
	            if (((EditText) me.getValue().getChildAt(3)).getText().toString().length()>0 && Validaciones.esNumero(((EditText) me.getValue().getChildAt(3)).getText().toString())){
	            	int num = Integer.parseInt(((EditText) me.getValue().getChildAt(3)).getText().toString());
	            	if (num < 0){
	            		encontrado = true;
	            		break;
	            	}
	            }
	            if (!Validaciones.esNumero(((EditText) me.getValue().getChildAt(3)).getText().toString()) ){
	            	//mensajeError("Error", "Verifique la cantidad del producto "+ ((TextView) me.getValue().getChildAt(2)).getText().toString());
	            	encontrado = true;
	            	break;
	            }
	            else{
	            	encontrado = false;
	            }
            }
		}
		return encontrado;
	}
	
	private boolean validaCantidad(int idProd, int cantidad){
   	 //Definición para servicio Web
		String SOAP_ACTIONV = "capeconnect:servicios:serviciosPortType#validaCantidad";
	    String METHOD_NAMEV = "validaCantidad";
	    String NAMESPACEV = "http://www.your-company.com/servicios.wsdl";
	    String URLV = "http://"+HOST+"/tienda/servicios/servicios.php";
	    SoapSerializationEnvelope envelopeValida;
	    HttpTransportSE httptValida;
	    SoapObject resultValida;
	    
	    SoapObject requestValida = new SoapObject(NAMESPACEV, METHOD_NAMEV);
		try{
	        httptValida = new HttpTransportSE(URLV);
	        envelopeValida = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
	        envelopeValida.dotNet = false;
	        requestValida.addProperty ("idProd", idProd );
	        requestValida.addProperty ("cantidad", cantidad);
	        envelopeValida.setOutputSoapObject(requestValida);
	        httptValida.call(SOAP_ACTIONV, envelopeValida);
	        resultValida = (SoapObject)envelopeValida.bodyIn;
	        SoapPrimitive result = (SoapPrimitive) resultValida.getProperty("result");
	        //Log.e("resultado valida cant", result.toString());
	        if (Integer.parseInt(result.toString()) == 1){
	        	return true;
	        }
	        else{
	        	return false;
	        }
	    	
	    }catch (Exception e) {
	    	return false;
		}
   }
	
	private OnClickListener ivInicioPres = new OnClickListener() {
		
		public void onClick(View arg0) {
           Intent intent = new Intent();
           intent.setClass(Cesta.this, Principal.class);
           startActivity(intent);
          
           finish();
		}
	};
	
	private OnClickListener ivActualizaCestaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			actualizaCesta();
		}
	};
	
	private OnClickListener ivLimpiaCestaPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			limpiaCesta();
		}
	};
	
	private OnClickListener ivRegresarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.setClass(Cesta.this, MuestraCategorias.class);
	        startActivity(intent);
	        finish();
		}
	};

	private OnClickListener ivRegresarPresIni = new OnClickListener() {
		
		public void onClick(View arg0) {
	        Intent intent = new Intent();
	        intent.setClass(Cesta.this, Principal.class);
	        startActivity(intent);
	        finish();
		}
	};
	
	private OnClickListener ivConfirmarPedidoPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			if (validaCesta() == false){
			        Intent intent = new Intent();
			        intent.setClass(Cesta.this, VerificaCliente.class);
			        startActivity(intent);
			        finish();
			}
			else{
				mensajeError("Error", "Verifique cantidad");
			}
		}
	};
	
	 private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(Cesta.this)

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
