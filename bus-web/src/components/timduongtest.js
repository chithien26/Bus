import React, { useEffect, useRef, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MapContainer, TileLayer, Marker, Popup, Polyline, useMapEvents } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import { Modal, ListGroup, Form, InputGroup, Button } from 'react-bootstrap';
import L from 'leaflet';
import '../style/timduong.css';
import APIs from '../config/APIs';
import { endpoints } from '../config/APIs';
import axios from 'axios';

// Định nghĩa các icon cho điểm đi, điểm đến, và trạm
const startIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [41, 41]
});

const endIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [41, 41]
});
const stationIcon = new L.Icon({
  iconUrl: 'https://cdn-icons-png.flaticon.com/512/3448/3448339.png',
  iconSize: [40, 51],
  iconAnchor: [10, 30],
  popupAnchor: [0, -30],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [40, 40]
});


const TimDuong = () => {
  const [startLocation, setStartLocation] = useState('');
  const [endLocation, setEndLocation] = useState('');
  const [mapPosition, setMapPosition] = useState([10.762622, 106.660172]);
  const [selectedPosition, setSelectedPosition] = useState(null);
  const [showMenu, setShowMenu] = useState(false);
  const [menuPosition, setMenuPosition] = useState({ lat: 0, lng: 0 });
  const [markers, setMarkers] = useState({ start: null, end: null });
  const [startSuggestions, setStartSuggestions] = useState([]);
  const [endSuggestions, setEndSuggestions] = useState([]);
  const [stations, setStations] = useState([]);
  const [route, setRoute] = useState([]);
  const [tramDi, setTramDi] = useState('');
  const [tramDen, setTramDen] = useState('');
  const [stationsInRoute, setStationsInRoute] = useState([]);

  const [pathFromStartToTramDi, setPathFromStartToTramDi] = useState([]); // Đường từ điểm đi đến trạm đi
  const [pathFromTramDiToEnd, setPathFromTramDiToEnd] = useState([]); // Đường từ trạm đi đến điểm đến
  const [pathThroughStations, setPathThroughStations] = useState([]); // Đường qua các trạm trong route
 
 
  useEffect(() => {
    const loadStations = async () => {
      try {
        let res = await APIs.get(endpoints['station']);
        setStations(res.data);
      } catch (ex) {
        console.error('Error loading stations:', ex);
      }
    };
    loadStations();
  }, []);

  const handleGetCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        position => {
          const { latitude, longitude } = position.coords;
          setStartLocation(`Latitude: ${latitude}, Longitude: ${longitude}`);
          setMapPosition([latitude, longitude]);
          setMarkers(prev => ({ ...prev, start: { lat: latitude, lng: longitude } }));
        },
        error => {
          console.error('Error getting location', error);
        }
      );
    } else {
      alert('Geolocation is not supported by this browser.');
    }
  };

  const reverseGeocode = async (lat, lng) => {
    try {
      const response = await fetch(`https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lng}&format=json`);
      const data = await response.json();
      return data.display_name || `Latitude: ${lat}, Longitude: ${lng}`;
    } catch (error) {
      console.error('Error in reverse geocoding:', error);
      return `Latitude: ${lat}, Longitude: ${lng}`;
    }
  };

  const geocode = async (query) => {
    try {
      const viewbox = '106.4000,11.1000,107.0000,10.4000';
      const response = await fetch(`https://nominatim.openstreetmap.org/search?q=${query}&format=json&viewbox=${viewbox}&bounded=1`);
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error in geocoding:', error);
      return [];
    }
  };

  const handleSelectOption = async (type) => {
    const address = await reverseGeocode(menuPosition.lat, menuPosition.lng);
    if (type === 'start') {
      setStartLocation(address);
      setMarkers(prev => ({ ...prev, start: menuPosition }));
    } else if (type === 'end') {
      setEndLocation(address);
      setMarkers(prev => ({ ...prev, end: menuPosition }));
    }
    setShowMenu(false);
  };

  const handleClickMap = (latlng) => {
    setSelectedPosition(latlng);
    setMenuPosition(latlng);
    setShowMenu(true);
  };

  const debounceTimeoutRef = useRef(null);

  const handleSearchChange = async (event, type) => {
    const query = event.target.value;

    if (debounceTimeoutRef.current) {
      clearTimeout(debounceTimeoutRef.current); // Clear previous timeout
    }

    debounceTimeoutRef.current = setTimeout(async () => {
      if (type === 'start') {
        setStartLocation(query);
        if (query.length > 2) {
          const suggestions = await geocode(query);
          setStartSuggestions(suggestions);
        } else {
          setStartSuggestions([]);
        }
      } else if (type === 'end') {
        setEndLocation(query);
        if (query.length > 2) {
          const suggestions = await geocode(query);
          setEndSuggestions(suggestions);
        } else {
          setEndSuggestions([]);
        }
      }
    }, 0); 
  };

  const handleSuggestionClick = (suggestion, type) => {
    const { display_name, lat, lon } = suggestion;
    if (type === 'start') {
      setStartLocation(display_name);
      setMarkers(prev => ({ ...prev, start: { lat: parseFloat(lat), lng: parseFloat(lon) } }));
    } else if (type === 'end') {
      setEndLocation(display_name);
      setMarkers(prev => ({ ...prev, end: { lat: parseFloat(lat), lng: parseFloat(lon) } }));
    }
    setMapPosition([parseFloat(lat), parseFloat(lon)]);
    setStartSuggestions([]);
    setEndSuggestions([]);
  };

  const MapEvents = () => {
    useMapEvents({
      click: (event) => {
        const { lat, lng } = event.latlng;
        handleClickMap({ lat, lng });
      },
    });
    return null;
  };

  const getDistance = (lat1, lon1, lat2, lon2) => {
    const toRad = (value) => (value * Math.PI) / 180;
    const R = 6371; // Radius of Earth in km
    const dLat = toRad(lat2 - lat1);
    const dLon = toRad(lon2 - lon1);
    const a = 
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
      Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  };

  const findNearestStation = (lat, lon, stations) => {
    return stations.reduce((nearest, station) => {
      const distance = getDistance(lat, lon, station.latitude, station.longitude);
      if (distance < nearest.distance) {
        return { ...station, distance };
      }
      return nearest;
    }, { distance: Infinity });
  };

  const findRouteAndStation = async (startStation, endStation) => {
    let formData = new FormData();
    formData.append('startStationId', startStation.id);
    formData.append('endStationId', endStation.id);
  
    // Console log để kiểm tra nội dung của FormData
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });
  
    try {
      // Sử dụng phương thức 'post' thay vì 'get'
      let res = await axios.get('http://localhost:8080/route/get-by-two-station', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      // Xử lý dữ liệu từ res
      return res.data;
    } catch (ex) {
      console.error('Error loading stations:', ex);
    }
  };
  

  
  const findClosestStations = async () => {
    setTramDen([]);
    setTramDi([]);
    setPathFromStartToTramDi([]);
    setPathFromTramDiToEnd([]);
    setPathThroughStations([]);
    if (markers.start && markers.end) {
      const startNearestStation = findNearestStation(markers.start.lat, markers.start.lng, stations);
      const endNearestStation = findNearestStation(markers.end.lat, markers.end.lng, stations);
      setTramDi(startNearestStation);
      setTramDen(endNearestStation);
  
      if (startNearestStation && endNearestStation) {
        try {
          // Gọi hàm để tìm trạm trên tuyến đường
          const stationsOn = await findRouteAndStation(startNearestStation, endNearestStation);
          
          
          if (!stationsOn) {
            console.log('No stations found on route.');
            return;
          }
          console.log('Stations On:', stationsOn);
  
          // Tạo path qua các trạm
          const pathThrough = [
            [startNearestStation.latitude, startNearestStation.longitude],
            ...stationsOn.map(station => [station.latitude, station.longitude]),
            [endNearestStation.latitude, endNearestStation.longitude]
          ];
  
          // Tính toán tuyến đường từ điểm bắt đầu đến trạm đầu tiên
          const responseStartToTramDi = await fetch(`https://api.mapbox.com/directions/v5/mapbox/driving/${markers.start.lng},${markers.start.lat};${startNearestStation.longitude},${startNearestStation.latitude}?geometries=geojson&access_token=${MAPBOX_API_KEY}`);
          const dataStartToTramDi = await responseStartToTramDi.json();
          console.log('Start to Tram Di Data:', dataStartToTramDi);
          if (dataStartToTramDi.routes && dataStartToTramDi.routes[0] && dataStartToTramDi.routes[0].geometry) {
            setPathFromStartToTramDi(dataStartToTramDi.routes[0].geometry.coordinates.map(coord => [coord[1], coord[0]]));
          }
  
          // Tính toán tuyến đường từ trạm đi đến điểm kết thúc
          const responseTramDiToEnd = await fetch(`https://api.mapbox.com/directions/v5/mapbox/driving/${endNearestStation.longitude},${endNearestStation.latitude};${markers.end.lng},${markers.end.lat}?geometries=geojson&access_token=${MAPBOX_API_KEY}`);
          const dataTramDiToEnd = await responseTramDiToEnd.json();
          console.log('Tram Di to End Data:', dataTramDiToEnd);
          if (dataTramDiToEnd.routes && dataTramDiToEnd.routes[0] && dataTramDiToEnd.routes[0].geometry) {
            setPathFromTramDiToEnd(dataTramDiToEnd.routes[0].geometry.coordinates.map(coord => [coord[1], coord[0]]));
          }
  
          // Tính toán tuyến đường qua tất cả các trạm trong danh sách
          const pathThroughCoords = [
            [startNearestStation.longitude, startNearestStation.latitude], // Trạm đầu tiên
            ...stationsOn.map(station => [station.longitude, station.latitude]), // Các trạm tiếp theo
            [endNearestStation.longitude, endNearestStation.latitude] // Trạm cuối cùng
          ].map(coord => coord.join(',')).join(';');
  
          const responsePathThroughStations = await fetch(`https://api.mapbox.com/directions/v5/mapbox/driving/${pathThroughCoords}?geometries=geojson&access_token=${MAPBOX_API_KEY}`);
          const dataPathThroughStations = await responsePathThroughStations.json();
          console.log('Path Through Stations Data:', dataPathThroughStations);
          if (dataPathThroughStations.routes && dataPathThroughStations.routes[0] && dataPathThroughStations.routes[0].geometry) {
            setPathThroughStations(dataPathThroughStations.routes[0].geometry.coordinates.map(coord => [coord[1], coord[0]]));
          }
          
        } catch (ex) {
          console.error('Error fetching directions:', ex);
        }
      } else {
        console.log('Cannot find nearest stations for both start and end.');
      }
    } else {
      console.log('Both start and end locations must be set to find nearest stations.');
    }
  };
  
  
  const MAPBOX_API_KEY = 'pk.eyJ1IjoiaHV5dGh1YTAiLCJhIjoiY20wbXFjcWkzMDUyeTJycXNncG44OGoxYyJ9.GpSOzqXFCvy_HVOsKP-uHQ';
  return (
    <div className="container mt-4">
      <div className="d-flex">
        <div className="form-container" style={{ flex: 3 }}>
          <form>
            <div className="form-group mb-3">
              <h1 className="mb-4">Nhập thông tin điểm đi và điểm đến</h1>
              <label htmlFor="startLocation" className="form-label">Điểm đi</label>
              <InputGroup>
                <Form.Control
                  id="startLocation"
                  type="text"
                  value={startLocation}
                  onChange={(e) => handleSearchChange(e, 'start')}
                  placeholder="Nhập điểm đi"
                />
                <Button variant="dark" onClick={handleGetCurrentLocation}>
                  Lấy vị trí hiện tại
                </Button>
              </InputGroup>
              {startSuggestions.length > 0 && (
                <ListGroup className="position-absolute z-index-1" style={{ maxHeight: '200px', overflowY: 'auto', width: '100%' }}>
                  {startSuggestions.map((suggestion, index) => (
                    <ListGroup.Item key={index} action onClick={() => handleSuggestionClick(suggestion, 'start')}>
                      {suggestion.display_name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              )}
            </div>
            <div className="form-group mb-4">
              <label htmlFor="endLocation" className="form-label">Điểm đến</label>
              <InputGroup>
                <Form.Control
                  id="endLocation"
                  type="text"
                  value={endLocation}
                  onChange={(e) => handleSearchChange(e, 'end')}
                  placeholder="Nhập điểm đến"
                />
              </InputGroup>
              {endSuggestions.length > 0 && (
                <ListGroup className="position-absolute z-index-1" style={{ maxHeight: '200px', overflowY: 'auto', width: '100%' }}>
                  {endSuggestions.map((suggestion, index) => (
                    <ListGroup.Item key={index} action onClick={() => handleSuggestionClick(suggestion, 'end')}>
                      {suggestion.display_name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              )}
            </div>
            <Button variant="success" className="w-100 mb-2" onClick={findClosestStations}>
              Tìm kiếm
            </Button>
            {route.length > 0 && (
              <div className="mt-4 route-info">
                <h5 className="text-primary">Tuyến phải đi:</h5>
                <ListGroup className="mb-3">
                  {route.map((route, index) => (
                    <ListGroup.Item key={index} className="route-item bg-light">
                      <i className="bi bi-signpost-2"></i> {route.name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
                
                <h5 className="text-primary">Trạm:</h5>
                <ListGroup className="mb-3">
                  {stationsInRoute.map((station, index) => (
                    <ListGroup.Item key={index} className="station-item bg-light">
                      <i className="bi bi-building"></i> {station.name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>

                <div className="travel-guide bg-info p-3 rounded text-white">
                  <h6 className="mb-2">Hướng dẫn di chuyển</h6>
                  <p className="mb-1">Từ điểm đi di chuyển đến <strong>{tramDi.name}</strong></p>
                  <p>Từ trạm cuối di chuyển đến <strong>{tramDen.name}</strong></p>
                </div>
              </div>
            )}

          </form>
        </div>
        <div className="map-container" style={{ flex: 7, height: '700px' }}>
        <MapContainer center={mapPosition} zoom={13} style={{ height: '100%', width: '100%' }}>
          <TileLayer
            url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/{z}/{x}/{y}?access_token=${MAPBOX_API_KEY}`}
            attribution='&copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a> contributors'
          />
          {markers.start && <Marker position={markers.start} icon={startIcon}><Popup>Điểm đi</Popup></Marker>}
          {markers.end && <Marker position={markers.end} icon={endIcon}><Popup>Điểm đến</Popup></Marker>}
          {stations.map(station => (
            <Marker key={station.id} position={[station.latitude, station.longitude]} icon={stationIcon}>
              <Popup>{station.name}</Popup>
            </Marker>
          ))}
          <Polyline positions={pathFromStartToTramDi} color="blue" />
          <Polyline positions={pathFromTramDiToEnd} color="green" />
          <Polyline positions={pathThroughStations} color="red" />
         
          <MapEvents />
        </MapContainer>

        </div>
      </div>
      <Modal show={showMenu} onHide={() => setShowMenu(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Chọn tùy chọn</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ListGroup>
            <ListGroup.Item action onClick={() => handleSelectOption('start')}>
              Điểm đi
            </ListGroup.Item>
            <ListGroup.Item action onClick={() => handleSelectOption('end')}>
              Điểm đến
            </ListGroup.Item>
            <ListGroup.Item action onClick={() => setShowMenu(false)}>
              Thoát
            </ListGroup.Item>
          </ListGroup>
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default TimDuong;