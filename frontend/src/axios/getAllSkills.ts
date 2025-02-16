import axiosInstance from "../Shared/Api/axiosInstance";
import {skillResponseModel} from "@/model/projectResponseModel";


export const getAllSkills = async (): Promise<skillResponseModel[]> => {
  // Use menuResponseModel[] directly in the get call
  const backendUrl = process.env.REACT_APP_BACKEND_URL;
  const response = await axiosInstance.get<skillResponseModel[]>(
    `${backendUrl}/api/v1/skill`
  );
  return response.data;
};