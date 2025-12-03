document.addEventListener('DOMContentLoaded', () => {
    const btnApply   = document.getElementById('btnApplyFilters');
    const tbody      = document.getElementById('topBooksBody');

    btnApply.addEventListener('click', () => {
        const publisher = document.getElementById('filtroPublisher').value;
        const priceText = document.getElementById('filtroPrecio').value;

        let [minPrice, maxPrice] = [null, null];
        if (priceText === '$1 - $2')        { minPrice = 1;  maxPrice = 2; }
        if (priceText === '$3 - $4')        { minPrice = 3;  maxPrice = 4; }
        if (priceText === '$5 and up')      { minPrice = 5;  maxPrice = null; }

        const params = new URLSearchParams();
        if (publisher !== 'All Publishers') params.append('publisher', publisher);
        if (minPrice !== null) params.append('minPrice', minPrice);
        if (maxPrice !== null) params.append('maxPrice', maxPrice);

        fetch(`/reports/api/books?${params}`)
            .then(r => r.json())
            .then(data => {
                tbody.innerHTML = '';
                data.forEach((b, idx) => {
                    const row = tbody.insertRow();
                    row.insertCell(0).innerText = idx + 1;
                    row.insertCell(1).innerText = b.title;
                    row.insertCell(2).innerText = b.exam;
                    row.insertCell(3).innerText = `$${b.price}`;
                    row.insertCell(4).innerText = b.publisher;
                });
            })
            .catch(console.error);
    });

    /* ---------- Exportar Excel ---------- */
    document.getElementById('btnExportTopBooks').addEventListener('click', () => {
        const publisher = document.getElementById('filtroPublisher').value;
        const priceText = document.getElementById('filtroPrecio').value;

        let [minPrice, maxPrice] = [null, null];
        if (priceText === '$1 - $2')   { minPrice = 1;  maxPrice = 2; }
        if (priceText === '$3 - $4')   { minPrice = 3;  maxPrice = 4; }
        if (priceText === '$5 and up') { minPrice = 5;  maxPrice = null; }

        const params = new URLSearchParams();
        if (publisher !== 'All Publishers') params.append('publisher', publisher);
        if (minPrice !== null) params.append('minPrice', minPrice);
        if (maxPrice !== null) params.append('maxPrice', maxPrice);

        window.open(`/reports/api/books/excel?${params.toString()}`, '_blank');
    });
});