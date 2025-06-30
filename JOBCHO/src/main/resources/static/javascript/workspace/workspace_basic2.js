const icon = document.getElementById('plus_icon');
const menuIcon = document.getElementById('menu_icon');
const popup = document.getElementById('popup');
const popup2 = document.getElementById('popup2');
const noticeLink = document.getElementById('notice-link');
const menuLink = document.getElementById('menu_link');;
const overlay = document.getElementById('overlay');
const modal = document.getElementById('modal');
const taskLink = document.getElementById('task-link');
const voteLink = document.getElementById('vote-link')
const createLink = document.getElementById('create-task');
const bookmarkLink = document.getElementById('bookmark-link');
const taskDetailLink = document.getElementById('task_detail_container');
const wrapper = document.querySelector('.main');
const bookmarkTotalContainer = document.querySelector('.bookmark_total_container');
const taskTotalContainer = document.querySelector('.task_total_container');
const voteTotalContainer = document.querySelector('.vote_total_container');
const createToatalContainer = document.querySelector('.create_task_total_container');
const taskDetailTotalContainer = document.querySelector('.task_detail_total_container');
//const modal2 = document.getElementById('modal2');
//const chatroomCreateLink = document.getElementById('addChatroombtn');


const folderArrow = document.querySelector('.folder_arrow');


const create_folder = document.getElementById('create_folder');
const create_folder_btn = document.getElementById('create_folder_btn');
const create_folder_input = document.getElementById('create_folder_input');
create_folder_btn.addEventListener('click', () => {
	create_folder.style.display = 'block';
});

create_folder_input.addEventListener('keydown', (event) => {
	if (event.key === 'Enter') {
		create_folder.style.display = 'none';
	}
});

const icon_notifi = document.getElementById('plus_icon_notificaiton');
const icon_notifi_more = document.getElementById('min_icon_notificaiton');
const notificaiton_short = document.getElementById('notificaiton_short');
const notificaiton_more = document.getElementById('notificaiton_more');

if (icon_notifi != null) {
	icon_notifi.addEventListener('click', () => {
		notificaiton_short.style.display = 'none'
		notificaiton_more.style.display = 'flex'
	});
}

if (icon_notifi_more != null) {
	icon_notifi_more.addEventListener('click', () => {
		notificaiton_short.style.display = 'flex'
		notificaiton_more.style.display = 'none'

	});
}

taskLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	bookmarkTotalContainer.style.display = 'none';
	voteTotalContainer.style.display = 'none';
	taskTotalContainer.style.display = 'block';
	//taskTotalContainer.style.display = (taskTotalContainer.style.display == 'block') ? 'none' : 'block';
});
bookmarkLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	voteTotalContainer.style.display = 'none';
	taskTotalContainer.style.display = 'none';
	bookmarkTotalContainer.style.display = 'block';
});
voteLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	bookmarkTotalContainer.style.display = 'none';
	taskTotalContainer.style.display = 'none';
	voteTotalContainer.style.display = 'block';
});

if (createLink != null) {
	createLink.addEventListener('click', (e) => {
		e.preventDefault();
		taskTotalContainer.style.display = 'none';
		createToatalContainer.style.display = 'block';
	});
}

if (taskDetailLink != null) {
	taskDetailLink.addEventListener('click', (e) => {
		//e.preventDefault();
		//const workspaceId = taskDetailLink.dataset.workspaceId;
		//const chatroomId = taskDetailLink.dataset.chatroomId;
		//const taskId = taskDetailLink.dataset.taskId;
		//const url = `/workspace/${workspaceId}/${chatroomId}/side/${taskId}`;
		//window.location.href = url;
	})
}

if (icon != null) {
	//아이콘 ... 
	icon.addEventListener('click', () => {
		popup.style.display = (popup.style.display === 'block') ? 'none' : 'block';
	});
}

if (noticeLink != null) {
	noticeLink.addEventListener('click', (e) => {
		e.preventDefault();
		overlay.style.display = 'block';
		modal.style.display = 'block';
	});
}

function openModal(btn) {
	const index = btn.getAttribute("data-folder-index");
	const modal = document.getElementById("modal2-" + index);
	if (modal) {
		modal.style.display = "block";
		overlay.style.display = 'block';
	}
}

