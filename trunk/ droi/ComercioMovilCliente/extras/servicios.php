<?php
   //obtenerPedidos(2);
   //insertaProductoOrden(5,29,10);
   //obtenerDatosCliente('jferaa007@gmail.com','123456');
  // print_r (obtenerDatosCliente('jferaa007@gmail.com','12345'));
   /*
   $cliente = array("sexoCliente"=>"m", "nombreCliente"=>"Sujeto X", "apellidoCliente"=>"a", "fechaNacCliente"=>"1977/05/30", "emailCliente"=>"c", "empresaCliente"=>"Persona persona", "direccCliente"=>"Por ahi", "coloniaCliente"=>"Centrooo","cpCliente"=>"r","ciudadCliente"=>"j", "estadoCliente"=>"k", "paisCliente"=>"138","telefonoCliente"=>"jj", "faxCliente"=>"ll", "noticiasCliente"=>"oo", "passwdCliente"=>"pp"
   );
   nuevoCliente($cliente);
   */
  require("lib/nusoap.php"); //librería de soap
  $servidor = new soap_server("servicios.wsdl");
  
  function obtenerCategorias(){
   
	class Categoria {
	public $idCat;
	public $nombreCat;	
	public $imagenCat;

   public function __construct($idCat, $nombreCat, $imagenCat){
     $this->idCat = $idCat;
	 $this->nombreCat = $nombreCat;
	 $this->imagenCat = $imagenCat;
   
    }

   }

   $link2 = mysql_connect("localhost", "admin","admin"); 
      mysql_select_db("tienda", $link2); 
      $result2 = mysql_query("select c.categories_id, cd.categories_name, c.categories_image from categories as c, categories_description as cd, languages as l where c.categories_id = cd.categories_id and cd.language_id = l.languages_id and l.code='es'", $link2); 
    
	  $cont = 0;
	  while ($row2 = mysql_fetch_row($result2)){ 
         $cat[$cont] = new Categoria($row2[0], $row2[1], $row2[2]);
		 $cont++;
      }
	 $listaCategorias = array("categorias"=>$cat);
     return $listaCategorias;
  }
  
  function obtenerProductosPorCategoria($idCat){
   //p.products_id, pd.products_name, p.products_image, p.products_price, m.manufacturers_name
	class ProductosCat {
	public $idProd;
	public $nombreProd;	
	public $imagenProd;
	public $precioProd;
	public $fabricanteProd;

   public function __construct($idProd, $nombreProd, $imagenProd, $precioProd, $fabricanteProd){
     $this->idProd = $idProd;
	 $this->nombreProd = $nombreProd;
	 $this->imagenProd = $imagenProd;
	 $this->precioProd = $precioProd;
	 $this->fabricanteProd = $fabricanteProd;
   
   }
   	public function __get($var) {
		return $this->$var;
	}
	
	public function __set($var, $value) {
		$this->$var = $value;
	}
	
   }

   $link2 = mysql_connect("localhost", "admin","admin"); 
      mysql_select_db("tienda", $link2); 
      $result2 = mysql_query("select  p.products_id, pd.products_name, p.products_image, p.products_price, m.manufacturers_name       from categories c, categories_description cd, products_to_categories pc,  products p, products_description pd, languages l,      manufacturers m where c.categories_id = cd.categories_id and cd.language_id = l.languages_id and  c.categories_id = pc.categories_id and pc.products_id = p.products_id and  p.products_id = pd.products_id and pd.language_id = l.languages_id and p.manufacturers_id = m.manufacturers_id and l.code = 'es' and c.categories_id = ".$idCat, $link2);    
    
	  $cont = 0;
	  while ($row2 = mysql_fetch_row($result2)){ 
         $cat[$cont] = new ProductosCat($row2[0], $row2[1], $row2[2], $row2[3], $row2[4]);
		 $cont++;
      }
	 
	 $arregloProductos = array("productos"=>$cat);
	 
     return $arregloProductos;
  }


  class Producto {
	public $idProd;
	public $nombreProd;	
	public $descripProd;		
	public $modeloProd;
	public $cantidadProd;				
	public $imagenProd;
	public $precioProd;
	public $fabricanteProd;

   public function __construct($idProd, $nombreProd, $descripProd, $modeloProd, $cantidadProd, $imagenProd, $precioProd, $fabricanteProd){
     $this->idProd = $idProd;
	 $this->nombreProd = $nombreProd;
	 $this->descripProd = $descripProd;
	 $this->modeloProd = $modeloProd;
	 $this->cantidadProd = $cantidadProd;
	 $this->imagenProd = $imagenProd;
	 $this->precioProd = $precioProd;
	 $this->fabricanteProd = $fabricanteProd;
   
   }
   	public function __get($var) {
		return $this->$var;
	}
	
	public function __set($var, $value) {
		$this->$var = $value;
	}
	
   }

  

  function obtenerProducto($idProd){
  /*
  "select p.products_id, pd.products_name, pd.products_description, p.products_model, p.products_quantity, p.products_image, pd.products_url, p.products_price, p.products_tax_class_id, p.products_date_added, p.products_date_available, p.manufacturers_id from " . TABLE_PRODUCTS . " p, " . TABLE_PRODUCTS_DESCRIPTION . " pd where p.products_status = '1' and p.products_id = '" . (int)$HTTP_GET_VARS['products_id'] . "' and pd.products_id = p.products_id and pd.language_id = '" . (int)$languages_id . "'"
  */
     $link1 = mysql_connect("localhost", "admin","admin"); 
     mysql_select_db("tienda", $link1); 
	 
	 
     $result1 = mysql_query("select p.products_id, pd.products_name, pd.products_description, p.products_model, p.products_quantity, p.products_image, p.products_price, m.manufacturers_name FROM products p, products_description pd, languages l, manufacturers m WHERE p.products_status = '1' and p.products_id = pd.products_id and pd.language_id = l.languages_id and l.code = 'es' and p.manufacturers_id = m.manufacturers_id and p.products_id = ".$idProd, $link1);     
	 
	  while ($row = mysql_fetch_row($result1)){ 
	     if ($row[3] == ''){
		    $row[3] = "_";
		 }
	     $result = array("idProd" => $row[0], "nombreProd"=> $row[1],"descripProd"=> $row[2], "modeloProd"=> $row[3], "cantidadProd"=> $row[4], "imagenProd"=> $row[5], "precioProd"=> $row[6], "fabricanteProd"=> $row[7]);
      }
	  //print_r($result);
	  
     return $result;
  }
  
  
  function nuevoCliente($cliente){
     $link1 = mysql_connect("localhost", "admin","admin"); 
     mysql_select_db("tienda", $link1); 
     //$arreglocliente = array("clientes"=>$cliente); 
	 //print_r($cliente);
	 //echo "Nombre: ".$cliente["nombreCliente"];
	 //Datos cliente CUSTOMER
	  $result = mysql_query("insert into customers(customers_gender, customers_firstname, customers_lastname, customers_dob, customers_email_address, customers_default_address_id, customers_telephone, customers_fax, customers_password, customers_newsletter) values('".$cliente["sexoCliente"]."', '".
	                           $cliente["nombreCliente"]."', '".
							   $cliente["apellidoCliente"]."', '".
							   $cliente["fechaNacCliente"]."', '".
							   $cliente["emailCliente"]."', null, '".
							   $cliente["telefonoCliente"]."', '".
							   $cliente["faxCliente"]."', '".
							   tep_encrypt_password($cliente["passwdCliente"])."', '".
							   $cliente["noticiasCliente"]."')", $link1);     


	 //Libreta direcciones ADDRESS_BOOK
      $idCte =  mysql_insert_id();
	  
	  $result2 = mysql_query("insert into address_book(customers_id, entry_gender, entry_company, entry_firstname, entry_lastname, entry_street_address, entry_suburb, entry_postcode, entry_city, entry_state, entry_country_id, entry_zone_id) values('".(int)$idCte."', '".
	                           $cliente["sexoCliente"]."', '".
							   $cliente["empresaCliente"]."', '".
							   $cliente["nombreCliente"]."', '".
							   $cliente["apellidoCliente"]."', '".
							   $cliente["direccCliente"]."', '".
							   $cliente["coloniaCliente"]."', '".
							   $cliente["cpCliente"]."', '".
							   $cliente["ciudadCliente"]."', '".
							   $cliente["estadoCliente"]."', ".
							   $cliente["paisCliente"].", 0)", $link1);     
							   
	$direcc_id = mysql_insert_id();
	$result3 = mysql_query("update customers set customers_default_address_id ='".(int)$direcc_id."' where customers_id = '".(int)$idCte."'",$link1);
	
		 //informacion cliente CUSTOMER_INFO
	$result4 =  mysql_query("insert into customers_info (customers_info_id, customers_info_number_of_logons, customers_info_date_account_created) values ('".(int)$idCte."', '0', now() )",$link1);
							   
	 return $result4;
  }
  
  function obtenerPaises(){
  
  class Pais {
	public $idPais;
	public $nombrePais;	

   public function __construct($idPais, $nombrePais){
     $this->idPais = $idPais;
	 $this->nombrePais = $nombrePais;
   
   }
   	public function __get($var) {
		return $this->$var;
	}
	
	public function __set($var, $value) {
		$this->$var = $value;
	}
	
   }
  
  
	  $link = mysql_connect("localhost", "admin","admin"); 
		  mysql_select_db("tienda", $link); 
		  $result = mysql_query("select countries_id, countries_name  from countries", $link); 
		
		  $cont = 0;
		  while ($row = mysql_fetch_row($result)){ 
			 $paises[$cont] = new Pais($row[0], $row[1]);
			 $cont++;
		  }
		 $listaPaises = array("paises"=>$paises);
		 return $listaPaises;
  }
  
  //función para validar el email
  function validaEmailCliente($emailCliente){
	 $link = mysql_connect("localhost", "admin","admin"); 
	 mysql_select_db("tienda", $link); 
	 $result = mysql_query("select customers_email_address from customers where customers_email_address = '".$emailCliente."'", $link);  
	 
	 while ($row = mysql_fetch_row($result)){ 
	    $email = $row[0];
	  }
			  
	 return $email;
			  
  }
  
  //función para validar al cliente
  function validaCliente($emailCliente, $passwdCliente){
 	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	$result = mysql_query("select customers_email_address, customers_password from customers where customers_email_address = '".$emailCliente."'", $link);
		  // and c.customers_password='".$passwdCliente."'
		  while ($row = mysql_fetch_row($result)){ 
			 if (tep_validate_password($passwdCliente, $row[1])) {
			 	$email = $row[0];		    
			 }
		  }
		  return $email; 
  }
  
  //función para obtener los datos de un cliente
  function obtenerDatosCliente($emailCliente){
	class Cliente {
		public $idCliente;
		public $nombreCliente;
		public $apellidoCliente;
		public $fechaNacCliente;
		public $emailCliente;
		public $empresaCliente;
		public $direccCliente;
		public $coloniaCliente;
		public $cpCliente;	
		public $ciudadCliente;
		public $estadoCliente;
		public $paisCliente;
		public $telefonoCliente;
		public $faxCliente;
		public $sexoCliente;
	
	
	   public function __construct($idCliente, $nombreCliente, $apellidoCliente, $fechaNacCliente, $emailCliente, $empresaCliente, $direccCliente, $coloniaCliente, $cpCliente, $ciudadCliente, $estadoCliente, $paisCliente, $telefonoCliente, $faxCliente, $sexoCliente){
		$this->idCliente = $idCliente;
		$this->nombreCliente = $nombreCliente;
		$this->apellidoCliente = $apellidoCliente;
		$this->fechaNacCliente = $fechaNacCliente;
		$this->emailCliente = $emailCliente;
		$this->empresaCliente = $empresaCliente;
		$this->direccCliente = $direccCliente;
		$this->coloniaCliente = $coloniaCliente;
		$this->cpCliente = $cpCliente;	
		$this->ciudadCliente = $ciudadCliente;
		$this->estadoCliente = $estadoCliente;
		$this->paisCliente = $paisCliente;
		$this->telefonoCliente = $telefonoCliente;
		$this->faxCliente = $faxCliente;
		$this->sexoCliente = $sexoCliente;
	   }

		public function __get($var) {
			return $this->$var;
		}
		
		public function __set($var, $value) {
			$this->$var = $value;
		}
   }

	  	  $link = mysql_connect("localhost", "admin","admin"); 
		  mysql_select_db("tienda", $link); 
		  $result = mysql_query("select c.customers_id, c.customers_firstname, c.customers_lastname, c.customers_dob, c.customers_email_address, ab.entry_company, ab.entry_street_address, ab.entry_suburb, ab.entry_postcode, ab.entry_city, ab.entry_state, co.countries_name, c.customers_telephone, c.customers_fax, c.customers_password, c.customers_gender from customers as c, address_book as ab, countries as co where c.customers_default_address_id = ab.address_book_id and ab.entry_country_id = co.countries_id and c.customers_email_address = '".$emailCliente."'", $link); 
		  // and c.customers_password='".$passwdCliente."'

		
		  while ($row = mysql_fetch_row($result)){ 
		  	 if ($row[5]==''){
			    $row[5] = '_';
			 }
			 if ($row[13]==''){
			    $row[13] = '_';
			 }

			 $cliente = new Cliente($row[0], $row[1], $row[2], $row[3], $row[4], $row[5], $row[6], $row[7], $row[8], $row[9], $row[10], $row[11], $row[12], $row[13], $row[15]);		    

		  }
		 return $cliente;
}

