import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './layout/Header';
import StationPage from './components/Station';
import HomePage from './components/HomePage';
import RoutePage from './components/RoutePage';

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/route" element={<RoutePage />} />
        <Route path="/station" element={<StationPage />} />
      </Routes>
    </Router>
  );
}

export default App;
