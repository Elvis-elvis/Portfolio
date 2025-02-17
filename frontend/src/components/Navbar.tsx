import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';  // Import Link from react-router-dom
import './Navbar.css';
import i18n from "../i18n"; // Import i18n instance
import { useTranslation } from "react-i18next"; // Import translation hook


const Navbar: React.FC = (): JSX.Element => {

  const { t } = useTranslation(); // Use translation function
  const [loading, setLoading] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isElvis, setIsElvis] = useState<boolean>(false);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("access_token");
    if (token) {
      setIsAuthenticated(true);
      fetchUserRoles(token);
    }
  }, []);

  const fetchUserRoles = (token: string) => {
    try {
      const base64Url = token.split(".")[1];
      const decodedPayload = JSON.parse(atob(base64Url));
      const roles = decodedPayload["https://portfolio/roles"] || [];

      setIsElvis(roles.includes("Elvis"));
    } catch (err) {
      console.error("Error decoding user roles:", err);
      setIsElvis(false);
    }
  };

  const handleLoginRedirect = () => {
    setLoading(true);
    const audience = "https://dev-mwu16egco04uokhe.us.auth0.com/api/v2/";
    const clientId = process.env.REACT_APP_AUTH0_CLIENT_ID;
    const redirectUri = process.env.REACT_APP_AUTH0_CALLBACK_URL;

    window.location.href =
        `https://dev-mwu16egco04uokhe.us.auth0.com/authorize?` +
        `response_type=token&` +
        `client_id=${clientId}&` +
        `redirect_uri=${redirectUri}&` +
        `scope=openid profile email read:current_user read:roles&` +
        `audience=${audience}&` +
        `prompt=login`;
  };

  const handleLogout = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("auth_token");
    sessionStorage.clear();

    setIsAuthenticated(false);
    setIsElvis(false);
    window.location.href = "/";
  };

  const toggleLanguage = () => {
    const newLanguage = i18n.language === "en" ? "fr" : "en";
    i18n.changeLanguage(newLanguage);
  };

  const scrollToSection = (sectionId: string) => {
    const section = document.getElementById(sectionId);
    if (section) {
      section.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
      <nav className="navbar">


        <ul className={`navbarList ${menuOpen ? "active" : ""}`}>
          <li onClick={() => scrollToSection('home')} className="navbarItem">
            <Link to="/">{t("navbar.home")}</Link>
          </li>
          <li onClick={() => scrollToSection('bio')} className="navbarItem">
            <Link to="/">{t("navbar.bio")}</Link>
          </li>
          <li onClick={() => scrollToSection('skills-section')} className="navbarItem">
            <Link to="/">{t("navbar.skills")}</Link>
          </li>
          <li onClick={() => scrollToSection('projects')} className="navbarItem">
            <Link to="/">{t("navbar.projects")}</Link>
          </li>
          <li className="navbarItem">
            <Link to="/comments">{t("navbar.comments")}</Link>
          </li>
          {/* Social Media Links */}
          <li className="navbarItem">
            <a href="https://www.linkedin.com/in/elvis-ruberwa-a2b7b4328/" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-linkedin"></i>
            </a>
          </li>
          <li className="navbarItem">
            <a href="https://github.com/Elvis-elvis" target="_blank" rel="noopener noreferrer">
              <i className="fab fa-github"></i>
            </a>
          </li>

          {isAuthenticated && isElvis && (
              <li className="navbarItem">
                <Link to="/Admin" onClick={() => setMenuOpen(false)}>{t("navbar.adminDashboard")}</Link>
              </li>
          )}

          {!isAuthenticated ? (
              <button className={`loginButton ${loading ? "loading" : ""}`} onClick={handleLoginRedirect}
                      disabled={loading}>
                {loading ? t("navbar.redirecting") : t("navbar.login")}
              </button>
          ) : (
              <button className="loginButton" onClick={handleLogout}>
                {t("navbar.logout")}
              </button>
          )}

          {/* Translation Button */}
          <button className="translateButton" onClick={toggleLanguage}>
            {i18n.language.toUpperCase()}
          </button>
        </ul>

      </nav>
  );
};

export default Navbar;
