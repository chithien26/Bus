import React, { useEffect, useRef, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import { ListGroup, Button } from 'react-bootstrap';
import L from 'leaflet';
import axios from 'axios';

const startIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [41, 41],
});

const endIcon = new L.Icon({
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [41, 41],
});

const stationIcon = new L.Icon({
  iconUrl: 'https://cdn-icons-png.flaticon.com/512/3448/3448339.png',
  iconSize: [40, 51],
  iconAnchor: [10, 30],
  popupAnchor: [0, -30],
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  shadowSize: [40, 40],
});

const TimDuong = () => {
  const [listStation, setListStation] = useState([]);
  const [listRouteStation, setListRouteStation] = useState([]);
  const [startAddress, setStartAddress] = useState('');
  const [endAddress, setEndAddress] = useState('');
  const [mapPosition, setMapPosition] = useState([10.762622, 106.660172]);
  const [markers, setMarkers] = useState({ start: null, end: null });

  const [startSuggestions, setStartSuggestions] = useState([]);
  const [endSuggestions, setEndSuggestions] = useState([]);

  const debounceTimeoutRef = useRef(null);

  useEffect(() => {
    const loadStations = async () => {
      let res = await axios.get('/station');
      setListStation(res.data);
    };
    loadStations();
  }, []);

  useEffect(() => {
    if (listRouteStation.length > 0) {
      const firstStation = listRouteStation[0];
      setMapPosition([firstStation.latitude, firstStation.longitude]);
    }
  }, [listRouteStation]);

  const geocode = async (query) => {
    try {
      const viewbox = '106.4000,11.1000,107.0000,10.4000';
      const response = await fetch(`https://nominatim.openstreetmap.org/search?q=${query}&format=json&viewbox=${viewbox}&bounded=1`);
      return await response.json();
    } catch (error) {
      console.error('Error in geocoding:', error);
      return [];
    }
  };

  const handleSearchChange = async (event, type) => {
    const query = event.target.value;
    if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);

    debounceTimeoutRef.current = setTimeout(async () => {
      if (query.length > 0) {
        const suggestions = await geocode(query);
        if (type === 'start') setStartSuggestions(suggestions);
        else setEndSuggestions(suggestions);
      } else {
        setStartSuggestions([]);
        setEndSuggestions([]);
      }
    }, 300);
  };

  const getDistance = (lat1, lon1, lat2, lon2) => {
    const toRad = (value) => (value * Math.PI) / 180;
    const R = 6371;
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
      if (distance < nearest.distance) return { ...station, distance };
      return nearest;
    }, { distance: Infinity });
  };

  const handleSuggestionClick = (suggestion, type) => {
    const { display_name, lat, lon } = suggestion;
    if (type === 'start') {
      setStartAddress(display_name);
      const nearestStation = findNearestStation(parseFloat(lat), parseFloat(lon), listStation);
      setMarkers(prev => ({ ...prev, start: nearestStation }));
    } else if (type === 'end') {
      setEndAddress(display_name);
      const nearestStation = findNearestStation(parseFloat(lat), parseFloat(lon), listStation);
      setMarkers(prev => ({ ...prev, end: nearestStation }));
    }
    setMapPosition([parseFloat(lat), parseFloat(lon)]);
    setStartSuggestions([]);
    setEndSuggestions([]);
  };

  const findRouteAndStation = async () => {
    if (!markers.start || !markers.end) {
      console.error("Vui lòng chọn điểm đi và điểm đến.");
      return;
    }

    try {
      let resRoute = await axios.get(`http://localhost:8080/route/get-by-two-station?startStationId=${markers.start.id}&endStationId=${markers.end.id}`);
      let routeId = resRoute.data.id;

      let resRouteStations = await axios.get(`http://localhost:8080/route-station/get-route-station-in-route?routeId=${routeId}&startStationId=${markers.start.id}&endStationId=${markers.end.id}`);
      setListRouteStation(resRouteStations.data);
    } catch (error) {
      console.error('Lỗi khi tải tuyến hoặc trạm:', error);
    }
  };

  const MAPBOX_API_KEY = 'pk.eyJ1IjoiaHV5dGh1YTAiLCJhIjoiY20wbXFjcWkzMDUyeTJycXNncG44OGoxYyJ9.GpSOzqXFCvy_HVOsKP-uHQ';

  return (
    <div className="container mt-4">
      <div className="d-flex">
        <div className="form-container" style={{ flex: 3 }}>
          <div className="form-group mb-3">
            <h1 className="mb-4">Nhập thông tin điểm đi và điểm đến</h1>
            <form>
              <label htmlFor="startAddress" className="form-label">Điểm đi</label>
              <input type='text' id='startAddress' onChange={(e) => handleSearchChange(e, 'start')} />
              {startSuggestions.length > 0 && (
                <ListGroup className="position-absolute z-index-1" style={{ maxHeight: '200px', overflowY: 'auto', maxWidth: '500px' }}>
                  {startSuggestions.map((suggestion, index) => (
                    <ListGroup.Item key={index} action onClick={() => handleSuggestionClick(suggestion, 'start')}>
                      {suggestion.display_name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              )}
              <br />
              <label htmlFor="endAddress" className="form-label">Điểm đến</label>
              <input type='text' id='endAddress' onChange={(e) => handleSearchChange(e, 'end')} />
              {endSuggestions.length > 0 && (
                <ListGroup className="position-absolute z-index-1" style={{ maxHeight: '200px', overflowY: 'auto', width: '100%' }}>
                  {endSuggestions.map((suggestion, index) => (
                    <ListGroup.Item key={index} action onClick={() => handleSuggestionClick(suggestion, 'end')}>
                      {suggestion.display_name}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              )}
              <Button variant="success" className="w-100 mb-2" onClick={findRouteAndStation}>
                Tìm kiếm
              </Button>
            </form>
          </div>
        </div>
        <div className="map-container" style={{ flex: 7, height: '700px' }}>
          <MapContainer center={mapPosition} zoom={13} style={{ height: '100%', width: '100%' }}>
            <TileLayer
              url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/512/{z}/{x}/{y}@2x?access_token=${MAPBOX_API_KEY}`}
              attribution='&copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a>'
            />
            {markers.start && (
              <Marker position={[markers.start.latitude, markers.start.longitude]} icon={startIcon}>
                <Popup>Điểm đi: {startAddress}</Popup>
              </Marker>
            )}
            {markers.end && (
              <Marker position={[markers.end.latitude, markers.end.longitude]} icon={endIcon}>
                <Popup>Điểm đến: {endAddress}</Popup>
              </Marker>
            )}
            {listRouteStation.map((station, index) => (
              <Marker key={index} position={[station.latitude, station.longitude]} icon={stationIcon}>
                <Popup>{station.name}</Popup>
              </Marker>
            ))}
          </MapContainer>
        </div>
      </div>
    </div>
  );
};

export default TimDuong;
