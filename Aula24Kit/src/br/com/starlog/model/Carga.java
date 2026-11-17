package br.com.starlog.model;

public class Carga {

    private final String codigoRastreio;
    private final String categoria;
    private final double peso;
    private final double valorDeclarado;

    public Carga(String codigoRastreio, String categoria, double peso, double valorDeclarado) {
        // Barreira Fail-Fast
        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de rastreio não pode ser nulo ou vazio.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser nula ou vazia.");
        }
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        if (valorDeclarado <= 0) {
            throw new IllegalArgumentException("Valor declarado deve ser maior que zero.");
        }

        this.codigoRastreio = codigoRastreio.trim().toUpperCase();
        this.categoria = categoria.trim().toUpperCase();
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
    }

    public String getCodigoRastreio() { return codigoRastreio; }
    public String getCategoria()      { return categoria; }
    public double getPeso()           { return peso; }
    public double getValorDeclarado() { return valorDeclarado; }

    /**
     * Seguro espacial: 2% do valor declarado.
     * Soma taxa fixa de R$ 150,00 se a carga for PERIGOSA
     * ou se o peso ultrapassar 100 kg.
     */
    public double calcularSeguro() {
        double seguro = valorDeclarado * 0.02;

        boolean perigosa = "PERIGOSA".equalsIgnoreCase(categoria);
        boolean pesada = peso > 100.0;

        if (perigosa || pesada) {
            seguro += 150.0;
        }

        return seguro;
    }
}