//función para obtener el costo del envío

function obtenerCostoEnvio(){
   $link = mysql_connect("localhost", "admin","admin"); 
   mysql_select_db("tienda", $link); 
   $result = mysql_query("select configuration_value from configuration where configuration_title = 'Shipping Cost'", $link); 
   while ($row = mysql_fetch_row($result)){ 
   $costo = $row[0];
  }	  
  return $costo;
}

//función para obtener los datos del banco
function obtenerDatosBanco(){

	$link = mysql_connect("localhost", "admin","admin"); 
   mysql_select_db("tienda", $link); 
   $result = mysql_query("SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_ACCOUNT'", $link); 
   while ($row = mysql_fetch_row($result)){ 
      $cuenta = $row[0];
   }	  

   $result2 = mysql_query("SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_BANK'", $link); 
   while ($row2 = mysql_fetch_row($result2)){ 
      $banco = $row2[0];
   }
   
   $result3 = mysql_query("SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_PAYTO'", $link); 
   while ($row3 = mysql_fetch_row($result3)){ 
      $titular = $row3[0];
   }
   
   $datos = array("numeroCuenta" => $cuenta, "nombreBanco" => $banco, "titularCuenta" => $titular );
   
   return $datos;

/*SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_ACCOUNT'*/
/*SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_BANK'*/
/*SELECT configuration_value from configuration where configuration_key = 'MODULE_PAYMENT_TRANSFER_PAYTO'*/

}

