package backend;

import java.util.List;

public class Item {
    private String pregunta; // "seleccion_mltiple" o "verdadero_falso"
    private List<String> opciones;
    private String respuestaCorrecta;
    private String tipo;
    private String nivelBloom;
    private int tiempoEstimado;
    private String respuestaUsuario;
    private String enunciado;

    public void setEnunciado(String enunciado) {
    this.enunciado = enunciado;
}
    public String getPregunta() { return pregunta; }
    public List<String> getOpciones() { return opciones; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public String getTipo() { return tipo; }
    public String getNivelBloom() { return nivelBloom; }
    public int getTiempoEstimado() { return tiempoEstimado; }

    public String getRespuestaUsuario() { return respuestaUsuario; }
    public void setRespuestaUsuario(String r) { this.respuestaUsuario = r; }
    public String getEnunciado() {return enunciado; }
    
}

