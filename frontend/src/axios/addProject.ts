import { AxiosResponse } from "axios";
import axiosInstance from '../Shared/Api/axiosInstance';
import { projectRequestModel } from "../model/projectRequestModel";

export const addProject = async (
    project: projectRequestModel
  ): Promise<AxiosResponse<void>> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    try {
      return await axiosInstance.post<void>(
        `${backendUrl}/api/project`,
        project
      );
    } catch (error) {
      throw new Error(`Failed to review dish: ${error}`);
    }
  };
