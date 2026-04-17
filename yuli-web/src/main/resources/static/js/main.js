/* ═══════════════════════════════════════════════════════════
   YULI WEB · main.js
   Navegación · Scroll reveal · Lightbox de galería
═══════════════════════════════════════════════════════════ */

(function () {
    'use strict';

    /* ── DOM references ─────────────────────────────────────── */
    const nav        = document.getElementById('nav');
    const navToggle  = document.getElementById('navToggle');
    const navLinks   = document.querySelector('.nav__links');
    const sections   = document.querySelectorAll('section[id]');
    const navItems   = document.querySelectorAll('.nav__links a');

    /* ── Nav: scroll shadow ─────────────────────────────────── */
    function onScroll() {
        nav.classList.toggle('scrolled', window.scrollY > 40);
        highlightActiveSection();
    }

    /* ── Nav: highlight active link ─────────────────────────── */
    function highlightActiveSection() {
        let current = '';
        sections.forEach(section => {
            const top = section.offsetTop - 120;
            if (window.scrollY >= top) {
                current = section.getAttribute('id');
            }
        });
        navItems.forEach(link => {
            link.classList.remove('active');
            if (link.getAttribute('href') === '#' + current) {
                link.classList.add('active');
            }
        });
    }

    /* ── Nav: mobile toggle ─────────────────────────────────── */
    navToggle.addEventListener('click', () => {
        navLinks.classList.toggle('open');
        const isOpen = navLinks.classList.contains('open');
        navToggle.setAttribute('aria-expanded', isOpen);
    });

    /* Close mobile menu on link click */
    navItems.forEach(link => {
        link.addEventListener('click', () => {
            navLinks.classList.remove('open');
        });
    });

    /* ── Scroll reveal ──────────────────────────────────────── */
    const revealTargets = document.querySelectorAll(
        '.quien__text, .quien__media, ' +
        '.historia__block, ' +
        '.momento, ' +
        '.galeria__item, ' +
        '.carta__body'
    );

    revealTargets.forEach(el => el.classList.add('reveal'));

    const revealObserver = new IntersectionObserver((entries) => {
        entries.forEach((entry, i) => {
            if (entry.isIntersecting) {
                // Slight stagger for gallery items
                const delay = entry.target.closest('.galeria__grid')
                    ? Math.min(i * 20, 300)
                    : 0;
                setTimeout(() => {
                    entry.target.classList.add('visible');
                }, delay);
                revealObserver.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1, rootMargin: '0px 0px -50px 0px' });

    revealTargets.forEach(el => revealObserver.observe(el));

    /* ── Lightbox ───────────────────────────────────────────── */
    const lightbox     = document.getElementById('lightbox');
    const lightboxImg  = document.getElementById('lightboxImg');
    const lightboxClose = document.getElementById('lightboxClose');
    const lightboxPrev  = document.getElementById('lightboxPrev');
    const lightboxNext  = document.getElementById('lightboxNext');
    const galleryItems  = Array.from(document.querySelectorAll('.galeria__item img'));

    let currentIndex = 0;

    function openLightbox(index) {
        currentIndex = index;
        lightboxImg.src = galleryItems[index].src;
        lightboxImg.alt = galleryItems[index].alt;
        lightbox.classList.add('open');
        document.body.style.overflow = 'hidden';
    }

    function closeLightbox() {
        lightbox.classList.remove('open');
        document.body.style.overflow = '';
        lightboxImg.src = '';
    }

    function showPrev() {
        currentIndex = (currentIndex - 1 + galleryItems.length) % galleryItems.length;
        lightboxImg.src = galleryItems[currentIndex].src;
        lightboxImg.alt = galleryItems[currentIndex].alt;
    }

    function showNext() {
        currentIndex = (currentIndex + 1) % galleryItems.length;
        lightboxImg.src = galleryItems[currentIndex].src;
        lightboxImg.alt = galleryItems[currentIndex].alt;
    }

    galleryItems.forEach((img, index) => {
        img.parentElement.addEventListener('click', () => openLightbox(index));
    });

    lightboxClose.addEventListener('click', closeLightbox);
    lightboxPrev.addEventListener('click', showPrev);
    lightboxNext.addEventListener('click', showNext);

    lightbox.addEventListener('click', (e) => {
        if (e.target === lightbox) closeLightbox();
    });

    document.addEventListener('keydown', (e) => {
        if (!lightbox.classList.contains('open')) return;
        if (e.key === 'Escape')      closeLightbox();
        if (e.key === 'ArrowLeft')   showPrev();
        if (e.key === 'ArrowRight')  showNext();
    });

    /* Touch swipe on lightbox */
    let touchStartX = 0;
    lightbox.addEventListener('touchstart', e => {
        touchStartX = e.changedTouches[0].clientX;
    }, { passive: true });
    lightbox.addEventListener('touchend', e => {
        const diff = touchStartX - e.changedTouches[0].clientX;
        if (Math.abs(diff) > 50) {
            diff > 0 ? showNext() : showPrev();
        }
    }, { passive: true });

    /* ── Init ───────────────────────────────────────────────── */
    window.addEventListener('scroll', onScroll, { passive: true });
    onScroll();

})();
