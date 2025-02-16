import { skillResponseModel } from "./projectResponseModel";

export interface projectRequestModel {
     projectName : string;
     iconUrl : string;
     gitRepo: string;
     skills : skillResponseModel[];
  }
  