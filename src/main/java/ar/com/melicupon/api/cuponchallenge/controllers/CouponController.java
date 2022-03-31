package ar.com.melicupon.api.cuponchallenge.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.melicupon.api.cuponchallenge.models.request.CouponRequest;
import ar.com.melicupon.api.cuponchallenge.models.response.CouponResponse;
import ar.com.melicupon.api.cuponchallenge.models.response.InfoItemResponse;
import ar.com.melicupon.api.cuponchallenge.services.CouponService;
import ar.com.melicupon.api.cuponchallenge.services.ItemService;

@RestController
public class CouponController {
    
    @Autowired
    CouponService cuponService;

    @Autowired
    ItemService itemService;
    
    
    //web method que realiza el calculo de la mejor opcion de canje para un cupon otorgado al cliente
    @PostMapping("/coupon")
    public ResponseEntity<CouponResponse> postCoupon(@RequestBody() CouponRequest couponRequest) throws IOException{
        
        CouponResponse respuesta = new CouponResponse();
        respuesta = cuponService.obtenerMejorOpcionDeItemsParaCupon(couponRequest);
    
        return ResponseEntity.ok(respuesta);
    }

    
    //web method para chequear que el metodo que le pega a la api de meli y trae los precios funciona correctamente
    //se ingresa el item id de un producto real de meli y nos devuelve su precio
    @GetMapping("/item/{itemId}")
    public ResponseEntity<InfoItemResponse> getItemInfo(@PathVariable String itemId){

        Float precioItem = itemService.obtenerPrecioPorItemId(itemId);

        InfoItemResponse respuesta = new InfoItemResponse();
        respuesta.message = "El precio fue consultado con exito";
        respuesta.itemPrice = precioItem;
        respuesta.isOk = true;
        
        return ResponseEntity.ok(respuesta);
        
    }
    
}