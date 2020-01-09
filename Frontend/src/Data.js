import React, { useState, useEffect } from 'react';
import Facade from './login/ApiFacade';

export default function Data() {
  const [data, setData] = useState([]);

  useEffect(() => {
    Facade.fetchPersons().then(res => setData(res));
  },[])

  const setNewData = (newData) => {
    setData(newData);
  }
  
  return (
    <div className="container">
      <h3>Persons</h3>
      <DataTable data={data} />
      <Search setNewData={setNewData} />
      <Hobby setNewData={setNewData} />
    </div>
  )
}

function DataTable({ data }) {
  return (
    <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col">Address</th>
            <th scope="col">Hobbies</th>
          </tr>
        </thead>
        <tbody>
          {data.map((person) => 
            <tr key={person.id}>
              <td>{person.firstName} {person.lastName}</td>
              <td>{person.email}</td>
              <td>{person.phone}</td>
              <td>{person.address.street}, {person.address.zip}, {person.address.city}</td>
              <td>{person.hobbies.map(hobby => hobby.name + " ")}</td>
            </tr> 
          )}
        </tbody>
      </table>
  )
}

function Search({ setNewData }) {
  const [search, setSearch] = useState({"term": "id", "search": "1"});

  const onSubmit = (evt) => {
    evt.preventDefault();
    Facade.fetchPersonBySearch(search.term, search.search)
      .then(res => Array.isArray(res) ? setNewData(res) : setNewData([res]))
      .catch(e => setNewData([]));
  }

  const onChange = (evt) => {
    setSearch({ ...search, [evt.target.id]: evt.target.value })
  }

  return (
    <form className="container" onSubmit={onSubmit} onChange={onChange}>
      <div className="row">
        <div className="input-group col-sm-3">
          <div className="input-group-prepend">
            <label className="input-group-text">Search Term</label>
          </div>
          <select className="custom-select" id="term">
            <option value="id">Id</option>
            <option value="email">Email</option>
            <option value="phone">Phone</option>
          </select>
        </div>
        <div className="input-group col-sm-6">
          <input type="text" id="search" className="form-control" placeholder="Search..."></input>
        </div>
        <div className="input-group col-sm-3">
          <input type="submit" className="btn btn-dark" value="Search" />
        </div>
      </div>
    </form>
  )
}

function Hobby({ setNewData }) {
  const [hobby, setHobby] = useState("1");
  const [hobbies, setHobbies] = useState([]);

  useEffect(() => {
    Facade.fetchAllHobbies().then(res => setHobbies(res));
  }, [])

  const onSubmit = (evt) => {
    evt.preventDefault();
    Facade.fetchHobby(hobby)
      .then(res => Array.isArray(res.persons) ? setNewData(res.persons) : setNewData([res.persons]))
      .catch(e => setNewData([]));
  }

  const onChange = (evt) => {
    setHobby(evt.target.value)
  }

  return (
    <form className="container" onSubmit={onSubmit} onChange={onChange}>
      <div className="row">
        <div className="input-group col-sm-3">
          <div className="input-group-prepend">
            <label className="input-group-text">Hobby</label>
          </div>
          <select className="custom-select" id="hobby">
            {hobbies.map(hobby => (
              <option key={hobby.id} value={hobby.id}>{hobby.name}</option>
            ))}
          </select>
        </div>
        <div className="input-group col-sm-3">
          <input type="submit" className="btn btn-dark" />
        </div>
      </div>
    </form>
  )
}