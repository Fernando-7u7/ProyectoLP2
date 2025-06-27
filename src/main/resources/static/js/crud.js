const imgPreview = document.getElementById('imgPreview');
    const imagenInput = document.getElementById('imagenInput');
    const editIcon = document.getElementById('editIcon');

    // Abrir selector al hacer clic en la imagen o el ícono
    imgPreview.addEventListener('click', () => imagenInput.click());
    editIcon.addEventListener('click', () => imagenInput.click());

    // Mostrar preview al seleccionar archivo
    imagenInput.addEventListener('change', event => {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = e => imgPreview.src = e.target.result;
        reader.readAsDataURL(file);
      }
    });