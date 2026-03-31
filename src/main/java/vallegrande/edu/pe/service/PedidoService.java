package vallegrande.edu.pe.service;

public class PedidoService {

    private static final double DESCUENTO_ADICIONAL = 20.0;
    private static final double UMBRAL_DESCUENTO_ADICIONAL = 500.0;

    /**
     * Calcula el total de un pedido aplicando descuento por tipo de cliente
     * y un descuento adicional si el subtotal supera el umbral definido.
     *
     * @param precio       precio unitario del producto
     * @param cantidad     cantidad de productos
     * @param tipoCliente  tipo de cliente (VIP, REGULAR, OTRO)
     * @return total final con descuentos aplicados
     */
    public double calcularTotal(double precio, int cantidad, TipoCliente tipoCliente) {
        validarParametros(precio, cantidad);
        double subtotal = calcularSubtotal(precio, cantidad);
        double conDescuento = aplicarDescuentoCliente(subtotal, tipoCliente);
        return aplicarDescuentoAdicional(conDescuento);
    }

    private void validarParametros(double precio, int cantidad) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
    }

    private double calcularSubtotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    private double aplicarDescuentoCliente(double subtotal, TipoCliente tipoCliente) {
        return subtotal - (subtotal * tipoCliente.getDescuento());
    }

    private double aplicarDescuentoAdicional(double total) {
        if (total > UMBRAL_DESCUENTO_ADICIONAL) {
            return total - DESCUENTO_ADICIONAL;
        }
        return total;
    }
}
