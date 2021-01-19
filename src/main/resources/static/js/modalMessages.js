window.onload = () => {
	let modalCont = document.getElementById('statusReport');
	let modalMessage = document.getElementById('statusMessage');
	if (modalMessage.innerHTML.trim() != "") {
		modalCont.classList.remove("d-none");
		$("#modalId").modal("show");
	}
}