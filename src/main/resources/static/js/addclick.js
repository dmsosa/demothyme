const tableRows = document.querySelectorAll(".table-clickable tbody tr");

for (const tableRow of tableRows) {
    if (tableRow != null) {
            tableRow.addEventListener("click", function() {
                window.location.href = this.dataset.href;
            });
    }
}


const changebtn = document.querySelector(".change-btn");
changebtn.addEventListener("click", function() {
    console.log("changed");
    document.getElementById("cap").innerText = "HEY";
})