window.onload = () => {
   	$('[data-toggle="popover"]').popover();
	// API Entry Point 
	var synth = window.speechSynthesis;

	// Get all user input through these DOM elements
	var pronounceButton = document.querySelectorAll('.pronounce')
	var englishPronounce = document.querySelectorAll('.spelling');

	// Define and get all available Web Speech API voices
	var voices = [];
	voices = synth.getVoices();
	var utterThis = [];
	var clickable = true;


	englishPronounce.forEach(function(spellingEl) {
		utterThis.push(new SpeechSynthesisUtterance(spellingEl.innerHTML));
	});

	pronounceButton.forEach(function(element,index) {
		var currHTMLinElem = element.innerHTML;
		element.onclick = function () {
			if (clickable) {
				element.classList.add("btn-bg-h");
				element.innerHTML = "<div class='spinner-border text-primary' role='status'></div>";
				utterThis[index].voice = voices.filter(function(voice) { return voice.name == 'David'; })[0];
				utterThis[index].pitch = 1;
				utterThis[index].rate = 0.6;
				synth.speak(utterThis[index]);	
			} else {
				document.querySelector('.alert').classList.remove('init-height-alert');
				setTimeout(function() {
					document.querySelector('.alert').classList.add('init-height-alert');
				}, 2000)
			}	
		};
		utterThis[index].onstart = function() {
			clickable = false;
		}
		utterThis[index].onend = function() {
		       element.innerHTML = currHTMLinElem;
		       clickable = true;
			   element.classList.remove("btn-bg-h");
		};
	});
}


	  