// App.js
import React, { createContext, useReducer } from 'react';
import { BrowserRouter as Router, Route, Routes, BrowserRouter } from 'react-router-dom';
import Header from './layout/Header';
import StationPage from './components/StationPage';
import HomePage from './components/HomePage';
import RoutePage from './components/RoutePage';
import FindRoute from './components/FindRoute';
import Login from './components/Login';
import RouteDetailPage from './components/RouteDetailPage';
import MyUserReducer from './reducers/MyUserReducer';
import { Container } from 'react-bootstrap';

export const MyUserContext = createContext();
export const MyDispatchContext = createContext();

function App() {
  const [user, dispatch] = useReducer(MyUserReducer, null);
  return (
    <MyUserContext.Provider value={user}>
      <MyDispatchContext.Provider value={dispatch}>
        <BrowserRouter>
          <Header />
          <Container>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/route" element={<RoutePage />} />
              <Route path="/station" element={<StationPage />} />
              <Route path="/find-route" element={<FindRoute />} />
              <Route path="/login" element={<Login />} />
              <Route path="/route/:id" element={<RouteDetailPage />} />
            </Routes>
          </Container>
        </BrowserRouter>
      </MyDispatchContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
