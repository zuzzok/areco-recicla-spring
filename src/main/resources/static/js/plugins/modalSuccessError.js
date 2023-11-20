
const successModal = document.getElementById("successModal");
const errorModal = document.getElementById("errorModal");


if (successModal) {
  const bSuccessModal = new bootstrap.Modal(successModal, {
    keyboard: false
  })
  bSuccessModal.show();
}

if (errorModal) {
  const bErrorModal = new bootstrap.Modal(errorModal, {
    keyboard: false
  })
  bErrorModal.show()
}
