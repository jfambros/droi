package comercio.movil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.google.android.maps.MapView.LayoutParams;


public class Mapa extends MapActivity{
	private MapView mapView;
	private MapController mc;
	private Button btnSatelite;
	//private Button btnCalles;
	private ImageView ivBuscar; 
	private Button btnGPS;
	
	
	private LocationManager lm;
    private LocationListener locationListener;
	
	//private Button btnBuscar;
	private GeoPoint pOrigen;
	private Geocoder gcUsuario;
	private GeoPoint pDestino;
	private char onOff = '0';
	private char satCall = 'c';
	
	private double latitudOr = 18.848661;
	private double longitudOr = -97.091745;
	//Para búsqueda
	
	private List<Overlay> listaOverlays;
	private List<Address> direccionUsuario;
	private double latitudUsuario;
	private double longitudUsuario;
	

	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        //Para búsqueda
        /*
        btnBuscar = (Button)findViewById(R.id.btnBuscarMapa);
        btnBuscar.setOnClickListener(btnBuscarPres);
        */
        ivBuscar = (ImageView)findViewById(R.id.ivBuscarMapa);
        ivBuscar.setOnClickListener(ivBuscarPres); 
        
        btnSatelite = (Button)findViewById(R.id.btnSateliteMapa);
        btnSatelite.setOnClickListener(btnSatelitePres);
        
        /*
        btnCalles = (Button)findViewById(R.id.btnCallesMapa);
        btnCalles.setOnClickListener(btnCallesPres);
        */
        btnGPS = (Button) findViewById(R.id.btnGPSMapa);
        btnGPS.setOnClickListener(btnGPSPres);
        
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
        mc.setZoom(17); 

        //---Add a location marker---
        
        MapOverlay mapOverlay = new MapOverlay(pOrigen, R.drawable.marcador);
        
       // List<Overlay> listOfOverlays = mapView.getOverlays();
        
        listaOverlays = mapView.getOverlays();
        listaOverlays.clear();
        listaOverlays.add(mapOverlay);        
 
