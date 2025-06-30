document.addEventListener("DOMContentLoaded", () => {
	// 🌿 프로필 변경
	const profileChangeBtn = document.querySelector(".btn_upload");
	const profileNoneBlock = document.querySelector(".profile_none_block");
	const profileCancelBtn = document.querySelector(".profile_cancel_btn");
	const originProfileImg = document.querySelector(".origin_profile_img");
	const profilePreviewImg = document.querySelector(".preview");
	const profileImgInput = document.querySelector(".profile_img_input");

	profileChangeBtn.addEventListener("click", () => {
		profileNoneBlock.style.display = profileNoneBlock.style.display === "block" ? "none" : "block";
	});

	profileCancelBtn.addEventListener("click", () => {
		profileNoneBlock.style.display = "none";
		profilePreviewImg.style.display = "none";
		originProfileImg.style.display = "block";
		profileImgInput.value = "";
	});

	// 🌿 프로필 이미지 미리보기 함수
	window.readURL = function(input) {
		if (input.files && input.files[0]) {
			const reader = new FileReader();
			reader.onload = e => {
				const profilePreviewImg = document.querySelector(".preview");
				const originProfileImg = document.querySelector(".origin_profile_img");

				profilePreviewImg.src = e.target.result;
				originProfileImg.style.display = "none";
				profilePreviewImg.style.display = "block";
			};
			reader.readAsDataURL(input.files[0]);
		}
	}

	if (profileImgInput) {
		profileImgInput.addEventListener("change", e => readURL(e.target));
	}

	// 🌿 유효성 검사 정규식들
	const pwRegex = /^(?=.*[a-zA-Z])(?=.*[\W_]).{8,}$/;
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

	// 🌿 각 항목별 토글 및 유효성 검사
	const items = document.querySelectorAll(".personal_info_items");

	items.forEach(item => {
		const arrowImg = item.querySelector("img[alt='arrow_down']");
		const userInfo = item.querySelector(".user_info");
		const changeForm = item.querySelector(".change_form");
		if (!arrowImg || !userInfo || !changeForm) return;

		const confirmBtn = changeForm.querySelector(".confirm_btn");
		const cancelBtn = changeForm.querySelector(".cancel_btn");
		const inputs = changeForm.querySelectorAll("input");

		// 🌿 토글 클릭시 폼 열고 닫기
		item.addEventListener("click", () => {
			if (changeForm.style.display === "block") return;
			const isDown = arrowImg.src.includes("arrow-down.png");
			arrowImg.src = isDown ? "/images/arrow-up.png" : "/images/arrow-down.png";

			if (!item.classList.contains("profile_delete")) {
				userInfo.style.display = isDown ? "none" : "block";
			}
			changeForm.style.display = isDown ? "block" : "none";

			if (isDown) {
				inputs.forEach(input => {
					if (input.name !== '_csrf') {
						input.value = "";
					}
				});
				confirmBtn.disabled = true;
				confirmBtn.style.backgroundColor = "rgb(169, 169, 169)";
				confirmBtn.style.borderColor = "rgb(169, 169, 169)";
				clearMessages(item);
			}
		});

		// 🌿 클릭 이벤트 버블링 방지
		changeForm.addEventListener("click", e => e.stopPropagation());

		// 🌿 취소 버튼 클릭 시 폼 닫기 및 초기화
		cancelBtn.addEventListener("click", e => {
			e.stopPropagation();
			arrowImg.src = "/images/arrow-down.png";
			userInfo.style.display = "block";
			changeForm.style.display = "none";
			inputs.forEach(input => (input.value = ""));
			confirmBtn.disabled = true;
			confirmBtn.style.backgroundColor = "rgb(169, 169, 169)";
			confirmBtn.style.borderColor = "rgb(169, 169, 169)";
			clearMessages(item);
		});

		// 🌿 입력값 변경에 따른 유효성 검사 및 버튼 활성화
		inputs.forEach(input => {
			input.addEventListener("input", () => {
				validateForm(item);
			});
		});
	});

	// 🌿 메시지 영역 초기화 함수
	function clearMessages(item) {
		const errorMsg = item.querySelector(".error_msg");
		if (errorMsg) errorMsg.textContent = "";
		const cautionMsg = item.querySelector(".caution");
		if (cautionMsg) cautionMsg.textContent = "";
	}

	// 🌿 폼별 유효성 검사 및 버튼 활성화 함수
	function validateForm(item) {
		const changeForm = item.querySelector(".change_form");
		const confirmBtn = changeForm.querySelector(".confirm_btn");

		clearMessages(item);

		if (item.classList.contains("profile_name")) {
			// 🌿 이름: 공백 제외 빈 값만 아니면 확인 버튼 활성화
			const nameInput = document.querySelector(".name_input");
			const nameVal = nameInput.value.trim();
			const csrfInput = changeForm.querySelector('input[name="_csrf"]');
			console.log("CSRF token value:", csrfInput ? csrfInput.value : "no csrf input found");
			if (nameVal.length > 0) {
				confirmBtn.disabled = false;
				confirmBtn.style.backgroundColor = "rgb(6, 195, 115)";
				confirmBtn.style.borderColor = "rgb(6, 195, 115)";
			} else {
				confirmBtn.disabled = true;
				confirmBtn.style.backgroundColor = "rgb(169, 169, 169)";
				confirmBtn.style.borderColor = "rgb(169, 169, 169)";
			}
		} else if (item.classList.contains("profile_email")) {
			// 🌿 이메일 형식 체크
			const emailInput = document.querySelector(".email_input");
			const emailVal = emailInput.value.trim();
			const emailCaution = item.querySelector(".email_caution");

			if (!emailRegex.test(emailVal)) {
				confirmBtn.disabled = true;
				emailCaution.style.display = "block";
				confirmBtn.style.backgroundColor = "rgb(169, 169, 169)";
				confirmBtn.style.borderColor = "rgb(169, 169, 169)";
			}
			else {
				confirmBtn.disabled = false;
				emailCaution.style.display = "none";
				confirmBtn.style.backgroundColor = "rgb(6, 195, 115)";
				confirmBtn.style.borderColor = "rgb(6, 195, 115)";
			}
		} else if (item.classList.contains("profile_pw")) {
			// 🌿 비밀번호: 현재 비밀번호 입력 + 새 비밀번호 형식 검사
			const currentPwInput = document.querySelector(".curr_pw_input");
			const newPwInput = document.querySelector(".new_pw_input");

			const currPwInputVal = currentPwInput.value.trim();
			const newPwInputVal = newPwInput.value.trim();
			const pwCaution = document.querySelector(".pw_caution");

			if (currPwInputVal.length > 0 && newPwInputVal.length > 0 && pwRegex.test(newPwInputVal)) {
				console.log("양식 통과");
				confirmBtn.disabled = false;
				pwCaution.style.display = "none";
				console.log(pwCaution.style.display);
				confirmBtn.style.backgroundColor = "rgb(6, 195, 115)";
				confirmBtn.style.borderColor = "rgb(6, 195, 115)"
			}
			else  {
				console.log("양식 불통과");
				console.log("currPW : " + currPwInputVal);
				console.log("newPW : " + newPwInputVal);
				confirmBtn.disabled = true;
				pwCaution.style.display = "block";
				pwCaution.textContent = " 비밀번호는 영문, 숫자, 특수문자로 이뤄진 8~20자 입니다."
				confirmBtn.style.backgroundColor = "rgb(169, 169, 169)";
				confirmBtn.style.borderColor = "rgb(169, 169, 169)";
			}
		}
	}

	document.querySelector(".change_form").addEventListener("submit", function(e) {
		console.log("폼 제출됨");
	});
});
