import React from 'react';
import { Link } from 'react-router-dom'; // Nếu bạn sử dụng React Router

const Header = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div className="container">
                <Link className="navbar-brand" to="/">
                    <i className="fas fa-bus"></i> BusApp
                </Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
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
                                <i className="fas fa-route"></i> Routes
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/station">
                                <i className="fas fa-bus"></i> Stations
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/about">
                                <i className="fas fa-info-circle"></i> About
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Header;
