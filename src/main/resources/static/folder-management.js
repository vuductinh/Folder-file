document.addEventListener('DOMContentLoaded', getFolders);
const btn = document.querySelector("button");
const closeSpan = document.getElementsByClassName("close")[0];

function showFiles(folderId) {
    fetch(`/get-files/getFiles?folderId=${folderId}`)
        .then(response => response.json())
        .then(item => {
            document.getElementById("currentPath").textContent = item.path;
            const fileDisplay = document.getElementById("file-display");
            fileDisplay.innerHTML = "";
            item.files.forEach(file => {
                const row = document.createElement("tr");
                const updatedOn = file.updated ? new Date(file.updated).toLocaleDateString('en-GB') : "N/A";
                const createdDate = file.created ? new Date(file.created).toLocaleDateString('en-GB') : "N/A";
                row.innerHTML = `
                <td onclick="showFiles(${file.id})"><i class="fa fa-folder" aria-hidden="true"></i> ${file.name}</td>
                <td>${updatedOn}</td>
                <td>${createdDate}</td>
                <td><button class="btn-sm btn-danger">Change folder</button></td>
            `;

                fileDisplay.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
}

function renderTree(data, container) {
        data.forEach(item => {
            const li = document.createElement('li');

            const node = document.createElement('span');
            node.className = 'folder';
            node.innerHTML = `
                ${item.children && item.children.length > 0
                    ? `<i class="fa fa-caret-right" onclick="toggleVisibility(${item.id})"></i>`
                    : ''}
                <i class="fa fa-folder" onclick="showFiles(${item.id})"></i>
                ${item.name}
            `;

            li.appendChild(node);
            if (item.children && item.children.length > 0) {
                const ul = document.createElement('ul');
                ul.id = 'folder-' + item.id;
                ul.style.display = 'none';
                renderTree(item.children, ul);
                li.appendChild(ul);
            }

            container.appendChild(li);
        });
    }

function toggleVisibility(folderId) {
    var folder = document.getElementById('folder-'+folderId);
        if (folder) {
            if (folder.style.display === "none") {
                folder.style.display = "block";
                folder.classList.add("active");
            } else {
                folder.style.display = "none";
                folder.classList.remove("active");
            }
        }
}

var preItems = [];

function getFolders(){
    fetch(`/get-files/getFolder`)
                .then(response => response.json())
                .then(items => {
                preItems = items;
                const treeElement = document.getElementById("tree");
                renderTree(items, treeElement);
                })
                .catch(error => console.error('Error:', error));
}

function openModal() {
    const modal = document.getElementById("addFolderModal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("addFolderModal");
    modal.style.display = "none";
}

function addFolder(){
   var name = document.getElementById("folderName").value;
   if(!name){
      alert('Name is not null');
      return;
   }
   if(preItems.filter(x=>x.name == name).length > 0){
      alert('Name is exit');
      return;
   }
   var response = fetch('/get-files/create' + `?idParent=${0}&name=${encodeURIComponent(name)}`, {
                              method: "POST",
                          });

   //preItems.push(item);
   const treeElement = document.getElementById("tree");
   renderTree(preItems, treeElement);
   document.getElementById("folderName").value = '';
   closeModal();
   getFolders();
}

function uploadFile(){
    const fileInput = document.getElementById("fileInput");
    fileInput.click();
    }

async function onSelectFile() {
    const fileInput = document.getElementById("fileInput");
    const files = fileInput.files;

    if (!files || files.length === 0) {
        alert("No files selected!");
        return;
    }
    const formData = new FormData();
    formData.append("id", document.getElementById("idParent").value);
    for (let i = 0; i < files.length; i++) {
        formData.append("files[]", files[i]);
    }

    try {
        const response = await fetch("/get-files/upload", {
            method: "POST",
            body: formData,
        });

        if (response.ok) {
            getFolders();
        } else {
            alert("lỗi update");
        }
    } catch (error) {

    }
}

function  backFolder(){
   var id = document.getElementById("idParent").value;
   if(id){
   showFiles(id);
   }
}