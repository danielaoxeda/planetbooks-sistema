function generateReport() {

    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const categorySelect = document.getElementById('category');

    const startDate = startDateInput ? startDateInput.value : '';
    const endDate = endDateInput ? endDateInput.value : '';
    const category = categorySelect ? categorySelect.value : '';

    const params = new URLSearchParams();
    if (startDate) params.append('startDate', startDate);
    if (endDate) params.append('endDate', endDate);
    if (category && category !== 'All...') params.append('category', category);

    const url = `/reports/transaction-data?${params.toString()}`;

    console.log("Fetching sales data for initial load from: " + url);

    fetch(url)
        .then(response => {
            if (!response.ok) {

                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); 
        })
        .then(data => {

            updateTransactionsTable(data);
            console.log(`Successfully loaded ${data.length} recent transactions.`);
        })
        .catch(error => {
            console.error('CRITICAL ERROR fetching initial sales data:', error);
            alert('Failed to load initial transactions. Please check server logs and network console.');
        });
}

function updateTransactionsTable(sales) {
    const tableBody = document.getElementById('recentSalesTableBody');
    if (!tableBody) return;

    tableBody.innerHTML = '';

    sales.forEach(sale => {
        const row = tableBody.insertRow();

        row.insertCell(0).innerText = sale.transactionId;

        row.insertCell(1).innerText = sale.customerName;

        row.insertCell(2).innerText = sale.bookTitle;

        const amountFormatted = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(sale.transactionAmount);
        row.insertCell(3).innerText = amountFormatted;

        row.insertCell(4).innerText = sale.transactionDate;

        const statusCell = row.insertCell(5);
        const badge = document.createElement('span');

        badge.className = `badge bg-light text-dark`;
        badge.innerText = sale.paymentStatus;
        statusCell.appendChild(badge);
    });
}


document.addEventListener('DOMContentLoaded', () => {

    generateReport();

    const excelBtn = document.querySelector('.btn-primary.rounded-pill');
    if (excelBtn) {
        excelBtn.addEventListener('click', () => {
            console.log('1. Botón pulsado');
            const start = document.getElementById('startDate').value;
            const end = document.getElementById('endDate').value;
            const cat = document.getElementById('category').selectedOptions[0].text;
            console.log('2. fechas:', start, end);
            if (!start || !end) {
                alert('Please pick start and end dates');
                return;
            }
            console.log('3. pasó validación');
            const url = `/reports/sales/excel?startDate=${start}&endDate=${end}&category=${cat}`;
            console.log('4. URL:', url);
            window.open(url, '_blank');
        });
    }
});