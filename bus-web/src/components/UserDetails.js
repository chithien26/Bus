// UserDetail.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const UserDetail = () => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem('access-token');
            const response = await fetch('http://localhost:8080/current-user', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.ok) {
                const data = await response.json();
                setUser(data);
            } else {
                // Nếu không tìm thấy, điều hướng đến trang đăng nhập
                navigate('/login');
            }
        };

        fetchUser();
    }, [navigate]);

    return (
        <div className="container">
            {user ? (
                <div>
                    <h1>Chi Tiết Người Dùng</h1>
                    <p>Tên người dùng: {user.username}</p>
                    <p>First Name: {user.firstName}</p>
                    <p>Last Name: {user.lastName}</p>
                    <p>Email: {user.email}</p>
                    <p>Số điện thoại: {user.phone}</p>
                    {/* Thêm các thông tin khác mà bạn muốn hiển thị */}
                </div>
            ) : (
                <p>Đang tải thông tin người dùng...</p>
            )}
        </div>
    );
};

export default UserDetail;
