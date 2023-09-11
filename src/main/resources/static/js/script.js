document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuButton = document.getElementById('mobileMenuButton');
    const mobileMenu = document.getElementById('mobileMenu');
    const hideMobileMenu = document.getElementById('hideMobileMenu');

    mobileMenuButton.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden')
    });

    hideMobileMenu.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden')
    });
});