package comercio.movil;

import android.widget.ImageView;
import android.widget.TextView;

public class ProductosListCat {
   private TextView tvNombre;
   private ImageView ivImagen;
   private TextView tvPrecio;
   private TextView tvFabricante;
   
	public TextView getTvFabricante() {
		return tvFabricante;
	}
public void setTvFabricante(TextView tvFabricante) {
		this.tvFabricante = tvFabricante;
	}
	public TextView getTvNombre() {
		return tvNombre;
	}
	public void setTvNombre(TextView tvNombre) {
		this.tvNombre = tvNombre;
	}
	public ImageView getIvImagen() {
		return ivImagen;
	}
	public void setIvImagen(ImageView ivImagen) {
		this.ivImagen = ivImagen;
	}
	public TextView getTvPrecio() {
		return tvPrecio;
	}
	public void setTvPrecio(TextView tvPrecio) {
		this.tvPrecio = tvPrecio;
	}
   
}
