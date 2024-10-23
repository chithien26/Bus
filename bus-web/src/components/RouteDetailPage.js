import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const RouteDetailPage = () => {
    const { id } = useParams();
    const [route, setRoute] = useState(null);
    const [routeStations, setRouteStations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const MAPBOX_API_KEY = 'pk.eyJ1IjoiaHV5dGh1YTAiLCJhIjoiY20wbXFjcWkzMDUyeTJycXNncG44OGoxYyJ9.GpSOzqXFCvy_HVOsKP-uHQ';

    useEffect(() => {
        const fetchRouteDetail = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/route/${id}`);
                setRoute(response.data);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchRouteDetail();
    }, [id]);

    useEffect(() => {
        const fetchRouteStations = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/route-station/route/${id}`);
                setRouteStations(response.data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchRouteStations();
    }, [id]);

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    const stationPositions = routeStations.map(rs => [rs.station.latitude, rs.station.longitude]);

    return (
        <div className="container mt-4">
            {route ? (
                <>
                    <div className="card mb-4">
                        <div className="card-header">
                            <h2>{route.name}</h2>
                        </div>
                        <div className="card-body">
                            <p><strong>Giá vé:</strong> {route.fare} VND</p>
                            <p><strong>Chuyến đầu:</strong> {route.firstTrip}</p>
                            <p><strong>Chuyến cuối:</strong> {route.lastTrip}</p>
                        </div>
                    </div>

                    <div style={{ width: '100%', height: '400px', minHeight: '400px' }}>
                        <MapContainer
                            center={stationPositions.length ? stationPositions[0] : [10.762622, 106.660172]} // Mặc định tọa độ đầu tiên hoặc fallback
                            zoom={13}
                            style={{ height: '100%', width: '100%' }}
                        >
                            <TileLayer
                                url={`https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/512/{z}/{x}/{y}@2x?access_token=${MAPBOX_API_KEY}`}
                                attribution='&copy; <a href="https://www.mapbox.com/about/maps/">Mapbox</a> contributors'
                                tileSize={512} // Mapbox yêu cầu kích thước tile là 512
                                zoomOffset={-1} // Điều chỉnh để phù hợp với kích thước 512 của Mapbox
                            />

                            {stationPositions.length > 1 && (
                                <Polyline positions={stationPositions} color="blue" />
                            )}

                            {routeStations.map(rs => {
                                const numberedIcon = L.divIcon({
                                    html: `<div style="position: relative; text-align: center; background: #32CD32; border-radius: 50%; width: 25px; height: 25px; line-height: 25px; color: white;">${rs.order}</div>`,
                                    iconSize: [25, 25],
                                    className: 'custom-div-icon'
                                });

                                return (
                                    <Marker
                                        key={rs.station.id}
                                        position={[rs.station.latitude, rs.station.longitude]}
                                        icon={numberedIcon}
                                    >
                                        <Popup>
                                            {rs.station.name} <br /> {rs.station.address}
                                        </Popup>
                                    </Marker>
                                );
                            })}
                        </MapContainer>
                    </div>
                </>
            ) : (
                <p>Không tìm thấy thông tin tuyến đường.</p>
            )}
        </div>
    );
};

export default RouteDetailPage;
