import React from 'react';
import './SocialNetwork.css';


class SocialNetworkUserList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        }
    }

    render() {
        let usersFetched = []
        if(this.props.socialNetworkName != "") {
            usersFetched.push('Mussum')
        }

        return (
            <div className="users">
                <h3 className="title">Usuários da rede {this.props.socialNetworkName}</h3>
                <ul>
                    {usersFetched.map(user => (
                        <li className="item" key={user}>
                            {user}
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}
class SocialNetworkList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            list: ['Twitter'],
            socialNetworkName: [],
        };
    }

    render() {
        function handleClick(e) {
            e.preventDefault();
            let socialNetworkName = e.target.text;
            this.setState({
                socialNetworkName: socialNetworkName,
            })
        }

        return (
            <div className="SocialNetwork">
                <div className="list">
                    <h3 className="title">Lista de redes sociais</h3>
                    <ul>
                        {this.state.list.map(item => (
                            <li className="item" key={item}>
                                <a href="#" onClick={handleClick.bind(this)}>
                                    {item}
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
                <SocialNetworkUserList socialNetworkName={this.state.socialNetworkName} />
            </div>
        );
    }
}

export default SocialNetworkList