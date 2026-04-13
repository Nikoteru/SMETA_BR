async function loadFragment(url, targetSelector) {
    const target = document.querySelector(targetSelector);
    if (!target) return;

    const response = await fetch(url);
    const html = await response.text();
    target.innerHTML = html;
}

async function initLayout() {
    await loadFragment('/fragments/sidebar.html', '#sidebar-placeholder');
    await loadFragment('/fragments/sidebar-menu.html', '#sidebar-menu-container');
    await loadFragment('/fragments/header.html', '#header-placeholder');

    initUserMenu();
    initMobileSidebar();
    setActiveMenuItem();
}

function initUserMenu() {
    const button = document.getElementById('userMenuButton');
    const dropdown = document.getElementById('userDropdown');

    if (!button || !dropdown) return;

    button.addEventListener('click', function (e) {
        e.stopPropagation();
        dropdown.classList.toggle('open');
    });

    document.addEventListener('click', function () {
        dropdown.classList.remove('open');
    });
}

function initMobileSidebar() {
    const mobileBtn = document.getElementById('mobileMenuBtn');
    const sidebar = document.getElementById('sidebar');

    if (!mobileBtn || !sidebar) return;

    mobileBtn.addEventListener('click', function () {
        sidebar.classList.toggle('open');
    });
}

function setActiveMenuItem() {
    const currentPath = window.location.pathname;
    const links = document.querySelectorAll('.sidebar-menu-item');

    links.forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPath) {
            link.classList.add('active');
        } else if (currentPath === '/' && href === '/index.html') {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });
}

document.addEventListener('DOMContentLoaded', initLayout);

