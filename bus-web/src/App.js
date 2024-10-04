import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './layout/Header';
import StationPage from './components/StationPage';
import HomePage from './components/HomePage';
import RoutePage from './components/RoutePage';
import FindRoute from './components/FindRoute';
import Login from './components/Login';
import RouteDetailPage from './components/RouteDetailPage';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/route" element={<RoutePage />} />
        <Route path="/station" element={<StationPage />} />
        <Route path="/find-route" element={<FindRoute />} />
        <Route path="/login" element={<Login />} />
        <Route path="/route/:id" element={<RouteDetailPage  />} />
      </Routes>
    </Router>
  );
}

export default App;
