import React, { useEffect, useRef, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import { ListGroup, Button } from 'react-bootstrap';
import L from 'leaflet';
import axios from 'axios';

// Định nghĩa biểu tượng cho các loại marker
const startIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
});

const endIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
});

const stationIcon = new L.Icon({
  iconUrl: 'https://cdn-icons-png.flaticon.com/512/3448/3448339.png',
  iconSize: [40, 51],
  iconAnchor: [10, 30],
  popupAnchor: [0, -30],
});

const TimDuong = () => {
  const [listStation, setListStation] = useState([]);
  const [startAddress, setStartAddress] = useState('');
  const [endAddress, setEndAddress] = useState('');
  const [startSuggestions, setStartSuggestions] = useState([]); // Khởi tạo như một mảng rỗng
  const [endSuggestions, setEndSuggestions] = useState([]); // Khởi tạo như một mảng rỗng
  const [markers, setMarkers] = useState({ start: null, end: null });
  const [listRouteStation, setListRouteStation] = useState([]);
  const debounceTimeoutRef = useRef(null);
  const MAPBOX_API_KEY = 'pk.eyJ1IjoiaHV5dGh1YTAiLCJhIjoiY20wbXFjcWkzMDUyeTJycXNncG44OGoxYyJ9.GpSOzqXFCvy_HVOsKP-uHQ';

  // Tải danh sách trạm từ API
  useEffect(() => {
    const loadStations = async () => {
      const res = await axios.get('http://localhost:8080/station');
      setListStation(res.data);
    };
    loadStations();
  }, []);

  // Hàm geocode
  const geocode = async (query) => {
    const bbox = '106.573246,10.762622,106.707797,10.869645'; // (minLng, minLat, maxLng, maxLat)
    const response = await fetch(`https://nominatim.openstreetmap.org/search?q=${query}&format=json&bounded=1&viewbox=${bbox}&addressdetails=1`);
    return await response.json();
  };


  // Hàm xử lý thay đổi tìm kiếm
  const handleSearchChange = async (event, type) => {
    const query = event.target.value;

    // Cập nhật địa chỉ
    if (type === 'start') {
      setStartAddress(query);
    } else {
      setEndAddress(query);
    }

    if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);

    debounceTimeoutRef.current = setTimeout(async () => {
      if (query.length > 0) {
        const suggestions = await geocode(query);
        if (type === 'start') setStartSuggestions(suggestions);
        else setEndSuggestions(suggestions);
      } else {
        // Nếu không có gì được nhập, xóa gợi ý
        if (type === 'start') setStartSuggestions([]);
        else setEndSuggestions([]);
      }
    }, 500);
  };

  // Hàm tìm trạm gần nhất
  const findNearestStation = (lat, lon) => {
    return listStation.reduce((nearest, station) => {
      const distance = getDistance(lat, lon, station.latitude, station.longitude);
      if (distance < nearest.distance) return { ...station, distance };
      return nearest;
    }, { distance: Infinity });
  };

  // Hàm tính khoảng cách giữa 2 tọa độ
  const getDistance = (lat1, lon1, lat2, lon2) => {
    const toRad = (value) => (value * Math.PI) / 180;
    const R = 6371; // Đường kính trái đất
    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);
    const a = Math.sin(dLat / 2) ** 2 + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(dLon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Trả về khoảng cách
  };

  // Hàm xử lý khi nhấn vào gợi ý
  const handleSuggestionClick = (suggestion, type) => {
    const newMarker = { latitude: suggestion.lat, longitude: suggestion.lon };
    setMarkers((prevMarkers) => ({ ...prevMarkers, [type]: newMarker }));
    if (type === 'start') {
      setStartAddress(suggestion.display_name);
      setStartSuggestions([]); // Xóa gợi ý sau khi chọn
    } else {
      setEndAddress(suggestion.display_name);
      setEndSuggestions([]); // Xóa gợi ý sau khi chọn
    }
  };

  // Hàm tìm tuyến đường
  const findRoute = async () => {
    if (!markers.start || !markers.end) {
      console.error("Vui lòng chọn điểm đi và điểm đến hợp lệ.");
      return;
    }

    const nearestStartStation = findNearestStation(markers.start.latitude, markers.start.longitude);
    const nearestEndStation = findNearestStation(markers.end.latitude, markers.end.longitude);

    try {
      const routeRes = await axios.get(`http://localhost:8080/route/get-by-two-station?startStationId=${nearestStartStation.id}&endStationId=${nearestEndStation.id}`);
      const routeId = routeRes.data.id;

      const stationsRes = await axios.get(`http://localhost:8080/route-station/get-route-station-in-route?routeId=${routeId}&startStationId=${nearestStartStation.id}&endStationId=${nearestEndStation.id}`);
      setListRouteStation(stationsRes.data);
      
    } catch (error) {
      console.error('Error loading route:', error);
      alert("Không tìm thấy tuyến nào!");
    }
  };

  return (
    <div className="container mt-3">
      <h1>Tìm Đường</h1>
      <div className="row">
        <div className="col-md-5">
          <div className="form-group">
            <label htmlFor="startAddress">Điểm bắt đầu</label>
            <input
              type="text"
              id="startAddress"
              value={startAddress}
              onChange={(e) => handleSearchChange(e, 'start')}
              className="form-control"
            />
            <ListGroup>
              {startSuggestions.map((suggestion) => (
                <ListGroup.Item
                  key={suggestion.place_id}
                  onClick={() => handleSuggestionClick(suggestion, 'start')}
                >
                  {suggestion.display_name}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </div>
          <div className="form-group">
            <label htmlFor="endAddress">Điểm kết thúc</label>
            <input
              type="text"
              id="endAddress"
              value={endAddress}
              onChange={(e) => handleSearchChange(e, 'end')}
              className="form-control"
            />
            <ListGroup>
              {endSuggestions.map((suggestion) => (
                <ListGroup.Item
                  key={suggestion.place_id}
                  onClick={() => handleSuggestionClick(suggestion, 'end')}
                >
                  {suggestion.display_name}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </div>
          <Button onClick={findRoute}>Tìm đường</Button>
        </div>
        <div className="col-md-7">
          <MapContainer center={[10.762622, 106.660172]} zoom={13} style={{ height: '400px' }}>
            <TileLayer
              url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/{z}/{x}/{y}?access_token=${MAPBOX_API_KEY}`}
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            />

            {markers.start && (
              <Marker position={[markers.start.latitude, markers.start.longitude]} icon={startIcon}>
                <Popup>Điểm bắt đầu: {startAddress}</Popup>
              </Marker>
            )}

            {markers.end && (
              <Marker position={[markers.end.latitude, markers.end.longitude]} icon={endIcon}>
                <Popup>Điểm kết thúc: {endAddress}</Popup>
              </Marker>
            )}

            {/* Nối các trạm trong listRouteStation nếu có dữ liệu */}
            {listRouteStation.length > 0 ? (
              <>
                {listRouteStation.map((routeStation) => (
                  <Marker key={routeStation.station.id} position={[routeStation.station.latitude, routeStation.station.longitude]} icon={stationIcon}>
                    <Popup>Trạm: {routeStation.station.name}</Popup>
                  </Marker>
                ))}

                {/* Lấy tọa độ của các trạm trong listRouteStation */}
                <Polyline
                  positions={listRouteStation.map(routeStation => [routeStation.station.latitude, routeStation.station.longitude])}
                  color="blue" // Màu của đường nối
                  weight={5} // Độ dày của đường
                />
              </>
            ) : (
              // Nếu listRouteStation trống, hiển thị tất cả trạm từ listStation
              listStation.map((station) => (
                <Marker key={station.id} position={[station.latitude, station.longitude]} icon={stationIcon}>
                  <Popup>Trạm: {station.name}</Popup>
                </Marker>
              ))
            )}
          </MapContainer>


        </div>
      </div>
    </div>
  );
};

export default TimDuong;
