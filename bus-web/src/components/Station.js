import React, { useState, useEffect } from 'react';
import axios from 'axios';

const StationPage = () => {
  const [stations, setStations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Gọi API để lấy danh sách station
  useEffect(() => {
    const fetchStations = async () => {
      try {
        const response = await axios.get('http://localhost:8080/station/list');
        setStations(response.data); // Giả sử response.data là danh sách station
        setLoading(false);
      } catch (err) {
        setError('Error fetching stations');
        setLoading(false);
      }
    };

    fetchStations();
  }, []);

  // Hiển thị thông tin loading, lỗi hoặc danh sách station
  if (loading) {
    return <p>Loading stations...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div className="container">
      <h1>Station List</h1>
      <ul className="list-group">
        {stations.map((station) => (
          <li key={station.id} className="list-group-item">
            {station.name} - Location: {station.latitude}, {station.longitude}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StationPage;
