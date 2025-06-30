document.addEventListener("DOMContentLoaded", function() {
	const searchInput = document.querySelector(".search_input");
	const members = document.querySelectorAll(".member_list_item");

	searchInput.addEventListener("input", function() {
		const keyword = searchInput.value.toLowerCase();

		members.forEach(member => {
			const name = member.querySelector(".member_name").textContent.toLowerCase();
			if (name.includes(keyword)) {
				member.style.display = "flex";
			} else {
				member.style.display = "none";
			}
		});
	});
});