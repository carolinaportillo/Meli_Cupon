package ar.com.melicupon.api.cuponchallenge.models.response;

/**
 * Model de tipo response creado para ortorgar una respuesta a la consulta de precio de un item en especifico,
 * que sera utilizado en el web method de tipo GET situado en el CouponController
 * @author Carolina Portillo
 */
public class InfoItemResponse {

    public String message;
    public boolean isOk;
    public Float itemPrice;
    
}
