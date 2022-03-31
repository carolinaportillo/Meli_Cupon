#Cupon Challenge de Mercado Libre.

Api Rest diseñada para realizar el calculo de items favoritos a canjear mediante un cupon que les fue otorgado a los usuarios de meli a modo de beneficio por ser clientes recurrentes de la empresa. Este sistema consume directamente de la api de mercado libre para obtener los precios de cada producto favorito por medio de su ID, el usuario tiene la posibilidad de ingresar la lista de items que ha marcado como favoritos junto con el monto del cupon, una vez procesada y analizada dicha informacion se realiza el calculo de la mejor combinacion posible para el usuario, el cual obtiene como respuesta un Model Response con la lista de items id que puede adquirir con el cupon y el total gastado. 

#Tecnologias utilizadas
JAVA VERSION 11

MAVEN

SPRINGBOOT

#Documentacion de Postman
[Documentacion Cupon Challenge]
(https://documenter.getpostman.com/view/16169975/UVyrTbHq "Documentacion Cupon Challenge")

#Pasos para ejecutar el proyecto

--> Clonar el repositorio con el comando git clone.

--> Ejecutar el codigo a nivel local.

--> El localhost por defecto es el 8080.

--> Post Coupon: http://localhost:8080/coupon

--> Get Item Price By Id: http://localhost:8080/item/{ID
