package aguasdelnorte.com.ar.agenda2.clases;

public class Categoria {
	
	public static String  getCategoria(int key) {
		String categoria="";
		switch (key) {
		case 1:
			categoria="C";
			break;
		case 2:
			categoria="F";
			break;
		case 3:
			categoria="R";
			break;	
		default:
			break;
		}
		return categoria;
	}
	public static String  getCategoriaDescripcion(char key) {
		String categoria="";
		
		
		switch (key) {
		case 'C':
			categoria="Compras";
			break;
		case 'F':
			categoria="Faturacion";
			break;
		case 'R':
			categoria="RRHH";
			break;	
		default:
			break;
		}
		return categoria;
	}
	public static int  getCategoriaPosicion(char key) {
		int categoria=0;
		
		
		switch (key) {
		case 'C':
			 categoria=1;
			break;
		case 'F':
			 categoria=2;
			break;
		case 'R':
			 categoria=3;
			break;	
		default:
			break;
		}
		return categoria;
	}
}
