import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './css/bootstrap.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import SignUp from "./SignUp";
import Header from "./Header";
import logo from './logo.svg';

// ReactDOM.render(<App />, document.getElementById('root'));

class Page extends React.Component {
    render() {
        return (
            <div className="banner">
                <Header/>
                <div className="container banner-bottom center-block">
                    <div className="col-md-6">
                        <img src={logo} className="app-logo img-responsive center-block"/>
                    </div>
                    <div className="col-md-6 row">
                        <div className="col-md-offset-1 col-md-8  ">
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
