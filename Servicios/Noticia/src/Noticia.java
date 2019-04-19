
public class Noticia {
	
	private static String titulo = "Título Predeterminado";
	private static String cuerpo = "Descripcion Predeterminado";
	
	public void setDescripcion(String descripcion) {
		cuerpo = descripcion;
		return;
	}
	public void setTitular(String titular) {
		titulo = titular;
		return;
	}
	
	public String getDescripcion() {
		return cuerpo;
	
	}
	
	public String getTitular() {
		return titulo;
	}
}
