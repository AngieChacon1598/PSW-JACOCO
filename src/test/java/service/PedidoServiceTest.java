package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vallegrande.edu.pe.service.PedidoService;
import vallegrande.edu.pe.service.TipoCliente;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

    private PedidoService service;

    @BeforeEach
    void setUp() {
        service = new PedidoService();
    }

    // ─── Descuentos por tipo de cliente ───────────────────────────────────────

    @Test
    void testClienteVIP_sinDescuentoAdicional() {
        // 100 * 2 = 200 → 20% descuento → 160 (no supera 500)
        double total = service.calcularTotal(100, 2, TipoCliente.VIP);
        assertEquals(160.0, total);
    }

    @Test
    void testClienteRegular_sinDescuentoAdicional() {
        // 100 * 2 = 200 → 10% descuento → 180 (no supera 500)
        double total = service.calcularTotal(100, 2, TipoCliente.REGULAR);
        assertEquals(180.0, total);
    }

    @Test
    void testClienteOtro_sinDescuento() {
        // 100 * 2 = 200 → sin descuento → 200
        double total = service.calcularTotal(100, 2, TipoCliente.OTRO);
        assertEquals(200.0, total);
    }

    // ─── Descuento adicional por superar umbral ────────────────────────────────

    @Test
    void testClienteRegular_conDescuentoAdicional() {
        // 100 * 6 = 600 → 10% descuento → 540 → supera 500 → 520
        double total = service.calcularTotal(100, 6, TipoCliente.REGULAR);
        assertEquals(520.0, total);
    }

    @Test
    void testClienteVIP_conDescuentoAdicional() {
        // 100 * 8 = 800 → 20% descuento → 640 → supera 500 → 620
        double total = service.calcularTotal(100, 8, TipoCliente.VIP);
        assertEquals(620.0, total);
    }

    @Test
    void testClienteOtro_conDescuentoAdicional() {
        // 100 * 6 = 600 → sin descuento → 600 → supera 500 → 580
        double total = service.calcularTotal(100, 6, TipoCliente.OTRO);
        assertEquals(580.0, total);
    }

    // ─── Casos límite (boundary) ───────────────────────────────────────────────

    @Test
    void testTotalExactoEnUmbral_noAplicaDescuentoAdicional() {
        // OTRO: precio=100, cantidad=5 → 500 exacto → NO supera 500 → 500
        double total = service.calcularTotal(100, 5, TipoCliente.OTRO);
        assertEquals(500.0, total);
    }

    @Test
    void testTotalJusteSobreUmbral_aplicaDescuentoAdicional() {
        // OTRO: precio=101, cantidad=5 → 505 → supera 500 → 485
        double total = service.calcularTotal(101, 5, TipoCliente.OTRO);
        assertEquals(485.0, total);
    }

    @Test
    void testPrecioEnCero() {
        // precio 0 es válido (producto gratuito)
        double total = service.calcularTotal(0, 5, TipoCliente.VIP);
        assertEquals(0.0, total);
    }

    @Test
    void testCantidadUno() {
        // cantidad mínima válida
        double total = service.calcularTotal(50, 1, TipoCliente.REGULAR);
        assertEquals(45.0, total);
    }

    // ─── Validaciones / excepciones ───────────────────────────────────────────

    @Test
    void testPrecioNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calcularTotal(-10, 2, TipoCliente.VIP));
    }

    @Test
    void testCantidadCero_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calcularTotal(100, 0, TipoCliente.VIP));
    }

    @Test
    void testCantidadNegativa_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calcularTotal(100, -3, TipoCliente.REGULAR));
    }
}
