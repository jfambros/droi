package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.Date;

public class OpBd {
	private final Context ctx;
	private SQLiteDatabase myBD=null;
	
/**
 * Definiciones de las tablas
 */
	private static final String NOMBRE_BD = "comercio.db";
	private static final String CREA_TABLACATEGORIA = "CREATE TABLE IF NOT EXISTS tblCategoria" +
			"(_id integer primary key autoincrement," +
			"nombreCat varchar not null," +
			"descripcionCat varchar not null);";
	private static final String CREA_TABLAFABRICANTE = "CREATE TABLE IF NOT EXISTS tblFabricante" +
			"(_idFabricante integer primary key autoincrement," +
			" nombreFabricante varchar not null);";
	private static final String CREA_TABLAPRODUCTO = "CREATE TABLE IF NOT EXISTS tblProducto" +
			"(_id integer primary key autoincrement, " +
			"modeloProd varchar not null," +
			"categoriaProd integer not null," +
			"nombreProd varchar not null," +
			"descripcionProd varchar not null," +
			"precioProd float not null," +
			"fabricanteProd integer not null," +
			"imagenProd varchar,"+
			"constraint categoriaFK foreign key(categoriaProd) references tblCategoria(_id)," +
			"constraint fabricanteFK foreign key(fabricanteProd) references tblFabricante(_idFabricante) );";	
	
	private static final String CREA_TABLACLIENTE = "CREATE TABLE IF NOT EXISTS tblCliente" +
			"(_idCliente integer primary key autoincrement, " +
	        "nombreCli varchar not null,"+
	        "apellidosCli varchar not null,"+
	        "direccionCli varchar,"+
	        "ciudadCli varchar,"+
	        "estadoCli varchar,"+
	        "codigoPostalCli varchar,"+
	        "telefonoCli varchar,"+
	        "emailCli varchar not null,"+
	        "nombreUsuario varchar not null,"+
	        "password varchar not null);";

	private static final String CREA_TABLAORDEN = "CREATE TABLE IF NOT EXISTS tblOrden" +
			"(_idOrden integer primary key autoincrement, " +
	        "clienteID integer not null,"+
	        "fechaOrden date not null,"+
	        "subTotal float not null,"+
	        "impuesto float not null,"+
	        "precioTotal float not null," +
	        "constraint clienteIDFK foreign key(clienteID) references tblCliente(_idCliente)"+
	        ");";
	
	private static final String CREA_TABLAORDENDETALLE = "CREATE TABLE IF NOT EXISTS tblDetalleOrden" +
			"(_idOrdenDetalle integer primary key autoincrement, " +
			"ordenID integer not null,"+
			"productoID integer not null,"+
			"cantidad integer not null," +
			"constraint ordenFK foreign key(ordenID) references tblOrden(_idOrden)," +
			"constraint productoFK foreign key(productoID) references tblProducto(_id) );";
	
	private static final String CREA_TABLACART = "CREATE TABLE IF NOT EXISTS tblCart" +
			"(_idCart integer primary key autoincrement, " +
			"productoID integer not null,"+
			"cantidad integer not null," +
			"constraint productoIDFK foreign key(productoID) references tblProducto(_id) );";
	
   private static final String CREA_TABLAUSUARIOS = "CREATE TABLE IF NOT EXISTS tblUsuarios"+
   			"(_idUsuario integer primary key autoincrement, "+
   			"nombreUsu varchar not null,"+
   			"passwUsu varchar not null,"+
   			"tipoUsu varchar not null"+
   			");";
	
	public OpBd(Context ctx){
		this.ctx = ctx;	
	}
	
	public void creaBD(){
	  myBD = ctx.openOrCreateDatabase(NOMBRE_BD,Context.MODE_PRIVATE, null);
	}
	
	/**
	 * Métodos para crear las tablas
	 */
	public void creaTablaCategoria(){
		myBD.execSQL(CREA_TABLACATEGORIA);
	}
	public void creaTablaFabricante(){
		myBD.execSQL(CREA_TABLAFABRICANTE);
	}
	public void creaTablaProducto(){
		myBD.execSQL(CREA_TABLAPRODUCTO);
	}
	public void creaTablaCliente(){
		myBD.execSQL(CREA_TABLACLIENTE);
	}
	public void creaTablaOrden(){
		myBD.execSQL(CREA_TABLAORDEN);
	}
	public void creaTablaDetalleOrden(){
		myBD.execSQL(CREA_TABLAORDENDETALLE);
	}	
    public void creaTablaCart(){
    	myBD.execSQL(CREA_TABLACART);
    }

    public void creaTablaUsuario(){
    	myBD.execSQL(CREA_TABLACART);
    }
    
    /**
     * Crear todas las tablas
     */
    
    public void creaTablas(){
       creaTablaCategoria();
       creaTablaFabricante();
       creaTablaProducto();
       creaTablaCliente();
       creaTablaOrden();
       creaTablaDetalleOrden();
       creaTablaCart();
       creaTablaUsuario();
    }
    
