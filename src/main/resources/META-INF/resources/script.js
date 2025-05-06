const URL = 'http://localhost:8081';
let entries = [];

const dateAndTimeToDate = (dateString, timeString) => {
    return new Date(`${dateString}T${timeString}`).toISOString();
};

const createEntry = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const entry = {};
    entry['checkIn'] = dateAndTimeToDate(formData.get('checkInDate'), formData.get('checkInTime'));
    entry['checkOut'] = dateAndTimeToDate(formData.get('checkOutDate'), formData.get('checkOutTime'));

    const editId = e.target.dataset.editId;
    const method = editId ? 'PUT' : 'POST';
    const url = editId ? `${URL}/entries/${editId}` : `${URL}/entries`;

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entry)
    }).then((result) => {
        result.json().then((entry) => {
            if (editId) {
                // Update existing entry
                const index = entries.findIndex(e => e.id === entry.id);
                if (index !== -1) {
                    entries[index] = entry;
                }
            } else {
                // Add new entry
                entries.push(entry);
            }
            renderEntries();
            // Reset form
            e.target.reset();
            delete e.target.dataset.editId;
        });
    });
};

const indexEntries = () => {
    fetch(`${URL}/entries`, {
        method: 'GET'
    }).then((result) => {
        result.json().then((result) => {
            entries = result;
            renderEntries();
        });
    });
    renderEntries();
};

const createCell = (text) => {
    const cell = document.createElement('td');
    cell.innerText = text;
    return cell;
};

const renderEntries = () => {
    const display = document.querySelector('#entryDisplay');
    display.innerHTML = '';
    entries.forEach((entry) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(entry.id));
        row.appendChild(createCell(new Date(entry.checkIn).toLocaleString()));
        row.appendChild(createCell(new Date(entry.checkOut).toLocaleString()));
        
        // Add edit and delete buttons
        const actionsCell = document.createElement('td');
        
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.onclick = () => editEntry(entry);
        actionsCell.appendChild(editButton);
        
        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.onclick = () => deleteEntry(entry.id);
        actionsCell.appendChild(deleteButton);
        
        row.appendChild(actionsCell);
        display.appendChild(row);
    });
};

const editEntry = (entry) => {
    const form = document.querySelector('#createEntryForm');
    const checkInDate = new Date(entry.checkIn);
    const checkOutDate = new Date(entry.checkOut);
    
    form.querySelector('[name="checkInDate"]').value = checkInDate.toISOString().split('T')[0];
    form.querySelector('[name="checkInTime"]').value = checkInDate.toTimeString().split(' ')[0];
    form.querySelector('[name="checkOutDate"]').value = checkOutDate.toISOString().split('T')[0];
    form.querySelector('[name="checkOutTime"]').value = checkOutDate.toTimeString().split(' ')[0];
    
    // Store the entry ID for update
    form.dataset.editId = entry.id;
};

const deleteEntry = (id) => {
    if (confirm('Are you sure you want to delete this entry?')) {
        fetch(`${URL}/entries/${id}`, {
            method: 'DELETE'
        }).then(() => {
            entries = entries.filter(entry => entry.id !== id);
            renderEntries();
        });
    }
};

// Kategorien und Tags laden für die Auswahlfelder
async function loadCategories() {
    const res = await fetch('/categories');
    const categories = await res.json();
    const categorySelect = document.getElementById('categorySelect');
    categorySelect.innerHTML = '';
    categories.forEach(cat => {
        const opt = document.createElement('option');
        opt.value = cat.id;
        opt.textContent = cat.title;
        categorySelect.appendChild(opt);
    });
}
async function loadTags() {
    const res = await fetch('/tags');
    const tags = await res.json();
    const tagSelect = document.getElementById('tagSelect');
    tagSelect.innerHTML = '';
    tags.forEach(tag => {
        const opt = document.createElement('option');
        opt.value = tag.id;
        opt.textContent = tag.title;
        tagSelect.appendChild(opt);
    });
}
// Beim Laden der Seite Auswahlfelder initialisieren
window.addEventListener('DOMContentLoaded', () => {
    loadCategories();
    loadTags();
});
// Beim Erstellen/Bearbeiten von Einträgen Kategorie und Tags mitgeben
// Passe createEntry und updateEntry entsprechend an

document.addEventListener('DOMContentLoaded', function(){
    const createEntryForm = document.querySelector('#createEntryForm');
    createEntryForm.addEventListener('submit', createEntry);
    indexEntries();
});