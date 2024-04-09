const tableRows = document.querySelector(".clickable-table tbody tr");
for (const tableRow of tableRows) {
    tableRow.addEventListener("click", function() {
        window.location.href = this.dataset.href;
    });
};