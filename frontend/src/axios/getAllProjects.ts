import { projectResponseModel } from '../model/projectResponseModel';
import axiosInstance from '../Shared/Api/axiosInstance';


export const getAllProjects = async (): Promise<projectResponseModel[]> => {
  // Use menuResponseModel[] directly in the get call
  const backendUrl = process.env.REACT_APP_BACKEND_URL;
  const response = await axiosInstance.get<projectResponseModel[]>(
    `${backendUrl}/api/project`
  );
  return response.data;
};