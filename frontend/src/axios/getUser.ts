import {userResponseModel} from "@/model/userResponseModel";
import axiosInstance from "@/Shared/Api/axiosInstance";

export const getUser = async (): Promise<userResponseModel[]> => {
    const backendUrl = process.env.REACT_APP_BACKEND_URL;
    const response = await axiosInstance.get<userResponseModel[]>(
      `${backendUrl}/api/user`
    );
  return response.data;
};
