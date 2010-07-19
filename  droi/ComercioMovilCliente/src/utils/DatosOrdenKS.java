package utils;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;

public class DatosOrdenKS extends OBDatosOrden{
	public static Class EVENT_CLASS = new DatosOrdenKS().getClass(); 
/*
 * 				<idCliente>String</idCliente>
				<nombreCliente>String</nombreCliente>
				<empresaCliente>String</empresaCliente>
				<direccCliente>String</direccCliente>
				<coloniaCliente>String</coloniaCliente>
				<ciudadCliente>String</ciudadCliente>
				<cpCliente>String</cpCliente>
				<estadoCliente>String</estadoCliente>
				<paisCliente>String</paisCliente>
				<telefonoCliente>String</telefonoCliente>
				<emailCliente>String</emailCliente>
				<idDireccFormatCliente>String</idDireccFormatCliente>
				<nombreEntrega>String</nombreEntrega>
				<empresaEntrega>String</empresaEntrega>
				<direccEntrega>String</direccEntrega>
				<coloniaEntrega>String</coloniaEntrega>
				<ciudadEntrega>String</ciudadEntrega>
				<cpEntrega>String</cpEntrega>
				<estadoEntrega>String</estadoEntrega>
				<paisEntrega>String</paisEntrega>
				<idDireccFormatEntrega>String</idDireccFormatEntrega>
				<nombreFactura>String</nombreFactura>
				<empresaFactura>String</empresaFactura>
				<direccFactura>String</direccFactura>
				<coloniaFactura>String</coloniaFactura>
				<ciudadFactura>String</ciudadFactura>
				<cpFactura>String</cpFactura>
				<estadoFactura>String</estadoFactura>
				<paisFactura>String</paisFactura>
				<idDireccFormatFactura>String</idDireccFormatFactura>
				<formaPago>String</formaPago>
				<tipoTarjetaCred>String</tipoTarjetaCred>
				<propietarioTarjetaCred>String</propietarioTarjetaCred>
				<numeroTarjetaCred>String</numeroTarjetaCred>
				<expiraTarjetaCred>String</expiraTarjetaCred>
				<ultimaModificacion>String</ultimaModificacion>
				<fechaCompra>String</fechaCompra>
				<private String >String</estadoOrden>
				<fechaOrdenTerminada>String</fechaOrdenTerminada>
				<moneda>String</moneda>
				<valorMoneda>String</valorMoneda>
				<comentario>String</comentario>
				<subTotal>String</subTotal>
				<tarifa>String</tarifa>
				<tipoEnvio>String</tipoEnvio>
 */
	private String idCliente;
	private String nombreCliente;
	private String empresaCliente;
	private String direccCliente;
	private String coloniaCliente;
	private String ciudadCliente;
	private String cpCliente;
	private String estadoCliente;
	private String paisCliente;
	private String telefonoCliente;
	private String emailCliente;
	private String idDireccFormatCliente;
	
	private String nombreEntrega;
	private String empresaEntrega;
	private String direccEntrega;
	private String coloniaEntrega;
	private String ciudadEntrega;
	private String cpEntrega;
	private String estadoEntrega;
	private String paisEntrega;
	private String idDireccFormatEntrega;
	
