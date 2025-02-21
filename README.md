# Parcial 1 AREP

Para clonar el proyecto 

git clone  ´ https://github.com/lalaro/PARCIAL1AREP.git ´

### Prerrequisitos

Se necesita de Maven (La versión más reciente) y Java 23, la instalación debe realizarse desde las paginas oficiales de cada programa.


### Instalación

Para Maven debe irse a https://maven.apache.org/download.cgi, descargar la versión más nueva que allá de Maven (En este caso tenemos la versión 3.9.6) y agregarse en la carpeta de Program Files, luego se hace la respectiva configuración de variables de entorno según la ubicación que tenemos para el archivo de instalación, tanto de MAVEN_HOME y de Path.
Luego revisamos que haya quedado bien configurado con el comando para Windows:

` mvn - v `
o
` mvn -version `

Para Java debe irse a https://www.oracle.com/java/technologies/downloads/?er=221886, descargar la versión 23 de Java y agregarse en la carpeta de Program Files, luego se hace la respectiva configuración de variables de entorno según la ubicación que tenemos para el archivo de instalación, tanto de JAVA_HOME y de Path.
Luego revisamos que haya quedado bien configurado con el comando para Windows:

` java -version `

Si no tenemos la versión solicitada podemos hacer lo siguiente, para el caso de Windows:

Ir al Windows PowerShell y ejecutar como administrador los siguientes codigos:

` [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-23", [System.EnvironmentVariableTarget]::Machine) `

Revisar las rutas de la máquina

`  $env:JAVA_HOME = "C:\Program Files\Java\jdk-23" `

`  $env:Path = "C:\Program Files\Java\jdk-23\bin;" + $env:Path `

`  echo $env:JAVA_HOME `

`  javac -version `

`  java -version `


Para compilar el proyecto:

´ mvn clean ´

´ mvn package ´

´ mvn compile ´


Para verificar el Servidor del backend:
java -cp "target\classes;target\dependencies\*" edu.escuelaing.app.reflexcalculator.mavenproject1.backend.HttpBackendServer

Para verificar el Servidor del usuario:
java -cp "target\classes;target\dependencies\*" edu.escuelaing.app.reflexcalculator.mavenproject1.frontend.HttpReflexCalculatorServer

El back corre por el puerto:
http://localhost:45000/ghl.html?vl=8&t=5

El front corre por el puerto:
http://localhost:35000/ghl.html?vl=8&t=5


Para verificar el archivo de destino para la caluladora se hace desde el puerto del back:

http://localhost:45000/index.html

## Construido con

* [Maven](https://maven.apache.org/) - Gestión de dependencias.
* [Java](https://www.java.com/es/) - Versionamiento en Java.
* [GitHub](https://docs.github.com/es) - Sistema de control de versiones distribuido.
* [IntelliJ](https://www.jetbrains.com/es-es/idea/) - Entorno de desarrollo integrado.

**Autor**
- Laura Valentina Rodríguez Ortegón **Ing de Sistemas**
