package ar.com.melicupon.api.cuponchallenge.models.response;

    
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model de tipo response que va a contener la lista de items id que podra finalmente canjear el usuario y el total 
 * gastado
 * @author Carolina Portillo
 */
public class CouponResponse {
    
    @JsonProperty(value = "item_ids")
    private List<String> itemsId;

    private Float total;

    public List<String> getItemsId() {
        return itemsId;
    }

    public void setItemsId(List<String> itemsId) {
        this.itemsId = itemsId;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
