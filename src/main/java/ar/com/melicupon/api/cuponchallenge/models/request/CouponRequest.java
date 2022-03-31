package ar.com.melicupon.api.cuponchallenge.models.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model request que se utilizara para enviar la lista de items favs y el monto del cupon otorgado
 * para posteriormente poder realizar el calculo de opciones a canjear por el usuario
 * @author Carolina Portillo
 */

 // se implementa el serializable para poder convertir el objeto en bytes y enviarlo de manera mas consisa
public class CouponRequest implements Serializable{
   
    // este atributo se declara para detectar la version de serializable del otro lado con el que se interactua
    private static final long serialVersionUID = 8799656478674716638L;

    @JsonProperty(value = "item_ids") //para cambiar el nombre cuando lo pongo en el postman
    private List<String> itemsId; //lista de items que van a ir en el post con el cupon

    private Float amount; //monto del cupon a gastar

    public List<String> getItemsId() {
        return itemsId;
    }

    public void setItemsId(List<String> itemsId) {
        this.itemsId = itemsId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
}