function openSideTask(btn) {
	const workspaceId = btn.getAttribute("data-workspace-id");
	const chatroomId = btn.getAttribute("data-chatroom-id");
	const taskId = btn.getAttribute("data-task-id");
	const url = `/workspace/${workspaceId}/${chatroomId}/side/${taskId}`;
	window.location.href = url;
}

function openSideMessage(btn) {
	const workspaceId = btn.getAttribute("data-workspace-id");
	const chatroomId = btn.getAttribute("data-chatroom-id");
	const messageId = btn.getAttribute("data-message-id");
	const url = `/workspace/${workspaceId}/${chatroomId}/side/message/${messageId}`;
	window.location.href = url;
}

function showModifyChat(btn) {
	const workspaceId = btn.getAttribute("data-workspace-id");
	const chatroomId = btn.getAttribute("data-chatroom-id");
	const url = `/workspace/${workspaceId}/${chatroomId}/side/modifyChat`;
	window.location.href = url;
}

function openChatMenu(index) {
	const menu = document.getElementById("chatmenu-" + index);
	if (menu) {
		menu.style.visibility = 'visible'
		menu.style.opacity = 1;
	}
}

function closeChatMenu(index) {
	const menu = document.getElementById("chatmenu-" + index);
	if (menu) {
		menu.style.visibility = 'none'
		menu.style.opacity = 0;
	}
}

//아이콘 메뉴 더보기
menuLink.addEventListener('click', (e) => {
	e.preventDefault();
	popup2.style.display = (popup2.style.display === 'block') ? 'none' : 'block';
});
document.addEventListener('click', (event) => {
	const isClickInsideIcon = icon && icon.contains(event.target);
	const isClickInsidePopup = popup && popup.contains(event.target);
	const isClickInsideMenuIcon = menuIcon && menuIcon.contains(event.target);
	const isClickInsidePopup2 = popup2 && popup2.contains(event.target);

	if (!isClickInsideIcon && !isClickInsidePopup) {
		if (popup != null) {
			popup.style.display = 'none';
		}
	}

	if (!isClickInsideMenuIcon && !isClickInsidePopup2) {
		if (popup2 != null) {
			popup2.style.display = 'none';
		}
	}
});


//공지사항 업로드 스크립트 불러옴
document.addEventListener("DOMContentLoaded", () => {
	const textArea = document.querySelector(".register_form textarea");
	if (document.querySelector(".register_form textarea") != null) {
		const originText = document.querySelector(".register_form textarea").value;
	}
	const numberOfText = document.querySelector(".numbers_of_text span");
	const editBtn = document.querySelector(".register_btn");

	if (textArea != null) {
		textArea.addEventListener("input", () => {
			if (textArea.value !== originText && textArea.value.length > 0) {
				editBtn.style.backgroundColor = "rgb(6, 195, 115)";
				editBtn.style.color = "rgb(250, 250, 250)";
			} else {
				editBtn.style.backgroundColor = "rgb(250, 250, 250)";
				editBtn.style.color = "rgb(200, 200, 200)";
			}

			numberOfText.textContent = textArea.value.length;
		});
	}
});

//즐겨찾기 스크립트 불러옴
document.addEventListener("DOMContentLoaded", () => {
	const allFormBtn = document.querySelector(".all_form_btn");
	const fileFormBtn = document.querySelector(".file_form_btn");
	if (allFormBtn != null) {
		allFormBtn.addEventListener("click", () => {
			fileFormBtn.style.borderColor = "rgb(250, 250, 250)";
			fileFormBtn.style.fontWeight = "400";
			allFormBtn.style.borderColor = "black";
			allFormBtn.style.fontWeight = "700";
		});

		fileFormBtn.addEventListener("click", () => {
			allFormBtn.style.borderColor = "rgb(250, 250, 250)";
			allFormBtn.style.fontWeight = "400";
			fileFormBtn.style.fontWeight = "700";
			fileFormBtn.style.borderColor = "black";
		});
	}
});

function toggleStar(imgElement) {
	const emptyStar = '/images/icons/empty_star.png';
	const filledStar = '/images/icons/star.png';
	const currentSrc = imgElement.getAttribute('src');

	if (currentSrc.includes('empty_star.png')) {
		imgElement.setAttribute('src', filledStar);
	} else {
		imgElement.setAttribute('src', emptyStar);
	}
}