//función para insertar la orden
function insertaOrden($orden){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	$ahora = getdate();
    $hora_actual = $ahora["hours"] . ":" . $ahora["minutes"] . ":" . $ahora["seconds"];
    $fecha_actual = $ahora["year"] . "-" .  $ahora["mon"] . "-" . $ahora["mday"];
    $fechaHora = $fecha_actual." ".$hora_actual;
	
	
	$insertaOrd = mysql_query("insert into orders(customers_id, customers_name, customers_company, customers_street_address, customers_suburb, customers_city, customers_postcode, customers_state, customers_country, customers_telephone, customers_email_address, customers_address_format_id, delivery_name, delivery_company, delivery_street_address, delivery_suburb, delivery_city, delivery_postcode, delivery_state, delivery_country, delivery_address_format_id, billing_name, billing_company, billing_street_address, billing_suburb, billing_city, billing_postcode, billing_state, billing_country, billing_address_format_id, payment_method, cc_type, cc_owner, cc_number, cc_expires, last_modified, date_purchased, orders_status, orders_date_finished, currency, currency_value) values ('". (int)$orden["idCliente"]. "', '".
	$orden["nombreCliente"]. "', '".
	$orden["empresaCliente"]. "', '".
	$orden["direccCliente"]. "', '".	
	$orden["coloniaCliente"]. "', '".	
	$orden["ciudadCliente"]. "', '".
	$orden["cpCliente"]. "', '".
	$orden["estadoCliente"]. "', '".	
	$orden["paisCliente"]. "', '".	
	$orden["telefonoCliente"]. "', '".	
	$orden["emailCliente"]. "', '".	
	(int)$orden["idDireccFormatCliente"]. "', '".	
	$orden["nombreEntrega"]. "', '".
	$orden["empresaEntrega"]. "', '".
	$orden["direccEntrega"]. "', '".	
	$orden["coloniaEntrega"]. "', '".	
	$orden["ciudadEntrega"]. "', '".
	$orden["cpEntrega"]. "', '".
	$orden["estadoEntrega"]. "', '".	
	$orden["paisEntrega"]. "', '".	
	(int)$orden["idDireccFormatEntrega"]. "', '".
	$orden["nombreFactura"]. "', '".
	$orden["empresaFactura"]. "', '".
	$orden["direccFactura"]. "', '".	
	$orden["coloniaFactura"]. "', '".	
	$orden["ciudadFactura"]. "', '".
	$orden["cpFactura"]. "', '".
	$orden["estadoFactura"]. "', '".	
	$orden["paisFactura"]. "', '".	
	(int)$orden["idDireccFormatFactura"]. "', '".
	$orden["formaPago"]. "', '".
	$orden["tipoTarjetaCred"]. "', '".
	$orden["propietarioTarjetaCred"]. "', '".
	$orden["numeroTarjetaCred"]. "', '".
	$orden["expiraTarjetaCred"]. "', '".
	$orden["ultimaModificacion"]. "', '".
	$fechaHora. "', '".
	(int)$orden["estadoOrden"]. "', '".
	$orden["fechaOrdenTerminada"]. "', '".
	$orden["moneda"]. "', '".
	$orden["valorMoneda"]. "'".
	") ", $link); 
	
	$ordenId = mysql_insert_id();
	
	$insertaOrdenHist = mysql_query("insert into orders_status_history(orders_id, orders_status_id, date_added, customer_notified, comments) values(".(int)$ordenId.", 1, '".
	$fechaHora."', 0, '".
	$orden["comentario"]."')", $link);
	
	$insertaOrdenSubTotal = mysql_query("insert into orders_total(orders_id, title, text, value, class, sort_order) values (".
	$ordenId.", 'Subtotal:', '".
	"$".$orden["subTotal"]."', ".
	$orden["subTotal"].", 'ot_subtotal', 1)", $link);

    if ($orden["tipoEnvio"] == "Si"){
	   $tipoEnvio =  utf8_decode('Tarifa Unica (La mejor opción):');
	}
	else{
	   $tipoEnvio =  utf8_decode('Recoger en tienda (Sin costo):');
	}
	$insertaOrdenEnvio = mysql_query("insert into orders_total(orders_id, title, text, value, class, sort_order) values (".
	$ordenId.", '".
	$tipoEnvio."', '".
	"$".$orden["tarifa"]."', ".
	$orden["tarifa"].", 'ot_shipping', 2)", $link);
	
	$total = $orden["subTotal"] + $orden["tarifa"];
	
	$insertaOrdenTotal = mysql_query("insert into orders_total(orders_id, title, text, value, class, sort_order) values (".
	$ordenId.", 'Total:', '".
	"<b>$".$total."</b>', ".
	$total.", 'ot_total', 4)", $link);
	
	
	return $ordenId;
	
}


