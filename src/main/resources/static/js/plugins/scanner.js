
// QR Scanner
const html5QrcodeScanner = new Html5Qrcode("qr-reader", {
  formatsToSupport: [Html5QrcodeSupportedFormats.QR_CODE],
  verbose: false,
});
let lastResult;


// DOM Elements
const qrreader = document.getElementById('qr-reader');
const qrbutton = document.getElementById("qr-button");
const qrmodal = document.getElementById("qr-modal");

// Variables
let qrtarget = ""


// Bootstrap Modal Element
const modal = new bootstrap.Modal(qrmodal, {
  keyboard: false
})


// Event Listeners
qrbutton.addEventListener("click", async (e) => {
  qrtarget = e.target.dataset.qrTarget;
  console.log(qrtarget);
  await html5QrcodeScanner.start(
    { facingMode: 'environment' },
    {
      fps: 5,
      qrbox: { width: 200, height: 200 },
    },
    onScanSuccess
    )
})

qrmodal.addEventListener("hidden.bs.modal", async (e) => {
  await html5QrcodeScanner.stop()
  qrreader.innerHTML = ""
})

async function onScanSuccess(decodedText, decodedResult) {
  if (decodedText !== lastResult) {

    const qrresult = document.getElementById(`${qrtarget}`);
    
    lastResult = decodedText;
    qrresult.value = decodedText
    
    modal.hide()
    
  } else {
    const qrresult = document.getElementById(`${qrtarget}`);
    qrresult.value = decodedText
    modal.hide()
  }
}
