import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

// Tạo biểu tượng marker tùy chỉnh để không bị lỗi marker mặc định
const customMarkerIcon = new L.Icon({
  iconUrl: '/icons/station-marker.png',
  iconSize: [35, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

const StationMap = () => {
  const [stations, setStations] = useState([]);

  // Fetch dữ liệu các trạm từ API
  useEffect(() => {
    const fetchStations = async () => {
      try {
        const response = await fetch('http://localhost:8080/station');
        const data = await response.json();
        setStations(data);
      } catch (error) {
        console.error('Error fetching stations:', error);
      }
    };

    fetchStations();
  }, []);

  return (
    <div style={{ height: '100vh', width: '100%' }}>
      {/* MapContainer dùng để chứa bản đồ */}
      <MapContainer
        center={[10.762622, 106.660172]} // Tọa độ trung tâm (ví dụ ở Hồ Chí Minh)
        zoom={13}
        style={{ height: '100%', width: '100%' }}
      >
        {/* TileLayer dùng để tải bản đồ từ OpenStreetMap */}
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
        />

        {/* Hiển thị các marker cho từng trạm */}
        {stations.map((station) => (
          <Marker
            key={station.id}
            position={[station.latitude, station.longitude]} // Vị trí marker (tọa độ trạm)
            icon={customMarkerIcon} // Sử dụng biểu tượng marker tùy chỉnh
          >
            {/* Popup hiện ra khi người dùng click vào marker */}
            <Popup>
              <strong>{station.name}</strong><br />
              {station.address}
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
};

export default StationMap;
