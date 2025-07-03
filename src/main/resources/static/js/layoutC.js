const swiper = new Swiper(".mySwiper", {
    loop: true,
    spaceBetween: 20,
    slidesPerView: 1, // por defecto en pantallas pequeñas
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
    breakpoints: {
      770: {
        slidesPerView: 2
      },
      992: {
        slidesPerView: 3
      },
	  1250:{
		slidesPerView:4
	  }
    }
  });
  
  const bannerSwiper = new Swiper('.bannerSwiper', {
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    effect: 'fade',
    fadeEffect: { crossFade: true },
    pagination: { el: '.swiper-pagination', clickable: true } 
  });
  
  const carritoSwiper = new Swiper('.carritoSwiper', {
    loop: true,
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    autoplay: {
      delay: 5000,
      disableOnInteraction: false,
    },
  });

  function confirmarCierreSesion(e) {
          e.preventDefault();

          Swal.fire({
              title: "¿Deseas cerrar sesión?",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#3085d6",
              cancelButtonColor: "#d33",
              confirmButtonText: "Sí, cerrar",
              cancelButtonText: "No"
          }).then((result) => {
              if (result.isConfirmed) {
                  document.getElementById("cerrarSesionForm").submit();
              }
          });
      }

      // Escucha para ambos botones (navbar y offcanvas)
      document.getElementById("cerrarSesionLink")?.addEventListener("click", confirmarCierreSesion);
      document.getElementById("cerrarSesionLinkOffcanvas")?.addEventListener("click", confirmarCierreSesion);