	/**
	 * Métodos para insertar en las tablas
	 */
	public void insertaCategoria(String nombCat, int descripCat){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("nombreCat", nombCat);
		newValues.put("descripcionCat", descripCat);
		myBD.insert("tblCategoria", null, newValues);
	}

	public void insertaFabricante(String nombFabricante){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("nombreFabricante", nombFabricante);
		myBD.insert("tblFabricante", null, newValues);
	}

	public void insertaProducto(String modelo, int categoria, String nombre, String descripcion, double precio, int fabricante ){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("modeloProd", modelo);
		newValues.put("categoriaProd", categoria);
		newValues.put("nombreProd", nombre);
		newValues.put("descripcionProd", descripcion);
		newValues.put("precioProd", precio);
		newValues.put("fabricanteProd", fabricante);
		myBD.insert("tblFabricante", null, newValues);
	}	

	public void insertaCliente(String nombre, String apellidos,	String direccion, String ciudad, String estado,	String codigoPostal, String telefono, String email,	String nombUsuario,	String password){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("nombreCli", nombre);
		newValues.put("apellidosCli", apellidos);
		newValues.put("direccionCli", direccion);
		newValues.put("ciudadCli", ciudad);
		newValues.put("estadoCli", estado);
		newValues.put("codigoPostalCli", codigoPostal);
		newValues.put("telefonoCli", telefono);
		newValues.put("emailCli", email);
		newValues.put("nombreUsuario", nombUsuario);
		newValues.put("password", password);		
		myBD.insert("tblCliente", null, newValues);
	}	

	public void insertaOrden(String clienteId, Date fechaOrden, double subtotal, double impuesto, double precioTotal ){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("clienteID", clienteId);
		newValues.put("fechaOrden", fechaOrden.toString());
		newValues.put("subTotal", subtotal);
		newValues.put("impuesto", impuesto);
		newValues.put("precioTotal", precioTotal);
		
		myBD.insert("tblOrden", null, newValues);
	}	

	public void insertaOrdenDetalle(String productoID, int cantidad){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("productoID", productoID);
		newValues.put("cantidad", cantidad);
		
		myBD.insert("tblOrdenDetalle", null, newValues);
	}	

	public void insertaCart(int productoID, int cantidad){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("productoID", productoID);
		newValues.put("cantidad", cantidad);
		
		myBD.insert("tblCart", null, newValues);
	}	

	public void insertaUsuario(String nombreUsu, String passwUsu, String tipoUsu){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("nombreUsu", nombreUsu);
		newValues.put("passwUsu", passwUsu);
		newValues.put("tipoUsu", tipoUsu);
		
		myBD.insert("tblUsuario", null, newValues);
	}		

	
	/**
	 * Métodos para eliminar en las tablas
	 */
	//TODO: Falta los demás métodos para el usuario
	
	public boolean eliminaCategoria(int idCategoria){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblCategoria", "_id =" + idCategoria, null) > 0;	
	}
	public boolean eliminaFabricante(int idFabricante){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblFabricante", "_idFabricante =" + idFabricante, null) > 0;	
	}
	public boolean eliminaProducto(int idProducto){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblProducto", "_id =" + idProducto, null) > 0;	
	}	
	public boolean eliminaCliente(int idCliente){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblCliente", "_idCliente =" + idCliente, null) > 0;	
	}
	public boolean eliminaOrden(int idOrden){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblOrden", "_idOrden =" + idOrden, null) > 0;	
	}
	public boolean eliminaOrdenDetalle(int idOrdenDetalle){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblOrdenDetalle", "_idOrdenDetalle =" + idOrdenDetalle, null) > 0;	
	}	
	public boolean eliminaCart(int idCart){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		return myBD.delete("tblCart", "_idCart =" + idCart, null) > 0;	
	}	
	
