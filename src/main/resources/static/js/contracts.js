window.contractsState = {
    page: 1,
    pageCount: 1,
    rowCount: 0,
    sortCols: [],
    sortDirs: [],
    filters: {
    id: '',
    contractNum: '',
    contractDate: '',
    contractorId: '',
    createDttm: '',
    updateDttm: '',
    createUserId: '',
    updateUserId: ''
}
};

function readContractsFiltersFromInputs() {
contractsState.filters.id = document.getElementById('filter-id')?.value?.trim() || '';
contractsState.filters.contractNum = document.getElementById('filter-contract-num')?.value?.trim() || '';
contractsState.filters.contractDate = document.getElementById('filter-contract-date')?.value || '';
contractsState.filters.contractorId = document.getElementById('filter-contractor-id')?.value?.trim() || '';
contractsState.filters.createDttm = document.getElementById('filter-create-dttm')?.value || '';
contractsState.filters.updateDttm = document.getElementById('filter-update-dttm')?.value || '';
contractsState.filters.createUserId = document.getElementById('filter-create-user-id')?.value?.trim() || '';
contractsState.filters.updateUserId = document.getElementById('filter-update-user-id')?.value?.trim() || '';
}

function applyContractsFiltersToInputs() {
    const setValue = (id, value) => {
    const el = document.getElementById(id);
    if (el) el.value = value ?? '';
};

setValue('filter-id', contractsState.filters.id);
setValue('filter-contract-num', contractsState.filters.contractNum);
setValue('filter-contract-date', contractsState.filters.contractDate);
setValue('filter-contractor-id', contractsState.filters.contractorId);
setValue('filter-create-dttm', contractsState.filters.createDttm);
setValue('filter-update-dttm', contractsState.filters.updateDttm);
setValue('filter-create-user-id', contractsState.filters.createUserId);
setValue('filter-update-user-id', contractsState.filters.updateUserId);
}

function renderContractsEmptyRow(text) {
    const table = document.getElementById('contractsTable');
    const tbody = table.querySelector('tbody');

tbody.innerHTML = `
<tr class="contracts-empty-row">
<td colspan="8">${text}</td>
</tr>
`;

table.style.display = 'table';
}

function renderContractsErrorRow(text) {
    const table = document.getElementById('contractsTable');
    const tbody = table.querySelector('tbody');

tbody.innerHTML = `
<tr class="contracts-empty-row contracts-error-row">
<td colspan="8">${text}</td>
</tr>
`;

table.style.display = 'table';
}

function getPageItems(currentPage, totalPages) {
    const items = [];

    if (totalPages <= 7) {
    for (let i = 1; i <= totalPages; i++) {
items.push(i);
}
    return items;
}

items.push(1);

    if (currentPage <= 4) {
items.push(2, 3, 4, 5, '...');
} else if (currentPage >= totalPages - 3) {
items.push('...');
items.push(totalPages - 4, totalPages - 3, totalPages - 2, totalPages - 1);
} else {
      items.push('...');
      items.push(currentPage - 1, currentPage, currentPage + 1);
      items.push('...');
  }

items.push(totalPages);

    return items;
}

function renderContractsPagination() {
    const pagination = document.getElementById('contracts-pagination');
    const pagesBox = document.getElementById('contracts-pages');
    const firstBtn = document.getElementById('contracts-first-btn');
    const prevBtn = document.getElementById('contracts-prev-btn');
    const nextBtn = document.getElementById('contracts-next-btn');
    const lastBtn = document.getElementById('contracts-last-btn');
    const pageInfo = document.getElementById('contracts-page-info');

    if (!pagination || !pagesBox || !firstBtn || !prevBtn || !nextBtn || !lastBtn || !pageInfo) {
    return;
}

    if (contractsState.rowCount === 0 || contractsState.pageCount <= 1) {
pagination.style.display = 'none';
    return;
}

    const currentPage = contractsState.page;
    const totalPages = contractsState.pageCount;

pagesBox.innerHTML = '';

    const pageItems = getPageItems(currentPage, totalPages);

pageItems.forEach(item => {
    if (item === '...') {
    const dots = document.createElement('span');
dots.className = 'page-dots';
dots.textContent = '...';
pagesBox.appendChild(dots);
    return;
}

    const btn = document.createElement('button');
btn.type = 'button';
btn.className = 'page-btn';
btn.textContent = item;

    if (item === currentPage) {
btn.classList.add('active');
btn.disabled = true;
} else {
      btn.addEventListener('click', () => loadContracts(item));
  }

pagesBox.appendChild(btn);
});

firstBtn.disabled = currentPage === 1;
prevBtn.disabled = currentPage === 1;
nextBtn.disabled = currentPage === totalPages;
lastBtn.disabled = currentPage === totalPages;

firstBtn.onclick = () => loadContracts(1);
prevBtn.onclick = () => {
    if (currentPage > 1) {
loadContracts(currentPage - 1);
}
};
nextBtn.onclick = () => {
    if (currentPage < totalPages) {
loadContracts(currentPage + 1);
}
};
lastBtn.onclick = () => loadContracts(totalPages);

pageInfo.textContent =
`Страница ${currentPage} из ${totalPages} • Всего записей: ${contractsState.rowCount}`;

pagination.style.display = 'flex';
}

