document.addEventListener("DOMContentLoaded", () => {

	// 채팅방 멤버 초대 모달 열기
	const inviteChatMemberImg = document.querySelector("#invite_chat_member_img");
	const closeBtn = document.querySelector(".close_btn");
	const inviteChatMemberContainer = document.querySelector(".modal_overlay");

	if (inviteChatMemberImg && closeBtn && inviteChatMemberContainer) {
		inviteChatMemberImg.addEventListener("click", () => {
			document.getElementById("modalBackdrop").style.display = "block";
			inviteChatMemberContainer.style.display = "block";
		});

		closeBtn.addEventListener("click", () => {
			document.getElementById("modalBackdrop").style.display = "none";
			inviteChatMemberContainer.style.display = "none";
		});
	}

});

function chatroom_member_img_toggle() {
	const chatMemberBtn = document.querySelector(".chatroom_member_img_btn");
	const chatMemberList = document.querySelector(".chatroom_member_list");
	chatMemberList.style.display = (chatMemberList.style.display === 'block') ? 'none' : 'block';
}