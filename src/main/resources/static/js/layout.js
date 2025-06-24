const toggleButton = document.getElementById('menu-toggle');
const sidebar = document.getElementById('sidebar');
const overlay = document.getElementById('overlay');
const closeButton = document.getElementById('sidebar-close');

toggleButton.addEventListener('click', function() {
	sidebar.classList.toggle('active');
	overlay.classList.toggle('active');
});

overlay.addEventListener('click', function() {
	sidebar.classList.remove('active');
	overlay.classList.remove('active');
});

closeButton.addEventListener('click', function() {
	sidebar.classList.remove('active');
	overlay.classList.remove('active');
});