function insertaProductoOrden($ordenId, $idProd, $cantidadProd){
   //descontar del almacen los productos
   $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	
   $cantidadProdAct = mysql_query("select products_quantity from products where products_id = ".$idProd, $link);
   while ($row = mysql_fetch_row($cantidadProdAct)){ 
      $cantidad = $row[0];
   }
   
   if ($cantidad >= $cantidadProd){
	   $resultProd = mysql_query("select p.products_model, pd.products_name, p.products_price from products as p, products_description as pd, languages as l where p.products_id = pd.products_id and pd.language_id = l.languages_id and l.code = 'es' and p.products_id = ".$idProd, $link);
		while ($row2 = mysql_fetch_row($resultProd)){ 
		   $producto = array("modeloProd"=> $row2[0], "nombreProd"=> $row2[1], "precioProd"=> $row2[2]);
		}
		//print_r ($producto);
	
      $insertaProd = mysql_query("insert into orders_products(orders_id, products_id, products_model, products_name, products_price, final_price, products_tax, products_quantity) values(".(int)$ordenId.", ".
	   (int)$idProd.", '".
	   $producto["modeloProd"]."', '".
	   $producto["nombreProd"]."', ".
	   (float)$producto["precioProd"].", ".
	   (float)$producto["precioProd"].", 0.00, ".
	   (int)$cantidadProd.")", $link);
	   
	
	   
	   
	   $actualizaCantidad = mysql_query("update products set products_quantity = products_quantity - ".$cantidadProd." where products_id = ".$idProd , $link);
	   //si result = 1 si se actualizo
	   $result = 1;
	   
   }
   else{
     //si result = 0 no se puede actualizar
      $result = 0;
   }
   
   return $result;
   
}

