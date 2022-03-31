package ar.com.melicupon.api.cuponchallenge.services;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.melicupon.api.cuponchallenge.models.request.CouponRequest;
import ar.com.melicupon.api.cuponchallenge.models.response.CouponResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service que se encarga de manejar exclusivamente todo lo que tenga que ver
 * con la logica de negocio y el manejo de datos relacionados al cupon
 * Contiene el calculo final de los items a canjear por el usuario segun el
 * monto del cupon otorgado
 * 
 * @author Carolina Portillo
 */

@Service
public class CouponService {

    @Autowired
    ItemService itemService;

    Logger logger = LoggerFactory.getLogger(CouponService.class);

    /**
     * Metodo que se encarga de leer los datos que provienen del cupon request y
     * realiza la devolucion de la lista de items que puedo canjear
     * con el monto del total gastado
     * 
     * @param cuponRequest
     * @return
     * @throws IOException
     */
    public CouponResponse obtenerMejorOpcionDeItemsParaCupon(CouponRequest cuponRequest) throws IOException {

        CouponResponse respuesta = new CouponResponse();
        Map<String, Float> itemsFavs = new LinkedHashMap<>();

        try {

            Float montoCupon = cuponRequest.getAmount();

            for (String item : cuponRequest.getItemsId()) {

                Float precioItem = itemService.obtenerPrecioPorItemId(item);

                if (precioItem <= montoCupon && precioItem != null) {

                    itemsFavs.put(item, precioItem);
                }

            }

            List<String> itemsFavsACanjear = itemService.calcularMejorOpcionCupon(itemsFavs, cuponRequest.getAmount());

            respuesta.setItemsId(itemsFavsACanjear);
            respuesta.setTotal(itemService.sumaTotal(itemsFavsACanjear));

        } catch (Exception e) {

            logger.error("Se ha producido un error al consultar los recursos:" + " " + e.getMessage());
        }

        return respuesta;
    }

}