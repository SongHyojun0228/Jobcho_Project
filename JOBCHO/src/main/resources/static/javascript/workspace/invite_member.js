document.addEventListener("DOMContentLoaded", () => {

	const inputEmail = document.querySelector(".input_email");
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	const emailCaution = document.querySelector(".email_caution");
	let emailPass = false;

	inputEmail.addEventListener("input", () => {
		if (emailRegex.test(inputEmail.value)) {
			emailCaution.style.display = 'none';
			emailPass = true;
		} else {
			emailCaution.style.display = 'block';
			emailCaution.style.color = 'rgb(169, 69, 65)';
			emailPass = false;
		}
		checkFormValid();
	});

	const sendBtn = document.querySelector(".send_btn");

	const checkFormValid = () => {
		if (emailPass) {
			sendBtn.style.backgroundColor = "rgb(6, 195, 115)";
			sendBtn.style.fontWeight = "500";
		} else {
			sendBtn.style.backgroundColor = "rgb(151, 231, 198)";
		}
	};

	inputEmail.addEventListener("input", checkFormValid);
	
});