//método para obtener la libreta de direcciones de un cliente
function obtenerLibretaDirecciones($idCliente){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	
   $cantidadProdAct = mysql_query("select a.address_book_id, a.customers_id, a.entry_gender, a.entry_company, a.entry_firstname, a.entry_lastname, a.entry_street_address, a.entry_suburb, a.entry_postcode, a.entry_city, a.entry_state, c.countries_name, a.entry_zone_id from address_book as a, countries as c where a.entry_country_id = c.countries_id and a.customers_id = ".$idCliente, $link);
   $cont = 0;
   while ($row = mysql_fetch_row($cantidadProdAct)){ 
      $direcc[$cont] = array("idLibretaDir"=>$row[0], "idCliente"=>$row[1], "sexoCliente"=>$row[2], "empresaCliente"=>$row[3], "nombreCliente"=>$row[4], "apellidoCliente"=>$row[5], "direccCliente"=>$row[6], "coloniaCliente"=>$row[7], "cpCliente"=>$row[8], "ciudadCliente"=>$row[9], "estadoCliente"=>$row[10], "nombrePais"=>$row[11], "idZona"=>$row[12]);
	  $cont++;
   }
   $listaPaises = array("libretaDirecciones"=>$direcc);
   
   return $listaPaises;
}

//método para insertar una nueva dirección en la libreta
function insertaLibretaDireccion($direccion){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	
   $insertaDireccion = mysql_query("insert into address_book(customers_id, entry_gender, entry_company, entry_firstname, entry_lastname, entry_street_address, entry_suburb, entry_postcode, entry_city, entry_state, entry_country_id, entry_zone_id) values('".(int)$direccion["idCliente"]."', '".
   $direccion["sexoCliente"]."', '".
   $direccion["empresaCliente"]."', '".
   $direccion["nombreCliente"]."', '".
   $direccion["apellidoCliente"]."', '".
   $direccion["direccCliente"]."', '".
   $direccion["coloniaCliente"]."', '".
   $direccion["cpCliente"]."', '".
   $direccion["ciudadCliente"]."', '".
   $direccion["estadoCliente"]."', '".
   $direccion["nombrePais"]."', '".
   $direccion["idZona"]."')", $link);

return $insertaDireccion;
}

