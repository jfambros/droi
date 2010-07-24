package utils;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;

public class DatosDireccionKS extends OBDatosDireccion{
	public static Class EVENT_CLASS = new DatosDireccionKS().getClass();
	private int idLibretaDir;
	private int idCliente;
	private String sexoCliente;
	private String empresaCliente;
	private String nombreCliente;
	private String apellidoCliente;
	private String direccCliente;
	private String coloniaCliente;
	private String cpCliente;
	private String ciudadCliente;
	private String estadoCliente;
	private int nombrePais;
	private int idZona;

	public int getIdLibretaDir() {
		return idLibretaDir;
	}

	public void setIdLibretaDir(int idLibretaDir) {
		this.idLibretaDir = idLibretaDir;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getSexoCliente() {
		return sexoCliente;
	}

	public void setSexoCliente(String sexoCliente) {
		this.sexoCliente = sexoCliente;
	}

	public String getEmpresaCliente() {
		return empresaCliente;
	}

	public void setEmpresaCliente(String empresaCliente) {
		this.empresaCliente = empresaCliente;
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

	public int getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(int nombrePais) {
		this.nombrePais = nombrePais;
	}

	public int getIdZona() {
		return idZona;
	}

	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}

	
	public Object getProperty(int index) {
		switch (index) {
        case 0:
            return idLibretaDir;
        case 1: 
            return idCliente;
        case 2:
            return sexoCliente;
        case 3:
            return empresaCliente;
        case 4:
            return nombreCliente;
        case 5:
            return apellidoCliente;
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
            return nombrePais;
        case 12:
            return idZona;
        default:
            return null;
        }		
	}
	

	public int getPropertyCount() {
		return 13;
	}

	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {

		switch (index) {
        case 0:
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "idLibretaDir";
            break;
        case 1: 
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "idCliente";
            break;
        case 2:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "sexoCliente";
            break;
        case 3:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "empresaCliente";
            break;
        case 4:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "nombreCliente";
            break;
        case 5:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "apellidoCliente";
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
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "nombrePais";
            break;
        case 12:
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "idZona";
            break;
        default:
            break;
        }
		
	}

	public void setProperty(int index, Object value) {
		switch (index) {
        case 0:
        	idLibretaDir = Integer.parseInt(value.toString());
            break;
        case 1: 
        	idCliente = Integer.parseInt(value.toString());
            break;
        case 2:
        	sexoCliente = value.toString();
            break;
        case 3:
        	empresaCliente = value.toString();
            break;
        case 4:
        	nombreCliente = value.toString();
            break;
        case 5:
        	apellidoCliente = value.toString();
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
        	nombrePais = Integer.parseInt(value.toString());
            break;
        case 12:
        	idZona = Integer.parseInt(value.toString());
            break;
        default:
            break;
        }		
	}

}
