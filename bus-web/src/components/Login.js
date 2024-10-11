// Login.js
import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { MyDispatchContext } from '../App';
import axios from 'axios';
import { Form, Button, Alert, Container } from 'react-bootstrap';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const dispatch = useContext(MyDispatchContext);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            // Gửi yêu cầu đăng nhập đến API
            const response = await axios.post('http://localhost:8080/login', {
                username,
                password
            });

            // Nhận token từ phản hồi và lưu trữ vào localStorage hoặc cookie
            const { token } = response.data;
            localStorage.setItem('access-token', token);

            // Lưu thông tin người dùng vào Context
            dispatch({ type: 'login', payload: { username } });

            // Điều hướng đến trang chủ sau khi đăng nhập thành công
            navigate('/');
        } catch (err) {
            setError('Tên đăng nhập hoặc mật khẩu không đúng');
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "80vh" }}>
            <div style={{ width: "400px" }}>
                <h3 className="text-center mb-4">Đăng Nhập</h3>
                {error && <Alert variant="danger">{error}</Alert>}
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="username">
                        <Form.Label>Tên đăng nhập</Form.Label>
                        <Form.Control
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group controlId="password" className="mt-3">
                        <Form.Label>Mật khẩu</Form.Label>
                        <Form.Control
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit" className="mt-4 w-100">
                        Đăng nhập
                    </Button>
                </Form>
            </div>
        </Container>
    );
};

export default Login;
