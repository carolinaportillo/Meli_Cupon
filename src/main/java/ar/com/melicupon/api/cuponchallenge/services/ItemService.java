package ar.com.melicupon.api.cuponchallenge.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service encargado de la logica y manejo de datos que tengan que ver con los
 * items favoritos del usuario en consideracion al canje
 * 
 * @author Carolina Portillo
 */

@Service
public class ItemService {

	Logger logger = LoggerFactory.getLogger(ItemService.class);

	/**
	 * Metodo que se encarga de crear la conexion con la api de meli y realiza la
	 * consulta de precio de un item en especifico,
	 * utilizando como parametro el ID de cada producto
	 * 
	 * @param itemId
	 * @return Float precioItem
	 */

	public Float obtenerPrecioPorItemId(String itemId) {

		try {
			URL meliUrl = new URL("https://api.mercadolibre.com/items/" + itemId); // se instancia un nuevo objeto url y
																					// se le asigna el valor, en este
																					// caso la url de meli

			HttpURLConnection conexion = (HttpURLConnection) meliUrl.openConnection(); // abre la conexion a la api

			conexion.setRequestMethod("GET"); // se especifica que tipo de peticion se va a realizar
			conexion.setRequestProperty("Content-Type", "application/json");
			conexion.setRequestProperty("Accept", "application/json");
			conexion.setRequestProperty("Accept-Charset", "UTF-8");
			conexion.setRequestProperty("Transfer-Encoding", "chunked");
			/*
			 * conexion.setConnectTimeout(5000);
			 * conexion.setReadTimeout(5000);
			 */

			int status = conexion.getResponseCode(); // se obtiene el estado de la conexion y se guarda en status

			if (status != 200) {

				logger.error("No se hallaron resultados de la consulta realizada a:" + " " + meliUrl.getPath());

				return null;
			}

			// se realiza la solicitud y se lee la respuesta de la misma en el Reader.
			BufferedReader bReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			// Esto es lo que vamos a devolver
			StringBuilder resultado = new StringBuilder();
			String linea;

			// mientras el bReader se pueda leer, agregar contenido al resultado
			while ((linea = bReader.readLine()) != null) {
				resultado.append(linea);
			}

			Float precioItem;

			JSONObject result = new JSONObject(resultado.toString());

			precioItem = (Float) result.getFloat("price");

			bReader.close();
			conexion.disconnect();

			return precioItem;

		} catch (MalformedURLException e) {

			logger.error("Se produjo un error en la construccion de la URL" + " " + e.getMessage());

		} catch (IOException e) {

			logger.error("Se produjo un error en la lectura de datos" + " " + e.getMessage());

		}
		return null;
	}

	/**
	 * metodo que calcula la suma total de los precios de cada item en una lista
	 * 
	 * @param itemsId
	 * @return Float con la suma total de precios
	 */

	public Float sumaTotal(List<String> itemsId) {

		Float sumaTotal = (float) 0;

		for (String item : itemsId) {

			Float precioItem = obtenerPrecioPorItemId(item);
			sumaTotal = sumaTotal + precioItem;
		}

		return sumaTotal;
	}

	// plantear un metodo que ordene la lista de itemsFav de menor a mayor precio

	/**
	 * Metodo que ordena los items de una lista segun su valor de manera ascendente
	 * 
	 * @param items
	 * @return
	 */
	public List<Entry<String, Float>> ordenarItemsPorValor(Map<String, Float> items) {

		// creando una lista a partir de elementos de HashMap para poder recorrerla y
		// ordenarla por valor

		List<Map.Entry<String, Float>> itemsListAsc = new ArrayList<Map.Entry<String, Float>>(items.entrySet());

		Collections.sort(itemsListAsc, new Comparator<Map.Entry<String, Float>>() {

			@Override
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		return itemsListAsc;
	}

	/*
	 * aca vuelvo a construir la coleccion map con la lista de items recorrida
	 * // arriba y almaceno el itemId(key)
	 * // con su precio/valor(float)
	 * Map<String, Float> itemsOrdenadosPorValorAsc = new HashMap<String, Float>();
	 * // mi elemento iterador de tipo map(item) recorre la itemList de arriba y los
	 * va
	 * // agregando a
	 * // la nueva lista map en pares de id(key) y precio(float)
	 * y
	 * // se retorna la lista ordenada
	 * for (Map.Entry<String, Float> item : itemsList) {
	 * itemsOrdenadosPorValorAsc.put(item.getKey(), item.getValue());
	 * }
	 * 
	 * return itemsOrdenadosPorValorAsc;
	 */

	/**
	 * Metodo encargado de analizar la mejor opcion para canjear el cupon
	 * 
	 * @param itemId
	 * @param amountCupon
	 * @return
	 */
	public List<String> calcularMejorOpcionCupon(Map<String, Float> itemId, Float amountCupon) {

		List<Entry<String, Float>> itemsPorPrecioAsc = new ArrayList<Map.Entry<String, Float>>();
		List<String> mejorOferta = new ArrayList<String>();
		List<Float> precios = new ArrayList<Float>();

		// aca ya esta todo ordenado de menor a mayor
		itemsPorPrecioAsc = ordenarItemsPorValor(itemId);

		Map<String, Float> itemsOrdenadosPorValorAsc = new HashMap<String, Float>();

		for (Map.Entry<String, Float> item : itemsPorPrecioAsc) {

			itemsOrdenadosPorValorAsc.put(item.getKey(), item.getValue());

		}
		// aca se transforman los datos del map en una lista de ids y otra de precios
		List<String> itemsId = itemsOrdenadosPorValorAsc.keySet().stream().collect(Collectors.toList());
		List<Float> preciosItems = itemsOrdenadosPorValorAsc.values().stream().collect(Collectors.toList());
		Float aux = (float) 0;
		Float aux2 = (float) 0;

		for (int i = 0; i < preciosItems.size(); i++) {

			List<String> listaItems = new ArrayList<String>();
			listaItems.add(itemsId.get(i));
			aux = preciosItems.get(i);

			for (int x = i + 1; x < preciosItems.size(); x++) {
				if ((aux + preciosItems.get(x) <= amountCupon)) {
					aux += preciosItems.get(x);
					listaItems.add(itemsId.get(x));
				} else {

					precios.add(aux);
					break;
				}
				if (aux > aux2) {
					mejorOferta = listaItems;
					aux2 = aux;
				}
			}
		}
		return mejorOferta;

	}

}