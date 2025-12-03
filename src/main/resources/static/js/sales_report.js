// src/main/resources/static/js/sales_report.js

// ***************************************************************
// FUNCIONES EN ÁMBITO GLOBAL PARA QUE SEAN ACCESIBLES
// ***************************************************************

/**
 * Función principal que recolecta filtros y realiza la llamada AJAX al backend.
 * Devuelve los datos JSON para la tabla.
 */
function generateReport() {
    // 1. Recolección de valores de filtro (aunque los campos no se usen para filtrar la tabla, 
    // se envían para mantener el endpoint flexible, pero la tabla solo los usa al inicio)
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const categorySelect = document.getElementById('category');

    const startDate = startDateInput ? startDateInput.value : '';
    const endDate = endDateInput ? endDateInput.value : '';
    const category = categorySelect ? categorySelect.value : '';

    // 2. Construcción de la URL (si los filtros están vacíos, el Servicio Java usará el defecto de 30 días)
    const params = new URLSearchParams();
    if (startDate) params.append('startDate', startDate);
    if (endDate) params.append('endDate', endDate);
    if (category && category !== 'All...') params.append('category', category);

    // URL del endpoint REST: /reports/transaction-data
    const url = `/reports/transaction-data?${params.toString()}`;

    console.log("Fetching sales data for initial load from: " + url);

    // 3. Llamada Asíncrona (Fetch API)
    fetch(url)
        .then(response => {
            if (!response.ok) {
                // Si hay un error 404/500, se lanza el error para ir al .catch
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // Parsea la lista de SaleTransactionRowDTO a JSON
        })
        .then(data => {
            // 4. Llama a la función para dibujar la tabla
            updateTransactionsTable(data);
            console.log(`Successfully loaded ${data.length} recent transactions.`);
        })
        .catch(error => {
            console.error('CRITICAL ERROR fetching initial sales data:', error);
            alert('Failed to load initial transactions. Please check server logs and network console.');
        });
}

/**
 * Dibuja las filas de la tabla "Recent Transactions" usando el array JSON.
 * @param {Array<Object>} sales - Array de objetos SaleTransactionRowDTO
 */
function updateTransactionsTable(sales) {
    const tableBody = document.getElementById('recentSalesTableBody');
    if (!tableBody) return;

    // Limpia las filas existentes (incluida cualquier fila vacía de Thymeleaf)
    tableBody.innerHTML = '';

    sales.forEach(sale => {
        const row = tableBody.insertRow();

        // Columna 1: Transaction ID
        row.insertCell(0).innerText = sale.transactionId;

        // Columna 2: Customer Name
        row.insertCell(1).innerText = sale.customerName;

        // Columna 3: Book Title
        row.insertCell(2).innerText = sale.bookTitle;

        // Columna 4: Transaction Amount (Formato de moneda)
        // Se asume que sale.transactionAmount es un número (Double)
        const amountFormatted = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(sale.transactionAmount);
        row.insertCell(3).innerText = amountFormatted;

        // Columna 5: Sale Date (LocalDate)
        // Spring convierte LocalDate a una cadena YYYY-MM-DD
        row.insertCell(4).innerText = sale.transactionDate;

        // Columna 6: Payment Status (Con estilo de badge)
        const statusCell = row.insertCell(5);
        const badge = document.createElement('span');
        // Usamos el campo statusBadgeColor enviado desde el DTO para el estilo CSS (ej. text-bg-success)
        badge.className = `badge ${sale.statusBadgeColor}`;
        badge.innerText = sale.paymentStatus;
        statusCell.appendChild(badge);
    });
}


// --- Event Listener Setup (Se ejecuta cuando el HTML está listo) ---
document.addEventListener('DOMContentLoaded', () => {
    // **ACCION CRÍTICA:** Ejecuta la carga de datos inmediatamente al cargar la página.
    generateReport();

    /* =====  NUEVO: descarga del Excel  ===== */
const excelBtn = document.querySelector('.btn-primary.rounded-pill');
if (excelBtn) {
    excelBtn.addEventListener('click', () => {
        console.log('1. Botón pulsado');
        const start = document.getElementById('startDate').value; // 2025-11-03
        const end   = document.getElementById('endDate').value;   // 2025-12-02
        const cat   = document.getElementById('category').selectedOptions[0].text;
        console.log('2. fechas:', start, end);
        if (!start || !end) {
            alert('Please pick start and end dates');
            return;
        }
        console.log('3. pasó validación');
        // Sin split: ya están en ISO
        const url = `/reports/sales/excel?startDate=${start}&endDate=${end}&category=${cat}`;
        console.log('4. URL:', url);
        window.open(url, '_blank');
    });
}
});