        /*
        List mapOverlays = mapView.getOverlays();
        mapOverlays.clear();
        Drawable drawableT = Mapa.this.getResources().getDrawable(R.drawable.marcador);
		MapaOverlayOptimizado tienda = new MapaOverlayOptimizado(drawableT);
		OverlayItem oTienda = new OverlayItem(pOrigen, "Ubicacion", "Tienda");
		tienda.addOverlay(oTienda);
		mapOverlays.add(tienda); 
		*/
        mapView.invalidate();
    
    }
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
    	return super.onKeyDown(keyCode, event);
    }
	
	private class MyLocationListener implements LocationListener 
    {
        public void onLocationChanged(Location loc) {
        	if (loc != null) {                
        		loc.getLatitude();
        		loc.getLongitude();
        		/*
                Toast.makeText(getBaseContext(), 
                    "Location changed : Lat: " + loc.getLatitude() + 
                    " Lng: " + loc.getLongitude(), 
                    Toast.LENGTH_SHORT).show();
                */
                GeoPoint p = new GeoPoint(
                        (int) (loc.getLatitude() * 1E6), 
                        (int) (loc.getLongitude() * 1E6));
                /*
                mc.animateTo(p);
                mc.setZoom(17);                
                mapView.invalidate();
                */
                pDestino = p;

               
				MapOverlay mapDestino = new MapOverlay(pDestino, R.drawable.marcadorusuario);
		        listaOverlays = mapView.getOverlays();
		        if (listaOverlays.size()==2){
		        	listaOverlays.remove(1);
		        }
		        //listaOverlays.clear();
		        listaOverlays.add(mapDestino); 

				mc.animateTo(pDestino);
                mc.setZoom(17);                
                mapView.invalidate();   
            
                
            }
        }


        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }


        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }


        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
 
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    class MapOverlay extends com.google.android.maps.Overlay
    {
    	private GeoPoint gp;
    	private int marcador;
    	public MapOverlay(GeoPoint gp, int marcador){
    		super();
    		this.gp = gp;
    		this.marcador = marcador;
    	}
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(gp, screenPts);
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), marcador);
            canvas.drawBitmap(bmp, screenPts.x-10, screenPts.y-40, null);   
            
            
            if (pDestino != null){
	            Paint mPaint = new Paint();
	            mPaint.setDither(true);
	            mPaint.setColor(Color.RED);
	            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	            mPaint.setStrokeJoin(Paint.Join.ROUND);
	            mPaint.setStrokeCap(Paint.Cap.ROUND);
	            mPaint.setStrokeWidth(2);
	            
	            Point p1 = new Point();
	            Point p2 = new Point();
	
	            Path path = new Path(); 
	            Projection projection = mapView.getProjection();
	            
	            projection.toPixels(pOrigen, p1);
	            projection.toPixels(pDestino, p2);
	
	            path.moveTo(p2.x, p2.y);
	            path.lineTo(p1.x,p1.y);
	
	            canvas.drawPath(path, mPaint);
            }
            
            
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
    
    
    public void porSatelite(){
    	mapView.setStreetView(false);
    	mapView.setSatellite(true);
    }
    
    public void porCalles(){
    	mapView.setSatellite(false);
    	mapView.setStreetView(true);
    }
    
    private void activaGPS(){
    	try{
    	//---use the LocationManager class to obtain GPS locations---
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);    
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 
            0, 
            0, 
            locationListener);
        
        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Double lat = loc.getLatitude() * 1E6;
        Double lon = loc.getLongitude() * 1E6;
        
        GeoPoint punto = new GeoPoint(lat.intValue(), lon.intValue());
        pDestino = punto;
        
        MapOverlay mapDestino = new MapOverlay(pDestino, R.drawable.marcadorusuario);
        listaOverlays = mapView.getOverlays();
        if (listaOverlays.size()==2){
        	listaOverlays.remove(1);
        }
        //listaOverlays.clear();
        listaOverlays.add(mapDestino); 

		mc.animateTo(pDestino);
        mc.setZoom(17);                
        mapView.invalidate();
        
    	}
    	catch (Exception e) {
    		Toast.makeText(this, "No se puede obtener la ubicación", Toast.LENGTH_SHORT).show();

		}
         
        //Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        /*
        Toast.makeText(getBaseContext(), 
                "Latitude: " + loc.getLatitude() + 
                " Longitude: " + loc.getLongitude(), 
                Toast.LENGTH_LONG).show();
                */
        
        
      /*
        double lat = loc.getLatitude() * 1E6;
        double lon = loc.getLongitude() * 1E6;
        
        GeoPoint punto = new GeoPoint(lat.intValue(), lon.intValue());
        pDestino = punto;
        
        MapOverlay mapDestino = new MapOverlay(pDestino, R.drawable.marcadorusuario);
        listaOverlays = mapView.getOverlays();
        if (listaOverlays.size()==2){
        	listaOverlays.remove(1);
        }
        //listaOverlays.clear();
        listaOverlays.add(mapDestino); 

		mc.animateTo(pDestino);
        mc.setZoom(17);                
        mapView.invalidate();
        */
    }
      
    private OnClickListener btnSatelitePres = new OnClickListener() {
		
		public void onClick(View arg0) {
			if (satCall == 's'){
			   porSatelite();
			   btnSatelite.setText("Calles");
			   satCall = 'c';
			}
			else{
				porCalles();
				btnSatelite.setText("Satélite");
				satCall = 's';
			}
		}
	}; 
	/*
	private OnClickListener btnCallesPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			porCalles();
		}
	};
	*/
	
	private OnClickListener btnGPSPres = new OnClickListener() {
		
		public void onClick(View arg0) {
			if (onOff == '0'){
				onOff = '1';
				btnGPS.setText("GPS Off");
				activaGPS();
			
			}
			else{
				onOff = '0';				
				btnGPS.setText("GPS On");
				lm.removeUpdates(locationListener);
			}
		}
	};
	
	private OnClickListener ivBuscarPres = new OnClickListener() {
		
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
						pDestino = new GeoPoint((int) (latitudUsuario * 1E6),
								(int) (longitudUsuario * 1E6));
						
						/*
						 * Poner  marcador
						 */
						
						//limpiarCapas(pOrigen, pDestino);
						//En caso de querer varios marcadores
						/*
						List mapOverlays = mapView.getOverlays();
						mapOverlays.clear();
						
						Drawable drawableT = Mapa.this.getResources().getDrawable(R.drawable.marcador);
						MapaOverlayOptimizado tienda = new MapaOverlayOptimizado(drawableT);
						OverlayItem oTienda = new OverlayItem(pOrigen, "Ubicacion", "Tienda");
						tienda.addOverlay(oTienda);
						mapOverlays.add(tienda); 
						
						Drawable drawable = Mapa.this.getResources().getDrawable(R.drawable.marcadorusuario);
						MapaOverlayOptimizado itemizedOverlay = new MapaOverlayOptimizado(drawable);
						OverlayItem usuario = new OverlayItem(pDestino, "Ubicación", "Tu ubicación");
						itemizedOverlay.addOverlay(usuario);
						// add more overlayItems...
						mapOverlays.add(itemizedOverlay);
						*/
						MapOverlay mapDestino = new MapOverlay(pDestino, R.drawable.marcadorusuario);
				        listaOverlays = mapView.getOverlays();
				        if (listaOverlays.size()==2){
				        	listaOverlays.remove(1);
				        }
				        //listaOverlays.clear();
				        listaOverlays.add(mapDestino); 

						mc.animateTo(pDestino);
		                mc.setZoom(17);                
		                mapView.invalidate(); 
						
		                
		                
					}
			   }
			   
			}
			catch (Exception e) {
				Log.e("error BotonBuscarDirec", e.toString());

			}
		}
	};
    
    //Método para obtener la ruta, aún no disponible para México
    /*
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
						pDestino = new GeoPoint((int) (latitudUsuario * 1E6),
								(int) (longitudUsuario * 1E6));

						//drawPath(pOrigen, pDestino, Color.GREEN, mapView);
					}
			   }
			   
			}
			catch (Exception e) {
				Log.e("error BotonBuscarDirec", e.toString());

			}
		}
	};
	*/
	
/*
	protected void onDestroy() {
		super.onDestroy(); 
		lm.removeUpdates(locationListener); 
	};
	*/
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
