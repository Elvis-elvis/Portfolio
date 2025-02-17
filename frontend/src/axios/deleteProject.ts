import axiosInstance from '../Shared/Api/axiosInstance';

export const deleteProject = async (projectId: string): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.delete<void>(`${backendUrl}/api/project/${projectId}`);
};
