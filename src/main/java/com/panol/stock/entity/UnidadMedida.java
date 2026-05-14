package com.panol.stock.entity;

public enum UnidadMedida {

    UNIDAD("Unidad", "Unidades"),
    PLACA("Placa", "Placas"),
    LATA("Lata", "Latas"),
    CAJA("Caja", "Cajas"),
    PACK("Pack", "Packs"),
    BOLSA("Bolsa", "Bolsas"),
    BIDON("Bidón", "Bidones"),
    BALDE("Balde", "Baldes"),
    KG("kg", "kg"),
    GRAMO("gramo", "gramos"),
    LITRO("litro", "litros"),
    ML("ml", "ml"),
    METRO("metro", "metros"),
    CM("cm", "cm");

    private final String singular;
    private final String plural;

    UnidadMedida(String singular, String plural) {
        this.singular = singular;
        this.plural = plural;
    }

    public String getTexto(int cantidad) {
        return cantidad == 1 ? singular : plural;
    }
}
