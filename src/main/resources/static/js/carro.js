// ===========================
// 1. Utilidades de tarjeta
// ===========================

// Formatea el número de tarjeta como "1234 5678 9012 3456"
function formatCardNumber(input) {
    let value = input.value.replace(/\D/g, ''); // Solo dígitos

    if (value.length > 16) {
        value = value.slice(0, 16);
    }

    let formatted = '';
    for (let i = 0; i < value.length; i++) {
        if (i > 0 && i % 4 === 0) {
            formatted += ' ';
        }
        formatted += value[i];
    }

    input.value = formatted;
}

// Valida si el número de tarjeta tiene 16 dígitos
function validateCardInput(input) {
    let value = input.value.replace(/[^0-9\s]/g, '');
    input.value = value;

    const digitsOnly = value.replace(/\s/g, '');
    if (digitsOnly.length === 16) {
        input.setCustomValidity('');
    } else {
        input.setCustomValidity('El número de tarjeta debe tener 16 dígitos');
    }
}

// ===========================
// 2. Mostrar/Ocultar sección de tarjeta
// ===========================

function toggleTarjetaInputs(show) {
    const tarjetaSection = document.getElementById('tarjeta');
    const tarjetaInput = document.getElementById('tarjeta-number');
    const codigoSeguridad = document.getElementById('codigo-seguridad');

    tarjetaSection.style.display = show ? 'block' : 'none';
    tarjetaInput.required = show;
    codigoSeguridad.required = show;
}

// ===========================
// 3. Validar selects de mes y año antes de enviar
// ===========================

function validarFechaTarjeta(form) {
    const mesSelect = form.querySelector('select[name="mes"]');
    const anioSelect = form.querySelector('select[name="anio"]');

	if (mesSelect.selectedIndex === 0 || anioSelect.selectedIndex === 0) {
	        Swal.fire({
	            icon: 'info',
	            title: 'Información incompleta',
	            text: 'Por favor selecciona un mes y un año válidos para la tarjeta.',
	            confirmButtonText: 'Entendido'
	        });
	        return false;
	    }
    return true;
}

// ===========================
// 4. Botón para procesar compra
// ===========================

function manejarProcesarCompra() {
    const btn = document.getElementById('btnProcesarCompra');
    const estaLogueado = btn.getAttribute('data-logueado') === 'true';

    if (!estaLogueado) {
        const alertMsg = encodeURIComponent('¡Ups! Necesitas iniciar sesión para completar tu compra. Por favor, ingresa para continuar.');
        window.location.href = '/login?alert=' + alertMsg;
    } else {
        const modalDelivery = new bootstrap.Modal(document.getElementById('modalDelivery'));
        modalDelivery.show();
    }
}

// ===========================
// 5. Inicialización general
// ===========================

document.addEventListener('DOMContentLoaded', function () {
    const pagoTarjeta = document.getElementById('pagoTarjeta');
    const pagoEfectivo = document.getElementById('pagoEfectivo');
    const form = document.getElementById('formDelivery');

    // Eventos para mostrar/ocultar campos de tarjeta
    pagoTarjeta.addEventListener('change', () => toggleTarjetaInputs(true));
    pagoEfectivo.addEventListener('change', () => toggleTarjetaInputs(false));

    // Estado inicial de tarjeta
    toggleTarjetaInputs(pagoTarjeta.checked);

    // Validación al enviar formulario
    form.addEventListener('submit', function (e) {
        if (pagoTarjeta.checked && !validarFechaTarjeta(form)) {
            e.preventDefault(); // Bloquear envío
        }
    });

    // Evento para mostrar modal o redirigir a login
    document.getElementById('btnProcesarCompra')
        .addEventListener('click', manejarProcesarCompra);
});
