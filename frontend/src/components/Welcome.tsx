import React, { useState, useEffect } from 'react';
import './Welcome.css';
import Navbar from './Navbar';
import profilePhoto from './assets/profilePhoto.png';
import javaIcon from './assets/java.png';
import pythonIcon from './assets/python.png';
import csharpIcon from './assets/c-sharp.png';
import gitIcon from './assets/git.png';
import usa from './assets/united-states.png';
import french from './assets/france.png';
import spanish from './assets/spain.png';
import kinyarwanda from './assets/flagOfRwanda.png';
import azureIcon from './assets/azure.png'
import bootstrapIcon from './assets/bootstrap.png'
import dockerIcon from './assets/docker.png'
import htmlIcon from './assets/html.png'
import jiraIcon from './assets/jira.png'
import LaravelIcon from './assets/Laravel.png'
import linuxIcon from './assets/linux.png'
import mariadbIcon from './assets/mariadb.png'
import mongodbIcon from './assets/mongodb.png'
import mysqlIcon from './assets/mysql.png'
import reactTypescriptIcon from './assets/reactTypescript.png'
import springBootIcon from './assets/springBoot.png'
import javaScriptIcon from './assets/JavaScript.png'
import ProjectList from "../components/ProjectList";
import { useTranslation } from 'react-i18next';




const Welcome: React.FC = (): JSX.Element => {
  const { t } = useTranslation();


  const createMatrixColumns = () => {
    const columns = [];
    for (let i = 0; i < 15; i++) {
      columns.push(
          <div
              key={i}
              className="matrixColumn"
              style={{ left: `${i * 6}%`, '--delay': Math.random() } as React.CSSProperties}
          />
      );
    }
    return columns;
  };

  const [, setIsElvis] = useState<boolean>(false);

  useEffect(() => {
    const fetchUserRoles = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (!accessToken) {
        console.error('No access token found');
        setIsElvis(false);
        return;
      }

      try {
        const base64Url = accessToken.split('.')[1];
        const decodedPayload = JSON.parse(atob(base64Url));
        const roles = decodedPayload['https://portfolio/roles'] || [];

        setIsElvis(roles.includes('Haitham'));
      } catch (err) {
        console.error('Error decoding user roles:', err);
        setIsElvis(false);
      }
    };

    fetchUserRoles();
  }, []);

  const [matrixColumns, setMatrixColumns] = useState<JSX.Element[]>([]);

  useEffect(() => {
    setMatrixColumns(createMatrixColumns());
  }, []);

  return (
      <div>
        <div id="home" className="welcomePage">
          <div className="matrixRain">{matrixColumns}</div>
          <Navbar/>
          <div className="welcomeText">
            <h1> Hi, I'm Elvis Ruberwa </h1>
          </div>

          <div id="bio" className="section">
            <div className="aboutMeContainer">
              <div className="photoWrapper">
                <img src={profilePhoto} alt="Profile" className="profilePhoto"/>
              </div>
              <div className="infoWrapper">
                <p>
                  I’m a third-year Computer Science student at Champlain College, focused on full stack development, IT,
                  and cybersecurity. I’m passionate about working in teams, which I’ve integrated through playing
                  basketball and working in multiple group projects. I’m ambitious about personal growth and have strong
                  integrity, always ensuring my work is done with care and accountability.
                </p>
              </div>
            </div>
          </div>

          <div id="skills-section">
            <h2>Skills</h2>
            <div className="skills-container">
              <div className="skill-item">
                <img src={javaIcon} alt="Java" className="skill-icon"/>
                <span className="skill-name">Java</span>
              </div>
              <div className="skill-item">
                <img src={javaScriptIcon} alt="JavaScript" className="skill-icon"/>
                <span className="skill-name">JavaScript</span>
              </div>
              <div className="skill-item">
                <img src={pythonIcon} alt="Python" className="skill-icon"/>
                <span className="skill-name">Python</span>
              </div>
              <div className="skill-item">
                <img src={csharpIcon} alt="C#" className="skill-icon"/>
                <span className="skill-name">C#</span>
              </div>
              <div className="skill-item">
                <img src={gitIcon} alt="Git" className="skill-icon"/>
                <span className="skill-name">Git</span>
              </div>
              <div className="skill-item">
                <img src={azureIcon} alt="Azure" className="skill-icon"/>
                <span className="skill-name">Azure</span>
              </div>
              <div className="skill-item">
                <img src={bootstrapIcon} alt="Bootstap" className="skill-icon"/>
                <span className="skill-name">Bootstap</span>
              </div>
              <div className="skill-item">
                <img src={dockerIcon} alt="Docker" className="skill-icon"/>
                <span className="skill-name">Docker</span>
              </div>
              <div className="skill-item">
                <img src={htmlIcon} alt="HTML" className="skill-icon"/>
                <span className="skill-name">HTML</span>
              </div>
              <div className="skill-item">
                <img src={jiraIcon} alt="Jira" className="skill-icon"/>
                <span className="skill-name">Jira</span>
              </div>
              <div className="skill-item">
                <img src={LaravelIcon} alt="Laravel" className="skill-icon"/>
                <span className="skill-name">Laravel</span>
              </div>
              <div className="skill-item">
                <img src={linuxIcon} alt="Linux" className="skill-icon"/>
                <span className="skill-name">Linux</span>
              </div>
              <div className="skill-item">
                <img src={mariadbIcon} alt="MariaDB" className="skill-icon"/>
                <span className="skill-name">MariaDB</span>
              </div>
              <div className="skill-item">
                <img src={mongodbIcon} alt="MongoDB" className="skill-icon"/>
                <span className="skill-name">MongoDB</span>
              </div>
              <div className="skill-item">
                <img src={mysqlIcon} alt="mySQL" className="skill-icon"/>
                <span className="skill-name">mySQL</span>
              </div>
              <div className="skill-item">
                <img src={reactTypescriptIcon} alt="React/TypeScript" className="skill-icon"/>
                <span className="skill-name">React/TypeScript</span>
              </div>
              <div className="skill-item">
                <img src={springBootIcon} alt="SpringBoot" className="skill-icon"/>
                <span className="skill-name">SpringBoot</span>
              </div>

            </div>

            <h2>Languages</h2>
            <div className="languages-container">
              <div className="language-item">
                <img src={usa} alt="English" className="language-flag"/>
                <span className="language-name">English</span>
              </div>
              <div className="language-item">
                <img src={french} alt="French" className="language-flag"/>
                <span className="language-name">French</span>
              </div>
              <div className="language-item">
                <img src={kinyarwanda} alt="Kinyarwanda" className="language-flag"/>
                <span className="language-name">Kinyarwanda</span>
              </div>
              <div className="language-item">
                <img src={spanish} alt="Spanish" className="language-flag"/>
                <span className="language-name">Spanish</span>
              </div>
            </div>
          </div>

          <div id="projects">
            <h2>{t('My Projects')}</h2>
            <ProjectList/>
          </div>

        </div>
      </div>
  );
};

export default Welcome;
