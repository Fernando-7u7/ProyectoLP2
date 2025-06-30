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