package comercio.movil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import utils.MyOverLay;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;


public class Mapa extends MapActivity{
	private MapView mapView;
	private MapController mc;
	private Button btnBuscar;
	private GeoPoint pOrigen;
	private Geocoder gcUsuario;
	
	private double latitudOr = 18.848661;
	private double longitudOr = -97.091745;
	private List<Address> direccionUsuario;
	private double latitudUsuario;
	private double longitudUsuario;

	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        
        btnBuscar = (Button)findViewById(R.id.btnBuscarMapa);
        btnBuscar.setOnClickListener(btnBuscarPres);
        
        mapView = (MapView) findViewById(R.id.mapView);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        mc = mapView.getController();
        /*
        String coordinates[] = {"18.84885", "-97.091712"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 */
        
        pOrigen = new GeoPoint(
            (int) (latitudOr * 1E6), 
            (int) (longitudOr * 1E6));
        gcUsuario = new Geocoder(this);
 
        mc.animateTo(pOrigen);
        mc.setZoom(18); 

        //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay();
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);        
 
        mapView.invalidate();
    
    }
 
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    class MapOverlay extends com.google.android.maps.Overlay
    {
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
 
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(pOrigen, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.marcador);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-40, null);         
            return true;
        }
        
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
        	/*
        	Geocoder geoCoder = new Geocoder(Mapa.this, Locale.getDefault());    
            try {
                List<Address> addresses = geoCoder.getFromLocationName(
                    "ORIENTE 6 No. 1418 ORIZABA, VERACRUZ, MEXICO", 5);
                String add = "";
                if (addresses.size() > 0) {
                    p = new GeoPoint(
                            (int) (addresses.get(0).getLatitude() * 1E6), 
                            (int) (addresses.get(0).getLongitude() * 1E6));
                    mc.animateTo(p);    
                    
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
 
                        Toast.makeText(getBaseContext(), 
                                p.getLatitudeE6() / 1E6 + "," + 
                                p.getLongitudeE6() /1E6 , 
                                Toast.LENGTH_SHORT).show();
                    
                    mapView.invalidate();
                }    
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        	*/
           // ---when user lifts his finger---
        	
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                    
                //Esto de aquí abajo sirve para obtener longitud y latitud
                /*
                    Toast.makeText(getBaseContext(), 
                        p.getLatitudeE6() / 1E6 + "," + 
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
                        */
                
                Geocoder geoCoder = new Geocoder(
                        getBaseContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geoCoder.getFromLocation(
                        p.getLatitudeE6()  / 1E6, 
                        p.getLongitudeE6() / 1E6, 1);
 
                    String add = "";
                    if (addresses.size() > 0) 
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                             i++)
                           add += addresses.get(0).getAddressLine(i) + "\n";
                    }
 
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {                
                    e.printStackTrace();
                }   
                return true;


            }
            else
            return false;
            
        }  
    
        
    }
    
    private void drawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01)
    {
    // connect to map web service
    StringBuilder urlString = new StringBuilder();
    urlString.append("http://maps.google.com/maps?f=d&hl=en");
    urlString.append("&saddr=");//from
    urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 ));
    urlString.append(",");
    urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 ));
    urlString.append("&daddr=");//to
    urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
    urlString.append(",");
    urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
    urlString.append("&ie=UTF8&0&om=0&output=kml");
    Log.d("xxx","URL="+urlString.toString());
    // get the kml (XML) doc. And parse it to get the coordinates(direction route).
    Document doc = null;
    HttpURLConnection urlConnection= null;
    URL url = null;
    try
    { 
    url = new URL(urlString.toString());
    urlConnection=(HttpURLConnection)url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setDoOutput(true);
    urlConnection.setDoInput(true);
    urlConnection.connect(); 

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    doc = db.parse(urlConnection.getInputStream()); 

    if(doc.getElementsByTagName("GeometryCollection").getLength()>0)
    {
    //String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName();
    String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ;
    Log.d("xxx","path="+ path);
    String [] pairs = path.split(" "); 
    String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height
    // src
    GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
    mMapView01.getOverlays().add(new MyOverLay(startGP,startGP,1));
    GeoPoint gp1;
    GeoPoint gp2 = startGP; 
    for(int i=1;i<pairs.length;i++) // the last one would be crash
    {
    lngLat = pairs[i].split(",");
    gp1 = gp2;
    // watch out! For GeoPoint, first:latitude, second:longitude
    gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
    mMapView01.getOverlays().add(new MyOverLay(gp1,gp2,2,color));
    Log.d("xxx","pair:" + pairs[i]);
    }
    mMapView01.getOverlays().add(new MyOverLay(dest,dest, 3)); // use the default color
    } 
    }
    catch (MalformedURLException e)
    {
    e.printStackTrace();
    }
    catch (IOException e)
    {
    e.printStackTrace();
    }
    catch (ParserConfigurationException e)
    {
    e.printStackTrace();
    }
    catch (SAXException e)
    {
    e.printStackTrace();
    }
    }
    
    
    
    private OnClickListener btnBuscarPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			EditText tvDireccion = (EditText)findViewById(R.id.etDireccionMapa);
			try{
			   direccionUsuario =  gcUsuario.getFromLocationName(tvDireccion.getText().toString(), 1);
			   if (direccionUsuario.size() == 0){
			      mensajeError("Error", "No se encontró dirección");
			   }
			   else{
				   for (int i = 0; i < direccionUsuario.size(); ++i) {
						//Guardamos el resultado en formato latitud y longitud
						Address x = direccionUsuario.get(i);
						latitudUsuario = x.getLatitude();
						longitudUsuario = x.getLongitude();
						//TODO hacer la llamada al método
					}
			   }
			   
			}
			catch (Exception e) {
				Log.e("error BotonBuscarDirec", e.toString());

			}
		}
	};
	
	private void mensajeError(String titulo, String msj){
		 new AlertDialog.Builder(Mapa.this)

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
