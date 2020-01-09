import React, { useState, useEffect } from 'react';
import Facade from './ApiFacade'

export default function LoggedIn() {
  const [fetch, setFetch] = useState(false);
  const [user, setUser] = useState();
  const [hobbies, setHobbies] = useState([]);

  const [newHobby, setNewHobby] = useState({id: 0, name: "", description: ""});

  useEffect(() => {
    Facade.fetchUser().then(res => setUser(res)).catch(e => console.log(e));
  }, [])

  useEffect(() => {
    Facade.fetchAllHobbies().then(res => setHobbies(res)).catch(e => console.log(e));
  }, [fetch]);


  const addHobby = (hobby) => {
    Facade.addEditHobby(hobby.id, hobby)
    .then(res => setFetch(!fetch))
    .catch(e => console.log(e));
    setNewHobby({id: 0, name: "", description: ""})
  }

  const editHobby = (id) => {
    Facade.fetchHobby(id).then(res => setNewHobby(res)).catch(e => console.log(e));
  }

  const deleteHobby = (id) => {
    Facade.deleteHobby(id).then(res => setFetch(!fetch));
  }

  // eslint-disable-next-line
  if(user.roleList != "admin") {
    return (
      <div>
        <h2>Not logged in as admin</h2>
      </div>
    ) 
  }
  return (
    <div className="container">
      <h3>Hobbies</h3>
      <DataTable data={hobbies} deleteHobby={deleteHobby} editHobby={editHobby} />
      <AddEdit addHobby={addHobby} newHobby={newHobby} />
    </div>
  ) 
}

function DataTable({ data, editHobby, deleteHobby }) {
  return (
    <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {data.map((hobby) => 
            <tr key={hobby.id}>
              <td>{hobby.name}</td>
              <td>{hobby.description}</td>
              <td>
                <button className="btn btn-primary" onClick={() => editHobby(hobby.id)}>Edit</button>
                <button className="btn btn-primary" onClick={() => deleteHobby(hobby.id)}>Remove</button>
              </td>
            </tr> 
          )}
        </tbody>
      </table>
  )
}

function AddEdit({ addHobby, newHobby }) {
  const [hobby, setHobby] = useState(newHobby);

  useEffect(() => setHobby({ ...newHobby }), [newHobby]);

  const onSubmit = (evt) => {
    evt.preventDefault();
    if(hobby.name === "" || hobby.description === "")
      return;

    addHobby(hobby);
  }

  const onChange = (evt) => {
    setHobby({ ...hobby, [evt.target.id]: evt.target.value })
  }

  return (
    <form className="container" onSubmit={onSubmit}>
      <input type="hidden" id="id" className="form-control" onChange={onChange} value={hobby.id}></input>
      <div className="row">
        <div className="input-group col-sm-5">
          <input type="text" id="name" className="form-control" placeholder="Name..." onChange={onChange} value={hobby.name}></input>
        </div>
        <div className="input-group col-sm-5">
          <input type="text" id="description" className="form-control" placeholder="Description..." onChange={onChange} value={hobby.description}></input>
        </div>
        <div className="input-group col-sm-2">
          <input type="submit" className="btn btn-dark" value={hobby.id !== 0 ? "Edit" : "Add"} />
        </div>
      </div>
    </form>
  )
}