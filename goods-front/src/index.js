import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import SignUp from "./SignUp";

// ReactDOM.render(<App />, document.getElementById('root'));
ReactDOM.render(<SignUp />, document.getElementById('root'));
registerServiceWorker();
