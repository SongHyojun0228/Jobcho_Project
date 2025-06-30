document.addEventListener("DOMContentLoaded", () => {
	const inputEmail = document.querySelector(".input_email");
	const inputPw = document.querySelector(".input_password");
	const loginBtn = document.querySelector(".login_btn");

	console.log(inputEmail.value + ", " + inputPw.value);

	// 🌿 로그인 시 모든 양식이 채워져 있는 지 여부
	const logincheckFormValid = () => {
		if (inputEmail.value.length > 0 && inputPw.value.length > 0) {
			console.log(inputEmail.value + ", " + inputPw.value);
			loginBtn.style.backgroundColor = "rgb(6, 195, 115)";
			loginBtn.style.fontWeight = "700";
		} else {
			loginBtn.style.backgroundColor = "rgb(151, 231, 198)";
		}
	};

	inputEmail.addEventListener("input", logincheckFormValid);
	inputPw.addEventListener("input", logincheckFormValid);
});