//método de búsqueda de palabra clave
function buscaProductos($palabra){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	
   $consulta = mysql_query("select  p.products_id, pd.products_name, p.products_image, p.products_price, m.manufacturers_name       from categories c, categories_description cd, products_to_categories pc,  products p, products_description pd, languages l,      manufacturers m where c.categories_id = cd.categories_id and cd.language_id = l.languages_id and  c.categories_id = pc.categories_id and pc.products_id = p.products_id and  p.products_id = pd.products_id and pd.language_id = l.languages_id and p.manufacturers_id = m.manufacturers_id and l.code = 'es' and pd.products_description like '%".$palabra."%'", $link);
   
   	  $cont = 0;
	  while ($row = mysql_fetch_row($consulta)){ 
         $prod[$cont] = array("idProd" => $row[0], "nombreProd"=>$row[1], "imagenProd" => $row[2], "precioProd" => $row[3], "fabricanteProd" => $row[4]);
		 $cont++;
      }
	  
	 $arregloProductos = array("productos"=>$prod);
	 
     return $arregloProductos;
   
}

function obtenerPedidos($idCliente){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
   $consulta = mysql_query("select o.orders_id, o.date_purchased, o.delivery_name, o.billing_name, ot.value as order_total, s.orders_status_name from orders as o, orders_total as ot, orders_status as s, languages as l where o.orders_id = ot.orders_id and ot.class = 'ot_total' and o.orders_status = s.orders_status_id and s.language_id = l.languages_id and l.code = 'es'  and s.public_flag = '1' and o.customers_id = ".$idCliente." order by o.orders_id DESC", $link);
   
   $cont = 0;
   while ($row = mysql_fetch_row($consulta)){ 
 	  $consulta2 = mysql_query("SELECT count(products_id) as total FROM orders_products where orders_id = ".$row[0],$link);
      while ($row2 = mysql_fetch_row($consulta2)){
         $totProd = $row2[0];
	  } 
      $pedido[$cont] = array("idOrden"=>$row[0], "fechaOrden"=>$row[1], "cantidadProd" =>$totProd, "totalOrden"=>$row[4]);
	  $cont++;
   }
   //print_r($pedido);
   $arregloPedidos = array("pedidos" => $pedido);
   return $arregloPedidos;
}

function obtenerPedidosPendientes($idCliente, $estado){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
   $consulta = mysql_query("select o.orders_id, o.date_purchased, o.delivery_name, o.billing_name, ot.value as order_total, s.orders_status_name from orders as o, orders_total as ot, orders_status as s, languages as l where o.orders_id = ot.orders_id and ot.class = 'ot_total' and o.orders_status = s.orders_status_id and s.language_id = l.languages_id and l.code = 'es'  and s.public_flag = '1' and s.orders_status_name = '".$estado."' and o.customers_id = ".$idCliente." order by o.orders_id DESC", $link);
   
   $cont = 0;
   while ($row = mysql_fetch_row($consulta)){ 
 	  $consulta2 = mysql_query("SELECT count(products_id) as total FROM orders_products where orders_id = ".$row[0],$link);
      while ($row2 = mysql_fetch_row($consulta2)){
         $totProd = $row2[0];
	  } 
      $pedido[$cont] = array("idOrden"=>$row[0], "fechaOrden"=>$row[1], "cantidadProd" =>$totProd, "totalOrden"=>$row[4]);
	  $cont++;

   }
   //print_r($pedido);
   $arregloPedidos = array("pedidos" => $pedido);
   return $arregloPedidos;
   
}


