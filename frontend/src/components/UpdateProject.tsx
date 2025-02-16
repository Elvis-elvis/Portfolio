import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import "./UpdateProject.css";
import { projectResponseModel, skillResponseModel } from '../model/projectResponseModel';
import { getProject, updateProject } from '../axios/updateProject';
import { getAllSkills } from '../axios/getAllSkills';
import { projectRequestModel } from '../model/projectRequestModel';
import { useTranslation } from 'react-i18next';

const UpdateProject: React.FC = (): JSX.Element => {
  const { projectId } = useParams<{ projectId: string }>();
  const [projectName, setProjectName] = useState<string>('');
  const [iconUrl, setIconUrl] = useState<string>('');
  const [gitRepo, setGitRepo] = useState<string>('');
  const [skills, setSkills] = useState<skillResponseModel[]>([]);
  const [selectedSkills, setSelectedSkills] = useState<skillResponseModel[]>([]);
  const navigate = useNavigate();
  const { t } = useTranslation(); // Using the translation hook

  useEffect(() => {
    const fetchProjectAndSkills = async (): Promise<void> => {
      try {
        if (!projectId) return;

        // Fetch project details
        const project: projectResponseModel = await getProject(projectId);
        setProjectName(project.projectName);
        setIconUrl(project.iconUrl);
        setGitRepo(project.gitRepo);
        setSelectedSkills(project.skills || []); // Ensure selectedSkills is always an array

        // Fetch all skills
        const fetchedSkills = await getAllSkills();
        setSkills(fetchedSkills || []); // Ensure skills is always an array
      } catch (error) {
        console.error('Error fetching project or skills:', error);
      }
    };

    fetchProjectAndSkills();
  }, [projectId]);

  const handleSkillToggle = (skill: skillResponseModel): void => {
    if (selectedSkills.find((s) => s.skillId === skill.skillId)) {
      setSelectedSkills(selectedSkills.filter((s) => s.skillId !== skill.skillId));
    } else {
      setSelectedSkills([...selectedSkills, skill]);
    }
  };

  const handleSubmit = async (event: React.FormEvent): Promise<void> => {
    event.preventDefault();

    if (!projectId) {
      alert(t('projectIdMissing'));
      return;
    }

    const updatedProject: projectRequestModel = {
      projectName,
      iconUrl,
      gitRepo,
      skills: selectedSkills,
    };

    try {
      await updateProject(projectId, updatedProject);
      alert(t('projectUpdatedSuccess'));
      navigate('/');
    } catch (error) {
      console.error('Error updating project:', error);
      alert(t('projectUpdateFailed'));
    }
  };

  return (
    <div className="update-project-form">
      <h2>{t('updateProject')}</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="projectName">{t('projectName')}</label>
          <input
            type="text"
            id="projectName"
            value={projectName}
            onChange={(e) => setProjectName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="iconUrl">{t('iconUrl')}</label>
          <textarea
            id="iconUrl"
            value={iconUrl}
            onChange={(e) => setIconUrl(e.target.value)}
            required
          ></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="gitRepo">{t('gitRepo')}</label>
          <input
            type="text"
            id="gitRepo"
            value={gitRepo}
            onChange={(e) => setGitRepo(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>{t('skills')}</label>
          <div className="skill-logos-container">
            {skills.map((skill) => (
              <img
                key={skill.skillId}
                src={skill.skillLogo}
                alt={skill.skillName}
                className={`skill-logo ${selectedSkills.find((s) => s.skillId === skill.skillId) ? 'selected' : ''}`}
                onClick={() => handleSkillToggle(skill)}
              />
            ))}
          </div>
        </div>
        <button type="submit" className="btn btn-primary">
          {t('updateProjectButton')}
        </button>
      </form>
    </div>
  );
};

export default UpdateProject;
