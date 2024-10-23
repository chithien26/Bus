import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

// Tạo biểu tượng marker tùy chỉnh để không bị lỗi marker mặc định
const customMarkerIcon = new L.Icon({
  iconUrl: '/icons/marker.png',
  iconSize: [35, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [50, 50],
});

const StationMap = () => {
  const [stations, setStations] = useState([]);

  const MAPBOX_API_KEY = 'pk.eyJ1IjoiaHV5dGh1YTAiLCJhIjoiY20wbXFjcWkzMDUyeTJycXNncG44OGoxYyJ9.GpSOzqXFCvy_HVOsKP-uHQ';

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
        {/* TileLayer dùng để tải bản đồ từ Mapbox */}
        <TileLayer
          url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/512/{z}/{x}/{y}@2x?access_token=${MAPBOX_API_KEY}`}
          attribution='&copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a> contributors'
          tileSize={512} // Mapbox yêu cầu kích thước tile là 512
          zoomOffset={-1} // Điều chỉnh để phù hợp với kích thước 512 của Mapbox
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