function obtenerDetallePedido($idPedido){
	$link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	$consultaCom = mysql_query("select comments from orders_status_history where orders_id = ".$idPedido, $link);
	while ($rowCom = mysql_fetch_row($consultaCom)){ 
	   $comentario = $rowCom[0];
	}
	
	$consultaOT = mysql_query("select value from orders_total where class = 'ot_subtotal' and orders_id = ".$idPedido, $link);
	while ($rowOT = mysql_fetch_row($consultaOT)){ 
	   $subTotal = $rowOT[0];
	}
	
	$consultaTar = mysql_query("select value from orders_total where class = 'ot_shipping' and orders_id = ".$idPedido, $link);
	while ($rowTar = mysql_fetch_row($consultaTar)){ 
	   $tarifa = $rowTar[0];
	}

	$consultaTEnvio = mysql_query("select title from orders_total where class = 'ot_shipping' and orders_id =".$idPedido, $link);
	while ($rowTEnvio = mysql_fetch_row($consultaTEnvio)){ 
	   $tipoEnvio = $rowTEnvio[0];
	}
	
	$consultaStatus = mysql_query("select os.orders_status_name from orders_status as os, languages as l, orders as o where l.code = 'es' and l.languages_id = os.language_id and os.orders_status_id = o.orders_status and o.orders_id =".$idPedido, $link);
	while ($rowStatus = mysql_fetch_row($consultaStatus)){ 
	   $status = $rowStatus[0];
	}
	/*
	select os.orders_status_name
from orders_status as os, languages as l, orders_status_history as osh
where l.code = 'es' and
l.languages_id = os.language_id and
os.orders_status_id = osh.orders_status_id and
osh.date_added = ( SELECT max(date_added)
   FROM orders_status_history o
   where orders_id = 45)
	*/
	
   $consulta = mysql_query("select customers_id, customers_name, customers_company, customers_street_address, customers_suburb, customers_city, customers_postcode, customers_state, customers_country, customers_telephone, customers_email_address, customers_address_format_id, delivery_name, delivery_company, delivery_street_address, delivery_suburb, delivery_city, delivery_postcode, delivery_state, delivery_country, delivery_address_format_id, billing_name, billing_company, billing_street_address, billing_suburb, billing_city, billing_postcode, billing_state, billing_country, billing_address_format_id, payment_method, cc_type, cc_owner, cc_number, cc_expires, last_modified, date_purchased, orders_status, orders_date_finished from orders where orders_id = ".$idPedido, $link);
   while ($row = mysql_fetch_row($consulta)){ 
   
	   if ($row[2]==''){
		$row[2] = '_';
	   }
	   if ($row[13]==''){
		$row[13] = '_';
	   }
	   if ($row[22]==''){
		$row[22] = '_';
	   }	   
   
         $detallePedido = array("idCliente" => $row[0], "nombreCliente"=>$row[1], "empresaCliente" => $row[2], "direccCliente" => $row[3], "coloniaCliente" => $row[4], "ciudadCliente" => $row[5], "cpCliente" => $row[6], "estadoCliente" => $row[7], "paisCliente" => $row[8], "telefonoCliente" => $row[9],  "emailCliente" => $row[10], "idDireccFormatCliente" => $row[11], "nombreEntrega" => $row[12], "empresaEntrega" => $row[13], "direccEntrega" => $row[14], "coloniaEntrega" => $row[15], "ciudadEntrega" => $row[16], "cpEntrega" => $row[17], "estadoEntrega" => $row[18], "paisEntrega" => $row[19], "idDireccFormatEntrega" => $row[20], "nombreFactura" => $row[21], "empresaFactura" => $row[22], "direccFactura" => $row[23], "coloniaFactura" => $row[24], "ciudadFactura" => $row[25], "cpFactura" => $row[26], "estadoFactura" => $row[27], "paisFactura" => $row[28], "idDireccFormatFactura" => $row[29], "formaPago" => $row[30], "tipoTarjetaCred" => $row[31], "propietarioTarjetaCred" => $row[32],  "numeroTarjetaCred" => $row[33], "expiraTarjetaCred" => $row[34], "ultimaModificacion" => $row[35], "fechaOrden" => $row[36], "estadoOrden" => $status,  "fechaOrdenTerminada" => $row[38], "comentario" => $comentario,  "subTotal" => $subTotal,  "tarifa" => $tarifa,  "tipoEnvio" => $tipoEnvio);

      }
	  
    return $detallePedido;
}

function obtenerProductosOrden($idPedido){
   $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	$cont++;
	$consulta = mysql_query("select products_id, products_model, products_name, products_price, final_price, products_quantity from orders_products where orders_id = ".$idPedido, $link);
	while ($row = mysql_fetch_row($consulta)){ 
	   if ($row[1]==''){
		$row[1] = '_';
	   }	
	
		$productos[$cont++] = array("idProd"=>$row[0], "modeloProd"=>$row[1], "nombreProd"=>$row[2], "precioProd"=>$row[3], "precioFinalProd"=>$row[4], "cantidadProd"=>$row[5]);
}
	
	$listaProductos = array("productos" => $productos);
	
	return $listaProductos;
}

function obtenerHistorialStatus($idPedido){
    $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 
	
	$cont++;
	$consulta = mysql_query("select osh.orders_status_id, os.orders_status_name, osh.date_added, osh.customer_notified, osh.comments from orders_status as os, languages as l, orders_status_history as osh where l.code = 'es' and l.languages_id = os.language_id and os.orders_status_id = osh.orders_status_id and osh.orders_id =  ".$idPedido, $link);
	while ($row = mysql_fetch_row($consulta)){ 
		$historial[$cont++] = array("idStatus"=>$row[0], "nombreStat"=>$row[1], "fechaStat"=>$row[2], "notificacion"=>$row[3], "comentario"=>$row[4], );
	}
	
	$listaHistorial = array("historial"=>$historial);
	return $listaHistorial;
		
		
}

