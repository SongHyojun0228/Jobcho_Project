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
icon_notifi.addEventListener('click', () => {
	notificaiton_short.style.display = 'none'
	notificaiton_more.style.display = 'flex'
});

icon_notifi_more.addEventListener('click', () => {
		notificaiton_short.style.display = 'flex'
		notificaiton_more.style.display = 'none'
		
});

taskLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	bookmarkTotalContainer.style.display = 'none';
	voteTotalContainer.style.display = 'none';
	taskDetailTotalContainer.style.display = 'none';
	taskTotalContainer.style.display =  'block';
	//taskTotalContainer.style.display = (taskTotalContainer.style.display == 'block') ? 'none' : 'block';
});
bookmarkLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	voteTotalContainer.style.display = 'none';
	taskDetailTotalContainer.style.display = 'none';
	taskTotalContainer.style.display =  'none';
	bookmarkTotalContainer.style.display = 'block';
});
voteLink.addEventListener('click', (e) => {
	e.preventDefault();
	wrapper.classList.toggle('show-aside');
	bookmarkTotalContainer.style.display = 'none';
	taskDetailTotalContainer.style.display = 'none';
	taskTotalContainer.style.display =  'none';
	voteTotalContainer.style.display = 'block';
});
createLink.addEventListener('click',(e) => {
	e.preventDefault();
	taskTotalContainer.style.display =  'none';
	taskDetailTotalContainer.style.display = 'none';
	createToatalContainer.style.display = 'block';
});
taskDetailLink.addEventListener('click',(e)=>{
	e.preventDefault();
	bookmarkTotalContainer.style.display = 'none';
	voteTotalContainer.style.display = 'none';
	taskTotalContainer.style.display =  'none';
	taskDetailTotalContainer.style.display = 'block';
})

//아이콘 ... 
icon.addEventListener('click', () => {
	popup.style.display = (popup.style.display === 'block') ? 'none' : 'block';
});
noticeLink.addEventListener('click', (e) => {
	e.preventDefault();
	overlay.style.display = 'block';
	modal.style.display = 'block';
});

//아이콘 메뉴 더보기
menuLink.addEventListener('click', (e) => {
	e.preventDefault();
	popup2.style.display = (popup2.style.display === 'block') ? 'none' : 'block';
});
document.addEventListener('click', (event) => {
	const isClickInsideIcon = icon.contains(event.target);
	const isClickInsidePopup = popup.contains(event.target);
	const isClickInsideMenuIcon = menuIcon.contains(event.target);
	const isClickInsidePopup2 = popup2.contains(event.target);

	if (!isClickInsideIcon && !isClickInsidePopup) {
		popup.style.display = 'none';
	}

	if (!isClickInsideMenuIcon && !isClickInsidePopup2) {
		popup2.style.display = 'none';
	}
});
