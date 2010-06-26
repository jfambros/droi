package comercio.movil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utils.Pais;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class NuevoCliente extends Activity{
	private Spinner spinnPais;
	private ArrayList<Pais> arregloPaises;
	private ArrayList<String> nombPais;
	private ImageView ivCalendarioNvoCte;
	private ImageView ivContinuarNvoCte;
	//calendario
    private int anio, mes, dia;
	private static final int ID_DATEPICKER = 0;
	private DatePickerDialog dp;
	
	 public void onCreate(Bundle savedInstanceState) {
		 try{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nuevocliente);
            //botones
	        spinnPais = (Spinner) findViewById(R.id.spinnerNvoCte);
	        ivCalendarioNvoCte = (ImageView) findViewById(R.id.ivCalendarioNvoCte);
	        ivCalendarioNvoCte.setOnClickListener(ivCalendarioNvoCtePres);
	        
	        ivContinuarNvoCte = (ImageView)findViewById(R.id.ivContinuarNvoCte);
	        ivContinuarNvoCte.setOnClickListener(ivContinuarNvoCtePres);
	        
	        
	        llenaPais();
		 }
		 catch(Exception err){
			 Log.e("Error",err.toString());
		 }
	  }
	 
	 
	 
	 
	 private void llenaPais(){
        arregloPaises = new ArrayList<Pais>();
        nombPais = new ArrayList<String>();
        
        for (int i=0; i<5; i++){
        	Pais p = new Pais();
        	p.setIdPais(i);
        	p.setNombrePais("Pais "+i);
        	arregloPaises.add(p);
        	nombPais.add("Pais "+i);
        }
        ArrayAdapter<String> adapterPais = new ArrayAdapter<String>( 
                this,
                android.R.layout.simple_spinner_item,
                nombPais );

        adapterPais.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        
            spinnPais.setAdapter(adapterPais);
            
            spinnPais.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, 
                            View view, 
                            int position, 
                            long id) {
                        Pais d = arregloPaises.get(position);
                        Log.i("Pais seleccionado", d.getNombrePais());
                        
                    }

					public void onNothingSelected(AdapterView<?> arg0) {
					
					}
                }
            );
        
	 }
	 
	 private OnClickListener ivCalendarioNvoCtePres = new OnClickListener() {
		
		public void onClick(View arg0) {
		   final Calendar c = Calendar.getInstance();
		    anio = c.get(Calendar.YEAR);
		    mes = c.get(Calendar.MONTH);
		    dia = c.get(Calendar.DAY_OF_MONTH);
		    showDialog(ID_DATEPICKER);
		}
	};
	
	private OnClickListener ivContinuarNvoCtePres = new OnClickListener() {
		
		public void onClick(View arg0) {
			
		}
	}; 
	
	
	protected Dialog onCreateDialog(int id) {
		/*
		SimpleDateFormat formatF = new SimpleDateFormat("dd/MM/yyyy");
		switch(id){
		   case ID_DATEPICKER:
			   final Calendar c = Calendar.getInstance();
			    anio = c.get(Calendar.YEAR);
			    mes = c.get(Calendar.MONTH);
			    dia = c.get(Calendar.DAY_OF_MONTH);
			    dp = new DatePickerDialog(this, myDateSetListener, anio, mes, dia);

			   try{
			   Date date = formatF.parse(formatF.format(c.getTime()));
			   c.setTime(date);
			   dp.setTitle(formatF.format(date));
			   Log.i("Fecha: ",formatF.format(date));
			   dp.updateDate(
					   c.get(Calendar.YEAR),
					   c.get(Calendar.MONTH),
					   c.get(Calendar.DAY_OF_MONTH)
					   );
			   }
			   catch (ParseException e) {
				   dp.updateDate(anio, mes,dia);
			   }
			   return dp;
		   default:
			    return null;
		}
		*/
		
		  switch(id){
		   case ID_DATEPICKER:
		    return new DatePickerDialog(this,
		      myDateSetListener,
		      anio, mes, dia);
		   default:
		    return null;
		  }
		  
	}
	

/*	
	protected void onPrepareDialog(int id, Dialog dialog) {
		SimpleDateFormat formatF = new SimpleDateFormat("dd/MM/yyyy");
		final Calendar c = Calendar.getInstance();
		switch(id){
		   case ID_DATEPICKER:
			   DatePickerDialog dlg = (DatePickerDialog) dialog;
			   dlg.setTitle(formatF.format(c.getTime()));		// New -- update dialog title
			   dlg.updateDate(
						c.get(Calendar.YEAR),
						c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH)
				);
			   
		}
	}
	*/
	private DatePickerDialog.OnDateSetListener myDateSetListener
	  = new DatePickerDialog.OnDateSetListener(){
	   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
   
		   
		   TextView tvCalendarioNvoCte = (TextView) findViewById(R.id.etFechaNacNvoCte);
		   String fecha = dayOfMonth+"/"+
		   				  (monthOfYear+1)+ "/"+
		   				  year;
	    
	       tvCalendarioNvoCte.setText(fecha);
	       
	    
	   } 
	   
	 };
	 
}
