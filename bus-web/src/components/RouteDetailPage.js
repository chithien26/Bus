import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';

// Tạo icon mặc định cho trạm xe buýt
const busStationIcon = new L.Icon({
    iconUrl: 'https://cdn-icons-png.flaticon.com/512/2750/2750715.png',
    iconSize: [25, 25],
});

const RouteDetailPage = () => {
    const { id } = useParams();
    const [route, setRoute] = useState(null);
    const [routeStations, setRouteStations] = useState([]); // Dữ liệu các trạm
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch thông tin tuyến đường
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

    // Fetch thông tin các trạm thuộc tuyến
    useEffect(() => {
        const fetchRouteStations = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/route-station/route/${id}`);
                setRouteStations(response.data); // Cập nhật danh sách các trạm
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

    // Tạo danh sách tọa độ từ các trạm để vẽ Polyline
    const stationPositions = routeStations.map(rs => [rs.station.latitude, rs.station.longitude]);

    return (
        <div className="container mt-4 mb-5">
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

                    <MapContainer
                        center={[10.762622, 106.660172]} // Toạ độ mặc định (TPHCM)
                        zoom={14}
                        style={{ height: '400px', width: '100%' }}
                    >
                        <TileLayer
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        />

                        {/* Vẽ đường nối giữa các trạm */}
                        <Polyline positions={stationPositions} color="blue" />

                        {routeStations.map(rs => {
                            // Tạo DivIcon hiển thị số thứ tự
                            const numberedIcon = L.divIcon({
                                html: `<div style="position: relative; text-align: center; background: #32CD32; border-radius: 50%; width: 25px; height: 25px; line-height: 25px; color: white;">${rs.order}</div>`,
                                iconSize: [25, 25], // Kích thước của số
                                className: 'custom-div-icon' // Lớp CSS tùy chỉnh
                            });

                            return (
                                <Marker
                                    key={rs.station.id}
                                    position={[rs.station.latitude, rs.station.longitude]} // Sử dụng tọa độ của trạm
                                    icon={numberedIcon} // Sử dụng icon số thứ tự
                                >
                                    <Popup>
                                        {rs.station.name} <br /> {rs.station.address}
                                    </Popup>
                                </Marker>
                            );
                        })}
                    </MapContainer>
                </>
            ) : (
                <p>Không tìm thấy thông tin tuyến đường.</p>
            )}
        </div>
    );
};

export default RouteDetailPage;
