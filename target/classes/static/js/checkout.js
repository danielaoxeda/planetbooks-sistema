document.addEventListener("DOMContentLoaded", () => {

    // -------------------------
    // ELEMENTOS DOM
    // -------------------------
    const cartItemsModal = document.getElementById("cartItems");
    const cartTotalModal = document.getElementById("cartTotal");

    const checkoutPageItems = document.getElementById("cartPageItems");
    const checkoutPageTotal = document.getElementById("cartPageTotal");

    const checkoutButtons = document.querySelectorAll(".checkout-btn");
    const cartCount = document.getElementById("cartCount");

    // -------------------------
    // CART
    // -------------------------
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    function saveCart() {
        localStorage.setItem("cart", JSON.stringify(cart));
    }

    function updateCartCount() {
        const count = cart.reduce((s, i) => s + i.quantity, 0);

        if (!cartCount) return;

        cartCount.textContent = count;
        cartCount.style.display = count > 0 ? "inline-block" : "none";
    }

    // -------------------------
    // TOTAL
    // -------------------------
    function getTotal() {
        return cart.reduce((s, i) => s + parseFloat(i.price) * i.quantity, 0);
    }

    // -------------------------
    // RENDER GLOBAL (Modal + Cart.html)
    // -------------------------
    function renderCart() {

        // MODAL
        if (cartItemsModal && cartTotalModal) {
            if (cart.length === 0) {
                cartItemsModal.innerHTML = `
                    <tr><td colspan="5" class="text-center">Your cart is empty for now.</td></tr>
                `;
                cartTotalModal.textContent = "0.00";
            } else {
                cartItemsModal.innerHTML = cart.map((item, idx) => {
                    const price = parseFloat(item.price);
                    const total = price * item.quantity;

                    return `
                        <tr>
                            <td>${item.name}</td>
                            <td>
                                <button class="btn btn-sm btn-outline-secondary" data-action="dec" data-index="${idx}">-</button>
                                <span class="mx-2">${item.quantity}</span>
                                <button class="btn btn-sm btn-outline-secondary" data-action="inc" data-index="${idx}">+</button>
                            </td>
                            <td>$${price.toFixed(2)}</td>
                            <td>$${total.toFixed(2)}</td>
                            <td><button class="btn btn-sm btn-danger" data-action="remove" data-index="${idx}">X</button></td>
                        </tr>
                    `;
                }).join("");

                cartTotalModal.textContent = getTotal().toFixed(2);
            }
        }

        // CART PAGE (checkout.html)
        if (checkoutPageItems && checkoutPageTotal) {
            if (cart.length === 0) {
                checkoutPageItems.innerHTML = `
                    <tr><td colspan="5" class="text-center">Your cart is empty.</td></tr>
                `;
                checkoutPageTotal.textContent = "0.00";
            } else {
                checkoutPageItems.innerHTML = cart.map((item, idx) => {
                    const price = parseFloat(item.price);
                    const total = price * item.quantity;

                    return `
                        <tr>
                            <td>${item.name}</td>
                            <td>$${price.toFixed(2)}</td>
                            <td>${item.quantity}</td>
                            <td>$${total.toFixed(2)}</td>
                            <td><button class="btn btn-sm btn-danger" data-action="remove" data-index="${idx}">X</button></td>
                        </tr>
                    `;
                }).join("");

                checkoutPageTotal.textContent = getTotal().toFixed(2);
            }
        }

        updateCartCount();
    }

    // -------------------------
    // AGREGAR PRODUCTOS
    // -------------------------
    document.querySelectorAll(".add-to-cart").forEach(btn => {
        btn.addEventListener("click", () => {
            const name = btn.dataset.name;
            const price = parseFloat(btn.dataset.price);

            const existing = cart.find(i => i.name === name);

            if (existing) existing.quantity++;
            else cart.push({ name, price, quantity: 1 });

            saveCart();
            renderCart();

            window.sonner?.success(`${name} added to cart`);
        });
    });

    // -------------------------
    // BOTONES DEL MODAL
    // -------------------------
    if (cartItemsModal) {
        cartItemsModal.addEventListener("click", e => {
            const index = e.target.dataset.index;
            const action = e.target.dataset.action;

            if (!action) return;

            if (action === "inc") cart[index].quantity++;
            if (action === "dec" && cart[index].quantity > 1) cart[index].quantity--;
            if (action === "remove") cart.splice(index, 1);

            saveCart();
            renderCart();
        });
    }

    // -------------------------
    // BOTONES EN CART.HTML
    // -------------------------
    if (checkoutPageItems) {
        checkoutPageItems.addEventListener("click", e => {
            const index = e.target.dataset.index;
            const action = e.target.dataset.action;

            if (!action) return;

            if (action === "remove") {
                cart.splice(index, 1);
                saveCart();
                renderCart();
                window.sonner?.success("Item removed");
            }
        });
    }

    // -------------------------
    // CHECKOUT (SOLO SESIÓN SPRING)
    // -------------------------
    const userLogged = document.body.dataset.user === "true";
    console.log("[checkout] userLogged:", userLogged, "data-user:", document.body.dataset.user);

    function handleCheckout(event) {
        console.log("[checkout] Click en:", event.currentTarget.id);

        if (!userLogged) {
            window.sonner?.error("Please log in to proceed.");
            window.location.href = "/login";
            return;
        }

        if (cart.length === 0) {
            window.sonner?.error("Your cart is empty!");
            return;
        }

        window.location.href = "/checkout";
    }

    checkoutButtons.forEach(btn =>
        btn.addEventListener("click", handleCheckout)
    );

    // -------------------------
    // INICIALIZAR
    // -------------------------
    renderCart();
});

// PAYPAL BUTTONS
if (document.getElementById("paypal-button-container")) {
    paypal.Buttons({

        createOrder() {
            const total = document.getElementById("cartPageTotal").innerText;

            return fetch("/api/paypal/create-order", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ amount: total })
            })
            .then(res => res.json())
            .then(data => data.id);
        },

        onApprove(data) {
            return fetch("/api/paypal/capture-order", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ orderId: data.orderID })
            })
            .then(res => res.json())
            .then(result => {

                if (result.status === "COMPLETED") {
                    window.sonner.toast.success("Payment successful! Sending your books...");
                    window.location.href = "/checkout/success";
                }
            });
        }

    }).render("#paypal-button-container");
}