function toggleContractsSort(columnName) {
    const index = contractsState.sortCols.indexOf(columnName);

    if (index === -1) {
contractsState.sortCols.push(columnName);
contractsState.sortDirs.push('asc');
} else {
      contractsState.sortDirs[index] =
  contractsState.sortDirs[index] === 'asc' ? 'desc' : 'asc';
  }

renderContractsSortHeaders();
loadContracts(1);
}

function removeContractsSort(columnName) {
    const index = contractsState.sortCols.indexOf(columnName);

    if (index === -1) {
    return;
}

contractsState.sortCols.splice(index, 1);
contractsState.sortDirs.splice(index, 1);

renderContractsSortHeaders();
loadContracts(1);
}

function renderContractsSortHeaders() {
document.querySelectorAll('#contractsTable th[data-sort-col]').forEach(th => {
    const columnName = th.dataset.sortCol;
    const index = contractsState.sortCols.indexOf(columnName);

    const indicator = th.querySelector('.sort-indicator');
    const priority = th.querySelector('.sort-priority');
    const removeBtn = th.querySelector('.sort-remove-btn');

th.classList.remove('active-sort', 'sort-asc', 'sort-desc');

    if (index === -1) {
    if (indicator) indicator.textContent = '↕';
    if (priority) {
priority.textContent = '';
priority.style.visibility = 'hidden';
}
    if (removeBtn) {
removeBtn.style.visibility = 'hidden';
}
    return;
}

    const dir = contractsState.sortDirs[index];

th.classList.add('active-sort');
th.classList.add(dir === 'asc' ? 'sort-asc' : 'sort-desc');

    if (indicator) {
indicator.textContent = dir === 'asc' ? '▲' : '▼';
}

    if (priority) {
priority.textContent = String(index + 1);
priority.style.visibility = 'visible';
}

    if (removeBtn) {
removeBtn.style.visibility = 'visible';
}
});
}

function initContractsSorting() {
document.querySelectorAll('#contractsTable th[data-sort-col]').forEach(th => {
th.addEventListener('click', () => {
toggleContractsSort(th.dataset.sortCol);
});

    const removeBtn = th.querySelector('.sort-remove-btn');
    if (removeBtn) {
removeBtn.addEventListener('click', (event) => {
event.stopPropagation();
removeContractsSort(th.dataset.sortCol);
});
}
});

renderContractsSortHeaders();
}

function initContractsFilters() {
    const filterIds = [
        'filter-id',
        'filter-contract-num',
        'filter-contract-date',
        'filter-contractor-id',
        'filter-create-dttm',
        'filter-update-dttm',
        'filter-create-user-id',
        'filter-update-user-id'
    ];

filterIds.forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;

el.addEventListener('change', () => {
    if (el.dataset.skipNextChange === '1') {
el.dataset.skipNextChange = '0';
    return;
}

readContractsFiltersFromInputs();
loadContracts(1);
});

el.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
event.preventDefault();
el.dataset.skipNextChange = '1';
readContractsFiltersFromInputs();
loadContracts(1);
}
});

el.addEventListener('click', (event) => {
event.stopPropagation();
});
});

applyContractsFiltersToInputs();
}

