xBtnElement = document.querySelector(".x_btn");
totalContainer = document.querySelector(".csChatBox");

xBtnElement.addEventListener("click", function() {
	totalContainer.style.display = 'none'
	totalContainer.style['z-index'] = '0'
});

openCs = document.getElementById('cs_a');
openCs.addEventListener("click", function() {
	totalContainer.style.display = 'block'
	totalContainer.style['z-index'] = '3'
	const ham = document.getElementById('ham_popup');
	ham.style.display = 'none';
});

document.getElementById('cs_a').addEventListener('click', function(e) {
	e.preventDefault();
});

cslinks = document.querySelectorAll('.chat-link');
cslinks.forEach(function(link) {
	link.addEventListener('click', function(e) {
		e.preventDefault();
		const url = this.href;
		window.open(url, 'chatWindow', 'width=395,height=850,resizable=yes');
	});
});
