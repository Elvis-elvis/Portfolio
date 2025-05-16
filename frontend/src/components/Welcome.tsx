import React, { useState, useEffect } from 'react';
import './Welcome.css';
import Navbar from './Navbar';
import profilePhoto from './assets/profilePhoto.png';
import ProjectList from "../components/ProjectList";
import { useTranslation } from 'react-i18next';
import { getBio, updateBio } from '../axios/bioActions';
import { getAllLanguages, addLanguage, updateLanguage, deleteLanguage } from '../axios/languageActions';

import javaIcon from './assets/java.png';
import pythonIcon from './assets/python.png';
import csharpIcon from './assets/c-sharp.png';
import gitIcon from './assets/git.png';
import azureIcon from './assets/azure.png';
import bootstrapIcon from './assets/bootstrap.png';
import dockerIcon from './assets/docker.png';
import htmlIcon from './assets/html.png';
import jiraIcon from './assets/jira.png';
import LaravelIcon from './assets/Laravel.png';
import linuxIcon from './assets/linux.png';
import mariadbIcon from './assets/mariadb.png';
import mongodbIcon from './assets/mongodb.png';
import mysqlIcon from './assets/mysql.png';
import reactTypescriptIcon from './assets/reactTypescript.png';
import springBootIcon from './assets/springBoot.png';
import javaScriptIcon from './assets/JavaScript.png';

const Welcome: React.FC = (): JSX.Element => {
  const { t } = useTranslation();
  const [bio, setBio] = useState('');
  const [tempBio, setTempBio] = useState('');
  const [editing, setEditing] = useState(false);
  const [isElvis, setIsElvis] = useState(false);
  const [languages, setLanguages] = useState<any[]>([]);
  const [newLangName, setNewLangName] = useState('');
  const [newLangFlag, setNewLangFlag] = useState('');
  const [editingLangId, setEditingLangId] = useState<string | null>(null);
  const [matrixColumns, setMatrixColumns] = useState<JSX.Element[]>([]);

  useEffect(() => {
    const fetchUserRoles = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (!accessToken) return;

      try {
        const base64Url = accessToken.split('.')[1];
        const decodedPayload = JSON.parse(atob(base64Url));
        const roles = decodedPayload['https://portfolio/roles'] || [];
        setIsElvis(roles.includes('Elvis'));
      } catch (err) {
        console.error('Error decoding roles:', err);
        setIsElvis(false);
      }
    };

    const fetchBio = async () => {
      try {
        const data = await getBio();
        setBio(data.content);
        setTempBio(data.content);
      } catch (err) {
        console.error('Failed to fetch bio:', err);
      }
    };

    const fetchLangs = async () => {
      try {
        const data = await getAllLanguages();
        setLanguages(data);
      } catch (err) {
        console.error('Failed to fetch languages:', err);
      }
    };

    fetchUserRoles();
    fetchBio();
    fetchLangs();

    const columns = Array.from({ length: 15 }, (_, i) => (
      <div
        key={i}
        className="matrixColumn"
        style={{ left: `${i * 6}%`, '--delay': Math.random() } as React.CSSProperties}
      />
    ));
    setMatrixColumns(columns);
  }, []);

  const handleSave = async () => {
    try {
      await updateBio(tempBio);
      setBio(tempBio);
      setEditing(false);
      alert("Bio updated successfully!");
    } catch (err) {
      console.error("Failed to update bio:", err);
      alert("Error updating bio.");
    }
  };

  const handleLangSubmit = async () => {
    try {
      if (editingLangId) {
        await updateLanguage(editingLangId, { name: newLangName, flagUrl: newLangFlag });
      } else {
        await addLanguage({ name: newLangName, flagUrl: newLangFlag });
      }
      const updated = await getAllLanguages();
      setLanguages(updated);
      setNewLangName('');
      setNewLangFlag('');
      setEditingLangId(null);
    } catch (err) {
      alert('Failed to save language');
    }
  };

  const handleEditLang = (lang: any) => {
    setNewLangName(lang.name);
    setNewLangFlag(lang.flagUrl);
    setEditingLangId(lang.id);
  };

  const handleDeleteLang = async (id: string) => {
    try {
      await deleteLanguage(id);
      setLanguages(await getAllLanguages());
    } catch (err) {
      alert('Failed to delete language');
    }
  };

  const skillIcons: Record<string, string> = {
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

  return (
    <div id="home" className="welcomePage">
      <div className="matrixRain">{matrixColumns}</div>
      <Navbar />
      <div className="welcomeText">
        <h1>{t("welcomePage.greeting")}</h1>

        <div id="bio" className="section">
          <div className="aboutMeContainer">
            <div className="photoWrapper">
              <img src={profilePhoto} alt="Profile" className="profilePhoto" />
            </div>
            <div className="infoWrapper">
              {editing ? (
                <>
                  <textarea
                    value={tempBio}
                    onChange={(e) => setTempBio(e.target.value)}
                    className="bio-edit-textarea"
                  />
                  <button className="btn btn-success mt-2" onClick={handleSave}>
                    {t('update')}
                  </button>
                </>
              ) : (
                <>
                  <p className="bio-paragraph">{bio}</p>
                  {isElvis && (
                    <button
                      className="btn btn-light btn-sm mt-2"
                      style={{ position: 'relative', zIndex: 9999, backgroundColor: 'white' }}
                      onClick={() => setEditing(true)}
                    >
                      ✏️
                    </button>
                  )}
                </>
              )}
            </div>
          </div>
        </div>

        <div id="skills-section">
          <h2>{t('welcomePage.skills.title')}</h2>
          <div className="skills-container">
            {Object.keys(skillIcons).map((skill) => (
              <div className="skill-item" key={skill}>
                <img src={skillIcons[skill]} alt={t(`welcomePage.skills.${skill}`)} className="skill-icon" />
                <span className="skill-name">{t(`welcomePage.skills.${skill}`)}</span>
              </div>
            ))}
          </div>
        </div>

        <h2>{t('welcomePage.languages.title')}</h2>
        <div className="languages-container">
          {languages.map((lang) => (
            <div className="language-item" key={lang.id}>
              <img src={lang.flagUrl} alt={lang.name} className="language-flag" />
              <span className="language-name">{lang.name}</span>
              {isElvis && (
                <>
                  <button onClick={() => handleEditLang(lang)}>✏️</button>
                  <button onClick={() => handleDeleteLang(lang.id)}>🗑️</button>
                </>
              )}
            </div>
          ))}
        </div>

        {isElvis && (
          <div className="language-form">
            <input
              type="text"
              placeholder="Language Name"
              value={newLangName}
              onChange={(e) => setNewLangName(e.target.value)}
            />
            <input
              type="text"
              placeholder="Flag URL"
              value={newLangFlag}
              onChange={(e) => setNewLangFlag(e.target.value)}
            />
            <button className="btn btn-primary" onClick={handleLangSubmit}>
              {editingLangId ? 'Update' : 'Add'}
            </button>
          </div>
        )}
      </div>

      <div id="projects">
        <h2>{t('welcomePage.projects')}</h2>
        <ProjectList />
      </div>
    </div>
  );
};

export default Welcome;