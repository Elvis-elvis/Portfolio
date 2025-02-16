export interface skillResponseModel {
    skillId: number;
    skillName: string;
    skillLogo: string;
  }
  



export interface projectResponseModel {
    projectId: number;
     projectName : string;
     iconUrl : string;
     gitRepo: string;
     skills : skillResponseModel[];
  }
  