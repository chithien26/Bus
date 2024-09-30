import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const Home = () => {
    return (
        <div className="container mt-5">
            <header className="text-center mb-4" style={{ backgroundColor: '#007bff', color: 'white', padding: '20px', borderRadius: '10px', marginBottom: '20px' }}>
                <h1 className="display-4">Chào Mừng Đến Với Dịch Vụ Xe Buýt Công Cộng</h1>
                <p className="lead">Kết nối thành phố, giảm thiểu ùn tắc, bảo vệ môi trường.</p>
            </header>

            <div className="row mb-4">
                <div className="col-md-6">
                    <img src="https://via.placeholder.com/500" alt="Bus Service" className="img-fluid rounded shadow" />
                </div>
                <div className="col-md-6">
                    <h2>Giới thiệu Dịch Vụ</h2>
                    <p>Chúng tôi cung cấp dịch vụ xe buýt công cộng an toàn và tiện lợi, phục vụ nhu cầu di chuyển của người dân thành phố. Với nhiều tuyến đường và thời gian linh hoạt, chúng tôi mong muốn mang đến sự thuận tiện cho mọi người.</p>
                    <button className="btn btn-primary">Tìm hiểu thêm</button>
                </div>
            </div>

            <div className="text-center">
                <h2>Ưu Điểm</h2>
                <div className="row">
                    <div className="col-md-4">
                        <div className="card mb-4 shadow-sm" style={{ transition: 'transform 0.2s' }}>
                            <img src="https://via.placeholder.com/300" alt="Economical" className="card-img-top" />
                            <div className="card-body">
                                <h5 className="card-title">Tiết Kiệm Chi Phí</h5>
                                <p className="card-text">Giá vé phải chăng giúp bạn tiết kiệm tối đa chi phí đi lại.</p>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="card mb-4 shadow-sm" style={{ transition: 'transform 0.2s' }}>
                            <img src="https://via.placeholder.com/300" alt="Convenient" className="card-img-top" />
                            <div className="card-body">
                                <h5 className="card-title">Tiện Lợi</h5>
                                <p className="card-text">Nhiều tuyến đường và thời gian linh hoạt giúp bạn dễ dàng di chuyển.</p>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="card mb-4 shadow-sm" style={{ transition: 'transform 0.2s' }}>
                            <img src="https://via.placeholder.com/300" alt="Eco-Friendly" className="card-img-top" />
                            <div className="card-body">
                                <h5 className="card-title">Thân Thiện Với Môi Trường</h5>
                                <p className="card-text">Giảm thiểu lượng khí thải và ùn tắc giao thông.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Home;
