# AST
Proyecto C de AST

/*********************************************************/
  Intrucciones de compilzación e instalación del servicio
/*********************************************************/

Estas instrucciones se dividen en 3 partes:
  - Instalación de servicios
  - Instalación de módulos
  - Instalación del servidor jUDDI

INSTALACIÓN DE SERVICIOS:

Los servicios desplegados son 3 servicios POJO muy sencillos de implementar:
1. Compila los 4 archivos .java y guarda los resultantes .class en la carpeta: _{APACHE_HOME}/webapps/axis2/WEB-INF/pojo_
2. Comprueba que todos los servicios están disponibles desde el navegador


INSTALACIÓN DE MÓDULOS:

1. Se supone que tenemos ya el fichero LoggingModule.mar y CounterModule.mar que vamos a guardar en la carpeta: _{APACHE_HOME}/webapps/axis2/WEB-INF/modules. Estos ficheros contienen los .class de cada módulo y sus respectivos ficheros module.xml, que le indica al axis2 cuando llamar a las funciones del módulo.

2. Abrimos el fichero localizado en : _{APACHE_HOME}/webapps/axis2/WEB-INF/conf/axis2.xml y lo editamos de tal forma que en las "Phases" (después de las que están predefinidas en el sistema) vamos a añadir las "phases" de nuestros módulos. Es decir, en cada "PhaseOrder" se le indican los módulos que va a implementar el axis2.

3. Los .class que se deben llamar en cada situación (Inflow, OutFlow...) se especifican en el module.xml, el cual describimos en el apartado 1.

4. Una vez movidos los .mar al apartado de "modules", anteriormente descrito, arrancamos el axis si no lo estaba y vamos a administración. Una vez en administración en "Available modules" podemos ver nuestros módulos y la descripción (opcional) que le hayamos escrito. Ahora solo nos queda ir al apartado de "engage module" y escoger el servicio para el que queramos implementar el módulo. Podemos implementarlo para todos si quisiésemos, pero no todos los servicios están adaptados a las funcionalidades que realiza el módulo.
