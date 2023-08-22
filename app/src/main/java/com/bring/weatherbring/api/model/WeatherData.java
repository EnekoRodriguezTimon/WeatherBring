package com.bring.weatherbring.api.model;

import com.bring.weatherbring.api.model.WeatherItem;

import java.util.List;

public class WeatherData {
    private String cod; // Código de respuesta
    private double message; // Mensaje de respuesta
    private int cnt; // Cantidad de elementos en la lista
    private List<WeatherItem> list; // Lista de elementos de pronóstico

    // Getter y Setter para cada atributo

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherItem> getList() {
        return list;
    }

    public void setList(List<WeatherItem> list) {
        this.list = list;
    }
}
