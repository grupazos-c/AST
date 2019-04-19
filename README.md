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

1. Compilamos los ficheros .java de los módulos y (si esta hecho en eclipse) guardamos el directorio que está dentro de la carpeta "bin" del proyecto de eclipse en otra carpeta. A continuación escribimos el fichero module.xml, lo metemos en la carpeta "META-INF". Comprimimos el directorio obtenido de la carpeta bin con el META-INF.

2. Le cambiamos la extensión del .zip, .rar, etc resultante del apartado anterior a .mar.

3. Se supone que tenemos ya el fichero LoggingModule.mar y CounterModule.mar, que guardaremos en la carpeta: _{APACHE_HOME}/webapps/axis2/WEB-INF/modules_. Estos ficheros contienen los .class de cada módulo con sus respectivos ficheros module.xml, que le indica al axis2 en que momento de la ejecución y a que método deben llamar.

4. Abrimos el fichero localizado en : _{APACHE_HOME}/webapps/axis2/WEB-INF/conf/axis2.xml_ y lo editamos. De tal forma que en las "Phases" (después de las que están predefinidas en el sistema) vamos a añadir las "phases" de nuestros módulos. Es decir, en cada "PhaseOrder" se le indican los módulos que va a implementar el axis2.

5. Los .class y los métodos que se deben llamar en cada situación (Inflow, OutFlow...) se especifican en el module.xml, el cual describimos en el apartado 1.

6. Una vez movidos los .mar al apartado de "modules", anteriormente descrito, arrancamos el axis si no lo estaba y vamos a administración. Una vez en administración en "Available modules" podemos ver nuestros módulos y la descripción (opcional) que le hayamos escrito. Ahora solo nos queda ir al apartado de "engage module" y escoger el servicio para el que queramos implementar el módulo. Podemos implementarlo para todos si quisiésemos, pero no todos los servicios están adaptados a las funcionalidades que realiza el módulo.

7. Con el CounterModule se generan dos archivos "IncommingMessage.txt" y "OutgoingMessage.txt" en la raíz del directorio de archivos del servidor. Al abrirlos veremos el número de mensajes entrantes y salientes, respectivamente.

INTALACIÓN DE jUDDI:

1. Decomprimir juddi-distro.zip donde querramos guardar nuestro servidor

2. el servidor viene con su propio apache que se ubicará predeterminadamente en el puerto 8080

3. Tras iniciar y ajustar como deseemos el apache, deberemos arrancarlo ejecutando el archivo _juddi-distro-3.3.6/juddi-tomcat-3.3.6/bin/startup.sh_ 

4. Cuando un servicio se dé de alta deberá comunicárselo al servidor UDDI mediante la clase ClienteUDDI. Para el caso de nuestro servicio de Foro solo tenemos que ejecutar el archivo ForoSetUp.java que, junto a la clase ClienteUDDI, dará de alta el servicio en el servidor UDDI. La ejecucion de este archivo tendrá como parámetro de entrada la URL a dicho servicio (p.e http:\\localhost:7162/axis2/services/Foro ó 192.168.43.2:8080/axis2/services/Foro)
