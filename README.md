# Avila RAN v2
GouScriptView-Interface
* Download Avila RAN v2 (avilaran-setup.zip)
[AQUI](https://github.com/miguel2m/GouScriptView/blob/master/GouScriptView/AvilaRANv2-dist/AvilaRan-setup.zip)
* Descomprimir avilaran-setup.zip:
  Avila RAN se instala en la carpeta Users/appdata/local (No necesita de permisos de administrador)
* Caracteristicas: Descomprime Archivos .gz e interpreta Archivos xml (GEXPORT) ademas de crear GOU script según lista de NODEB.
* PILA DE ERRORES:

| CODIGO | CONCEPTO |
|:------:|--------------------------------------------------------------------------------------|
| 200 | EL SCRIPT DEL NODEB SE CREÓ EXITOSAMENTE |
| 400 | ERROR EL NODEB ES ATM |
| 401 | ERROR EN LA TABLA IPRT, EL CAMPO DSTIP (VRF) NO ESTA ASOCIADO AL NODOB |
| 402 | ERROR EL NODEB NO ESTA CARGADA A LA BD |
| 403 | ERROR EL NODEB NO SE ENCUENTRA EN LA RNC O la RNC NO ESTA CARGADA EN LA BD |
| 404 | ERROR EL NODOB PERTENECE A UN IPPOOL (EL ANI EN LA TABLA IPRT NO PERTENECE A LA RNC) |
| 405 | ERROR EN LA RNC EL PUERTO NO POSEE NEXTHOP |
| 500 | LA BASE DE DATOS NO ESTA CARGADA CON LOS ARCHIVOS DE LA RNC O NODEB |
| 501 | ERROR EN EXPORTAR EL ARCHIVO EXCEL |
| 502 | ERROR EN GENERAL |

* Recuerda: Una vez creado los script correspondientes, echa un vistaso al log del script creado segun la RNC correspondiente.
