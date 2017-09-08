import React, { Component } from 'react';
import './css/bootstrap.css';


class SignUp extends Component {
    render() {
        return (
            <div className="container">
                <div className="col-md-offset-4 col-md-4">
                    <form className="col-md-offset-1 col-md-10" action="http://localhost:8081/user/add" method="post">
                        <div className="form-group">
                            <label for="exampleInputUserName">UserName</label>
                            <input type="text" name="name" className="form-control" id="exampleInputUserName"
                                   placeholder="UserName"/>
                        </div>
                        <div className="form-group">
                            <label for="exampleInputEmail">Email address</label>
                            <input type="email" name="email" className="form-control" id="exampleInputEmail" placeholder="Email"/>
                        </div>
                        <div className="form-group">
                            <label for="exampleInputPassword">Password</label>
                            <input type="password" name="password" className="form-control" id="exampleInputPassword"/>
                        </div>
                        <button type="submit" className="btn btn-sm">Sign Up</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default SignUp;
