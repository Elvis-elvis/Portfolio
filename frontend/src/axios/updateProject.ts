import axiosInstance from "../Shared/Api/axiosInstance";
import { projectRequestModel } from "../model/projectRequestModel";
import { projectResponseModel } from "../model/projectResponseModel";



export const updateProject = async (
    projectId: string,
    project: projectRequestModel
  ): Promise<void> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    await axiosInstance.put<void>(`${backendUrl}/api/project/${projectId}`, project);
  };
  
  export const getProject = async (projectId: string): Promise<projectResponseModel> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get<projectResponseModel>(
      `${backendUrl}/api/project/${projectId}`
    );
    return response.data;
  };
  