Sistema de Cajero Electronico   (C) Guillermo Jimenez 2016.

Este ejemplo muestra la implementacion parcial de un sistema de cajero
electronico en el servidor de aplicaciones Tomcat, accediendo a una base de
datos MySQL. (Los comandos utilizan la sintaxis de MS-Windows.)

Para ponerlo en operacion se requieren varios pasos.
0.  Instalar la version mas reciente de Java, y crear la variable de entorno
      JAVA_HOME   C:...\Java
1.  Instalar la version mas reciente de MySQL
2.  Crear la base de datos 'cajero'
       (se puede utilizar una herramienta como navicat (www.navicat.com) 
3.  Crear la tabla 'cliente', con los campos:
      -usuario: string longitud 20 (llave primaria) 
      -password: string longitud 15
      -cuenta: entero longitud 10
      -nombre: string longitud 40 
4.  Crear la tabla 'cuenta', conteniendo los campos
      -idcuenta: entero longitud 10 (llave primaria)
      -saldo: float longitud 15 y 2 decimales
5.  Agregar por lo menos un registro a las tablas 'cliente' y 'cuenta' que
      tengan el mismo numero de cuenta (cuenta = idcuenta)) 
6.  Instalar la version mas reciente de Tomcat,
      y definir la variable de entorno
      CATALINA_HOME C:\---\Tomcat
7.  Copiar mysql-connector-java-5.1.38-bin.jar dentro de C:\...Tomcat\lib (Se asume que es el conector mas reviente para MySQL-Java)
8.  Agregar a CLASSPATH el archivo C:\...Tomcat\lib\mysql-connector-java-5.1.38-bin.jar
9.  Agregar a CLASSPATH el archivo C:\...Tomcat\lib\servlet-api.jar
10. Agregar a CLASSPATH el directorio C:\...Tomcat\webapps\Cajero\WEB-INF\classes
11. Desempacar el archivo 'cajero.zip' dentro del directorio Tomcat\webapps
12. Compilar utilizando el comando (en una ventana de Command): 
      C:\..\webapps\Cajero\Entidades> javac -d ..\WEB-INF\classes *.java
      C:\..\webapps\Cajero\Controles> javac -d ..\WEB-INF\classes *.java
      C:\..\webapps\Cajero\Interfaces> javac -d ..\WEB-INF\classes *.java
13. Iniciar la ejecucion de Tomcat (C:\...Tomcat\bin\startup.bat).
14. Indicar en el navegador Web el URL:
    http://localhost:8080/Cajero/

NOTAS IMPORTANTES
-Al modificar CLASSPATH asegurarse de dar toda la trayectoria desde C:\...
-Cada vez que hagas cambios a un Servlet debes reiniciar Tomcat.

ASPECTOS DE SEGURIDAD QUE SE INCLUYEN
-El primero es evitar "SQL injection" en Entidades mediante el uso
   de "SQL prepared statements".
-Incluye el uso de Session para no tener que intercambiar datos importantes
   entre Cliente (navegador Web) y el Servidor.
-Tambien se utilizan anotaciones para no tener que especificar en el archivo
   web.xml los detalles de cada Servlet que agerguemos a la aplicacion Web,
   aunque esto realmente no tiene que ver son seguridad, sino tomar ventaja
   de lo que proporciona la ultima version/especificacion de Servlets.
-Se intenta en lo posible eliminar el uso de "import x.*", para solo importar
   lo que realmente es requerido por cada una de las clases de la aplicacion.  

COMO FUNCIONA EL EJEMPLO
-Al iniciar Tomcat se invoca a la clase ContextListener, la cual establece 
   la conexion a la base de datos (via DBConnectionManager) y guarda la conexion en
   ServletContext (accesible a todos los Servlets). Es importante tomar en cuenta
   que ContextListener asume que para la base de datos: user="root" y password="admin". 
-El parametro Connection se envia a todos los metodos de las Entidades, para
   que se haga uso de una sola conexion a la base de datos.
-Los datos de autenticacion de cada acceso por usuarios individuales a la
   aplicacion Web se colocan en Session. Esos datos son destruidos por LogoutServlet
   al terminar la sesion.
-Claramente la arquitectura de la aplicacion viola la regla de comunicacion 
   Interfaz-Interfaz de los diagramas de robustez, pero es la unica manera en
   la que se puede mantener los aspectos de seguridad y una sola conexion
   a la base de datos. Esto ultimo es muy importante, porque MySQL no soporta muchas
   conexiones a un mismo tiempo (tampoco otros DBMS).
