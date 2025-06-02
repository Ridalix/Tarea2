package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;


public class TestManager {
    private List<Item> items;

    public TestManager() {
        items = new ArrayList<>();
    }
    
    
    public void cargarItemsDesdeArchivo(File file) {
    items.clear();
    try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
        Gson gson = new Gson();
        Type itemListType = new TypeToken<List<Item>>(){}.getType();
        items = gson.fromJson(reader, itemListType);

        if (items == null) {
            throw new RuntimeException("no ítems válidos.");
        }
    } catch (IOException e) {
        throw new RuntimeException("Error al leer: " + e.getMessage());
    }
    }

        public List<Item> getItems() {
            return items;
        }

    public void setRespuesta(int index, String texto) {
        if (index >= 0 && index < items.size()) {
            items.get(index).setRespuestaUsuario(texto);
        }
    }

    public List<Item> getRespuestas() {
        return new ArrayList<>(items);
    }

    public String calcularTiempoTotal() {
        int totalMin = items.stream().mapToInt(Item::getTiempoEstimado).sum();
        return String.valueOf(totalMin);
    }


    public void cargarItems(List<Item> items2) { }
    public int getCantidadItems() {
     return items.size();
    }

    public Item getItem(int index) {
        return items.get(index);
    }


    public void guardarRespuestasEnArchivo(File archivo) {
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8);) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(items, writer);
    } catch (IOException e) {
        throw new RuntimeException("Error al guardar las respuestas: " + e.getMessage());
    }
    }

    public Map<String, Double> calcularPorcentajePorNivel() {
    Map<String, Integer> correctosPorNivel = new HashMap<>();
    Map<String, Integer> totalesPorNivel = new HashMap<>();

    for (Item item : items) {
        String nivel = item.getNivelBloom();
        totalesPorNivel.put(nivel, totalesPorNivel.getOrDefault(nivel, 0) + 1);
        if (item.getRespuestaCorrecta().equalsIgnoreCase(item.getRespuestaUsuario())) {
            correctosPorNivel.put(nivel, correctosPorNivel.getOrDefault(nivel, 0) + 1);
        }
    }

    Map<String, Double> porcentaje = new HashMap<>();
    for (String nivel : totalesPorNivel.keySet()) {
        int correctos = correctosPorNivel.getOrDefault(nivel, 0);
        int total = totalesPorNivel.get(nivel);
        porcentaje.put(nivel, 100.0 * correctos / total);
    }
    return porcentaje;
}

public Map<String, Double> calcularPorcentajePorTipo() {
    Map<String, Integer> correctosPorTipo = new HashMap<>();
    Map<String, Integer> totalesPorTipo = new HashMap<>();

    for (Item item : items) {
        String tipo = item.getTipo();
        totalesPorTipo.put(tipo, totalesPorTipo.getOrDefault(tipo, 0) + 1);
        if (item.getRespuestaCorrecta().equalsIgnoreCase(item.getRespuestaUsuario())) {
            correctosPorTipo.put(tipo, correctosPorTipo.getOrDefault(tipo, 0) + 1);
        }
    }

    Map<String, Double> porcentaje = new HashMap<>();
    for (String tipo : totalesPorTipo.keySet()) {
        int correctos = correctosPorTipo.getOrDefault(tipo, 0);
        int total = totalesPorTipo.get(tipo);
        porcentaje.put(tipo, 100.0 * correctos / total);
    }
    return porcentaje;
}

}

