package comercio.movil;

public class ProductosCat {
	private String idProd;
    private String nombreProd;
    private String precioProd; 
    private String imagenProd;
    private String nombreFabricante;
    
	public String getIdProd() {
		return idProd;
	}
	public void setIdProd(String idProd) {
		this.idProd = idProd;
	}
	public String getNombreFabricante() {
		return nombreFabricante;
	}
	public void setNombreFabricante(String nombreFabricante) {
		this.nombreFabricante = nombreFabricante;
	}
	public String getNombreProd() {
		return nombreProd;
	}
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}
	public String getPrecioProd() {
		return precioProd;
	}
	public void setPrecioProd(String precioProd) {
		this.precioProd = precioProd;
	}
	public String getImagenProd() {
		return imagenProd;
	}
	public void setImagenProd(String imagenProd) {
		this.imagenProd = imagenProd;
	}
}
