function addInput(inpSelector, insAfterSelector) {
	// Inserts an <input> element after a particular element - used for the pages that add words.
	let origInput = document.querySelector(inpSelector);
	let insAfter = document.querySelector(insAfterSelector);
	let clone = origInput.cloneNode();
	insAfter.insertAdjacentHTML("afterend", clone.outerHTML + "<br>");
}

// Shows the alert for successful password change in index.jsp
function showAndHideAlert() {
	let elem = document.querySelector('#change-pass');
	elem.classList.remove('init-height-alert');
	setTimeout(function() {
		elem.classList.add('init-height-alert');
	}, 2000);
}

// Changes look and behavior of all popovers based on the user agent - default behavior is the mobile behavior (only activates on focus, bottom placement, etc.)
function popoverBehavior() {
	let popovers = document.querySelectorAll('.popovers');
	if (!navigator.userAgent.match(/(android|iphone|blackberry|ipod|netfront|model-orange|javaplatform|windows phone|samsung|htc|opera mobile|opera mobi|opera mini|huawei|bolt|fennec|gobrowser|maemo browser|nokia|bb10|ipad)/gi)) {
		popovers.forEach(function(popover,index) {
			popover.dataset.trigger = "hover";
			popover.dataset.placement = "right";
			popover.dataset.role = "";
		});
	}
	
}


// Voice synthesis logic for the utter user input page

function userInputSynth() {
	// API Entry Point 
	var synth = window.speechSynthesis;

	// Get user input through these DOM elements
	var pronounceButton = document.querySelector('.buttonPron')
	var englishPronounce = document.querySelector('.spelling');

	// Define and get all available Web Speech API voices
	var voices = [];
	voices = synth.getVoices();
	var utterThis = "";
	
	// Create an instance of SpeechSynthesisUtterance from the user's input and set the settings for it accordingly
	utterThis = new SpeechSynthesisUtterance(englishPronounce.value);
	utterThis.voice = voices.filter(function(voice) { return voice.name == 'David'; })[0];
    utterThis.pitch = 1;
	utterThis.rate = 0.6;
	
	// Hold the current contents of the pronounce button so we can swap the "currently uttering" HTML and the "pronounce" text
	var currHTMLinElem = pronounceButton.innerHTML;
	
	
	pronounceButton.classList.add("btn-bg-h");
	pronounceButton.innerHTML = "<div class='spinner-border text-primary text-primary-hover-override' role='status'></div>";
	synth.speak(utterThis);	
	
	utterThis.onstart = function() {
		pronounceButton.disabled = true;
	};
	utterThis.onend = function() {
		pronounceButton.innerHTML = currHTMLinElem;
		pronounceButton.disabled = false;
		pronounceButton.classList.remove("btn-bg-h");
	};
}

document.querySelector('.buttonPron').addEventListener("click", userInputSynth);