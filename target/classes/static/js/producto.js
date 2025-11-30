document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.querySelector('input[type="search"]');
  const searchForm = document.querySelector('form[role="search"]');
  const cards = Array.from(document.querySelectorAll(".card.book-card"));

  if (!searchInput) {
    console.error("Busqueda: no se encontró el input de búsqueda (input[type='search']).");
    return;
  }
  if (cards.length === 0) {
    console.warn("Busqueda: no se encontraron tarjetas .card.book-card. Comprueba las clases en tu HTML.");
  }

  function applyFilter() {
    const searchText = searchInput.value.toLowerCase().trim();
    let anyVisible = false;

    cards.forEach(card => {
      const title = card.querySelector(".card-title")?.textContent.toLowerCase() || "";
      const category = card.querySelector(".badge.text-bg-secondary")?.textContent.toLowerCase() || "";
      const publisher = card.querySelector(".text-muted")?.textContent.toLowerCase() || "";
      const description = card.querySelector(".card-text")?.textContent.toLowerCase() || "";

      const match = searchText === "" ||
                    title.includes(searchText) ||
                    category.includes(searchText) ||
                    publisher.includes(searchText) ||
                    description.includes(searchText);

      // la columna contenedora (para mantener el grid) — busca el ancestro con clases de columna
      const col = card.closest(".col-sm-6, .col-lg-4, .col-xl-3, [class*='col-']");

      if (match) {
        if (col) col.style.display = "";
        else card.style.display = "";
        anyVisible = true;
      } else {
        if (col) col.style.display = "none";
        else card.style.display = "none";
      }
    });

    // Mensaje si no hay resultados — crea/oculta
    let noResults = document.getElementById("no-results-msg");
    if (!noResults) {
      noResults = document.createElement("div");
      noResults.id = "no-results-msg";
      noResults.className = "alert alert-warning mt-3";
      noResults.style.display = "none";
      noResults.textContent = "No se encontraron productos.";
      // lo insertamos justo después del contenedor de cards
      const grid = document.querySelector(".row.row-cols-1");
      if (grid) grid.parentNode.insertBefore(noResults, grid.nextSibling);
    }
    noResults.style.display = anyVisible ? "none" : "";
  }

  // búsqueda en vivo
  searchInput.addEventListener("input", applyFilter);

  // evitar submit que recarga la página; para usuarios que prefieren botón "Search"
  if (searchForm) {
    searchForm.addEventListener("submit", function (e) {
      e.preventDefault();
      applyFilter();
    });
  }

  // filtro inicial (por si hay texto prellenado)
  applyFilter();

  console.log("Busqueda: script cargado. Tarjetas encontradas:", cards.length);
})
