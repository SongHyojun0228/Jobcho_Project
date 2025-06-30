document.addEventListener("DOMContentLoaded", () => {
  const inviteBtn = document.querySelector(".invite_btn");
  const checkboxes = document.querySelectorAll(".member_checkbox");
  const countDisplay = document.getElementById("selectedCount");

  updateUI();

  checkboxes.forEach(cb => {
    cb.addEventListener("change", updateUI);
  });

  function updateUI() {
    const checkedCount = document.querySelectorAll(".member_checkbox:checked").length;

    if (checkedCount > 0) {
      inviteBtn.style.backgroundColor = "rgb(6, 195, 115)";
	  inviteBtn.style.color = "rgb(250, 250, 250)";
      inviteBtn.disabled = false;
    } else {
      inviteBtn.style.backgroundColor = "rgb(250, 250, 250)";
	  inviteBtn.style.color = "rgb(228, 228, 228)";
      inviteBtn.disabled = true;
    }

    if (countDisplay) {
      countDisplay.textContent = checkedCount;
    }
  }
});
