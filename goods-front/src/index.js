import React from 'react';
import ReactDOM from 'react-dom';
import './css/bootstrap.css';
import './css/bootstrap-grid.css';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import SignUp from "./SignUp";
import Header from "./Header";
import logo from './logo.svg';

// ReactDOM.render(<App />, document.getElementById('root'));

class Page extends React.Component {
    render() {
        return (
            <div className="banner ">
                <Header/>
                <div className="container">
                    <div className="row align-self-center">
                        <div className="col-6 align-self-center">
                            <img src={logo} className="app-logo img-responsive"/>
                        </div>
                        <div className="col-6 align-self-center">
                            <SignUp/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

ReactDOM.render(<Page/>, document.getElementById('root'));

registerServiceWorker();
