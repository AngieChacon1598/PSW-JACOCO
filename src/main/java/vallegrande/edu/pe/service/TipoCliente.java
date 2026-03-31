package vallegrande.edu.pe.service;

public enum TipoCliente {
    VIP(0.20),
    REGULAR(0.10),
    OTRO(0.0);

    private final double descuento;

    TipoCliente(double descuento) {
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }
}
