import org.uddi.api_v3.*;

public class ForoSetUp {

	/**
	 * Subida de servicio post al servidor UDDI
	 * @param args agrs[0] URL del servicio Foro (p.e. http:\\localhost:7162/axis2/services/Foro)
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		ClienteUDDI clienteuddi = new ClienteUDDI("usuario", "clave");
		AuthToken token = clienteuddi.obtenerTokenDeAutentificacion();

		clienteuddi.registrarNegocio(new Name("VigoCoffeeLovers", ""),
				new Description("Negocio de VigoCoffeLovers: hacemos cosas", ""), token);
		
		System.out.println(clienteuddi.obtenerClaveNegocio(new Name("VigoCoffeeLovers", ""), token).toCharArray());

		clienteuddi.registrarServicio(new Name("Foro", ""),
				new Description("Foro de VigoCoffeeLovers en el que puedes compartir post con todos los usuarios del mundo", ""), args[0],
				clienteuddi.obtenerClaveNegocio(new Name("VigoCoffeeLovers", ""), token), token);
//				negocio.getBusinessKey(), token);
		
		String ruta = clienteuddi.obtenerDireccionServicio(clienteuddi.obtenerClaveServicio(new Name("Foro",""), new Name("VigoCoffeeLovers",""), token), token);
		
		System.out.println(ruta);
		
	}

}
