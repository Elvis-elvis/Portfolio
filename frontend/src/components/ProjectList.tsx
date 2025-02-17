import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './ProjectList.css';
import { projectResponseModel } from '../model/projectResponseModel';
import { getAllProjects } from '../axios/getAllProjects';
import { useTranslation } from 'react-i18next';
import {deleteProject} from "@/axios/deleteProject";

const ProjectList: React.FC = (): JSX.Element => {
  const [projects, setProjects] = useState<projectResponseModel[]>([]);
  const [isZako, setIsZako] = useState<boolean>(false); // State to check if the user has the "Zako" role
  const [loading, setLoading] = useState<boolean>(true);
  const navigate = useNavigate();
  const { t } = useTranslation();  // Using the translation hook

  const handleDeleteProject = async (projectId: string) => {
    if (window.confirm("Are you sure you want to delete this project?")) {
      try {
        await deleteProject(projectId);
        setProjects((prevProjects) =>
            prevProjects.filter(p => p.projectId !== parseInt(projectId))
        );
      } catch (error) {
        console.error("Error deleting project:", error);
      }
    }
  };

  useEffect(() => {
    const fetchUserRoles = async () => {
      const accessToken = localStorage.getItem('access_token');
      if (!accessToken) {
        console.error('No access token found');
        setIsZako(false);
        return;
      }

      try {
        const base64Url = accessToken.split('.')[1];
        const decodedPayload = JSON.parse(atob(base64Url));
        const roles = decodedPayload['https://portfolio/roles'] || []; // Replace with your namespace

        setIsZako(roles.includes('Elvis')); // Check if the user has the "Zako" role
      } catch (err) {
        console.error('Error decoding user roles:', err);
        setIsZako(false);
      }
    };

    const fetchProjectData = async (): Promise<void> => {
      try {
        setLoading(true);
        const response = await getAllProjects();
        if (Array.isArray(response)) {
          setProjects(response);
        } else {
          console.error('Fetched data is not an array:', response);
        }
      } catch (error) {
        console.error('Error fetching projects:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserRoles(); // Fetch and check user roles
    fetchProjectData(); // Fetch project data
  }, []);

  const handleAddProject = (): void => {
    navigate('/addProject');
  };

  const handleUpdateProject = (projectId: number): void => {
    navigate(`/updateProject/${projectId}`);
  };

  if (loading) {
    return <div>{t('loadingProjects')}</div>;
  }

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        {isZako && (
            <button className="btn btn-success btn-circle" onClick={handleAddProject}>
              +
            </button>

        )}
      </div>
      <div className="row">
        {projects.length > 0 ? (
          projects.map((project) => (
            <div className="col-md-6 mb-4" key={project.projectId}>

              <div className="card project-card">
                <div className="card-img-wrapper">
                  <a 
                    href={
                      project.projectName === "Compte Express"
                        ? "https://github.com/Carlos-123321/CompteExpress"
                        : project.projectName === "Champlain Pet Clinic"
                        ? "https://github.com/cgerard321/champlain_petclinic"
                        : project.projectName === "Interactive Dashboard for Civision Company"
                        ? "https://github.com/Elvis-elvis/interactive-dashboard"
                        : "#"
                    } 
                    target="_blank" 
                    rel="noopener noreferrer"
                  >
                    <img
                      src={project.iconUrl}
                      alt={project.projectName}
                      className="card-img-top project-image"
                    />
                  </a>
                </div>
                <div className="card-body">
                  <h5 className="card-title project-name">{project.projectName}</h5>
                  <p className="card-text project-description">{project.gitRepo}</p>
                </div>
                <div className="card-footer">
                  <div className="skill-logos">
                    {project.skills.map((skill) => (
                      <img
                        key={skill.skillId}
                        src={skill.skillLogo}
                        alt={skill.skillName}
                        className="skill-logo"
                      />
                    ))}
                  </div>
                </div>

                {isZako && (
                    <div className="d-flex justify-content-between mb-2">
                      <button
                          className="btn btn-secondary btn-sm btn-circle"
                          onClick={() => handleUpdateProject(project.projectId)}
                      >
                        ✏️
                      </button>
                      <button
                          className="btn btn-danger btn-sm btn-circle"
                          onClick={() => handleDeleteProject(project.projectId.toString())} // Convert to string here
                      >
                        ❌
                      </button>

                    </div>
                )}

              </div>
            </div>
          ))
        ) : (
          <p className="no-items">{t('noProjectsAvailable')}</p>
        )}
      </div>
    </div>
  );
};

export default ProjectList;
