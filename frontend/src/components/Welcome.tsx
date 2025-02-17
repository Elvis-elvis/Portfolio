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

  const skillIcons: { [key: string]: string } = {
    java: javaIcon,
    python: pythonIcon,
    csharp: csharpIcon,
    git: gitIcon,
    azure: azureIcon,
    bootstrap: bootstrapIcon,
    docker: dockerIcon,
    html: htmlIcon,
    jira: jiraIcon,
    javaScript: javaScriptIcon,
    laravel: LaravelIcon,
    linux: linuxIcon,
    mariadb: mariadbIcon,
    mongodb: mongodbIcon,
    mysql: mysqlIcon,
    reactTypescript: reactTypescriptIcon,
    springBoot: springBootIcon,
  };

  const languageIcons: { [key: string]: string } = {
    english: usa,
    french: french,
    kinyarwanda: kinyarwanda,
    spanish: spanish,
  };


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
            <h1>{t("welcomePage.greeting")}</h1>

            <div id="bio" className="section">
              <div className="aboutMeContainer">
                <div className="photoWrapper">
                  <img src={profilePhoto} alt="Profile" className="profilePhoto"/>
                </div>
                <div className="infoWrapper">
                  <p>{t("welcomePage.bioSection.bioDescription")}</p>
                </div>
              </div>
            </div>

            <div id="skills-section">
              <h2>{t('welcomePage.skills.title')}</h2>
              <div className="skills-container">
                {Object.keys(skillIcons).map((skill) => (
                    <div className="skill-item" key={skill}>
                      <img src={skillIcons[skill]} alt={t(`welcomePage.skills.${skill}`)} className="skill-icon"/>
                      <span className="skill-name">{t(`welcomePage.skills.${skill}`)}</span>
                    </div>
                ))}
              </div>
            </div>

            <h2>{t('welcomePage.languages.title')}</h2>
            <div className="languages-container">
              {Object.keys(languageIcons).map((language) => (
                  <div className="language-item" key={language}>
                    <img src={languageIcons[language]} alt={t(`welcomePage.languages.${language}`)}
                         className="language-flag"/>
                    <span className="language-name">{t(`welcomePage.languages.${language}`)}</span>
                  </div>
              ))}
            </div>
          </div>

          <div id="projects">
            <h2>{t('welcomePage.projects')}</h2>
            <ProjectList/>
          </div>

        </div>
      </div>
  );
};

export default Welcome;
