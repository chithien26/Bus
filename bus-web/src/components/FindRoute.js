import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Polyline } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import axios from 'axios';
import '../style/timduong.css';

const TimDuong = () => {
  const [departure, setDeparture] = useState('');
  const [destination, setDestination] = useState('');
  const [startPosition, setStartPosition] = useState(null);
  const [endPosition, setEndPosition] = useState(null);
  const [stations, setStations] = useState([]);
  const [routeStations, setRouteStations] = useState([]);

  useEffect(() => {
    // Gọi API để lấy danh sách trạm xe buýt
    const loadStations = async () => {
      try {
        let res = await axios.get('http://localhost:8080/station');
        setStations(res.data);
      } catch (error) {
        console.error('Error loading stations:', error);
      }
    };
    loadStations();
  }, []);

  const findNearestStation = (lat, lng) => {
    let nearestStation = null;
    let minDistance = Infinity;

    stations.forEach(station => {
      const distance = getDistance(lat, lng, station.latitude, station.longitude);
      if (distance < minDistance) {
        minDistance = distance;
        nearestStation = station;
      }
    });
    return nearestStation;
  };

  const getDistance = (lat1, lon1, lat2, lon2) => {
    const R = 6371; // Radius of the Earth in km
    const dLat = ((lat2 - lat1) * Math.PI) / 180;
    const dLon = ((lon2 - lon1) * Math.PI) / 180;
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos((lat1 * Math.PI) / 180) * Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in km
  };

  const handleFindRoute = async () => {
    try {
      // Giả sử bạn có một API để tìm đường giữa hai trạm gần nhất
      let startStation = findNearestStation(startPosition.lat, startPosition.lng);
      let endStation = findNearestStation(endPosition.lat, endPosition.lng);

      let res = await axios.get(`http://localhost:8080/route/find?startStationId=${startStation.id}&endStationId=${endStation.id}`);
      setRouteStations(res.data);
    } catch (error) {
      console.error('Error finding route:', error);
    }
  };

  return (
    <div className="timduong-container">
      <div className="input-container">
        <input
          type="text"
          placeholder="Địa chỉ đi"
          value={departure}
          onChange={(e) => setDeparture(e.target.value)}
        />
        <input
          type="text"
          placeholder="Địa chỉ đến"
          value={destination}
          onChange={(e) => setDestination(e.target.value)}
        />
        <button onClick={handleFindRoute}>Tìm đường</button>
      </div>

      <MapContainer center={[10.762622, 106.660172]} zoom={13} style={{ height: '500px', width: '100%' }}>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {startPosition && <Marker position={startPosition}></Marker>}
        {endPosition && <Marker position={endPosition}></Marker>}

        {routeStations.length > 0 && (
          <Polyline positions={routeStations.map(station => [station.latitude, station.longitude])} color="blue" />
        )}
      </MapContainer>
    </div>
  );
};

export default TimDuong;
