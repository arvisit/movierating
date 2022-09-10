/**
 * 
 */
 
function deleteImage(inputId, imageId, defaultValue) {
    document.getElementById(inputId).value = defaultValue;
    document.getElementById(imageId).src = defaultValue;
    document.getElementById("selectedImg").value = "";
}

function loadImageTo(img) {
    document.getElementById(img).src = URL.createObjectURL(event.target.files[0]);
}