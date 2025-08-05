const API_URL = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    const pathname = window.location.pathname;

    if (pathname.includes('gallery')) loadGallery();
    if (pathname.includes('artwork')) loadArtworkDetails();
    if (pathname.includes('add-edit')) handleFormSubmit();
});



function viewArt(id) {
    window.location.href = `artwork.html?id=${id}`;
}

async function loadArtworkDetails() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    const res = await fetch(`${API_URL}/artwork/${id}`);
    const art = await res.json();

    const artDiv = document.getElementById('artDetails');
    artDiv.innerHTML = `
        <h1 class="art-title">${art.title}</h1>
        <img src="data:${art.imageType};base64,${art.imageData}" alt="${art.title}" class="art-image mb-4">

        <div class="art-details text-start mx-auto" style="max-width: 600px;">
            <p><strong>Artist:</strong> ${art.artist}</p>
            <p><strong>Medium:</strong> ${art.medium}</p>
            <p><strong>Year:</strong> ${art.year}</p>
        </div>
    `;

    document.getElementById('editBtn').onclick = () => {
        window.location.href = `add-edit.html?id=${id}`;
    };
}


async function deleteArtWork() {
    const id = new URLSearchParams(window.location.search).get('id');
    await fetch(`${API_URL}/artwork/${id}`, { method: 'DELETE' });
    alert('Deleted successfully');
    window.location.href = 'gallery.html';
}

function handleFormSubmit() {
    const form = document.getElementById('artworkForm');
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');

    if (id) loadForEdit(id);

    form.onsubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('title', document.getElementById('title').value);
        formData.append('artist', document.getElementById('artist').value);
        formData.append('medium', document.getElementById('medium').value);
        formData.append('year', document.getElementById('year').value);
        const file = document.getElementById('imageFile').files[0];
        if (file) formData.append('imageFile', file);

        formData.append('artwork', new Blob([JSON.stringify({
            title: formData.get('title'),
            artist: formData.get('artist'),
            medium: formData.get('medium'),
            year: formData.get('year'),
        })], { type: 'application/json' }));

        const method = id ? 'PUT' : 'POST';
        const endpoint = id ? `${API_URL}/artwork/${id}` : `${API_URL}/artwork`;

        await fetch(endpoint, {
            method,
            body: formData
        });

        alert('Artwork saved successfully!');
        window.location.href = 'gallery.html';
    };
}

async function loadForEdit(id) {
    const res = await fetch(`${API_URL}/artwork/${id}`);
    const art = await res.json();
    document.getElementById('title').value = art.title;
    document.getElementById('artist').value = art.artist;
    document.getElementById('medium').value = art.medium;
    document.getElementById('year').value = art.year;
}

document.addEventListener('DOMContentLoaded', () => {
    const pathname = window.location.pathname;

    if (pathname.includes('gallery')) {
        loadGallery();
        const searchInput = document.getElementById('searchInput');
        searchInput.addEventListener('input', () => {
            const keyword = searchInput.value.trim();
            if (keyword.length === 0) {
                loadGallery(); // Reset to all
            } else {
                searchArtworks(keyword);
            }
        });
    }

    if (pathname.includes('artwork')) loadArtworkDetails();
    if (pathname.includes('add-edit')) handleFormSubmit();
});

async function loadGallery() {
    const res = await fetch(`${API_URL}/artworks`);
    const artworks = await res.json();
    renderGallery(artworks);
}

async function searchArtworks(keyword) {
    try {
        const res = await fetch(`${API_URL}/artwork/search?keyword=${encodeURIComponent(keyword)}`);
        const artworks = await res.json();
        renderGallery(artworks);
    } catch (e) {
        console.error("Search failed:", e);
        document.getElementById('gallery').innerHTML = `<p>No results found.</p>`;
    }
}

function renderGallery(artworks) {
    const gallery = document.getElementById('gallery');
    gallery.innerHTML = '';

    if (!artworks.length) {
        gallery.innerHTML = `<div class="text-center text-muted">No artworks found.</div>`;
        return;
    }

    artworks.forEach(art => {
        const col = document.createElement('div');
        col.className = 'col';

        col.innerHTML = `
            <div class="card card-custom h-100" onclick="viewArt(${art.id})">
                <img src="data:${art.imageType};base64,${art.imageData}" class="card-img-top" alt="${art.title}">
                <div class="card-body p-2">
                    <h5 class="card-title">${art.title}</h5>
                </div>
            </div>
        `;

        gallery.appendChild(col);
    });
}


