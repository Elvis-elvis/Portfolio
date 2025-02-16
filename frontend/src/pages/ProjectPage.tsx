import ProjectList from "@/components/ProjectList";
import {Navbar} from "react-bootstrap";


export default function ProjectPage(): JSX.Element {
  return (
    <div>
      <Navbar />
      <ProjectList />
    </div>
  );
}