async function loadContracts(page = null) {
    const pLmt = Math.max(1, Number(document.getElementById('contracts-pLmt')?.value || 10));
    const pFstInput = document.getElementById('contracts-pFst');

    let pFst = Math.max(0, Number(pFstInput?.value || 0));

    if (page !== null) {
    pFst = (page - 1) * pLmt;
}

    if (pFstInput) {
pFstInput.value = pFst;
}

contractsState.page = Math.floor(pFst / pLmt) + 1;

    const params = new URLSearchParams();
params.set('pLmt', String(pLmt));
params.set('pFst', String(pFst));

readContractsFiltersFromInputs();

    if (contractsState.filters.id) {
params.set('pId', contractsState.filters.id);
}
    if (contractsState.filters.contractNum) {
params.set('pContractNum', contractsState.filters.contractNum);
}
    if (contractsState.filters.contractDate) {
params.set('pContractDate', contractsState.filters.contractDate);
}
    if (contractsState.filters.contractorId) {
params.set('pContractorId', contractsState.filters.contractorId);
}
    if (contractsState.filters.createDttm) {
params.set('pCreateDttm', contractsState.filters.createDttm);
}
    if (contractsState.filters.updateDttm) {
params.set('pUpdateDttm', contractsState.filters.updateDttm);
}
    if (contractsState.filters.createUserId) {
params.set('pCreateUserId', contractsState.filters.createUserId);
}
    if (contractsState.filters.updateUserId) {
params.set('pUpdateUserId', contractsState.filters.updateUserId);
}

contractsState.sortCols.forEach(col => params.append('pOrderCols', col));
contractsState.sortDirs.forEach(dir => params.append('pOrderDirs', dir));

    const url = `/api/contracts?${params.toString()}`;

    const message = document.getElementById('contracts-message');
    const table = document.getElementById('contractsTable');
    const tbody = table.querySelector('tbody');
    const pagination = document.getElementById('contracts-pagination');

message.textContent = 'Загрузка...';
tbody.innerHTML = '';

    if (pagination) {
pagination.style.display = 'none';
}

try {
    const response = await fetch(url);

    if (!response.ok) {
    throw new Error(`HTTP ${response.status}`);
}

    const contracts = await response.json();

    if (!contracts || contracts.length === 0) {
    contractsState.pageCount = 1;
    contractsState.rowCount = 0;

    message.textContent = 'Ничего не найдено по текущим фильтрам';
renderContractsEmptyRow('Ничего не найдено по текущим фильтрам');
renderContractsSortHeaders();
    return;
}

    contracts.forEach(contract => {
    const row = document.createElement('tr');

    row.innerHTML = `
<td>${contract.id ?? ''}</td>
<td>${contract.contractNum ?? ''}</td>
<td>${contract.contractDate ?? ''}</td>
<td>${contract.contractorId ?? ''}</td>
<td>${contract.createDttm ?? ''}</td>
<td>${contract.updateDttm ?? ''}</td>
<td>${contract.createUserId ?? ''}</td>
<td>${contract.updateUserId ?? ''}</td>
`;

    tbody.appendChild(row);
});

    const firstRow = contracts[0];
    contractsState.pageCount = Number(firstRow.pCnt ?? 1);
    contractsState.rowCount = Number(firstRow.rCnt ?? contracts.length);

    if (contractsState.page > contractsState.pageCount) {
    contractsState.page = contractsState.pageCount;
}

    message.textContent = '';
    table.style.display = 'table';

renderContractsSortHeaders();
renderContractsPagination();
} catch (error) {
contractsState.pageCount = 1;
contractsState.rowCount = 0;

message.textContent = 'Ошибка загрузки данных: ' + error.message;
renderContractsErrorRow('Ошибка загрузки данных: ' + error.message);
renderContractsSortHeaders();
}
}

async function showContractsFragment() {
    const modal = document.getElementById('contracts-modal');

    if (!modal.dataset.loaded) {
    await loadFragment('/fragments/contracts.html', '#contracts-modal');
modal.dataset.loaded = 'true';

initContractsSorting();
initContractsFilters();

    const loadBtn = document.getElementById('load-contracts-btn');
    if (loadBtn) {
loadBtn.addEventListener('click', () => loadContracts());
}
}

modal.style.display = 'block';
renderContractsSortHeaders();
    await loadContracts(1);
}

function hideContractsFragment() {
    const modal = document.getElementById('contracts-modal');
modal.style.display = 'none';
modal.innerHTML = '';
delete modal.dataset.loaded;
}