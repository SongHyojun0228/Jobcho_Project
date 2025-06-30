document.addEventListener("DOMContentLoaded", () => {

	// 🌿 이메일 입력에서 이메일 형식 여부 검사
	const inputEmail = document.querySelector(".email_input");
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
		joincheckFormValid();
	});

	// 🌿 비밀번호 양식 검사
	const inputPassword = document.querySelector(".input_password");
	const passwordRegexCaution = document.querySelector(".password_regex_caution");
	const pwRegex = /^(?=.*[a-zA-Z])(?=.*[\W_n]).{8,}$/;
	let pwRegexPass = false;

	inputPassword.addEventListener("input", () => {
		if (pwRegex.test(inputPassword.value)) {
			pwRegexPass = true;
			passwordRegexCaution.style.color = 'black';
		} else {
			passwordRegexCaution.style.color = 'rgb(169, 69, 65)';
			pwRegexPass = false;
		}
		joincheckFormValid();
	});

	// 🌿 비밀번호 재확인 일치 여부 
	const inputCheckedPassword = document.querySelector(".input_checked_password");
	const passwordCaution = document.querySelector(".password_caution");
	let passwordPass = false;

	inputCheckedPassword.addEventListener("input", () => {
		if (inputPassword.value === inputCheckedPassword.value) {
			passwordCaution.style.display = 'none';
			passwordPass = true;
		} else {
			passwordCaution.style.display = 'block';
			passwordCaution.style.color = 'rgb(255, 53, 53)';
			passwordPass = false;
		}
		joincheckFormValid();
	});

	// 🌿 모든 양식이 채워져 있고, 모든 유효성 검사를 통과했을 경우
	// : btnColor 변경
	const nextBtn = document.querySelector(".next_btn");
	const inputName = document.querySelector(".input_name");

	const joincheckFormValid = () => {
		if (emailPass && passwordPass && pwRegexPass && inputName.value.length > 0) {
			nextBtn.disabled = false;
			nextBtn.style.backgroundColor = "rgb(6, 195, 115)";
			nextBtn.style.fontWeight = "500";
		} else {
			console.log("emailPass : " + emailPass);
			console.log("passwordPass : " + passwordPass);
			console.log("pwRegexPass : " + pwRegexPass);
			nextBtn.disabled = true;
			nextBtn.style.backgroundColor = "rgb(151, 231, 198)";
		}
	};

	inputEmail.addEventListener("input", joincheckFormValid);
	inputPassword.addEventListener("input", joincheckFormValid);
	inputCheckedPassword.addEventListener("input", joincheckFormValid);
	inputName.addEventListener("input", joincheckFormValid);
	
});