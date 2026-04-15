async function loadFragment(url, selector) {
    const response = await fetch(url);

    if (!response.ok) {
    throw new Error(`Не удалось загрузить фрагмент: HTTP ${response.status}`);
}

    const html = await response.text();
document.querySelector(selector).innerHTML = html;
}

function initSidebarActive() {
    const currentPage = (window.location.pathname.split('/').pop() || 'index.html').toLowerCase();

document.querySelectorAll('.sidebar-menu-item').forEach(link => {
    const linkPage = (link.getAttribute('href') || '').split('/').pop().toLowerCase();
link.classList.toggle('active', linkPage === currentPage);
});
}

async function initPage() {
    await loadFragment('/fragments/sidebar.html', '#sidebar-placeholder');
initSidebarActive();

    const docTypeSelect = document.getElementById('docType');

docTypeSelect.addEventListener('change', async function () {
    const docType = this.value;

    if (docType === '1') {
    await showContractsFragment();
} else {
  hideContractsFragment();
  }
});
}

document.addEventListener('DOMContentLoaded', initPage);