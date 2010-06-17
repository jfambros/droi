package comercio.movil;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;


public class Principal extends TabActivity {
    /** Called when the activity is first created. */
    private TabHost mTabHost;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("Inicio").setIndicator("Inicio",getResources().getDrawable(R.drawable.inicio)).setContent(new Intent(this, Portada.class)));
        mTabHost.addTab(mTabHost.newTabSpec("Buscar").setIndicator("Buscar",getResources().getDrawable(R.drawable.buscar)).setContent(new Intent(this, Buscar.class)));
        mTabHost.addTab(mTabHost.newTabSpec("Carrito").setIndicator("Carrito",getResources().getDrawable(R.drawable.cesta)).setContent(new Intent(this, Cesta.class)));
        //mTabHost.addTab(mTabHost.newTabSpec("Producto").setIndicator("Producto",getResources().getDrawable(R.drawable.icon)).setContent(new Intent(this, Producto.class)));
       
        mTabHost.setCurrentTab(0);   
    }
}