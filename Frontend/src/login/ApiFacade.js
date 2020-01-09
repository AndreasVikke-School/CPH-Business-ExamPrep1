const URL = "http://localhost:8080/securitystarter";

function handleHttpErrors(res) {
    if (!res.ok) {
        return Promise.reject({ status: res.status, fullError: res.json() })
    }
    return res.json();
}

function ApiFacade() {
    //Insert utility-methods from a latter step (d) here
    const setToken = (token) => {
        localStorage.setItem('jwtToken', token)
    }
    const getToken = () => {
        return localStorage.getItem('jwtToken')
    }
    const loggedIn = () => {
        const loggedIn = getToken() != null;
        return loggedIn;
    }
    const logout = () => {
        localStorage.removeItem("jwtToken");
    }
    
    const makeOptions = (method, addToken, body) => {
        var opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                'Accept': 'application/json',
            }
        }
        if (addToken && loggedIn()) {
            opts.headers["x-access-token"] = getToken();
        }
        if (body) {
            opts.body = JSON.stringify(body);
        }
        return opts;
    }

    const login = (user, pass) => {
        const options = makeOptions("POST", true, { username: user, password: pass });
        return fetch(URL + "/api/login", options)
            .then(handleHttpErrors)
            .then(res => { setToken(res.token) })
    }

    const fetchUser = () => {
        const options = makeOptions("GET", true); //True add's the token
        return fetch(URL + "/api/info/user", options).then(handleHttpErrors);
    }

    const fetchPersons = () => {
        return fetch(URL + "/api/person/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchPersonBySearch = (term, search) => {
        return fetch(URL + "/api/person/" + term + "/" + search, makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchAllHobbies = () => {
        return fetch(URL + "/api/hobby/all", makeOptions("GET")).then(handleHttpErrors);
    }

    const fetchHobby = (id) => {
        return fetch(URL + "/api/hobby/" + id, makeOptions("GET")).then(handleHttpErrors);
    }

    const addEditHobby = (id, hobby) => {
        if(id === 0)
            return fetch(URL + "/api/hobby/add", makeOptions("POST", true, hobby)).then(handleHttpErrors);
        else
            return fetch(URL + "/api/hobby/edit/" + id, makeOptions("POST", true, hobby)).then(handleHttpErrors);
    }

    const deleteHobby = (id) => {
            return fetch(URL + "/api/hobby/delete/" + id, makeOptions("DELETE", true)).then(handleHttpErrors);
    }

    return {
        login,
        logout,
        fetchUser,
        fetchPersons,
        fetchPersonBySearch,
        fetchHobby,
        fetchAllHobbies,
        addEditHobby,
        deleteHobby
    }

}

export default ApiFacade();