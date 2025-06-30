document.addEventListener("DOMContentLoaded", () => {
	const domainInput = document.querySelector("input[name='workspaceDomain']");
	const submitButton = document.querySelector("button[type='submit']");
	const cautionMsg = document.querySelector(".domain_caution");

	const domainRegex = /^[a-zA-Z0-9-]{5,}$/;

	domainInput.addEventListener("keyup", () => {
		const value = domainInput.value;

		if (domainRegex.test(value)) {
			submitButton.disabled = false;
			cautionMsg.style.color = "rgb(125, 125, 125)"; 
			submitButton.style.backgroundColor = "rgb(6, 195, 115)";
		} else {
			submitButton.disabled = true;
			cautionMsg.style.color = "rgb(169, 69, 65)";
			submitButton.style.backgroundColor = "rgb(169, 169, 169)";
		}
	});
});
