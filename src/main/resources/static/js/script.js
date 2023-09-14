document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuButton = document.getElementById('mobileMenuButton');
    const mobileMenu = document.getElementById('mobileMenu');
    const hideMobileMenu = document.getElementById('hideMobileMenu');
    const dropdownMenu = document.getElementById('dropdownMenu');
    const dropdownButton = document.getElementById('dropdownButton');

    mobileMenuButton?.addEventListener('click', () => {
        mobileMenu?.classList.toggle('hidden')
    });

    hideMobileMenu?.addEventListener('click', () => {
        mobileMenu?.classList.toggle('hidden')
    });

    dropdownButton?.addEventListener('mouseover', () => {
        toggleDropdown()
    })
    dropdownButton?.addEventListener('mouseleave', () => {
        toggleDropdown()
    })

    function toggleDropdown() {
        if (dropdownMenu?.classList.contains('scale-100')) {
            dropdownMenu?.classList.remove('scale-100', 'opacity-100');
            dropdownMenu?.classList.add('scale-0', 'opacity-0');
        } else {
            dropdownMenu?.classList.remove('scale-0', 'opacity-0');
            dropdownMenu?.classList.add('scale-100', 'opacity-100');
        }
    }
});