	private String nombreFactura;
	private String empresaFactura;
	private String direccFactura;
	private String coloniaFactura;
	private String ciudadFactura;
	private String cpFactura;
	private String estadoFactura;
	private String paisFactura;
	private String idDireccFormatFactura;
	private String formaPago;
	private String tipoTarjetaCred;
	private String propietarioTarjetaCred;
	private String numeroTarjetaCred;
	private String expiraTarjetaCred;
	private String ultimaModificacion;
	private String fechaCompra;
	private String estadoOrden;
	private String fechaOrdenTerminada;
	private String moneda;
	private String valorMoneda;
	private String comentario;
	private String subTotal;
	private String tarifa;
	private String tipoEnvio;
	
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getEmpresaCliente() {
		return empresaCliente;
	}
	public void setEmpresaCliente(String empresaCliente) {
		this.empresaCliente = empresaCliente;
	}
	public String getDireccCliente() {
		return direccCliente;
	}
	public void setDireccCliente(String direccCliente) {
		this.direccCliente = direccCliente;
	}
	public String getColoniaCliente() {
		return coloniaCliente;
	}
	public void setColoniaCliente(String coloniaCliente) {
		this.coloniaCliente = coloniaCliente;
	}
	public String getCiudadCliente() {
		return ciudadCliente;
	}
	public void setCiudadCliente(String ciudadCliente) {
		this.ciudadCliente = ciudadCliente;
	}
	public String getCpCliente() {
		return cpCliente;
	}
	public void setCpCliente(String cpCliente) {
		this.cpCliente = cpCliente;
	}
	public String getEstadoCliente() {
		return estadoCliente;
	}
	public void setEstadoCliente(String estadoCliente) {
		this.estadoCliente = estadoCliente;
	}
	public String getPaisCliente() {
		return paisCliente;
	}
	public void setPaisCliente(String paisCliente) {
		this.paisCliente = paisCliente;
	}
	public String getTelefonoCliente() {
		return telefonoCliente;
	}
	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	public String getIdDireccFormatCliente() {
		return idDireccFormatCliente;
	}
	public void setIdDireccFormatCliente(String idDireccFormatCliente) {
		this.idDireccFormatCliente = idDireccFormatCliente;
	}
	public String getNombreEntrega() {
		return nombreEntrega;
	}
	public void setNombreEntrega(String nombreEntrega) {
		this.nombreEntrega = nombreEntrega;
	}
	public String getEmpresaEntrega() {
		return empresaEntrega;
	}
	public void setEmpresaEntrega(String empresaEntrega) {
		this.empresaEntrega = empresaEntrega;
	}
	public String getDireccEntrega() {
		return direccEntrega;
	}
	public void setDireccEntrega(String direccEntrega) {
		this.direccEntrega = direccEntrega;
	}
	public String getColoniaEntrega() {
		return coloniaEntrega;
	}
	public void setColoniaEntrega(String coloniaEntrega) {
		this.coloniaEntrega = coloniaEntrega;
	}
	public String getCiudadEntrega() {
		return ciudadEntrega;
	}
	public void setCiudadEntrega(String ciudadEntrega) {
		this.ciudadEntrega = ciudadEntrega;
	}
	public String getCpEntrega() {
		return cpEntrega;
	}
	public void setCpEntrega(String cpEntrega) {
		this.cpEntrega = cpEntrega;
	}
	public String getEstadoEntrega() {
		return estadoEntrega;
	}
	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}
	public String getPaisEntrega() {
		return paisEntrega;
	}
	public void setPaisEntrega(String paisEntrega) {
		this.paisEntrega = paisEntrega;
	}
	public String getIdDireccFormatEntrega() {
		return idDireccFormatEntrega;
	}
	public void setIdDireccFormatEntrega(String idDireccFormatEntrega) {
		this.idDireccFormatEntrega = idDireccFormatEntrega;
	}
	public String getNombreFactura() {
		return nombreFactura;
	}
	public void setNombreFactura(String nombreFactura) {
		this.nombreFactura = nombreFactura;
	}
	public String getEmpresaFactura() {
		return empresaFactura;
	}
	public void setEmpresaFactura(String empresaFactura) {
		this.empresaFactura = empresaFactura;
	}
	public String getDireccFactura() {
		return direccFactura;
	}
	public void setDireccFactura(String direccFactura) {
		this.direccFactura = direccFactura;
	}
	public String getColoniaFactura() {
		return coloniaFactura;
	}
	public void setColoniaFactura(String coloniaFactura) {
		this.coloniaFactura = coloniaFactura;
	}
	public String getCiudadFactura() {
		return ciudadFactura;
	}
	public void setCiudadFactura(String ciudadFactura) {
		this.ciudadFactura = ciudadFactura;
	}
	public String getCpFactura() {
		return cpFactura;
	}
	public void setCpFactura(String cpFactura) {
		this.cpFactura = cpFactura;
	}
	public String getEstadoFactura() {
		return estadoFactura;
	}
	public void setEstadoFactura(String estadoFactura) {
		this.estadoFactura = estadoFactura;
	}
	public String getPaisFactura() {
		return paisFactura;
	}
	public void setPaisFactura(String paisFactura) {
		this.paisFactura = paisFactura;
	}
	public String getIdDireccFormatFactura() {
		return idDireccFormatFactura;
	}
	public void setIdDireccFormatFactura(String idDireccFormatFactura) {
		this.idDireccFormatFactura = idDireccFormatFactura;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getTipoTarjetaCred() {
		return tipoTarjetaCred;
	}
	public void setTipoTarjetaCred(String tipoTarjetaCred) {
		this.tipoTarjetaCred = tipoTarjetaCred;
	}
	public String getPropietarioTarjetaCred() {
		return propietarioTarjetaCred;
	}
	public void setPropietarioTarjetaCred(String propietarioTarjetaCred) {
		this.propietarioTarjetaCred = propietarioTarjetaCred;
	}
	public String getNumeroTarjetaCred() {
		return numeroTarjetaCred;
	}
	public void setNumeroTarjetaCred(String numeroTarjetaCred) {
		this.numeroTarjetaCred = numeroTarjetaCred;
	}
	public String getExpiraTarjetaCred() {
		return expiraTarjetaCred;
	}
	public void setExpiraTarjetaCred(String expiraTarjetaCred) {
		this.expiraTarjetaCred = expiraTarjetaCred;
	}
	public String getUltimaModificacion() {
		return ultimaModificacion;
	}
	public void setUltimaModificacion(String ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}
	public String getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public String getEstadoOrden() {
		return estadoOrden;
	}
	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}
	public String getFechaOrdenTerminada() {
		return fechaOrdenTerminada;
	}
	public void setFechaOrdenTerminada(String fechaOrdenTerminada) {
		this.fechaOrdenTerminada = fechaOrdenTerminada;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getValorMoneda() {
		return valorMoneda;
	}
	public void setValorMoneda(String valorMoneda) {
		this.valorMoneda = valorMoneda;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public Object getProperty(int index) {
		switch (index) {
        case 0:
            return idCliente;
        case 1: 
            return nombreCliente;
        case 2:
            return empresaCliente;
        case 3:
            return direccCliente;
        case 4:
            return coloniaCliente;
        case 5:
            return ciudadCliente;
        case 6:
            return cpCliente;
        case 7:
            return estadoCliente;
        case 8:
            return paisCliente;
        case 9:
            return telefonoCliente;
        case 10:
            return emailCliente;
        case 11:
            return idDireccFormatCliente;
        case 12:
            return nombreEntrega;
        case 13:
            return empresaEntrega;
        case 14:
            return direccEntrega;
        case 15:
            return coloniaEntrega;
        case 16:
            return ciudadEntrega;
        case 17:
            return cpEntrega;
        case 18:
            return estadoEntrega;
        case 19:
            return paisEntrega;            
        case 20:
            return idDireccFormatEntrega;
        case 21:
            return nombreFactura;
        case 22:
            return empresaFactura;
        case 23:
            return direccFactura;
        case 24:
            return coloniaFactura;
        case 25:
            return ciudadFactura;
        case 26:
            return cpFactura;
        case 27:
            return estadoFactura;
        case 28:
            return paisFactura;
        case 29:
            return idDireccFormatFactura;
        case 30:
            return formaPago;
        case 31:
            return tipoTarjetaCred;
        case 32:
            return propietarioTarjetaCred;
        case 33:
            return numeroTarjetaCred;
        case 34:
            return expiraTarjetaCred;
        case 35:
            return ultimaModificacion;
        case 36:
            return fechaCompra;
        case 37:
            return estadoOrden;
        case 38:
            return fechaOrdenTerminada;
        case 39:
            return moneda;
        case 40:
            return valorMoneda;
        case 41:
            return comentario;
        case 42:
            return subTotal;
        case 43:
            return tarifa;
        case 44:
            return tipoEnvio;              
        default:
            return null;
        }
	
	}
	public int getPropertyCount() {
		return 45;
	}
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
        case 0:
        	info.type = PropertyInfo.STRING_CLASS;
            info.name = "idCliente";
        case 1: 
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "nombreCliente";
        case 2:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "empresaCliente";
        case 3:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "direccCliente";
        case 4:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "coloniaCliente";
        case 5:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "ciudadCliente";
        case 6:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "cpCliente";
        case 7:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "estadoCliente";
        case 8:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "paisCliente";
        case 9:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "telefonoCliente";
        case 10:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "emailCliente";
        case 11:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "idDireccFormatCliente";
        case 12:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "nombreEntrega";
        case 13:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "empresaEntrega";
        case 14:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "direccEntrega";
        case 15:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "coloniaEntrega";
        case 16:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "ciudadEntrega";
        case 17:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "cpEntrega";
        case 18:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "estadoEntrega";
        case 19:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "paisEntrega";            
        case 20:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "idDireccFormatEntrega";
        case 21:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "nombreFactura";
        case 22:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "empresaFactura";
        case 23:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "direccFactura";
        case 24:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "coloniaFactura";
        case 25:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "ciudadFactura";
        case 26:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "cpFactura";
        case 27:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "estadoFactura";
        case 28:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "paisFactura";
        case 29:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "idDireccFormatFactura";
        case 30:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "formaPago";
        case 31:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "tipoTarjetaCred";
        case 32:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "propietarioTarjetaCred";
        case 33:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "numeroTarjetaCred";
        case 34:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "expiraTarjetaCred";
        case 35:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "ultimaModificacion";
        case 36:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "fechaCompra";
        case 37:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "estadoOrden";
        case 38:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "fechaOrdenTerminada";
        case 39:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "moneda";
        case 40:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "valorMoneda";
        case 41:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "comentario";
        case 42:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "subTotal";
        case 43:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "tarifa";
        case 44:
        	info.type = PropertyInfo.STRING_CLASS;
        	info.name = "tipoEnvio";              
        default:
            break;
        }
		
	}
	public void setProperty(int index, Object value) {
		switch (index) {
        case 0:
        	idCliente = value.toString();
        	break;
        case 1: 
        	nombreCliente = value.toString();
        	break;
        case 2:
        	empresaCliente = value.toString();
        	break;
        case 3:
        	direccCliente  = value.toString();
        	break;
        case 4:
        	coloniaCliente  = value.toString();
        	break;
        case 5:
        	ciudadCliente = value.toString();
        	break;
        case 6:
        	cpCliente  = value.toString();
        	break;
        case 7:
        	estadoCliente  = value.toString();
        	break;
        case 8:
        	paisCliente  = value.toString();
        	break;
        case 9:
        	telefonoCliente = value.toString();
        	break;
        case 10:
        	emailCliente  = value.toString();
        	break;
        case 11:
        	idDireccFormatCliente  = value.toString();
        	break;
        case 12:
        	nombreEntrega  = value.toString();
        	break;
        case 13:
        	empresaEntrega  = value.toString();
        	break;
        case 14:
        	direccEntrega  = value.toString();
        	break;
        case 15:
        	coloniaEntrega  = value.toString();
        	break;
        case 16:
        	ciudadEntrega  = value.toString();
        	break;
        case 17:
        	cpEntrega = value.toString();
        	break;
        case 18:
        	estadoEntrega  = value.toString();
        	break;
        case 19:
        	paisEntrega = value.toString();
        	break;
        case 20:
        	idDireccFormatEntrega  = value.toString();
        	break;
        case 21:
        	nombreFactura = value.toString();
        	break;
        case 22:
        	empresaFactura = value.toString();
        	break;
        case 23:
        	direccFactura = value.toString();
        	break;
        case 24:
        	coloniaFactura = value.toString();
        	break;
        case 25:
        	ciudadFactura = value.toString();
        	break;
        case 26:
        	cpFactura = value.toString();
        	break;
        case 27:
        	estadoFactura = value.toString();
        	break;
        case 28:
        	paisFactura = value.toString();
        	break;
        case 29:
        	idDireccFormatFactura = value.toString();
        	break;
        case 30:
        	formaPago = value.toString();
        	break;
        case 31:
        	tipoTarjetaCred = value.toString();
        	break;
        case 32:
        	propietarioTarjetaCred = value.toString();
        	break;
        case 33:
        	numeroTarjetaCred = value.toString();
        	break;
        case 34:
        	expiraTarjetaCred = value.toString();
        	break;
        case 35:
        	ultimaModificacion = value.toString();
        	break;
        case 36:
        	fechaCompra = value.toString();
        	break;
        case 37:
        	estadoOrden = value.toString();
        	break;
        case 38:
        	fechaOrdenTerminada = value.toString();
        	break;
        case 39:
        	moneda = value.toString();
        	break;
        case 40:
        	valorMoneda = value.toString();
        	break;
        case 41:
        	comentario = value.toString();
        	break;
        case 42:
        	subTotal = value.toString();
        	break;
        case 43:
        	tarifa = value.toString();
        	break;
        case 44:
        	tipoEnvio = value.toString(); 
        	break;
        default:
            break;
        }
		
	} 
	
}
