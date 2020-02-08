# Avila RAN v2
GouScriptView-Interface
* Descarga Avila RAN v2 (avilaran-setup.zip)
[AQUI](https://github.com/miguel2m/GouScriptView/blob/master/GouScriptView/AvilaRANv2-dist/AvilaRan-setup.zip)
* Descomprimir avilaran-setup.zip:
  Avila RAN se instala en la carpeta Users/appdata/local (No necesita de permisos de administrador).
* Descarga Avila RAN v2 PORTABLE (avilaran-portable.zip)
[AQUI](https://github.com/miguel2m/GouScriptView/blob/master/GouScriptView/AvilaRANv2-dist/avilaran-portable.zip)
* Descomprimir avilaran-portable.zip: Ejecutar AvilaRan.exe ó GouScriptView.jar si el antivirus bloquea ejecución de archivos .exe
* Características: Descomprime Archivos .gz e interpreta Archivos xml (GEXPORT) además de crear GOU script según lista de NODEB.
* PILA DE ERRORES:

| CODIGO | CLASE | CONCEPTO |
|:------:|------|--------------------------------------------------------------------------------------|
| 200 |SUCCESSFUL| EL SCRIPT DEL NODEB SE CREÓ EXITOSAMENTE |
| 400 |ERROR | EL NODEB ES ATM |
| 401 |WARNING | EN LA TABLA IPRT, EL CAMPO DSTIP (VRF) NO ESTA ASOCIADO AL NODEB |
| 402 |ERROR | EL NODEB NO ESTA CARGADA A LA BD |
| 403 |ERROR | EL NODEB NO SE ENCUENTRA EN LA RNC O LA RNC NO ESTA CARGADA EN LA BD |
| 404 |ERROR| EL NODOB PERTENECE A UN IPPOOL (EL ANI EN LA TABLA IPRT NO PERTENECE A LA RNC) |
| 405 |ERROR| EN LA RNC, EL PUERTO NO POSEE NEXTHOP |
| 500 |ERROR| LA BASE DE DATOS NO ESTA CARGADA CON LOS ARCHIVOS DE LA RNC O NODEB |
| 501 |ERROR| EN EXPORTAR EL ARCHIVO EXCEL |
| 502 |ERROR| EN GENERAL |

* Recuerda: Una vez creado los script, verifica el log del script creado según la RNC correspondiente.