function actualizaDatosCliente($cliente){
 $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 

	$actualizaDatos = mysql_query("update customers set customers_gender = '".
	 $cliente["sexoCliente"]."', customers_firstname = '".
     $cliente["nombreCliente"]."', customers_lastname = '".
     $cliente["apellidoCliente"]."', customers_dob = '".
     $cliente["fechaNacCliente"]." 00:00:00', customers_email_address = '".
     $cliente["emailCliente"]."', customers_telephone = '".
     $cliente["telefonoCliente"]."', customers_fax = '".
     $cliente["faxCliente"]."' where customers_id = ". 
	 $cliente["idCliente"]	
	,$link);
	
	$buscaDir = mysql_query("select customers_default_address_id from customers  where customers_id = ".$cliente["idCliente"] , $link);
	
	while ($row = mysql_fetch_row($buscaDir)){
	    $dir = $row[0];
	}
	
	$actualizaDir = mysql_query("update address_book set entry_gender = '".
	$cliente["sexoCliente"]."', entry_company = '".
	$cliente["empresaCliente"]."', entry_firstname = '".
	$cliente["nombreCliente"]."', entry_lastname = '".
	$cliente["apellidoCliente"]."', entry_street_address = '".
	$cliente["direccCliente"]."', entry_suburb = '".
	$cliente["coloniaCliente"]."', entry_postcode = '".	
	$cliente["cpCliente"]."', entry_city = '".
	$cliente["ciudadCliente"]."', entry_state = '".
	$cliente["estadoCliente"]."', entry_country_id = '".
	$cliente["paisCliente"]."' where address_book_id = ".
	$dir	
	,$link);
	
	
	return $actualizaDir;
}

function actualizaContra($emailCliente, $nuevaContra){
   $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link); 

	$actualizaDatos = mysql_query("update customers set customers_password ='".tep_encrypt_password($nuevaContra)."' where customers_email_address = '".$emailCliente."'", $link);
	
	return $actualizaDatos;
}


function actualizaContrasenia($emailCliente, $contraActual, $nuevaContra){
    $link = mysql_connect("localhost", "admin","admin"); 
	mysql_select_db("tienda", $link);
	$consulta = mysql_query("select customers_password from customers where customers_email_address = '".$emailCliente."'", $link);
	while ($row = mysql_fetch_row($consulta)){
	    if (tep_validate_password($contraActual, $row[0])){
		   $val = 1;
		}
	}
	if ($val == 1){
		$actualizaDatos = mysql_query("update customers set customers_password ='".tep_encrypt_password($nuevaContra)."' where    customers_email_address = '".$emailCliente."'", $link);
		return $actualizaDatos;
	}
	else{
	   return 0;
	}
	
	
}


/*

  function validaCliente(){

   $link = mysql_connect("localhost", "admin","admin"); 
      mysql_select_db("tienda", $link); 
      $result = mysql_query("select customers_id from customers where customers_email_address = '".$emailCliente."' and c.customers_password='".$passwdCliente."'", $link); 
     return $result;
  }
*/
  
  
  function tep_validate_password($plain, $encrypted) {
    if (tep_not_null($plain) && tep_not_null($encrypted)) {
// split apart the hash / salt
      $stack = explode(':', $encrypted);

      if (sizeof($stack) != 2) return false;

      if (md5($stack[1] . $plain) == $stack[0]) {
        return true;
      }
    }

    return false;
  }

////
// This function makes a new password from a plaintext password. 
  function tep_encrypt_password($plain) {
    $password = '';

    for ($i=0; $i<10; $i++) {
      $password .= tep_rand();
    }

    $salt = substr(md5($password), 0, 2);

    $password = md5($salt . $plain) . ':' . $salt;

    return $password;
  }
  
  function tep_rand($min = null, $max = null) {
      static $seeded;
  
      if (!isset($seeded)) {
        mt_srand((double)microtime()*1000000);
        $seeded = true;
      }
  
      if (isset($min) && isset($max)) {
        if ($min >= $max) {
          return $min;
        } else {
          return mt_rand($min, $max);
        }
      } else {
        return mt_rand();
      }
    }
	
	function tep_not_null($value) {
    if (is_array($value)) {
      if (sizeof($value) > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      if ( (is_string($value) || is_int($value)) && ($value != '') && ($value != 'NULL') && (strlen(trim($value)) > 0)) {
        return true;
      } else {
        return false;
      }
    }
  }
  
  
   $HTTP_RAW_POST = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA:'';
   $servidor->service($HTTP_RAW_POST_DATA);
   exit();
?>
