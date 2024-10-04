import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {jwtDecode} from "jwt-decode"; 

const Header = () => {
    const [loggedInUser, setLoggedInUser] = useState(null);
    const [userData, setUserData] = useState(null);

    useEffect(() => {
      const token = localStorage.getItem("token"); // Lấy token từ localStorage
      
      if (token) {
        try {
          // Sử dụng jwtDecode thay vì atob
          const decoded = jwtDecode(token);

          setUserData(decoded); // Lưu thông tin người dùng sau khi decode
        } catch (error) {
          console.error("Failed to decode JWT:", error);
        }
      } else {
        console.log("No token found");
      }
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setLoggedInUser(null);
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div className="container">
                <Link className="navbar-brand" to="/">
                    <i className="fas fa-bus"></i> BusApp
                </Link>
                <button
                    className="navbar-toggler"
                    type="button"
                    data-toggle="collapse"
                    data-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/">
                                <i className="fas fa-home"></i> Home
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/route">
                                <i className="fas fa-route"></i> Route
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/station">
                                <i className="fas fa-bus"></i> Station
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/about">
                                <i className="fas fa-info-circle"></i> About
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/find-route">
                                <i className="fas fa-search"></i> Find Route
                            </Link>
                        </li>
                        <li className="nav-item">
                            {userData ? (
                                <p>Welcome, {userData.username}</p> // Hiển thị thông tin người dùng
                            ) : (
                                <p>Please log in</p>
                            )}
                        </li>
                        {loggedInUser ? (
                            <>
                                <li className="nav-item">
                                    <span className="nav-link text-light">Hello, {loggedInUser}!</span>
                                </li>
                                <li className="nav-item">
                                    <button className="nav-link btn btn-link text-light" onClick={handleLogout}>
                                        <i className="fas fa-sign-out-alt"></i> Logout
                                    </button>
                                </li>
                            </>
                        ) : (
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">
                                    <i className="fas fa-sign-in-alt"></i> Login
                                </Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Header;
