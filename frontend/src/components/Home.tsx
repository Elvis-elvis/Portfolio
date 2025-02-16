import React from 'react';
import './Home.css'; // Import the CSS file for styling

const Home: React.FC = (): JSX.Element => {
  return (
    <div className="homePage">
      <h2>HOME</h2>
      <div className="bioCardContainer">
        {/* Just displaying the HOME text in a styled card */}
        <div className="bioCard">
          <h3>HOME</h3>
        </div>
      </div>
    </div>
  );
};

export default Home;
