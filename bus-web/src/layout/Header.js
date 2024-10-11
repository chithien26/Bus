// Header.js
import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { MyDispatchContext, MyUserContext } from '../App';
import { Button } from 'react-bootstrap';

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('access-token');
        dispatch({ type: 'logout' });
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div className="container">
                <Link className="navbar-brand" to="/">
                    <i className="fas fa-bus"></i> BusApp
                </Link>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/"> <i className="fas fa-home"></i> Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/route"> <i className="fas fa-route"></i> Route</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/station"><i className="fas fa-bus"></i> Station</Link>
                        </li>
                        <li className="nav-item d-flex align-items-center"> {/* Sử dụng d-flex và align-items-center */}
                            {user ? (
                                <>
                                    <span className="nav-link me-2"><i className="fas fa-user"></i> {user.username}</span>
                                    <Link className="nav-link" to="#" onClick={handleLogout}>
                                        <i className="fas fa-sign-out-alt"></i> Đăng xuất
                                    </Link>
                                </>
                            ) : (
                                <Link className="nav-link" to="/login"><i className="fas fa-sign-in-alt"></i> Đăng nhập</Link>
                            )}
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Header;
