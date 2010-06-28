package utils;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DatosClienteKS extends OBDatosCliente  {
	public static Class EVENT_CLASS = new DatosClienteKS().getClass(); 
	
	private String sexoCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String fechaNacCliente;
	private String emailCliente;
	private String empresaCliente;
	private String direccCliente;
	private String coloniaCliente;
	private String cpCliente;
	private String ciudadCliente;
	private String estadoCliente;
	private String paisCliente;
	private String telefonoCliente;
	private String faxCliente;
	private String noticiasCliente;
	private String passwdCliente;
	
	
	public String getSexoCliente() {
		return sexoCliente;
	}
	public void setSexoCliente(String sexoCliente) {
		this.sexoCliente = sexoCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApellidoCliente() {
		return apellidoCliente;
	}
	public void setApellidoCliente(String apellidoCliente) {
		this.apellidoCliente = apellidoCliente;
	}
	public String getFechaNacCliente() {
		return fechaNacCliente;
	}
	public void setFechaNacCliente(String fechaNacCliente) {
		this.fechaNacCliente = fechaNacCliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
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
	public String getCpCliente() {
		return cpCliente;
	}
	public void setCpCliente(String cpCliente) {
		this.cpCliente = cpCliente;
	}
	public String getCiudadCliente() {
		return ciudadCliente;
	}
	public void setCiudadCliente(String ciudadCliente) {
		this.ciudadCliente = ciudadCliente;
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
	public String getFaxCliente() {
		return faxCliente;
	}
	public void setFaxCliente(String faxCliente) {
		this.faxCliente = faxCliente;
	}
	public String getNoticiasCliente() {
		return noticiasCliente;
	}
	public void setNoticiasCliente(String noticiasCliente) {
		this.noticiasCliente = noticiasCliente;
	}
	public String getPasswdCliente() {
		return passwdCliente;
	}
	public void setPasswdCliente(String passwdCliente) {
		this.passwdCliente = passwdCliente;
	}
	
	public Object getProperty(int index) {
		switch (index) {
        case 0:
            return sexoCliente;
        case 1: 
            return nombreCliente;
        case 2:
            return apellidoCliente;
        case 3:
            return fechaNacCliente;
        case 4:
            return emailCliente;
        case 5:
            return empresaCliente;
        case 6:
            return direccCliente;
        case 7:
            return coloniaCliente;
        case 8:
            return cpCliente;
        case 9:
            return ciudadCliente;
        case 10:
            return estadoCliente;
        case 11:
            return paisCliente;
        case 12:
            return telefonoCliente;
        case 13:
            return faxCliente;
        case 14:
            return noticiasCliente;
        case 15:
            return passwdCliente;
        default:
            return null;
        }

	}
	
	public int getPropertyCount() {
		return 16;
	}
	public void getPropertyInfo(int index, Hashtable propiedades, PropertyInfo info) {
		switch (index) {
        case 0:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "sexoCliente";
            break;
        case 1: 
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "nombreCliente";
            break;
        case 2:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "apellidoCliente";
            break;
        case 3:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "fechaNacCliente";
            break;
        case 4:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "emailCliente";
            break;
        case 5:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "empresaCliente";
            break;
        case 6:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "direccCliente";
            break;
        case 7:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "coloniaCliente";
            break;
        case 8:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "cpCliente";
            break;
        case 9:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "ciudadCliente";
            break;
        case 10:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "estadoCliente";
            break;
        case 11:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "paisCliente";
            break;
        case 12:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "telefonoCliente";
            break;
        case 13:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "faxCliente";
            break;
        case 14:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "noticiasCliente";
            break;
        case 15:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "passwdCliente";
            break;
        default:
            break;
        }
		
	}
	public void setProperty(int index, Object value) {
		switch (index) {
        case 0:
        	sexoCliente = value.toString();
            break;
        case 1: 
        	nombreCliente = value.toString();
            break;
        case 2:
        	apellidoCliente = value.toString();
            break;
        case 3:
        	fechaNacCliente = value.toString();
            break;
        case 4:
        	emailCliente = value.toString();
            break;
        case 5:
        	empresaCliente = value.toString();
            break;
        case 6:
        	direccCliente = value.toString();
            break;
        case 7:
        	coloniaCliente = value.toString();
            break;
        case 8:
        	cpCliente = value.toString();
            break;
        case 9:
        	ciudadCliente = value.toString();
            break;
        case 10:
        	estadoCliente = value.toString();
            break;
        case 11:
        	paisCliente = value.toString();
            break;
        case 12:
        	telefonoCliente = value.toString();
            break;
        case 13:
        	faxCliente = value.toString();
            break;
        case 14:
        	noticiasCliente = value.toString();
            break;
        case 15:
        	passwdCliente = value.toString();
            break;
        default:
            break;
        }
		
	}
}
