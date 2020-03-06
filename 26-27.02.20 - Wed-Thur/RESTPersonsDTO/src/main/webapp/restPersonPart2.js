fetchFunction("http://localhost:8080/rest/api/person/all", insertAllUsersInTable);

function fetchFunction(fetchUrl, callback) {
    fetch(fetchUrl)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            callback(data);
        });
};

function insertAllUsersInTable(dataArray) {
    let printString = createTableFromArray(dataArray);
    document.getElementById("myDiv").innerHTML = printString;
};

function createTableFromArray(array) {
    let tableHead = "<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Phone</th>";
    let htmlRows = "";
    console.log(array.all);

    array.all.forEach(element => {
        let temp = "<tr>" +
            "<td>" + element.id + "</td>" +
            "<td>" + element.fName + "</td>" +
            "<td>" + element.lName + "</td>" +
            "<td>" + element.phone + "</td>" +
            "<tr>"
        htmlRows += temp;
    });

    return "<table border='1'>" + tableHead + htmlRows + "</table>";
};

//Reload data
document.getElementById("reload_btn").addEventListener('click', (event) => {
    fetchFunction("http://localhost:8080/rest/api/person/all", insertAllUsersInTable);
});

//Add new person
document.getElementById("add_new_person_btn").addEventListener('click', (event) => {
    addUser();
});

function addUser() {
    let addFirstName = document.getElementById("newFirstName").value;
    let addLastName = document.getElementById("newLastName").value;
    let addPhone = document.getElementById("newPhone").value;
    console.log(addFirstName);
    console.log(addLastName);
    console.log(addPhone);
    let options = {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            fName: addFirstName,
            lName: addLastName,
            phone: addPhone,
        })
    }
    fetch("http://localhost:8080/rest/api/person/add", options);
};

