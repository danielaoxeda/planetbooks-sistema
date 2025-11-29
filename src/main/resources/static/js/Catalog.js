document.addEventListener("DOMContentLoaded", () => {
  const links = document.querySelectorAll(".category-link");
  const cardsContainer = document.getElementById("book-list");
  const cards = Array.from(cardsContainer.querySelectorAll("div[data-category]"));
  const searchInput = document.getElementById("search-input");
  const sortSelect = document.querySelector(".form-select");

  let activeCategory = "all";

  // --- Función para aplicar filtros (categoría + búsqueda) ---
  function applyFilters() {
    const searchTerm = searchInput.value.toLowerCase();

    cards.forEach(card => {
      const text = card.innerText.toLowerCase();
      const matchesCategory = (activeCategory === "all" || card.dataset.category === activeCategory);
      const matchesSearch = text.includes(searchTerm);

      if (matchesCategory && matchesSearch) {
        card.style.display = "block";
      } else {
        card.style.display = "none";
      }
    });
  }

  // --- Función para ordenar ---
  function applySorting() {
    const sortValue = sortSelect.value;

    let visibleCards = cards.filter(card => card.style.display !== "none");

    visibleCards.sort((a, b) => {
      const priceA = parseFloat(a.querySelector(".price-current")?.innerText.replace("$", "").trim() || 0);
      const priceB = parseFloat(b.querySelector(".price-current")?.innerText.replace("$", "").trim() || 0);

      switch (sortValue) {
        case "lower-price":
          return priceA - priceB;
        case "highest-price":
          return priceB - priceA;
        case "best-rated":
          // Sin rating aún, lo deja fijo
          return 0;
        case "featured":
        default:
          return 0;
      }
    });

    // Reordenar en el DOM solo las visibles
    visibleCards.forEach(card => cardsContainer.appendChild(card));
  }

  // --- Eventos ---
  links.forEach(link => {
    link.addEventListener("click", e => {
      e.preventDefault();
      activeCategory = link.getAttribute("data-category");

      links.forEach(l => l.classList.remove("active"));
      link.classList.add("active");

      applyFilters();
      applySorting();
    });
  });

  searchInput.addEventListener("input", () => {
    applyFilters();
    applySorting();
  });

  sortSelect.addEventListener("change", () => {
    applyFilters();
    applySorting();
  });

  // Inicial
  applyFilters();
  applySorting();
});


// -- CARRITO --
const cartItems = [];
const cartItemsContainer = document.getElementById("cartItems");
const cartTotal = document.getElementById("cartTotal");
const cartCount = document.getElementById("cartCount");

// Escuchar clicks en los botones de "Add to Cart"
document.querySelectorAll(".add-to-cart").forEach(btn => {
  btn.addEventListener("click", () => {
    const name = btn.getAttribute("data-name");
    const price = parseFloat(btn.getAttribute("data-price"));

    const item = cartItems.find(p => p.name === name);
    if (item) {
      item.quantity++;
    } else {
      cartItems.push({ name, price, quantity: 1 });
    }

    renderCart();
  });
});

function renderCart() {
  cartItemsContainer.innerHTML = "";
  let total = 0;
  let itemCount = 0;

  cartItems.forEach((item, index) => {
    const itemTotal = item.price * item.quantity;
    total += itemTotal;
    itemCount += item.quantity;

    cartItemsContainer.innerHTML += `
      <tr>
        <td>${item.name}</td>
        <td>${item.quantity}</td>
        <td>$ ${item.price.toFixed(2)}</td>
        <td>$ ${itemTotal.toFixed(2)}</td>
        <td>
          <button class="btn btn-sm btn-danger" onclick="removeItem(${index})">Remove</button>
        </td>
      </tr>
    `;
  });

  if (cartItems.length === 0) {
    cartItemsContainer.innerHTML = `
      <tr>
        <td colspan="5" class="text-center">Your cart is empty for now.</td>
      </tr>`;
  }

  cartTotal.textContent = total.toFixed(2);
  cartCount.textContent = itemCount;

  //  Ocultar el badge si no hay productos
  cartCount.style.display = itemCount > 0 ? "inline-block" : "none";

  // Reinicializar íconos Lucide si se buguean
  if (window.lucide) {
    lucide.createIcons();
  }
}

function removeItem(index) {
  cartItems.splice(index, 1);
  renderCart();
}

// Al inicio: ocultar el contador
cartCount.style.display = "none";


