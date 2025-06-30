document.addEventListener("DOMContentLoaded", () => {

	const inputEmail = document.querySelector(".input_email");
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	const emailCaution = document.querySelector(".email_caution");
	const endBtn = document.querySelector(".send_authentication_number");
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

	const joincheckFormValid = () => {
		if (emailPass && inputEmail.value.length > 0) {
			endBtn.style.backgroundColor = "rgb(6, 195, 115)";
			endBtn.style.fontWeight = "500";
		} else {
			endBtn.style.backgroundColor = "rgb(151, 231, 198)";
		}
	};
});

function sendMail() {
	const email = document.getElementById("email").value;
	if (!email) {
		alert("이메일을 입력하세요.");
		return;
	}

	const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

	fetch("/mailSend", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded",
			[header]: token // CSRF 토큰 삽입
		},
		body: `mail=${encodeURIComponent(email)}`
	})
		.then(res => res.json())
		.then(data => {
			if (data.success) {
				document.getElementById("result").innerText = "인증번호가 전송되었습니다. 이메일을 확인해주세요.";
			} else {
				document.getElementById("result").innerText = "인증번호 전송 실패: " + data.error;
			}
		})
		.catch(err => {
			document.getElementById("result").innerText = "통신 에러: " + err;
		});
}

// 인증번호 확인 요청
/* function checkVerification() {
	const email = document.getElementById("email").value;
	const userNumber = document.getElementById("verificationNumber").value;

	if (!email || !userNumber) {
		alert("이메일과 인증번호를 모두 입력하세요.");
		return;
	}

	fetch(`/mailCheck?mail=${encodeURIComponent(email)}&userNumber=${userNumber}`)
		.then(res => res.json())
		.then(isMatch => {
			if (isMatch) {
				document.getElementById("result").innerText = "인증번호가 일치합니다!";
			} else {
				document.getElementById("result").innerText = "인증번호가 일치하지 않습니다.";
			}
		})
		.catch(err => {
			document.getElementById("result").innerText = "통신 에러: " + err;
		});
} */