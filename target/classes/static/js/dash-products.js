function loadBookData(btn) {

    document.getElementById("edit-id").value = btn.dataset.id;
    document.getElementById("edit-title").value = btn.dataset.title;
    document.getElementById("edit-publisher").value = btn.dataset.publisher;
    document.getElementById("edit-description").value = btn.dataset.description;
    document.getElementById("edit-exam").value = btn.dataset.exam;
    document.getElementById("edit-level").value = btn.dataset.level;
    document.getElementById("edit-price").value = btn.dataset.price;

    document.getElementById("edit-material_type").value = btn.dataset.material;
    document.getElementById("edit-year").value = btn.dataset.year;
    document.getElementById("edit-format").value = btn.dataset.format;

    // Set form action
    document.getElementById("editProductForm").action =
        "/dash-products/edit/" + btn.dataset.id;

    // Open modal
    new bootstrap.Modal(
        document.getElementById("editProductModal")
    ).show();
}

