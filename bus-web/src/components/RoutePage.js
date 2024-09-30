import axios from 'axios';
import React, { useEffect, useState } from 'react';

const RoutePage = () => {
    const [routes, setRoutes] = useState([]); 
    const [loading, setLoading] = useState(true); // State cho loading
    const [error, setError] = useState(null); // State cho lỗi
    const [searchTerm, setSearchTerm] = useState(''); // State cho từ khóa tìm kiếm

    const fetchRoutes = async (keyword = '') => {
        try {
            const response = await axios.get(`http://localhost:8080/route/list?kw=${keyword}`); // Gọi API để lấy dữ liệu routes
            setRoutes(response.data); // Cập nhật state với dữ liệu nhận được
        } catch (err) {
            setError(err.message); // Cập nhật state lỗi nếu xảy ra
        } finally {
            setLoading(false); // Tắt loading sau khi hoàn thành việc lấy dữ liệu
        }
    };

    useEffect(() => {
        fetchRoutes(); // Gọi hàm lấy dữ liệu khi component được render
    }, []); // Chạy một lần khi component được render

    const handleSearch = (e) => {
        e.preventDefault();
        setLoading(true); // Bật trạng thái loading khi tìm kiếm
        fetchRoutes(searchTerm); // Gọi hàm tìm kiếm với từ khóa
    };

    if (loading) {
        return <p>Loading...</p>; // Hiển thị thông báo loading
    }

    if (error) {
        return <p>Error: {error}</p>; // Hiển thị thông báo lỗi
    }

    return (
        <div className="container mt-4">
            <h1 className="text-center mb-4">Danh sách Tuyến Đường</h1>

            <form onSubmit={handleSearch} className="mb-4">
                <div className="input-group" style={{ width: '400px'}}>
                    <input
                        type="text"
                        className="form-control form-control-sm" 
                        placeholder="Tìm kiếm theo tên tuyến"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)} 
                    />
                    <button className="btn btn-primary btn-sm" type="submit">Tìm kiếm</button>
                </div>
            </form>

            <div className="row">
                {routes.map(route => (
                    <div className="col-md-4 mb-4" key={route.id}>
                        <div className="card border-info">
                            <div className="card-header text-white bg-info">
                                <h5 className="card-title">
                                    <span 
                                        className="badge rounded-circle bg-light text-dark me-2"
                                        style={{ width: '30px', height: '30px', display: 'inline-flex', alignItems: 'center', justifyContent: 'center' }}
                                    >
                                        {route.id}
                                    </span>
                                    {route.name}
                                </h5>
                            </div>
                            <div className="card-body">
                                <p className="card-text"><strong>Giá vé:</strong> {route.fare} VND</p>
                                <div className="d-flex justify-content-between">
                                    <p className="card-text"><strong>Chuyến đầu:</strong> {route.firstTrip}</p>
                                    <p className="card-text"><strong>Chuyến cuối:</strong> {route.lastTrip}</p>
                                </div>
                                
                                <button
                                    onClick={() => window.location.href = `/route/${route.id}`}
                                    className="btn btn-primary"
                                    style={{ cursor: 'pointer' }}
                                >
                                    Xem chi tiết
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default RoutePage;
