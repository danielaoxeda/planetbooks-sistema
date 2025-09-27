function updatePeruTime() {
    const optionsDate = { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' };
    const optionsTime = { hour: '2-digit', minute: '2-digit', second: '2-digit' };

    // Hora de Lima - Perú (GMT-5)
    const peruTime = new Date().toLocaleString("en-US", { timeZone: "America/Lima" });

    const dateObj = new Date(peruTime);
    document.getElementById("peru-date").innerText = dateObj.toLocaleDateString("en-US", optionsDate);
    document.getElementById("peru-time").innerText = dateObj.toLocaleTimeString("en-US", optionsTime);
}

setInterval(updatePeruTime, 1000);
updatePeruTime();
lucide.createIcons();
