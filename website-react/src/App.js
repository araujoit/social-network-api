import React from 'react';
import logo from './logo.svg';
import './App.css';
import MenuWebsite from './MenuWebsite';
import SocialNetworkList from './SocialNetwork';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <MenuWebsite />
      </header>
      <section>
        <SocialNetworkList />
      </section>
    </div>
  );
}

export default App;
