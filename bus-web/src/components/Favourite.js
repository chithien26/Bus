import React, { useState, useEffect, useContext } from 'react';
import { MyUserContext } from '../App';
import { ListGroup } from 'react-bootstrap';

const Favourite = () => {
    const [favourites, setFavourites] = useState([]);
    const user = useContext(MyUserContext); // Lấy thông tin người dùng

    useEffect(() => {
        const fetchFavourites = async () => {
            if (!user) return; // Kiểm tra người dùng đã đăng nhập chưa

            try {
                const response = await fetch('http://localhost:8080/favourite', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('access-token')}`, // Thêm token nếu cần
                        'Content-Type': 'application/json'
                    }
                });

                if (!response.ok) {
                    throw new Error('Could not fetch favourites');
                }

                const data = await response.json();
                setFavourites(data);
            } catch (error) {
                console.error('Error fetching favourites:', error);
            }
        };

        fetchFavourites();
    }, [user]);

    return (
        <div className="container mt-5">
            <h1>Danh Sách Tuyến Đường Yêu Thích</h1>
            <ListGroup>
                {favourites.length === 0 ? (
                    <ListGroup.Item>Chưa có tuyến đường yêu thích nào.</ListGroup.Item>
                ) : (
                    favourites.map((favourite) => (
                        <ListGroup.Item key={favourite.id}>
                            <strong>{favourite.route.routeNumber} {favourite.route.name}</strong>
                        </ListGroup.Item>
                    ))
                )}
            </ListGroup>
        </div>
    );
};

export default Favourite;