	/**
	 * Métodos para consultar todo el contenido de las tablas
	 */
    public Cursor consultaCategorias() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblCategoria", null, null, null, null, null, null);
    }
    public Cursor consultaFabricantes() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblFabricante", null, null, null, null, null, null);
    }
    public Cursor consultaProducto() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblProducto", null, null, null, null, null, null);
    }    
    public Cursor consultaCliente() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblCliente", null, null, null, null, null, null);
    }
    public Cursor consultaOrden() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblOrden", null, null, null, null, null, null);
    }  
    public Cursor consultaOrdenDetalle() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblOrdenDetalle", null, null, null, null, null, null);
    }
    public Cursor consultaCart() {
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
        return myBD.query("tblCart", null, null, null, null, null, null);
    }     
    /**
     * Métodos para consultar solo un registro de las tablas
     * 
     */
    
    public Cursor consultaUnaCategoria(int idCat){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.query(true, "tblCategoria", new String[] {"_id","nombreCat", "descripcionCat"}, "_id =" + idCat, null, null, null, null, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }
    public Cursor consultaUnFabricante(int idFab){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.query(true, "tblFabricante", new String[] {"_idFabricante","nombreFabricante"}, "_idFabricante =" + idFab, null, null, null, null, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }
    public Cursor consultaUnProducto(Context ct, int idProducto){
    	String sql = "select p._id, p.nombreProd, p.descripcionProd, p.precioProd, p.imagenProd, p.modeloProd, f.nombreFabricante " +
    			"from tblProducto as p, tblFabricante as f " +
    			"where p.fabricanteProd = f._idFabricante and " +
    			"p._id = "+idProducto;
    	myBD = ct.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.rawQuery(sql, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }
    

    

    public Cursor consultaUnCliente(int idCliente){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.query(true, "tblCliente", new String[] {"_idCliente","nombreCli","apellidosCli", "direccionCli","ciudadCli", "estadoCli", "codigoPostalCli","telefonoCli","email","nombreUsuario"}, "_idCliente =" + idCliente, null, null, null, null, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }
    
    public Cursor consultaUnCart(Context ct, int idCart){
    	String sql = "p._idProducto, p.modeloProd, p.nombreProd, p.precioProd, c.cantidad " +
		   "from tblProducto as p, tblCart as c " +
		   "where p._idProducto = c.productoID and " +
		   "c._idCart = "+idCart;
        myBD = ct.openOrCreateDatabase(NOMBRE_BD, 1, null);
        Cursor cu = myBD.rawQuery(sql, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }   
    
    public Cursor consultaProductosDeCategoria(Context ct, int idCategoria){
    	String sql = "select p._id, p.nombreProd, p.modeloProd, p.precioProd, p.imagenProd, f.nombreFabricante " +
    			"from tblProducto as p, tblFabricante as f " +
    			"where p.fabricanteProd = f._idFabricante and " +
    			"p.categoriaProd = "+idCategoria;
    	myBD = ct.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.rawQuery(sql, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }      
    
    public Cursor consultaUnProductoCat(Context ct, int idCat){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	Cursor cu = myBD.query(true, "tblProducto", new String[] {"_id","nombreProd","modeloProd","precioProd","imagenProd"}, "categoriaProd =" + idCat, null, null, null, null, null);
    	if (cu != null)
    	   return cu;
    	else
    		return null;
    }       
    
    /**
     * Métodos para actualizar las tablas  
     */
    
    public boolean actualizaCategoria(int idCat, String nombCat, int descripCat){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	ContentValues args = new ContentValues();
        args.put("nombreCat", nombCat);
        args.put("descripcionCat", descripCat);

        return myBD.update("tblCategoria", args, "_id =" + idCat, null) > 0;    	
    }

    public boolean actualizaFabricante(int idFab, String nombFab){
    	myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
    	ContentValues args = new ContentValues();
        args.put("nombreFabricante", nombFab);

        return myBD.update("tblFabricante", args, "_id =" + idFab, null) > 0;    	
    }    
    
	public boolean actualizaProducto(int idProducto, String modelo, int categoria, String nombre, String descripcion, double precio, int fabricante ){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues nuevosValores = new ContentValues();
		nuevosValores.put("modeloProd", modelo);
		nuevosValores.put("categoriaProd", categoria);
		nuevosValores.put("nombreProd", nombre);
		nuevosValores.put("descripcionProd", descripcion);
		nuevosValores.put("precioProd", precio);
		nuevosValores.put("fabricanteProd", fabricante);
		return myBD.update("tblProducto", nuevosValores, "_id =" + idProducto, null) > 0;
	}
    
	public boolean actualizaCliente(int idCliente, String nombre, String apellidos,	String direccion, String ciudad, String estado,	String codigoPostal, String telefono, String email,	String nombUsuario,	String password){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues nuevosValores = new ContentValues();
		nuevosValores.put("nombreCli", nombre);
		nuevosValores.put("apellidosCli", apellidos);
		nuevosValores.put("direccionCli", direccion);
		nuevosValores.put("ciudadCli", ciudad);
		nuevosValores.put("estadoCli", estado);
		nuevosValores.put("codigoPostalCli", codigoPostal);
		nuevosValores.put("telefonoCli", telefono);
		nuevosValores.put("emailCli", email);
		nuevosValores.put("nombreUsuario", nombUsuario);
		nuevosValores.put("password", password);		
		return myBD.update("tblCliente", nuevosValores, "_idCliente =" + idCliente, null) > 0;
	}   

	
	public boolean actualizaCart(int idCart, int productoID, int cantidad){
		myBD = ctx.openOrCreateDatabase(NOMBRE_BD, 1, null);
		ContentValues nuevosValores = new ContentValues();
		// Assign values for each row.
		nuevosValores.put("cantidad", cantidad);
		
		return myBD.update("tblCart", nuevosValores, "_idCart =" + idCart+" and productoID = "+productoID, null) > 0;
	}	
} 
