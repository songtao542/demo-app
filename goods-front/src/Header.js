import React, {Component} from 'react';
import './css/bootstrap.css';
import './css/bootstrap-grid.css';
import "./header.css"
import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';
import NavDropdown from 'react-bootstrap/lib/NavDropdown';
import MenuItem from 'react-bootstrap/lib/MenuItem';


class Header extends Component {
    render() {
        return (
            <nav className="navbar  navbar-expand-lg navbar-dark bg-dark ">
                <div className="container">
                    <a className="navbar-brand" href="#">Navbar</a>
                    <button className="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
                        <div className="navbar-nav mr-auto">
                            <a className="nav-item nav-link active" href="#">Home</a>
                            <a className="nav-item nav-link" href="#">Features</a>
                            <a className="nav-item nav-link" href="#">Pricing</a>
                            <a className="nav-item nav-link disabled " href="#">Disabled</a>
                        </div>
                        <div className="navbar-nav float-right my-2 my-lg-0">
                            <a className="nav-item nav-link" href="#">Sign Up</a>
                            <span className="nav-span align-self-center">or</span>
                            <a className="nav-item nav-link" href="#">Sign In</a>
                        </div>
                    </div>
                </div>
            </nav>
        );
    }
}

export default Header;