function toggleStar(imgElement) {
	const emptyStar = '/images/icons/empty_star.png';
	const filledStar = '/images/icons/star.png';

	const currentSrc = imgElement.getAttribute('src');
	imgElement.setAttribute('src', currentSrc.includes('empty_star') ? filledStar : emptyStar);
}

function toggleFolder(folderHeader) {
	const folderContent = folderHeader.nextElementSibling;
	if (!folderContent) return;

	const isVisible = folderContent.style.display !== 'none';
	folderContent.style.display = isVisible ? 'none' : 'block';

	const arrowImg = folderHeader.querySelector('.folder_arrow');
	if (arrowImg) {
		const downArrow = '/images/icons/arrow-down.png';
		const rightArrow = '/images/icons/arrow-right.png';

		const currentSrc = arrowImg.getAttribute('src');
		arrowImg.setAttribute('src', currentSrc.includes('arrow-down') ? rightArrow : downArrow);
	}
}

function shownotifi(btn) {
	const content = btn.getAttribute('data-notification-content');
	const textarea = document.getElementById('notification_content');
	textarea.value = content;
	overlay.style.display = 'block';
	modal.style.display = 'block';
}

function shownotifiOnMessage(btn) {
	const content = btn.getAttribute('data-message-content');
	const textarea = document.getElementById('notification_content');
	textarea.value = content;
	overlay.style.display = 'block';
	modal.style.display = 'block';
}

function showMessageEdit(btn) {
	const content = btn.getAttribute('data-message-content');
	const messageId = btn.getAttribute('data-message-id');
	const textarea = document.getElementById('message_content');
	const idArea = document.getElementById('messageId');
	const modal3 = document.getElementById('modal3');
	textarea.value = content;
	idArea.value = messageId;
	overlay.style.display = 'block';
	modal3.style.display = 'block';
}

function showTaskOnMessage(btn) {
	const content = btn.getAttribute('data-message-content');
	const textarea = document.getElementById('task_content');
	textarea.value = content;
	wrapper.classList.toggle('show-aside');
	bookmarkTotalContainer.style.display = 'none';
	voteTotalContainer.style.display = 'none';
	taskTotalContainer.style.display = 'none';
	createToatalContainer.style.display = 'block';
}

function showAlertDel() {
	const modal4 = document.getElementById('modal4');
	overlay.style.display = 'block';
	modal4.style.display = 'block';
}
function showAlertOut() {
	const modal5 = document.getElementById('modal5');
	overlay.style.display = 'block';
	modal5.style.display = 'block';
}

function showFileUpload() {
	const fileArea = document.getElementById('chat_file');
	fileArea.style.display = 'block';
}

function showHamPopup() {
	const ham = document.getElementById('ham_popup');
	ham.style.display = (ham.style.display == 'block') ? 'none' : 'block';
}

if (document.getElementById('event_prevent')) {
	const link = document.getElementById('event_prevent')
	link.addEventListener('click', (e) => {
		e.preventDefault();
	})
}

function showOrganization() {
	const chart = document.getElementById('organization_chart');
	chart.style.display = (chart.style.display == 'block') ? 'none' : 'block';
}

if (document.querySelector('.event_prevent')) {
	const link = document.querySelector('.event_prevent')
	link.addEventListener('click', (e) => {
		e.preventDefault();
	})
}

function showAlarm() {
	const link = document.getElementById('side_alarm');
	link.style.display = (link.style.display == 'block') ? 'none' : 'block';
}
if (document.querySelector('.header_alarm_icon')) {
	const link = document.querySelector('.header_alarm_icon')
	link.addEventListener('click', (e) => {
		e.preventDefault();
	})
}

document.addEventListener('DOMContentLoaded', function() {
	const cancelBtns = document.querySelectorAll('.cancel_btn');
	const overlay = document.getElementById('overlay');

	cancelBtns.forEach(btn => {
		btn.addEventListener('click', function() {
			const index = btn.getAttribute('data-folder-index');
			const modal = document.getElementById('modal2-' + index);
			if (modal) {
				modal.style.display = 'none';
			}
			if (overlay) {
				overlay.style.display = 'none';
			}
		});
	});
});



